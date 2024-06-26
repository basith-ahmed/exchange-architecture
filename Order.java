public class Order {
    private String orderId;
    private String symbol;
    private double price;
    private int quantity;
    private OrderType type;
    private OrderStatus status;

    //Add getters and setters
}

enum OrderType {
    MARKET, LIMIT
}

enum OrderStatus {
    NEW, EXECUTED, CANCELLED
}
