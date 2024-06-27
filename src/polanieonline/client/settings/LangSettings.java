package polanieonline.client.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import polanieonline.client.gui.ClientGUI;
import polanieonline.client.gui.ClientMenu;
import polanieonline.client.gui.ClientPanel;

public class LangSettings extends ClientGUI {
	private ClientMenu clientMenu;
	private ClientPanel clientPanel;

	public LangSettings(Locale locale, ClientMenu clientMenu, ClientPanel clientPanel) {
		super(locale);
		this.clientMenu = clientMenu;
		this.clientPanel = clientPanel;
	}

	public void show() {
		JDialog languagesDialog = new JDialog();
		languagesDialog.setTitle(getWord("languages"));
		languagesDialog.setSize(400, 300);
		languagesDialog.setModal(true);
		languagesDialog.setLocationRelativeTo(null);

		// Dodanie przykładowych opcji do okna dialogowego "Languages"
		JPanel panel = new JPanel();
		panel.add(new JLabel(getWord("choose_language")));
		String[] languages = {"English", "Español", "Polski"};
		JComboBox<String> languageComboBox = new JComboBox<>(languages);
		panel.add(languageComboBox);
		
		JButton applyButton = new JButton(getWord("apply"));
		panel.add(applyButton);

		languagesDialog.add(panel);

		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Locale selectedLocale = null;
				String selectedLanguage = (String) languageComboBox.getSelectedItem();
				if (selectedLanguage.equals("English")) {
					selectedLocale = new Locale("en");
				} else if (selectedLanguage.equals("Español")) {
					selectedLocale = new Locale("es");
				} else if (selectedLanguage.equals("Polski")) {
					selectedLocale = new Locale("pl");
				}

				if (selectedLocale != null) {
					setLocale(selectedLocale);
					clientMenu.setLocale(selectedLocale);
					clientPanel.setLocale(selectedLocale);
					clientPanel.refreshComponents();
				}

				languagesDialog.dispose();
				refresh();
			}
		});

		languagesDialog.setVisible(true);
	}

	@Override
	public JPanel createMainPanel() {
		return new JPanel(); // Placeholder, this method won't be used in this context
	}

	@Override
	public JMenuBar createMenuBar() {
		return new JMenuBar(); // Placeholder, this method won't be used in this context
	}
}