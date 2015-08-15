Risk
=========

This is a Java clone of the popular board game Risk, written for my final project for CIS120! It uses Swing to make a basic interface displaying the board, cards, dice, and implements all the standard Risk rules such as attacking, fortifying, using cards, getting continent bonuses, and so on.

![](/images/startscreen.png)

![](/images/game.png)

##How it works

Game:
The runnable file, which initially displays a startScreen and adds a MouseListener that checks for if the player clicked on start (and selected a number of players). Then, it initializes the game by making status panels and the Board.

Start:
A JPanel that displays the start menu. It has images for the different possible states (e.g. risk2players.png shows the main menu with the 2 Players option darkened) and a mouseMotionListener that checks if the mouse is hovering over a button. The functionality of clicking the buttons is in Game because the numPlayers must be accessible to make the board.

Dice:
A JPanel that displays the dice. It has an inner class Die that represents one die, and the whole panel is updated as a player attacks a country.

Country:
An object with a set of adjacent countries, a name, a number of soldiers, a boolean for if it is selected, and ints for coordinates and dimension. It has a method to check if the mouse is in the bounds of the rectangle that represents the country.

Player:
An object with an int array representing cards, a set of countries owned, and a boolean for if it is dead. There is also a static list of ints for the deck, a static counter for how many card sets have been turned in, and a static boolean for if a player already won a card that turn. This contains all of the methods for card functionality, such as getting cards, using sets of cards, shuffling the deck, and so on.

Board:
The JComponent that contains most of the game logic. Board contains a list of country sets for the continents, an int array for the continent bonuses in order to see if a player gets a continent bonus. It also contains an array of players and an array of colors to implement the ownership of countries. There are two countries that are used to store currently selected countries in order to make most of the interactions between countries. There is also an int for the number of troops available to place, and an int representing whose turn it is. There are JLabels for turnInfo and cardInfo so that the Board can set the text of the status panels. Finally, this also contains an enum that represents the different stages of a players turn. The Board has a mouse listener that has different functionalities based on what the mode is.