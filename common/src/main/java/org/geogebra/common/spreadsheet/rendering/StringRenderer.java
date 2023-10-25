package org.geogebra.common.spreadsheet.rendering;

import org.geogebra.common.awt.GColor;
import org.geogebra.common.awt.GFont;
import org.geogebra.common.awt.GGraphics2D;
import org.geogebra.common.factories.AwtFactory;
import org.geogebra.common.spreadsheet.core.CellRenderer;
import org.geogebra.common.util.shape.Rectangle;

/**
 * Renderer for plain text cells
 */
public class StringRenderer implements CellRenderer {
	private static GFont baseFont = AwtFactory.getPrototype()
			.newFont("serif", 0, 12);
	private static GGraphics2D measuringGraphics = AwtFactory.getPrototype()
			.createBufferedImage(100, 100, true).createGraphics();
	private static final int VERTICAL_PADDING = 3;

	@Override
	public void draw(Object data, int fontStyle, int offset, GGraphics2D graphics,
			Rectangle cellBorder) {
		graphics.setColor(GColor.BLACK);
		GFont font = baseFont.deriveFont(fontStyle);
		graphics.setFont(font);
		graphics.drawString(data.toString(), cellBorder.getMinX() + offset,
				cellBorder.getMaxY() - VERTICAL_PADDING);
	}

	@Override
	public boolean match(Object renderable) {
		return renderable instanceof String;
	}

	@Override
	public double measure(Object data, int fontStyle) {
		GFont font = baseFont.deriveFont(fontStyle);
		return (int) AwtFactory.getPrototype().newTextLayout(data.toString(),
				font, measuringGraphics.getFontRenderContext()).getAdvance();
	}
}
