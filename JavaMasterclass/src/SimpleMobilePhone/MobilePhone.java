package SimpleMobilePhone;

import java.util.ArrayList;

public class MobilePhone {

    private String myNumber;
    private ArrayList<Contact> myContacts;

    public MobilePhone(String myNumber) {
        this.myNumber = myNumber;
        myContacts = new ArrayList<Contact>();
    }


    public boolean addNewContact(Contact contact) {
        if (findContact(contact.getName()) >= 0) {
            System.out.println("Contact present");
            return false;
        }

        myContacts.add(contact);
        return true;

    }

    public void printContactList(){

        if(myContacts.size()!=0) {

            for (int i = 0; i < this.myContacts.size(); i++) {
                System.out.println("Name: " + this.myContacts.get(i).getName() + " Number: " + this.myContacts.get(i).getPhoneNumber());
            }
        }
        else{
            System.out.println("Contacts list empty");
        }

    }



    public String queryContact(Contact contact){
        if(findContact(contact)>=0){
            return contact.getName();
        }
        return null;
    }

    public Contact queryContact(String name){
        int position = findContact(name);

        if(position>=0){
            return this.myContacts.get(position);
        }
        return null;
    }

    public boolean removeContact(Contact contact){
        int foundPosition = findContact(contact);
        if(foundPosition<0){
            System.out.println("Contact not found");
            return false;
        }
        myContacts.remove(foundPosition);
        System.out.println(contact.getName()+" was deleted");
        return true;
    }

    public boolean updateContact(Contact oldContact, Contact newContact) {
        int foundPosition = findContact(oldContact);

        if(foundPosition<0){
            System.out.println(oldContact.getName()+" was not found");
            return false;
        }
        this.myContacts.set(foundPosition,newContact);
        return true;


    }

    public int findContact(Contact contact) {
        return myContacts.indexOf(contact);
    }


    private int findContact(String contactName) {

        for (int i=0;i<this.myContacts.size();i++) {
            Contact contact = this.myContacts.get(i);
            if (contact.getName().equals(contactName)) {
                return i;
            }
        }
        return -1;

    }


}
