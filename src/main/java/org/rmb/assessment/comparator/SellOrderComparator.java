package org.rmb.assessment.comparator;

import org.rmb.assessment.model.Order;

import java.util.Comparator;

public class SellOrderComparator implements Comparator<Order> {
    public int compare(Order o1, Order o2) {
        if (o1.getPrice() < o2.getPrice()) {
            return -1; // Compare in ascending order for sell orders
        } else if (o1.getPrice() > o2.getPrice()) {
            return 1; // Compare in ascending order for sell orders
        } else {
            // If prices are equal, orders are ordered based on their timestamps
            return Long.compare(o1.getTimestamp(), o2.getTimestamp());
        }
    }
}