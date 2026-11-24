# Java Solitaire

## About

This repository contains my CLI implementation of the Klondike Solitaire Game to be submitted as a deliverable for the
Java Training Session

This project is written with `Java JDK 25.0.4` and built using `Maven`.

## Getting Started

1. Clone Repository

```shell
git clone https://github.com/yukiroow/solitaire-java.git
```

2. Open project in your Java IDE (Maven)

3. Compile the project and run

## Running the Game

1. Prepare a text file containing a 52-Card Deck delimited by commas, with each card in SUIT-RANK format (Ex. `D-K`,
   `H-10`, `S-2`, `C-A`)

> TIP: You may copy and paste the string of text below to guarantee a No-Shuffle win :)

```plaintext
D-A,H-A,S-A,C-A,H-2,S-2,C-2,H-3,S-3,C-3,
H-4,S-4,C-4,H-5,S-5,C-5,H-6,S-6,C-6,H-7,
S-7,C-7,H-8,S-8,C-8,H-9,S-9,C-9,D-2,D-3,
D-4,D-5,D-6,D-7,D-8,D-9,D-10,D-J,D-Q,D-K,
H-10,H-J,H-Q,H-K,S-10,S-J,S-Q,S-K,C-10,C-J,
C-Q,C-K
```

2. Package the Game

```shell
mvn clean compile
```

3. Run the Game using the generated JAR file

```shell
java -jar harrydom-solitaire-0.1.jar <deck_file> [ -visible | -hidden ]
```

> TIP: You can use the `-visible` or `-hidden` flags to show or hide the face-down cards in a Manoeuvre Pile. The
> `-hidden` flag is loaded by default when no flag is specified.

## Original Specification

### Classic Solitaire: Klondike Solitaire

Sometimes called by the generic name 'Solitaire' (eg Microsoft's Solitaire is actually Klondike Solitaire). This is
probably the most widely known solitaire game in the world, so is naturally a favourite. Klondike Solitaire involves a
fair amount of luck, but there is still some skill involved. The success rate is moderate.

### Aim

The aim of Klondike Solitaire is to build ascending suit sequences, in the foundation zone.

### How to Play

The opening tableau has:

- 7 **manoeuvre** stacks, of increasing length `++`;
- 4 **foundation** stacks, and;
- the **talon**.

In the **manoeuvre** zone, form descending sequences of alternate color. Multiple cards forming a run can be moved at
once.

At any time, flip cards from the **talon**. Where possible, move these cards into the **manoeuvre** zone, or directly
into the **foundation** zone. If, during the course of play, a **manoeuvre** stack becomes empty, only a King can be
moved onto it.

You may cycle through the **talon** an unlimited number of times.

