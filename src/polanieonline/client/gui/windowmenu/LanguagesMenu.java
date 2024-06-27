package polanieonline.client.gui.windowmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import polanieonline.client.gui.MainMenu;
import polanieonline.common.language.LanguageSystem;

public class LanguagesMenu extends LanguageSystem {
	private MainMenu mainMenu;

	public LanguagesMenu(Locale locale, MainMenu mainMenu) {
		super(locale);
		this.mainMenu = mainMenu;
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
					mainMenu.setLocale(selectedLocale);
				}

				languagesDialog.dispose();
				mainMenu.updateMenu();
			}
		});

		languagesDialog.setVisible(true);
	}
}