package edu.gatech.arktos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

@SuppressWarnings("serial")
public class ButtonExtended extends JComponent implements ComponentListener, FocusListener, MouseListener {

	protected static double ROUNDEDBORDERS_PERCENTAGE = 0.30;
	protected static Color  BORDER_NORMAL             = Color.getHSBColor(0, 0, 0.75f);
	protected static Color  BORDER_PRESSED            = Color.getHSBColor(0, 0, 0.70f);
	protected static Color  BORDER_SELECTED           = Color.getHSBColor(0, 0, 0.80f);
	protected static Color  BORDER_SELECTEDOVER       = Color.getHSBColor(0, 0, 0.85f);
	protected static Color  BACKGROUND_NORMAL         = Color.getHSBColor(0, 0, 0.88f);
	protected static Color  BACKGROUND_PRESSED        = Color.getHSBColor(0, 0, 0.82f);
	protected static Color  BACKGROUND_SELECTED       = Color.getHSBColor(0, 0, 0.93f);
	protected static Color  BACKGROUND_SELECTEDOVER   = Color.getHSBColor(0, 0, 0.98f);
	
	private Image currentNormalImage;
	private Image currentSelectedImage;
	private Image currentSelectedOverImage;
	private Image currentSelectedOverFocusedImage;
	private Image currentPressedImage;
	private Image currentPressedSelectedImage;
	private Image currentFocusedImage;
	private Image currentFocusedSelectedImage;
	private Image currentImage;
	
	private boolean        mouseDown = false;
	private boolean        mouseOver = false;
	private AbstractButton internalButton;
	private LabelExtended  label;

	public ButtonExtended(String text, Font f) {
		label = new LabelExtended(text, f);
		label.setHorizontalAlignment(LabelExtended.ALIGN_CENTER);

		add(label);
		label.setLocation(5, 4);

		commonInit();

		setSize(label.getWidth() + 10, label.getHeight() + 8);
	}

	private void commonInit() {
		internalButton = new JButton() {
			@Override
			public void repaint() {
			}

			@Override
			public void paint(Graphics g) {
			}
		};

		add(internalButton);

		internalButton.setLocation(0, 0);

		addComponentListener(this);
		internalButton.addFocusListener(this);
		internalButton.addMouseListener(this);
	}

	@Override
	public void setToolTipText(String tooltipText) {
		internalButton.setToolTipText(tooltipText);
	}

	public void setText(String text) {
		if (label == null)
			return;

		label.setText(text);
	}

	public String getText() {
		return label.getText();
	}

	private void updateImage() {
		if (mouseDown) {
			if (internalButton.isSelected() || mouseOver) {
				currentImage = currentPressedSelectedImage;
			} else {
				currentImage = currentPressedImage;
			}
		} else if (internalButton.hasFocus()) {
			if (internalButton.isSelected()) {
				if (mouseOver) {
					currentImage = currentSelectedOverFocusedImage;
				} else {
					currentImage = currentFocusedSelectedImage;
				}
			} else {
				if (mouseOver) {
					currentImage = currentSelectedOverFocusedImage;
				} else {
					currentImage = currentFocusedImage;
				}
			}
		} else if (internalButton.isSelected()) {
			if (mouseOver) {
				currentImage = currentSelectedOverImage;
			} else {
				currentImage = currentSelectedImage;
			}
		} else {
			if (mouseOver) {
				currentImage = currentSelectedImage;
			} else {
				currentImage = currentNormalImage;
			}
		}

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(currentImage, 0, 0, this);

		super.paint(g);
	}

	private void recalcImages() {
		int width = getWidth();
		int height = getHeight();
		int bordersArc = (int) ((width > height) ? height
				* ROUNDEDBORDERS_PERCENTAGE : width * ROUNDEDBORDERS_PERCENTAGE);

		currentNormalImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentSelectedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentSelectedOverImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentSelectedOverFocusedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentPressedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentPressedSelectedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentFocusedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		currentFocusedSelectedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D gNormal, gSelected, gSelectedO, gSelectedOF, gPressed, gPressedS, gFocused, gFocusedS;
		gNormal = (Graphics2D) currentNormalImage.getGraphics();
		gSelected = (Graphics2D) currentSelectedImage.getGraphics();
		gSelectedO = (Graphics2D) currentSelectedOverImage.getGraphics();
		gSelectedOF = (Graphics2D) currentSelectedOverFocusedImage
				.getGraphics();
		gPressed = (Graphics2D) currentPressedImage.getGraphics();
		gPressedS = (Graphics2D) currentPressedSelectedImage.getGraphics();
		gFocused = (Graphics2D) currentFocusedImage.getGraphics();
		gFocusedS = (Graphics2D) currentFocusedSelectedImage.getGraphics();
		gNormal.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gSelected.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gSelectedO.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gSelectedOF.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gPressed.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gPressedS.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gFocused.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gFocusedS.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		gNormal.setColor(BACKGROUND_NORMAL);
		gSelected.setColor(BACKGROUND_SELECTED);
		gSelectedO.setColor(BACKGROUND_SELECTEDOVER);
		gSelectedOF.setColor(BACKGROUND_SELECTEDOVER);
		gPressed.setColor(BACKGROUND_PRESSED);
		gPressedS.setColor(BACKGROUND_PRESSED);
		gFocused.setColor(BACKGROUND_NORMAL);
		gFocusedS.setColor(BACKGROUND_SELECTED);
		gNormal.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gSelected.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gSelectedO.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gSelectedOF.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gPressed.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gPressedS.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gFocused.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gFocusedS.fillRoundRect(0, 0, getWidth(), getHeight(), bordersArc,
				bordersArc);
		gNormal.setColor(BORDER_NORMAL);
		gSelected.setColor(BORDER_SELECTED);
		gSelectedO.setColor(BORDER_SELECTEDOVER);
		gSelectedOF.setColor(BORDER_SELECTEDOVER);
		gPressed.setColor(BORDER_PRESSED);
		gPressedS.setColor(BORDER_PRESSED);
		gFocused.setColor(BORDER_NORMAL);
		gFocusedS.setColor(BORDER_SELECTED);

		gNormal.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gSelected.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gSelectedO.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gSelectedOF.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gSelectedOF.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5,
				bordersArc - 2, bordersArc - 2);
		gPressed.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gPressed.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5,
				bordersArc - 2, bordersArc - 2);
		gPressedS.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gPressedS.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5,
				bordersArc - 2, bordersArc - 2);
		gFocused.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gFocused.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5,
				bordersArc - 2, bordersArc - 2);
		gFocusedS.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1,
				bordersArc, bordersArc);
		gFocusedS.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5,
				bordersArc - 2, bordersArc - 2);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);

		internalButton.setSize(width, height);
		if (label != null) {
			label.setSize(width - 10, height - 8);
		}

		recalcImages();
		updateImage();
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		internalButton.setSize(getWidth(), getHeight());

		recalcImages();
		updateImage();
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
		updateImage();
	}

	@Override
	public void focusLost(FocusEvent e) {
		updateImage();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		updateImage();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		updateImage();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseOver = true;
		updateImage();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseOver = false;
		updateImage();
	}
	
	public void addActionListener(ActionListener listener) {
		internalButton.addActionListener(listener);
	}
}