import org.kentuni.WeatherStation.Implementations.Factory;
import org.kentuni.WeatherStation.Implementations.Platform;
import org.kentuni.WeatherStation.Sensors.WindSpeedSensor;
import org.kentuni.WeatherStation.Units.WindSpeed;

public class GustDetector {

    /**
     * How often to check the wind speed.
     */
    public static final int INTERVAL_MILLIS = 5000;

    /**
     * How many times to check the wind speed when asked for a reading.
     */
    public static final int ITERATIONS = 4;

    /**
     * The minimum speed, in kilometers per hour, of a gust.
     */
    public static final double GUST_ABOVE_KM_H = 29.6;

    /**
     * The minimum difference in speed, in kilometers per hour,
     * between the lowest speed and the highest speed in a gust.
     */
    public static final double GUST_RANGE_KM_H = 16.7;

    /**
     * The sensor to use.
     */
    private WindSpeedSensor sensor;

    /**
     * The constructor.
     * Sets up the sensor.
     */
    public GustDetector() {
        sensor = new Factory(Platform.MOCK).getWindSpeedSensor();
    }

    /**
     * Runs for a while, asking the wind sensor for readings every few seconds, and returns an array of wind speeds.
     * @throws InterruptedException, Required so that "Thread.sleep" is handled correctly.
     */
    public WindSpeed[] getReadings() throws InterruptedException {

        WindSpeed[] windSpeeds = new WindSpeed[ITERATIONS];

        for (int i = 0; i < ITERATIONS; i++) {
            Thread.sleep(INTERVAL_MILLIS);
            windSpeeds[i] = sensor.getWindSpeed();
        }

        return windSpeeds;
    }

    public WindSpeed getGust() throws InterruptedException {
        WindSpeed[] windSpeeds = getReadings();
        
        // The first wind speed we look at is both the highest and the lowest so far.
        WindSpeed highest = windSpeeds[0];
        WindSpeed lowest = windSpeeds[0];

        for (WindSpeed windSpeed : windSpeeds) {

            if (windSpeed.inKilometersPerHour() > highest.inKilometersPerHour()) {
                // If the speed we are looking at is higher than the previous best, replace it.
                highest = windSpeed;
            } else if (windSpeed.inKilometersPerHour() < lowest.inKilometersPerHour()) {
                // Otherwise, if the speed we are looking at is lower than the previous best, replace it.
                lowest = windSpeed;
            }
        }

        final double highestKmH = highest.inKilometersPerHour();
        final double lowestKmH = lowest.inKilometersPerHour();

        // If the highest speed is at gust speeds,
        // and the difference between the highest and the lowest is big enough to be classed as a gust.
        if (highestKmH >= GUST_ABOVE_KM_H && highestKmH - lowestKmH >= GUST_RANGE_KM_H) {
            // Return the highest speed we reached.
            return highest;
        } else {
            // Otherwise, return nothing to let them know that no gust was found.
            return null;
        }
    }
}
