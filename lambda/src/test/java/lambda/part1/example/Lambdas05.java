package lambda.part1.example;

import data.Person;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("Convert2MethodRef")

public class Lambdas05 {

    private <T> void printResult(T value, Function<T, String> function) {
        System.out.println(function.apply(value));
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

    class ThrowableClass implements Runnable {

        @Override
        public void run() {

        }
    }

    @FunctionalInterface
    interface ThrowableInterface {

        void method() throws Exception;
    }

    @Test
    public void exception() {
        ThrowableInterface throwableInterfaceRef = () -> {
            Thread.sleep(100);
        };



        Runnable throwableClassRef = new ThrowableClass();
        throwableClassRef.run();

        Runnable r = () -> {
//            Thread.sleep(100);
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
        Set<Person> treeSet = new TreeSet<>((Comparator<Person> & Serializable)(o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        treeSet.add(new Person("b", "b", 2));
        treeSet.add(new Person("a", "a", 1));
        treeSet.add(new Person("c", "c", 3));

        System.out.println(treeSet);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream stream = new ObjectOutputStream(byteArrayOutputStream);
            stream.writeObject(treeSet);


            System.out.println(new String(byteArrayOutputStream.toByteArray()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface PersonFactory {

        Person create(String name, String lastName, int age);
    }

    private void withFactory(PersonFactory pf) {
        pf.create("name", "lastName", 33).print();
    }

    @Test
    public void factory() {
        PersonFactory factory = Person::new;
        withFactory(factory);
    }
}
