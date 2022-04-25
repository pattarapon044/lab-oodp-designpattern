package lab.oodp.test;

import lab.oodp.test.TestReflectionUtils;
import lab.oodp.company.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class unit tests the {@link Employee} and {@link Manager} classes.
 *
 */
public class TestEmployeeAndManager {

    private Manager theBoss;

    private Manager anne;
    private Manager bob;

    private Employee caitlin;
    private Employee dave;
    private Employee eve;

    /**
     * Creates company hierarchy. The Boss has Anne and Bob under them. Anne in turn manages Caitlin, while Bob
     * manages Dave and Eve.
     */
    @BeforeEach
    public void setUp() {
        theBoss = new Manager(1, "The Boss", "boss@trex-sandwich.com", "CEO", 200000);

        anne = new Manager(2, "Anne", "anne@trex-sandwich.com", "HR manager", 150000);
        bob = new Manager(3, "Bob", "bob@trex-sandwich.com", "Tech lead", 150000);

        caitlin = new Employee(4, "Caitlin", "caitlin@trex-sandwich.com", "Customer service", 60000);
        dave = new Employee(5, "Dave", "dave@trex-sandwich.com", "Software engineer", 120000);
        eve = new Employee(6, "Eve", "eve@trex-sandwich.com", "UX engineer", 120000);

        theBoss.addEmployee(anne);
        theBoss.addEmployee(bob);

        anne.addEmployee(caitlin);

        bob.addEmployee(dave);
        bob.addEmployee(eve);
    }

    /**
     * Tests getters, such as getId. The test is written this way so I can make the unit test compile and run, even when
     * the methods haven't yet been typed out (i.e. I can try to call methods which may not even exist, and have the
     * program still compile).
     * <p>
     * For interest, see: https://www.geeksforgeeks.org/reflection-in-java/
     */
    @Test
    public void testGetters() {

        assertEquals(1, (Integer)TestReflectionUtils.callMethod(theBoss, "getId"));
        assertEquals("The Boss", TestReflectionUtils.callMethod(theBoss, "getName"));
        assertEquals("boss@trex-sandwich.com", TestReflectionUtils.callMethod(theBoss, "getEmail"));
        assertEquals("CEO", TestReflectionUtils.callMethod(theBoss, "getJobTitle"));
        assertEquals(200000, (Integer) TestReflectionUtils.callMethod(theBoss, "getSalary"));

        assertEquals(6, (Integer) TestReflectionUtils.callMethod(eve, "getId"));
        assertEquals("Eve", TestReflectionUtils.callMethod(eve, "getName"));
        assertEquals("eve@trex-sandwich.com", TestReflectionUtils.callMethod(eve, "getEmail"));
        assertEquals("UX engineer", TestReflectionUtils.callMethod(eve, "getJobTitle"));
        assertEquals(120000, (Integer) TestReflectionUtils.callMethod(eve, "getSalary"));

    }

    /**
     * Tests whether the toString() method returns the correct value
     */
    @Test
    public void testToString() {
        assertEquals("The Boss (email: boss@trex-sandwich.com)", theBoss.toString());
        assertEquals("Dave (email: dave@trex-sandwich.com)", dave.toString());
    }

    /**
     * Tests whether the equals() and hashCode() methods return the expected values.
     */
    @Test
    public void testEquals() {

        // m1 and m2 have all the same properties, and should thus be considered equal.
        // m3 - m7 each differ from m1 by a single property, and thus none of them should be considered equal.
        Manager m1 = new Manager(1, "M1", "m1@trex-sandwich.com", "CEO", 200000);
        Manager m2 = new Manager(1, "M1", "m1@trex-sandwich.com", "CEO", 200000);
        Manager m3 = new Manager(2, "M1", "m1@trex-sandwich.com", "CEO", 200000);
        Manager m4 = new Manager(1, "M4", "m1@trex-sandwich.com", "CEO", 200000);
        Manager m5 = new Manager(1, "M1", "m4@trex-sandwich.com", "CEO", 200000);
        Manager m6 = new Manager(1, "M1", "m1@trex-sandwich.com", "CEO1", 200000);
        Manager m7 = new Manager(1, "M1", "m1@trex-sandwich.com", "CEO", 200001);

        // Check for equality as described above
        assertEquals(m1, m2);
        assertNotEquals(m1, m3);
        assertNotEquals(m1, m4);
        assertNotEquals(m1, m5);
        assertNotEquals(m1, m6);
        assertNotEquals(m1, m7);

        // Where equals() returns true, two objects' hash codes should be the same. Where equals() returns false, two
        // objects' hash codes should be different.s
     }

    /**
     * Tests whether each Manager contains all expected employees according to the hierarchy defined in the setUp() method.
     */
    @Test
    public void testEmploymentHierarchyTopDown() {

        List<Employee> bossEmployees = theBoss.getEmployees();
        assertNotNull(bossEmployees);
        assertEquals(2, bossEmployees.size());
        assertSame(anne, bossEmployees.get(0));
        assertSame(bob, bossEmployees.get(1));

        List<Employee> anneEmployees = anne.getEmployees();
        assertNotNull(anneEmployees);
        assertEquals(1, anneEmployees.size());
        assertSame(caitlin, anneEmployees.get(0));

        List<Employee> bobEmployees = bob.getEmployees();
        assertNotNull(bobEmployees);
        assertEquals(2, bobEmployees.size());
        assertSame(dave, bobEmployees.get(0));
        assertSame(eve, bobEmployees.get(1));

    }

