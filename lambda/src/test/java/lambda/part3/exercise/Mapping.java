package lambda.part3.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess"})
public class Mapping {

    private static class MapHelper<T> {

        private final List<T> list;

        public MapHelper(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }

        // ([T], T -> R) -> [R]
        public <R> MapHelper<R> map(Function<T, R> f) {
            List<R> list = new ArrayList<>();
            for (T elem: this.getList()) {
                list.add(f.apply(elem));
            }
            return new MapHelper<>(list);
        }

        // ([T], T -> [R]) -> [R]
        public <R> MapHelper<R> flatMap(Function<T, List<R>> f) {
            List<R> list = new ArrayList<>();
            for (T elem: this.getList()) {
                list.addAll(f.apply(elem));
            }
            return new MapHelper<>(list);
        }
    }

    public List<JobHistoryEntry> addOneYear(List<JobHistoryEntry> jobList) {
        List<JobHistoryEntry> newList = new ArrayList<>();
        for (JobHistoryEntry elem : jobList) {
            newList.add(new JobHistoryEntry(elem.getDuration() + 1, elem.getPosition(), elem.getEmployer()));
        }
        return newList;
    }

    public List<JobHistoryEntry> changeQaToQA(List<JobHistoryEntry> jobList) {
        List<JobHistoryEntry> newList = new ArrayList<>();
        for (JobHistoryEntry elem : jobList) {
            newList.add("qa".equals(elem.getPosition())? elem.withPosition("QA"):elem);
        }
        return newList;
    }

    @Test
    public void mapping() {
        List<Employee> employees = getEmployees();

        List<Employee> mappedEmployees = new MapHelper<>(employees)
                .map(e -> e.withPerson(e.getPerson().withFirstName("John")))     // Изменить имя всех сотрудников на John .map(e -> e.withPerson(e.getPerson().withFirstName("John")))
                .map(e -> e.withJobHistory(addOneYear(e.getJobHistory()))) // Добавить всем сотрудникам 1 год опыта .map(e -> e.withJobHistory(addOneYear(e.getJobHistory())))
                .map(e -> e.withJobHistory(changeQaToQA(e.getJobHistory()))) // Заменить все qa на QA
                .getList();

        List<Employee> expectedResult = Arrays.asList(
            new Employee(new Person("John", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(3, "dev", "epam"),
                        new JobHistoryEntry(2, "dev", "google")
                )),
            new Employee(new Person("John", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(4, "QA", "yandex"),
                        new JobHistoryEntry(2, "QA", "epam"),
                        new JobHistoryEntry(2, "dev", "abc")
                )),
            new Employee(new Person("John", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(6, "QA", "epam")
                ))
        );

        assertEquals(mappedEmployees, expectedResult);
    }

    private List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(new Person("a", "Galt", 30),
                    Arrays.asList(
                            new JobHistoryEntry(2, "dev", "epam"),
                            new JobHistoryEntry(1, "dev", "google")
                    )),
                new Employee(new Person("b", "Doe", 40),
                    Arrays.asList(
                            new JobHistoryEntry(3, "qa", "yandex"),
                            new JobHistoryEntry(1, "qa", "epam"),
                            new JobHistoryEntry(1, "dev", "abc")
                    )),
                new Employee(new Person("c", "White", 50),
                    Collections.singletonList(
                            new JobHistoryEntry(5, "qa", "epam")
                    ))
            );
    }

    private static class LazyMapHelper<T, R> {

        List<T> list;
        Function<T, R> function;

        public LazyMapHelper(List<T> list, Function<T, R> function) {
            this.list = list;
            this.function = function;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, Function.identity());
        }

        public List<R> force() {
            List<R> newList = new ArrayList<>();
            for (T elem: list) {
                newList.add(this.function.apply(elem));
            }
            return newList;
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> f) {
            return new LazyMapHelper<>(this.list, this.function.andThen(f));
        }
    }

    private static class LazyFlatMapHelper<T,R> {

        private final List<T> list;
        private final Function<T, List<R>> mapper;

        public LazyFlatMapHelper(List<T> list, Function<T, List<R>> mapper) {
            this.list = list;
            this.mapper = mapper;
        }

        public static <T> LazyFlatMapHelper<T,T> from (List<T> list) {
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap (Function<R, List<U>> remapper) {
            return new LazyFlatMapHelper<>(list, mapper.andThen(result -> result.stream()
                    .flatMap(element -> remapper.apply(element).stream())
                    .collect(Collectors.toList())));
        }

        public List<R> force() {
            List<R> result = new ArrayList<>();
            /*for (T value : list) {
                result.addAll(mapper.apply(value));
            }*/
            list.stream().map(mapper::apply).forEach(result::addAll);
            return result;
        }
    }

    // TODO * LazyFlatMapHelper

    @Test
    public void lazyFlatMapping() {
        List<Employee> employees = getEmployees();
        List<JobHistoryEntry> force = LazyFlatMapHelper.from(employees)
                                                       .flatMap(Employee::getJobHistory)
                                                       .force();
        /*List<Character> force1 = LazyFlatMapHelper.from(employees)
                                                  .flatMap(Employee::getJobHistory)
                                                  .flatMap(entry -> entry.getPosition()
                                                          .chars().)

                */

    }

    @Test
    public void lazyMapping() {
        List<Employee> employees = getEmployees();

        List<Employee> mappedEmployees = LazyMapHelper.from(employees)
                .map(e -> e.withPerson(e.getPerson().withFirstName("John")))     // Изменить имя всех сотрудников на John .map(e -> e.withPerson(e.getPerson().withFirstName("John")))
                .map(e -> e.withJobHistory(addOneYear(e.getJobHistory()))) // Добавить всем сотрудникам 1 год опыта .map(e -> e.withJobHistory(addOneYear(e.getJobHistory())))
                .map(e -> e.withJobHistory(changeQaToQA(e.getJobHistory()))) // Заменить все qa на QA
                .force();

        List<Employee> expectedResult = Arrays.asList(
            new Employee(new Person("John", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(3, "dev", "epam"),
                        new JobHistoryEntry(2, "dev", "google")
                )),
            new Employee(new Person("John", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(4, "QA", "yandex"),
                        new JobHistoryEntry(2, "QA", "epam"),
                        new JobHistoryEntry(2, "dev", "abc")
                )),
            new Employee(new Person("John", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(6, "QA", "epam")
                ))
        );

        assertEquals(mappedEmployees, expectedResult);
    }
}
