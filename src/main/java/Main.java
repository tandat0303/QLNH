import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import menu.*;
import restaurant.*;
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
        restaurantManagement.menu.loadTableFromFile();
        restaurantManagement.menu.loadEmployeeFromFile();

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
            
            System.out.println("1. Quan ly ban");
            System.out.println("2. Quan ly nhan vien");
            System.out.println("3. Them mon an");
            System.out.println("4. Xoa mon an");
            System.out.println("5. Sua mon an");
            System.out.println("6. Xem menu");
            System.out.println("7. Tim mon an");
            System.out.println("8. Tinh tong doanh thu");
            System.out.println("9. Xem lich su don hang");
            System.out.println("10. Luu menu");
            System.out.println("0. Thoat");
            
            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manageTables(sc);
                    break;
                case 2:
                    manageEmployees(sc);
                    break;
                case 3:
                    addFoodMenu(sc);
                    break;
                case 4:
                    removeFoodMenu(sc);
                    break;
                case 5:
                    updateFoodMenu(sc);
                    break;
                case 6:
                    displayMenu();
                    break;
                case 7:
                    searchFoodMenu(sc);
                    break;
                case 8:
                    calculateTotalRevenue();
                    break;
                case 9:
                    viewOrderDetail();
                    break;
                case 10:
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

    public void manageTables(Scanner sc) {
        int choice;
        do {
            System.out.println("\n1. Xem danh sach ban");
            System.out.println("2. Them ban");
            System.out.println("3. Xoa ban");
            System.out.println("4. Luu danh sach ban");
            System.out.println("0. Thoat");

            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    displayTables();
                    break;
                case 2:
                    addTableList(sc);
                    break;
                case 3:
                    deleteTableList(sc);
                    break;
                case 4:
                    saveTable(sc);
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

    public void manageEmployees(Scanner sc) {
        int choice;
        do {
            System.out.println("\n1. Xem danh sach nhan vien");
            System.out.println("2. Them nhan vien");
            System.out.println("3. Xoa nhan vien");
            System.out.println("4. Luu danh sach nhan vien");
            System.out.println("0. Thoat");

            System.out.print("Nhap lua chon: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    displayEmployees();
                    break;
                case 2:
                    addEmployeeList(sc);
                    break;
                case 3:
                    deleteEmployeeList(sc);
                    break;
                case 4:
                    saveEmployee();
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

    public void addFoodMenu(Scanner sc) {
        System.out.println("Chon loai mon an:");
        
        System.out.println("1. Mon chinh");
        System.out.println("2. Mon trang mieng");
        System.out.println("3. Do uong");
        System.out.println("4. Mon an nhe");
        
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
                System.out.println("Them mon chinh thanh cong!");
                break;
            case 2:
                addFood(new Dessert(name, price));
                System.out.println("Them mon trang mieng thanh cong!");
                break;
            case 3:
                addFood(new Drink(name, price));
                System.out.println("Them do uong thanh cong!");
                break;
            case 4:
                addFood(new Snack(name, price));
                System.out.println("Them mon an nhe thanh cong!");
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
        System.out.println("Da xoa mon " + nameToRemove);
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

    public void saveMenu() {
        menu.saveToFile();
        System.out.println("Da luu menu!");
    }

    public void displayTables(){
        menu.displayTables();
    }

    public void addTableList(Scanner sc){
        System.out.print("Nhap so ban: ");
        int tableNumber = sc.nextInt();
        sc.nextLine();

        addTable(new Table(tableNumber, "Trong"));
        System.out.println("Them ban thanh cong!");
    }

    public void deleteTableList(Scanner sc){
        System.out.print("Nhap so ban can xoa: ");
        int tableNumber = sc.nextInt();
        sc.nextLine();
        deleteTable(tableNumber);
        System.out.println("Xoa ban thanh cong!");
    }

    public void saveTable(Scanner sc){
        menu.saveTableListToFile();
        System.out.println("Danh sach ban da duoc luu.");
    }

    public void displayEmployees(){
        menu.displayEmployees();
    }

    public void addEmployeeList(Scanner sc){
        System.out.print("Nhap ten nhan vien: ");
        String employeeName = sc.nextLine();
        System.out.print("Nhap muc luong: ");
        double salary = sc.nextDouble();

        addEmployee(new Employee(employeeName, salary));
        System.out.println("Them nhan vien thanh cong!");
    }

    public void deleteEmployeeList(Scanner sc){
        System.out.print("Nhap ten nhan vien can xoa: ");
        String employeeName = sc.nextLine();

        deleteEmployee(employeeName);
        System.out.println("Xoa nhan vien thanh cong!");
    }

    public void saveEmployee(){
        menu.saveEmployeeListToFile();;
        System.out.println("Danh sach nhan vien da duoc luu.");
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
    public void viewOrderDetail(){
        menu.viewOrderDetail();
    }

    @Override
    public void addTable(Table table){
        menu.addTable(table);
    }

    @Override
    public void deleteTable(int tableNumber){
        menu.deleteTable(tableNumber);
    }

    @Override
    public void addEmployee(Employee employee){
        menu.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(String employeeName){
        menu.deleteEmployee(employeeName);
    }
}
