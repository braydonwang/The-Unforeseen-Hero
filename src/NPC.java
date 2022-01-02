/*
 * [NPC.java]
 * Version 1.0
 * This program represents the NPCs in the game
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* NPC
* This class holds the variables and methods for each NPC.
*/

class NPC extends Interactable{
  private String name;
  
  NPC(String name) {
    this.name = name;
  }
  
  /**
   * getName
   * This method returns the current name of the NPC
   * @return a String variable that represents the name
   */
  
  public String getName() {
    return this.name;
  }
}