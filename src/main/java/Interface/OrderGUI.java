package Interface;

public interface OrderGUI {
    void placeOrder(String foodName, int quantity, String employeeName, int tableNumber);
    void completeOrder(int tableNumber);
}
