package polanieonline.client.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import polanieonline.common.language.LanguageSystem;
import java.util.Locale;

public class ClientGUI extends LanguageSystem {
	protected JFrame frame;

	public ClientGUI(Locale locale) {
		super(locale);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void refresh() {
		if (frame != null) {
			frame.setJMenuBar(createMenuBar());
			frame.revalidate();
			frame.repaint();
		}
	}

	// Placeholder methods to be overridden by subclasses
	public JPanel createMainPanel() {
		return new JPanel();
	}

	public JMenuBar createMenuBar() {
		return new JMenuBar();
	}
}