Here's our notes and planning for this project
-
- guava graph documentation: https://guava.dev/releases/23.0/api/docs/com/google/common/graph/Graph.html
https://github.com/google/guava/wiki/GraphsExplained#building-graph-instances


- make replit run faster maybe
https://replit.com/talk/learn/How-to-Avoid-Run-Button-Lag-And-Make-your-REPLS-fasterhigh-voltage/49374
  

MONDAY CLASS:
  - x import arraylist 
  - - x add to arraylist correctly
  - 
- maybe start on the big general graph
  - move from one point to another
  - all of type room
- fix descriptions for items

Wednesday Class:
- maybe start on the big general graph
  - move from one point to another
  - x all of type room
- **need a graph by friday

Thursday meeting:
- x smaller furniture graphs
- moving around via commands on the smaller graph

Friday 4/22 class:
- x peer code review
  - discussed moving between nodes
  - data structures (graph acting as binary tree, arrayList to hold items, maybe add more in mini games)
- x no more errors! beginGame is running and accepts a text input
- x fix printing out getFurniture list to suggest possible moves
- x reorganized structure of building graph into its own file, made some more errors but were able to fix by moving some calls around
- x move!!!!!! within testing room
- x added another data structre, set to store nodes in graph

Monday 4/25 Class:
- items
  - x picking up
  - x dropping off

Monday 4/25 meeting:
- x npcs
  - --> made into furniture
- other commands with items
- x bag/satchel
  - x old rat meds situation

Tuesday 4/26 meeting:
- x items
- x restrictions using items
  - not being able to go outside from the breakroom wo they keycard
  - not being able to go outside from the roof without the rope

Wednesday 4/27 in class:
- x items
- mini games
- make it a playable
  - new line after answer
  - each letter comes out space by space
  - if list is empty--> no pickup command

Thursday 4/28 meeting:
- x drop command
- x make cage keys free the rats
- make clear pain meds old rat satchel situation
- x rat translator?
- x confusion btwn cageKey and keyCard
- better room description in general
- x you are in blank room


BIG GOAL:
- x big graph done by end of week
- x move
- x check insides of arraylists. make sure it works
- x big graph of rooms
- x furniture little graphs
- x commands
- x mini games
- done!

list of commands:
- look
- inventory/satchel
- take
- drop
- eat
- kill scientist with rat sword
- move/go

take input split on space, if else for all possibilities 


front end things that jordan is talking about
- text-based interface
- engaging storyline
- restricted paths
back end things that jordan is talking about
 - are the data structures effective + efficient
 - is the design extensible
 - have we made an architecture diagram describing the relationship between classes
 - would that just entail like how main creates rooms and npcs and where we want to actually run the game?



RAT NEEDS GLASSES

ideas from code review??
 - options from current location are edges
 - 
todo
- pick up items
- puzzles, restrictions
- talking to NPCs
x go between big graph and little graph
- reach the ending?


todo
- x ending!
- x error handling for input
- x npcs
- x how we interact with items
- mini games
- x delete commands? carryable?
- x drink cleaning supplies, die
- maze
- x comments
- drop and go throws an exception
  - x if item is not in bag/hands FIXED (i think)
  - x if only the command is said FIXED (i think)