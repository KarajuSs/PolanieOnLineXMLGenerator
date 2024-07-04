package polanieonline.client.gui;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;

public class ClientPanel extends ClientGUI {
	private JTabbedPane tabbedPane;
	private ItemsPanel itemsPanel;
	private JPanel creaturesPanel;
	private JLabel itemsLabel;
	private JLabel creaturesLabel;
	private XMLConsole xmlConsole;

	public ClientPanel(Locale locale) {
		super(locale);
	}

	@Override
	public JPanel createMainPanel() {
		tabbedPane = new JTabbedPane();

		xmlConsole = new XMLConsole();
		itemsPanel = new ItemsPanel(getLocale(), xmlConsole);
		xmlConsole.setItemsPanel(itemsPanel);

		creaturesPanel = new JPanel(new BorderLayout());
		creaturesLabel = new JLabel(getLangKey("creatures"));
		creaturesPanel.add(creaturesLabel, BorderLayout.CENTER);

		tabbedPane.addTab(getLangKey("items"), itemsPanel);
		tabbedPane.addTab(getLangKey("creatures"), creaturesPanel);
		tabbedPane.addTab(getLangKey("xml_console"), xmlConsole);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		return mainPanel;
	}

	public void refreshComponents() {
		itemsPanel.refresh();
		creaturesLabel.setText(getLangKey("creatures"));
		tabbedPane.setTitleAt(0, getLangKey("items"));
		tabbedPane.setTitleAt(1, getLangKey("creatures"));
		tabbedPane.setTitleAt(2, getLangKey("xml_console"));
	}
}
