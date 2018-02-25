# TitanUtil

[![](https://jitpack.io/v/frc5431/TitanUtil.svg)](https://jitpack.io/#frc5431/TitanUtil)

A collection of utility classes to make robot programming easier for all robots.
Do not riddle yourself with redundant and repetitive code when you have a library
that will do all of the grunt work for you.

# Features
**Current features include:**
## Core
* Motor toggling
* Built-in joystick deadzoniung
* Xbox and drone controller button mapping
* Game data parser

## Mimic
* Observing and Mimicing basic drivebase pathfinding data
* Saving mimic data to a flash drive
* Loading mimic data from a flash drive
* Portability to add more complex mechanisms like intakes

# Install
There are a few ways of installing TitanUtil

## Gradle
Add the following to the root build.gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency (Make sure it's the latest release)
```
dependencies {
    compile 'com.github.frc5431:TitanUtil:1.0.5' //Replace 1.0.5 with the latest release
}
```


## Clone
This is a idea (jetbrains) and eclipse tandem project. You may clone the repository
and import it through either IDE. However, if you want to go through the troubles
of cloning, why not just switch over to gradle and not have the worry about deleting
and cloning again!

```
$ user@computer: git clone git@github.com:frc5431/TitanUtil.git
```

Now you can go into either intellij (recommended) or eclipse and import the project!

## Basic
Copy Titan.java from the src core folder into your project. All of
the core/basic utilities are found in that one file