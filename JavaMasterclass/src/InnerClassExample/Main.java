package InnerClassExample;

import java.nio.BufferUnderflowException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Button btnPoint = new Button("Print");

    public static void main(String[] args) {

//        class OnClickListener implements Button.OnClickListener{
//            public OnClickListener(){
//                System.out.println("I've been attached!!");
//            }
//
//            @Override
//            public void onClick(String title) {
//                System.out.println(title+" was clicked");
//            }
//        }



        btnPoint.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(String title) {
                System.out.println(title+" was Clicked");
            }
        });
        listen();

//        GearBox mcLaren = new GearBox(6);
//
//        mcLaren.addGear(1,5.3);
//        mcLaren.addGear(2,10.6);
//        mcLaren.addGear(3,15.9);
//        mcLaren.operateClutch(true);
//        mcLaren.changeGear(1);
//        mcLaren.operateClutch(false);
//        System.out.println(mcLaren.wheelSpeed(1000));
//        mcLaren.operateClutch(true);
//        mcLaren.changeGear(2);
//        mcLaren.operateClutch(false);
//        System.out.println(mcLaren.wheelSpeed(3000));
//        mcLaren.operateClutch(true);
//        mcLaren.changeGear(3);
//        mcLaren.operateClutch(false);
//        System.out.println(mcLaren.wheelSpeed(6000));

    }

    public static void listen() {
        boolean quit = false;
        while (!quit) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    quit = true;
                    break;
                case 1:
                    btnPoint.onClick();
            }
        }
    }
}
