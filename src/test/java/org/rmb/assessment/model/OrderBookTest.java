package org.rmb.assessment.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rmb.assessment.enums.Side;

import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {
    private OrderBook orderBook;

    @BeforeEach
    public void setUp() {
        orderBook = new OrderBook();
    }


    @AfterEach
    void tearDown() {

    }

    @Test
    void addOrder() {
        orderBook.addOrder(100.0,10,Side.BUY);
        orderBook.addOrder(110.0,20,Side.SELL);
        assertEquals(1, orderBook.getBuyOrders().size());
        assertEquals(1, orderBook.getSellOrders().size());
    }

    @Test
    void deleteOrder() {
        orderBook.addOrder(100.0,10,Side.BUY);
        System.out.println("dlt: "+ orderBook.getBuyOrders());
        orderBook.deleteOrder(orderBook.getBuyOrders().peek().getId());
        assertTrue(orderBook.getBuyOrders().isEmpty());
    }

    @Test
    public void testDeleteOrderThrowsExceptionIfOrderIdNotFound() {
        // Attempt to delete an order with a non-existing ID
        assertThrows(IllegalArgumentException.class, () -> {
            orderBook.deleteOrder("non-existing-id");
        });
    }

    @Test
    void modifyOrder() {
        orderBook.addOrder(100.0,10,Side.BUY);
        System.out.println("mod: "+ orderBook.getBuyOrders());
        orderBook.modifyOrder(orderBook.getBuyOrders().peek().getId(),15);
        assertEquals(15,orderBook.getBuyOrders().peek().getQuantity());
    }

    @Test
    void modifyOrderLosePriority() {

        orderBook.addOrder(100.0,10,Side.BUY);
        orderBook.addOrder(100.0,15,Side.BUY);
        orderBook.addOrder(99.0,10,Side.BUY);
        orderBook.addOrder(95.0,10,Side.BUY);

        // Modify order with highest priority quantity
        orderBook.modifyOrder(orderBook.getBuyOrders().peek().getId(), 30);

        // We expect order with detials : price 100.0, quantity 15, Side BUY to now be on top

        assertEquals(15,orderBook.getBuyOrders().peek().getQuantity());
        assertEquals(100.0,orderBook.getBuyOrders().peek().getPrice());
        assertEquals(Side.BUY,orderBook.getBuyOrders().peek().getSide());

    }

    @Test
    public void testModifyOrderThrowsExceptionIfOrderIdNotFound() {
        // Attempt to modify an order with a non-existing ID
        assertThrows(IllegalArgumentException.class, () -> {
            orderBook.modifyOrder("non-existing-id", 10);
        });
    }

    @Test
    void getOrdersInPriceRange() {
        orderBook.addOrder(100.0,10,Side.SELL);
        orderBook.addOrder(120.0,15,Side.SELL);
        orderBook.addOrder(135.0,10,Side.SELL);
        orderBook.addOrder(135.0,10,Side.SELL);
        orderBook.addOrder(105.0,10,Side.SELL);


        // Retrieve orders within the price range [100.0, 120.0]
        List<Order> ordersInRange = orderBook.getOrdersInPriceRange(100.0, 120.0,Side.SELL.toString());

        // Assert that the correct number of orders is returned
        assertEquals(3, ordersInRange.size());

        // Assert that each order is within the specified price range
        for (Order order : ordersInRange) {
            assertTrue(order.getPrice() >= 100.0 && order.getPrice() <= 120.0);
        }



    }

}