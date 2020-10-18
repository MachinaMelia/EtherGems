# Ether Gems ![](https://img.shields.io/github/license/MachinaMelia/EtherGems.svg) ![](https://img.shields.io/badge/release-1.15.2--1.0.0.0-brightgreen) ![](https://cf.way2muchnoise.eu/versions/413038.svg) ![](https://cf.way2muchnoise.eu/413038.svg) 

This mod is intended to bring [Xenoblade Chronicles](https://www.nintendo.com/games/detail/xenoblade-chronicles-definitive-edition-switch/) gem crafting into Minecraft as faithfully as possible.

In your world you will now find Ether Deposits, concentrated crystals of the World’s lifeblood. Pure Ether Deposits yield pure crystals which can be used to make a refining device known as an Ether Furnace. These crystals appear where high amounts of their respective elements are present. Normal Ether Deposits spawn in any biome, with no preference between elements, you just have to go mining. These crystals can then be refined and added to weapons and armor that have accommodating slots for gems, granting their user unique abilities.

Check out the [CurseForge page](https://www.curseforge.com/minecraft/mc-mods/ethergems) for more info!

## Features

1. Adds Ether Deposits in the world that drop Crystals
2. Adds an Ether Furnace to craft Crystals into Gems.
3. Adds Gems that enhance combat and gameplay.
4. Adds Gem Slots to Armor, Swords, and Axes
5. Adds a Gem and Crystal Inventory screen.

## How to install

Download [Forge](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.15.2.html) and install it into your Minecraft directory. (Note this project uses Forge 1.15.2-31.2.0)


Download one of the pre-built `.jar` files from the Releases section or build your own (see below)!

Place it in your `.minecraft/mods` folder and enjoy!

## Submit an Issue
This mod is new and may have incompatibilites with other mods. Please let us know these so we can fix them. You can submit issues [here](https://github.com/MachinaMelia/EtherGems/issues).

##### Known issues
[AutoRegLib](https://www.curseforge.com/minecraft/mc-mods/autoreglib) required by [Quark](https://www.curseforge.com/minecraft/mc-mods/quark) causes armor and weapons to be created without slots.

## Building

Make sure the latest version of Java JDK 1.8.0 is installed.

```zsh
git clone https://github.com/MachinaMelia/EtherGems.git
```

In the root directory of the project run
##### MacOS or Linux
```zsh
./gradlew build
```

##### Windows
```cmd
gradlew.bat build
```

The `.jar` file can be found under `./build/libs`

You can also import the `build.gradle` file into [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).

## License

Copyright © 2020 MachinaMelia

The code in this project is licensed under LGPL-2.1 or later. It can be found [here](https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html).

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.
