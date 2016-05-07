# Game
An pokemon-themed Android shooter game

I didn't start using Github until 2016, I will be putting in more comments and explanations to each of the code files.

##Objects in the game
There are in total of 9 objects: background, choppermissle, coin, explosion, gift, player, smokepuff, and zombie.

##Class methods
Each class contains 3 basic methods

###1, Constructor
Every object extends GameObject class, which has: Bitmap res, int x, int y, int w, int h, int numFrames for parameters.
###2, update()

###3, draw()

##Other classes
GamePanel is the most important class among others. It creates and destroys surface, initialize all objects, handles touch on the screen, updates the screen every 1 nanosecond.



