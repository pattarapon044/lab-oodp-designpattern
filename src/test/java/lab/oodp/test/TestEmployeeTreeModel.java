package lab.oodp.test;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;
import lab.oodp.company.view.EmployeeTreeAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.tree.TreeModel;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmployeeTreeModel {

    private Manager theBoss;

    private Manager anne;
    private Manager bob;

    private Employee caitlin;
    private Employee dave;
    private Employee eve;

    private EmployeeTreeAdapter treeAdapter;

    /**
     * Set up employment hierarchy and create adapter instance to test
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

        this.treeAdapter = new EmployeeTreeAdapter(theBoss);
        assertTrue(treeAdapter instanceof TreeModel);
    }

    /**
     * Tests whether getRoot() returns the boss as expected
     */
    @Test
    public void testGetRoot() {
        assertSame(theBoss, treeAdapter.getRoot());
    }

    /**
     * Tests whether getChildCount() works as expected
     */
    @Test
    public void testGetChildCount() {
        assertEquals(2, treeAdapter.getChildCount(theBoss));
        assertEquals(0, treeAdapter.getChildCount(dave));
    }

    /**
     * Tests whether isLeaf() works as expected, in all three cases
     */
    @Test
    public void testIsLeaf() {

        // Employees which aren't Managers should always return true
        assertTrue(treeAdapter.isLeaf(dave));

        // Managers with employees should return false
        assertFalse(treeAdapter.isLeaf(bob));

        // Managers without employees should return true
        Manager managerWithNoEmployees = new Manager(10, "blah", "blah", "blah", 1);
        anne.addEmployee(managerWithNoEmployees);
        assertTrue(treeAdapter.isLeaf(managerWithNoEmployees));
    }

    /**
     * Tests whether getChild() works as expected
     */
    @Test
    public void testGetChild() {
        assertSame(anne, treeAdapter.getChild(theBoss, 0));
        assertSame(eve, treeAdapter.getChild(bob, 1));
    }

    /**
     * Tests whether getIndexOfChild() works as expected
     */
    @Test
    public void testGetIndexOfChild() {
        assertEquals(0, treeAdapter.getIndexOfChild(theBoss, anne));
        assertEquals(1, treeAdapter.getIndexOfChild(bob, eve));
        assertEquals(-1, treeAdapter.getIndexOfChild(theBoss, eve));
    }

}
