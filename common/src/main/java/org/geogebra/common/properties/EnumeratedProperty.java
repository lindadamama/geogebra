package org.geogebra.common.properties;

import java.util.List;

import org.geogebra.common.exam.restrictions.ValueFilter;

/**
 * A property whose value is one of an array of predefined values,
 * similarly to an enumeration, where the value is one a finite set of possible values.
 * {@link ValuedProperty#setValue(Object)} will now throw a RuntimeException
 * if the value is not member of the {@link EnumeratedProperty#getValues()} ()}
 * array.
 */
public interface EnumeratedProperty<V> extends ValuedProperty<V> {

	/**
	 * Gets the list of available values for this property.
	 * When calling {@link EnumeratedProperty#setValue(Object)}
	 * the value must be one of these values, otherwise it throws a RuntimeException.
	 * @return predefined values, except those filtered by {@link ValueFilter} during exam mode
	 */
	List<V> getValues();

	/**
	 * Adds a {@link ValueFilter} to this property which can modify the list of available values
	 * returned by {@link EnumeratedProperty#getValues()}.
	 *
	 * @param valueFilter the {@link ValueFilter} to be added.
	 */
	void addValueFilter(ValueFilter valueFilter);

	/**
	 * Removes a previously added {@link ValueFilter} from this property.
	 * Once removed, the filter will no longer affect the list of available values
	 * returned by {@link EnumeratedProperty#getValues()}.
	 *
	 * @param valueFilter the {@link ValueFilter} to be removed.
	 */
	void removeValueFilter(ValueFilter valueFilter);

	/**
	 * Get the index of the value of this property in the array
	 * {@link EnumeratedProperty#getValues()}. Returns -1 if the value is null.
	 * @return index of value in the array or -1.
	 */
	int getIndex();

	/**
	 * Set the value of this property to the index-th element in the array
	 * {@link EnumeratedProperty#getValues()}. Might throw {@link ArrayIndexOutOfBoundsException}
	 * if the index is invalid with respect to the values array.
	 * @param index index of the value
	 */
	void setIndex(int index);
}
