package BillsBurgers;

public class HealthyHamburger extends Hamburger{

    private boolean lowFatMayo;
    private boolean dietCoke;
private int price;

    public HealthyHamburger(String breadRoll, String meat, int price, Additions additions, boolean lowFatMayo, boolean dietCoke) {
        super(breadRoll, meat, price, additions);
        this.lowFatMayo = lowFatMayo;
        this.dietCoke = dietCoke;
        this.price=price;
    }


    @Override
    public void getPrice() {
        super.getPrice();
        if (lowFatMayo) {
            price+= 2;
            System.out.println("Lettuce Added   ->" + 2);
        }
        if (dietCoke) {
            price += 2;
            System.out.println("Lettuce Added   ->" + 2);
        }
    }
}
