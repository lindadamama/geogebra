package org.geogebra.common.contextmenu;

import static org.geogebra.regexp.shared.contextmenu.ContextMenuFactory.makeTableValuesContextMenu;
import static org.geogebra.regexp.shared.contextmenu.TableValuesContextMenuItem.Item.*;
import static org.junit.Assert.assertEquals;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geogebra.common.BaseUnitTest;
import org.geogebra.common.gui.view.table.InvalidValuesException;
import org.geogebra.common.gui.view.table.TableValuesModel;
import org.geogebra.common.gui.view.table.TableValuesView;
import org.geogebra.common.kernel.geos.GeoFunction;
import org.geogebra.common.kernel.geos.GeoLine;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.GeoNumeric;
import org.geogebra.common.kernel.kernelND.GeoEvaluatable;
import org.geogebra.regexp.shared.contextmenu.TableValuesContextMenuItem;
import org.junit.Before;
import org.junit.Test;

public class TableValuesContextMenuTests extends BaseUnitTest {
	private TableValuesView tableValuesView;
	private TableValuesModel tableValuesModel;

	@Before
	public void setup() {
		super.setup();
		tableValuesView = new TableValuesView(getKernel());
		getKernel().attach(tableValuesView);
		tableValuesModel = tableValuesView.getTableValuesModel();
	}

	@Test
	public void testInScientificCalculator() {
		GeoEvaluatable geoEvaluatable = new GeoLine(getConstruction());

		assertEquals(
				List.of(Edit.toContextMenuItem(),
						ClearColumn.toContextMenuItem()),
				makeTableValuesContextMenu(geoEvaluatable, 0, tableValuesModel, true, false)
		);
	}

	@Test
	public void testFirstColumn() {
		GeoEvaluatable geoEvaluatable = new GeoLine(getConstruction());

		assertEquals(
				List.of(Edit.toContextMenuItem(),
						ClearColumn.toContextMenuItem(),
						ImportData.toContextMenuItem(),
						Separator.toContextMenuItem(),
						Statistics1.toContextMenuItem(new String[]{ "x" })),
				makeTableValuesContextMenu(geoEvaluatable, 0, tableValuesModel, false, false)
		);
	}

	@Test
	public void testFirstColumnInExamMode() {
		GeoEvaluatable geoEvaluatable = new GeoLine(getConstruction());

		assertEquals(
				List.of(Edit.toContextMenuItem(),
						ClearColumn.toContextMenuItem(),
						Separator.toContextMenuItem(),
						Statistics1.toContextMenuItem(new String[]{ "x" })),
				makeTableValuesContextMenu(geoEvaluatable, 0, tableValuesModel, false, true)
		);
	}

	@Test
	public void testWithFunctionColumn() throws InvalidValuesException {
		tableValuesView.setValues(-2.0, 2.0, 1.0);
		GeoFunction geoFunction = new GeoFunction(getConstruction());
		tableValuesView.addAndShow(geoFunction);

		assertEquals(
				List.of(HidePoints.toContextMenuItem(),
						Edit.toContextMenuItem(),
						RemoveColumn.toContextMenuItem()),
				makeTableValuesContextMenu(geoFunction, 1, tableValuesModel, false, false)
		);
	}

	@Test
	public void testWithVisibleGeoListPoints() throws InvalidValuesException {
		tableValuesView.setValues(0.0, 2.0, 1.0);
		GeoList geoList = new GeoList(getConstruction());
		geoList.add(new GeoNumeric(getConstruction(), 1.0));
		geoList.add(new GeoNumeric(getConstruction(), 2.0));
		tableValuesView.addAndShow(geoList);

		assertEquals(
				List.of(HidePoints.toContextMenuItem(),
						RemoveColumn.toContextMenuItem(),
						Separator.toContextMenuItem(),
						Statistics1.toContextMenuItem(new String[]{ "y_{1}" }),
						Statistics2.toContextMenuItem(new String[] { "x y_{1}" }),
						Regression.toContextMenuItem()),
				makeTableValuesContextMenu(geoList, 1, tableValuesModel, false, false)
		);
	}

	@Test
	public void testWithHiddenGeoListPoints() throws InvalidValuesException {
		tableValuesView.setValues(0.0, 2.0, 1.0);
		GeoList geoList = new GeoList(getConstruction());
		geoList.add(new GeoNumeric(getConstruction(), 1.0));
		geoList.add(new GeoNumeric(getConstruction(), 2.0));
		tableValuesView.addAndShow(geoList);
		geoList.setPointsVisible(false);

		assertEquals(
				List.of(ShowPoints.toContextMenuItem(),
						RemoveColumn.toContextMenuItem(),
						Separator.toContextMenuItem(),
						Statistics1.toContextMenuItem(new String[]{ "y_{1}" }),
						Statistics2.toContextMenuItem(new String[]{ "x y_{1}" }),
						Regression.toContextMenuItem()),
				makeTableValuesContextMenu(geoList, 1, tableValuesModel, false, false)
		);
	}

	@Test
	public void testStatisticsItemTitleSubscript() {
		TableValuesContextMenuItem item = Statistics1.toContextMenuItem(new String[]{ "y_{1}" });
		AttributedString title = item.getLocalizedTitle(getLocalization());

		assertEquals("y1 Statistics", parseAttributedString(title));
		assertEquals(Set.of(TextAttribute.SUPERSCRIPT), title.getIterator().getAllAttributeKeys());

		AttributedCharacterIterator iterator = title.getIterator();

		iterator.setIndex(0);
		assertEquals(Map.of(), iterator.getAttributes());

		iterator.setIndex(1);
		assertEquals(
				Map.of(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB),
				iterator.getAttributes()
		);

		iterator.setIndex(2);
		assertEquals(Map.of(), iterator.getAttributes());
	}

	private String parseAttributedString(AttributedString text) {
		StringBuilder stringBuilder = new StringBuilder();
		AttributedCharacterIterator iterator = text.getIterator();
		while (iterator.current() != CharacterIterator.DONE) {
			stringBuilder.append(iterator.current());
			iterator.next();
		}
		return stringBuilder.toString();
	}
}
