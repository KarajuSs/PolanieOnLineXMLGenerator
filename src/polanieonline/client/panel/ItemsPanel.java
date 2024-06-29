package polanieonline.client.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import polanieonline.client.console.XMLConsole;
import polanieonline.common.language.LanguageSystem;

public class ItemsPanel extends JPanel {
	private JTextField itemNameField;
	private JComboBox<String> itemCategoryComboBox;
	private JTextField itemSubclassField;
	private JTextArea itemDescriptionArea;
	private JPanel attributesPanel;
	private JTextField defenseValueField;
	private JTextField offenseValueField;
	private JTextField attackSpeedField;
	private JTextField rangeField;
	private JComboBox<String> addAttributeComboBox;
	private List<String> addedAttributes;
	private JLabel imageLabel;
	private JLabel imageNameLabel;
	private LanguageSystem languageSystem;

	private JLabel itemNameLabel;
	private JLabel itemCategoryLabel;
	private JLabel uploadImageLabel;
	private JLabel descriptionLabel;
	private JLabel previewLabel;

	private TitledBorder mainPanelBorder;
	private TitledBorder attributesPanelBorder;
	private TitledBorder imagePanelBorder;

	private static final String[] DEFENSIVE_CATEGORIES = {
		"armor", "belts", "helmet", "cloak", "legs", "boots", "glove", "ring", "shield"
	};

	private static final String[] DEFAULT_DYNAMIC_ATTRIBUTES = {
		"minimum_usage_level", "minimum_equip_level", "maximum_upgrade_amount"
	};

	private static final Map<String, String> CATEGORY_MAP;
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

	private XMLConsole xmlConsole;

