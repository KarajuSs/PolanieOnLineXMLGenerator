package polanieonline.client.panel;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JPanel;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.item.AttributesSectionPanel;
import polanieonline.client.panel.item.MainSectionPanel;

public class ItemsPanel extends JPanel {
	private MainSectionPanel msp;
	private AttributesSectionPanel asp;
	private XMLConsole xmlConsole;

	public ItemsPanel(Locale locale, XMLConsole xmlConsole) {
		this.xmlConsole = xmlConsole;
		setLayout(new BorderLayout());

		msp = new MainSectionPanel(this, locale, xmlConsole);
		asp = new AttributesSectionPanel(this, locale, xmlConsole);

		add(msp, BorderLayout.NORTH);
		add(asp, BorderLayout.CENTER);
	}

	public String getItemName() {
		return msp.getItemName();
	}
	public String getItemClass() {
		return msp.getItemClass();
	}
	public String getItemSubclass() {
		return msp.getItemSubclass();
	}
	public String getItemDescription() {
		return msp.getItemDescription();
	}
	public String getSelectedItemClass() {
		return (String) msp.getItemClassList().getSelectedItem();
	}
	public String getAtkValue() {
		return asp.getAtkValue();
	}
	public String getDefValue() {
		return asp.getDefValue();
	}
	public String getRateValue() {
		return asp.getRateValue();
	}
	public String getRangeValue() {
		return asp.getRangeValue();
	}
	public void updateAttributesPanel() {
		asp.updateAttributesPanel();
	}
	public Map<String, String> getItemClassesMap() {
		return asp.getItemClassesMap();
	}

	public List<String> getAddedAttributes() {
		return asp.getAddedAttributes();
	}
	public String getDynamicAttributeValue(String langKey) {
		return asp.getDynamicAttributeValue(langKey);
	}

	public void refresh() {
		msp.refresh();
		asp.refresh();
	}
}
