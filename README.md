# Tetris

Recreating the famous puzzle video game - Tetris. <br>

## Architecture
This application uses the Model-View Controller architecture. The view part<br>
is linked to controller that handles all the actions related to view. Whenever <br>
any action is made, controller of the game makes changes in the model. <br>
This way the view is not allowed to make the direct changes to a model but first <br>
ask the controller.


## Running the program
To build and run the project write in the terminal or IDE:
```mvn compile exec:java```

## Getting JavaDoc
To generate JavaDoc for the project, write in the terminal or IDE:
```mvn javadoc:javadoc```

