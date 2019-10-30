package gui.mainview;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 2497284889828941450L;

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
