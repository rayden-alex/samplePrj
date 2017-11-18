package myProg;

import lombok.extern.slf4j.Slf4j;
import myProg.services.AbonService;
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



        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("ApplicationContext is UP");

        AbonService abonService = ctx.getBean("abonServiceImpl", AbonService.class);

        log.error(abonService.count().toString());

//                try {
//            Thread.sleep(1000 * 10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        abonDao.findAbonById(21L).forEach(abon -> log.debug("{}", abon));
//         log.error("Count={}", abonDao.findAbonById(1000000L).size());

//
//        try {
//            Thread.sleep(1000 * 10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        log.info("----------------");
//        //log.error("Count={}", abonDao.findAbonById(1000000L).size());
//
//        try (AbonWriteToFileCallbackHandler rch = new AbonWriteToFileCallbackHandler()) {
//            abonDao.writeFioById(10_000L, rch);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.gc();
//
//        try {
//            Thread.sleep(1000 * 10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        log.info("----------------");
//        //log.error("Count={}", abonDao.findAbonById(1000000L).size());
//
//        try (AbonWriteToFileCallbackHandler rch = new AbonWriteToFileCallbackHandler()) {
//            abonDao.writeFioById(1_000_000L, rch);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.gc();
//
//        log.info("FINISH");
//
//
//
//        try {
//            Thread.sleep(1000 * 20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
