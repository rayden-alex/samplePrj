package myProg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@Slf4j
public class Program {
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


        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        AbonDao abonDao = ctx.getBean("abonDaoBean", AbonDao.class);

        abonDao.findFioById(21L).forEach(abon -> log.debug("{}", abon));
        abonDao.findFioById(25L).forEach(abon -> log.error(abon.toString()));
        log.info("");

        System.out.println("FINISH");


    }
}
