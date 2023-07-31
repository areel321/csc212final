import java.util.ArrayList;
import com.google.common.graph.*;

/**
 *  Class to create rooms
 *  @author Allison Reel, Laura Dreher, Joey Elsbernd 
 *  @version Spring 2022
 */
public class Room {
  
  private String name;
  private String description;
  private ArrayList<Item> items = new ArrayList<Item>();
  private ArrayList<Furniture> furniture = new ArrayList<Furniture>();
  private ArrayList<Furniture> npcs = new ArrayList<Furniture>();
  private Furniture floor = new Furniture("floor", "the floor, from here you can reach all the doors and furniture in the room");
  private ImmutableGraph<Furniture> roomGraph =  null;
  

  /**constructor for room*/
  public Room(String name, String description) {
    this.description = description;
    this.name = name;
  }
  /** constructor for empty room*/
  public Room() {
    this.description = null;
    this.name = null;
  }

  /** getter for name
  * @return string name
  */
  public String getName() {
    return this.name;
  }

  /** getter for room furniture graph
  * @return room furniture graph
  */
  public ImmutableGraph<Furniture> getRoomGraph() {
    return this.roomGraph;
  }

  /** setter for room furniture graph
  * @param room furiture graph
  */
  public void setRoomGraph(ImmutableGraph<Furniture> roomGraph) {
    this.roomGraph = roomGraph;
  }

  /** getter for npcs
  * @return arraylist of npcs
  */
  public ArrayList<Furniture> getNPCs(){
    return this.npcs;
  }

  /** getter for item list printing
  * @return string of items
  */
  public String getItemList() {
    String itemString = "";
    for (int i = 0; i < this.items.size(); i++) {
      itemString += items.get(i).getName() + ", ";
    }
    return itemString;
  }

  /** getter for item list
  * @return arraylist of items 
  */
  public ArrayList<Item> getItems() {
    return this.items;
  }

  /** to string method for printing
  * @return string name
  */
  public String toString() {
    return this.name;
  }

  /** getter for description
  * @return string description
  */
  public String getDescription() {
    return this.description;
  }

  /** getter for string list of furniture
  * @return string of furniture
  */
  public String getFurnitureString() {
    String furnitureString = "";
    for (int i = 0; i < this.furniture.size(); i++) {
      furnitureString += furniture.get(i).getDescription() + ", ";
    }
    return furnitureString;
  }

  /** getter for furniture list
  * @return arraylist furniture
  */
  public ArrayList<Furniture> getFurniture(){
    return this.furniture;
  }

  /** getter for floor
  * @return furniture floor
  */
  public Furniture getFloor() {
    return this.floor;
  }

  /** constructs item list for room
  * @param items to be added to list
  */
  public void buildItemList(Item... optionalItems){
    for (int i = 0; i < optionalItems.length; i++) {
      this.items.add(optionalItems[i]);
    }
  }
  public void addToItemList(Item item) {
    this.items.add(item);
  }

  /** constructs furniture list for room
  * @param furniture to be added to list
  */
  public void buildFurnitureList(Furniture... optionalFurniture) {
    for (int i = 0; i < optionalFurniture.length; i++) {
      this.furniture.add(optionalFurniture[i]);
    }
  }
  

  /** constructs npc list for room
  * @param npcs to be added to list
  */
  public void buildNPCList(Furniture... optionalNPC) {
    for (int i = 0; i < optionalNPC.length; i++) {
      this.npcs.add(optionalNPC[i]);
    }
  }
  
}