import com.google.common.graph.*;
import com.google.common.io.Flushables;

import java.util.*;

/**
 * Class to build the graphs, items, furniture, and npcs of the game
 * 
 * @author Allison Reel, Laura Dreher, Joey Elsbernd
 * @version Spring 2022
 */
public class BuildGame {
  // rooms and their furniture graphs
  private Room breakRoom;
  private ImmutableGraph<Furniture> breakRoomGraph;
  private Room chemistryRoom;
  private ImmutableGraph<Furniture> chemistryRoomGraph;
  private Room hideyHole;
  private ImmutableGraph<Furniture> hideyHoleGraph;
  private Room ratRoom;
  private ImmutableGraph<Furniture> ratRoomGraph;
  private Room roof;
  private ImmutableGraph<Furniture> roofGraph;
  private Room storageCloset;
  private ImmutableGraph<Furniture> storageClosetGraph;
  private Room testingRoom;
  private ImmutableGraph<Furniture> testingRoomGraph;
  private Room outside;
  private ImmutableGraph<Furniture> outsideGraph;
  private ImmutableGraph<Room> roomGraph;

  /* constructor */
  public BuildGame() {
    makeRooms();
    makeGraph();
  }

  /*
   * method to get connected nodes in graph
   * 
   * @param current furniture, current graph
   * 
   * @return string of reachable locations
   */
  public String reachable(Furniture current, ImmutableGraph<Furniture> roomGraph) {
    String reachableNodes = "";
    Set<Furniture> nodes = roomGraph.adjacentNodes(current);
    for (Furniture node : nodes) {
      reachableNodes += node + ", ";
    }
    return reachableNodes;
  }

  /*
   * method to go to another loction
   * 
   * @param current furnitre, string of where to go
   * 
   * @return new furniture location
   */
  public Furniture goToFurniture(Furniture currentNode, String userString, ImmutableGraph<Furniture> roomGraph) {
    userString = userString.toLowerCase();
    Furniture newNode = new Furniture();
    Set<Furniture> nodes = roomGraph.adjacentNodes(currentNode);
    for (Furniture node : nodes) {
      String nodeName = node.getName().toLowerCase();
      if (userString.contains(nodeName)) {
        newNode = node;
      }
    }
    if (roomGraph.hasEdgeConnecting(currentNode, newNode)) {
      currentNode = newNode;
      return currentNode;

    }
    return null;
  }

  /*
   * method to move to new room location
   * 
   * @param current room, string of new room
   * 
   * @return new room location
   */
  public Room changeRoom(String newStr, Room currentRoom) {
    Room newNode = new Room();
    Set<Room> nodes = roomGraph.adjacentNodes(currentRoom);
    newStr = newStr.toLowerCase();
    for (Room node : nodes) {
      String nodeName = node.getName().toLowerCase();

      if (newStr.equals(nodeName)) {
        newNode = node;
      }
    }
    if (newNode.getName() == null) {
      return null;
    } else {
      return newNode;
    }

  }

  /*
   * method to add item to inventory
   * 
   * @param string of item name, current room location, rat reny
   * 
   * @return item added to inventory
   */
  public Item pickupItem(String newStr, Room currentRoom, Rat rat) {
    newStr = newStr.toLowerCase();
    Item newItem = new Item();
    ArrayList<Item> items = currentRoom.getItems();
    for (Item item : items) {
      String itemName = item.getName().toLowerCase();
      if (newStr.contains(itemName)) {
        newItem = item;
        currentRoom.getItems().remove(item);
        break;
      }
    }
    if (newItem.getName() == null) {
      return null;
    }
    if (rat.getHasBag() && !rat.handsFull()) {
      rat.addToBag(newItem);
    } else if (rat.handsFull()) {
      System.out.println("Your hands are full!! Maybe there is a satchel somewhere...");
      currentRoom.getItems().add(newItem);
    } else {
      rat.addToHands(newItem);
    }
    return newItem;

  }

