package Practice;

public class ByteShortIntLong {

    public static void main(String[] args) {


        System.out.println(ByteShortIntLong.poundsToKilograms(2));

    }

    public static double poundsToKilograms(int value){

        return (value * 0.45359237);

    }

    public static void printDatatypesMinMaxValues(){
        System.out.println("Integer min value:"+Integer.MIN_VALUE);
        System.out.println("Integer max value:"+Integer.MAX_VALUE);

        System.out.println("Byte min value:"+Byte.MIN_VALUE);
        System.out.println("Byte max value:"+Byte.MAX_VALUE);

        System.out.println("Short min value:"+Short.MIN_VALUE);

        System.out.println("Short max value:"+Short.MAX_VALUE);
        System.out.println("Float min value:"+Float.MIN_VALUE);
        System.out.println("Float max value:"+Float.MAX_VALUE);

        System.out.println("Double min value:"+Double.MIN_VALUE);
        System.out.println("Double max value:"+Double.MAX_VALUE);

        int myIntValue = 5/3;
        float myFloatValue=(float) 5/3;
        double myDoubleValue=(double) 5/3;

        System.out.println("myIntValue : " +myIntValue);
        System.out.println("myFloatValue: " +myFloatValue);
        System.out.println("myDoubleValue: "+myDoubleValue);

    }
}
