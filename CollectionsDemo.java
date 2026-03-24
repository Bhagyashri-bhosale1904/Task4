import java.util.*;
import java.util.stream.Collectors;
class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;

    public Employee(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("%d: %s (%s)", id, name, department);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
class DataSorter<T extends Comparable<? super T>> {
    
    public List<T> sortList(List<T> list) {
        Collections.sort(list);
        return list;
    }
    public List<T> sortList(List<T> list, Comparator<? super T> comparator) {
        list.sort(comparator);
        return list;
    }
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}

public class CollectionsDemo {
    public static void main(String[] args) {
        // 1. List Demonstration
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(101, "Alice", "Engineering"));
        employeeList.add(new Employee(103, "Bob", "Marketing"));
        employeeList.add(new Employee(102, "Charlie", "HR"));

        System.out.println("___ Original List ___");
        employeeList.forEach(System.out::println);

        Set<Employee> employeeSet = new HashSet<>(employeeList);
        employeeSet.add(new Employee(101, "Alice", "Engineering"));

        System.out.println("\n___ Unique Employees (Set) ___");
        System.out.println("Set size: " + employeeSet.size());

        Map<Integer, Employee> employeeMap = new HashMap<>();
        employeeList.forEach(emp -> employeeMap.put(emp.getId(), emp));

        System.out.println("\n___ Employee Map ___");
        System.out.println("Employee 102: " + employeeMap.get(102));

    
        DataSorter<Employee> sorter = new DataSorter<>();

        List<Employee> sortedById = sorter.sortList(new ArrayList<>(employeeList));
        System.out.println("\n___ Sorted by ID ___");
        sortedById.forEach(System.out::println);

        List<Employee> sortedByName = sorter.sortList(
            new ArrayList<>(employeeList),
            Comparator.comparing(Employee::getName));

        System.out.println("\n___ Sorted by Name ___");
        sortedByName.forEach(System.out::println);
        Map<String, List<Employee>> byDepartment = employeeList.stream()
            .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println("\n___ Employees by Department ___");
        byDepartment.forEach((dept, emps) ->
            System.out.println(dept + ": " + emps.size() + " employees"));


        List<Employee> engineers = DataSorter.filter(employeeList,
            e -> "Engineering".equals(e.getDepartment()));
        System.out.println("\n___ Filtered Engineers ___");
        engineers.forEach(System.out::println);
    }
}
