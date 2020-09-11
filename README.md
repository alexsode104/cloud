<div align="center">
    <img src="icons/cloud.svg" width="300px" />
</div>

# Command Library Of (yo)Ur Dreams

[![CodeFactor](https://www.codefactor.io/repository/github/sauilitired/cloud/badge)](https://www.codefactor.io/repository/github/sauilitired/cloud)

This is going to be a general-purpose Java command library. It will allow programmers
to define command chains that users can use to execute pre-defined actions.

The library was named cloud because using it makes you feel like you're floating around on a cloud in heaven. It's an experience of pure bliss and joy. This is unlike the experience of using **a**ny **c**ommand **f**ramework that currently exists for the JVM, which can be compared to drowning in a pool of lava while watching your family get eaten by a pack of wolves. 

Its feature set is derived from **a**lready existing **c**ommand **f**rameworks, while being less restrictive, opinionated and confusing. CLOUD is built to be completely deterministic and your commands will behave exactly as you've programmed them to. No fluff and no mess, just a smooth cloud-like experience.

The code is based on a paper that can be found [here](https://github.com/Sauilitired/Sauilitired/blob/master/AS_2020_09_Commands.pdf).

## Goals

- Allow for commands to be defined using builder patterns
- Allow for commands to be defined using annotated methods
- Allow for command pre-processing
- Allow for command suggestion outputs

Once the core functionality is present additional goals are:

- Create a Minecraft specific implementation and add appropriate bindings (bukkit, paper, velocity and bungee) with Brigadier mappings where appropriate
- Create a Discord implementation
- Create a Java CLI implementation

## Links

- Discord: discord.gg/KxkjDVg


## Maven

cloud is available from [IntellectualSites](https://intellectualsites.com)' maven repository:

```xml
<repository>
    <id>intellectualsites-snapshots</id>
    <url>https://mvn.intellectualsites.com/content/repositories/snapshots</url>
</repository>
```

```xml
<dependency>
    <groupId>com.intellectualsites</groupId>
    <artifactId></artifactId>
    <version></version>
</dependency>
```
