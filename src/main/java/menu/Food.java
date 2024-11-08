package menu;

public abstract class Food {
    protected String name;
    protected double price;
    private int quantitySold;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void display() {
        System.out.println(getType() + ": " + name + ", Gia: " + price);
    }
    
    public abstract String getType();

    public void setName(String n){
        name = n;
    }
    
    public String getName() {
        return name;
    }

    public void setPrice(double p){
        price = p;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setQuantitySold(int qs) {
        quantitySold = qs;
    }

    public int getQuantitySold() {
        return quantitySold;
    }
}
