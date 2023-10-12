package org.geogebra.common.spreadsheet.core;

public interface CopyPasteCutTabularData {
	void copy(TabularRange range, String content);

	void paste(TabularRange range, String content);

	void cut(TabularRange range, String content);
}
