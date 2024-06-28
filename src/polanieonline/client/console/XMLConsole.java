package polanieonline.client.console;

import javax.swing.*;
import java.awt.*;
import polanieonline.client.panel.ItemsPanel;

public class XMLConsole extends JPanel {
	private JTextArea xmlTextArea;
	private ItemsPanel itemsPanel;

	public XMLConsole(ItemsPanel itemsPanel) {
		this.itemsPanel = itemsPanel;
		setLayout(new BorderLayout());

		// XML Text Area
		xmlTextArea = new JTextArea(20, 50);
		xmlTextArea.setEditable(false);
		xmlTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Powiększona czcionka
		JScrollPane scrollPane = new JScrollPane(xmlTextArea);

		// Dodanie komponentów do panelu
		add(scrollPane, BorderLayout.CENTER);

		setBorder(BorderFactory.createTitledBorder("XML Console"));
	}

	public void setItemsPanel(ItemsPanel itemsPanel) {
		this.itemsPanel = itemsPanel;
	}

	public void refreshXML() {
		if (itemsPanel == null) {
			xmlTextArea.setText("");
			return;
		}
		String xmlContent = generateXMLContent();
		xmlTextArea.setText(xmlContent);
	}

	private String generateXMLContent() {
		String itemName = itemsPanel.getItemName();
		String itemCategory = itemsPanel.getItemCategory();
		String itemSubclass = itemsPanel.getItemSubclass();
		String itemDescription = itemsPanel.getItemDescription();
		boolean isDefensive = itemsPanel.isDefensive();
		String defenseValue = itemsPanel.getDefenseValue();
		String offenseValue = itemsPanel.getOffenseValue();
		String attackSpeed = itemsPanel.getAttackSpeed();
		String range = itemsPanel.getRange();
		String minLevel = itemsPanel.getDynamicAttributeValue("minimum_equip_level");
		String maxImproves = itemsPanel.getDynamicAttributeValue("maximum_upgrade_amount");
		String minUse = itemsPanel.getDynamicAttributeValue("minimum_usage_level");

		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<item name=\"").append(itemName).append("\">\n");
		xmlBuilder.append("\t<type class=\"").append(itemCategory).append("\" subclass=\"")
				.append(itemSubclass).append("\" tileid=\"-1\"/>\n");
		xmlBuilder.append("\t<description>").append(itemDescription).append("</description>\n");
		xmlBuilder.append("\t<implementation class-name=\"games.stendhal.server.entity.item.Item\"/>\n");
		xmlBuilder.append("\t<attributes>\n");

		if (offenseValue != null && !offenseValue.isEmpty()) {
			xmlBuilder.append("\t\t<atk value=\"").append(offenseValue).append("\"/>\n");
		}
		if (defenseValue != null && !defenseValue.isEmpty()) {
			xmlBuilder.append("\t\t<def value=\"").append(defenseValue).append("\"/>\n");
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
		if (attackSpeed != null && !attackSpeed.isEmpty()) {
			xmlBuilder.append("\t\t<rate value=\"").append(attackSpeed).append("\"/>\n");
		}

		xmlBuilder.append("\t</attributes>\n");
		xmlBuilder.append("\t<weight value=\"\"/>\n"); // Placeholder for weight
		xmlBuilder.append("\t<value value=\"\"/>\n"); // Placeholder for value
		xmlBuilder.append("\t<equipable>\n");
		xmlBuilder.append("\t\t<slot name=\"ground\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"content\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"bag\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"trade\"/>\n");
		xmlBuilder.append("\t\t<slot name=\"armor\"/>\n");
		xmlBuilder.append("\t</equipable>\n");
		xmlBuilder.append("</item>\n");

		return xmlBuilder.toString();
	}
}
