 /**
 * A class for representing a wind speed value to be returned by a {@link
 * WindSpeedSensor}.
 *
 * @author  Joe Reid
 * @see WindSpeedSensor
 */
public class WindSpeed {

    private int halfRevolutions;
    
    /**
     * The sole constructor for a {@link WindSpeed} object.
     *
     * @param halfRevolutions the number of revolutions of the wind sensor.
     *
     * @param timeMillis the amount of time that's passed.
     */
    public WindSpeed(final int halfRevolutions,
            final long timeMillis) {
        this.halfRevolutions = halfRevolutions;
    }
    
    /**
     * A method for getting the number of half revolutions that were recorded
     * in this wind speed.
     */
    public double inHalfRevolutions() {
        return halfRevolutions;
    }
}