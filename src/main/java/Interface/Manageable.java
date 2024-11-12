package Interface;

import menu.Food;
import restaurant.Employee;
import restaurant.Table;

public interface Manageable{
    void addFood(Food food);
    void removeFood(String name);
    void updateFood(String name);
    void displayMenu();
    void searchFood(String name);
    void calculateTotalRevenue();
    void viewOrderDetail();
    void placeOrder(String foodName, int quantity, String employeeName, int tableNumber);
    void completeOrder(int tableNumber);
    void addTable(Table table);
    void deleteTable(int tableNumber);
    void addEmployee(Employee employee);
    void deleteEmployee(String employeeName);
}
