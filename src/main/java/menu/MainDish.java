package menu;

public class MainDish extends Food {
    public MainDish(String name, double price) {
        super(name, price);
    }

    @Override
    public String getType() {
        return "Mon chinh";
    }
}
