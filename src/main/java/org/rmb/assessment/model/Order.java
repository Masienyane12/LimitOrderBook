package org.rmb.assessment.model;

import org.rmb.assessment.enums.Side;

public class Order {
    private static long nextId = 1L;
    private String id;
    private double price;
    private int quantity;
    private Side side;
    private long timestamp;

    public Order(double price, int quantity, Side side) {
        this.id = String.valueOf(nextId++);
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.timestamp = System.currentTimeMillis(); // Initialize with current timestamp
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Side getSide() {
        return side;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", side=" + side +
                ", timestamp=" + timestamp +
                '}';
    }
}
