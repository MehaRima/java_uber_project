package gui.mainview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A console that prints some relevant information updates in the GUI (i.e., ride completions, or ride confirmations...).
 * @author Erwan
 *
 */
public class Console extends JPanel {
	
	private static final long serialVersionUID = -6386384294458306011L;
	

    public Console() {
    	JTextArea textarea = new JTextArea((int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/67), (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/20.5));
    	textarea.setBackground(Color.white);
    	textarea.setForeground(Color.black);
    	textarea.setEditable(false);
    	textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
    	textarea.setMargin(new Insets(0,10,0,0));
    	JScrollPane scroll = new JScrollPane(textarea);
    	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	this.add(scroll);
    	this.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/(5/3)), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2.5)));
    	this.setOpaque(false);
    	String title = "Information Console";
    	Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), title);
    	TitledBorder tborder = (TitledBorder) border;
    	this.setBorder(tborder);
    	PrintStream stream = new PrintStream(new CustomOutputStream(textarea));
    	System.setOut(stream);
    	System.setErr(stream);
    }

    
}
