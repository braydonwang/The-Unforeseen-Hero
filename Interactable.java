/*
 * [Interactable.java]
 * Version 1.0
 * This program represents the interactable items in the game.
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Interactable
* This class holds all the necessary functions needed for interactable items, including chests and NPCs
*/

abstract class Interactable {
  //Initialize all variables
  private boolean interacted;
  private String message;
  
  Interactable() {
    this.interacted = false;
  }
  
  /**
   * displayMessage
   * This method returns the current message
   * @return a String variable, the message
   */
  
  public String displayMessage() {
    return this.message;
  }
  
  /**
   * getInteracted
   * This method returns boolean value if the object has been interacted or not
   * @return an boolean value that represents if interacted with
   */
  
  public boolean getInteracted() {
    return this.interacted;
  }
  
  /**
   * changeInteracted
   * This method accepts a new boolean value for interacted and changes the old one
   * @param interacted, the new interacted value
   */
  
  public void changeInteracted(boolean interacted) {
    this.interacted = interacted;
  }
  
  /**
   * changeMessage
   * This method accepts the new message and changes the old one
   * @param message, the new message
   */
  
  public void changeMessage(String message) {
    this.message = message;
  }
}