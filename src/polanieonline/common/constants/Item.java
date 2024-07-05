package polanieonline.common.constants;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import polanieonline.common.language.LanguageSystem;

public class Item {
	private static LanguageSystem lang;
	public static final Map<String, String> CLASS_KEYS = new HashMap<>();
	public static final Map<String, String> ATTRIBUTE_KEYS = new HashMap<>();

	public static final String[] DEFENSIVE_CLASS = {
		"armor", "belts", "helmet", "cloak", "legs", "boots", "glove", "ring", "shield"
	};
	public static final String[] DEFAULT_DYNAMIC_ATTRIBUTES = {
		"minimum_usage_level", "minimum_equip_level", "maximum_upgrade_amount"
	};

	static {
		// Inicjalizacja map za pomocą metody
		initKeys(Locale.getDefault());
	}

	private static void initKeys(Locale locale) {
		lang = new LanguageSystem(locale);

		// Item classes language keys
		CLASS_KEYS.put(lang.getLangKey("ammunition"), "ammunition");
		CLASS_KEYS.put(lang.getLangKey("armor"), "armor");
		CLASS_KEYS.put(lang.getLangKey("axe"), "axe");
		CLASS_KEYS.put(lang.getLangKey("belts"), "belts");
		CLASS_KEYS.put(lang.getLangKey("boots"), "boots");
		CLASS_KEYS.put(lang.getLangKey("cloak"), "cloak");
		CLASS_KEYS.put(lang.getLangKey("club"), "club");
		CLASS_KEYS.put(lang.getLangKey("dagger"), "dagger");
		CLASS_KEYS.put(lang.getLangKey("glove"), "glove");
		CLASS_KEYS.put(lang.getLangKey("helmet"), "helmet");
		CLASS_KEYS.put(lang.getLangKey("legs"), "legs");
		CLASS_KEYS.put(lang.getLangKey("magia"), "magia");
		CLASS_KEYS.put(lang.getLangKey("ranged"), "ranged");
		CLASS_KEYS.put(lang.getLangKey("ring"), "ring");
		CLASS_KEYS.put(lang.getLangKey("shield"), "shield");
		CLASS_KEYS.put(lang.getLangKey("sword"), "sword");
		CLASS_KEYS.put(lang.getLangKey("wand"), "wand");
		CLASS_KEYS.put(lang.getLangKey("whip"), "whip");

		// Attr language keys
		ATTRIBUTE_KEYS.put(lang.getLangKey("minimum_usage_level"), "minimum_usage_level");
		ATTRIBUTE_KEYS.put(lang.getLangKey("minimum_equip_level"), "minimum_equip_level");
		ATTRIBUTE_KEYS.put(lang.getLangKey("maximum_upgrade_amount"), "maximum_upgrade_amount");
		ATTRIBUTE_KEYS.put(lang.getLangKey("attack_speed"), "attack_speed");
		ATTRIBUTE_KEYS.put(lang.getLangKey("defense_value"), "defense_value");
		ATTRIBUTE_KEYS.put(lang.getLangKey("offense_value"), "offense_value");
		ATTRIBUTE_KEYS.put(lang.getLangKey("lifesteal"), "lifesteal");
	}
}
