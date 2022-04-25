package lab.oodp.company.view;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class EmployeeTreeAdapter implements TreeModel {


    public EmployeeTreeAdapter(Employee employee) {
        // TODO complete this
    }

    @Override
    public Object getRoot() {
        // TODO complete this
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        // TODO complete this
        return null;
    }

    @Override
    public int getChildCount(Object parent) {
        // TODO complete this
        return 0;
    }

    @Override
    public boolean isLeaf(Object node) {
        // TODO complete this
        return false;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        // TODO complete this
        return 0;
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
