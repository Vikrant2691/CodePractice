package InnerClassChallenge;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        MyLinkedList linkedList= new MyLinkedList(null);

        String stringData= "Darwin Brisbane Perth Melbourne Canberra Adelaide Sydney Canberra";

        String[] data = stringData.split(" ");


        ArrayList<Integer> arr= new ArrayList<Integer>();

        arr.add(1);
        arr.add(2);






        for(String s:data){
            linkedList.addItem(new Node(s));
        }
//
//        linkedList.addItem(new Node("b"));
//        linkedList.addItem(new Node("a"));
//        linkedList.addItem(new Node("c"));
//
//        linkedList.addItem(new Node("d"));
//        linkedList.addItem(new Node("Canberra"));
//        linkedList.addItem(new Node("Adelaide"));
//        linkedList.addItem(new Node("Sydney"));
        linkedList.traverse(linkedList.getRoot());
        linkedList.removeItem(new Node("Perth"));

    }

}
