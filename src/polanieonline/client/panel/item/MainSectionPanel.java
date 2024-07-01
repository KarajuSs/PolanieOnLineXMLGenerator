package polanieonline.client.panel.item;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;
import polanieonline.common.listener.SimpleActionListener;
import polanieonline.common.listener.SimpleDocumentListener;

public class MainSectionPanel extends SectionPanel {
	private ItemsPanel itemsPanel;

	private TitledBorder mainPanelBorder;
	private TitledBorder imagePanelBorder;

	private JLabel itemNameLabel;
	private JLabel itemClassLabel;
	private JLabel itemImageLabel;
	private JLabel itemImageTitleLabel;
	private JLabel itemDescriptionLabel;
	private JLabel imagePreviewLabel;

	private JTextField itemNameField;
	private JTextField itemSubclassField;
	private JComboBox<String> itemClassList;
	private JTextArea itemDescriptionArea;

	public MainSectionPanel(ItemsPanel itemsPanel, Locale locale, XMLConsole xmlConsole) {
		super(locale, xmlConsole);
		this.itemsPanel = itemsPanel;

		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());

		JPanel panel = new JPanel(new GridBagLayout());
		mainPanelBorder = BorderFactory.createTitledBorder(getTitleBorder());
		panel.setBorder(mainPanelBorder);

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(createMainPanel(panel), BorderLayout.NORTH);

		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(createImagePreviewPanel(), BorderLayout.NORTH);

		add(leftPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

		updateXMLConsole();
	}

	private JPanel createMainPanel(JPanel panel) {
		itemNameLabel = new JLabel(getItemNameLabel() + ":");
		addComponent(panel, itemNameLabel, 0, 0, 1, 1, GridBagConstraints.WEST);
		itemNameField = new JTextField(20);
		itemNameField.getDocument().addDocumentListener(new SimpleDocumentListener(() -> updateXMLConsole()));
		addComponent(panel, itemNameField, 1, 0, 2, 1, GridBagConstraints.EAST);

		itemClassLabel = new JLabel(getItemClassLabel() + ":");
		addComponent(panel, itemClassLabel, 0, 1, 1, 1, GridBagConstraints.WEST);
		itemClassList = new JComboBox<>(getItemClasses());
		itemClassList.addActionListener(new SimpleActionListener(() -> itemsPanel.updateAttributesPanel(), () -> updateXMLConsole()));
		addComponent(panel, itemClassList, 1, 1, 2, 1, GridBagConstraints.EAST);

		itemImageLabel = new JLabel(getItemImageLabel() + ":");
		addComponent(panel, itemImageLabel, 0, 2, 1, 1, GridBagConstraints.WEST);
		JButton uploadButton = new JButton(getItemImageLabel());
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("png", "png"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					itemSubclassField = new JTextField(selectedFile.getName().replaceFirst("[.][^.]+$", "")); // Save the image file name without extension as subclass
					ImageIcon imageIcon = new ImageIcon(selectedFile.getPath());
					Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
					itemImageLabel.setIcon(new ImageIcon(image));
					itemImageTitleLabel.setText(selectedFile.getName());
					updateXMLConsole();
				}
			}
		});
		addComponent(panel, uploadButton, 1, 2, 2, 1, GridBagConstraints.EAST);

		itemDescriptionLabel = new JLabel(getItemDescriptionLabel());
		addComponent(panel, itemDescriptionLabel, 0, 3, 1, 1, GridBagConstraints.NORTHWEST);
		itemDescriptionArea = new JTextArea(3, 20);
		itemDescriptionArea.getDocument().addDocumentListener(new SimpleDocumentListener(() -> updateXMLConsole()));
		addComponent(panel, new JScrollPane(itemDescriptionArea), 1, 3, 2, 1, GridBagConstraints.EAST);

		return panel;
	}

	private JPanel createImagePreviewPanel() {
		JPanel imagePanel = new JPanel(new BorderLayout());
		imagePanelBorder = BorderFactory.createTitledBorder(getPreviewNameLabel());
		imagePanel.setBorder(imagePanelBorder);
		imagePanel.setBackground(Color.WHITE);
		imagePanel.setPreferredSize(new Dimension(150, 150));

		itemImageLabel = new JLabel();
		itemImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemImageLabel.setVerticalAlignment(SwingConstants.CENTER);

		itemImageTitleLabel = new JLabel("", SwingConstants.CENTER);

		imagePreviewLabel = new JLabel(getPreviewNameLabel() + ":");
		imagePanel.add(imagePreviewLabel, BorderLayout.NORTH);
		imagePanel.add(itemImageLabel, BorderLayout.CENTER);
		imagePanel.add(itemImageTitleLabel, BorderLayout.SOUTH);

		return imagePanel;
	}

	private String[] getItemClasses() {
		String[] categories = {
			getLangKey("ammunition"), getLangKey("armor"), getLangKey("axe"), getLangKey("belts"), getLangKey("boots"), getLangKey("cloak"),
			getLangKey("club"), getLangKey("dagger"), getLangKey("glove"), getLangKey("helmet"), getLangKey("legs"),
			getLangKey("magia"), getLangKey("ranged"), getLangKey("ring"), getLangKey("shield"), getLangKey("sword"),
			getLangKey("wand"), getLangKey("whip")
		};
		return categories;
	}

	private String getItemNameLabel() {
		return getLangKey("item_name");
	}
	private String getItemClassLabel() {
		return getLangKey("item_category");
	}
	private String getItemImageLabel() {
		return getLangKey("upload_image");
	}
	private String getItemDescriptionLabel() {
		return getLangKey("description");
	}
	private String getPreviewNameLabel() {
		return getLangKey("preview");
	}

	public String getItemName() {
		return itemNameField.getText();
	}
	public String getItemSubclass() {
		return itemSubclassField != null ? itemSubclassField.getText() : "";
	}
	public String getItemClass() {
		String selectedCategory = (String) itemClassList.getSelectedItem();
		return itemsPanel.getItemClassesMap().getOrDefault(selectedCategory, "").toLowerCase();
	}
	public String getItemDescription() {
		return itemDescriptionArea.getText();
	}
	public JComboBox<String> getItemClassList() {
		return itemClassList;
	}

	@Override
	protected String getPanelTitle() {
		return "main";
	}

	public void refresh() {
		//setSectionLang(locale);

		itemNameLabel.setText(getItemNameLabel() + ":");
		itemClassLabel.setText(getItemClassLabel() + ":");
		itemImageLabel.setText(getItemImageLabel() + ":");
		itemDescriptionLabel.setText(getItemDescriptionLabel() + ":");
		imagePreviewLabel.setText(getPreviewNameLabel() + ":");

		mainPanelBorder.setTitle(getTitleBorder().getTitle());
		imagePanelBorder.setTitle(getPreviewNameLabel());

		itemClassList.removeAllItems();
		for (String clazz : getItemClasses()) {
			itemClassList.addItem(clazz);
		}

		revalidate();
		repaint();
		updateXMLConsole();
	}
}
