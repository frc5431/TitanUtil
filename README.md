# TitanUtil
[![](https://jitpack.io/v/frc5431/TitanUtil.svg)](https://jitpack.io/#frc5431/TitanUtil)
[![](https://github.com/frc5431/TitanUtil/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/frc5431/TitanUtil/actions)

[Docs](https://www.frc5431.com/TitanUtil/)

A collection of utility classes to make robot programming easier.
Do not riddle yourself with redundant and repetitive code when you have a library
that will do all of the grunt work for you.

# Features
**Current features include:**
## Core
* Motor toggling
* Built-in joystick deadzoning
* Xbox and drone controller button mapping
* Game data parser

## Robot
* Command based robot control
* Axis command groups (useful for driving)
* Builtin core utilities
* Added Components and Cross-Class Sensor Data

## Mimic
* Observing and Mimicing basic drivebase pathfinding data
* Saving mimic data to a flash drive
* Loading mimic data from a flash drive
* Portability to add more complex mechanisms like intakes

# Install
The supported way of introducing TitanUtil to a project is via Gradle. Gradle 
is the current build system for WPILib robot projects.

To install, add the following to the root build.gradle
```
allprojects {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

and, add the dependency (Make sure it's the latest release)
```
dependencies {
    implementation 'com.github.frc5431:TitanUtil:2021.10.1' //Replace tag with the latest release if needed
}
```

# License

BSD 2-Clause License

Copyright (c) 2021, 5431 Titan Robotics
All rights reserved

Refer to LICENSE file for more information, located in the base of this project

