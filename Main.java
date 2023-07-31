import com.google.common.graph.*;
import com.google.common.io.PatternFilenameFilter;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *  Class to run main method
 *  @author Allison Reel, Laura Dreher, Joey Elsbernd 
 *  @version Spring 2022
 */

class Main {
  /* main method to run game */
  public static void main(String[] args) {
    //builds new instance of graph to run game
    BuildGame graph = new BuildGame();
    runGame(graph);
  }

  /* run game method to run game */
  public static void runGame(BuildGame graph) {
    //game is not finished
    boolean finish = false;
    boolean answeredRiddle = false;
    //build furniture graph
    ImmutableGraph<Furniture> testingRoomGraph = graph.getTestingRoomGraph();
    //build entire room graph
    ImmutableGraph<Room> bigG = graph.getGraph();
    //get beginning room
    Room testingRoom = graph.getTestingRoom();
    //get beginning furniture
    Furniture newPlace = graph.getTestingRoom().getFurniture().get(1);
    // beat scientist
    boolean beatScientist = false;

    //create main character rat instance
    Rat reny = new Rat(testingRoom, newPlace);
    //scanner
    Scanner keyboard = new Scanner(System.in);
    System.out.println(
        "You are a rat sitting in a cage that you have never seen before. But one of the bars is loose!! You want to escape this animal testing hellhole. Using all of your rat strength you pry the bar back to create a small opening. Freedom!! But what will you do with it?");

    System.out.println("You are in a room with a mysterious hideyhole, a large door to a room filled with rats, and the cage you just exited. There is a table below you, and a maze underneath the table.");
    String userYN = "";

    //set starting room as testing room
    Room currentRoom = testingRoom;
    //while game is not finished
    while (!finish) {
      // checking restrictions
      //givning old rat his pain meds for satchel
      if ((reny.handsIncludes("meds") || reny.bagIncludes("meds")) && newPlace.getName().equals("old rat")) {
        reny.switchToBag();
        System.out.println("Congrats! You have acquired the satchel! You can now hold unlimited items.");
        String[] oldRatChat2 = {"\"My back feels so much better!\"", "\"Try to escape and free the other rats\"", "\"use your satchel to hold items you need to free yourself and your comrades\""};
        newPlace.setDialogue(oldRatChat2);
      //finish game if leaving roof with rope
      } else if (newPlace.getName().equals("roof ledge")) {
        if (reny.inventoryBag().contains("rope") || reny.inventoryHands().contains("rope")) {
          System.out.println("You climb down to freedom!");
          System.out.println("Yayy!!! You Win!");
          finish = true;
          break;
        //finish game by jumping off roof
        } else if (!reny.inventoryBag().contains("rope") || !reny.inventoryHands().contains("rope")) {
          System.out.println("you'd probably fall if you tried to get down from here, would you like to try anyways?");
          userYN = keyboard.nextLine();
          if (userYN.equals("yes") || userYN.equals("Yes")) {
            System.out.println("oh no! you died :(");
            finish = true;
            break;
          } else {
            newPlace = currentRoom.getFloor();
            continue;
          }
        }
      //free the other rats
      } else if (newPlace.getName().equals("rat cages") && reny.handsIncludes("keys") || reny.bagIncludes("keys")) {
        System.out.println("You have freed all the rats from the cages with your keys!  Will you all be able to escape?");
          
      //fight mean scientist
      } else if (newPlace.getName().equals("mean scientist") && !beatScientist) {
        if (reny.handsIncludes("sword")  || reny.bagIncludes("sword")) {
          Item keycard = new Item("keycard", false, "a keycard to the outside");
          System.out.println("You defeat the scientist with your sword! He dropped a keycard in the carnage, and you pick it up.");
          beatScientist = true;
          String[] meanScientistChat2 = {"\"oooffff\"", "\"ugghhh\"", "\"wwuuuhhh\""};
          newPlace.setDialogue(meanScientistChat2);
          newPlace.setDescription("The mean scientist is sprawled on the floor, stunned by your strength");
            
          if (reny.getHasBag()) {
            reny.addToBag(keycard);
          } else if (!reny.handsFull()) {
            reny.addToHands(keycard);
          } else {
            currentRoom.addToItemList(keycard);
            System.out.println("Your hands are full. Come back later to pick it up.");
          }
          
        } else if(reny.getCanCook()){
          Item keycard = new Item("keycard", false, "a keycard to the outside");
          if(reny.handsIncludes("food") || reny.bagIncludes("food")){
            System.out.println("This scientist looks pretty mean, but you feel an urge that if you wowed him with your cooking skills, he'd let you off easy. Using the food you found, you quickly put together a masterpiece of flavor, and present it to the scientist.");
            beatScientist = true;
            
            if(reny.handsIncludes("translator")
            || reny.bagIncludes("translator")){
              System.out.println("\"My potion works! This rat can cook!\"");
              }
            
            System.out.println("The scientist is so excited he drops his keycard. You pick it up.");
            String[] meanScientistChat2 = {"\"wow!!\"", "\"this is so delicious!!\"", "\"I've never had food this good!!\""};
            newPlace.setDialogue(meanScientistChat2);
            newPlace.setDescription("The mean scientist is stunned by your cooking skills");
              
            if (reny.getHasBag()) {
              reny.addToBag(keycard);
            } else if (!reny.handsFull()) {
              reny.addToHands(keycard);
            } else {
              currentRoom.addToItemList(keycard);
              System.out.println("Your hands are full. Come back later to pick it up.");
            }
            Item dropFood = new Item();
            if(reny.getHasBag()){
              dropFood = reny.getItemBag("food");
            } else{
              dropFood = reny.getItemHands("food");
            }
            
            reny.dropItem(dropFood, currentRoom);
              
          } else{
            System.out.println("This scientist looks pretty mean. But maybe if you found some food and made him a nice meal with your new cooking skills...");
          }
        } else {
          System.out.println("The scientist lunges at you, trying to catch you. Quick!! Go somewhere else!");
        }
        

        
      //open the cabinet
      } else if (newPlace.getName().equals("cabinet")) {
        System.out.println("Enter the keycode to get inside");
        userYN = keyboard.nextLine();
        if (userYN.equals("3345")) {
          Item painMeds = new Item("meds", true, "Pain meds for the old rat");
          System.out.println("The locked cabinet opens. Some pain meds are inside");
          if (reny.getHasBag()) {
            reny.addToBag(painMeds);
            System.out.println("The pain meds are added to your inventory");
          } else if (!reny.handsFull()) {
            reny.addToHands(painMeds);
            System.out.println("The pain meds are added to your inventory");

          } else {
            currentRoom.addToItemList(painMeds);
            System.out.println("Your hands are full. Come back later to pick it up");
          }

        } else {
          System.out.println("That's not the right code");
        }
      }
      //talk to kid for cage keys
      else if (newPlace.getName().equals("kid") && !answeredRiddle) {
        if (reny.handsIncludes("translator") || reny.bagIncludes("translator") && (!reny.bagIncludes("keys") || !reny.handsIncludes("keys"))) {
          System.out.println("Omg a rat! If you guess my favorite animal I can give you a key");
          String[] kidChat = {"\"Good luck on your journey\""};
          newPlace.setDialogue(kidChat);
          if (animalGuess(keyboard)) {
            answeredRiddle = true;
            System.out.println("The kid hands you the keys");
            Item cageKeys = new Item ("keys", false, "The kid's keys look like they fit perfectly into all the rat cages");
            if (reny.getHasBag()) {
            reny.addToBag(cageKeys);
            System.out.println("The keys are added to your inventory");
          } else if (!reny.handsFull()) {
            reny.addToHands(cageKeys);
            System.out.println("The keys are added to your inventory");

          } else {
            currentRoom.addToItemList(cageKeys);
            System.out.println("Your hands are full. If you drop something you can pick them up");
            }
          }
        } 
      }

      String userAnswer = keyboard.nextLine();
      //space to clear up console
      System.out.println();

      // pick things up
      if (userAnswer.contains("pick") || userAnswer.contains("take")) {
        int itemIndex = userAnswer.lastIndexOf(" ");
        String itemName = userAnswer.substring(itemIndex);
        Item item = graph.pickupItem(itemName, currentRoom, reny);
        if (item == null) {
          System.out.println("You can't do that. Maybe try another command");
          continue;
        }
        System.out.println(item.getDescription());

        // eat items
      } else if (userAnswer.contains("eat")) {
        int itemIndex = userAnswer.lastIndexOf(" ");
        String itemName = userAnswer.substring(itemIndex + 1);
        System.out.println(itemName);
        if (reny.bagIncludes(itemName) || reny.handsIncludes(itemName)) {
          Item edibleItem = new Item();
          if (reny.getHasBag()) {
            edibleItem = reny.getItemBag(itemName);
          } else {
            edibleItem = reny.getItemHands(itemName);
          }
          // if item is edible
          if (edibleItem.getEdible()) {
            System.out.println("You ate the " + itemName);
            //eating the potion
            if (itemName.contains("potion")) {
              System.out.println("Wow! What a rush! You now have a strange urge to cook...");
              reny.canCook();
              
            //eating the meds
            } else if (itemName.contains("meds")) {
              System.out.println("Those were for the old rat! You probably won't get anything from them now...");
            //eating rat or human food
            } else if (itemName.contains("food")) {
              System.out.println("Mmm yummy!");
            //eating cleaning supplies
            } else if (itemName.contains("supplies")) {
              System.out.println("oh no! those cleaning supplies were toxic, you died :(");
              finish = true;
              }
            
            reny.dropItem(edibleItem, currentRoom);
            
            // if item is not edible
          } else {
            System.out.println("that item is not edible");
            continue;
          }
          // they cannot try to eat an item they dont have
        } else {
          System.out.println("You dont have that item! Pick it up before you try to eat it.");
          continue;
        }
        // check inventory
      } else if (userAnswer.contains("inventory")) {
        if (reny.getHasBag()) {
          System.out.println("Your bag inventory is: " + reny.inventoryBag());
        } else {
          System.out.println("Your hand inventory is: " + reny.inventoryHands());
        }
        //space to clear up console
        System.out.println();
      //help command
      } else if (userAnswer.contains("help")){
        System.out.println("You are in: " + currentRoom.getName());
        System.out.println(
          "You can reach: " + graph.reachable(newPlace, currentRoom.getRoomGraph()));
      if (currentRoom.getItemList() != null) {
        System.out.println("You could try to pick up: " + currentRoom.getItemList());
        }
        if (reny.getHasBag()) {
          System.out.println("Your bag inventory is: " + reny.inventoryBag());
        } else {
          System.out.println("Your hand inventory is: " + reny.inventoryHands());
        }
        System.out.println("You can also choose to talk to someone, drop, or eat one of your items.");
        //space to clear up console
        System.out.println();
        // drop items
      } else if (userAnswer.contains("drop")) {
        int itemIndex = userAnswer.lastIndexOf(" ");
        if (itemIndex == -1) {
          System.out.println("You can't remove that");
          continue;
        }
        String itemName = userAnswer.substring(itemIndex);
        Item toRemove = new Item();
        if (reny.getHasBag()) {
          toRemove = reny.getItemBag(itemName);
        } else {
          toRemove = reny.getItemHands(itemName);
        }
        if (toRemove!= null) {
          reny.dropItem(toRemove, currentRoom);
        }
        else {
          System.out.println("You can't remove that");
          continue;
        }
        
        
        // talk to NPCs
      } else if (userAnswer.contains("talk") || userAnswer.contains("speak")) {
        userAnswer = userAnswer.toLowerCase();
        for (Furniture NPC : currentRoom.getNPCs()){
          if(userAnswer.contains(NPC.getName())){
            System.out.println(NPC.talk());
           }
         }
      }
      // go to new place
      else {
        // this holds the position in case new place returns null
        Furniture oldPlace = newPlace;
        newPlace = graph.goToFurniture(newPlace, userAnswer, currentRoom.getRoomGraph());
        // error handling if new place returns null. restarts while loop
        if (newPlace == null) {
          System.out.println("You can't do that. Maybe try another command");
          newPlace = oldPlace;
          continue;
        }
        // prints out description of new furniture
        System.out.println(newPlace.getDescription());
        //space to clear up console
      System.out.println();

        if (newPlace.getName().contains("door")) {
          // pull out everything that is not the door
          int index = newPlace.getName().lastIndexOf(" ");
          String placeName = newPlace.getName().substring(0, index);
          placeName = placeName.toLowerCase();

          if (placeName.equals("outside") && currentRoom.getName().equals("breakroom")) {
            if (reny.getHasBag()) {
              if (!reny.bagIncludes("keycard")){
              System.out.println("Oh no! The door is locked, maybe if you had a keycard? \n");
            continue;
              } else {
                System.out.println("the outside! congrats");
                finish=true;
              }
            } else {
              if (!reny.handsIncludes("keycard")){
              System.out.println("Oh no! The door is locked, maybe if you had a keycard? \n");
            continue;
              } else {
                System.out.println("the outside! congrats");
                finish=true;
              }
            }
            
          } else {
            System.out.println("Do you want to go to " + placeName + "?");
            userYN = keyboard.nextLine();
            System.out.println();
            userYN = userYN.toLowerCase();
            if (userYN.equals("y") || userYN.equals("yes") ||userYN.equals("yeah")) {
              currentRoom = graph.changeRoom(placeName, currentRoom);
              newPlace = currentRoom.getFurniture().get(0);
              // make sure arrays are in the right order
              System.out.println(currentRoom.getDescription());
              //space to clear up console
      System.out.println();
            }   
            else {
              System.out.println("Ok you can stay in the same room.");
              newPlace = currentRoom.getFloor();
            }
          } 
        }
      }
      // ends the game
      if (currentRoom.getName().equals("outside")) {
        finish = true;
        //closes program
        System.exit(0);
        break;
      }
    }
    
    
  }

  /** method to run guessing game when talking to kid */
  public static boolean animalGuess(Scanner keyboard) {
    System.out.println("Here's a hint:");
    System.out.println("I'm an insect but I'm not a cockroach\nI feed on nectar but I'm not a bee\nI'm colorful but I'm not a bee\nI flutter but I'm not a humming bird");

    System.out.println("You have 5 guesses!");
    String guess = "";
    for (int i = 0; i < 5; i++) {
      guess = keyboard.nextLine();
      if (guess.contains("butterfly")) {
        System.out.println("You guessed it!");
        return true;
      }
      else {
        System.out.println("That's not the answer");
      }
    }
    System.out.println("You didn't get the answer. Come back later to try again");
    return false;
  }

}