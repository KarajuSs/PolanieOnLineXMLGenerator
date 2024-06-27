package polanieonline.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JFrame;

import polanieonline.client.gui.MainMenu;
import polanieonline.client.settings.UserSettings;

public class viewClient {
	public static void main(String[] args) {
		UserSettings userSettings = new UserSettings();
		Locale userLocale = userSettings.getLocale();

		// Utworzenie głównego okna
		JFrame frame = new JFrame("Entities XML struct generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setMinimumSize(new java.awt.Dimension(800, 600));

		// Utworzenie obiektu MainMenu i dodanie menu bar do okna
		MainMenu mainMenu = new MainMenu(userLocale);
		mainMenu.setFrame(frame);
		frame.setJMenuBar(mainMenu.createMenuBar());

		// Ustawienie okna jako widocznego
		frame.setVisible(true);

		// Zapis ustawień przed zamknięciem aplikacji
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				userSettings.setLocale(mainMenu.getLocale());
				userSettings.saveSettings();
			}
		});
	}
}