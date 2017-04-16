import org.kentuni.WeatherStation.Drivers.RainMeter;
import org.kentuni.WeatherStation.Drivers.RainMeterListener;
import org.kentuni.WeatherStation.Sensors.RainSensor;
import org.kentuni.WeatherStation.Units.RainFall;

public class PiRainSensor implements RainMeterListener {

    /**
     * The constructor.
     * Gets the RainMeter object (getInstance), and asks it to tell us when there is rain.
     */
    public PiRainSensor() {
        RainMeter rainMeter = RainMeter.getInstance();
        rainMeter.addListener(this);
    }

    /**
     * Resets the counter, and the last collection time.
     */
    public void resetCounter() {
    }

    /**
     * Get the amount of rainfall since we last reset the counter.
     */
    public double getRainfall() {
        return 0;
    }

    /**
     * Called when the rain meter records 0.2794mm of rainfall.
     */
    public void onTriggered() {
    }
}