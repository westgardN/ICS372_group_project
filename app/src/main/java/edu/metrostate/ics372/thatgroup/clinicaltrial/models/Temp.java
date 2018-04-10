/**
 * File: Temp.java
 */
package edu.metrostate.ics372.thatgroup.clinicaltrial.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;

/**
 * A Temp reading consists of a single double as its value and an
 * optional unit of measurement
 *
 * @author That Group
 * @see
 */
public class Temp extends Reading {
    private static final String MSG_TEMP_TAKEN = "Temp taken";
    private static final String MSG_TEMP_IS = " is: ";
    private static final String MSG_TEMP_ON = " on ";

    private static final long serialVersionUID = -1060997244536301933L;
    protected UnitValue value;

    /**
     * Initializes an empty temperature reading with the temperature initialized
     * to Double.NEGATIVE_INFINITY.
     */
    public Temp() {
        this(null, null, null, Double.NEGATIVE_INFINITY, null);
    }

    /**
     * Initializes a temperature reading based on the specified parameters.
     *
     * @param patientId The ID of the patient this reading is for.
     * @param id        The ID of this reading.
     * @param date      The date and time the reading was taken.
     * @param value     The value of the reading. Must be a Number
     * @param clinicId  The ID of the clinic associated with this object.
     */
    public Temp(String patientId, String id, LocalDateTime date, Object value, String clinicId) {
        super(patientId, id, date, value, clinicId);
    }

    /**
     * Returns the temperature of this reading as a UnitValue value.
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of this reading. The value can either be a String in Temp Unit format, it can be just
     * be a Doulbe if no unit of measurement is needed, or it can be an instance of an UnitValue.
     *
     * @param value the new value for this reading. If value is null, then the value of this unit value is
     *              set to Double.NEGATIVE_INFINITY.
     * @throws IllegalArgumentException indicates that value is not a UnitValue, String, or Number
     */
    @Override
    public void setValue(Object value) {
        if (value != null) {
            if (value instanceof UnitValue == false && value instanceof Number == false && value instanceof String == false) {
                throw new IllegalArgumentException(Strings.ERR_VALUE_NAN);
            }

            if (value instanceof UnitValue) {
                this.value = ((UnitValue) value).clone();
            } else if (value instanceof String) {
                this.value = new UnitValue((String) value);
            } else if (value instanceof Number) {
                this.value = new UnitValue((Number) value);
            }
        } else {
            this.value = new UnitValue(Double.NEGATIVE_INFINITY);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(MSG_TEMP_TAKEN);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        if (date != null) {
            String formattedDateTime = date.format(formatter);
            builder.append(MSG_TEMP_ON);
            builder.append(formattedDateTime);
        }
        builder.append(MSG_TEMP_IS);
        builder.append(getValue());
        return builder.toString();
    }
}
