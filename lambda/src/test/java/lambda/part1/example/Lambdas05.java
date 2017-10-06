package lambda.part1.example;

import data.Person;
import org.junit.Test;

import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("Convert2MethodRef")

public class Lambdas05 {

    private <T> void printResult(T t, Function<T, String> function) {
        String lastName = function.apply(t);


        System.out.println(lastName);
    }

    private final Person person = new Person("John", "Galt", 33);

    @Test
    public void printField() {

        /*
        printResult(person, new Function<Person, String>() {
            @Override
            public String apply(Person person) {
                return person.getLastName();
            }
        });
         */


        printResult(person, Person::getLastName);  /// getLastName(Person this)

        //BiFunction<Person, String, Person> changeFirstName = Person::withFirstName;

        //printResult(changeFirstName.apply(person, "newName"), Person::getFirstName);
    }


    private static class PersonHelper {

        static String stringRepresentation(Person person) {
            return person.toString();
        }
    }


    @Test
    public void printStringRepresentation() {
        printResult(person, PersonHelper::stringRepresentation);
    }

    @Test
    public void exception() {
            Runnable r = () -> {
            //Thread.sleep(100);  // it causes an InterruptedException which is not declared in method run (Runnable interface)
            person.print();
        };

        r.run();
    }


    // First case

    @FunctionalInterface
    private interface DoSomething {
        void doSmth();
    }

    private void conflict(Runnable r) {
        System.out.println("Runnable");
        r.run();
    }

    private void conflict(DoSomething d) {
        System.out.println("DoSomething");
        d.doSmth();
    }

    private String printAndReturn() {    // even this method returns String it can still be used as method reference where void method is expected
        person.print();
        return person.toString();
    }

    @Test
    public void callConflict() {
        //conflict(this::printAndReturn);   // ambiguous -> cant be complied
    }

    // Second case

    @FunctionalInterface
    private interface DoSomething2 {
        int doSmth();
    }

    private void conflict2(Runnable r) {
        System.out.println("Runnable");
        r.run();
    }

    private void conflict2(DoSomething2 d) {
        System.out.println("DoSomething");
        d.doSmth();
    }

    private String printAndReturn2() {
        person.print();
        return person.toString();
    }

    @Test
    public void callConflict2() {
        conflict2(this::printAndReturn2);   // ambiguous -> cant be complied
    }


    @Test
    public void serializeTree() {

    }

    private interface PersonFactory {
        Person create(String name, String lastName, int age);
    }

    private void withFactory(PersonFactory pf) {
        pf.create("name", "lastName", 33).print();
    }

    @Test
    public void factory() {
        withFactory(Person::new);
    }   // using constructor as method reference (since java 8)
}
