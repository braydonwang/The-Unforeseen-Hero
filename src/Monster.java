import java.awt.*;

/*
 * [Monster.java]
 * Version 1.0
 * This program represents the monsters in the game
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Monster
* This class holds the methods for the monsters on the screen.
*/

abstract class Monster extends Body {
  Image wolfDown1 = Toolkit.getDefaultToolkit().getImage("WolfDown1.png");
  Image wolfDown2 = Toolkit.getDefaultToolkit().getImage("WolfDown2.png");
  Image wolfDown3 = Toolkit.getDefaultToolkit().getImage("WolfDown3.png");
  Image wolfUp1 = Toolkit.getDefaultToolkit().getImage("WolfUp1.png");
  Image wolfUp2 = Toolkit.getDefaultToolkit().getImage("WolfUp2.png");
  Image wolfUp3 = Toolkit.getDefaultToolkit().getImage("WolfUp3.png");
  Image wolfLeft1 = Toolkit.getDefaultToolkit().getImage("WolfLeft1.png");
  Image wolfLeft2 = Toolkit.getDefaultToolkit().getImage("WolfLeft2.png");
  Image wolfLeft3 = Toolkit.getDefaultToolkit().getImage("WolfLeft3.png");
  Image wolfRight1 = Toolkit.getDefaultToolkit().getImage("WolfRight1.png");
  Image wolfRight2 = Toolkit.getDefaultToolkit().getImage("WolfRight2.png");
  Image wolfRight3 = Toolkit.getDefaultToolkit().getImage("WolfRight3.png");
  private double movementSpeed;
  private int speed;
  Image[][] sprites = {{wolfDown1,wolfDown2,wolfDown3,wolfDown2},{wolfUp1,wolfUp2,wolfUp3,wolfUp2},{wolfLeft1,wolfLeft2,
    wolfLeft3,wolfLeft2}, {wolfRight1,wolfRight2,wolfRight3,wolfRight2}};
  Monster(int hp, int row, int col, int attackCD, int coins, String name) {
    super(hp,row,col,attackCD,coins,name,48*col-70,48*row-348);
    this.movementSpeed = 0;
    this.speed = 2;
  }
  
  /**
   * draw
   * This method displays the wolf onto the screen
   * @param name, the String variable for name of the wolf
   * @param g, the graphics variable
   * @param dirX, the int variable that represents the x location of the wolf in proporation to the map
   * @param dirY, the int variable that represents the y location of the wolf in proporation to the map
   */
  
  public void draw(String name,Graphics g,int dirX, int dirY) {
    //Displaying and moving the monsters
    if (name.equals("Wolf")) {
      if (this.getDirection() == 1) {
        if (this.getMotion()) {
          yCoordinate--;
        }
        g.drawImage(sprites[1][this.getCurrentSprite()],xCoordinate-dirX,yCoordinate-dirY,null);
      } else if (this.getDirection() == 3) {
        if (this.getMotion()) {
          yCoordinate++;
        }
        g.drawImage(sprites[0][this.getCurrentSprite()],xCoordinate-dirX,yCoordinate-dirY,null);
      } else if (this.getDirection() == 2) {
        if (this.getMotion()) {
          xCoordinate++;
        }
        g.drawImage(sprites[3][this.getCurrentSprite()],xCoordinate-dirX,yCoordinate-dirY,null);
      } else {
        if (this.getMotion()) {
          xCoordinate--;
        }
        g.drawImage(sprites[2][this.getCurrentSprite()],xCoordinate-dirX,yCoordinate-dirY,null);
      }
    }
  }
  
  /**
   * getMovementSpeed
   * This method returns the total movement speed timer of the monsters
   * @return a double variable that represents the movement speed timer
   */
  
  public double getMovementSpeed() {
    return this.movementSpeed;
  }
  
  /**
   * updateMovementSpeed
   * This method increases the total movement speed timer with the parameter
   * @param amount, a double variable that represents the time that has elapsed
   */
  
  public void updateMovementSpeed(double amount) {
    this.movementSpeed += amount;
  }
  
  /**
   * restartMovementSpeed
   * This method restarts the total movement speed timer to zero
   * @return void
   */
  
  public void restartMovementSpeed() {
    this.movementSpeed = 0;
  }
  
  /**
   * getSpeed
   * This method returns the default speed value of the monster
   * @return an int value that represents how long before the monster can move again
   */
  
  public int getSpeed() {
    return this.speed;
  }
  
  /**
   * changeSpeed
   * This method accepts an int variable that represents the new speed and changes the old one with it
   * @param speed, an int variable that represents the new speed
   */
  
  public void changeSpeed(int speed) {
    this.speed = speed;
  }
}