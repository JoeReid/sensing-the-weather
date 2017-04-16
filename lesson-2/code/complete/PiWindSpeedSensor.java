import org.kentuni.WeatherStation.Drivers.Anemometer;
import org.kentuni.WeatherStation.Drivers.AnemometerListener;
import org.kentuni.WeatherStation.Sensors.WindSpeedSensor;


public class PiWindSpeedSensor implements AnemometerListener {

    /**
     * The number of times the anemometer has turned a half-circle.
     */
    private int count = 0;
    
    /**
     * The interval to expect calls to getWindSpeed, in milliseconds.
     */
    private long intervalMillis;

    public PiWindSpeedSensor(long intervalMillis) {
        // Asks the anemometer to let us know when it's completed half a turn.
        Anemometer.getInstance().addListener(this);
        this.intervalMillis = intervalMillis;
    }

    /**
     * Gets the wind speed as the number of times the anemometer has completed a full circle since the last time
     * something asked for the wind speed.
     * {@link WindSpeed} handles most of the maths.
     * @param The time that's passed since we were last asked for the wind speed, in milliseconds.
     * @return The wind speed.
     */
    public WindSpeed getWindSpeed() {
        WindSpeed speed = new WindSpeed(count, intervalMillis);
        count = 0;
        return speed;
    }

    /**
     * Called when the anemometer completes half a turn.
     * We keep count by incrementing {@link #count}.
     */
    @Override
    public void onTriggered() {
        count++;
    }
    
    public static void onInterval(long millis) throws InterruptedException {
        PiWindSpeedSensor sensor = new PiWindSpeedSensor(millis);
        while(true) {
            Thread.sleep(millis);
            WindSpeed speed = sensor.getWindSpeed();
            System.out.println(speed.inKilometersPerHour());
        }
    }
}