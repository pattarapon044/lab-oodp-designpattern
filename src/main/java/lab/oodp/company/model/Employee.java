package lab.oodp.company.model;

import java.util.*;

/**
 * Intended to represent an employee at a company. Each employee has a number of info fields,
 * and can be managed by a Manager.
 */
public class Employee {

    // Necessary fields here
    // I just create it all to final fields now, if this program is more ways to uses just change it to non-final
    // Examples : employee can set new salary, or employee change his name ,....,  Just change it to non-final and create setter method
    private final int id;
    private final String name;
    private final String email;
    private final String jobTitle;
    private final int salary;

    /**
     * Manger of this employee that can be set in Manager addEmployee's method
     */
    private Manager manager;

    /**
     * Creates a new employee
     *
     * @param id       the employee id
     * @param name     the employee's name
     * @param email    the employee's email address
     * @param jobTitle the employee's job title
     * @param salary   the employee's salary
     */
    public Employee(int id, String name, String email, String jobTitle, int salary) {
        // TODO complete this
        this.id = id;
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    /**
     * Gets this employee's Manager, if any.
     *
     * @return the employee's manager, or null if the employee has no manager
     */
    public Manager getManager() {
        // TODO complete this
        return manager;
    }


    /**
     * Setter for manger to be called in class Manager
     * @param manager of this employee that is not this
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    /**
     * Gets the total "reporting chain" for this employee. This is essentially a list of employees starting at the root
     * of the employment hierarchy. Examples:
     * <p>
     * If this employee has no manager, the list will just contain this employee.
     * <p>
     * If this employee has a manager, then the list will contain its manager's reporting chain, with this employee
     * added at the end.
     *
     * @return this employee's reporting chain, as a List.
     */
    public List<Employee> getReportingChain() {
        // TODO complete this
        List<Employee> reportingChain = new ArrayList<>(); // array list for stored the reporting chains

        // Add reporting chains
        // It will be : this -> this.manger -> this.manger.manger -> ... -> top level of manager
        reportingChain.add(this);
        Manager manager;
        for (manager = this.manager; manager != null; manager = manager.getManager()){ // pointing this manger and its manger till null
            reportingChain.add(manager);
        }

        // reverse it to big manger -> lower manager -> ... -> this and return it
        Collections.reverse(reportingChain);
        return reportingChain;
    }

    /**
     * Gets a string representation of this employee, which should be of the form: name (email: email)
     */
    // TODO override and implement toString()
    @Override
    public String toString(){
        // name (email: ...@domain)
        return name + " (email: " + email + ")";
    }

    /**
     * Gets a value indicating whether this employee is equal to the given object. The object is considered equal if
     * it is non-null, is an Employee, and has a matching id, salary, name, email, and job title.

     * HINT: Don't include the employee's manager in the equality calculations (or if you do, just check it
     * with == rather than .equals(), or just check the manager's name rather than the whole object). Otherwise, you
     * might run into a StackOverflowError (I'll leave it as an exercise to the reader to figure out why this might be).
     *
     * @param o the object to check
     * @return true if the given object meets the conditions described above; false otherwise
     */
    // TODO override and implement equals()
    @Override
    public boolean equals(Object o){
        // this will automatically check o is null or not if o is null it is not instance of Employee
        if (o instanceof Employee){
            Employee other = (Employee) o;


            // First check if manger is not null check other's name and manager's name
            if (((manager != null) && Objects.equals(other.getName(), manager.getName()))){
                return false;
            }

            // Check important data is all equals
            return  other.getId() == this.id
                    && other.getSalary() == this.salary
                    && Objects.equals(other.getName(), this.name)
                    && Objects.equals(other.getEmail(), this.email)
                    && Objects.equals(other.getJobTitle(), this.jobTitle);
        }

        // return false if o is null or not instance of Employee
        return false;
    }


}
