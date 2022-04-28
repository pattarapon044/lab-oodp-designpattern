package lab.oodp.company.model;

import java.util.*;

/**
 * A special type of Employee that can also manage other employees.
 * <p>
 * Each Manager can manage many Employees, but each Employee can only have one direct Manager.
 */
public class Manager extends Employee {

    // Necessary fields
    private final List<Employee> employees = new ArrayList<>();

    /**
     * Creates a new manager
     *
     * @param id       the manager id
     * @param name     the manager's name
     * @param email    the manager's email address
     * @param jobTitle the manager's job title
     * @param salary   the manager's salary
     */
    public Manager(int id, String name, String email, String jobTitle, int salary) {
        super(id, name, email, jobTitle, salary);
    }

    /**
     * Gets all employees managed by this manager. The returned List must not be able to be directly modified. Investigate
     * the {@link Collections} class for info on how this might be achieved.
     *
     * @return an unmodifiable list of employees
     */
    public List<Employee> getEmployees() {
        // TODO complete this
        return employees;
    }

    /**
     * Sets up the given employee to be managed by this manager. In addition to adding the employee to this manager's
     * employees list, the method should remove it from its old manager's list, if any, and set the employee's manager
     * field appropriately.
     *
     * @param employee the employee to be managed by this manager
     * @throws NullPointerException if the employee is null
     */
    public void addEmployee(Employee employee) {
        // TODO complete this

        // Throw NullPointer exception if employee that be added is null
        if (employee == null) throw new NullPointerException("NullPointerException was expected");

        // Add employee to employees list if it is not exists in list
        if (!employees.contains(employee)) {
            employees.add(employee); // Add to list
            employee.setManager(this); // Just link employee's manger to this too
        }
    }

    /**
     * Removes the given employee from this manager. If the given employee isn't managed by this manager, this method
     * should do nothing.
     *
     * @param employee the employee to be removed
     * @throws NullPointerException if the employee is null
     */
    public void removeEmployee(Employee employee) {
        // TODO complete this
        // Throw NullPointer exception if removal needed employee is null
        if (employee == null){
            throw new NullPointerException("NullPointerException was expected");
        }

        // Remove employee If employee is in this employees list
        if (employees.contains(employee)){
            this.employees.remove(employee); // Remove from list
            employee.setManager(null); // Set manager of removed employee to null
        }
    }

    /**
     * Gets a list of all employees starting with this one, and including all descendants. This list should be
     * generated using pre-order traversal of the employment hierarchy.
     * <p>
     * For example, if this employee is "The Boss", and this is the boss's employment hierarchy:
     * <ul>
     *     <li>The Boss</li>
     *     <li><ul>
     *         <li>Anne</li>
     *         <li><ul>
     *             <li>Caitlin</li>
     *         </ul></li>
     *         <li>Bob</li>
     *         <li><ul>
     *             <li>Dave</li>
     *             <li>Eve</li>
     *         </ul></li>
     *     </ul></li>
     * </ul>
     * Then, this should be the list returned:
     * <ul>
     *     <li>The Boss</li>
     *     <li>Anne</li>
     *     <li>Caitlin</li>
     *     <li>Bob</li>
     *     <li>Dave</li>
     *     <li>Eve</li>
     * </ul>
     * <p>
     * See: <a href="https://www.tutorialspoint.com/data_structures_algorithms/tree_traversal.htm">tree traversal algorithms</a>
     *
     * @return a list containing this employee and all employees directly or indirectly managed by this employee
     */
    public List<Employee> getAllEmployees() {
        // TODO complete this
        List<Employee> allEmployees = new ArrayList<>();
        Iterator<Employee> i = employees.iterator();
        allEmployees.add(this); //Add this to list first

        while (i.hasNext()){
            Employee current = i.next();

            // If current is a manger, just call like a recursive function with current's getEmployees method
            // Bigger one will be added to the list first following with lower more
            if (current instanceof Manager){
                Manager manger = (Manager) current;
                allEmployees.addAll(manger.getAllEmployees()); // recursive
            } else { // If current is a leaf is just add it to list
                allEmployees.add(current);
            }
        }

        return allEmployees;
    }

    /**
     * Returns a value indicating whether the given object is equal to this one. The given object is considered equal if
     * it is non-null, is a Manager, its superclass equals returns true, and its employees list is equal to this object's
     * employees list.
     * HINT: a List's equals() method returns true if the other object is a List with the same elements in the same
     * order (i.e. you don't manually need a loop etc. to check for List equality).
     *
     * @param o the object to check
     * @return true if the object meets the conditions outlined above, false otherwise
     */
    // TODO override and implement equals()
    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }

        if (o instanceof Manager){
            Manager other = (Manager) o;
            return super.equals(o)
                    && Objects.equals(this.employees, other.getEmployees());
        }

        return false;
    }

}