  /* method to add attributes and make instances of each room */
  public void makeRooms() {
    // heres all our doors
    Furniture outsideDoor = new Furniture("Outside door", "A door with a red exit sign on it, it must go outside!");
    Furniture chemistryRoomDoor = new Furniture("Chemistry Room door",
        "A door to the Chemistry room. There are all sorts of concoctions in there.");
    Furniture testingRoomDoor = new Furniture("Testing Room door",
        "A door to the testing room. This was where your journey began.");
    Furniture breakRoomDoor = new Furniture("Break Room door", "A door to the break room, where scientists relax.");
    Furniture storageClosetDoor = new Furniture("Storage Closet door", "A door to a storage closet.");
    Furniture hideyHoleDoor = new Furniture("Hideyhole door", "A small rat-sized door.");
    Furniture ratRoomDoor = new Furniture("Rat Room door", "A door to a room full of rats in cages.");
    Furniture roofDoor = new Furniture("Roof door", "A hatch in the ceiling leading to the roof.");

    // breakroom
    String[] meanScientistChat = { "\"I hate rats\"",
        "\"Rats are only useful to us as cooks, thats why I'm turning all the rats into cooks.\"",
        "\"The only good rat is a cooking rat\"" };
    Furniture meanScientist = new Furniture("mean scientist", "", meanScientistChat);
    Furniture pantry = new Furniture("pantry", "You arrive at an unlocked pantry with food inside");
    Item food = new Item("food", true, "human food. yum yum.");
    this.breakRoom = new Room("break room",
        "You have entered the break room, where scientists take their breaks. In the room there is a pantry with human food inside, a mean scientist in the corner, and doors leading outside, to the Chemistry Room, and to the Hideyhole.");
    breakRoom.buildItemList(food);
    breakRoom.buildNPCList(meanScientist);
    breakRoom.buildFurnitureList(breakRoom.getFloor(), pantry, chemistryRoomDoor, hideyHoleDoor, outsideDoor);
    breakRoomGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(chemistryRoomDoor, breakRoom.getFloor())
        .putEdge(hideyHoleDoor, breakRoom.getFloor()).putEdge(outsideDoor, breakRoom.getFloor())
        .putEdge(breakRoom.getFloor(), pantry).putEdge(breakRoom.getFloor(), meanScientist).build();
    breakRoom.setRoomGraph(breakRoomGraph);
    breakRoom.getFloor().setDescription("You're standing on the floor of the break room. From here you can reach the pantry, the mean scientist, the hideyhole door, the chemistry room door, and a door leading outside");

    // chemistry room
    Item potion = new Item("potion", true, "Weird looking concoction... Maybe try to eat it?");
    Furniture stickyNote = new Furniture("note", "A sticky note. it reads: PASSWORD: 3345");
    Furniture cabinet = new Furniture("cabinet",
        "there is a glass cabinet with bottles inside. There is a lock on the cabinet, but there is a note stuck to the glass next to it...");
    Furniture tableCR = new Furniture("table",
        "There is a table cluttered with scientific equipment. A yummy smelling potion bubbles on top of a bunsen burner. Sadly there are no rat meds... Maybe somewhere else in the room.");

    this.chemistryRoom = new Room("Chemistry Room",
        "In the chemistry room there is a cluttered table on one side and a locked cabinet on the other. There are doors leading to the testing room, the break room, and the rat room");
    chemistryRoom.getFloor().setDescription(
        "You're standing on the floor of the chemistry room. From here you can reach the table, the cabinet, the testing room door, the break room door, and the rat room door");
    chemistryRoom.buildItemList(potion);
    chemistryRoom.buildFurnitureList(chemistryRoom.getFloor(), cabinet, testingRoomDoor, breakRoomDoor, ratRoomDoor,
        tableCR, stickyNote);
    chemistryRoom.buildNPCList();
    chemistryRoomGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(chemistryRoom.getFloor(), cabinet)
        .putEdge(testingRoomDoor, chemistryRoom.getFloor()).putEdge(chemistryRoom.getFloor(), tableCR)
        .putEdge(breakRoomDoor, chemistryRoom.getFloor()).putEdge(ratRoomDoor, chemistryRoom.getFloor())
        .putEdge(stickyNote, cabinet).putEdge(cabinet, chemistryRoom.getFloor()).build();

    chemistryRoom.setRoomGraph(chemistryRoomGraph);

    // hidey hole
    String[] oldRatChat = {
        "\"Oof, my back!! If you give me my pain meds, I'll trade you for my satchel. I think I saw them in the chemistry room.\"" };

    Furniture oldRat = new Furniture("old rat",
        "An old sage rat. He looks like he has back pain. Maybe try talking to him?", oldRatChat);

    this.hideyHole = new Room("hideyhole",
        "You are in a hideyhole with paths to the rat room door, the testing room door, and the break room door. There is an old sage rat in the corner with a lot to say");
    hideyHole.buildNPCList(oldRat);
    hideyHole.buildItemList();
    hideyHole.getFloor()
        .setDescription("You're standing on the floor in a dark hidey hole, across from you is an old sage rat");
    hideyHole.buildFurnitureList(hideyHole.getFloor(), ratRoomDoor, testingRoomDoor, breakRoomDoor);
    hideyHoleGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(hideyHole.getFloor(), ratRoomDoor)
        .putEdge(hideyHole.getFloor(), testingRoomDoor).putEdge(hideyHole.getFloor(), breakRoomDoor)
        .putEdge(hideyHole.getFloor(), oldRat).build();
    hideyHole.setRoomGraph(hideyHoleGraph);

    // rat room
    String[] ratChat = { "\"Looks like the scientists gave the keys to our cages to that kid over there\"",
        "\"Maybe if you had a translator you could talk to the kid\"", "\"Please free us!\"" };
    Furniture manyRats = new Furniture("rats", "You see hundreds of rats living in cages. Maybe try talking to them",
        ratChat);
    Furniture ratCages = new Furniture("rat cages", "Inside the cages are hundreds of rats. Maybe go talk to the rats");
    String[] kidChat = { "\"goo gee\"", "\"bahahnshshgd\"", "\"abababbadodo\"" };
    Furniture kid = new Furniture("kid",
        "The kid is young and carrying keys. On the wall next to him hangs a rope, and (very oddly) a small rat sized sword. Maybe he's odd, but he seems nice, I would try talking to him.",
        kidChat);
    Item rope = new Item("rope", false, "A sturdy climbing rope");
    Item sword = new Item("sword", false, "A rat-sized sword. It looks dangerous");

    this.ratRoom = new Room("rat room",
        "You are in the rat room, so-called because of the hundreds of rat cages stacked against the far wall. On the other side of the cages there is a kid sitting in a chair. You feel akin to the caged rats because you were once one of them.  You think to youself, 'I wonder if I can try to save them...''");
    ratRoom.getFloor().setDescription(
        "From the floor of the Rat Room you can go to the kid, the testing room door, the storage closet door, and the rat cages");
    ratRoom.buildNPCList(manyRats, kid);
    ratRoom.buildItemList(rope, sword);
    ratRoom.buildFurnitureList(ratRoom.getFloor(), ratCages, storageClosetDoor, testingRoomDoor);

    ratRoomGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(storageClosetDoor, ratCages)
        .putEdge(testingRoomDoor, ratCages).putEdge(ratCages, kid).putEdge(storageClosetDoor, kid)
        .putEdge(testingRoomDoor, kid).putEdge(storageClosetDoor, ratRoom.getFloor())
        .putEdge(ratCages, ratRoom.getFloor()).putEdge(kid, ratRoom.getFloor())
        .putEdge(ratRoom.getFloor(), testingRoomDoor).putEdge(ratCages, manyRats).build();
    ratRoom.setRoomGraph(ratRoomGraph);

    // roof
    Furniture roofEdge = new Furniture("roof ledge", "the rooftop edge");
    this.roof = new Room("roof",
        "You have reached the roof. Looking around, the walls are too slippery to climb down and it's too high to jump. Maybe if you had a rope it would be easier to escape.");
    roof.buildNPCList();
    roof.buildItemList();
    roof.buildFurnitureList(roof.getFloor(), roofEdge);
    roofGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(roof.getFloor(), roofEdge)
        .putEdge(roof.getFloor(), storageClosetDoor).putEdge(roofEdge, outsideDoor).build();
    roof.setRoomGraph(roofGraph);

    // storage closet
    Furniture shelf1 = new Furniture("shelf 1", "The bottom shelf has some cleaning supplies on it.");
    Furniture shelf2 = new Furniture("shelf 2", "The second shelf has some jars of rat food");
    Furniture shelf3 = new Furniture("shelf 3", "The third shelf has an item labeled \"Translator\" ");
    Furniture shelf4 = new Furniture("shelf 4",
        "The fourth shelf is empty, but there is a hatch leading to the roof above it");
    Furniture lamp = new Furniture("lamp", "A lamp in the corner of the closet");

    Item cleaningSupplies = new Item("supplies", true, "There is cleaning supplies sitting on the shelf");
    Item ratTranslator = new Item("translator", false, "A machine labeled rat translator");
    Item ratFood = new Item("food", true, "Yummy rat food, good to eat");

    this.storageCloset = new Room("storage closet",
        "You enter the storage closet. There is a wall of 4 vertical shelves in front of you. At the top of the shelves is a metal hatch");
    storageCloset.getFloor()
        .setDescription("On the floor of the storage closet you can reach shelf 1, the lamp, and the rat room door");
    storageCloset.buildItemList(cleaningSupplies, ratTranslator, ratFood);
    storageCloset.buildFurnitureList(storageCloset.getFloor(), shelf1, shelf2, shelf3, shelf4, ratRoomDoor, roofDoor,
        lamp);
    storageClosetGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(shelf1, shelf2)
        .putEdge(shelf2, shelf3).putEdge(shelf3, shelf4).putEdge(storageCloset.getFloor(), ratRoomDoor)
        .putEdge(shelf4, roofDoor).putEdge(storageCloset.getFloor(), shelf1).putEdge(storageCloset.getFloor(), lamp)
        .putEdge(lamp, shelf1).build();
    storageCloset.setRoomGraph(storageClosetGraph);

    // outside
    this.outside = new Room("outside", "You are outside! You have won the game!!");
    outside.buildFurnitureList(outside.getFloor(), breakRoomDoor);
    outsideGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(breakRoomDoor, outside.getFloor())
        .putEdge(roofEdge, outside.getFloor()).build();
    outside.setRoomGraph(outsideGraph);

    // testing room
    Furniture cage = new Furniture("cage", "You are in a cage with a loose bar");
    Furniture table = new Furniture("table",
        "a table you can climb on, behind you is the cage you escaped from and below you is a maze you can jump into.");
    Furniture maze = new Furniture("maze",
        "You solve the maze easily (you're a very smart rat) and find some food at the end. You can try to pick it up, or you can jump on to the floor.");

    this.testingRoom = new Room("testing room",
        "You enter the testing room. You see the open cage you escaped from and a hideyhole to its left. Below the cage is a table leading to a maze with food at the end.");
    testingRoom.getFloor().setDescription(
        "From the floor you can reach the maze, the rat room door, the chemistry room door, and the hideyhole door");

    // build smaller graph of furniture in testing room
    testingRoomGraph = GraphBuilder.undirected().<Furniture>immutable().putEdge(cage, table).putEdge(table, maze)
        .putEdge(maze, testingRoom.getFloor()).putEdge(table, testingRoom.getFloor())
        .putEdge(testingRoom.getFloor(), hideyHoleDoor).putEdge(testingRoom.getFloor(), ratRoomDoor)
        .putEdge(table, maze).putEdge(testingRoom.getFloor(), chemistryRoomDoor).build();

    testingRoom.buildItemList(ratFood);
    testingRoom.buildFurnitureList(testingRoom.getFloor(), cage, table, maze, ratRoomDoor, hideyHoleDoor,
        chemistryRoomDoor);
    testingRoom.setRoomGraph(testingRoomGraph);
  }

