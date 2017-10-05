package lambda.part1.example;


import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

// JSR-335 Lambda Expressions for the Java Programming Language

// https://github.com/java8-course/lambda

@SuppressWarnings({"Guava", "Convert2Lambda", "Anonymous2MethodRef"})
public class Lambdas01 {

    @Test
    public void sortPersons() {
        Person[] persons = {
                new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 40),
                new Person("name 2", "lastName 1", 30)
        };

        Arrays.sort(persons, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });

        assertArrayEquals(new Person[]{
                new Person("name 2", "lastName 1", 30),
                new Person("name 1", "lastName 2", 40),
                new Person("name 3", "lastName 3", 20)
        }, persons);
    }

    @Test
    public void findFirstByName_foreach() {
        List<Person> persons = ImmutableList.of(
                new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 40),
                new Person("name 2", "lastName 1", 30),
                new Person("name 1", "lastName 3", 40)
        );

        Person person = null;

        for (Person p : persons) {
            // if (p.getFirstName().equals("name 1")) {   NPE threat , need to flip
            if ("name 1".equals(p.getFirstName())) {
                person = p;
                break;
            }
        }

        if (person != null) {
            person.print();
        }

        //Asserts
        assertNotNull(person);
        assertEquals(new Person("name 1", "lastName 2", 40), person);

    }

    @Test
    public void findFirstByName_guava() {
        List<Person> persons = ImmutableList.of(
                new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 40),
                new Person("name 2", "lastName 1", 30)
        );

        final Optional<Person> personOptional =
                FluentIterable.from(persons)
                        .firstMatch(new Predicate<Person>() {
                            @Override
                            public boolean apply(Person p) {
                                return "name 1".equals(p.getFirstName());
                            }
                        });

        if (personOptional.isPresent()) {
            personOptional.get().print();
            assertNotNull(personOptional.get());
            assertEquals(new Person("name 1", "lastName 2", 40), personOptional.get());
        }
    }

    @Test
    public void lastNamesSet() {
        List<Person> persons = ImmutableList.of(
                new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 40),
                new Person("name 2", "lastName 1", 30)
        );

        final Map<String, Person> personByLastName =
                FluentIterable.from(persons)
                        .uniqueIndex(new Function<Person, String>() {
                            @Override
                            public String apply(Person person) {
                                return person.getLastName();
                            }
                        });

        assertEquals(personByLastName.get("lastName 3"), new Person("name 3", "lastName 3", 20));
    }

}
