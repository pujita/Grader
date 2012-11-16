package edu.gatech.arktos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class JComboBoxThemed<E> extends JComponent implements FocusListener, MouseListener, ComponentListener, PopupMenuListener {
	
	protected static double ROUNDEDBORDERS_PERCENTAGE = 0.30;
    protected static Color  FOREGROUND_COLOR          = Color.BLACK;
    protected static Color  BACKGROUND_NORMAL         = Color.getHSBColor(0, 0, 0.88f);
    protected static Color  BACKGROUND_FOCUSED        = Color.getHSBColor(0, 0, 0.93f);
    protected static Color  BACKGROUND_SELECTED       = Color.getHSBColor(0.581f, 0.2f, 0.8f);
    protected static Color  BORDER_NORMAL             = Color.getHSBColor(0, 0, 0.75f);
    protected static Color  BORDER_FOCUSED            = Color.getHSBColor(0, 0, 0.80f);

    private JComboBox<E> comboBox;
    private boolean   focused     = false;
    private boolean   mouseOver   = false;
    private Image     normalImage;
    private Image     focusedImage;
    private Image     selectedImage;
    private Image     current;
    
    private JComboBoxUI theme;
    
    
    public void setFont(Font font) {
    	comboBox.setFont(font);
    }

    public JComboBoxThemed() {
        setOpaque(false);

        comboBox = new JComboBox<E>();

        theme = new JComboBoxUI();
        
        comboBox.setUI(theme);
        comboBox.setForeground(FOREGROUND_COLOR);
        comboBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                updateImage();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                updateImage();
            }
        });
        comboBox.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateImage();
            }
        });
        comboBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (mouseOver == false) {
                    mouseOver = true;
                    updateImage();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Point parentLocation = JComboBoxThemed.this.getLocationOnScreen();
                    Point mouseLocation = e.getLocationOnScreen();

                    if (mouseLocation.getX() >= parentLocation.getX() + getWidth() || mouseLocation.getY() >= parentLocation.getY() + getHeight()
                    		|| mouseLocation.getX() <= parentLocation.getX() || mouseLocation.getY() <= parentLocation.getY()) {
                        mouseOver = false;
                        updateImage();
                    }
                }
                catch (Throwable ex) {
                }
            }
        });
        
        add(comboBox);

        addFocusListener(this);
        addMouseListener(this);
        addComponentListener(this);
        comboBox.addPopupMenuListener(this);
    }
    
    @Override
    public void focusGained(FocusEvent e) {
        comboBox.requestFocusInWindow();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        comboBox.requestFocusInWindow();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOver = true;
        updateImage();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            Point location = getLocationOnScreen();
            Point mouseLocation = e.getLocationOnScreen();

            if (mouseLocation.getX() >= location.getX() + getWidth() || mouseLocation.getY() >= location.getY() + getHeight()
            		|| mouseLocation.getX() <= location.getX() || mouseLocation.getY() <= location.getY()) {
            	
                mouseOver = false;
                updateImage();
            }
        }
        catch (Throwable ex) {
        }
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        int width = getWidth();
        int height = getHeight();
        int bordersArc = (int)((width > height) ? height*ROUNDEDBORDERS_PERCENTAGE : width*ROUNDEDBORDERS_PERCENTAGE);
        int internalWidth = width - bordersArc;
        int internalHeight = height - bordersArc;
        comboBox.setSize(internalWidth, internalHeight);

        normalImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        focusedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        selectedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D gNormal, gFocused, gSelected;
        gNormal  = (Graphics2D)normalImage.getGraphics();
        gFocused = (Graphics2D)focusedImage.getGraphics();
        gSelected = (Graphics2D)selectedImage.getGraphics();
        gNormal.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gFocused.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gSelected.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gNormal.setColor( BACKGROUND_NORMAL);
        gFocused.setColor(BACKGROUND_FOCUSED);
        gSelected.setColor(BACKGROUND_SELECTED);
        gNormal.fillRoundRect( 0, 0, getWidth(), getHeight(), bordersArc, bordersArc);
        gFocused.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc, bordersArc);
        gSelected.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc, bordersArc);
        gNormal.setColor( BORDER_NORMAL);
        gFocused.setColor(BORDER_FOCUSED);
        gSelected.setColor(BORDER_FOCUSED);
        gNormal.drawRoundRect( 0, 0, getWidth() - 1, getHeight() - 1, bordersArc, bordersArc);
        gFocused.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, bordersArc, bordersArc);
        gSelected.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, bordersArc, bordersArc);
        
        comboBox.setLocation(bordersArc/2, bordersArc/2);

        updateImage();
    }

    @Override
    public boolean requestFocusInWindow() {
        return comboBox.requestFocusInWindow();
    }

    public void addActionListener(ActionListener al) {
        comboBox.addActionListener(al);
    }

    private void updateImage() {
    	if (comboBox.hasFocus()) {
    		current = selectedImage;
    		comboBox.setBackground(BACKGROUND_SELECTED);
    	}
    	else if (focused || mouseOver || !comboBox.isEnabled()) {
            current = focusedImage;
            comboBox.setBackground(BACKGROUND_FOCUSED);
        }
        else {
            current = normalImage;
            comboBox.setBackground(BACKGROUND_NORMAL);
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(current, 0, 0, this);
        
        super.paint(g);
    }
    
    
    public void addItem(E item) {
    	comboBox.addItem(item);
    }
    

    @Override
    public void focusLost(FocusEvent e) {
    }

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		comboBox.dispatchEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		theme.setPopupVisible(true);
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		theme.setPopupVisible(false);
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		theme.setPopupVisible(false);
	}
	
}