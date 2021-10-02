import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.print("Select exercise number: ");
        try {
            int exNum = Integer.parseInt(reader.readLine());
            switch (exNum) {
                case 2 -> ex2_changeCasing();
                case 3 -> ex3_ContainsEmployee();
                case 4 -> ex4_Employees_With_Salary_Over_50000();
                case 5 -> ex5_Employees_from_Department();
                case 6 -> ex6_Adding_a_New_Address_and_Updating_Employee();
                case 7 -> ex7_addresses_with_Employee_Count();
                case 8 -> ex8_Get_Employee_with_Project();
                case 9 -> ex9_Find_Latest_10_Projects();
                case 10 -> ex10_Increase_Salaries();
                case 11 -> ex11_Find_Employees_by_FirstName();
                case 12 -> ex12_Employees_Maximum_Salaries();
                case 13 -> ex13_Remove_Towns();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ex13_Remove_Towns() throws IOException {
        System.out.print("Enter town name to be deleted: ");
        String townName = reader.readLine();

        Town town = entityManager.createQuery("select t from Town t where t.name = :tn", Town.class)
                .setParameter("tn", townName)
                .getSingleResult();

        int addresses = removeAddressesByTownId(town.getId());

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();

        System.out.printf("%d address in %s deleted%n", addresses, townName);
    }

    private int removeAddressesByTownId(int townId) {
        List<Address> addresses = entityManager.createQuery("select a from Address a where a.town.id = :tn", Address.class)
                .setParameter("tn", townId).getResultList();

        addresses.forEach(t -> t.getEmployees().forEach(em -> em.setAddress(null)));

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
        return addresses.size();
    }

    @SuppressWarnings("unchecked")
    private void ex12_Employees_Maximum_Salaries() {
        List<Object[]> resultList = entityManager.createNativeQuery("select d.`name`, max(e.salary) as `m_salary` from departments d \n" +
                "join employees e on d.department_id = e.department_id\n" +
                "group by d.`name` \n" +
                "having `m_salary` not between 30000 and 70000").getResultList();
        resultList.forEach(r -> System.out.printf("%s - %s%n", r[0], r[1]));
    }

    private void ex11_Find_Employees_by_FirstName() throws IOException {
        System.out.print("Enter first name: ");
        String firstName = reader.readLine();
        entityManager.createQuery("select e from Employee e where e.firstName like :patt", Employee.class)
                .setParameter("patt", firstName + "%")
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    private void ex10_Increase_Salaries() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Employee e set e.salary = e.salary * 1.12 " +
                "where e.department.id in :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.createQuery("select e from Employee e where e.department.id in :ids", Employee.class)
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .getResultStream().forEach(e -> System.out.printf("%s %s ($%.2f)%n",
                e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private void ex9_Find_Latest_10_Projects() {
        entityManager.createQuery("select p from Project p where p.endDate is null order by p.startDate desc", Project.class)
                .getResultStream().limit(10).sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .forEach(p -> System.out.printf("Project name: %s%n\tProject Description: %s%n" +
                                "\tProject Start Date: %s%n" +
                                "\tProject End Date: %s%n",
                        p.getName(),
                        p.getDescription(),
                        p.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                        p.getEndDate() == null ? "null" : p.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))));
    }

    private void ex8_Get_Employee_with_Project() throws IOException {
        System.out.print("Enter employee id: ");
        int emplId = Integer.parseInt(reader.readLine());
        Employee employee = entityManager.createQuery("Select e from Employee e where e.id = :empl_id", Employee.class)
                .setParameter("empl_id", emplId)
                .getSingleResult();
        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .forEach(p -> System.out.println("\t" + p.getName()));
    }

    private void ex7_addresses_with_Employee_Count() {
        entityManager.createQuery("select a from Address a order by a.employees.size desc ", Address.class)
                .getResultStream().limit(10)
                .forEach(a -> System.out.printf("%s, %s - %d employees%n", a.getText(),
                        a.getTown() == null ? "Unknown" : a.getTown().getName(),
                        a.getEmployees().size()));
    }

    private void ex6_Adding_a_New_Address_and_Updating_Employee() throws IOException {
        entityManager.getTransaction().begin();
        // add new address
        Address address = new Address();
        address.setText("Vitoshka 15");
        entityManager.persist(address);
        int addressId = address.getId();
        // get employee last name
        System.out.print("Enter employee last name: ");
        String lastName = reader.readLine();
        // update employee
        int i = entityManager.createQuery("update Employee e set e.address.id = :addr where e.lastName = :l_name")
                .setParameter("addr", addressId)
                .setParameter("l_name", lastName).executeUpdate();
        System.out.println(i);
        entityManager.getTransaction().commit();
    }

    private void ex5_Employees_from_Department() {
        entityManager.createQuery("select e from Employee e where e.department.name = 'Research and Development' " +
                "order by e.salary, e.id", Employee.class)
                .getResultStream().forEach(e -> System.out.printf("%s %s from %s - $%.2f%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private void ex4_Employees_With_Salary_Over_50000() {
        entityManager.createQuery("SELECT e from Employee e where e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream().forEach(
                e -> System.out.println(e.getFirstName())
        );
    }

    private void ex3_ContainsEmployee() throws IOException {
        System.out.print("Enter employee full name: ");
        String[] fullName = reader.readLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long empl = entityManager.createQuery("SELECT count(e) from Employee e " +
                "where e.firstName = :f_name and e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        if (empl != 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    private void ex2_changeCasing() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town t SET t.name = upper(t.name) where length(t.name) <= 5 ");
        System.out.println(query.executeUpdate());
        entityManager.getTransaction().commit();
    }
}
