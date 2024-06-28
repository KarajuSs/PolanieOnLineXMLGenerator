package polanieonline.client.gui;

import java.awt.BorderLayout;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import polanieonline.client.panel.ItemsPanel;
import polanieonline.client.console.XMLConsole;

public class ClientPanel extends ClientGUI {
	private JTabbedPane tabbedPane;
	private JPanel itemsPanel;
	private JPanel creaturesPanel;
	private XMLConsole xmlConsole;
	private JLabel itemsLabel;
	private JLabel creaturesLabel;

	public ClientPanel(Locale locale) {
		super(locale);
	}

	@Override
	public JPanel createMainPanel() {
		tabbedPane = new JTabbedPane();

		xmlConsole = new XMLConsole(null);
		itemsPanel = new ItemsPanel(getLocale(), xmlConsole);
		xmlConsole.setItemsPanel((ItemsPanel) itemsPanel);

		creaturesPanel = new JPanel(new BorderLayout());
		creaturesLabel = new JLabel(getWord("creatures"));
		creaturesPanel.add(creaturesLabel, BorderLayout.CENTER);

		tabbedPane.addTab(getWord("items"), itemsPanel);
		tabbedPane.addTab(getWord("creatures"), creaturesPanel);
		tabbedPane.addTab(getWord("xml_console"), xmlConsole);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		return mainPanel;
	}

	public void refreshComponents() {
		((ItemsPanel) itemsPanel).refreshLanguage(getLocale());
		creaturesLabel.setText(getWord("creatures"));
		tabbedPane.setTitleAt(0, getWord("items"));
		tabbedPane.setTitleAt(1, getWord("creatures"));
		tabbedPane.setTitleAt(2, getWord("xml_console"));
	}
}
