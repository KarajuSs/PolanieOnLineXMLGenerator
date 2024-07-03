package polanieonline.client.panel.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;
import polanieonline.common.filter.IntegerNumericFilter;
import polanieonline.common.listener.SimpleDocumentListener;

public class SpecialSectionPanel extends SectionPanel {
	private ItemsPanel itemsPanel;

	private TitledBorder panelBorder;
	private JPanel panel;

	private JCheckBox[] elementCheckBoxes;
	private JTextField[] elementValueFields;

	private static final String[] ELEMENTS = {"earth", "ice", "water", "fire", "light", "dark"};

	public SpecialSectionPanel(ItemsPanel itemsPanel, Locale locale, XMLConsole xmlConsole) {
		super(locale, xmlConsole);
		this.itemsPanel = itemsPanel;

		initUI();
	}

	protected String getPanelTitle() {
		return "special_protection";
	}

	private void initUI() {
		setLayout(new BorderLayout());
		panelBorder = getTitleBorder();
		setBorder(panelBorder);

		panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		add(panel, BorderLayout.NORTH);

		generateFields(gbc);
	}

	private void generateFields(GridBagConstraints gbc) {
		elementCheckBoxes = new JCheckBox[ELEMENTS.length];
		elementValueFields = new JTextField[ELEMENTS.length];

		for (int i = 0; i < ELEMENTS.length; i++) {
			final int index = i; // Użycie tymczasowej zmiennej finalnej
			String element = ELEMENTS[i];
			JLabel elementLabel = new JLabel(getLangKey(element) + ":");
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 0.1;
			panel.add(elementLabel, gbc);

			elementCheckBoxes[index] = new JCheckBox();
			elementCheckBoxes[index].addActionListener(e -> {
				toggleElementField(index);
				updateXMLConsole();
			});
			gbc.gridx = 1;
			panel.add(elementCheckBoxes[index], gbc);

			elementValueFields[index] = createNumericTextField();
			elementValueFields[index].setEnabled(false);
			elementValueFields[index].getDocument().addDocumentListener(new SimpleDocumentListener(() -> updateXMLConsole()));
			gbc.gridx = 2;
			gbc.weightx = 0.9;
			panel.add(elementValueFields[index], gbc);
		}

		resetFields();
		panel.revalidate();
		panel.repaint();
		updateXMLConsole();
	}

	public void resetFields() {
		String selectedCategory = itemsPanel.getSelectedItemClass();
		boolean isDefensive = itemsPanel.isDefensiveCategory(selectedCategory);

		for (int i = 0; i < ELEMENTS.length; i++) {
			JCheckBox checkbox = elementCheckBoxes[i];
			JTextField field = elementValueFields[i];

			checkbox.setEnabled(isDefensive);
			checkbox.setSelected(false);

			field.setText("");
			field.setEnabled(false);

			if (isDefensive && checkbox.isSelected()) {
				field.setEnabled(true);
			}
		}
	}

	public Map<String, Double> getSusceptibilityValues() {
		Map<String, Double> protectionValues = new HashMap<>();
		for (int i = 0; i < ELEMENTS.length; i++) {
			if (elementCheckBoxes[i].isSelected()) {
				try {
					int intValue = Integer.parseInt(elementValueFields[i].getText());
					if (intValue < 0 || intValue > 200) {
						throw new NumberFormatException("Value out of range");
					}
					double value = intValue / 100.0;
					protectionValues.put(ELEMENTS[i], value);
				} catch (NumberFormatException e) {
					protectionValues.put(ELEMENTS[i], 0.0);
				}
			}
		}
		return protectionValues;
	}

	private void toggleElementField(int index) {
		elementValueFields[index].setEnabled(elementCheckBoxes[index].isSelected());
	}

	private JTextField createNumericTextField() {
		JTextField textField = new JTextField(10);
		textField.setDocument(new IntegerNumericFilter());
		textField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> validateValue(textField)));
		return textField;
	}

	private void validateValue(JTextField textField) {
		try {
			int value = Integer.parseInt(textField.getText());
			if (value < 0 || value > 200) {
				textField.setBackground(Color.RED);
			} else {
				textField.setBackground(Color.WHITE);
			}
		} catch (NumberFormatException e) {
			textField.setBackground(Color.RED);
		}
	}

	public void refresh() {
		panelBorder.setTitle(getTitleBorder().getTitle());

		resetFields();
		revalidate();
		repaint();
		updateXMLConsole();
	}
}
