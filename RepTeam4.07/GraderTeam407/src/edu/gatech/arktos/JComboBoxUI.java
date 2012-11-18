package edu.gatech.arktos;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class JComboBoxUI extends BasicComboBoxUI {
	
	private JButton b;
	private ImageIcon expanded;
	private ImageIcon collapsed;
	
	public JComboBoxUI() {
		String pathSeparator = System.getProperty("file.separator");
		
		try {
			expanded = new ImageIcon(ResourcesDispatcher.getImage("resources" + pathSeparator + "arrowup.png"));
			collapsed = new ImageIcon(ResourcesDispatcher.getImage("resources" + pathSeparator + "arrowdown.png"));
		}
		catch (ResourceException e) {
			e.printStackTrace();
		}
	}
	
	public void setPopupVisible(boolean v) {
		if (v) {
			b.setIcon(expanded);
		}
		else {
			b.setIcon(collapsed);
		}
	}
	
	public JButton createArrowButton() {
		b = new JButton();
		b.setBorder(null);
		b.setOpaque(true);
		b.setFocusable(false);
		
		try {
			b.setIcon(new ImageIcon(ResourcesDispatcher.getImage("resources" + System.getProperty("file.separator") + "arrowdown.png")));
		} catch (ResourceException e) {
			e.printStackTrace();
		}
		
		b.setContentAreaFilled(false);
		
		return b;
	}
	
	public ComboPopup createPopup() {
		BasicComboPopup popup = new BasicComboPopup(this.comboBox);
		
		popup.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(0, 0, 0.75f)));
		
		return popup;
	}
}
