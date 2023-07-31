Final Project

For your final project in CSC 212, we want you to implement something interesting to you, involving data structures, that takes you beyond what you have already learned in the course. You may choose to work alone, or in teams of up to 3 people.

In contrast to our weekly homework assignments, your project will require to you to not only implement the various components of your project, but also to reason through the design of the underlying data structures and how they'll communicate with one another.

This document describes a "default project" that we encourage you to work on if you find the premise interesting. On the other hand, if you have a different project idea that appeals to you more and fits within the parameters of the course, that's equally welcome! (Details on proposing your own project apear at the end of this document, below the instructions for Option 1.)
Option 1: Default Project - A Text Adventure

In the early days of computing, before graphics-based video games were a thing, people wrote games based on prose alone. Called text adventures, they are a combination of story and puzzle, requiring the player to figure out the best way to advance through a sequence of barriers and challenges. For the final project, you can recreate this bygone art form by writing and implementing your own personal text adventure.

Before starting, if you would like to experience some of the classics, you can play them online through a web emulator. Here are several landmark games:

    Colossal Cave Adventure
    Zork: The Great Underground Empire
    Beyond the Tesseract -- a mathematically-inspired game featuring a data structure you've learned about this semester!

Text adventure games generally start with a brief introduction, setting the scene. For example, a text-based adventure game called Escape from Ford Hall might begin like this:

It is a beautiful autumn day at Smith College. You have recently arrived on campus after several grueling semesters of remote learning, and are feeling both nervous and excited to start the school year. You are taking CSC 212 this semester, and so you decide to familiarize yourself with Ford Hall to be sure that you are familiar with how to get to the room where TA Hours will be held (you've heard they're a lifesaver).

Current location: Ford atrium.
You are in the atrium of Ford Hall. It's very sunny.
Exits:
Stairs go up.
Stairs go down.
Double doors go north.
What do you want to do? >

To interact with a text adventure game, you issue simple two-word commands consisting of a verb and an object, such as GO NORTH, GET LAMP, LIGHT LAMP, etc. Continuing the above example, you might issue the following command, which triggers a corresponding phase of the story:

What do you want to do? > GO UP
You climb the stairs.

Current location: Ford second floor.
You are on the second floor of Ford Hall.
Exits:
Stairs go up.
Stairs go down.
What do you want to do?  >

