package edu.metrostate.ics372.thatgroup.clinicaltrial.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.metrostate.ics372.thatgroup.clinicaltrial.resources.Strings;


/**
 * The ReadingFactory is a factory class for creating Reading objects of various types.
 * <p>
 * You can use ReadingFactory to get the type of an existing Reading instance or use
 * it to create type specific readings.
 *
 * @author That Group
 * @see edu.metrostate.ics372.thatgroup.clinicaltrial.models.Reading
 */
public class ReadingFactory {
    public static final String PRETTY_WEIGHT = "Weight";
    public static final String PRETTY_TEMPERATURE = "Temperature";
    public static final String PRETTY_STEPS = "Steps";
    public static final String PRETTY_BLOOD_PRESSURE = "Blood Pressure";
    public static final String XML_BLOOD_PRESSURE = "BloodPressure";
    public static final String WEIGHT = "weight";
    public static final String TEMPERATURE = "temp";
    public static final String STEPS = "steps";
    public static final String BLOOD_PRESSURE = "blood_press";
    protected static List<String> readingTypes;
    protected static List<String> prettyReadingTypes;

    static {
        readingTypes = new ArrayList<>();
        prettyReadingTypes = new ArrayList<>();

        readingTypes.add(BLOOD_PRESSURE);
        readingTypes.add(STEPS);
        readingTypes.add(TEMPERATURE);
        readingTypes.add(WEIGHT);

        prettyReadingTypes.add(PRETTY_BLOOD_PRESSURE);
        prettyReadingTypes.add(PRETTY_STEPS);
        prettyReadingTypes.add(PRETTY_TEMPERATURE);
        prettyReadingTypes.add(PRETTY_WEIGHT);
    }

    /**
     * Returns an unmodifiable list of the types of readings
     * that can be created by this factory.
     *
     * @return an unmodifiable list of the types of readings this factory can create.
     */
    public static List<String> getReadingTypes() {
        return Collections.unmodifiableList(readingTypes);
    }

    public static List<String> getPrettyReadingTypes() {
        return Collections.unmodifiableList(prettyReadingTypes);
    }

    /**
     * Returns a new reading based on the type specified. If the type is
     * unknown, an IllegalArgumentException is thrown.
     *
     * @param type the type of Reading to create. type must be one of the types
     *             found in the list returned from getReadingTypes
     * @return a new Reading of the specified type
     * @throws IllegalArgumentException indicates an unknown type was specified
     */
    public static Reading getReading(String type) {
        Reading answer = null;

        switch (type) {
            case XML_BLOOD_PRESSURE:
            case PRETTY_BLOOD_PRESSURE:
            case BLOOD_PRESSURE:
                answer = new BloodPressure();
                break;
            case PRETTY_STEPS:
            case STEPS:
                answer = new Steps();
                break;
            case PRETTY_TEMPERATURE:
            case TEMPERATURE:
                answer = new Temp();
                break;
            case PRETTY_WEIGHT:
            case WEIGHT:
                answer = new Weight();
                break;
            default:
                throw new IllegalArgumentException(Strings.ERR_READING_FACTORY_UNKNOWN_READING_TYPE + type);
        }

        return answer;
    }

    /**
     * Returns the type of Reading the specified reading is a type of.
     *
     * @param reading the reading to determine the type of. Cannot be null.
     * @return the reading type returned will be one of the values in the list
     * of supported reading types.
     * @throws IllegalArgumentException indicates that reading is null.
     */
    public static String getReadingType(Reading reading) {
        String answer = null;

        if (reading instanceof BloodPressure) {
            answer = BLOOD_PRESSURE;
        } else if (reading instanceof Steps) {
            answer = STEPS;
        } else if (reading instanceof Temp) {
            answer = TEMPERATURE;
        } else if (reading instanceof Weight) {
            answer = WEIGHT;
        } else if (reading != null) {
            answer = reading.getClass().getName();
        } else {
            throw new IllegalArgumentException(Strings.ERR_READING_FACTORY_NULL_OR_UNKNOWN_READING);
        }

        return answer;
    }

    /**
     * Returns a string representation of the type for the specified reading that is suitable for use in a UI
     *
     * @param reading the reading to get the type of. Cannot be null.
     * @return a string representation of the type for the specified reading that is suitable for use in a UI
     * @throws IllegalArgumentException indicates that reading is null.
     */
    public static String getPrettyReadingType(Reading reading) {
        String answer = null;

        if (reading instanceof BloodPressure) {
            answer = PRETTY_BLOOD_PRESSURE;
        } else if (reading instanceof Steps) {
            answer = PRETTY_STEPS;
        } else if (reading instanceof Temp) {
            answer = PRETTY_TEMPERATURE;
        } else if (reading instanceof Weight) {
            answer = PRETTY_WEIGHT;
        } else if (reading != null) {
            answer = reading.getClass().getName();
        } else {
            throw new IllegalArgumentException(Strings.ERR_READING_FACTORY_NULL_OR_UNKNOWN_READING);
        }

        return answer;
    }
}