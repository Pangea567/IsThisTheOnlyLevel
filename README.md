# 🐘 Is This The Only Level? – Java Implementation

A Java reimplementation of the classic puzzle-platformer **“Is This The Only Level?”** built using the **StdDraw** graphics library. This project demonstrates fundamental **game development concepts** such as physics simulation, collision detection, object-oriented design, and stage-based gameplay mechanics.

The goal of the game is simple: guide the elephant to the exit door. However, every level secretly changes the rules of the world. Although the map looks identical in each stage, a different mechanic forces the player to rethink how to solve the level.

---

## 🎮 Gameplay Overview

The game consists of **five stages that appear visually identical**, but each stage introduces a hidden twist. The player must discover the rule change and adjust their strategy accordingly.

| Stage | Name | Twist |
|------|------|------|
| 1 | Standard | Normal movement and button mechanics |
| 2 | Inverted | Left and right controls are swapped |
| 3 | Moon Gravity | The player automatically jumps continuously |
| 4 | Persistence | The button must be pressed five times to open the door |
| 5 | Paradox | The player must touch spikes to unlock the door |

---

## ⚙️ Technical Implementation

### Game Loop

The game uses a continuous loop to handle player input, physics updates, collision detection, and rendering. The loop updates the game state frame-by-frame using `StdDraw.pause(20)` which creates a simple frame-based animation system.

### Physics System

Player movement is controlled using a simple kinematic physics model.

Velocity update:

```
vy = vy + gravity
```

Position update:

```
y = y + vy
```

This allows the player to experience gravity and jumping behavior similar to classic platformer games.

### Collision Detection (AABB)

Collision detection between the player and world objects is implemented using **Axis-Aligned Bounding Box (AABB)** logic.

```java
boolean horizontalOverlap = playerRight > obsLeft && playerLeft < obsRight;
boolean verticalOverlap = playerTop > obsBottom && playerBottom < obsTop;

return horizontalOverlap && verticalOverlap;
```

This approach is used to detect interactions between the player and platforms, walls, spikes, buttons, and the exit door.

---

## 🏗 Project Architecture

The project follows a **modular object-oriented structure** that separates game responsibilities into different classes.

```
OmerFarukKaragoz.java   → Main entry point and game loop
Game.java               → Game state management and rendering
Player.java             → Player movement and physics
Map.java                → Level layout and collision objects
Stage.java              → Stage-specific mechanics and rule configuration
```

This architecture makes it easier to manage stage-specific mechanics without modifying the core game logic.

---

## ▶️ Running the Project

### Requirements

- Java Development Kit (**JDK 8 or higher**)
- `StdDraw.java` library in the same project directory

### Compile and Run

Clone the repository:

```
git clone https://github.com/yourusername/this-is-the-only-level-java
```

Navigate to the project directory and run:

```
javac *.java
java OmerFarukKaragoz
```

---

## 🎥 Gameplay Demo

A gameplay demonstration video is available here:

YouTube: *(Add your video link here)*

---

## 🎓 Educational Purpose

This project was developed as part of a **Java programming assignment** to practice important computer science and software engineering concepts such as:

- Object-Oriented Programming (OOP)
- Game loop design
- Physics simulation
- Collision detection
- Modular game architecture

---

## 📄 License

This project is created for **educational purposes**. Feel free to explore, modify, and experiment with the code.