The simplicity of the commands means that they are easy to parse: the verb (describing the action) comes first, and the object (what you're acting on) comes second. Some commands may work only on a specific kind of object, while other instructions might be universal (such as moving your character around). If the game doesn't recognize a command, or cannot apply the given verb to the given object, then it should report to the user that it is confused, and ask for a new instruction.

The environment in a text adventure is a series of locations, which a player can move between according to predefined connections (hmm, entities with connections between them -- sounds familiar...). Each location has a description, a collection of available paths/exits, and possibly one or more creatures or items you'll encounter at that location. In some cases you may need to accomplish a task or fulfill some condition before options become available. For example, you may need to be carrying a key in order to move through a locked door to traverse the connection leading to a new location.
Phase 0: Worldbuilding

We recommend starting by thinking about your story:

    What is the premise of your game?
    Who is the main character?
    What is their objective, or what problem are they trying to solve?
    Where do they find themselves at the beginning of the game, and where do they need to wind up?
    Will they need to pick up any objects, gain any skills, or unlock any achievements along the way?

You may find it helpful to plan your game out on paper before implementing it. We strongly recomment drawing a map with all your locations and showing the connectivity between them.

If you choose to include them as a feature of your game, you should also note the locations of any artifacts or objects, as well as their eventual use. For example, the map for Escape from Ford Hall might include the following location and note:

    Nick's Office: contains many plants, a green bicycle, and Nick's OneCard --> required to open Nick's Lab
    Jordan's Old Office (a windowless closet): contains cobwebs, a half-empty pack of orange Tic Tacs, and a sealed envelope marked "CONFIDENTIAL" --> contains secret code required to unlock hidden compartment in Ford Basement

To start development you can hard-code a small game environment and use it for testing. As your game environment gets bigger, you can continue to use hard-coding, or you could store the game information in a text file that is loaded in to generate the game's environment -- much as we did with the decision tree assignment.
Phase I: Moving Around

A minimum playable game will offer an environment where the player can move around and explore by taking different paths. There should be at least one terminal location where the player wins (or loses) and the game ends. This section poses a series of questions that can help you in planning your program.
Game Play

Most games have a special class that sets up and runs the game. For sake of uniformity, let's call it Adventure. Consider these questions:

    While the game is running, the interaction with the user will probably happen within some sort of loop (or you could use recursion). Envision the game in action. What needs to happen within each iteration of this loop?
    What utility functions would make the loop succinct and easy to read?
    What setup needs to take place before the game runs? (You might return to this question after considering the other parts of the game discussed below.)
    How will the program detect that the game has ended?

Locations

You will likely want to have a class that keeps track of information about different locations in the game. For sake of uniformity, let's call it Place. Consider these questions:

    How will different places be identified?
    How will the game know how to describe a place to the player?
    How will the game know whether a place is a terminal location?
    How will the game know what directions are valid moves (i.e., will lead them to a new location)?
    How will the player know what directions are valid moves?
    How can you quickly and accurately verify that your game environment includes all the locations you intended?

Connections

You will probably also want to have a class that keeps track of the connections between different locations. For sake of uniformity, let's call it Exit. Consider these questions:

    How will different connections be identified?
    How will the starting and ending points of a connection be known?
    Will connections be traversible in both directions, or only one way?
    If both directions, will the description and action of the exit be the same both ways?
    How will the game know how to describe the transition between one place and another to the player?
    How will travel directions be represented in the game?
    How will the direction words specified by a player (e.g., NORTH) be converted to an internal representation (if different)?
    How will the game associate the direction specified by a player (e.g., NORTH) with a particular connection to another location?
    How can you quickly and accurately verify that your game environment includes all the connections you intended?

Generality

Besides the above, you should consider questions relating to the development and maintenance of the game.

    If you want to add additional locations to the game, how will that work in your implementation?
    If you want to add additional connections between locations, how will that work in your implementation?
    If you want to replace the entire environment with a new set of locations and connections, how will that work in your implementation? If you want to replace the entire environment with a new set of locations and connections, how would the new environment be specified in the first place?
    How will you test each of the classes you write?

Phase II: Enhanced Gameplay

Creating a navigable environment is a great achievement, and a good start to a game. To make a really compelling game, you'll need to add some challenges that the player must overcome in order to make progress. This might mean finding a key, casting a spell, sneaking past a guard, etc. The possibilities are endless!

Of course, you'll need to figure out how to incorporate these challenges into your program. In general, there are two approaches: the first is to sprinkle specialized code here and there in your program that applies to a specific situation, and implements the resolution of the challenge. (E.g., if you are above the drawbridge, and issue the command PULL LEVER, then an additional exit is added on the floor below so that the player can cross the drawbridge.) Since this approach is ad hoc, it's up to you to decide how it should work. It's not particularly extensible, but if the number of challenges is small then you shold be able to manage it.

The second approach is to design your game with a general mechanism that will support challenges, and then use it to craft the specific situations the player will face. For example, maybe exits generally require the player to possess some item (key, identification, etc.) before becoming accessible. Under this scheme, ordinary exits can list no item as required, so they are always accessible. Similarly, maybe some items cannot be collected unless one first has (or does not have...) another item. And so on. These sorts of restrictions can be built into the game from the start, so that every exit potentially requires an item to use, every item requires some condition to be picked up, etc. Only those with null for the specified requirement are always available.
Designing Challenges

Consider the following list of questions about the types of challenges you wish to include in your game:

    What types of things can't happen until certain challenge conditions are met? Seeing exits? Taking exits? Picking up items? Using items in some other way?
    How will you implement the prohibition until the challenge is overcome?
    What kinds of conditions could lead to solving a challenge? Holding an item? Not holding an item? Visiting a particular location? Other ideas?
    How will the solving of a challenge be stored or represented in your game? In other words, how will the game know that a challenge has been solved?
    How will the player know that they have solved a challenge?

Items

Challenges often revolve around items. In fact, with a loose interpretation of the concept, items can become a general mechanism for keeping track of all challenges that have been overcome. For example, a player might gain an invisible item "Slayed the Dragon" that won't show up in their inventory but would nevertheless allow them to enter the dragon's cave and take the treasure inside.

If you wish to include items in your game, let's assume you will have a class called Item. Consider these questions:

    How will the location of an item be represented/recorded in your game?
    How will the player know what items are present in a location?
    How will the player know what items they are carrying?
    Will some items be invisible to the player, and if so how will the game know which items are invisible?
    Will items just be tokens that the player carries around, or will it be possible to perform actions with/on them?
    How does the game know what actions are possible with a given item? For example, you can light a lamp but not a sword.
    How does the game know the effect of a given action performed on a given item (assuming that the action is allowed)? How is the effect then implemented?
    Does the player need to hold an item to perform an action on it? Or does it just need to be in the same location? Or does it vary by item, and if so how will the game know the difference?

Other Questions

    What other effects might you trigger as a result of some player action?
    Might the descriptions of locations or exits change?
    Do you want an element of time in your game? Every action could use up a certain amount of game time, and other effects could be based on the current time. Do descriptions change based on the time? Are some actions available only at certain times? Is there a limited amount of time to solve the game?
    What other creative additions can you come up with?

Option 2: Propose Your Own!

If you like, you can come up with your own plan for the final project. Your project should involve a significant application of some data structure, and should in some way take you beyond what we have done already in class and in the previous homework assignments.

Your project should be similar in scope and complexity to the text adventure example described here. To propose a custom project, you should write up a one-page description of what you intend to accomplish and how it will extend your understanding of data structures. Include a checklist with point values, similar to the one given here for the text adventure project. We recommend that you bring your idea to us in office hours before submitting a proposal.

To ensure that you will have time to work on the project, we need your proposal by Friday, April 15. We'll reply with feedback and (assuming everything looks ok) approval to go ahead.