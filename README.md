## Solution Breakdown:

Order Class: This class represents an order with attributes such as ID, price, quantity, side, and timestamp. Each order is uniquely identified by an ID, and the timestamp is used for ordering purposes.

Side Enum: This enum represents the side of an order, which can be either BUY or SELL.

BuyOrderComparator Class: This comparator is responsible for comparing buy orders. It orders buy orders based on their prices in descending order. If prices are equal, orders are ordered based on their timestamps.

SellOrderComparator Class: This comparator is responsible for comparing sell orders. It orders sell orders based on their prices in ascending order. If prices are equal, orders are ordered based on their timestamps.

OrderBook Class: This class represents an order book, which manages buy and sell orders using priority queues. It provides methods to add, delete, and modify orders.

Reason for Chosen Data Structures:

HashMap for Order Map: HashMap is chosen for the order map because it provides fast insertion, deletion, and lookup operations with an average time complexity of O(1). This allows for efficient management of orders by their unique IDs.

PriorityQueue for Buy and Sell Orders: PriorityQueue is chosen for managing buy and sell orders because it provides automatic sorting based on the specified comparator. This allows orders to be maintained in the order book in their prioritized order based on price and timestamp.


#####################################################################################################

## Explanation:

a. Efficiency Mechanisms:

PriorityQueues are used to store orders, ensuring log(n) time complexity for insertion, deletion, and retrieval operations.
HashMap is used to store orders with their IDs for efficient retrieval and deletion.
When modifying an order, we remove and re-add the order to ensure it loses priority, which might have a slight overhead but maintains the order book's integrity efficiently.
b. Solution approach:

The OrderBook class encapsulates the order book functionality with methods for adding, deleting, and modifying orders.
Order class represents an order with necessary fields.
Side enum represents whether the order is a buy or sell order.
Test cases are provided to validate the functionality of the OrderBook.

c. Data Structures:

PriorityQueues are used to maintain the buy and sell orders, ensuring orders are kept in priority order based on price.
HashMap is used to store orders with their IDs for efficient retrieval and deletion.

#############################################################################################################

## Running the Application
1. Run the Main class: `java Main`

## Usage

The Application will Preload an OrderBook from a given CSV file,  then printout the table for each of the two sides. 

It will then present a menu with the following options to the user:

1. Add Order: Add a new order to the order book.
2. Delete Order: Delete an existing order from the order book.
3. Modify Order: Modify an existing order in the order book.
4. Get Orders in Price Range: View orders within a specified price range.
5. View Current Orders: Display all current orders in the order book.
6. Exit: Exit the application.

