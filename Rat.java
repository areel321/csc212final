import java.util.ArrayList;
/**
 *  Class to create rat character
 *  @author Allison Reel, Laura Dreher, Joey Elsbernd 
 *  @version Spring 2022
 */
public class Rat {
  private boolean hasBag = false;
  private Item[] hands = new Item[2];
  private ArrayList<Item> bag = new ArrayList<>();
  private Room currentRoom;
  private Furniture currentFurniture;
  private boolean cook = false;

  /** constructor for rat*/
  public Rat(Room currentRoom, Furniture currentFurniture) {
    this.currentRoom = currentRoom;
    this.currentFurniture = currentFurniture;
  }


  /** setter for room
  * @param new room
  */
  public void setCurrentRoom(Room newRoom) {
    this.currentRoom = newRoom;
  }

  /** getter for room
  * @return current room
  */
  public Room getCurrentRoom() {
    return this.currentRoom;
  }

  /** setter for furniture
  * @param new furniture
  */
  public void setCurrentFurniture(Furniture newFurniture) {
    this.currentFurniture = newFurniture;
  }

  /** getter for hand inventory
  * @return string of hand inventory
  */
  public String inventoryHands() {
    return hands[0] + " and " + hands[1];
  } 

  /** getter for bag inventory
  * @return string of bag inventory
  */
  public String inventoryBag() {
    String str = "";
    for (Item item : bag) {
      str += item.getName() + ", "; 
    }
    return str;
  }

  /** check if rat has bag
  * @return boolean hasBag
  */
  public boolean getHasBag() {
    return this.hasBag;
  }

  /** checks if hands are full
  * @return boolean 
  */
  public boolean handsFull() {
    if (this.hands[0] != null && this.hands[1] != null) {
      return true;
    }
    else {
      return false;
    }
  }

  /** method to add item to hands
  * @param item to add
  */
  public void addToHands(Item item) {
    //System.out.println("test test");
    if (this.hands[0] == null) {
      this.hands[0]= item;
    }
    else if (this.hands[1] == null) {
      this.hands[1] =  item;
    }
  }

  /** method to switch items from hands to bag
   * @return arrayList bag
  */
  public ArrayList<Item> switchToBag(){
    this.hasBag = true;
    if (this.hands[0] != null && !this.hands[0].getName().equals("meds")) {
      this.bag.add(hands[0]);
    }
    else if (this.hands[1] != null && !this.hands[1].getName().equals("meds")) {
      this.bag.add(hands[1]);
    }
    this.hands[0]= null;
    this.hands[1] = null;
    return bag;
  }

  /** method to check if item is in bag
  * @param string of item name
  * @return boolean
  */
  public boolean bagIncludes(String item){
    if(this.inventoryBag().contains(item)){
      return true;
    }
    else{
      return false;
    }
  }

  /** method to check if item is in hands
  * @param string of item name
  * @return boolean
  */
  public boolean handsIncludes(String item) {
    if (this.inventoryHands().contains(item)) {
      return true;
    }
    else {
      return false;
    }
  } 

  
 /** method to add item to bag
  * @param item to add
  */
  public void addToBag(Item item) {
    this.bag.add(item);
  }

  /** method to get item in bag
  * @param string of item name
  * @return item
  */
  public Item getItemBag(String str) {
    str = str.toLowerCase();
    for (Item item : this.bag) {
      String itemName = item.getName().toLowerCase();
      if (str.contains(itemName)) {
        return item;
      }
    }
    return null;
  }

  /** method to get item in hands
  * @param string of item name
  * @return item
  */
  public Item getItemHands(String str) {
    str = str.toLowerCase();
    for (int i=0; i<2; i++) {
      Item item = hands[i];
      if (item == null) {
        continue;
      }
      String itemName = item.getName().toLowerCase();
      if (item != null && str.contains(itemName)) {
        return item;
      }
    }
    return null;
  }

  /** method to drop item
  * @param item to be dropped, current room
  */
  public void dropItem(Item item, Room currentRoom){
    if (this.getHasBag()){
      bag.remove(item);
    } else {
      for (int i=0; i<2; i++) {
      Item toRemove = hands[i];
    if (toRemove != null && toRemove.getName().contains(item.getName())) {
        hands[i] = null;
        }
      }
    }
    currentRoom.addToItemList(item);
  }

  /** method to make reny cook
  */
  public void canCook(){
    this.cook = true;
  }

  /** method to check if rat can cook
  * @return boolean can cook
  */
  public boolean getCanCook(){
    return this.cook;
  }
  
}
  