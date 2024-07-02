package polanieonline.client.panel.item;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;
import polanieonline.common.filter.NumericDocumentFilter;
import polanieonline.common.listener.SimpleActionListener;
import polanieonline.common.listener.SimpleDocumentListener;

public class AttributesSectionPanel extends SectionPanel {
	private ItemsPanel itemsPanel;

	private TitledBorder itemAttrPanelBorder;
	private JPanel itemAttrPanel;

	private JTextField defenseValueField;
	private JTextField offenseValueField;
	private JTextField attackSpeedField;
	private JTextField rangeField;

	private JComboBox<String> addAttrList;
	private List<String> addedAttributes = new ArrayList<>();

	private static final String[] DEFENSIVE_CATEGORIES = {
		"armor", "belts", "helmet", "cloak", "legs", "boots", "glove", "ring", "shield"
	};
	private static final String[] DEFAULT_DYNAMIC_ATTRIBUTES = {
		"minimum_usage_level", "minimum_equip_level", "maximum_upgrade_amount"
	};

	public static final Map<String, String> CATEGORY_MAP;
	static {
		CATEGORY_MAP = new HashMap<>();
		CATEGORY_MAP.put("Amunicja", "ammunition");
		CATEGORY_MAP.put("Zbroja", "armor");
		CATEGORY_MAP.put("Topór", "axe");
		CATEGORY_MAP.put("Pas", "belts");
		CATEGORY_MAP.put("Buty", "boots");
		CATEGORY_MAP.put("Płaszcz", "cloak");
		CATEGORY_MAP.put("Młot", "club");
		CATEGORY_MAP.put("Sztylet", "dagger");
		CATEGORY_MAP.put("Rękawice", "glove");
		CATEGORY_MAP.put("Hełm", "helmet");
		CATEGORY_MAP.put("Spodnie", "legs");
		CATEGORY_MAP.put("Zaklęcie magiczne", "magia");
		CATEGORY_MAP.put("Broń zasięgowa", "ranged");
		CATEGORY_MAP.put("Pierścień", "ring");
		CATEGORY_MAP.put("Tarcza", "shield");
		CATEGORY_MAP.put("Miecz", "sword");
		CATEGORY_MAP.put("Różdżka", "wand");
		CATEGORY_MAP.put("Bicz", "whip");
	}

	private static final Map<String, String> ATTRIBUTE_KEYS;
	static {
		ATTRIBUTE_KEYS = new HashMap<>();
		ATTRIBUTE_KEYS.put("minimum_usage_level", "Minimalny poziom używania");
		ATTRIBUTE_KEYS.put("minimum_equip_level", "Minimalny poziom założenia");
		ATTRIBUTE_KEYS.put("maximum_upgrade_amount", "Maksymalna ilość ulepszeń");
		ATTRIBUTE_KEYS.put("attack_speed", "Szybkość ataku");
		ATTRIBUTE_KEYS.put("defense_value", "Ilość defensywy");
		ATTRIBUTE_KEYS.put("offense_value", "Ilość ofensywy");
	}

	public AttributesSectionPanel(ItemsPanel itemsPanel, Locale locale, XMLConsole xmlConsole) {
		super(locale, xmlConsole);
		this.itemsPanel = itemsPanel;

		initUI();
	}

	protected String getPanelTitle() {
		return "attributes";
	}

	private void initUI() {
		setLayout(new BorderLayout());

		itemAttrPanel = new JPanel(new GridBagLayout());
		itemAttrPanelBorder = BorderFactory.createTitledBorder(getTitleBorder());
		itemAttrPanel.setBorder(itemAttrPanelBorder);

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(itemAttrPanel, BorderLayout.CENTER);

		add(leftPanel, BorderLayout.CENTER);

		updateAttributesPanel();
	}

	public void updateAttributesPanel() {
		itemAttrPanel.removeAll();
		addedAttributes.clear();

		// Dynamiczne atrybuty
		addAttrList = new JComboBox<>(new String[]{});
		JButton addAttributeButton = new JButton(getAttrButtonTitle());
		addAttributeButton.addActionListener(new SimpleActionListener(() -> addDynamicAttribute(), () -> updateXMLConsole()));
		JPanel addAttributePanel = new JPanel(new FlowLayout());
		addAttributePanel.add(addAttrList);
		addAttributePanel.add(addAttributeButton);
		addComponent(itemAttrPanel, addAttributePanel, 0, 0, 2, 1, GridBagConstraints.CENTER);

		String selectedCategory = itemsPanel.getSelectedItemClass();
		boolean isDefensive = isDefensiveCategory(selectedCategory);

		GridBagConstraints gbc = createGbc(0, 1);

		if (selectedCategory == null) {
			System.err.println("Selected category is null");
			return;
		}

		// Reset fields
		defenseValueField = null;
		offenseValueField = null;
		attackSpeedField = null;
		rangeField = null;

		if (isDefensive) {
			defenseValueField = createNumericTextField();
			addAttributeField("defense_value", defenseValueField, gbc);
			gbc.gridy++;
		} else {
			offenseValueField = createNumericTextField();
			addAttributeField("offense_value", offenseValueField, gbc);
			gbc.gridy++;

			if (ignoreTheseItemClasses(selectedCategory)) {
				attackSpeedField = createNumericTextField();
				addAttributeField("attack_speed", attackSpeedField, gbc);
				gbc.gridy++;
			}

			if (selectedCategory.equals(getLangKey("ammunition")) || selectedCategory.equals(getLangKey("ranged"))
					|| selectedCategory.equals(getLangKey("wand")) || selectedCategory.equals(getLangKey("magia")) || selectedCategory.equals(getLangKey("whip"))) {
				rangeField = createNumericTextField();
				addAttributeField("range", rangeField, gbc);
				gbc.gridy++;
			}
		}

		resetDynamicAttributes(); // Ensure dynamic attributes are reset and ready to be added
		itemAttrPanel.revalidate();
		itemAttrPanel.repaint();
		updateXMLConsole();
	}

