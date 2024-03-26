package org.rmb.assessment.model;

import org.rmb.assessment.enums.Side;

import java.util.PriorityQueue;

public class MatchingEngine {
    private OrderBook orderBook;

    public MatchingEngine(OrderBook orderBook) {
        this.orderBook = orderBook;
    }


    public void executeTrade(Order order) {
        if (order.getSide() == Side.BUY) {
            executeBuyTrade(order);
        } else {
            executeSellTrade(order);
        }
    }

    public void executeSellTrade(Order sellOrder) {
        PriorityQueue<Order> buyOrders = orderBook.getBuyOrders();
        boolean matched = false;

        while (!buyOrders.isEmpty() && sellOrder.getQuantity() > 0) {
            Order buyOrder = buyOrders.peek();
            if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                int quantityToExecute = Math.min(sellOrder.getQuantity(), buyOrder.getQuantity());
                executeTrade(buyOrder, sellOrder, quantityToExecute);
                matched = true;
            } else {
                break; // No more matching orders at this price level
            }
        }

        // If no matching orders found, add sell order back to order book
        if (!matched) {
            orderBook.addOrder(sellOrder);
        }
    }

    public void executeBuyTrade(Order buyOrder) {
        PriorityQueue<Order> sellOrders = orderBook.getSellOrders();
        boolean matched = false;

        while (!sellOrders.isEmpty() && buyOrder.getQuantity() > 0) {
            Order sellOrder = sellOrders.peek();
            if (sellOrder.getPrice() <= buyOrder.getPrice()) {
                int quantityToExecute = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                executeTrade(buyOrder, sellOrder, quantityToExecute);
                matched = true;
            } else {
                break; // No more matching orders at this price level
            }
        }

        // If no matching orders found, add buy order back to order book
        if (!matched) {
            orderBook.addOrder(buyOrder);
        }
    }

    public void matchOrders() {
        PriorityQueue<Order> buyOrders = orderBook.getBuyOrders();
        PriorityQueue<Order> sellOrders = orderBook.getSellOrders();

        while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();
            Order sellOrder = sellOrders.peek();

            if (buyOrder.getPrice() >= sellOrder.getPrice()) {
                int quantityToMatch = Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
                executeTrade(buyOrder, sellOrder, quantityToMatch);
            } else {
                break; // No more matching orders at this price level
            }
        }
    }

    public void executeTrade(Order buyOrder, Order sellOrder, int quantity) {
        // Update the order book with the executed trade
        orderBook.getOrderMap().remove(buyOrder.getId());
        orderBook.getOrderMap().remove(sellOrder.getId());

        orderBook.getBuyOrders().remove(buyOrder);
        orderBook.getSellOrders().remove(sellOrder);

        buyOrder.setQuantity(buyOrder.getQuantity() - quantity);
        sellOrder.setQuantity(sellOrder.getQuantity() - quantity);

        if (buyOrder.getQuantity() > 0) {
            orderBook.getBuyOrders().add(buyOrder);
            orderBook.getOrderMap().put(buyOrder.getId(), buyOrder);
        }

        if (sellOrder.getQuantity() > 0) {
            orderBook.getSellOrders().add(sellOrder);
            orderBook.getOrderMap().put(sellOrder.getId(), sellOrder);
        }

        System.out.println("Trade executed between  Order " + buyOrder.getId() + " and  Order " + sellOrder.getId()
                + " for quantity " + quantity + " at price " + sellOrder.getPrice());
    }

}
