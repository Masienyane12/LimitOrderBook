package org.rmb.assessment;

import org.rmb.assessment.comparator.BuyOrderComparator;
import org.rmb.assessment.comparator.SellOrderComparator;
import org.rmb.assessment.enums.Side;
import org.rmb.assessment.model.MatchingEngine;
import org.rmb.assessment.model.Order;
import org.rmb.assessment.model.OrderBook;

import java.io.*;
import java.util.*;

public class Main {
    private static OrderBook orderBook;
    private static MatchingEngine matchingEngine;
    public static void main(String[] args) {
        orderBook = new OrderBook();
        matchingEngine = new MatchingEngine(orderBook);

        // Load initial orders from CSV file
        loadOrdersFromCSV("orders.csv");

        // Display initial order book
        System.out.println("Initial Order Book:");
        printOrderBook();
        displayMenu();
    }

    private static void loadOrdersFromCSV(String filename) {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            // Skip the header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    double price = Double.parseDouble(parts[0]);
                    int quantity = Integer.parseInt(parts[1]);
                    Side side = Side.valueOf(parts[2]);
                    orderBook.addOrder(price, quantity, side);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading orders from CSV: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            matchingEngine.matchOrders();
            System.out.println("\nMenu:");
            System.out.println("1. Add Order");
            System.out.println("2. Delete Order");
            System.out.println("3. Modify Order");
            System.out.println("4. Get Orders in Price Range");
            System.out.println("5. View Current Orders");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addOrder(scanner);
                    break;
                case 2:
                    deleteOrder(scanner);
                    break;
                case 3:
                    modifyOrder(scanner);
                    break;
                case 4:
                    getOrdersInPriceRange(scanner);
                    break;
                case 5:
                    System.out.println("Current Orders:");
                    printOrderBook();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addOrder(Scanner scanner) {
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter side (BUY/SELL): ");
        String sideStr = scanner.next();
        Side side = Side.valueOf(sideStr);

        orderBook.addOrder(price, quantity, side);
        System.out.println("Order added successfully.");
    }

    private static void deleteOrder(Scanner scanner) {
        System.out.print("Enter order ID to delete: ");
        String orderId = scanner.next();

        try {
            orderBook.deleteOrder(orderId);
            System.out.println("Order deleted successfully.");
        }catch (IllegalArgumentException e){
            System.out.println("You entered None existing order ID, Please check and Try again");
        }
    }

    private static void modifyOrder(Scanner scanner) {
        System.out.print("Enter order ID to modify: ");
        String orderId = scanner.next();
        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        try {
            orderBook.modifyOrder(orderId, newQuantity);
            System.out.println("Order modified successfully.");
        }catch (IllegalArgumentException e){
            System.out.println("You entered None existing order ID, Please check and Try again");
        }

    }

    private static void getOrdersInPriceRange(Scanner scanner) {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        System.out.print("Enter side (BUY/SELL): ");
        String sideStr = scanner.next();

        System.out.println("Orders in price range:");
        orderBook.getOrdersInPriceRange(minPrice, maxPrice, sideStr)
                .forEach(System.out::println);
    }

    private static void printOrderBook() {
        System.out.println("BUY Orders:");
        printOrdersByPriority(orderBook.getBuyOrders(), new BuyOrderComparator());

        System.out.println("\nSELL Orders:");
        printOrdersByPriority(orderBook.getSellOrders(), new SellOrderComparator());
    }

    private static void printOrdersByPriority(PriorityQueue<Order> orders, Comparator<Order> comparator) {
        List<Order> orderList = new ArrayList<>(orders);
        orderList.sort(comparator); // Sort using the provided comparator

        System.out.printf("%-10s %-10s %-10s %s%n", "ID", "Price", "Quantity", "Side");
        for (Order order : orderList) {
            System.out.printf("%-10s %-10.2f %-10d %s%n",
                    order.getId(), order.getPrice(), order.getQuantity(), order.getSide());
        }

    }

}