
/**
 *  Class to create carryable items
 *  @author Allison Reel, Laura Dreher, Joey Elsbernd 
 *  @version Spring 2022
 */
public class Item {
  private String name;
  private boolean edible;
  private String description;

  /* constructor */
  public Item(String name, boolean edible, String description) {
    this.name = name;
    this.edible = edible;
    this.description = description;
  }

  /*constructor */
  public Item() {
    this.name = null;
    this.description = null;
  }


  /* getter for description
   * @return string description
  */
  public String getDescription() {
    return this.description;
  }

  /* setter for description
   * @param string description
  */
  public void setDescription(String description) {
    this.description = description;
  }

  /* getter for name
   * @return string name
  */
  public String getName() {
    return this.name;
  }

  /* to string for printing
   * @return string name
  */
  public String toString() {
    return this.name;
  }

  /* to check if item is edible
  * @return boolean edible
  */
 public boolean getEdible() {
    return this.edible;
  }
  
}
