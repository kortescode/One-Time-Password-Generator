package gui;

import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;

public interface IOTPConstants
{
	public Font			FONT_PANEL 	 = new Font("Arial", Font.PLAIN , 13);
	public FontMetrics	FONT_METRICS = new JPanel().getFontMetrics(FONT_PANEL);
}
