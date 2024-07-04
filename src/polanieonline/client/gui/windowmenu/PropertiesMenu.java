package polanieonline.client.gui.windowmenu;

import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import polanieonline.common.language.LanguageSystem;

public class PropertiesMenu extends LanguageSystem {
	public PropertiesMenu(Locale locale) {
		super(locale);
	}

	public void show() {
		JDialog propertiesDialog = new JDialog();
		propertiesDialog.setTitle(getLangKey("properties"));
		propertiesDialog.setSize(400, 300);
		propertiesDialog.setModal(true);
		propertiesDialog.setLocationRelativeTo(null);

		// Dodanie przykładowych opcji do okna dialogowego "Properties"
		JPanel panel = new JPanel();
		panel.add(new JLabel("Adjust properties:"));
		JCheckBox checkBox1 = new JCheckBox("Enable feature A");
		JCheckBox checkBox2 = new JCheckBox("Enable feature B");
		panel.add(checkBox1);
		panel.add(checkBox2);

		propertiesDialog.add(panel);
		propertiesDialog.setVisible(true);
	}
}
