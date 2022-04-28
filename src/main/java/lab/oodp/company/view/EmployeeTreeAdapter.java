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
        if (parent instanceof Manager){
            Manager manager = (Manager) parent;
            List<Employee> employees = manager.getEmployees();
            return employees.get(index);
        }

        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        // TODO complete this
        if (parent instanceof Manager){
            Manager manger = (Manager) parent;
            return manger.getEmployees().size();
        }

        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        // TODO complete this

        if (node instanceof Manager) {
            Manager manager = (Manager) node;
            return !Objects.equals(manager, root) && manager.getEmployees().isEmpty();
        }

        return node instanceof Employee;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        // TODO complete this

        if (parent instanceof Manager){
            Manager manger = (Manager) parent;
            Iterator<Employee> i = manger.getEmployees().iterator();

            int index = 0;
            while (i.hasNext()){
                Employee current = i.next();
                if (Objects.equals(current, child)){
                    return index;
                }
                index++;
            }
        }

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
