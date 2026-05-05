# Ultimate Maze Experience
A game about escaping a maze. You need to escape 100 times to succeed and secure your completion time. There are no pauses and no saves. The game is very speedrun-oriented and features a persistent timer in the corner of the screen.

-[Maze generator](#maze-generator) <br>
-[In game experience](#in-game-experience) <br>
-[Build with](#build-with)

<hr>

## Maze Generator

<p>The file labyrinth.java defines a class with a constructor that generates a maze of a specified size. It is an implementation of Prim's algorithm, which follows these four key steps to create the maze:</p>
<ol>
    <li>Initialize a grid full of walls with dimensions $x \times y$, representing the final size of the maze.</li>
    <li>Pick a random starting cell, mark it as part of the maze, and add its neighboring walls to a list.</li>
    <li>Pick a random wall from the list. If it neighbors only one cell that is already part of the maze, turn the wall into a path, mark the newly reached cell as part of the maze, and add its neighbors to the list.</li>
    <li>Repeat step 3 until there are no more walls left in the list to process.</li>
</ol>
The file then generates a text-based version of the maze below.
<img src="maze.png">
<br>

## In game experience:
<img src="10.png">
<img src="11.png">
<img src="time.png">


## Build with
<ul>
<li>Java 23</li>
</ul>