    /**
     * Tests whether each Employee's getManager() method returns the expected value according to the hierarchy defined
     * in the setUp() method.
     */
    @Test
    public void testEmploymentHierarchyBottomUp() {

        assertNull(theBoss.getManager());

        assertSame(theBoss, anne.getManager());
        assertSame(theBoss, bob.getManager());

        assertSame(anne, caitlin.getManager());

        assertSame(bob, dave.getManager());
        assertSame(bob, eve.getManager());

    }

    /**
     * Tests whether the getReportingChain() method works correctly in the case that the employee in question
     * has no manager
     */
    @Test
    public void testReportingChain_root() {
        List<Employee> chain = theBoss.getReportingChain();
        assertEquals(1, chain.size());
        assertSame(theBoss, chain.get(0));
    }

    /**
     * Tests whether the getReportingChain() method works correctly in the case that the employee in question
     * has a manager, but that manager has no manager itself
     */
    @Test
    public void testReportingChain_oneLevel() {
        List<Employee> chain = bob.getReportingChain();
        assertEquals(2, chain.size());
        assertSame(theBoss, chain.get(0));
        assertSame(bob, chain.get(1));
    }

    /**
     * Tests whether the getReportingChain() method works correctly in the case that the employee in question
     * has a manager, and that manager also has a manager
     */
    @Test
    public void testReportingChain_twoLevels() {
        List<Employee> chain = eve.getReportingChain();
        assertEquals(3, chain.size());
        assertSame(theBoss, chain.get(0));
        assertSame(bob, chain.get(1));
        assertSame(eve, chain.get(2));
    }

    /**
     * Tests whether the getAllEmployees() method works as intented. That is, when called on the root manager (i.e.
     * "The Boss" in this case), it will return all employees in the company, using pre-order traversal.
     */
    @Test
    public void testGetAllEmployees() {
        List<Employee> all = theBoss.getAllEmployees();
        assertEquals(6, all.size());
        assertSame(theBoss, all.get(0));
        assertSame(anne, all.get(1));
        assertSame(caitlin, all.get(2));
        assertSame(bob, all.get(3));
        assertSame(dave, all.get(4));
        assertSame(eve, all.get(5));
    }

    /**
     * Tests whether adding a new employee works correctly.
     */
    @Test
    public void testAddNewEmployee() {

        Employee faith = new Employee(7, "Faith", "faith@trex-sandwich.com", "Customer service", 63000);
        anne.addEmployee(faith);

        assertSame(anne, faith.getManager());

        assertEquals(2, anne.getEmployees().size());
        assertEquals(caitlin, anne.getEmployees().get(0));
        assertEquals(faith, anne.getEmployees().get(1));
    }

    /**
     * Tests whether a NullPointerException is appropriately thrown when trying to add a null employee. Also checks
     * that the manager's employment list wasn't incorrectly modified.
     */
    @Test
    public void testAddNull() {
        try {
            theBoss.addEmployee(null);
            fail("NullPointerException was expected");

        } catch (NullPointerException e) {
            assertEquals(2, theBoss.getEmployees().size());
            assertSame(anne, theBoss.getEmployees().get(0));
            assertSame(bob, theBoss.getEmployees().get(1));
        }
    }

    /**
     * Tests whether adding an employee with an existing manager will correctly re-assign that employee to the new
     * manager.
     */
    @Test
    public void testReassignExistingEmployee() {

        anne.addEmployee(eve);

        assertSame(anne, eve.getManager());
        assertEquals(2, anne.getEmployees().size());
        assertEquals(caitlin, anne.getEmployees().get(0));
        assertEquals(eve, anne.getEmployees().get(1));

        assertEquals(1, bob.getEmployees().size());
        assertEquals(dave, bob.getEmployees().get(0));
    }

    /**
     * Tests whether removing an employee works correctly.
     */
    @Test
    public void testRemoveEmployee() {
        theBoss.removeEmployee(anne);

        assertEquals(1, theBoss.getEmployees().size());
        assertSame(bob, theBoss.getEmployees().get(0));

        assertNull(anne.getManager());
    }

    /**
     * Tests whether removing an employee from a manager, where that manager wasn't managing that employee to begin with,
     * functions correctly (i.e. alters neither the manager's employee list nor the employee's manager field).
     */
    @Test
    public void testRemoveWrongEmployee() {
        theBoss.removeEmployee(dave);

        assertEquals(2, theBoss.getEmployees().size());
        assertSame(anne, theBoss.getEmployees().get(0));
        assertSame(bob, theBoss.getEmployees().get(1));

        assertSame(bob, dave.getManager());
    }

    /**
     * Tests whether a NullPointerException is appropriately thrown when trying to remove a null employee, and that the
     * manager's employee list isn't incorrectly modified.
     */
    @Test
    public void testRemoveNull() {
        try {
            theBoss.removeEmployee(null);
            fail("NullPointerException was expected");

        } catch (NullPointerException e) {
            assertEquals(2, theBoss.getEmployees().size());
            assertSame(anne, theBoss.getEmployees().get(0));
            assertSame(bob, theBoss.getEmployees().get(1));
        }
    }

}
