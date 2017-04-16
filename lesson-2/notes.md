* Had to remove timespan calculation from the PiWindSpeedSensor as it could be its own lesson.
* Going for the sleeping while-loop implementation, as in the Python version.
  * Not sure how well this will work in BlueJ, as it blocks the Main thread and doesn't naturally halt.
* Moved WindSpeed over to project, and removed interface implementation from PiWindSpeedSensor (can't meet contract).
* Changed from the alteration of lesson 1 idea used in the Python version, because Java's too verbose for it to work well.

