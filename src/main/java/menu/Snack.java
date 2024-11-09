package menu;

public class Snack extends Food {
    public Snack(String name, double price) {
        super(name, price);
    }

    @Override
    public String getType() {
        return "Mon an nhe";
    }
}
