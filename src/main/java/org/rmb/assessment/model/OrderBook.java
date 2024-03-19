package org.rmb.assessment.model;

import org.rmb.assessment.comparator.BuyOrderComparator;
import org.rmb.assessment.comparator.SellOrderComparator;
import org.rmb.assessment.enums.Side;

import java.util.*;

public class OrderBook {
    private Map<String, Order> orderMap;
    private PriorityQueue<Order> buyOrders;
    private PriorityQueue<Order> sellOrders;

    public OrderBook() {
        this.orderMap = new HashMap<>();
        this.buyOrders = new PriorityQueue<>(new BuyOrderComparator());
        this.sellOrders = new PriorityQueue<>(new SellOrderComparator());
    }

    public void addOrder(double price, int quantity, Side side) {
        Order order = new Order(price, quantity, side);
        orderMap.put(order.getId(),order);
        if (side == Side.BUY) {
            buyOrders.add(order);
        } else {
            sellOrders.add(order);
        }
        System.out.println("Added Order: " + order);
    }

    public void deleteOrder(String id) {
        Order order = orderMap.get(id);
        if (order == null) {
            throw new IllegalArgumentException("Order ID not found");
        }
        orderMap.remove(id);
        if (order.getSide() == Side.BUY) {
            buyOrders.remove(order);
        } else {
            sellOrders.remove(order);
        }
        System.out.println("Deleted Order: "+ order);
    }

    public void modifyOrder(String id, int newQuantity) {
        Order order = orderMap.get(id);
        if (order == null) {
            throw new IllegalArgumentException("Order ID not found");
        }
        System.out.println("Order Before Modification: " + order);
        order.setQuantity(newQuantity);
        // Re-add the order to lose priority
        deleteOrder(id);
        addOrder(order.getPrice(), newQuantity, order.getSide());

    }

    public List<Order> getOrdersInPriceRange(double minPrice, double maxPrice, String sideStr) {
        Side side = Side.valueOf(sideStr.toUpperCase());
        PriorityQueue<Order> ordersQueue = (side == Side.BUY) ? buyOrders : sellOrders;
        List<Order> ordersInRange = new ArrayList<>();
        for (Order order : ordersQueue) {
            if (order.getPrice() >= minPrice && order.getPrice() <= maxPrice) {
                ordersInRange.add(order);
            }
        }
        return ordersInRange;
    }

    public Map<String, Order> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public void setBuyOrders(PriorityQueue<Order> buyOrders) {
        this.buyOrders = buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }

    public void setSellOrders(PriorityQueue<Order> sellOrders) {
        this.sellOrders = sellOrders;
    }
}
