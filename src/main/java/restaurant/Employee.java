package restaurant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Employee {
    private String name;
    private double salary;

    public Employee(String n, double s) {
        name = n;
        salary = s;
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/employees.txt", true))) {
            writer.write("Ten: " + name + ", Luong: " + salary);
            writer.newLine();
            System.out.println("Da luu thong tin nhan vien vao file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double s) {
        salary = s;
    }

    public void displayInfo() {
        System.out.println("Ten: " + name + ", Luong: " + salary);
    }
}
