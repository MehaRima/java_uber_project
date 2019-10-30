# OOP Course Final Project : Uber

Welcome to the repo for my OOP final course project that I developed at the end of 2018 !

## Overview

The general idea behind this project was to simulate interactions between fictious users and a Uber-like platform that manages drivers and vehicles.

-   Users can wander around in a fictious environment delimited by a square, and call a driver in order to go to a different location.
-   Driver assignment is automatically handled by the platform by looking at the relative distance to the available drivers and by taking into account certain KPIs regarding the drivers.

-   Several ride types are implemented (UberX, UberBlack, UberPool...) with their own pricing, passenger capacity and other specificities.
-   Traffic conditions (that affect travel time and pricing) are also randomly simulated and bring some additional complexity to the model.

This project was my first experience developing a (relatively) large object-oriented program, and I was also a newcomer to Java. As a result, many beginner-level mistakes and anti-patterns were certainly made, but it was also a good opportunity to get initiated to software development and implementing good practices (unit testing, documentation, exception handling), design patterns, and OOP concepts (polymorphism, inheritance, data encapsulation, etc.).

Working on this project was a stepping stone that introduced me to the world of OOP and got me interested in various other languages and projects in the following months : object oriented JavaScript for mobile and web development (React/React Native), C# for Unity game development, object-oriented Python (applied to web dev with Django), C++.

## Project structure

All the Java source code is in the `src` folder, that is further split into :

-   `model` that contains the UML diagrams designed before starting the actual implementation, made with [Papyrus for Eclipse](https://www.eclipse.org/papyrus/).

-   `mainClasses` contains the definitions for the main objects of the project (`Car` and its subclasses, `Driver`, `Ride` and its subclasses...)
-   `helpers` contains the definitions for all the utils and helper classes used elsewhere in the code.
-   `exceptions` contains the definitions for the custom exceptions that were created for the project.
-   `designPatterns` contains the definitions of the classes/interfaces useful for implementing certain design patterns (Factory, Observer...).
-   `tests` contains all the unit tests of the project.
-   `cli` contains the classes that implement the Command Line Interface and its associated commands. The entry point of the CLI is the `main` function of the `CLImain.java` file. When launching the CLI, the `help` command is there to inform you of all the possible actions.
-   `gui` contains the classes that implement the Graphical User Interface. The entry point of the GUI is the `main` function of the `mainview/GUImain.java` file. The interface was developed using the _Swing_ library for Java.
-   `resources` contains the static assets for the GUI.
-   `eval` contains "test scenario" files that can be input to the CLI using the `init` command.
-   `resources` contains the static files (images) used for the GUI.

The docs can be generated using the "Generate JavaDoc" command in the Eclipse IDE, or using the Java command line.
