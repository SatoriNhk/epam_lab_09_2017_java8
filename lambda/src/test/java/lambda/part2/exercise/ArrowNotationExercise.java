package lambda.part2.exercise;

import data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ArrowNotationExercise {

    @Test
    public void getAge() {
        // Person -> Integer
        final Function<Person, Integer> getAge = p -> p != null? p.getAge(): null;

        assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
    }

    @Test
    public void compareAges() {

        // (Person, Person) -> boolean
        final BiPredicate<Person, Person> compareAges = (p1, p2) -> p1 != null && p2 != null && p1.getAge() == p2.getAge();

        //throw new UnsupportedOperationException("Not implemented");               // --------????????????????????????????????????
        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }




    /*
    @Test
    public void getFullName() {
        //Person -> String
        final Function<Person, String> getFullName = p -> {if (p != null) return p.getFirstName() + " " + p.getLastName(); return null;};

        assertEquals("aa bbb", getFullName.apply(new Person("aa", "bbb", 1)));
    }*/

    // TODO
    // ageOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int)
    //

    @Test
    public void getAgeOfPersonWithTheLongestFullName() {
        // Person -> String
        final Function<Person, String> getFullName = p -> p != null? p.getFirstName() + " " + p.getLastName(): null; // TODO

        // (Person, Person) -> Integer
        // TODO use ageOfPersonWithTheLongestFullName(getFullName)

        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName =
                (p1, p2) ->
                        Integer.compare(getFullName.apply(p1).length(), getFullName.apply(p2).length()) == 1? p1.getAge(): p2.getAge();

        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}
