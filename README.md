# Ultimate Maze Experience
A game about escaping a maze. You need to escape 100 times to succeed and secure your completion time. There are no pauses and no saves. The game is very speedrun-oriented and features a persistent timer in the corner of the screen.

-[Maze generator](#maze-generator) <br>
-[In game experience](#in-game-experience) <br>
-[Build with](#build-with)

<hr>

## Maze Generator

<p>File labirynth.java defines a class with constructor that creates a maze of wanted size. It is an implementation of Prim's algortihm that has 4 key steps of creating a maze:</p>
<ol>
    <li>Grid full of walls with dimensions x by y, where x and y are dimensions of the maze expected at the end</li>
    <li>Pick a random wall, turn it into the path and "officialy" make it a part of the maze, as well as 4 walls near it</li>
    <li>Pick a random wall AGAIN, but this time point number 2 is repeated only when it neighbours only ne path (wont create a loop)</li>
    <li>Repeat point number 3 until almost no walls from the original grid are "outside" the maze</li>
</ol>
File creates a text version of the maze.

## In game experience:
<img src="10.png">
<img src="11.png">
<img src="time.png">


## Build with
<ul>
<li>Java 23</li>
</ul>
