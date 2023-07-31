Prompts to be posted to Moodle shortly, stay tuned!
What was your approach to tackling this project?
What new thing(s) did you learn / figure out in completing this project?
Is there anything that you wish you had implemented differently?
If you had unlimited time, what additional features would you implement?
If you could go back in time and give your past self some advice about this project, what hints would you give?

We started this project by brainstorming the story we wanted, along with the rooms/locations, NPCs, items, and furniture needed for that. Our approach to tackling this project was first setting up the structure of our codebase, including our classes and their attributes, then working on the gameplay.  We spent a lot of time organizing how we wanted to build our rooms, and initially had each room as their own class before making just one room class and building each as their own instance (this also allowed us to make our large game map of type room). It was easier then to start working on the game itself because all of our locations, items, and furniture were already made and had the methods we needed.  
New things we learned while working on this project were how to work with a third party implementation when using Guava for our graph, how to navigate a graph, and ways we can use interfaces and parent classes to our advantage.  
When thinking about things we would have implemented differently, it would have been easier if we just made one large graph, instead of a graph of rooms and then smaller furniture graphs and if we allowed items to be attached to furniture instead of the room.  Right now, items in the room can be picked up anywhere in that room, it would be better for gameplay if they could only be picked up at certain furniture locations.  We also could have used generics to make better bag and hand methods for the inventory instead of switching between them.  An alternative data structure we could have used for the inventory would be a stack, to reflect that if something was underneath something else in the bag it couldn't be reached, but we like to think that the satchel has pockets so everything can be reached at all times.  
If we had unlimited time, we would have expanded on the storyline involving freeing all the rats, added an actual maze to the beginning, and added more NPCs. In addition to just freeing all the rats from their cages, you could also be able to free them from the testing facility with you.  We would have added more scientists and rats you could interact with and play more mini-games. 
If we could go back and give ourselves advice on this project, we would have looked further into other graph implementation methods we could have used other than guava.  Hints we would give ourselves would be to think about how you want items and classes to be connected to each other and how do you want to move between them.  