  /* method to build large graph */
  public void makeGraph() {
    // build big graph of all the rooms
    roomGraph = GraphBuilder.undirected().<Room>immutable().putEdge(this.testingRoom, this.hideyHole)
        .putEdge(this.testingRoom, this.ratRoom).putEdge(this.testingRoom, this.chemistryRoom)
        .putEdge(this.ratRoom, this.storageCloset).putEdge(this.ratRoom, this.chemistryRoom)
        .putEdge(this.chemistryRoom, this.breakRoom).putEdge(this.chemistryRoom, this.hideyHole)
        .putEdge(this.storageCloset, this.roof)
        .putEdge(this.hideyHole, this.ratRoom).putEdge(this.hideyHole, this.breakRoom)
        .putEdge(this.outside, this.breakRoom).putEdge(this.outside, this.roof)
        .build();
  }

  /*
   * method to get entire room graph
   * 
   * @return room graph
   */
  public ImmutableGraph<Room> getGraph() {
    return this.roomGraph;
  }

  /*
   * method to get testing room
   * 
   * @return testing room
   */
  public Room getTestingRoom() {
    return this.testingRoom;
  }

  /*
   * method to get chemistry room
   * 
   * @return chemistry room
   */
  public Room getChemistryRoom() {
    return this.chemistryRoom;
  }

