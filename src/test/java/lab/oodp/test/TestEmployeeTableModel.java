package lab.oodp.test;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;
import lab.oodp.company.view.EmployeeTableAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.TableModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the EmployeeTableModelAdapter works correctly
 *
 */
public class TestEmployeeTableModel {

    private EmployeeTableAdapter tableModel;

    /**
     * Set up employment hierarchy and create adapter to test
     */
    @BeforeEach
    public void setUp() {
        Manager theBoss = new Manager(1, "The Boss", "boss@trex-sandwich.com", "CEO", 200000);

        Manager anne = new Manager(2, "Anne", "anne@trex-sandwich.com", "HR manager", 150000);
        Manager bob = new Manager(3, "Bob", "bob@trex-sandwich.com", "Tech lead", 150000);

        Employee caitlin = new Employee(4, "Caitlin", "caitlin@trex-sandwich.com", "Customer service", 60000);
        Employee dave = new Employee(5, "Dave", "dave@trex-sandwich.com", "Software engineer", 120000);
        Employee eve = new Employee(6, "Eve", "eve@trex-sandwich.com", "UX engineer", 120000);

        theBoss.addEmployee(anne);
        theBoss.addEmployee(bob);
        anne.addEmployee(caitlin);
        bob.addEmployee(dave);
        bob.addEmployee(eve);

        this.tableModel = new EmployeeTableAdapter(theBoss);
        assertTrue(tableModel instanceof TableModel);
    }

    /**
     * Tests whether getColumnCount() works correctly
     */
    @Test
    public void testGetColumnCount() {
        assertEquals(6, tableModel.getColumnCount());
    }

    /**
     * Tests whether getColumnName() works correctly
     */
    @Test
    public void testColumnName() {
        assertEquals("ID", tableModel.getColumnName(0));
        assertEquals("Name", tableModel.getColumnName(1));
        assertEquals("Email address", tableModel.getColumnName(2));
        assertEquals("Job title", tableModel.getColumnName(3));
        assertEquals("Reports to", tableModel.getColumnName(4));
        assertEquals("Salary", tableModel.getColumnName(5));
    }

    /**
     * Tests whether getRowCount() works correctly
     */
    @Test
    public void testGetRowCount() {
        assertEquals(6, tableModel.getRowCount());
    }

    /**
     * Tests whether getValueAt() works correctly
     */
    @Test
    public void testGetValueAt() {
        assertEquals("CEO", tableModel.getValueAt(0, 3));
        assertEquals("anne@trex-sandwich.com", tableModel.getValueAt(1, 2));
        assertEquals("Anne", tableModel.getValueAt(2, 4));
        assertEquals("Bob", tableModel.getValueAt(3, 1));
        assertEquals("$120000", tableModel.getValueAt(4, 5));
        assertEquals(6, tableModel.getValueAt(5, 0));
    }

}
