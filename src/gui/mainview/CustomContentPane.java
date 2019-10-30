package gui.mainview;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * The background of the mainframe of the GUI.
 * @author Erwan
 *
 */
public class CustomContentPane extends JPanel {
	
	private static final long serialVersionUID = -472367215415136053L;

	private Image bgImage;
	
	public CustomContentPane() throws IOException {
		this.bgImage = ImageIO.read(new File("src/resources/main_bg.png"));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // Draw the background image.
	    g.drawImage(bgImage, 0, 0, this);
	  }


	public Image getBgImage() {
		return bgImage;
	}

	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
	}

}
