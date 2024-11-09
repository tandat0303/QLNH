package Interface;

import menu.Food;

public interface FoodGUI {
    void addFood(Food food);
    void removeFood(String name);
    void updateFood(String name);
    void displayMenu();
    void searchFood(String name);
    void calculateTotalRevenue();
    void viewOrderDetail();
}