	public ItemsPanel(Locale locale, XMLConsole xmlConsole) {
		languageSystem = new LanguageSystem(locale);
		setLayout(new BorderLayout());
		addedAttributes = new ArrayList<>();
		this.xmlConsole = xmlConsole;

		// Inicjalizacja attributesPanel
		attributesPanel = new JPanel(new GridBagLayout());
		attributesPanelBorder = BorderFactory.createTitledBorder(getWord("attributes"));
		attributesPanel.setBorder(attributesPanelBorder);

		// Główne
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanelBorder = BorderFactory.createTitledBorder(getWord("main"));
		mainPanel.setBorder(mainPanelBorder);

		// Dodawanie komponentów do mainPanel
		itemNameLabel = new JLabel(getWord("item_name") + ":");
		addComponent(mainPanel, itemNameLabel, 0, 0, 1, 1, GridBagConstraints.WEST);
		itemNameField = new JTextField(20);
		itemNameField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void removeUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void insertUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
		});
		addComponent(mainPanel, itemNameField, 1, 0, 2, 1, GridBagConstraints.EAST);

		itemCategoryLabel = new JLabel(getWord("item_category") + ":");
		addComponent(mainPanel, itemCategoryLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
		String[] categories = {
			getWord("ammunition"), getWord("armor"), getWord("axe"), getWord("belts"), getWord("boots"), getWord("cloak"),
			getWord("club"), getWord("dagger"), getWord("glove"), getWord("helmet"), getWord("legs"),
			getWord("magia"), getWord("ranged"), getWord("ring"), getWord("shield"), getWord("sword"),
			getWord("wand"), getWord("whip")
		};
		itemCategoryComboBox = new JComboBox<>(categories);
		itemCategoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAttributesPanel();
				updateXMLConsole();
			}
		});
		addComponent(mainPanel, itemCategoryComboBox, 1, 1, 2, 1, GridBagConstraints.EAST);

		uploadImageLabel = new JLabel(getWord("upload_image") + ":");
		addComponent(mainPanel, uploadImageLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		JButton uploadButton = new JButton(getWord("upload_image"));
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "bmp"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					itemSubclassField = new JTextField(selectedFile.getName().replaceFirst("[.][^.]+$", "")); // Save the image file name without extension as subclass
					ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
					Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
					imageLabel.setIcon(new ImageIcon(image));
					imageNameLabel.setText(selectedFile.getName());
					updateXMLConsole();
				}
			}
		});
		addComponent(mainPanel, uploadButton, 1, 2, 2, 1, GridBagConstraints.EAST);

		descriptionLabel = new JLabel(getWord("description") + ":");
		addComponent(mainPanel, descriptionLabel, 0, 3, 1, 1, GridBagConstraints.NORTHWEST);
		itemDescriptionArea = new JTextArea(3, 20);
		itemDescriptionArea.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void removeUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void insertUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
		});
		addComponent(mainPanel, new JScrollPane(itemDescriptionArea), 1, 3, 2, 1, GridBagConstraints.EAST);

		// Panel dla podglądu grafiki
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanelBorder = BorderFactory.createTitledBorder(getWord("preview"));
		imagePanel.setBorder(imagePanelBorder);
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setPreferredSize(new Dimension(150, 150));

		imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);

		imageNameLabel = new JLabel("", SwingConstants.CENTER);

		previewLabel = new JLabel(getWord("preview") + ":");
		imagePanel.add(previewLabel, BorderLayout.NORTH);
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		imagePanel.add(imageNameLabel, BorderLayout.SOUTH);

		// Dodanie paneli do głównego panelu
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(mainPanel, BorderLayout.NORTH);
		leftPanel.add(attributesPanel, BorderLayout.CENTER);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(imagePanel, BorderLayout.NORTH);

		add(leftPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

		// Dynamiczne atrybuty
		addAttributeComboBox = new JComboBox<>(new String[]{});
		JButton addAttributeButton = new JButton(getWord("add_attribute"));
		addAttributeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDynamicAttribute();
				updateXMLConsole();
			}
		});
		JPanel addAttributePanel = new JPanel(new FlowLayout());
		addAttributePanel.add(addAttributeComboBox);
		addAttributePanel.add(addAttributeButton);

		// Dodanie paneli do głównego panelu
		leftPanel.add(addAttributePanel, BorderLayout.SOUTH);
		updateAttributesPanel();
	}

	public void refreshLanguage(Locale newLocale) {
		languageSystem.setLocale(newLocale);

		itemNameLabel.setText(getWord("item_name") + ":");
		itemCategoryLabel.setText(getWord("item_category") + ":");
		uploadImageLabel.setText(getWord("upload_image") + ":");
		descriptionLabel.setText(getWord("description") + ":");
		previewLabel.setText(getWord("preview") + ":");

		mainPanelBorder.setTitle(getWord("main"));
		attributesPanelBorder.setTitle(getWord("attributes"));
		imagePanelBorder.setTitle(getWord("preview"));

		itemCategoryComboBox.removeAllItems();
		String[] categories = {
			getWord("ammunition"), getWord("armor"), getWord("axe"), getWord("belts"), getWord("boots"), getWord("cloak"),
			getWord("club"), getWord("dagger"), getWord("glove"), getWord("helmet"), getWord("legs"),
			getWord("magia"), getWord("ranged"), getWord("ring"), getWord("shield"), getWord("sword"),
			getWord("wand"), getWord("whip")
		};
		for (String category : categories) {
			itemCategoryComboBox.addItem(category);
		}

		updateAttributesPanel();
		revalidate();
		repaint();
		updateXMLConsole();
	}

	private void updateAttributesPanel() {
		attributesPanel.removeAll();
		addedAttributes.clear();

		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		boolean isDefensive = isDefensiveCategory(selectedCategory);

		GridBagConstraints gbc = createGbc(0, 0);

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

			if (selectedCategory.equals(getWord("ammunition")) || selectedCategory.equals(getWord("ranged")) || selectedCategory.equals(getWord("wand")) || selectedCategory.equals(getWord("magia")) || selectedCategory.equals(getWord("whip"))) {
				rangeField = createNumericTextField();
				addAttributeField("range", rangeField, gbc);
				gbc.gridy++;
			}
		}

		resetDynamicAttributes(); // Ensure dynamic attributes are reset and ready to be added
		attributesPanel.revalidate();
		attributesPanel.repaint();
		updateXMLConsole();
	}

	private JTextField createNumericTextField() {
		JTextField textField = new JTextField(10);
		((AbstractDocument) textField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
		return textField;
	}

	private void addAttributeField(String key, JTextField textField, GridBagConstraints gbc) {
		JLabel label = new JLabel(getWord(key) + ":");
		label.putClientProperty("attributeKey", key); // Store the attribute key for later retrieval
		label.putClientProperty("textField", textField); // Powiązanie textField z label
		addComponent(attributesPanel, label, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(attributesPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void removeUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void insertUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
		});
	}

	private void resetDynamicAttributes() {
		addAttributeComboBox.removeAllItems();

		// Dodanie specyficznych dynamicznych atrybutów dla określonych kategorii
		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		if (selectedCategory != null) {
			String englishCategory = CATEGORY_MAP.get(selectedCategory);

			if (isDefensiveCategory(selectedCategory)) {
				addAttributeComboBox.addItem(getWord("offense_value"));
			}
			if (!isDefensiveCategory(selectedCategory) && !("ammunition".equals(englishCategory))) {
				addAttributeComboBox.addItem(getWord("defense_value"));
			}

			if ("ranged".equals(englishCategory) || "wand".equals(englishCategory)) {
				addAttributeComboBox.addItem(getWord("attack_speed"));
			}
		}

		for (String attr : DEFAULT_DYNAMIC_ATTRIBUTES) {
			addAttributeComboBox.addItem(getWord(attr));
		}

		attributesPanel.revalidate();
		attributesPanel.repaint();
	}

	private boolean isDefensiveCategory(String category) {
		for (String defensiveCategory : DEFENSIVE_CATEGORIES) {
			if (getWord(defensiveCategory).equals(category)) {
				return true;
			}
		}
		return false;
	}

	private void addDynamicAttribute() {
		String selectedAttribute = (String) addAttributeComboBox.getSelectedItem();
		String attributeKey = null;

		// Find the key corresponding to the selected attribute
		for (Map.Entry<String, String> entry : ATTRIBUTE_KEYS.entrySet()) {
			if (getWord(entry.getKey()).equals(selectedAttribute)) {
				attributeKey = entry.getKey();
				break;
			}
		}

		if (attributeKey == null || addedAttributes.contains(attributeKey)) {
			JOptionPane.showMessageDialog(this, getWord("error_duplicate_field"), getWord("error_title"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		addedAttributes.add(attributeKey);

		GridBagConstraints gbc = createGbc(0, attributesPanel.getComponentCount() / 2 + 1);
		JLabel label = new JLabel(selectedAttribute + ":");
		JTextField textField = createNumericTextField();
		label.putClientProperty("attributeKey", attributeKey); // Store the attribute key for later retrieval
		label.putClientProperty("textField", textField); // Powiązanie textField z label
		textField.putClientProperty("attributeKey", attributeKey); // Powiązanie key z textField
		addComponent(attributesPanel, label, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(attributesPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void removeUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
			public void insertUpdate(DocumentEvent e) {
				updateXMLConsole();
			}
		});

		attributesPanel.revalidate();
		attributesPanel.repaint();
		updateXMLConsole();
	}

	public String getItemName() {
		return itemNameField.getText();
	}

	public String getItemCategory() {
		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		return CATEGORY_MAP.getOrDefault(selectedCategory, "").toLowerCase();
	}

	public String getItemSubclass() {
		return itemSubclassField != null ? itemSubclassField.getText() : "";
	}

	public String getItemDescription() {
		return itemDescriptionArea.getText();
	}

	public boolean isDefensive() {
		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		return isDefensiveCategory(selectedCategory);
	}

	public String getDefenseValue() {
		return defenseValueField != null ? defenseValueField.getText() : "";
	}

	public String getOffenseValue() {
		return offenseValueField != null ? offenseValueField.getText() : "";
	}

	public String getAttackSpeed() {
		return attackSpeedField != null ? attackSpeedField.getText() : "";
	}

	public String getRange() {
		return rangeField != null ? rangeField.getText() : "";
	}

	public String getDynamicAttributeValue(String key) {
		for (Component comp : attributesPanel.getComponents()) {
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

	private String getWord(String key) {
		return languageSystem.getWord(key);
	}

	private void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor) {
		GridBagConstraints gbc = createGbc(gridx, gridy);
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.anchor = anchor;
		container.add(component, gbc);
	}

	private GridBagConstraints createGbc(int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return gbc;
	}

	private void updateXMLConsole() {
		if (xmlConsole != null) {
			xmlConsole.refreshXML();
		}
	}

	private class NumericDocumentFilter extends DocumentFilter {
		private final int MAX_VALUE = 32767;

		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
			if (isNumeric(newText)) {
				super.insertString(fb, offset, string, attr);
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
			newText = newText.substring(0, offset) + text + newText.substring(offset + length);
			if (isNumeric(newText)) {
				super.replace(fb, offset, length, text, attrs);
			}
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			super.remove(fb, offset, length);
		}

		private boolean isNumeric(String text) {
			if (text == null || text.isEmpty()) {
				return false;
			}
			try {
				int value = Integer.parseInt(text);
				return value >= 1 && value <= MAX_VALUE;
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
}
