# Tetris

Recreating the famous puzzle video game - Tetris. <br>

## Architecture
This application uses the Model-View Controller architecture. The view part<br>
is linked to controller that handles all the actions related to view. Whenever <br>
any action is made, controller of the game makes changes in the model. <br>
This way the view is not allowed to make the direct changes to a model but first <br>
ask the controller.

## Gameplay

Here are some screenshots of the Tetris gameplay:

![Screenshot 1](/img/menu_instruction.png)
This screenshot shows the main menu of the Tetris game on the left. It includes the `'Start Game'`, `'Leaderboard'`, `'Instructions'` and `'Quit'` buttons. On the right side, it is the window which appears after selecting the `'Instructions'` button, which explains the rules and controlers of the game.

![Screenshot 2](/img/gameplay.png)
These screenshots show the gameplay once the `'Start Game'` button is pressed. The user is moved to the game grid where the they are allowed to move the pieces over the board by rotating their position or moving down faster. On the right side, the game over screen is shown when the game is over.

![Screenshot 3](/img/stat_score.png)
Once the game is over, the screen on the left side appears. It is the window where the user can record their score and save it or leave to the menu without saving their score. To view the saved result, the player can press the `'Leaderboard'` button in the menu to see all saved results.

## Running the program
To build and run the project write in the terminal or IDE:
```mvn compile exec:java```

## Getting JavaDoc
To generate JavaDoc for the project, write in the terminal or IDE:
```mvn javadoc:javadoc```

