package menu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import restaurant.Employee;
import restaurant.Table;

public class Menu {
    private List<Food> foodList = new ArrayList<>();
    private List<Table> tableList = new ArrayList<>();
    private List<Employee> employeeList = new ArrayList<>();

    public void addFood(Food food) {
        foodList.add(food);
    }

    public void removeFood(String name) {
        foodList.removeIf(food -> food.getName().equalsIgnoreCase(name));
    }

    public void updateFood(String name) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                System.out.println("Da tim thay mon an: " + food.getName() + " voi gia: " + food.getPrice() + " (VND)");
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
        System.out.println("Thuc don: ");
        
        for (Food food : foodList) {
            food.display();
        }
    }

    public void searchFood(String name) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(name)) {
                food.display();
                return;
            }
        }
        System.out.println("Khong tim thay mon an: " + name);
    }
    
    public void calculateTotalRevenue() {
        int mainDishCount = 0;
        int dessertCount = 0;
        int drinkCount = 0;
        int snackCount = 0;
        
        double total = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String foodName = parts[1];
                int quantitySold = Integer.parseInt(parts[2]);
                
                if (getFoodByName(foodName) instanceof MainDish) {
                    mainDishCount += quantitySold;
                } else if (getFoodByName(foodName) instanceof Dessert) {
                    dessertCount += quantitySold;
                } else if (getFoodByName(foodName) instanceof Drink) {
                    drinkCount += quantitySold;
                } else if (getFoodByName(foodName) instanceof Snack) {
                    snackCount += quantitySold;
                }
                
                Food food = getFoodByName(foodName);
                if (food != null) {
                    total += food.getPrice() * quantitySold;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("Doanh thu nha hang: \n");
        
        System.out.println("So luong mon chinh da ban: " + mainDishCount);
        System.out.println("So luong mon trang mieng da ban: " + dessertCount);
        System.out.println("So luong do uong da ban: " + drinkCount);
        System.out.println("So luong mon an nhe da ban: " + snackCount);

        System.out.println("Tong doanh thu: " + total + " (VND)");
    }
    
    public void placeOrder(String foodName, int quantity, String employeeName, int tableNumber) {
        boolean employeeExists = false;
        for (Employee employee : employeeList) {
            if (employee.getName().equalsIgnoreCase(employeeName)) {
                employeeExists = true;
                break;
            }
        }
        if (!employeeExists) {
            System.out.println("Khong tim thay nhan vien : " + employeeName);
            return;
        }
    
        Table selectedTable = null;
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                selectedTable = table;
                break;
            }
        }
        if (selectedTable == null) {
            System.out.println("Khong tim thay ban so : " + tableNumber);
            return;
        }
    
        Food food = getFoodByName(foodName);
        if (food == null) {
            System.out.println("Khong tim thay mon an: " + foodName);
            return;
        }
    
        food.setQuantitySold(food.getQuantitySold() + quantity);
        System.out.println("Dat mon thanh cong: " + food.getName() + " x " + quantity + " tai ban so " + tableNumber);
    
        selectedTable.setStatus("Dang phuc vu");
        saveTableListToFile();
    
        saveOrderToFile(employeeName, foodName, quantity, tableNumber);
    }
    
    private void saveOrderToFile(String employeeName, String foodName, int quantity, int tableNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/orders.txt", true))) {
            writer.write(employeeName + "," + foodName + "," + quantity + "," + tableNumber);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void completeOrder(int tableNumber) {
        Table selectedTable = null;
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                selectedTable = table;
                break;
            }
        }

        if (selectedTable == null) {
            System.out.println("Khong tim thay ban so: " + tableNumber);
            return;
        }

        if (selectedTable.getStatus().equals("Trong")) {
            System.out.println("Ban so " + selectedTable.getTableNumber() + " hien dang trong");
            return;
        }
        
        List<String> remainingOrders = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders.txt"));
             BufferedWriter historyWriter = new BufferedWriter(new FileWriter("data/orders_history.txt", true))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int orderTableNumber = Integer.parseInt(parts[3]);
    
                if (orderTableNumber == tableNumber) {
                    historyWriter.write(line);
                    historyWriter.newLine();
                } else {
                    remainingOrders.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/orders.txt"))) {
            for (String order : remainingOrders) {
                writer.write(order);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        for (Table table : tableList) {
            if (table.getTableNumber() == tableNumber) {
                table.setStatus("Trong");
                System.out.println("Thanh toan thanh cong ban so " + tableNumber);
                saveTableListToFile();
                return;
            }
        }
        System.out.println("Khong tim thay ban so: " + tableNumber);
    }
    
    public void viewOrderDetail() {
        System.out.println("Lich su don hang:\n");
        try (BufferedReader reader = new BufferedReader(new FileReader("data/orders_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String employeeName = parts[0];
                String foodName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                int tableNumber = Integer.parseInt(parts[3]);
                
                System.out.println("Nhan vien order: " + employeeName + ", " + foodName + " x " + quantity + ", Ban so " + tableNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void displayTables() {
        for (Table table : tableList) {
            table.displayInfo();
        }
    }
    
    public void addTable(Table table) {
        tableList.add(table);
    }

    public void deleteTable(int tableNumber) {
        tableList.removeIf(table -> table.getTableNumber() == tableNumber);
    }

    public void saveTableListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/tables.txt"))) {
            for (Table table : tableList) {
                writer.write(table.getTableNumber() + "," + table.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayEmployees() {
        for (Employee employee : employeeList) {
            employee.displayInfo();
        }
    }
    
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void deleteEmployee(String employeeName) {
        employeeList.removeIf(employee -> employee.getName().equals(employeeName));
    }

    public void saveEmployeeListToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/employees.txt"))) {
            for (Employee employee : employeeList) {
                writer.write(employee.getName() + "," + employee.getSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    switch (parts[0].toLowerCase()) {
                        case "mon chinh":
                            addFood(new MainDish(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "mon trang mieng":
                            addFood(new Dessert(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "do uong":
                            addFood(new Drink(parts[1], Double.parseDouble(parts[2])));
                            break;
                        case "mon an nhe":
                            addFood(new Snack(parts[1], Double.parseDouble(parts[2])));
                            break;
                        default:
                            System.out.println("Loai mon khong hop le: " + parts[0]);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTableFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/tables.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addTable(new Table(Integer.parseInt(parts[0]), parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeeFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                addEmployee(new Employee(parts[0], Double.parseDouble(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Food getFoodByName(String foodName) {
        for (Food food : foodList) {
            if (food.getName().equalsIgnoreCase(foodName)) {
                return food;
            }
        }
        return null;
    }
}

