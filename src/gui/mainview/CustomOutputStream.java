package gui.mainview;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * A custom OutputStream to redirect the System outputs from the program to a console within the GUI.
 * @author Erwan
 *
 */
public class CustomOutputStream extends OutputStream {
	
	private JTextArea textArea;
    
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
