package myProg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {
    private static final Logger log = LoggerFactory.getLogger(Program.class);

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
        log.info("Test logger message");


    }
}
