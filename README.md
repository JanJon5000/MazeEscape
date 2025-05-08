# MazeGame
I decided to divide this repo's README into a few sections:
    <p> -[Maze generator](#maze-generator) </p>
    <p> -soon </p>
    <p> -soon </p>
    <p> -soon </p>

<hr>

### Maze Generator

<p>File labirynth.java defines a class with constructor that creates a maze of wanted size. It is an implementation of Prim's algortihm that has 4 key steps of creating a maze:</p>
<ol>
    <li>Grid full of walls with dimensions x by y, where x and y are dimensions of the maze expected at the end</li>
    <li>Pick a random wall, turn it into the path and "officialy" make it a part of the maze, as well as 4 walls near it</li>
    <li>Pick a random wall AGAIN, but this time point number 2 is repeated only when it neighbours only ne path (wont create a loop)</li>
    <li>Repeat point number 3 until almost no walls from the original grid are "outside" the maze</li>
</ol>
File creates a text version of the maze, that later is turned into an image.