import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private Map<String, Order> orderBook = new HashMap<>();

    public void placeOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        orderBook.put(order.getOrderId(), order);
        //send order to market
    }

    public void executeOrder(String orderId) {
        Order order = orderBook.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.EXECUTED);
            //execute the order
        }
    }

    public void cancelOrder(String orderId) {
        Order order = orderBook.get(orderId);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED);
            //cancel the order
        }
    }
}
