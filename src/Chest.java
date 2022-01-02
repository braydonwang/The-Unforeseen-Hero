/*
 * [Chest.java]
 * Version 1.0
 * This program represents chest box that can be opened by the player.
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Chest
* This class holds all the necessary functions needed for the chest boss including the items inside and 
* the loot given when opened.
*/

class Chest extends Interactable {
  //Initialize all variables
  private int coins;
  private Item item;
  
  Chest() {
    this.coins = (int)(Math.random()*20) + 5;
    int randNum = (int)(Math.random()*4);
    if (randNum == 2) {
      item = new Item(10,75);
      this.changeMessage("You recieved " + this.coins + " coins and a health pot!");
    } else if (randNum == 3) {
      item = new Item(11,50);
      this.changeMessage("You recieved " + this.coins + " coins and a mana pot!");
    } else {
      item = null;
      this.changeMessage("You recieved " + this.coins + " coins!");
    }
  }
  
  /**
   * getCoins
   * This method returns the current number of coins the chest contains
   * @return an int value that represents the number of coins
   */
  
  public int getCoins() {
    return this.coins;
  }
  
  /**
   * getItem
   * This method returns the current item that the chest contains
   * @return an Item variable that represents the current item that the chest holds
   */
  
  public Item getItem() {
    return this.item;
  }
}