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
There are a few ways of installing TitanUtil

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
    <version>1.0.5</version>
</dependency>
```

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

## Jar
You can go to the releases download the jar or you can clone the repo (look right above you)
to download the compiled library. Added this to your build path, and it's recommended that you
add it relative to project in eclipse so that you can develop on multiple computers without library
issues.

## Basic
Copy [Titan.java](https://raw.githubusercontent.com/frc5431/TitanUtil/master/src/main/java/frc/team5431/titan/Titan.java) into your project. All of
the core/basic utilities are found in that one file

# License
GNU GENERAL PUBLIC LICENSE
Version 3, 29 June 2007

Copyright © 2007 Free Software Foundation, Inc. <http://fsf.org/>

Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.

<br>
<br>
<br>
<br>
<br>
<br>

![](https://sites.google.com/a/pisd.edu/titan-robotics/_/rsrc/1493840311490/home-1/FullColorlogo%20with%20numbers.png?height=950&width=950)
