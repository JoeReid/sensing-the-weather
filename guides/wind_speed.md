# About the anemometer

This is the anemometer sensor supplied with the Raspberry Pi Weather Station kit. It is used to measure wind speed.

![Anemometer](images/anemometer.png)

## How does it work?

The wind catches the three cups and drives them round, spinning the central section. To help explain how the device works, you can dismantle it following these steps:

1. First, hold the base in one hand and pull on the blades/cups with the other hand. You don't need to use much force.

Look at the underside of the blades/cups and you'll see a small metal cylinder on one side. This is a magnet, just like the one found on the bucket of the rain gauge. Test it with a paper clip if you like.

![Anemometer Magnet](images/anemometer_magnet.png)

1. Now use a screwdriver to remove the three screws on the bottom of the base. The base should then pop out easily. Slide it down the cable to get it out of the way. If you look inside you'll see our old friend the reed switch again.

![Anemometer Reed](images/anemometer_reed.png)

So what does this mean? When the blades/cups are in their original position and spinning, the magnet will rotate in a tight circle above the reed switch. So for every complete rotation, there will be two moments when the switch is closed.

If we can detect the number of rotations in a given time period, we can calculate the speed at which the arms are spinning. As some energy is lost in the pushing of the cups, an anemometer often under-reports the wind speed. To compensate for this we will multiply our calculated speed by a factor of 1.18, which is specific to this anemometer.

The following algorithm can be used to calculate wind speed:

> For each time period **t**  
> - **count** = recorded anemometer signals
> - **rotations** = count / 2  
> - **distance** = rotations * 2 * pi * radius (9cm)  
> - **speed** = distance / t (**in cm/s**)  
>
> To convert **speed** into **km/h**  
> - speed = speed / 100000 (**km/s**)  
> - speed = speed * 3600 (**km/h**)  
>
> To compensate for anemometer factor  
> - speed = speed * 1.18  


## How does the sensor connect?

To connect the anemometer to the Weather Station board, first set up the main Weather Station box using the [Weather Station guide](https://www.raspberrypi.org/learning/weather-station-guide).

1. Connect the anemometer to the wind vane.
1. Connect the wind vane to the Raspberry Pi Weather Station.

When connected, the anemometer uses GPIO pin 5 (BCM).


