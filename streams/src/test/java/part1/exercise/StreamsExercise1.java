package part1.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class StreamsExercise1 {

    @Test
    public void getAllEpamEmployees() {
        List<Employee> employees = Arrays.asList(
            new Employee(new Person("John", "Galt", 20),
                    Arrays.asList(
                        new JobHistoryEntry(3, "dev", "epam"),
                        new JobHistoryEntry(2, "dev", "google")
                    )),
            new Employee(new Person("John", "Doe", 21),
                    Arrays.asList(
                        new JobHistoryEntry(4, "BA", "yandex"),
                        new JobHistoryEntry(2, "QA", "epam"),
                        new JobHistoryEntry(2, "dev", "abc")
                    )),
            new Employee(new Person("John", "White", 22),
                    Collections.singletonList(
                        new JobHistoryEntry(6, "QA", "epam")
                    )),
            new Employee(new Person("John", "Galt", 23),
                    Arrays.asList(
                        new JobHistoryEntry(2, "dev", "google")
        )));

        List<Person> epamEmployees = null;// TODO all persons with experience in epam

        assertEquals(Arrays.asList(
                new Person("John", "Galt", 20),
                new Person("John", "Doe", 21),
                new Person("John", "White", 22)),
            epamEmployees
        );
    }

    @Test
    public void getEmployeesStartedFromEpam() {
        List<Employee> employees = Arrays.asList(
                new Employee(new Person("John", "Galt", 20),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(2, "dev", "google")
                        )),
                new Employee(new Person("John", "Doe", 21),
                        Arrays.asList(
                                new JobHistoryEntry(4, "BA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(new Person("John", "White", 22),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        )),
                new Employee(new Person("John", "Galt", 23),
                        Arrays.asList(
                                new JobHistoryEntry(2, "dev", "google")
                        )));

        List<Person> epamEmployees = null;// TODO all persons with first experience in epam

        assertEquals(Arrays.asList(
                new Person("John", "Galt", 20),
                new Person("John", "White", 22)),
                epamEmployees
        );
    }

    @Test
    public void sumEpamDurations() {
        List<Employee> employees = Arrays.asList(
            new Employee(new Person("John", "Galt", 20),
                    Arrays.asList(
                            new JobHistoryEntry(3, "dev", "epam"),
                            new JobHistoryEntry(2, "dev", "google")
                    )),
            new Employee(new Person("John", "Doe", 21),
                    Arrays.asList(
                            new JobHistoryEntry(4, "BA", "yandex"),
                            new JobHistoryEntry(2, "QA", "epam"),
                            new JobHistoryEntry(2, "dev", "abc")
                    )),
            new Employee(new Person("John", "White", 22),
                    Collections.singletonList(
                            new JobHistoryEntry(6, "QA", "epam")
                    )),
            new Employee(new Person("John", "Galt", 23),
                    Arrays.asList(
                            new JobHistoryEntry(2, "dev", "google")
        )));


        int result = 0; // TODO sum
        assertEquals(11, result);
    }

}
