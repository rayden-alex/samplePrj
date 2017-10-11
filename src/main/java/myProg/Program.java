package myProg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

public class Program {
    private static final Logger log = Logger.getLogger(Program.class.getName());

    public static void main(String[] args) {
        Calc calc = new Calc();
        System.out.println(calc.add(23, 34));

        Person personAlice = new Person("Alice", 18);
        Person personBob = new Person("Bob", 28);

        ArrayList<Person> list = new ArrayList<>();
        list.add(personAlice);
        list.add(personBob);

        Person[] sortedArray = list.toArray(new Person[list.size()]);
        Arrays.sort(sortedArray, Comparator.comparing(Person::getName));

        System.out.println("logggggggg");
        log.severe("Test logger message");


    }
}
