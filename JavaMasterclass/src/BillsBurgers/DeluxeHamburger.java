package BillsBurgers;

public class DeluxeHamburger extends Hamburger {

   private boolean chips;
   private boolean drinks;

    public DeluxeHamburger(String breadRoll, String meat, int price, Additions additions, boolean chips, boolean drinks) {
        super(breadRoll, meat, price, additions);
        this.chips = chips;
        this.drinks = drinks;
    }
}
