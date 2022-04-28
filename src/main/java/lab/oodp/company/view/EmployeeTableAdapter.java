package lab.oodp.company.view;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableAdapter extends AbstractTableModel {

    private final Manager boss;
    String[] columnsName;

    public EmployeeTableAdapter(Manager boss) {
        // TODO complete this
        this.boss = boss;
        this.columnsName = new String[]{"ID", "Name", "Email address", "Job title", "Reports to", "Salary"};
    }

    @Override
    public int getRowCount() {
        // TODO complete this
        return boss.getAllEmployees().size();
    }

    @Override
    public int getColumnCount() {
        // TODO complete this
        return columnsName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO complete this
        Employee valueOwner = boss.getAllEmployees().get(rowIndex);
        switch (columnIndex){
            case 0: return valueOwner.getId();
            case 1: return valueOwner.getName();
            case 2: return valueOwner.getEmail();
            case 3: return valueOwner.getJobTitle();
            case 4: return valueOwner.getManager() == null ? "N/A" : valueOwner.getManager().getName();
            case 5: return "$" + valueOwner.getSalary();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        // TODO complete this
        return columnsName[column];
    }

    // *** Exercise Four below this point ***
    // TODO (optional) override and implement isCellEditable(), setValueAt()

}
