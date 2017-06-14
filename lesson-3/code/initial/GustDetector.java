import org.bluej.WeatherStation.Implementations.Factory;
import org.bluej.WeatherStation.Implementations.Platform;
import org.bluej.WeatherStation.Sensors.WindSpeedSensor;
import org.bluej.WeatherStation.Units.WindSpeed;

public class GustDetector {

    /**
     * The constructor.
     * Sets up the sensor.
     */
    public GustDetector() {
        // Fill in.
    }

    /**
     * Runs for a while, asking the wind sensor for readings every few seconds, and returns an array of wind speeds.
     * @throws InterruptedException, Required so that "Thread.sleep" is handled correctly.
     */
    public WindSpeed[] getReadings() throws InterruptedException {
        // Fill in.
        return null;
    }

    public WindSpeed getGust() throws InterruptedException {
        // Fill in.
        return null;
    }
}
