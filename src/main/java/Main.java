import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import menu.Menu;
import menu.MainDish;
import menu.Food;
import menu.Dessert;
import menu.Drink;
import Interface.Manageable;

public class Main implements Manageable {
    private Menu menu;
    private Map<String, Integer> sales;

    public Main() {
        menu = new Menu();
        sales = new HashMap<>();
    }

    public static void main(String[] args) {
        Main restaurantManagement = new Main();
        restaurantManagement.menu.loadFromFile();

        Scanner sc = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Chon vai tro:");
            
            System.out.println("1. Quan Ly");
            System.out.println("2. Khach Hang");
            System.out.println("0. Thoat");
            
            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1:
                    restaurantManagement.handleManagerRole(sc);
                    break;
                case 2:
                    restaurantManagement.handleCustomerRole(sc);
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
                    break;
            }
        } while(choice != 0);

        sc.close();
    }

    public void handleManagerRole(Scanner sc) {
        int choice;
        do {
            System.out.println("\n---------- Quan Ly ----------");
            System.out.println("1. Them mon an");
            System.out.println("2. Xoa mon an");
            System.out.println("3. Sua mon an");
            System.out.println("4. Xem menu");
            System.out.println("5. Tim mon an");
            System.out.println("6. Tinh tong doanh thu");
            System.out.println("7. Thong ke mon an da ban");
            System.out.println("8. Luu menu");
            System.out.println("0. Thoat");
            
            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addFoodMenu(sc);
                    break;
                case 2:
                    removeFoodMenu(sc);
                    break;
                case 3:
                    updateFoodMenu(sc);
                    break;
                case 4:
                    displayMenu();
                    break;
                case 5:
                    searchFoodMenu(sc);
                    break;
                case 6:
                    calculateTotalRevenue();
                    break;
                case 7:
                    statisticsFoodQuantity();
                    break;
                case 8:
                    saveMenu();
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
                    break;
            }
        } while (choice != 0);
    }

    public void handleCustomerRole(Scanner sc) {
        int choice;
        do {
            System.out.println("\n---------- Khach Hang ----------");
            System.out.println("1. Xem menu");
            System.out.println("2. Tim mon an");
            System.out.println("3. Dat mon");
            System.out.println("0. Thoat");
            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    searchFoodMenu(sc);
                    break;
                case 3:
                    placeOrderMenu(sc);
                    break;
                case 0:
                    System.out.println("Dang thoat ...");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (choice != 0);
    }

    public void addFoodMenu(Scanner sc) {
        System.out.println("Chọn loai mon an:");
        
        System.out.println("1. Mon chinh");
        System.out.println("2. Mon trang mieng");
        System.out.println("3. Do uong");
        
        System.out.print("Nhap lua chon: ");
        int typeChoice = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhap ten mon an: ");
        String name = sc.nextLine();
        System.out.print("Nhap gia mon an: ");
        double price = sc.nextDouble();
        sc.nextLine();

        switch (typeChoice) {
            case 1:
                addFood(new MainDish(name, price));
                System.out.println("Them mon an thanh cong!");
                break;
            case 2:
                addFood(new Dessert(name, price));
                System.out.println("Them mon an thanh cong!");
                break;
            case 3:
                addFood(new Drink(name, price));
                System.out.println("Them mon an thanh cong!");
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                break;
        }
    }

    public void removeFoodMenu(Scanner sc) {
        System.out.print("Nhap ten mon can xoa: ");
        String nameToRemove = sc.nextLine();
        removeFood(nameToRemove);
    }

    public void updateFoodMenu(Scanner sc) {
        System.out.print("Nhap ten mon can sua: ");
        String nameToUpdate = sc.nextLine();
        updateFood(nameToUpdate);
    }

    public void searchFoodMenu(Scanner sc) {
        System.out.print("Nhap ten mon can tim: ");
        String nameToSearch = sc.nextLine();
        searchFood(nameToSearch);
    }

    public void placeOrderMenu(Scanner sc) {
        System.out.print("Nhap ten mon an: ");
        String foodName = sc.nextLine();
        System.out.print("Nhap so luong: ");
        int quantity = sc.nextInt();
        sc.nextLine();
        menu.placeOrder(foodName, quantity);
    }

    private void saveMenu() {
        menu.saveToFile();
        System.out.println("Da luu menu!");
    }

    @Override
    public void addFood(Food food) {
        menu.addFood(food);
    }

    @Override
    public void removeFood(String name) {
        menu.removeFood(name);
    }

    @Override
    public void displayMenu() {
        menu.displayMenu();
    }

    @Override
    public void searchFood(String name) {
        menu.searchFood(name);
    }

    @Override
    public void updateFood(String name) {
        menu.updateFood(name);
    }
    
    @Override
    public void calculateTotalRevenue(){
        menu.calculateTotalRevenue();
    }
    
    @Override
    public void statisticsFoodQuantity(){
        menu.statisticsFoodQuantity();
    }
}
