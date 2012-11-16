package edu.gatech.arktos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class LabelExtended extends JComponent {

	    public static final int ALIGN_LEFT   = 0;
	    public static final int ALIGN_CENTER = 1;
	    public static final int ALIGN_RIGHT  = 2;
	
	    protected static Color FOREGROUND_COLOR = Color.BLACK;

	    private boolean auto;
	    private boolean wrap;
	    private String text;
	    private JLabel label;
	    private JLabel label2;
	    private int alignment;
	    
	    public LabelExtended(String text) {
	        super();
	        init(text, null);
	    }
	    
	    public LabelExtended(String text, Font font) {
	        super();
	        init(text, font);
	    }
	    
	    private void init(String s, Font font) {
	        label = new JLabel(s);
	        label.setForeground(FOREGROUND_COLOR);
	        label.setFocusable(false);
	        label.setBorder(null);
	        label2 = new JLabel(s);
	        label2.setForeground(FOREGROUND_COLOR);
	        label2.setFocusable(false);
	        label2.setBorder(null);
	        label2.setVisible(false);
	        
	        text = s;
	        wrap = true;
	        auto = false;
	        alignment = 0;
	        
	        if (font != null) {
	        	label.setFont(font);
		    	label2.setFont(font);
	        }
	        add(label);
	        add(label2);
	        setFocusable(false);
	        setBorder(null);
	        pack();
	        resizeLabel();
	    }
	    
	    public void setFont(Font f) {
	    	label.setFont(f);
	    	label2.setFont(f);
	    	
	    	resizeLabel();
	    }
	    
	    public void setBold(boolean bold) {
	        label2.setVisible(bold);
	    }
	    
	    public void setText(String text) {
	        this.text = text;
	        
	        if (wrap) {
	            wrap();
	        }
	        else {
	            if (auto) {
	                pack();
	            }
	            
	            label.setText(text);
	            label.setText(text);
	            resizeLabel();
	        }
	        
	        positionLabel();
	    }
	    
	    public String getText() {
	        return text;
	    }
	    
	    @Override
	    public void setSize(int width, int height) {
	        setSize(width, height, true);
	    }
	    
	    private void setSize(int width, int height, boolean check) {
	        if (check && (auto || (getWidth() == width && getHeight() == height))) return;
	        
	        super.setSize(width, height);
	        
	        if (wrap) {
	            wrap();
	        }
	        
	        positionLabel();
	    }

	    public void setAutoWrap(boolean autoWrap) {
	        wrap = autoWrap;
	        if (autoWrap) wrap();
	        else {
	            label.setText(text);
	            label2.setText(text);
	            resizeLabel();
	            positionLabel();
	        }
	    }
	    
	    public void setAutoSize(boolean autoSize) {
	        auto = autoSize;
	        if (autoSize) pack();
	    }

	    public void wrap() {
	        FontMetrics fm = getFontMetrics(getFont());
	        int widthLimit = getWidth() - 2;
	        
	        if (text == null || fm.stringWidth("...") > widthLimit) return;
	        
	        String drawString = text;
	        
	        while (fm.stringWidth(drawString) > widthLimit) {
	            drawString = drawString.substring(0, drawString.length() - 1);
	        }
	        
	        if (drawString.length() != getText().length()) {
	            while (fm.stringWidth(drawString.concat("...")) > widthLimit) {
	                drawString = drawString.substring(0, drawString.length() - 1);
	            }
	            drawString = drawString.concat("...");
	            
	            label.setText(drawString);
	            label2.setText(drawString);
	        }
	        else {
	            label.setText(text);
	            label2.setText(text);
	        }
	        
	        resizeLabel();
	        positionLabel();
	    }

	    public void pack() {
	        FontMetrics fm = getFontMetrics(getFont());
	        
	        if (text == null) return;
	        
	        int width = fm.stringWidth(text) + 9;
	        int height = fm.getMaxAscent() + fm.getMaxDescent() + 7;
	        setSize(Math.max(width, 1), Math.max(height, 1), false);
	        
	        positionLabel();
	    }

	    public void setHorizontalAlignment(int textAlignment) {
	        if (textAlignment < 0 || textAlignment > 3) return;
	        
	        alignment = textAlignment;
	        
	        positionLabel();
	    }
	    
	    private void positionLabel() {
	        int vpos = getHeight()/2 - label.getHeight()/2;
	        
	        if (alignment == ALIGN_LEFT || (alignment == ALIGN_CENTER && label.getWidth() >= getWidth())) {
	            label.setLocation(0, vpos);
	        }
	        else if (alignment == ALIGN_CENTER) {
	            label.setLocation(getWidth()/2 - label.getWidth()/2, vpos);
	        }
	        else {
	            label.setLocation(getWidth() - label.getWidth(), vpos);
	        }
	        
	        label2.setLocation(label.getX() + 1, label.getY());
	    }
	    
	    private void resizeLabel() {
	        FontMetrics fm = getFontMetrics(getFont());
	        
	        String text = label.getText();
	        if (text == null) return;
	        
	        label.setSize(Math.max(fm.stringWidth(text), 1), fm.getMaxAscent() + fm.getMaxDescent());
	        label2.setSize(label.getWidth(), label.getHeight());
	    }

	    @Override
	    public void setOpaque(boolean opaque) {
	        super.setOpaque(opaque);
	        label.setOpaque(opaque);
	        label2.setOpaque(opaque);
	    }

	    @Override
	    public Font getFont() {
	        return label.getFont();
	    }

	    @Override
	    public void setEnabled(boolean enabled) {
	        label.setEnabled(enabled);
	        label2.setEnabled(enabled);
	    }
	    
	}
