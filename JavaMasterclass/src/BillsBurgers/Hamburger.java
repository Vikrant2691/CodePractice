package BillsBurgers;

public class Hamburger {

    private String breadRoll;
    private String meat;
    private int price;
    private Additions additions;

    public Hamburger(String breadRoll, String meat, int price, Additions additions) {
        this.breadRoll = breadRoll;
        this.meat = meat;
        this.price = price;
        this.additions = additions;
    }

    public int getBasePrice(){
        return price;
    }

    public void getPrice() {

        System.out.println("Base Price  ->" + getBasePrice());

        if (additions.isLettuce()) {
            price += 2;
            System.out.println("Lettuce Added   ->" + 2);
        }
        if (additions.isTomato()) {
            price += 2;
            System.out.println("Tomato Added    ->" + 2);
        }
        if (additions.isCarrot()) {
            price += 2;
            System.out.println("Carrot Added    ->" + 2);
        }
        if (additions.isCheese()) {
            price += 2;
            System.out.println("Cheese Added    ->" + 2);
        }

        System.out.println("Final Price     ->" + price);
    }


}
