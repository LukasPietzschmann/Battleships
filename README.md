# Battleships
A clone of the game **Battleships** in Java by:
* **Lukas Pietzschmann** ([@LukasPietzschmann](https://github.com/LukasPietzschmann)) : Networking, AI, Game Logic
* **Fabian Schwarz** ([@fabian12943](https://github.com/fabian12943)): User Interface
* **Vincent Ugrai** ([@Gremmling](https://github.com/Gremmling)): Game Logic

This game was implemented as part of a lecture at Aalen University within four months.  
Partly the code is still a bit rough around it's edges, but most things work just fine.

<div style="text-align:center"><img src="https://github.com/LukasPietzschmann/Battleships/blob/master/Startup%20Screen.png" /><div>

## Features
The game supports:
* Two players on the same machine
* Two players on two different machines over the network

A player can either be a regular **Human Player** or the **AI**. Therefore the game includes a **Singleplayer** (Human vs. AI), as well as a **Multiplayer** (Human vs. 'Network').  
Also it's possible to save and later load the state of a game.

## Starting the Game
Running the game requires a proper Install of Java 8 or higher. Then the Game can be easily launched by double clicking the Battleships.jar.  
Alternatively you can run the command:
``
$ java -jar path/to/Battleships.jar
``
