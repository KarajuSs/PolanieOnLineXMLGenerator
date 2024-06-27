package polanieonline.client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import polanieonline.client.gui.ClientMenu;
import polanieonline.client.gui.ClientPanel;
import polanieonline.client.settings.LangSettings;
import polanieonline.client.settings.UserSettings;

public class viewClient {
	public static void main(String[] args) {
		UserSettings userSettings = new UserSettings();
		Locale userLocale = userSettings.getLocale();

		// Utworzenie głównego okna
		JFrame frame = new JFrame("Entities XML struct generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setMinimumSize(new Dimension(800, 600));

		// Załaduj ikonę
		try {
			Image icon = ImageIO.read(new File("data/gui/icon.png"));
			frame.setIconImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Utworzenie obiektu MainMenu i dodanie menu bar do okna
		ClientMenu clientMenu = new ClientMenu(userLocale);
		clientMenu.setFrame(frame);
		frame.setJMenuBar(clientMenu.createMenuBar());

		// Utworzenie obiektu MainPanel i dodanie do okna
		ClientPanel clientPanel = new ClientPanel(userLocale);
		frame.getContentPane().add(clientPanel.createMainPanel());
		
		// Utworzenie obiektu LangSettings i dodanie do ClientMenu
		LangSettings langSettings = new LangSettings(userLocale, clientMenu, clientPanel);
		clientMenu.setLangSettings(langSettings);

		// Ustawienie okna jako widocznego
		frame.setVisible(true);

		// Zapis ustawień przed zamknięciem aplikacji
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				userSettings.setLocale(clientMenu.getLocale());
				userSettings.saveSettings();
			}
		});
	}
}