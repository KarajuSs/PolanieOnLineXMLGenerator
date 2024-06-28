package polanieonline.client.panel;

import polanieonline.common.language.LanguageSystem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private Map<String, JTextField> dynamicAttributes;
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
		"armor", "belt", "helmet", "cloak", "pants", "boots", "gloves", "ring", "shield"
	};

	private static final String[] DEFAULT_DYNAMIC_ATTRIBUTES = {
		"minimum_usage_level", "minimum_equip_level", "maximum_upgrade_amount"
	};

	public ItemsPanel(Locale locale) {
		languageSystem = new LanguageSystem(locale);
		setLayout(new BorderLayout());
		dynamicAttributes = new HashMap<>();
		addedAttributes = new ArrayList<>();

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
		addComponent(mainPanel, itemNameField, 1, 0, 2, 1, GridBagConstraints.EAST);

		itemCategoryLabel = new JLabel(getWord("item_category") + ":");
		addComponent(mainPanel, itemCategoryLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
		String[] categories = {
			getWord("ammunition"), getWord("armor"), getWord("axe"), getWord("belt"), getWord("boots"), getWord("cloak"),
			getWord("hammer"), getWord("dagger"), getWord("gloves"), getWord("helmet"), getWord("pants"),
			getWord("magic_spell"), getWord("ranged_weapon"), getWord("ring"), getWord("shield"), getWord("sword"),
			getWord("wand"), getWord("whip")
		};
		itemCategoryComboBox = new JComboBox<>(categories);
		itemCategoryComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAttributesPanel();
			}
		});
		addComponent(mainPanel, itemCategoryComboBox, 1, 1, 2, 1, GridBagConstraints.EAST);

		uploadImageLabel = new JLabel(getWord("upload_image") + ":");
		addComponent(mainPanel, uploadImageLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		JButton uploadButton = new JButton(getWord("upload_image"));
		addComponent(mainPanel, uploadButton, 1, 2, 2, 1, GridBagConstraints.EAST);

		descriptionLabel = new JLabel(getWord("description") + ":");
		addComponent(mainPanel, descriptionLabel, 0, 3, 1, 1, GridBagConstraints.NORTHWEST);
		itemDescriptionArea = new JTextArea(3, 20);
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
			}
		});
		JPanel addAttributePanel = new JPanel(new FlowLayout());
		addAttributePanel.add(addAttributeComboBox);
		addAttributePanel.add(addAttributeButton);

		// Dodanie paneli do głównego panelu
		leftPanel.add(addAttributePanel, BorderLayout.SOUTH);
		updateAttributesPanel();

		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif", "bmp"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					itemSubclassField = new JTextField(selectedFile.getName()); // Save the image file name as subclass
					ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
					Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
					imageLabel.setIcon(new ImageIcon(image));
					imageNameLabel.setText(selectedFile.getName());
				}
			}
		});
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

		// Zaktualizuj listę rozwijaną kategorii przedmiotów
		itemCategoryComboBox.removeAllItems();
		String[] categories = {
			getWord("ammunition"), getWord("armor"), getWord("axe"), getWord("belt"), getWord("boots"), getWord("cloak"),
			getWord("hammer"), getWord("dagger"), getWord("gloves"), getWord("helmet"), getWord("pants"),
			getWord("magic_spell"), getWord("ranged_weapon"), getWord("ring"), getWord("shield"), getWord("sword"),
			getWord("wand"), getWord("whip")
		};
		for (String category : categories) {
			itemCategoryComboBox.addItem(category);
		}

		// Odśwież panel atrybutów
		updateAttributesPanel();

		revalidate();
		repaint();
	}

	private void updateAttributesPanel() {
		attributesPanel.removeAll();
		dynamicAttributes.clear();
		addedAttributes.clear();

		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		boolean isDefensive = isDefensiveCategory(selectedCategory);

		GridBagConstraints gbc = createGbc(0, 0);

		// Debug information
		System.out.println("Selected category: " + selectedCategory);
		System.out.println("Is defensive: " + isDefensive);

		if (selectedCategory == null) {
			System.err.println("Selected category is null");
			return;
		}

		if (getWord("ammunition") == null || getWord("ranged_weapon") == null || getWord("wand") == null || getWord("magic_spell") == null || getWord("whip") == null) {
			System.err.println("One of the getWord results is null");
			return;
		}

		// Wybór atrybutów w zależności od kategorii przedmiotu
		if (isDefensive) {
			defenseValueField = new JTextField(10);
			addAttributeField("defense_value", defenseValueField, gbc);
			// Dynamiczne atrybuty dla defensywnych przedmiotów
			resetDynamicAttributes();
			addAttributeComboBox.addItem(getWord("offense_value"));
		} else {
			offenseValueField = new JTextField(10);
			addAttributeField("offense_value", offenseValueField, gbc);
			gbc.gridy++;

			// Atrybut zasięgu dla wybranych kategorii
			if (selectedCategory.equals(getWord("ammunition")) || selectedCategory.equals(getWord("ranged_weapon")) || selectedCategory.equals(getWord("wand")) || selectedCategory.equals(getWord("magic_spell")) || selectedCategory.equals(getWord("whip"))) {
				rangeField = new JTextField(10);
				addAttributeField("range", rangeField, gbc);
				// Dynamiczne atrybuty dla tych kategorii
				resetDynamicAttributes();
				addAttributeComboBox.addItem(getWord("attack_speed"));
			} else {
				attackSpeedField = new JTextField(10);
				addAttributeField("attack_speed", attackSpeedField, gbc);
				// Dynamiczne atrybuty dla ofensywnych przedmiotów
				resetDynamicAttributes();
				addAttributeComboBox.addItem(getWord("defense_value"));
			}
		}

		attributesPanel.revalidate();
		attributesPanel.repaint();
	}

	private void addAttributeField(String key, JTextField textField, GridBagConstraints gbc) {
		addComponent(attributesPanel, new JLabel(getWord(key) + ":"), gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(attributesPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);
	}

	private void resetDynamicAttributes() {
		addAttributeComboBox.removeAllItems();

		// Dodaj niestandardowe atrybuty na górze
		for (String attr : addedAttributes) {
			if (!Arrays.asList(DEFAULT_DYNAMIC_ATTRIBUTES).contains(attr)) {
				addAttributeComboBox.addItem(attr);
			}
		}

		// Dodaj domyślne atrybuty na dole
		for (String attr : DEFAULT_DYNAMIC_ATTRIBUTES) {
			addAttributeComboBox.addItem(getWord(attr));
		}
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
		if (addedAttributes.contains(selectedAttribute)) {
			JOptionPane.showMessageDialog(this, getWord("error_duplicate_field"), getWord("error_title"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		addedAttributes.add(selectedAttribute);

		GridBagConstraints gbc = createGbc(0, attributesPanel.getComponentCount() / 2 + 1); // +1 to account for the static fields
		JLabel label = new JLabel(selectedAttribute + ":");
		JTextField textField = new JTextField(10);
		label.putClientProperty("textField", textField); // Store the related text field
		dynamicAttributes.put(selectedAttribute, textField);
		addComponent(attributesPanel, label, gbc.gridx, gbc.gridy, 1, 1, GridBagConstraints.WEST);
		addComponent(attributesPanel, textField, gbc.gridx + 1, gbc.gridy, 1, 1, GridBagConstraints.EAST);

		attributesPanel.revalidate();
		attributesPanel.repaint();
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
}
