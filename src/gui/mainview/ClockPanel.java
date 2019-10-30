package gui.mainview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.eventlisteners.AdvanceFixedTimeListener;
import gui.eventlisteners.AdvanceInputTimeListener;
import helpers.Environment;

/**
 * Panel where the Clock is displayed and can be manually activated.
 * @author Erwan
 *
 */
public class ClockPanel extends JPanel {
	
	private static final long serialVersionUID = -6552830807117097574L;

	public ClockPanel() {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/9)));
		this.setOpaque(false);
		
		JButton plus15 = new JButton("+15mn");
		plus15.setFont(GUImain.getDefaultFont());
		plus15.addActionListener(new AdvanceFixedTimeListener());
		
		JLabel currentTime = new JLabel();
		currentTime.setText(Environment.getInstance().getClock().toString());
		currentTime.setFont(new Font("Arial", Font.BOLD, 35));
		currentTime.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
		currentTime.setForeground(Color.black);
		
		JLabel plussign = new JLabel("+");
		plussign.setFont(GUImain.getDefaultFont());
		
		JTextField customminutes = new JTextField(3);
		customminutes.setFont(GUImain.getDefaultFont());
		
		JLabel mn = new JLabel("mn");
		mn.setFont(GUImain.getDefaultFont());
		
		JButton gobutton = new JButton("Go !");
		gobutton.setFont(GUImain.getDefaultFont());
		gobutton.addActionListener(new AdvanceInputTimeListener(customminutes));
		
		this.add(plus15);
		this.add(currentTime);
		this.add(plussign);
		this.add(customminutes);
		this.add(mn);
		this.add(gobutton);
	}
	
}