	private boolean ignoreTheseItemClasses(String selectedCategory) {
		return !(selectedCategory.equals(getLangKey("ammunition")) || selectedCategory.equals(getLangKey("ranged"))
			|| selectedCategory.equals(getLangKey("wand")) || selectedCategory.equals(getLangKey("magia")));
	}

	private JTextField createNumericTextField() {
		JTextField textField = new JTextField(10);
		((AbstractDocument) textField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		return textField;
	}

	private void addAttributeField(String key, JTextField textField, GridBagConstraints gbc) {
		JLabel label = new JLabel(getLangKey(key) + ":");
		label.putClientProperty("attributeKey", key); // Store the attribute key for later retrieval
		label.putClientProperty("textField", textField); // Powiązanie textField z label
		addComponent(itemAttrPanel, label, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(itemAttrPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);
		textField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> updateXMLConsole()));
	}

	private void resetDynamicAttributes() {
		addAttrList.removeAllItems();

		// Dodanie specyficznych dynamicznych atrybutów dla określonych kategorii
		String selectedCategory = itemsPanel.getSelectedItemClass();
		if (selectedCategory != null) {
			String englishCategory = CATEGORY_MAP.get(selectedCategory);

			if (isDefensiveCategory(selectedCategory)) {
				addAttrList.addItem(getAtkName());
			}
			if (!isDefensiveCategory(selectedCategory) && !("ammunition".equals(englishCategory))) {
				addAttrList.addItem(getDefName());
			}

			if ("ranged".equals(englishCategory) || "wand".equals(englishCategory)) {
				addAttrList.addItem(getSpeedAttackName());
			}
		}

		for (String attr : DEFAULT_DYNAMIC_ATTRIBUTES) {
			addAttrList.addItem(getLangKey(attr));
		}

		itemAttrPanel.revalidate();
		itemAttrPanel.repaint();
	}

	public boolean isDefensiveCategory(String category) {
		for (String defensiveCategory : DEFENSIVE_CATEGORIES) {
			if (getLangKey(defensiveCategory).equals(category)) {
				return true;
			}
		}
		return false;
	}

	private void addDynamicAttribute() {
		String selectedAttribute = (String) addAttrList.getSelectedItem();
		String attributeKey = null;

		// Find the key corresponding to the selected attribute
		for (Map.Entry<String, String> entry : ATTRIBUTE_KEYS.entrySet()) {
			if (getLangKey(entry.getKey()).equals(selectedAttribute)) {
				attributeKey = entry.getKey();
				break;
			}
		}

		if (attributeKey == null || addedAttributes.contains(attributeKey)) {
			JOptionPane.showMessageDialog(this, getLangKey("error_duplicate_field"), getLangKey("error_title"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		addedAttributes.add(attributeKey);

		GridBagConstraints gbc = createGbc(0, itemAttrPanel.getComponentCount() / 2 + 1);
		JLabel label = new JLabel(selectedAttribute + ":");
		JTextField textField = createNumericTextField();
		label.putClientProperty("attributeKey", attributeKey); // Store the attribute key for later retrieval
		label.putClientProperty("textField", textField); // Powiązanie textField z label
		textField.putClientProperty("attributeKey", attributeKey); // Powiązanie key z textField
		addComponent(itemAttrPanel, label, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(itemAttrPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);
		textField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> updateXMLConsole()));

		itemAttrPanel.revalidate();
		itemAttrPanel.repaint();
		updateXMLConsole();
	}

	public boolean isDefensive() {
		String selectedCategory = (String) addAttrList.getSelectedItem();
		return isDefensiveCategory(selectedCategory);
	}

	public String getDynamicAttributeValue(String key) {
		for (Component comp : itemAttrPanel.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				Object attributeKey = label.getClientProperty("attributeKey");
				if (attributeKey != null && attributeKey.equals(key)) {
					JTextField textField = (JTextField) label.getClientProperty("textField");
					if (textField != null) {
						return textField.getText();
					}
				}
			} else if (comp instanceof JTextField) {
				JTextField textField = (JTextField) comp;
				Object attributeKey = textField.getClientProperty("attributeKey");
				if (attributeKey != null && attributeKey.equals(key)) {
					return textField.getText();
				}
			}
		}
		return "";
	}

	public List<String> getAddedAttributes() {
		return new ArrayList<>(addedAttributes);
	}
	
	public Map<String, String> getItemClassesMap() {
		return CATEGORY_MAP;
	}

	private String getAttrButtonTitle() {
		return getLangKey("add_attribute");
	}
	private String getAtkName() {
		return getLangKey("offense_value");
	}
	private String getDefName() {
		return getLangKey("defense_value");
	}
	private String getSpeedAttackName() {
		return getLangKey("attack_speed");
	}
	private String getRangeName() {
		return getLangKey("range");
	}

	public String getAtkValue() {
		return offenseValueField != null ? offenseValueField.getText() : "";
	}
	public String getDefValue() {
		return defenseValueField != null ? defenseValueField.getText() : "";
	}
	public String getRateValue() {
		return attackSpeedField != null ? attackSpeedField.getText() : "";
	}
	public String getRangeValue() {
		return rangeField != null ? rangeField.getText() : "";
	}

	public void refresh() {
		itemAttrPanelBorder.setTitle(getTitleBorder().getTitle());

		updateAttributesPanel();
		revalidate();
		repaint();
		updateXMLConsole();
	}
}
