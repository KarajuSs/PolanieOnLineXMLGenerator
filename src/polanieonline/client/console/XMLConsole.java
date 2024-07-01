package polanieonline.client.console;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import polanieonline.client.panel.ItemsPanel;

public class XMLConsole extends JPanel {
	private JTextArea textArea;
	private ItemsPanel itemsPanel;

	public XMLConsole() {
		setLayout(new BorderLayout());
		textArea = new JTextArea(20, 40);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void setItemsPanel(ItemsPanel itemsPanel) {
		this.itemsPanel = itemsPanel;
	}

	public void refreshXML() {
		if (itemsPanel != null) {
			textArea.setText(generateXMLContent());
		} else {
			textArea.setText("ItemsPanel is not set.");
		}
	}

	public String generateXMLContent() {
		StringBuilder xmlBuilder = new StringBuilder();

		xmlBuilder.append("<item name=\"").append(itemsPanel.getItemName()).append("\">\n");
		xmlBuilder.append("\t<type class=\"").append(itemsPanel.getItemClass()).append("\" subclass=\"").append(itemsPanel.getItemSubclass()).append("\" tileid=\"-1\"/>\n");
		xmlBuilder.append("\t<description>").append(itemsPanel.getItemDescription()).append("</description>\n");
		xmlBuilder.append("\t<implementation class-name=\"games.stendhal.server.entity.item.Item\"/>\n");
		xmlBuilder.append("\t<attributes>\n");

		// Dodanie wartości "atk" i "def" zgodnie z kolejnością
		String atkValue = itemsPanel.getAtkValue();
		String defValue = itemsPanel.getDefValue();

		// Dodanie dynamicznych atrybutów jako dodatkowe
		String dynamicAtkValue = itemsPanel.getDynamicAttributeValue("offense_value");
		String dynamicDefValue = itemsPanel.getDynamicAttributeValue("defense_value");

		if (atkValue != null && !atkValue.isEmpty()) {
			xmlBuilder.append("\t\t<atk value=\"").append(atkValue).append("\"/>\n");
		} else if (dynamicAtkValue != null && !dynamicAtkValue.isEmpty()) {
			xmlBuilder.append("\t\t<atk value=\"").append(dynamicAtkValue).append("\"/>\n");
		}

		if (defValue != null && !defValue.isEmpty()) {
			xmlBuilder.append("\t\t<def value=\"").append(defValue).append("\"/>\n");
		} else if (dynamicDefValue != null && !dynamicDefValue.isEmpty()) {
			xmlBuilder.append("\t\t<def value=\"").append(dynamicDefValue).append("\"/>\n");
		}

		// Dynamiczne atrybuty
		String minUse = itemsPanel.getDynamicAttributeValue("minimum_usage_level");
		String minLevel = itemsPanel.getDynamicAttributeValue("minimum_equip_level");
		String maxImproves = itemsPanel.getDynamicAttributeValue("maximum_upgrade_amount");
		String attackSpeed = itemsPanel.getDynamicAttributeValue("attack_speed");
		String range = itemsPanel.getRangeValue();

		if (attackSpeed != null && !attackSpeed.isEmpty()) {
			xmlBuilder.append("\t\t<rate value=\"").append(attackSpeed).append("\"/>\n");
		}

		if (range != null && !range.isEmpty()) {
			xmlBuilder.append("\t\t<range value=\"").append(range).append("\"/>\n");
		}

		if (minUse != null && !minUse.isEmpty()) {
			xmlBuilder.append("\t\t<min_use value=\"").append(minUse).append("\"/>\n");
		}

		if (minLevel != null && !minLevel.isEmpty()) {
			xmlBuilder.append("\t\t<min_level value=\"").append(minLevel).append("\"/>\n");
		}

		if (maxImproves != null && !maxImproves.isEmpty()) {
			xmlBuilder.append("\t\t<max_improves value=\"").append(maxImproves).append("\"/>\n");
		}

		// Dodanie innych dynamicznych atrybutów
		for (String attr : itemsPanel.getAddedAttributes()) {
			String value = itemsPanel.getDynamicAttributeValue(attr);
			if (value != null && !value.isEmpty() && !attr.equals("minimum_usage_level") && !attr.equals("minimum_equip_level") && !attr.equals("maximum_upgrade_amount") && !attr.equals("attack_speed") && !attr.equals("offense_value") && !attr.equals("defense_value")) {
				xmlBuilder.append("\t\t<").append(attr).append(" value=\"").append(value).append("\"/>\n");
			}
		}

		xmlBuilder.append("\t</attributes>\n");
		xmlBuilder.append("\t<weight value=\"0\"/>\n");
		xmlBuilder.append("\t<value value=\"0\"/>\n");
		xmlBuilder.append("\t<equipable>\n");
		xmlBuilder.append("\t\t<slot name=\"ground\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"content\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"bag\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"trade\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"armor\"/>\n");
		xmlBuilder.append("\t</equipable>\n");
		xmlBuilder.append("</item>");

		return xmlBuilder.toString();
	}
}
