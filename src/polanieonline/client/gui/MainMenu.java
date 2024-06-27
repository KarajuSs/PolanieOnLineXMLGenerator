package polanieonline.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import polanieonline.client.gui.windowmenu.LanguagesMenu;
import polanieonline.client.gui.windowmenu.PropertiesMenu;
import polanieonline.common.language.LanguageSystem;

public class MainMenu extends LanguageSystem {
	private LanguagesMenu languagesMenu;
	private PropertiesMenu propertiesMenu;
	private JFrame frame;

	public MainMenu(Locale locale) {
		super(locale);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
		this.languagesMenu = new LanguagesMenu(getLocale(), this);
		this.propertiesMenu = new PropertiesMenu(getLocale());
	}

	@Override
	public void setLocale(Locale locale) {
		super.setLocale(locale);
		if (languagesMenu != null) {
			languagesMenu.setLocale(locale);
		}
		if (propertiesMenu != null) {
			propertiesMenu.setLocale(locale);
		}
		updateMenu();
	}

	public JMenuBar createMenuBar() {
		// Utworzenie menu bar
		JMenuBar menuBar = new JMenuBar();

		// Utworzenie menu "File"
		JMenu fileMenu = new JMenu(getWord("file"));
		menuBar.add(fileMenu);
		
		// Dodanie elementów do menu "File"
		JMenuItem saveMenuItem = new JMenuItem(getWord("save"));
		fileMenu.add(saveMenuItem);

		JMenuItem saveAsMenuItem = new JMenuItem(getWord("save_as"));
		fileMenu.add(saveAsMenuItem);

		JMenuItem newConfigMenuItem = new JMenuItem(getWord("new_configuration"));
		fileMenu.add(newConfigMenuItem);

		// Utworzenie menu "Window"
		JMenu windowMenu = new JMenu(getWord("window"));
		menuBar.add(windowMenu);

		// Dodanie elementów do menu "Window"
		JMenuItem languagesMenuItem = new JMenuItem(getWord("languages"));
		windowMenu.add(languagesMenuItem);
		// Dodanie akcji do elementów menu
		languagesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				languagesMenu.show();
			}
		});

		JMenuItem propertiesMenuItem = new JMenuItem(getWord("properties"));
		windowMenu.add(propertiesMenuItem);
		propertiesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				propertiesMenu.show();
			}
		});

		// Utworzenie menu "Info"
		JMenu infoMenu = new JMenu(getWord("info"));
		menuBar.add(infoMenu);

		return menuBar;
	}

	public void updateMenu() {
		if (frame != null) {
			frame.setJMenuBar(createMenuBar());
			frame.revalidate();
			frame.repaint();
		}
	}
}
