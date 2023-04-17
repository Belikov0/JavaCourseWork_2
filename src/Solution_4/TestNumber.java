package Solution_4;

import java.util.HashSet;

public class TestNumber {

    public static void main(String[] args) {
        Odd o_1 = new Odd(3);
        Odd o_2 = new Odd(5);
        Odd o_3 = new Odd(5);
        Odd o_4 = new Odd(6);   //Throw exception out of not odd

        Even e_1 = new Even(2);
        Even e_2 = new Even(4);
        Even e_3 = new Even(4);
        Even e_4 = new Even(7); //Throw exception out of not even

        //Test hashCode()

        //Test toString() and equals()
        System.out.println("\nTest toString() and equals(): ");
        System.out.println(String.format("\t%s.equals(%s): %s", o_1, o_2, o_1.equals(o_2)));
        System.out.println(String.format("\t%s.equals(%s): %s", o_2, o_3, o_2.equals(o_3)));
        System.out.println(String.format("\t%s.equals(%s): %s", e_1, e_2, e_1.equals(e_2)));
        System.out.println(String.format("\t%s.equals(%s): %s", e_2, e_3, e_2.equals(e_3)));

        //Test add() and multiply()
        System.out.println("\nTest add() and multiply()");
        System.out.println(String.format("\t%s add %s: %s; result type: %s", o_1, o_2, o_1.add(o_2), o_1.add(o_2).getClassName()));
        System.out.println(String.format("\t%s add %s: %s; result type: %s", e_1, o_2, e_1.add(o_2), e_1.add(o_2).getClassName()));
        System.out.println(String.format("\t%s add %s: %s; result type: %s", e_1, e_2, e_1.add(e_2), e_1.add(e_2).getClassName()));
        System.out.println(String.format("\t%s multiply %s: %s; result type: %s", o_1, o_2, o_1.multiply(o_2), o_1.multiply(o_2).getClassName()));
        System.out.println(String.format("\t%s multiply %s: %s; result type: %s", e_1, o_2, e_1.multiply(o_2), e_1.multiply(o_2).getClassName()));
        System.out.println(String.format("\t%s multiply %s: %s; result type: %s", e_1, e_2, e_1.multiply(e_2), e_1.multiply(e_2).getClassName()));

        //Test equals() in the collection
        HashSet<Number> set = new HashSet<>();
        System.out.println("\nTest equals() in the collection: ");
        System.out.println(String.format("\tAdd o_1 %s into set, is successful: %s", o_1, set.add(o_1)));
        System.out.println(String.format("\tAdd o_2 %s into set, is successful: %s", o_2, set.add(o_2)));
        System.out.println(String.format("\tAdd o_3 %s into set, is successful: %s", o_3, set.add(o_3)));
        System.out.println("\tCurrent set: " + set);
        System.out.println(String.format("\tAdd e_1 %s into set, is successful: %s", e_1, set.add(e_1)));
        System.out.println(String.format("\tAdd e_2 %s into set, is successful: %s", e_2, set.add(e_2)));
        System.out.println(String.format("\tAdd e_3 %s into set, is successful: %s", e_3, set.add(e_3)));
        System.out.println("\tCurrent set: " + set);

        //Test hashCode()
        System.out.println("\nTest hashCode()");
        System.out.println(String.format("\t%s's hashCode %d", o_1, o_1.hashCode()));
        System.out.println(String.format("\t%s's hashCode %d", o_2, o_2.hashCode()));
        System.out.println(String.format("\t%s's hashCode %d", o_3, o_3.hashCode()));
        System.out.println(String.format("\t%s's hashCode %d", e_1, e_1.hashCode()));
        System.out.println(String.format("\t%s's hashCode %d", e_2, e_2.hashCode()));
        System.out.println(String.format("\t%s's hashCode %d", e_3, e_3.hashCode()));

    }
}
