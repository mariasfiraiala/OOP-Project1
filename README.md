Copyright 2022 Maria Sfiraiala (maria.sfiraiala@stud.acs.upb.ro)

# GwentStone - Project1

## Design Patterns Used

1. Singleton Pattern

This pattern (or better called, anti-pattern) was used in order to simulate the presence of 3 global variables: `numberWinsPlayer1`, `numberWinsPlayer2` and `numberOfGames`.
Due to these values being required to maintain their values game after game (in order to provide statistics), the choice of using a Singleton pattern was obvious.

## Structure Of The Project

* `src/`
  * `cards/` - contains card related classes
    * `Card` - generic implementation of a card, will be used for inheritance in classes such as `Minion`, `Environment` and `Hero`
    * `Deck` - constructs decks based off `Minion`, `Environment` and classes that inherit them: `Disciple`, `Miraj`, `TheCursedOne`, `TheRipper` and `Firestorm`, `HeartHound`, `Winterfell`
    * `Disciple` - implementation for special minion 
    * `EmpressThorina` - implementation for Empress Thorina hero
    * `Environment` - abstract class, used for implementing special `Environment` cards
    * `Firestorm` - implementation for Environement card with the same name
    * `GeneralKocioraw` - implementation for General Kocioraw hero
    * `HearHound` - implementation for Environement card with the same name
    * `Hero` - abstract class, used for implementing special `Hero` cards
    * `LordRoyce` - implementation for Lord Royce hero
    * `Minion` - generic implementation for Minion card
    * `Miraj` - implementation for special minion
    * `TheCursedOne` - implementation for special minion
    * `TheRipper` - implementation for special minion
    * `Winterfell`- implementation for Environement card with the same name
  * `game/` - contains implementation entrypoint class and basic gameplay structure
    * `Game` - starts game: gets players info, seed, starts the action based system
    * `PrepareGame` - entrypoint of the program, its constructor starts the project
    * `Statistics` - gets statistics regarding the number of games played and winners
  * `table/` - contains commands and player related classes
    * `Commands` - class with 2 subclasses, each one of them with its own functionality: regular commands and debug commands
    * `Player` - info about the player: its indeces for the table, mana, etc

## Program Flow

Execution starts with the creation of a new object of type `PrepareGame`: it makes sure to go through all the games present in the array, initialize them, shuffle players decks and start interpreting commands. 

`Game` takes care of this functionality: it stores the current players, the order in which they apply commands (player turn flow) and correctly starting actions.

These actions have their own array, that we iterate through. For each command, we decode it and we call its method, from `Commands`, depending on its type.
It's possible to do so as we've marked the methods as `static`.

**Note**: Getting the information out of the classes provided by the skel is done by inserting cards in decks that were already constructed to mirror their type: `Minion` and `Environment` with their inheritors.

Once we've got our information safely saved in objects, the gameplay flow becomes easier to follow: each command is implemented as mentioned in our rules.

**Note**: Once the hero of one of our players is killed, the `whoWon` flag is set and any command that affects the table (or that isn't of debug type) is bloked from being applied.
We also set the statistical global variables with the needed information in order for it to be able to be printed right away.