  /*
   * method to get break room
   * 
   * @return break room
   */
  public Room getBreakRoom() {
    return this.breakRoom;
  }

  /*
   * method to get hidey hole
   * 
   * @return hidey hole
   */
  public Room getHideyHole() {
    return this.hideyHole;
  }

  /*
   * method to get storage closet
   * 
   * @return storage closet
   */
  public Room getStorageCloset() {
    return this.testingRoom;
  }

  /*
   * method to get rat room
   * 
   * @return rat room
   */
  public Room getRatRoom() {
    return this.ratRoom;
  }

  /*
   * method to get roof
   * 
   * @return roof
   */
  public Room getRoof() {
    return this.roof;
  }

  /*
   * method to get outside
   * 
   * @return outside
   */
  public Room getOutside() {
    return this.outside;
  }

  /*
   * method to get testing room furniture graph
   * 
   * @return testing room furniture graph
   */
  public ImmutableGraph<Furniture> getTestingRoomGraph() {
    return this.testingRoomGraph;
  }

  /*
   * method to get chemistry room furniture graph
   * 
   * @return chemistry room furniture graph
   */
  public ImmutableGraph<Furniture> getChemistryRoomGraph() {
    return this.chemistryRoomGraph;
  }

  /*
   * method to get break room furniture graph
   * 
   * @return break room furniture graph
   */
  public ImmutableGraph<Furniture> getBreakRoomGraph() {
    return this.breakRoomGraph;
  }

  /*
   * method to get hidey hole furniture graph
   * 
   * @return hidey hole furniture graph
   */
  public ImmutableGraph<Furniture> getHideyHoleGraph() {
    return this.hideyHoleGraph;
  }

  /*
   * method to get storage closet furniture graph
   * 
   * @return storage closet furniture graph
   */
  public ImmutableGraph<Furniture> getStorageClosetGraph() {
    return this.testingRoomGraph;
  }

  /*
   * method to get rat room furniture graph
   * 
   * @return rat room furniture graph
   */
  public ImmutableGraph<Furniture> getRatRoomGraph() {
    return this.ratRoomGraph;
  }

}