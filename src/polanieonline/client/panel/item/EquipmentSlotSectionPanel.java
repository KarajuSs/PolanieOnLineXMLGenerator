package polanieonline.client.panel.item;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import polanieonline.client.console.XMLConsole;
import polanieonline.client.panel.ItemsPanel;

public class EquipmentSlotSectionPanel extends SectionPanel {
	private ItemsPanel itemsPanel;

	private JCheckBox groundCheckBox;
	private JCheckBox contentCheckBox;
	private JCheckBox bagCheckBox;
	private JCheckBox tradeCheckBox;

	private JCheckBox armorCheckBox;
	private JCheckBox neckCheckBox;
	private JCheckBox headCheckBox;
	private JCheckBox cloakCheckBox;
	private JCheckBox lhandCheckBox;
	private JCheckBox rhandCheckBox;
	private JCheckBox fingerCheckBox;
	private JCheckBox beltCheckBox;
	private JCheckBox gloveCheckBox;
	private JCheckBox fingerbCheckBox;
	private JCheckBox legsCheckBox;
	private JCheckBox feetCheckBox;

	private JCheckBox[] allCheckBoxes;

	public EquipmentSlotSectionPanel(ItemsPanel itemsPanel, Locale locale, XMLConsole xmlConsole) {
		super(locale, xmlConsole);
		this.itemsPanel = itemsPanel;

		initUI();
	}

	@Override
	protected String getPanelTitle() {
		return "equipment_slots";
	}

	private void initUI() {
		setLayout(new BorderLayout());

		TitledBorder border = getTitleBorder();
		setBorder(border);

		JPanel slotPanel = new JPanel();
		slotPanel.setLayout(new GridLayout(8, 2));

		groundCheckBox = new JCheckBox(getLangKey("ground"));
		contentCheckBox = new JCheckBox(getLangKey("content"));
		bagCheckBox = new JCheckBox(getLangKey("bag"));
		tradeCheckBox = new JCheckBox(getLangKey("trade"));

		groundCheckBox.setSelected(true);
		contentCheckBox.setSelected(true);
		bagCheckBox.setSelected(true);
		tradeCheckBox.setSelected(true);

		neckCheckBox = new JCheckBox(getLangKey("neck"));
		headCheckBox = new JCheckBox(getLangKey("head"));
		cloakCheckBox = new JCheckBox(getLangKey("cloak"));
		lhandCheckBox = new JCheckBox(getLangKey("lhand"));
		armorCheckBox = new JCheckBox(getLangKey("armor"));
		rhandCheckBox = new JCheckBox(getLangKey("rhand"));
		fingerCheckBox = new JCheckBox(getLangKey("finger"));
		beltCheckBox = new JCheckBox(getLangKey("pas"));
		gloveCheckBox = new JCheckBox(getLangKey("glove"));
		fingerbCheckBox = new JCheckBox(getLangKey("fingerb"));
		legsCheckBox = new JCheckBox(getLangKey("legs"));
		feetCheckBox = new JCheckBox(getLangKey("feet"));

		allCheckBoxes = new JCheckBox[] {
				groundCheckBox, contentCheckBox, bagCheckBox, tradeCheckBox,
				neckCheckBox, headCheckBox, cloakCheckBox, lhandCheckBox,
				armorCheckBox, rhandCheckBox, fingerCheckBox, beltCheckBox,
				gloveCheckBox, fingerbCheckBox, legsCheckBox, feetCheckBox
		};

		for (JCheckBox checkBox : allCheckBoxes) {
			checkBox.addActionListener(e -> updateXMLConsole());
			slotPanel.add(checkBox);
		}

		add(slotPanel, BorderLayout.CENTER);
	}

