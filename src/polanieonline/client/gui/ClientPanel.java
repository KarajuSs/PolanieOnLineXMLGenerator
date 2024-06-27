package polanieonline.client.gui;

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
		// Utworzenie panelu zakładek
		tabbedPane = new JTabbedPane();

		// Utworzenie panelu dla "Items"
		JPanel itemsPanel = new JPanel();
		itemsLabel = new JLabel(getWord("items"));
		itemsPanel.add(itemsLabel);
		// TODO: Dodaj pola tekstowe i inne komponenty do itemsPanel według potrzeb

		// Utworzenie panelu dla "Creatures"
		JPanel creaturesPanel = new JPanel();
		creaturesLabel = new JLabel(getWord("creatures"));
		creaturesPanel.add(creaturesLabel);
		// TODO: Dodaj pola tekstowe i inne komponenty do creaturesPanel według potrzeb

		// Dodanie paneli do zakładek
		tabbedPane.addTab(getWord("items"), itemsPanel);
		tabbedPane.addTab(getWord("creatures"), creaturesPanel);

		// Utworzenie głównego panelu
		JPanel mainPanel = new JPanel();
		mainPanel.add(tabbedPane);

		return mainPanel;
	}

	public void refreshComponents() {
		itemsLabel.setText(getWord("items"));
		creaturesLabel.setText(getWord("creatures"));
		tabbedPane.setTitleAt(0, getWord("items"));
		tabbedPane.setTitleAt(1, getWord("creatures"));
	}
}