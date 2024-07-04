package polanieonline.client.panel.item;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import polanieonline.client.console.XMLConsole;
import polanieonline.common.language.LanguageSystem;

public abstract class SectionPanel extends JPanel {
	protected LanguageSystem languageSystem;
	protected XMLConsole xmlConsole;

	public SectionPanel(Locale locale, XMLConsole xmlConsole) {
		this.languageSystem = new LanguageSystem(locale);
		this.xmlConsole = xmlConsole;
	}

	protected TitledBorder getTitleBorder() {
		return BorderFactory.createTitledBorder(getLangKey(getPanelTitle()));
	}

	protected abstract String getPanelTitle();

	protected void updateXMLConsole() {
		if (xmlConsole != null) {
			xmlConsole.refreshXML();
		}
	}

	public String getLangKey(String key) {
		return languageSystem.getLangKey(key);
	}

	public void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor) {
		GridBagConstraints gbc = createGbc(gridx, gridy);
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.anchor = anchor;
		container.add(component, gbc);
	}

	public GridBagConstraints createGbc(int gridx, int gridy) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		return gbc;
	}
}
