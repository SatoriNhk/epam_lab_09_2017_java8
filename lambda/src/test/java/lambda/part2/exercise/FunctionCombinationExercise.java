package lambda.part2.exercise;

import data.Person;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FunctionCombinationExercise {

    @Test
    public void personHasNotEmptyLastNameAndFirstName0() {
        // Person -> boolean
        Predicate<Person> validate = p -> !p.getFirstName().isEmpty() && !p.getLastName().isEmpty();

        assertTrue(validate.test(new Person("a", "b", 0)));
        assertFalse(validate.test(new Person("", "b", 0)));
        assertFalse(validate.test(new Person("a", "", 0)));
    }

    // TODO
    // negate1: (Person -> boolean) -> (Person -> boolean)
    private Predicate<Person> negate1(Predicate<Person> test) {
        return p -> {
            return !test.test(p);
            //throw new UnsupportedOperationException();
        };
    }

    // TODO
    // validateFirstNameAndLastName: (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    private Predicate<Person> validateFirstNameAndLastName(Predicate<Person> t1, Predicate<Person> t2) {
        return p -> {
            return t1.test(p) && t2.test(p);
            //throw new UnsupportedOperationException();
        };
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName1() {
        Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> validateFirstName = negate1(hasEmptyFirstName);
        Predicate<Person> validateLastName = negate1(hasEmptyLastName);

        Predicate<Person> validate = validateFirstNameAndLastName(validateFirstName, validateLastName);

        assertTrue(validate.test(new Person("a", "b", 0)));
        assertFalse(validate.test(new Person("", "b", 0)));
        assertFalse(validate.test(new Person("a", "", 0)));
    }

    // TODO
    // negate: (T -> boolean) -> (T -> boolean)
    private <T> Predicate<T> negate(Predicate<T> test) {
        return T -> {
            return !test.test(T);
        };
        //throw new UnsupportedOperationException();
    }

    // TODO
    // and: (T -> boolean, T -> boolean) -> (T -> boolean)
    private <T> Predicate<T> and(Predicate<T> t1, Predicate<T> t2) {
        // TODO
        return T -> {
            return t1.test(T) && t2.test(T);
            //throw new UnsupportedOperationException();
        };
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName2() {
        Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        final Predicate<Person> validateFirstName = negate(hasEmptyFirstName); // TODO use negate
        final Predicate<Person> validateLastName = negate(hasEmptyLastName); // TODO use negate

        final Predicate<Person> validate = and(validateFirstName, validateLastName); // TODO use and

        assertTrue(validate.test(new Person("a", "b", 0)));
        assertFalse(validate.test(new Person("", "b", 0)));
        assertFalse(validate.test(new Person("a", "", 0)));
    }

    @Test
    public void personHasNotEmptyLastNameAndFirstName3() {
        Predicate<Person> hasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> hasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> validateFirstName = null; // TODO use Predicate::negate
        Predicate<Person> validateLastName = null; // TODO use Predicate::negate

        Predicate<Person> validate = null; // TODO use Predicate::and

        assertTrue(validate.test(new Person("a", "b", 0)));
        assertFalse(validate.test(new Person("", "b", 0)));
        assertFalse(validate.test(new Person("a", "", 0)));
    }

}
