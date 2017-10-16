package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee {
    private final Person person;
    private final List<JobHistoryEntry> jobHistory;

    public Employee(String name, String surname, int age, List<JobHistoryEntry> jobHistory) {
        this.person = new Person(name, surname, age);
        this.jobHistory = jobHistory;
    }

    public Employee(Person person, List<JobHistoryEntry> jobHistory) {
        this.person = person;
        this.jobHistory = jobHistory;
    }

    public Employee withPerson(Person p) {
        return new Employee(p, jobHistory);
    }

    public Employee withJobHistory(List<JobHistoryEntry> h) {
        return new Employee(person, h);
    }

    public Person getPerson(Employee this) {
        return person;
    }

    public List<JobHistoryEntry> getJobHistory() {
        return new ArrayList<>(jobHistory);
    }

    @Override
    public String toString() {
        return "Employee: " + person + " " + jobHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return Objects.equals(person, employee.person) && Objects.equals(jobHistory, employee.jobHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, jobHistory);
    }

}
