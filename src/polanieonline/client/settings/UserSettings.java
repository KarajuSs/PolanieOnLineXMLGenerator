package polanieonline.client.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class UserSettings {
	private static final String SETTINGS_FILE = "settings.properties";
	private Properties properties;

	public UserSettings() {
		properties = new Properties();
		loadSettings();
	}

	public void loadSettings() {
		try (FileInputStream in = new FileInputStream(SETTINGS_FILE)) {
			properties.load(in);
		} catch (IOException e) {
			// Plik nie istnieje lub wystąpił błąd podczas ładowania
		}
	}

	public void saveSettings() {
		try (FileOutputStream out = new FileOutputStream(SETTINGS_FILE)) {
			properties.store(out, "User Settings");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Locale getLocale() {
		String language = properties.getProperty("language", "en");
		return new Locale(language);
	}

	public void setLocale(Locale locale) {
		properties.setProperty("language", locale.getLanguage());
	}
}