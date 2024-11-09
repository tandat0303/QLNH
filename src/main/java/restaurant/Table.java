package restaurant;

public class Table {
    private int tableNumber;
    private String status;

    public Table(int tn, String s) {
        tableNumber = tn;
        status = s;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tn) {
        tableNumber = tn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        status = s;
    }

    public void displayInfo() {
        System.out.println("Ban so: " + tableNumber + ", Trang thai: " + status);
    }
}
