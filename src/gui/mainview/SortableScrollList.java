package gui.mainview;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import gui.eventlisteners.SortListsListener;

/**
 * A Custom Panel used to display the sorted lists of Cars, Drivers, etc.
 * @author Erwan
 *
 */
public class SortableScrollList extends JPanel {
	
	JTable table;
	
	JComboBox<String> sortbox;

	private static final long serialVersionUID = -4342647875511947200L;

	@SuppressWarnings("serial")
	public SortableScrollList(Object[][] data, String[] columnnames, String toplabel, String[] sortpolicies) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setOpaque(false);
		
		JPanel subpanel = new JPanel(new FlowLayout());
		JLabel sortby = new JLabel(toplabel);
		String[] sortPolicies = sortpolicies;
		this.sortbox = new JComboBox<String>(sortPolicies);
		this.sortbox.addActionListener(new SortListsListener());
		subpanel.setOpaque(false);
		subpanel.add(sortby);
		subpanel.add(sortbox);
		
		table = new JTable(data, columnnames) {
			public boolean isCellEditable(int row,int column){
				return false;  
			}
		};
		table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);
    	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	this.add(subpanel);
    	this.add(scroll);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JComboBox<String> getSortbox() {
		return sortbox;
	}

	public void setSortbox(JComboBox<String> sortbox) {
		this.sortbox = sortbox;
	}
	
	
	
	
}
