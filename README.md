# Java Solitaire

## About

This repository contains my CLI implementation of the Solitaire Game to be submitted as a deliverable for the Java
Training Session

This project is written with `Java JDK 25.0.4` and built using `Maven`.

## Getting Started

1. Clone Repository

```shell
git clone https://github.com/yukiroow/war-cards-java.git
```

2. Open project in your Java IDE (Maven)

3. Build artifact

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

In the **manoeuvre** zone, form descending sequences of alternate color. Multiple cards forming a run can be moved at once.

At any time, flip cards from the **talon**. Where possible, move these cards into the **manoeuvre** zone, or directly into the
**foundation** zone. If, during the course of play, a **manoeuvre** stack becomes empty, only a King can be moved onto it.

You may cycle through the **talon** an unlimited number of times.

## My Game Flow Algorithm

TODO