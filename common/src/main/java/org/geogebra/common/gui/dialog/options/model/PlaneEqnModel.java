package org.geogebra.common.gui.dialog.options.model;

import java.util.Arrays;
import java.util.List;

import org.geogebra.common.kernel.EquationBehaviour;
import org.geogebra.common.kernel.LinearEquationRepresentable;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.kernelND.GeoElementND;
import org.geogebra.common.kernel.kernelND.GeoPlaneND;
import org.geogebra.common.main.App;
import org.geogebra.common.main.Localization;

public class PlaneEqnModel extends MultipleOptionsModel {

	private List<Integer> eqnValues;

	public PlaneEqnModel(App app) {
		super(app);

		eqnValues = Arrays.asList(LinearEquationRepresentable.Form.IMPLICIT.rawValue,
				LinearEquationRepresentable.Form.USER.rawValue);
	}

	@Override
	public boolean isValidAt(int index) {
		GeoElement geo = getGeoAt(index);
		if (forceInputForm(geo)) {
			return false;
		}
		return isValid(geo);
	}

	public static boolean forceInputForm(GeoElementND geo) {
		EquationBehaviour equationBehaviour = geo.getKernel().getEquationBehaviour();
		boolean isUserInput = geo.getParentAlgorithm() == null;
		if (geo instanceof LinearEquationRepresentable) {
			if (isUserInput) {
				return equationBehaviour.getLinearAlgebraInputEquationForm() != null
						&& !equationBehaviour.allowsChangingEquationFormsByUser();
			}
		}
		return false;
	}

	public static boolean isValid(GeoElement geo) {
		return (geo instanceof GeoPlaneND) && (geo.getDefinition() != null);
	}

	private GeoPlaneND getLineAt(int index) {
		return (GeoPlaneND) getObjectAt(index);
	}

	@Override
	public void updateProperties() {
		int value0 = getValueAt(0);
		boolean equalMode = true;
		for (int i = 0; i < getGeosLength(); i++) {
			if (getValueAt(i) != value0) {
				equalMode = false;
			}
		}

		getListener()
				.setSelectedIndex(equalMode ? eqnValues.indexOf(value0) : -1);
	}

	@Override
	public List<String> getChoices(Localization loc) {
		return Arrays.asList(loc.getMenu("ImplicitLineEquation"), // index 1
				loc.getMenu("InputForm"));
	}

	@Override
	protected void apply(int index, int value) {
		getLineAt(index).setMode(eqnValues.get(value));
		getGeoAt(index).updateRepaint();
	}

	@Override
	public int getValueAt(int index) {
		return getLineAt(index).getToStringMode();
	}

}
