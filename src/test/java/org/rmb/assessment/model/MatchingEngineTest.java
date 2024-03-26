package org.rmb.assessment.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmb.assessment.enums.Side;

import static org.junit.jupiter.api.Assertions.*;

class MatchingEngineTest {
    private OrderBook orderBook;
    private MatchingEngine matchingEngine;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
        matchingEngine = new MatchingEngine(orderBook);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void executeTrade() {
        Order buyOrder = new Order(9, 40, Side.BUY);

        // Add the buy order to the order book
        orderBook.addOrder(buyOrder);

        // Call executeTrade() with the buy order
        matchingEngine.executeTrade(buyOrder);

        // Assert that the buy order is not removed because there is no match
        assertFalse(orderBook.getBuyOrders().isEmpty());
    }

    @Test
    public void testExecuteSellTrade() {
        // Create sell order
        Order sellOrder = new Order(9, 55, Side.SELL);

        // Add buy orders to the order book
        orderBook.addOrder(9, 40, Side.BUY);
        orderBook.addOrder(9, 20, Side.BUY);

        // Execute sell trade
        matchingEngine.executeSellTrade(sellOrder);

        // Check updated quantities
        assertEquals(5, orderBook.getBuyOrders().peek().getQuantity());
    }

    @Test
    public void testExecuteBuyTrade() {
        // Create buy order
        Order buyOrder = new Order(9, 40, Side.BUY);

        // Add sell orders to the order book
        orderBook.addOrder(9, 55, Side.SELL);

        // Execute buy trade
        matchingEngine.executeBuyTrade(buyOrder);

        // Check updated quantities
        assertEquals(15, orderBook.getSellOrders().peek().getQuantity());
    }

    @Test
    public void testScenario() {
        // Create buy orders
        Order buyOrder1 = new Order(9, 40, Side.BUY);
        Order buyOrder2 = new Order(9, 20, Side.BUY);
        Order buyOrder3 = new Order(9, 5, Side.BUY);

        // Create sell order
        Order sellOrder = new Order(9, 55, Side.SELL);

        // Add buy orders to the order book
        orderBook.addOrder(buyOrder1);
        orderBook.addOrder(buyOrder2);
        orderBook.addOrder(buyOrder3);


        // Execute sell trade
        matchingEngine.executeSellTrade(sellOrder);


        // Check updated quantities
        assertEquals(5, orderBook.getBuyOrders().peek().getQuantity());
        assertEquals(0, orderBook.getSellOrders().size());
    }

    @Test
    public void testMatchOrders() {
        // Create buy orders
        Order buyOrder1 = new Order(9, 40, Side.BUY);
        Order buyOrder2 = new Order(9, 20, Side.BUY);
        Order buyOrder3 = new Order(9, 10, Side.BUY);

        // Create sell order
        Order sellOrder = new Order(9, 55, Side.SELL);

        // Add buy orders to the order book
        orderBook.addOrder(buyOrder1);
        orderBook.addOrder(buyOrder2);
        orderBook.addOrder(buyOrder3);
        orderBook.addOrder(sellOrder);

        // Match orders
        matchingEngine.matchOrders();

        // Check the updated quantities
        assertEquals(2, orderBook.getBuyOrders().size());
        assertEquals(5, orderBook.getBuyOrders().peek().getQuantity());
        assertEquals(0, orderBook.getSellOrders().size()); // All sell orders should be fully filled

    }

    @Test
    public void testExecuteTrade() {
        // Create buy and sell orders
        Order buyOrder = new Order(9, 40, Side.BUY);
        Order sellOrder = new Order(9, 55, Side.SELL);

        // Execute trade
        matchingEngine.executeTrade(buyOrder, sellOrder, 40);

        // Check updated quantities
        assertEquals(0, buyOrder.getQuantity());
        assertEquals(15, sellOrder.getQuantity());
    }


}