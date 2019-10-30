package gui.mainview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import gui.dialogs.AddCarDriverDialog;
import gui.dialogs.AddCustomerDialog;
import gui.dialogs.AddDriverDialog;
import gui.dialogs.CustomSetupDialog;
import gui.dialogs.HelpDialog;

/**
 * Main menu bar of the user interface, that lays at the top of the frame.
 * @author Erwan
 *
 */
public class TopMenuBar extends JMenuBar {

	private static final long serialVersionUID = -5511353684955783810L;

	
	public TopMenuBar() {
		JMenu setup = new JMenu("Setup");
		JMenu addobj = new JMenu("Add Objects");
		JMenu help = new JMenu("Help");
		
		JMenuItem addcustomer = new JMenuItem("Add Customer");
		JMenuItem addcardriver = new JMenuItem("Add Car + Driver");
		JMenuItem adddriver = new JMenuItem("Add Driver to existing Car");

		MouseListener setuplistener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				new CustomSetupDialog();
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

	    };
		setup.addMouseListener(setuplistener);
		
		ActionListener addcustomerlistener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        new AddCustomerDialog();
		    }
		};
		addcustomer.addActionListener(addcustomerlistener);
		
		ActionListener addcardriverlistener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        new AddCarDriverDialog();
		    }
		};
		addcardriver.addActionListener(addcardriverlistener);
		
		ActionListener adddriverlistener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        new AddDriverDialog();
		    }
		};
		adddriver.addActionListener(adddriverlistener);
		
		MouseListener helplistener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				new HelpDialog();
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

	    };
		help.addMouseListener(helplistener);
		
		
		addobj.add(addcustomer);
		addobj.add(addcardriver);
		addobj.add(adddriver);
		
		this.add(setup);
		this.add(addobj);
		this.add(help);
	}
	
}
