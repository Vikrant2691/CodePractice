package SimpleMobilePhone;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.SoapBindingParameterStyle;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MobilePhone mobilePhone = new MobilePhone("9145273801");

    public static void main(String[] args) {

        boolean quit = false;
        startPhone();
        printActions();

        while (!quit) {

            System.out.println("\nEnter action: (6 to show available action)");

            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {

                case 0:
                    System.out.println("\n Shutting Down...");
                    quit = true;
                    break;
                case 1:
                    mobilePhone.printContactList();
                    break;
                case 2:
                    addNewContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    removeContact();
                    break;
                case 5:
                    queryContact();
                    break;
                case 6:
                    printActions();
                    break;
                default:
                    System.out.println("Incorrect input. Please try again");
                    break;

            }


        }

    }

    private static void updateContact() {

        System.out.println("Enter contact name to be updated:");
        String name = scanner.nextLine();

        Contact contact = mobilePhone.queryContact(name);

        if(contact!=null) {


            System.out.println("Enter contact number to be updated:");
            String number = scanner.nextLine();


            if (mobilePhone.updateContact(contact, Contact.createContact(name, number))) {
                System.out.println("Successfully updated the contact");
            } else
                System.out.println("Error updating the record");
        }
        else{
            System.out.println("Contact name not found");
        }

    }

    public static void removeContact() {
        System.out.println("Enter the contact to be removed");
        String name = scanner.nextLine();
        Contact contact = mobilePhone.queryContact(name);

        if(contact!=null) {
            if (mobilePhone.removeContact(contact)) {
                System.out.println("Contact removed.");
            } else {
                System.out.println("Not able to remove the contact");
            }
        }
        else{
            System.out.println("Contact name not found");
        }
    }


    public static void addNewContact() {

        System.out.println("Enter new contact name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new contact number: ");
        String number = scanner.nextLine();

        Contact contact = Contact.createContact(name, number);

        if (mobilePhone.addNewContact(contact))
            System.out.println("New Contact added " + name + ", number " + number);
        else
            System.out.println("Cannot add, " + name + ", already present.");

    }

    public static void queryContact() {
        System.out.println("Enter contact name to queried: ");
        String name = scanner.nextLine();

        Contact contact = mobilePhone.queryContact(name);
        if (contact != null)
            System.out.println("name: " + contact.getName() + "phone number: " + contact.getPhoneNumber());
        else
            System.out.println("Contact not found...");
    }


    public static void startPhone() {
        System.out.println("Starting Phone....");
    }

    private static void printActions() {
        System.out.println("\nAvailable actions:\npress");
        System.out.println("0   -   to shutdown\n" +
                "1  -   to print contact\n" +
                "2  -   to add new contact\n" +
                "3  -   to update an existing contact\n" +
                "4  -   to remove existing contact\n" +
                "5  -   to query an existing contact\n" +
                "6  -   to print a list of available actions\n");
        System.out.println("Choose your actions: ");
    }
}
