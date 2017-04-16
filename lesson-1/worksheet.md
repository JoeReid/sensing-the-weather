# Sensing the Weather - Rainfall Worksheet

In this lesson you will:

- Collect data using the Raspberry Pi Weather Station hardware
- Learn the difference between **continuous polling** and **interrupt handling**
- Convert the collected data into meaningful measurement information

## How does the rain gauge work?

Today you will be using the rain gauge sensor to collect data about rainfall. The gauge consists of a bucket to collect water, and a seesaw-like device to measure how much water passes through. Each time the bucket fills with a certain amount of water it tips, releasing the water and presenting the opposite bucket to be filled.

  ![](images/rain_gauge_both.jpg)

Each tip causes a magnet to pass in front a sensor called a reed switch, which closes the switch and triggers a `LOW` signal on GPIO pin 6. We can detect this `LOW` signal and use it to count how many times the bucket tips.

  ![](images/reed_switch.jpg)

In order to calculate the amount of water that has passed through the gauge we need to know:

  - The amount of water needed to tip the bucket, in this case **0.2794** mm (this can be found on the [datasheet](https://www.argentdata.com/files/80422_datasheet.pdf)).
  - How many times the bucket has tipped, which can be counted as the number of input signals.

  **Rainfall = 0.2794 * number of tips**

## Talking to the sensor

There are two primary ways to collect data from the sensor:

* Polling: We can ask the sensor if it's been tipped over and over again, until it says "yes".
Although this can give us an answer slightly faster, it prevents us from getting on with other things while we're busy asking.
* Interrupts: We can also tell the sensor to let us know when it's changed state. Although this can be slightly slower, the major benefit is that we can get on with other things and switch over to the sensor code when needed.

For the purposes of this worksheet, we will focus on using interrupts, i.e. telling the sensor to run a method when it changes state.

## Counting bucket tips

Make sure your rain gauge is connected to your Weather Station, then turn it on.

1. Open the BlueJ project found [here](https://bluej.org/raspberrypi/WeatherStation/lesson-1/initial.jar).
1. Open the PiRainSensor class.

We will need to store the size of the bucket as a constant (hence the capitals), and a counter for how many times the bucket has tipped.
First, we need to store the size of the bucket as a constant at the top of the class, and keep a counter for how many times the bucket has tipped.
  ```java
  public class PiRainSensor implements RainSensor, RainMeterListener {

  /**
	* The amount of rainfall the rain sensor bucket manages to catch.
	*/
  public static final double BUCKET_SIZE_MM = 0.2794;

  /**
   * The number of times the rain meter has collected 0.2794mm of rain.
   */
  private int count = 0;

  ```

There is a piece of code in the 
constructor of the class that makes sure that the *onTriggered* method is run every time the bucket tips.
We can test this code by printing to the terminal inside the method:

  ```java
  public void onTriggered() {
    System.out.println("Bucket tipped!");
  }
  ```

Compile and run our new code by creating a new instance of the PiRainSensor class on the object bench,
and then tip the bucket from side to side. You should see the terminal print "Bucket tipped!".

Let's make our code do something a bit more useful, for example, tell us how much rainfall there has been since we started running it.
Earlier on, we added the *count* variable, and we should increment it every time we are told the bucket has tipped.
Then we can add a *getRainfall* method so we can ask for the total rainfall since we created the object.

  ```java
  public void onTriggered() {
    count++;
  }
  
  public double getRainfall() {
    return BUCKET_SIZE_MM * count;
  }
  ```

The complete code can be found [here](https://bluej.org/raspberrypi/WeatherStation/lesson-1/complete.jar).

## Summary

You should now have a working rain gauge that uses interrupts to update only when the bucket is tipped.
Consider the following questions:
* What could you put in the *resetCounter* method to make it work correctly?
* What is the difference between polling and interrupt handling?
* Is one of these techniques better? If so, why?
* Why is the unit of measurement for the gauge **mm** rather than **ml**?

## What next

Now that you have built your rain gauge code you should test its accuracy. You could try tipping a known amount of water in ml into the top of the rain gauge. How much water in ml would you have to pour in per 1mm of measured rainfall?
