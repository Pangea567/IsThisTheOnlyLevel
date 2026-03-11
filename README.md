Is This The Only Level? - Java Implementation 🐘

A high-fidelity re-implementation of the classic 2D puzzle-platformer. Built using Java and the StdDraw library, this project demonstrates core game development principles, including custom physics, collision detection, and dynamic stage management.

🎮 The Challenge

In this game, the player controls an elephant through 5 seemingly identical stages. However, each stage introduces a "twist" that changes the rules of the world.

Stage Mechanics Breakdown

Stage

Name

Twist

1

Standard

Classic movement & button mechanics.

2

Inverted

Left and Right controls are swapped.

3

Moon Gravity

Continuous jumping (Auto-jump) logic.

4

Persistence

Button requires 5 presses to trigger the door.

5

Paradox

Players must touch the spikes to progress.

🛠 Technical Implementation

1. Physics Engine

The game uses a custom physics loop to handle movement and gravity.

Gravity: Velocity is updated per frame: $v_y = v_y + g$

Movement: Position updates based on velocity: $y_1 = y_0 + v_y$

2. Collision Detection (AABB)

We implemented Axis-Aligned Bounding Box logic to handle interactions between the player, platforms, and hazards.

boolean horizontalOverlap = playerRight > obsLeft && playerLeft < obsRight;
boolean verticalOverlap = playerTop > obsBottom && playerBottom < obsTop;
return horizontalOverlap && verticalOverlap;


3. Architecture

The project follows a modular Object-Oriented structure:

OmerFarukKaragoz.java: Main entry point & Game loop.

Game.java: State management, UI rendering, and input processing.

Player.java: Sprite management and kinematic properties.

Map.java: Level layout and collision handling.

Stage.java: Configuration for physics parameters and stage twists.

🏁 Installation & Running

Prerequisites

Java Development Kit (JDK) installed.

StdDraw.java must be in the project directory.

Launching the Game

Clone the repository.

Navigate to the directory and run:

java OmerFarukKaragoz.java


🎥 Gameplay Demo

Check out the full gameplay and mechanics explanation on YouTube:
Watch the Demo

📝 License

This project was developed for educational purposes. Feel free to use and modify!
