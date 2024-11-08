package menu;

public class Drink extends Food{
    public Drink(String name, double price){
        super(name, price);
    }
    
    @Override
    public String getType(){
        return "Do uong";
    }
}
