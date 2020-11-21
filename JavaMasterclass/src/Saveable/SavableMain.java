package Saveable;

public class SavableMain {

    public static void main(String[] args) {
        Saveable saveable=new Player("Vikrant",100,23);



    }


    public static void saveObject(Saveable saveableObject){
        for (int i=0;i<saveableObject.write().size();i++){
            System.out.println("Saving "+saveableObject.write().get(0)+" to storage device");

        }
    }
}
