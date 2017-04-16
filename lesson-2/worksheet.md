# Sensing the Weather - Wind Speed Worksheet

In this lesson you will:

- Collect data from an anemometer using the Raspberry Pi GPIO pins
- Use interrupt handling to detect inputs from the sensor
- Use simple circle theory to convert the collected data into meaningful wind speed measurements

## How does the anemometer work?

Today you will be using the anemometer sensor to collect data about wind speed. The sensor has three arms with buckets on the end which "catch" the wind, causing the arms to spin. If you were to dismantle the sensor, you would find a small magnet attached to the underside.

![](images/anemometer_with_magnet.png)

At **two** points within the magnet's rotation, it triggers a reed switch which produces a `LOW` signal we can detect via GPIO pin 5. So for each full rotation of the arms, the sensor will produce two detectable signals.

![](images/anemometer_reed.png)

So let's start collecting data from the sensor!

## Setup

Open the BlueJ project found [here](https://bluej.org/raspberrypi/WeatherStation/lesson-1/initial.jar).

Because the anemometer measures wind speed, we need to deal with time.
To keep things simple, the *PiWindSpeedSensor* class is given a time when it is first created, and then presumes that it's been that long since it was last called.

## Detecting anemometer interrupts

First, you need to be able to count the signals coming from the anemometer.
Open the *PiWindSpeedSensor* class.
This code is the same as lesson 1, in that you will need a counter, and you will need to increment the counter in the *onTriggered* method.

## Getting the wind speed

The major difference lies in how you get the wind speed.
Because turning the anemometer's data into something useful requires some maths, we will use a separate class: *WindSpeed*.

To use it, you will need to create a new *WindSpeed* object:


  ```java
  public WindSpeed getWindSpeed() {
    WindSpeed speed = new WindSpeed(count, intervalMillis);
    count = 0;
    return speed;
  }
  ```

The above code also resets the count for the next time we're asked for the wind speed.

## Testing our work so far

You can make sure that what you have so far works by compiling, right clicking on *PiWindSpeedSensor*, and running the *onInterval* method.
Pick an interval, in milliseconds, that the speed will be calculated.
You should see that the number of half rotations is printed on the screen repeatedly.

When you are happy that everything is working, right click the stripey red bar in the bottom left of the BlueJ window and click **"Reset Java Virtual Machine"**.

**Note**
If you do not reset the Java virtual machine, you won't be able to compile or run another test.

You should now be able to count the signals from the anemometer and represent them as *WindSpeed* objects; now let's fill in the *WindSpeed* class.

## Calculating wind speed

We know that the anemometer registers two signals per spin, so we can count the number of full rotations of the sensor by halving the number of detected inputs. But how do we change that into a speed?

1. Let's start by considering the formula for calculating [speed](http://www.bbc.co.uk/education/guides/zwwmxnb/revision):

    **speed = distance / time**

  To calculate **speed** we need to know the **distance** travelled in a certain amount of **time**. We already presume the time in *PiWindSpeedSensor*, so we don't need to calculate it any further.

1. The distance travelled by one of the cups will be equal to the number of rotations * the distance around the edge of the circle (circumference). So we could write:

    **speed = (rotations * circumference) / time**

1. The circumference can be calculated if we know either the **radius** or **diameter** of the circle.

    ![](images/pi_diagram.png)

    We can measure the radius of the circle made by the anemometer by measuring the distance from the centre to the edge of one of the cups. Knowing the radius, we can find the circumference with the formula `2 * pi * radius`. We also know that a whole rotation generates two signals, so if we halve the number of signals detected our formula becomes:

    **speed = ( (signals/2) * (2 * pi * radius) ) / time**

    This formula should enable us to calculate the speed of the wind in cm/s.

## Updating the code

Now that we know how to calculate the wind speed from the sensor signals, we need to write the code.

First of all, open up the *WindSpeed* class, remove the *halfRevolutions* field, and remove the *inHalfRevolutions* method; you won't need them anymore.

We will need the constant *CIRCLE_RADIUS_CM* (9cm in the Pi kit).
We will also need to store the time for later use, and convert the half revolutions we get from the anemometer into a distance in centimeters.
Once we have this, we can calculate the speed in centimeters per millisecond. Overall, our code should look like this:

  ```java
  
  // CIRCLE_RADIUS_CM constant = 9
  
  // timeMillis field.
  // centimeters field.
  
  public WindSpeed(final int halfRevolutions,
            final long timeMillis) {
    // Set the timeMillis field equal to the timeMillis parameter.
    // Calculate and store the distance in centimeters from the number of half revolutions, and the CIRCLE_RADIUS_CM constant.
  }
  
  public double inCentimetersPerMillisecond() {
    // Calculate the speed in centimeters per millisecond, and return it.
  }
  
  ```
When this is done, you should be able to test it as described above.

A solution can be found [here](code/windspeed_simple.md).

## Measurement units

Currently, the program we have created will measure the wind speed in **cm** per **millisecond**; however, this is not a particularly useful measurement. A more practical unit would be **km** per **hour**. 

It's a good idea to set up constants to store the values of the number of milliseconds in an hour, and the number of centimetres in a kilometre. This will make our calculations less confusing for other people to understand:

```java
public static final double CM_IN_A_KM = 100 * 1000;
public static final double MILLIS_IN_AN_HOUR = 1000 * 60 * 60;
```

In order to convert our units we'll need to:

- Convert cm -> km by **dividing** the distance by the number of cm in 1km
- Convert milliseconds -> hours by **multiplying** the speed by the number of seconds in 1 hour


Create a new method, *inKilometersPerHour*, that will use these new calculations.

## Calibration

### Question

Is the wind speed shown by your program accurate? Given the information on the [datasheet](https://www.argentdata.com/files/80422_datasheet.pdf), how could you test its accuracy?

### Answer

The [datasheet](https://www.argentdata.com/files/80422_datasheet.pdf) says that if the anemometer rotates once a second that should equate to 2.4 km/h. So in the example interval of 5 seconds, 5 spins (10 signals) should equal the same 2.4 km/h.

1. Run your program and spin the anemometer 5 times within the first 5 seconds. What wind speed value is reported?

  You should find that the reported speed is about 0.4km/hr less than the real wind speed. This loss of accuracy is due to something called the *anemometer factor* and is a result of some of the wind energy being lost in turning the arms.
  To compensate for this, we're going to have to **multiply** the reading generated by our program by a factor of **1.18** which should correct this error.
  Update the *inKilometersPerHour* method to multiply your speed in km/h by 1.18.

  Again, you might want to store the anemometer adjustment value as a constant with the value of 1.18, and then use the constant in your code to make sure it makes sense to other people.

  ```java
  public static final double CALIBRATION_FACTOR = 1.18;
  ```

Your final code should now look something like [this](https://bluej.org/raspberrypi/WeatherStation/complete.zip).

1. Re-run the code and this time you should get a value closer to 2.4:

## Summary

- You have created a program which uses the anemometer to measure wind speed. What kind of location would be most suitable for this device? 
- What factors should be considered when choosing a place for your anemometer outside?
