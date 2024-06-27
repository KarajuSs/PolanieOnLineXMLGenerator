package polanieonline.client.panel;

import polanieonline.common.language.LanguageSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
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
	private JComboBox<String> addAttributeComboBox;
	private List<JComponent> dynamicAttributes;
	private List<String> addedAttributes;
	private JLabel imageLabel;
	private JLabel imageNameLabel;

	private static final String[] DEFENSIVE_CATEGORIES = {
		"armor", "belt", "helmet", "cloak", "pants", "boots", "gloves", "ring", "shield"
	};

	private LanguageSystem languageSystem;

	public ItemsPanel(Locale locale) {
		languageSystem = new LanguageSystem(locale);
		setLayout(new BorderLayout());
		dynamicAttributes = new ArrayList<>();
		addedAttributes = new ArrayList<>();

		// Inicjalizacja attributesPanel
		attributesPanel = new JPanel(new GridBagLayout());
		attributesPanel.setBorder(BorderFactory.createTitledBorder(getWord("attributes")));

		// Główne
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createTitledBorder(getWord("main")));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(new JLabel(getWord("item_name") + ":"), gbc);
		gbc.gridx = 1;
		itemNameField = new JTextField(20);
		mainPanel.add(itemNameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(new JLabel(getWord("item_category") + ":"), gbc);
		gbc.gridx = 1;
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
		mainPanel.add(itemCategoryComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		mainPanel.add(new JLabel(getWord("upload_image") + ":"), gbc);
		gbc.gridx = 1;
		JButton uploadButton = new JButton(getWord("upload_image"));
		mainPanel.add(uploadButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		mainPanel.add(new JLabel(getWord("description") + ":"), gbc);
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		itemDescriptionArea = new JTextArea(3, 20);
		mainPanel.add(new JScrollPane(itemDescriptionArea), gbc);
		gbc.gridwidth = 1;

		// Panel dla podglądu grafiki
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanel.setBorder(BorderFactory.createTitledBorder(getWord("preview")));
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setPreferredSize(new Dimension(150, 150));

		imageLabel = new JLabel();
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);

		imageNameLabel = new JLabel("", SwingConstants.CENTER);

		imagePanel.add(new JLabel(getWord("preview") + ":"), BorderLayout.NORTH);
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
		addAttributeComboBox = new JComboBox<>(new String[]{
			getWord("minimum_usage_level"), getWord("minimum_equip_level"), getWord("maximum_upgrade_amount")
		});
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

	private void updateAttributesPanel() {
		attributesPanel.removeAll();
		dynamicAttributes.clear();
		addedAttributes.clear();

		String selectedCategory = (String) itemCategoryComboBox.getSelectedItem();
		boolean isDefensive = isDefensiveCategory(selectedCategory);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		gbc.gridy = 1;
		gbc.gridx = 0;
		if (isDefensive) {
			attributesPanel.add(new JLabel(getWord("defense_value") + ":"), gbc);
			gbc.gridx = 1;
			defenseValueField = new JTextField(10);
			attributesPanel.add(defenseValueField, gbc);
		} else {
			attributesPanel.add(new JLabel(getWord("offense_value") + ":"), gbc);
			gbc.gridx = 1;
			offenseValueField = new JTextField(10);
			attributesPanel.add(offenseValueField, gbc);
			gbc.gridy++;
			gbc.gridx = 0;
			attributesPanel.add(new JLabel(getWord("attack_speed") + ":"), gbc);
			gbc.gridx = 1;
			attackSpeedField = new JTextField(10);
			attributesPanel.add(attackSpeedField, gbc);
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
		if (addedAttributes.contains(selectedAttribute)) {
			JOptionPane.showMessageDialog(this, getWord("error_duplicate_field"), "Błąd", JOptionPane.ERROR_MESSAGE);
			return;
		}
		addedAttributes.add(selectedAttribute);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = attributesPanel.getComponentCount() / 2 + 1; // +1 to account for the static fields
		JLabel label = new JLabel(selectedAttribute + ":");
		JTextField textField = new JTextField(10);
		label.putClientProperty("textField", textField); // Store the related text field
		dynamicAttributes.add(label);
		attributesPanel.add(label, gbc);
		gbc.gridx = 1;
		attributesPanel.add(textField, gbc);
		attributesPanel.revalidate();
		attributesPanel.repaint();
	}

	private String getWord(String key) {
		return languageSystem.getWord(key);
	}
}
