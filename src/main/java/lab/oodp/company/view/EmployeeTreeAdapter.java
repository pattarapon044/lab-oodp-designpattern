package lab.oodp.company.view;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class EmployeeTreeAdapter implements TreeModel {

    private final Employee root;

    public EmployeeTreeAdapter(Employee employee) {
        // TODO complete this
        this.root = employee;
    }

    @Override
    public Object getRoot() {
        // TODO complete this
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        // TODO complete this
        // Can get child only parent is a manager
        if (parent instanceof Manager){
            Manager manager = (Manager) parent;
            List<Employee> employees = manager.getEmployees();
            return employees.get(index);
        }

        // parent is not a manager's object
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        // TODO complete this
        // Can count child only parent is a manger
        if (parent instanceof Manager){
            Manager manger = (Manager) parent;
            return manger.getEmployees().size(); // manager's employees count
        }

        // parent is not manger's object
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        // TODO complete this
        // node is a leaf when
        // 1. node is not root
        // 2. node is manger but have no employee to manage
        // 3. node is employee
        if (node instanceof Manager) {
            Manager manager = (Manager) node;
            return !Objects.equals(manager, root) && manager.getEmployees().isEmpty();
        }

        // node is employee or other object
        return node instanceof Employee;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        // TODO complete this
        // parent can get child when it is a manger
        if (parent instanceof Manager){
            Manager manger = (Manager) parent;

            // loop check which index is equals child
            int index = 0;
            for (Employee current : manger.getEmployees()) {
                if (Objects.equals(current, child)) return index; //return current index if found
                index++; // If not found increase index by 1 and check again
            }
        }

        // Return -1 if parent is not manger, or can not found a child in manager's employees
        return -1;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // Unused
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        // Unused
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        // Unused
    }
}
