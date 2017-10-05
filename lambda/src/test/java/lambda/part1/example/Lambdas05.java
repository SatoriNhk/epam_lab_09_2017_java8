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


        printResult(person, Person::getLastName);  /// ?!???!?!? apply has a parameter <T>, getLastName has not  ADD COMMENTS

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
            //Thread.sleep(100);
            person.print();
        };

        r.run();
    }

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

    private String printAndReturn() {
        person.print();
        return person.toString();
    }

    @Test
    public void callConflict() {
        //conflict(this::printAndReturn);
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
    }
}
