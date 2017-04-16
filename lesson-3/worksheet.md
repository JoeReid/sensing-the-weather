# Sensing the Weather - Wind Gust Speed Worksheet

In this lesson you will:

- Understand the definition of a 'gust of wind'
- Translate this definition into a format the computer can understand
- Be able to store wind speed measurements in an array for later analysis
- Be able to manipulate data items in an array

## What hardware will we use?

To measure the wind gust speed we will also use the anemometer, the same instrument we used to measure the wind speed in a previous lesson.


## Getting started

Open the BlueJ project found [here](https://bluej.org/raspberrypi/WeatherStation/lesson-3/initial.jar).

## Storing wind speed readings

In this exercise, we will be using the WindSpeed class we created in the last lesson to look for **gusts** of wind.

To start, open the GustDetector class.
We need to check the wind speed every 5 seconds (5000 milliseconds) for 20 seconds, so 4 times. We should store these as constants at the top of the class:


  ```java
  public static final int INTERVAL_MILLIS = 5000;
  public static final int ITERATIONS = 4;
  ```

We will also need access to a wind sensor, so we should store one as a field and create it in the constructor:

  ```java
  private PiWindSpeedSensor sensor;
  
  /**
   * The constructor.
   */
  public GustDetector() {
    sensor = new Factory(Platform.WEATHERSTATION_V1).getWindSpeedSensor();
  }
  ```

**Note:** If you are using doing this exercise away from a working weather station,
you can change `Platform.WEATHERSTATION_V1` to `Platform.MOCK` to receive fake data from the sensor instead.

Now let's create a *getReadings* method that we can use to get **4 readings** from the *WindSpeed* class, **5 seconds apart** from one another.
Our code will need to look as follows:
  ```
  Create an array to store our readings
  for-loop 4 times {
    sleep for 5 seconds
    read the wind sensor and store it in the array
    print the sensor data to the terminal
  }
  return the array
```

Have a go at filling in the code yourself.

The code you need in order to make the program sleep for 5 seconds will be:

  ```java
  Thread.sleep(INTERVAL_MILLIS);
  ```

Once you're done, you can check your answer with the solution [here](code/getReadings_complete.md)

You can test this by compiling, creating a new *GustDetector* object, and calling *getReadings*.

It should take 20 seconds to run, and give you back an array of four *WindSpeed* objects.
Have a go at spinning the anemometer while the program is running to see varying results.

## Checking for gusts

### Question

A gust of wind occurs within a given time period when:

- the highest wind speed measured during that period is above 29.6km/h AND
- the difference between the peak speed and lowest speed in that period is greater than 16.7km/h AND
- the time period is 20 seconds or less

Now that we have the data available to us, how can we ask the computer to check whether a gust has occurred?

Take a look at the data below which stores four wind speed readings in km/h,
taken 5 seconds apart, and decide for each line of data:

1. If there was a gust or not, and
2. If so, what the wind gust speed is.

```
Data #1 - [1.4, 2.6, 42.9, 4.4]
Data #2 - [1.3, 17.2, 24.2, 28.8]
Data #3 - [34.9, 33.1, 35.9, 31.3]
Data #4 - [2.9, 3.8, 2.1, 6.5]
```

### Answer

A gust has occurred for Data #1, but not in the other data sets. Let's examine why.
We already measured a time period of 20 seconds, but for a gust to have occurred, two further conditions need to be true:

- **Condition 1** - Highest wind speed above 29.6km/h
- **Condition 2** - Difference between highest and lowest is above 16.7km/h

So the results for each data set are:

| Data set      | Condition 1   | Condition 2   			| Gust?   |
| ------------- | ------------- | ------------------------- | ------- |
| Data #1      	| True (42.9)	| True (42.9 - 1.4 = 41.5)	| True 	  |
| Data #2      	| False (28.8) 	| True (28.8 - 1.3 = 27.5)	| False   |
| Data #3 		| True (35.9)  	| False (35.9 - 31.3 = 4.6)	| False   |
| Data #4 		| False (6.5)  	| False (6.5 - 2.1 = 4.4)	| False   |

(If you have studied Boolean logic, you might notice that these examples form the truth table for the logical operator 'AND'; for the gust to be true, both condition 1 AND condition 2 need to be true.)

## Adding a gust detection function

Now that we have a well-defined logical way of testing whether a gust has occurred, our final job is to add a method we can call to check whether a gust occurred in the last 20 seconds.

First, we need to create some constants for the minimum required wind speed for a gust, and the range between the highest and lowest speeds required for a gust:

  ```java
  public static final double GUST_ABOVE_KM_H = 29.6;
  public static final double GUST_RANGE_KM_H = 16.7;
  ```


Inside the *getGust* method, we need to do the following:

1. Find the highest and lowest wind speeds in the array.
2. Check to see if the highest/lowest wind speeds count as a valid gust.

  ```
  // Part One.
  Ask the getReadings method for an array of wind speeds, and store it.
  Store the current highest and current lowest speeds found. Set both = first element in windSpeeds array.
  for each wind speed in the windSpeeds array {
    if speed > current highest, set current highest = new speed
    else if speed < current lowest, set current lowest = new speed
  }
  
  // Part Two.
  if highest > GUST_ABOVE_KM_H and difference between highest and lowest > GUST_RANGE_KM_H {
    return the highest
  } else {
    return null
  }
  ```

You can check your code against a completed version [here](https://bluej.org/raspberrypi/WeatherStation/lesson-3/complete.jar).

Run and test your program. If you're testing it indoors by pushing the anemometer by hand, what issues do you think might occur which might make it difficult to test your code?

## Summary

- You have created a program which stores the last 20 seconds of wind speed data as four readings in a list, and checks whether a gust of wind has occurred.
- Are there any easier ways to test that your program works, i.e. could you make it so that you don't have to spin the anemometer to test?
- Why is it a good idea to set the current highest and lowest to the first element in the wind speeds array?
  - What would happen if you didn't do this?


## What next

- Devise a way to test your code indoors. It's unlikely that you will be able to generate "gusts" by spinning the anemometer at greater than 29.6km/h by hand. How could you test whether the code you have written to check for gusts actually works?
- Devise a test plan and test your code.
