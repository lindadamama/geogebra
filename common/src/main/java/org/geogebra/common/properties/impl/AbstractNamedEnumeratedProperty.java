package org.geogebra.common.properties.impl;

import java.util.Map;

import org.geogebra.common.main.Localization;
import org.geogebra.common.properties.NamedEnumeratedProperty;

/**
 * Base class for enumerated properties whose values have names associated with them.
 * When overriding this class, make sure to call
 * {@link AbstractNamedEnumeratedProperty#setNamedValues(Map)}
 * at some point in the constructor.
 */
public abstract class AbstractNamedEnumeratedProperty<V> extends AbstractEnumeratedProperty<V>
		implements NamedEnumeratedProperty<V> {

	private Map<V, String> valueNameTranslationIds;

	/**
	 * Constructs an AbstractNamedEnumeratedProperty
	 * @param localization the localization used
	 * @param name the name of the property
	 */
	public AbstractNamedEnumeratedProperty(Localization localization, String name) {
		super(localization, name);
	}

	/**
	 * Use this method to set the values of the property. These values are
	 * not localized.
	 * @param values localized values of this property // TODO DOCUMENTATION
	 */
	protected void setNamedValues(Map<V, String> values) {
		setValues(values.keySet().stream().toList());
		this.valueNameTranslationIds = values;
	}

	@Override
	public String[] getValueNames() {
		ensureValueNamesPresent();
		return getValues().stream().map(value -> getLocalization().getMenu(
				valueNameTranslationIds.get(value)
		)).toArray(String[]::new);
	}

	private void ensureValueNamesPresent() {
		if (valueNameTranslationIds == null) {
			throw new RuntimeException("Set values must be called in the constructor.");
		}
	}

}
