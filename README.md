Learning Project where I am trying to recreate the 2048 game (https://en.wikipedia.org/wiki/2048_(video_game))

This project is in a semi finished state, it is playable with unit tests for the majority of the code.
However there are still some future todo items should I ever choose to come back to it.

This is my first attempt at programming something of my own in Java. I did use the behaviour of the [original game](https://play2048.co/) as a guide, however the code is all my own work.


## How to Play:
Game is played using "WASD" to move the tiles on the board. Just run Program.java
"O" can be used to undo moves.
"P" can be used to redo moves.
Undoing a move and then playing from there normally, will delete the following moves that were made previously.

## To Do

- Ensure scores cannot go over int limit, maybe use a long instead if needed.
- Create a score class? Maybe.

## Ideas
- Have different size gameboards
- Custom Key Binds for movement keys
- Allow the option to continue playing after you win. Would need sprites for everything up to 65,536
- Display top 5 scores at the end of the game
- Names for high scores.
- Make scores nicely formatted
- Animations