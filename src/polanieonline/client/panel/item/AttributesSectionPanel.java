package polanieonline.client.panel.item;

import static polanieonline.common.constants.Item.ATTRIBUTE_KEYS;
import static polanieonline.common.constants.Item.CLASS_KEYS;
import static polanieonline.common.constants.Item.DEFAULT_DYNAMIC_ATTRIBUTES;
import static polanieonline.common.constants.Item.DEFENSIVE_CLASS;
import static polanieonline.common.constants.Nature.ELEMENTS_KEYS;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

	private JCheckBox natureTypeCheckBox;
	private JComboBox<String> natureTypeComboBox;

	private JComboBox<String> addAttrList;
	private List<String> addedAttributes = new ArrayList<>();

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
		natureTypeCheckBox = null;
		natureTypeComboBox = null;

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

			// Dodanie typu natury dla przedmiotów ofensywnych
			JLabel natureTypeLabel = new JLabel(getLangKey("nature_type") + ":");
			natureTypeCheckBox = new JCheckBox();
			natureTypeCheckBox.addActionListener(e -> toggleNatureTypeField());

			JPanel natureTypePanel = new JPanel(new FlowLayout());
			natureTypePanel.add(natureTypeCheckBox);
			natureTypePanel.add(natureTypeLabel);

			addComponent(itemAttrPanel, natureTypePanel, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);

			natureTypeComboBox = new JComboBox<>(itemsPanel.getTranslationNatureTypes());
			natureTypeComboBox.setEnabled(false);
			natureTypeComboBox.addActionListener(e -> updateXMLConsole());

			gbc.gridx++;
			addComponent(itemAttrPanel, natureTypeComboBox, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.EAST);
			gbc.gridy++;
		}

		resetDynamicAttributes(); // Ensure dynamic attributes are reset and ready to be added
		itemAttrPanel.revalidate();
		itemAttrPanel.repaint();
		updateXMLConsole();
	}

	private void toggleNatureTypeField() {
		boolean selected = natureTypeCheckBox.isSelected();
		natureTypeComboBox.setEnabled(selected);
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
			String englishCategory = CLASS_KEYS.get(selectedCategory);

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
		for (String defensiveCategory : DEFENSIVE_CLASS) {
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
			if (getLangKey(entry.getValue()).equals(selectedAttribute)) {
				attributeKey = entry.getValue();
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
		return CLASS_KEYS;
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

	public String getSelectedNatureType() {
		if (natureTypeCheckBox != null && natureTypeCheckBox.isSelected() && natureTypeComboBox != null) {
			String selectedNature = (String) natureTypeComboBox.getSelectedItem();
			return ELEMENTS_KEYS.get(selectedNature); // Return the key from the translation
		}
		return null;
	}

	public void refresh() {
		itemAttrPanelBorder.setTitle(getTitleBorder().getTitle());

		updateAttributesPanel();
		revalidate();
		repaint();
		updateXMLConsole();
	}
}
