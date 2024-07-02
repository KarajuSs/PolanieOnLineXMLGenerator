package polanieonline.client.panel.item;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;
import polanieonline.common.filter.IntegerNumericFilter;

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
			elementCheckBoxes[index].addActionListener(e -> toggleElementField(index));
			gbc.gridx = 1;
			panel.add(elementCheckBoxes[index], gbc);

			elementValueFields[index] = createNumericTextField();
			elementValueFields[index].setEnabled(false);
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

	private void toggleElementField(int index) {
		elementValueFields[index].setEnabled(elementCheckBoxes[index].isSelected());
	}

	private JTextField createNumericTextField() {
		JTextField textField = new JTextField(10);
		textField.setDocument(new IntegerNumericFilter());
		return textField;
	}

	public void refresh() {
		panelBorder.setTitle(getTitleBorder().getTitle());

		resetFields();
		revalidate();
		repaint();
		updateXMLConsole();
	}
}
