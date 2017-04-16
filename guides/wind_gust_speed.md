# About the anemometer

This is the anemometer sensor supplied with the Raspberry Pi Weather Station kit. It is used to measure wind gust speed.

![Anemometer](images/anemometer.png)

## How does it work?

You have already explored how the anemometer works in the wind speed lesson. Now we'll use the measurements from the anemometer to measure wind gust speed.

Gusts of wind are sudden brief increases in the speed of the wind. According to U.S. weather observing practice, gusts are reported when the peak wind speed reaches at least 16 knots, and the variation in wind speed between the peaks and lulls is at least 9 knots. The duration of a gust is usually less than 20 seconds. [[Source](http://glossary.ametsoc.org/wiki/Gust)]

To convert these into measurements we can use, 16 knots is approximately 29.6km/h, and 9 knots is 16.7km/h. If you're testing your program in a classroom by pushing the anemometer by hand, it's unlikely you will be able to reach a speed greater than 29.6km/h, so you'll need to change these threshold values to simulate gusts occurring. Don't forget to change them back when you put your Weather Station outside!

We can use this information to calculate when gusts appear using three rules. A gust occurs within a given time period when:

- the highest wind speed measured during a period is above 29.6km/h AND
- the difference between the peak speed and lowest speed in that period is greater than 16.7km/h AND
- the time period is 20 seconds or less

We'll measure the wind speed as before, but this time we'll store a range of values, allowing us to calculate whether a gust has occurred within the last 20-second time period.

The following algorithm can be used to calculate the gust speed:

    > STORE most recent four speed readings as a list
    >
    > FUNCTION check_for_gusts()
    > --- highest = MAX speed in list
    > --- lowest = MIN speed in list
    > --- IF highest > 29.6 and highest - lowest > 16.7
    > --- --- RETURN highest
    > --- ELSE
    > --- --- RETURN 0

## How does the sensor connect?

To connect the anemometer to the Weather Station board, first set up the main Weather Station box using the [Weather Station guide](https://www.raspberrypi.org/learning/weather-station-guide).

1. Connect the anemometer to the wind vane.
1. Connect the wind vane to the Raspberry Pi Weather Station.

When connected, the anemometer uses GPIO pin 5 (BCM).


