import java.util.Random;
/**
 *  Class to build furniture items
 *  extends item class
 *  @author Allison Reel, Laura Dreher, Joey Elsbernd 
 *  @version Spring 2022
 */

public class Furniture extends Item {
  
  private String description;
  private String name;
  private String[] dialogue = {};

  /**constructor for furniture*/
  public Furniture(String name, String description){
    super(name, false, description);
  }

  /**constructor for npcs*/
  public Furniture(String name, String description, String[] dialogue){
    super(name, false, description);
    this.dialogue = dialogue;
  }

  /**constructor for furniture*/
  public Furniture() {
    super (null, false, null);
  }

  /** 
  * setter for dialogue
  * @param string array
  */
  public void setDialogue(String[] newDialogue) {
    this.dialogue = newDialogue;
  } 

  /**
  * method to talk to npc
  * @return random stock phrase string
  */
  public String talk(){
    Random rand = new Random();
    double random = rand.nextDouble();
      
    if(this.dialogue.length == 1){
      return this.dialogue[0];
    }
    
  
    else{
      if(random < .33){
      return this.dialogue[0];
      }
      else if(random < .66){
        return this.dialogue[1];
      }
      else{
        return this.dialogue[2];
      }
    }
    
  }

  
}