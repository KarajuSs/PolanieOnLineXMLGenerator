package polanieonline.common.constants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import polanieonline.common.language.LanguageSystem;

public class Nature {
	private static LanguageSystem lang;

	public static final Map<String, String> ELEMENTS_KEYS = new HashMap<>();
	static {
		initKeys(Locale.getDefault());
	}

	private static void initKeys(Locale locale) {
		lang = new LanguageSystem(locale);

		ELEMENTS_KEYS.put(lang.getLangKey("earth"), "earth");
		ELEMENTS_KEYS.put(lang.getLangKey("ice"), "ice");
		ELEMENTS_KEYS.put(lang.getLangKey("water"), "water");
		ELEMENTS_KEYS.put(lang.getLangKey("fire"), "fire");
		ELEMENTS_KEYS.put(lang.getLangKey("light"), "light");
		ELEMENTS_KEYS.put(lang.getLangKey("dark"), "dark");
	}
}
