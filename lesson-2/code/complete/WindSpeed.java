 /**
 * A class for representing a wind speed value to be returned by a {@link
 * WindSpeedSensor}.
 *
 * @author  Joe Reid
 * @see WindSpeedSensor
 */
public class WindSpeed {
    /**
     * The number of centimeters in a kilometer.
     */
    public static final double CM_IN_A_KM = 100 * 1000;
    
    /**
     * The number of milliseconds in an hour.
     */
    public static final double MILLIS_IN_AN_HOUR = 1000 * 60 * 60;
    
    /**
     * The radius of the anemometer, in cm.
     */
    public static final double CIRCLE_RADIUS_CM = 9;

    /**
     * The amount to multiply our results by to correct for lost energy spinning the anemometer.
     */
    public static final double CALIBRATION_FACTOR = 1.18;
    
    /** The internal distance value in centimeters.*/
    private final double cm;

    /** The internal time value in milliseconds.*/
    private final long timeMillis;

    /**
     * The sole constructor for a {@link WindSpeed} object.
     *
     * @param revolutions the number of revolutions of the wind sensor over the
     * provided time period with fractional accuracy.
     *
     * @param circleRadiusCm the radius of the arc drawn by the arms of the
     * wind speed sensor hardware.
     *
     * @param timeMillis the time period that the revolutions value was measured
     * over
     */
    public WindSpeed(int halfRevolutions, long timeMillis) {

        this.timeMillis = timeMillis;
        
        double revolutions = halfRevolutions / 2f;

        // 2πr
        double circleCircumferenceCm = 2 * Math.PI * CIRCLE_RADIUS_CM;

        cm = CALIBRATION_FACTOR * circleCircumferenceCm * revolutions;
    }
    
    /**
     * A method for getting the wind speed value as represented in centimeters per millisecond.
     * 
     * @return The wind speed measured in centimeters per millisecond.
     */
    public double inCentimetersPerMillisecond() {
        return cm / timeMillis;
    }

    /**
     * A method for getting the wind speed value as represented in kilometers
     * per hour.
     *
     * @return The wind speed measured in kilometers per hour.
     */
    public double inKilometersPerHour() {
        double kilometers = cm / CM_IN_A_KM;
        double hours = timeMillis / MILLIS_IN_AN_HOUR;
        return kilometers / hours;
    }
}