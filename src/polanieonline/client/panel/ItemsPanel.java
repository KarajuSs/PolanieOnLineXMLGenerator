package polanieonline.client.panel;

import static polanieonline.common.constants.Nature.ELEMENTS_KEYS;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JPanel;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.item.AttributesSectionPanel;
import polanieonline.client.panel.item.EquipmentSlotSectionPanel;
import polanieonline.client.panel.item.MainSectionPanel;
import polanieonline.client.panel.item.SectionPanel;
import polanieonline.client.panel.item.SpecialSectionPanel;

public class ItemsPanel extends SectionPanel {
	private MainSectionPanel msp;
	private AttributesSectionPanel asp;
	private EquipmentSlotSectionPanel esp;
	private SpecialSectionPanel ssp;
	private XMLConsole xmlConsole;

	public ItemsPanel(Locale locale, XMLConsole xmlConsole) {
		super(locale, xmlConsole);
		setLayout(new BorderLayout());

		msp = new MainSectionPanel(this, locale, xmlConsole);
		asp = new AttributesSectionPanel(this, locale, xmlConsole);
		esp = new EquipmentSlotSectionPanel(this, locale, xmlConsole);
		ssp = new SpecialSectionPanel(this, locale, xmlConsole);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(msp, BorderLayout.NORTH);

		JPanel doublePanel = new JPanel(new BorderLayout());
		doublePanel.add(ssp, BorderLayout.NORTH);
		doublePanel.add(esp, BorderLayout.SOUTH);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(asp, BorderLayout.CENTER);
		bottomPanel.add(doublePanel, BorderLayout.EAST);

		panel.add(bottomPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
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
		esp.updateCheckBoxesForClass(getSelectedItemClass());
		ssp.resetFields();
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
	public boolean isDefensiveCategory(String category) {
		return asp.isDefensiveCategory(category);
	}
	public String getSelectedNatureType() {
		return asp.getSelectedNatureType();
	}

	public String[] getSelectedSlots() {
		return esp.getSelectedSlots();
	}

	public Map<String, Double> getSusceptibilityValues() {
		return ssp.getSusceptibilityValues();
	}

	public String[] getNatureTypes() {
		return ELEMENTS_KEYS.values().toArray(new String[0]);
	}

	public String[] getTranslationNatureTypes() {
		return ELEMENTS_KEYS.keySet().toArray(new String[0]);
	}

	public void refresh() {
		msp.refresh();
		asp.refresh();
		ssp.refresh();
		esp.refresh();
	}

	@Override
	protected String getPanelTitle() {
		return null;
	}
}
