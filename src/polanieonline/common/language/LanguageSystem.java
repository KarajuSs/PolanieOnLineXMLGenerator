package polanieonline.common.language;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LanguageSystem {
	private Locale locale;
	private ResourceBundle messages;

	public LanguageSystem(Locale locale) {
		setLocale(locale);
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
		String basePath = "data/languages/";
		try {
			String bundlePath = basePath + "lang_" + locale.getLanguage() + ".properties";
			messages = new PropertyResourceBundle(new FileInputStream(bundlePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Locale getLocale() {
		return locale;
	}

	public String getWord(String key) {
		return messages.getString(key);
	}
}
