//Importing all files
import java.awt.*;

/*
 * [Body.java]
 * Version 1.0
 * This program represents all objects of the game that can move and attack, including the player and the monsters.
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Body
* This abstract class contains all the necessary functions for the player and the monsters, including attacking, moving, 
* changing health, and returning all the crucial variables.
* @param hp, An int variable that represents the health of the object
* @param row, An int variable that represents the row number the object is on
* @param col, An int variable that represents the column number the object is on
* @param attackCD, An int variable that represents the duration of the object's delay before attacking again
* @param coins, An int variable that represents the number of coins the object initially has
* @param name, A string variable that holds the name of the object
* @param xCoordinate, An int variable that represents the x-coordinate of the object
* @param yCoordinate, An int variable that represents the y-coordinate of the object
*/

abstract class Body implements Moveable{
  //Initializing all variables
  private int hp;
  private int row;
  private int col;
  private double attackCD;
  private int coins;
  private int direction;
  private int currentSprite;
  private String name;
  private boolean inMotion;
  private boolean inAttack;
  int xCoordinate;
  int yCoordinate;
  private int dirCount;
  
  Body(int hp, int row, int col, int attackCD, int coins, String name, int xCoordinate, int yCoordinate) {
    this.hp = hp;
    this.row = row;
    this.col = col;
    this.attackCD = attackCD;
    this.coins = coins;
    this.currentSprite = 1;
    this.name = name;
    this.direction = 3;
    this.inMotion = false;
    this.xCoordinate = xCoordinate;
    this.yCoordinate = yCoordinate;
    this.dirCount = 0;
    this.inAttack = false;
  }
  
  /**
   * getRow
   * This method returns the row number that the object is on
   * @return an integer value that represents the row number
   */
  
  public int getRow() {
    return this.row;
  }
  
  /**
   * getCol
   * This method returns the column number that the object is on
   * @return an integer value that represents the column number
   */
  
  public int getCol() {
    return this.col;
  }
  
  /**
   * setRow
   * This method changes the row number with the new row number that it gets as its parameter
   * @param row, the new row number
   */
  
  public void setRow(int row) {
    this.row = row;
  }
  
  /**
   * setCol
   * This method changes the column number with the new column number that it gets as its parameter
   * @param col, the new column number
   */
  
  public void setCol(int col) {
    this.col = col;
  }
  
  /**
   * getCoins
   * This method returns the number of coins that the object has
   * @return an integer value that represents the number of coins=
   */
  
  public int getCoins() {
    return this.coins;
  }
  
  /**
   * setCoins
   * This method accepts an integer and changes the current number of coins with the parameter
   * @param coins, represents the new number of coins
   */
  
  public void setCoins(int coins) {
    this.coins = coins;
  }
  
  /**
   * getHealth
   * This method returns the total health that the object has
   * @return an integer value that represents the total health
   */
  
  public int getHealth() {
    return this.hp;
  }
  
  /**
   * changeHealth
   * This method takes in an integer value and changes the current health with the parameter
   * @param amount, represents the new total health
   */
  
  public void changeHealth(int amount) {
    this.hp = amount;
  }
  
  /**
   * moveUp
   * This method moves the object up by decreasing the row number, changing the current sprite and the direction to face up
   * @return void
   */
  
  public void moveUp() {
    this.row--;
    this.currentSprite++;
    if (this.currentSprite > 3) {
      this.currentSprite = 0;
    }
    this.direction = 1;
  }
  
  /**
   * moveDown
   * This method moves the object down by increasing the row number, changing the current sprite and the direction to face down
   * @return void
   */
  
  public void moveDown() {
    this.row++;
    this.currentSprite++;
    if (this.currentSprite > 3) {
      this.currentSprite = 0;
    }
    this.direction = 3;
  }
  
  /**
   * moveLeft
   * This method moves the object left by decreasing the column number, changing the current sprite and the direction to face left
   * @return void
   */
  
  public void moveLeft() {
    this.col--;
    this.currentSprite++;
    if (this.currentSprite > 3) {
      this.currentSprite = 0;
    }
    this.direction = 4;
  }
  
  /**
   * moveRight
   * This method moves the object right by increasing the column number, changing the current sprite and the direction to face right
   * @return void
   */
  
  public void moveRight() {
    this.col++;
    this.currentSprite++;
    if (this.currentSprite > 3) {
      this.currentSprite = 0;
    }
    this.direction = 2;
  }
  
  /**
   * getDirection
   * This method returns the current direction that the object is facing
   * @return an integer value that represents the direction
   */
  
  public int getDirection() {
    return this.direction;
  }
  
  /**
   * changeDirection
   * This method accepts an integer value as the new direction and changes the old direction with it
   * @param direction, represents the new direction
   */
  
  public void changeDirection(int direction) {
    this.direction = direction;
  }
  
  /**
   * getCurrentSprite
   * This method returns the current sprite image that the object is on
   * @return an integer value that represents the current sprite image
   */
  
  public int getCurrentSprite() {
    return this.currentSprite;
  }
  
  /**
   * changeCurrentSprite
   * This method accepts an integer value that represents the new sprite image and changes the old one
   * @param amount, represents the new sprite image
   */
  
  public void changeCurrentSprite(int amount) {
    this.currentSprite = amount;
  }
  
  /**
   * getName
   * This method returns the name of the object
   * @return a String value that represents the name
   */
  
  public String getName() {
    return this.name;
  }
  
  /**
   * getMotion
   * This method returns whether the object is in motion or not
   * @return true if the object is in motion, false otherwise
   */
  
  public boolean getMotion() {
    return this.inMotion;
  }
  
  /**
   * changeMotion
   * This method accepts a boolean variable that represents the new motion and changes the old one
   * @param motion, a boolean variable that represents the new motion
   */
  
  public void changeMotion(boolean motion) {
    this.inMotion = motion;
  }
  
  /**
   * getDirectionCount
   * This method returns the number of times that the object was in motion for
   * @return an integer value that represents the direction count
   */
  
  public int getDirectionCount() {
    return this.dirCount;
  }
  
  /**
   * changeDirectionCount
   * This method accepts an int variable that represents the new direction count and updates the old one
   * @param amount, the new direction count
   */
  
  public void changeDirectionCount(int amount) {
    this.dirCount = amount;
  }
  
  /**
   * getAttack
   * This method returns a boolean variable that represents whether the object is attacking or not
   * @return true is the object is attacking, false otherwise
   */
  
  public boolean getAttack() {
    return this.inAttack;
  }
  
  /**
   * changeAttack
   * This method accept a boolean variable that represents the new attack and updates the old one with it
   * @param attack, a boolean variable that represents the new attack
   */
  
  public void changeAttack(boolean attack) {
    this.inAttack = attack;
  }
  
  /**
   * updateAttackCD
   * This method accepts a double variable and updates the total attack cooldown timer with the new amount
   * @param amount, the total amount of time that has elapsed
   */
  
  public void updateAttackCD(double amount) {
    this.attackCD += amount;
  }
  
  /**
   * getAttackCD
   * This method returns the current total attack cooldown time that has elapsed
   * @return a double value, representing the current attack cooldown time
   */
  
  public double getAttackCD() {
    return this.attackCD;
  }
  
  /**
   * restartAttackCD
   * This method sets the attack cooldown timer to zero
   * @return void
   */
  
  public void restartAttackCD() {
    this.attackCD = 0;
  }
}