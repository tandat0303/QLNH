package menu;

public class Dessert extends Food {
    public Dessert(String name, double price) {
        super(name, price);
    }

    @Override
    public String getType() {
        return "Mon trang mieng";
    }
}
