package polanieonline.client.gui;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ClientPanel extends ClientGUI {
	private JTabbedPane tabbedPane;
	private JPanel itemsPanel;
	private JPanel creaturesPanel;
	private JLabel itemsLabel;
	private JLabel creaturesLabel;

	public ClientPanel(Locale locale) {
		super(locale);
	}

	@Override
	public JPanel createMainPanel() {
		tabbedPane = new JTabbedPane();

		itemsPanel = new JPanel(new BorderLayout());
		itemsLabel = new JLabel(getWord("items"));
		itemsPanel.add(itemsLabel, BorderLayout.CENTER);

		creaturesPanel = new JPanel(new BorderLayout());
		creaturesLabel = new JLabel(getWord("creatures"));
		creaturesPanel.add(creaturesLabel, BorderLayout.CENTER);

		tabbedPane.addTab(getWord("items"), itemsPanel);
		tabbedPane.addTab(getWord("creatures"), creaturesPanel);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		return mainPanel;
	}

	public void refreshComponents() {
		itemsLabel.setText(getWord("items"));
		creaturesLabel.setText(getWord("creatures"));
		tabbedPane.setTitleAt(0, getWord("items"));
		tabbedPane.setTitleAt(1, getWord("creatures"));
	}
}
