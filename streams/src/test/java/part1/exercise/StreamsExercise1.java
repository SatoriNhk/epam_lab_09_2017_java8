package part1.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Predicate<Employee> workedInEpam = employee -> employee.getJobHistory()
                                                               .stream()
                                                               .anyMatch(history -> "epam".equals(history.getEmployer()));
        List<Person> epamEmployees = employees.stream()
                                              .filter(workedInEpam)
                                              .map(Employee::getPerson)
                                              .collect(Collectors.toList());

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

        Predicate<Employee> startedFromEpam = employee -> !employee.getJobHistory().isEmpty()
                                                       && "epam".equals(employee.getJobHistory().get(0).getEmployer());

        List<Person> epamEmployees = employees.stream()
                                              .filter(startedFromEpam)
                                              .map(Employee::getPerson)
                                              .collect(Collectors.toList());

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

        int result = employees.parallelStream()
                              .flatMap(employee -> employee.getJobHistory().stream())
                              .filter(entry -> "epam".equals(entry.getEmployer()))
                              .mapToInt(JobHistoryEntry::getDuration)
                              .sum();
//                              .reduce(0, (value, entry) -> value + entry.getDuration(), Integer::sum);
//                              .collect(Collectors.summingInt(JobHistoryEntry::getDuration));

        assertEquals(11, result);
    }

}