	public String[] getSelectedSlots() {
		List<String> slots = new ArrayList<>();
		if (groundCheckBox.isSelected()) slots.add("ground");
		if (contentCheckBox.isSelected()) slots.add("content");
		if (bagCheckBox.isSelected()) slots.add("bag");
		if (tradeCheckBox.isSelected()) slots.add("trade");

		if (armorCheckBox.isSelected()) slots.add("armor");
		if (neckCheckBox.isSelected()) slots.add("neck");
		if (headCheckBox.isSelected()) slots.add("head");
		if (cloakCheckBox.isSelected()) slots.add("cloak");
		if (lhandCheckBox.isSelected()) slots.add("lhand");
		if (rhandCheckBox.isSelected()) slots.add("rhand");
		if (fingerCheckBox.isSelected()) slots.add("finger");
		if (beltCheckBox.isSelected()) slots.add("pas");
		if (gloveCheckBox.isSelected()) slots.add("glove");
		if (fingerbCheckBox.isSelected()) slots.add("fingerb");
		if (legsCheckBox.isSelected()) slots.add("legs");
		if (feetCheckBox.isSelected()) slots.add("feet");

		return slots.toArray(new String[0]);
	}
	
	public void resetCheckBoxes() {
		for (JCheckBox checkbox : allCheckBoxes) {
			checkbox.setSelected(false);
		}
		//updateCheckBoxesForClass(itemsPanel.getSelectedItemClass());

		groundCheckBox.setSelected(true);
		contentCheckBox.setSelected(true);
		bagCheckBox.setSelected(true);
		tradeCheckBox.setSelected(true);
	}
	
	public void updateCheckBoxesForClass(String clazz) {
		resetCheckBoxes();

		Map<String, String> itemClassesMap = itemsPanel.getItemClassesMap();
		String selectedCategory = itemClassesMap.get(clazz);

		if (selectedCategory != null) {
			switch (selectedCategory) {
				case "ammunition":
				case "axe":
				case "club":
				case "dagger":
				case "magia":
				case "sword":
				case "whip":
				case "shield":
				case "wand":
					lhandCheckBox.setSelected(true);
					rhandCheckBox.setSelected(true);
					break;
				case "armor":
					armorCheckBox.setSelected(true);
					break;
				case "ring":
					fingerCheckBox.setSelected(true);
					fingerbCheckBox.setSelected(true);
					break;
				case "boots":
					feetCheckBox.setSelected(true);
					break;
				case "legs":
					legsCheckBox.setSelected(true);
					break;
				case "cloak":
					cloakCheckBox.setSelected(true);
					break;
				case "helmet":
					headCheckBox.setSelected(true);
					break;
				case "belts":
					beltCheckBox.setSelected(true);
					break;
				case "glove":
					gloveCheckBox.setSelected(true);
					break;
				case "ranged":
					lhandCheckBox.setSelected(true);
					break;
			}
		}
	}

	public void refresh() {
		((TitledBorder) getBorder()).setTitle(getTitleBorder().getTitle());

		groundCheckBox.setText(getLangKey("ground"));
		contentCheckBox.setText(getLangKey("content"));
		bagCheckBox.setText(getLangKey("bag"));
		tradeCheckBox.setText(getLangKey("trade"));

		armorCheckBox.setText(getLangKey("armor"));
		neckCheckBox.setText(getLangKey("neck"));
		headCheckBox.setText(getLangKey("head"));
		cloakCheckBox.setText(getLangKey("cloak"));
		lhandCheckBox.setText(getLangKey("lhand"));
		rhandCheckBox.setText(getLangKey("rhand"));
		fingerCheckBox.setText(getLangKey("finger"));
		beltCheckBox.setText(getLangKey("pas"));
		gloveCheckBox.setText(getLangKey("glove"));
		fingerbCheckBox.setText(getLangKey("fingerb"));
		legsCheckBox.setText(getLangKey("legs"));
		feetCheckBox.setText(getLangKey("feet"));

		resetCheckBoxes();
		revalidate();
		repaint();
		updateXMLConsole();
	}
}
