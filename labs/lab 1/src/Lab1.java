import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
public class Lab1 {
    //Lab1.java: A simple program demonstrating basic language features.
    public static boolean contains(List<String> items, String desired) {
        //Return true if the list contains the desired item.
        //Iteration on elements
        for (String item : items) {
            //Conditional
            if (item.equals(desired)) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        //Variables
        int x = 3;
        double y = 4.0;
        int z = (int)Math.pow(Math.pow(x, 2) + Math.pow(y, 2), (1.0/2.0));

        //String Concatenation
        System.out.println("x: " + x);

        //String Formatting
        System.out.printf("y: %s%n", y);

        //Printing on Same Line
        System.out.print("z: ");
        System.out.println(z);

        //Strings and Characters
        String a = "hello";
        char b = 'j';
        String c = a.replace(a.charAt(0), b);

        //Iteration in Indices
        for (int i = 0; i != c.length(); i++) {
            System.out.print(c.charAt(i));  //Print on same line
        }
        System.out.println();  //Print the line 's end

        //List Usage
        LinkedList<String> cats = new LinkedList<>();
        cats.add("Mochi");
        cats.add("harvest");
        cats.pop();
        cats.add("Pearl");

        //Function Call
        boolean has_mochi = contains(cats,"Mochi");
        if (!has_mochi) {
            System.out.println("Bye bye, Mochi! Farewell!");
        }
    }
}