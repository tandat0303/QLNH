package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Food> foodList = new ArrayList<>();
    private static int count = 0;

    public void addFood(Food food) {
        foodList.add(food);
        count++;
    }

    public void removeFood(String name) {
        foodList.removeIf(food -> food.getName().equals(name));
    }

    public void updateFood(String name) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                System.out.println("Da tim thay mon an: " + food.getName() + " voi gia: " + food.getPrice());
                System.out.print("Nhap ten moi (Nhan Enter de giu nguyen): ");
                String newName = sc.nextLine();
                if (!newName.isEmpty()) {
                    food.setName(newName);
                }

                System.out.print("Nhap gia moi (Nhan -1 de giu nguyen): ");
                double newPrice = sc.nextDouble();
                if (newPrice >= 0) {
                    food.setPrice(newPrice);
                }

                found = true;
                System.out.println("Mon an da duoc cap nhat!");
                break;
            }
        }

        if (!found) {
            System.out.println("Khong tim thay mon an co ten: " + name);
        }

        saveToFile();
    }
    
    public void displayMenu() {
        for (Food food : foodList) {
            food.display();
        }
    }

    public void searchFood(String name) {
        for (Food food : foodList) {
            if (food.getName().equals(name)) {
                food.display();
                return;
            }
        }
        System.out.println("Khong tim thay mon an: " + name);
    }
    
    public double calculateTotalRevenue() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String foodName = parts[0];
                int quantitySold = Integer.parseInt(parts[1]);
                
                Food food = getFoodByName(foodName);
                if (food != null) {
                    total += food.getPrice() * quantitySold;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tong doanh thu: " + total);
        return total;
    }
    
    public void countFoodTypes() {
        int mainDishCount = 0;
        int dessertCount = 0;
        int drinkCount = 0;

        for (Food food : foodList) {
            if (food instanceof MainDish) {
                mainDishCount++;
            } else if (food instanceof Dessert) {
                dessertCount++;
            } else if (food instanceof Drink) {
                drinkCount++;
            }
        }

        System.out.println("So luong mon chinh: " + mainDishCount);
        System.out.println("So luong mon trang mieng: " + dessertCount);
        System.out.println("So luong do uong: " + drinkCount);
    }
    
    public void placeOrder(String foodName, int quantity) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(foodName)) {
                food.setQuantitySold(food.getQuantitySold() + quantity);
                System.out.println("Dat hang thanh cong: " + quantity + " x " + food.getName());

                saveOrderToFile(foodName, quantity);
                return;
            }
        }
        System.out.println("Khong tim thay mon an co ten: " + foodName);
    }
    
    public static int getCount() {
        return count;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/menu.txt"))) {
            for (Food food : foodList) {
                String foodLine = food.getType() + "," + food.getName() + "," + food.getPrice();
                writer.write(foodLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    if (parts[0].equalsIgnoreCase("Mon chinh")){
                        addFood(new MainDish(parts[1], Double.parseDouble(parts[2])));
                    } else {
                        addFood(new Dessert(parts[1], Double.parseDouble(parts[2])));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Food getFoodByName(String foodName) {
        for (Food food : foodList) {
            if (food.getName().equals(foodName)) {
                return food;
            }
        }
        return null;
    }

    public void saveOrderToFile(String foodName, int quantity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/orders.txt", true))) {
            writer.write(foodName + "," + quantity);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void statisticsFoodQuantity() {
        int mainDishCount = 0;
        int dessertCount = 0;
        int drinkCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String foodName = parts[0];
                int quantitySold = Integer.parseInt(parts[1]);

                if (getFoodByName(foodName) instanceof MainDish) {
                    mainDishCount += quantitySold;
                } else if (getFoodByName(foodName) instanceof Dessert) {
                    dessertCount += quantitySold;
                } else if (getFoodByName(foodName) instanceof Drink) {
                    drinkCount += quantitySold;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("So luong mon chinh da ban: " + mainDishCount);
        System.out.println("So luong mon trang mieng da ban: " + dessertCount);
        System.out.println("So luong do uong da ban: " + dessertCount);
    }
}

