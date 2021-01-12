# TitanUtil
[![](https://jitpack.io/v/frc5431/TitanUtil.svg)](https://jitpack.io/#frc5431/TitanUtil)

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
There are a few ways of installing TitanUtil, the easiest way is to use Gradle as GradleRIO is the new compiler.

## Gradle
Add the following to the root build.gradle
```
allprojects {
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency (Make sure it's the latest release)
```
dependencies {
    implementation 'com.github.frc5431:TitanUtil:2.1.0' //Replace 2.1.0 with the latest release
}
```

## Maven

Add the following code to your `<repositories>` tag
```
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Then, add the following dependency (Make sure it's the latest release)
```
<dependency>
    <groupId>com.github.frc5431</groupId>
    <artifactId>TitanUtil</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Jar
You can go to the releases download the jar or you can clone the repo (look right above you)
to download the compiled library. Added this to your build path, and it's recommended that you
add it relative to project in eclipse so that you can develop on multiple computers without library
issues.

## Basic
Copy the [components](https://raw.githubusercontent.com/frc5431/TitanUtil/master/src/main/java/frc/team5431/titan/components/) folder into your project. 
All of the core/basic utilities are found in there.

### License

BSD 2-Clause License

Copyright (c) 2021, 5431 Titan Robotics
All rights reserved
