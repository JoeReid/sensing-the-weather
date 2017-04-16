```java

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
}
```
