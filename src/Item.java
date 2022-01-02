/*
 * [Item.java]
 * Version 1.0
 * This program represents the items in the game
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Item
* This class holds all the necessary functions needed for items, including weapons, consumables and armour
*/

class Item {
  private int id;
  private int cost;
  
  Item(int id, int cost) {
    this.id = id;
    this.cost = cost;
  }
  
  /**
   * getId
   * This method returns the current id number of the item that helps determine what the item is
   * @return an integer value that represents the current id number
   */
  
  public int getId() {
    return this.id;
  }
  
  /**
   * getCost
   * This method returns the current cost of the item
   * @return an integer value that represents the current cost
   */
  
  public int getCost() {
    return this.cost;
  }
}