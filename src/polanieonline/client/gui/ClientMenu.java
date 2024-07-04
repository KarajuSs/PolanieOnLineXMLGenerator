package polanieonline.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import polanieonline.client.gui.windowmenu.PropertiesMenu;
import polanieonline.client.settings.LangSettings;

public class ClientMenu extends ClientGUI {
	private LangSettings langSettings;
	private PropertiesMenu propertiesMenu;

	public ClientMenu(Locale locale) {
		super(locale);
	}

	@Override
	public void setFrame(JFrame frame) {
		super.setFrame(frame);
		this.propertiesMenu = new PropertiesMenu(getLocale());
	}

	public void setLangSettings(LangSettings langSettings) {
		this.langSettings = langSettings;
	}

	@Override
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		if (langSettings != null) {
			langSettings.setLocale(locale);
		}
		if (propertiesMenu != null) {
			propertiesMenu.setLocale(locale);
		}
		refresh();
	}

	@Override
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(getLangKey("file"));
		menuBar.add(fileMenu);

		JMenuItem saveMenuItem = new JMenuItem(getLangKey("save"));
		fileMenu.add(saveMenuItem);

		JMenuItem saveAsMenuItem = new JMenuItem(getLangKey("save_as"));
		fileMenu.add(saveAsMenuItem);

		JMenuItem newConfigMenuItem = new JMenuItem(getLangKey("new_configuration"));
		fileMenu.add(newConfigMenuItem);

		JMenu windowMenu = new JMenu(getLangKey("window"));
		menuBar.add(windowMenu);

		JMenuItem languagesMenuItem = new JMenuItem(getLangKey("languages"));
		windowMenu.add(languagesMenuItem);
		languagesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (langSettings != null) {
					langSettings.show();
				}
			}
		});

		JMenuItem propertiesMenuItem = new JMenuItem(getLangKey("properties"));
		windowMenu.add(propertiesMenuItem);
		propertiesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				propertiesMenu.show();
			}
		});

		JMenu infoMenu = new JMenu(getLangKey("info"));
		menuBar.add(infoMenu);

		return menuBar;
	}
}