//Importing all files
import java.awt.*;

/*
 * [MainCharacter.java]
 * Version 1.0
 * This program represents the main character of the game and all the methods that comes with it
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* MainCharacter
* This class holds all the necessary functions needed for the main character, including movement and getting all the necessary variables
*/

class MainCharacter extends Body{
  //Initializing all variables
  private int level;
  private int mana;
  private int armour;
  private int skillPoints;
  private double healthRegen;
  private double manaRegen;
  private int baseHealth;
  private int baseArmour;
  private int baseMana;
  private int baseDamage;
  private int baseCoins;
  private int baseRegen;
  private int weapon;
  private int bulletX;
  private int bulletY;
  private boolean collision;
  
  //Initializing all images
  Image mainCharacterDown1 = Toolkit.getDefaultToolkit().getImage("MainCharacterDown1.png");
  Image mainCharacterDown2 = Toolkit.getDefaultToolkit().getImage("MainCharacterDown2.png");
  Image mainCharacterDown3 = Toolkit.getDefaultToolkit().getImage("MainCharacterDown3.png");
  Image mainCharacterUp1 = Toolkit.getDefaultToolkit().getImage("MainCharacterUp1.png");
  Image mainCharacterUp2 = Toolkit.getDefaultToolkit().getImage("MainCharacterUp2.png");
  Image mainCharacterUp3 = Toolkit.getDefaultToolkit().getImage("MainCharacterUp3.png");
  Image mainCharacterLeft1 = Toolkit.getDefaultToolkit().getImage("MainCharacterLeft1.png");
  Image mainCharacterLeft2 = Toolkit.getDefaultToolkit().getImage("MainCharacterLeft2.png");
  Image mainCharacterLeft3 = Toolkit.getDefaultToolkit().getImage("MainCharacterLeft3.png");
  Image mainCharacterRight1 = Toolkit.getDefaultToolkit().getImage("MainCharacterRight1.png");
  Image mainCharacterRight2 = Toolkit.getDefaultToolkit().getImage("MainCharacterRight2.png");
  Image mainCharacterRight3 = Toolkit.getDefaultToolkit().getImage("MainCharacterRight3.png");
  Image mainCharacterGreenSwordUp = Toolkit.getDefaultToolkit().getImage("MainCharacterGreenSwordUp.png");
  Image mainCharacterGreenSwordDown = Toolkit.getDefaultToolkit().getImage("MainCharacterGreenSwordDown.png");
  Image mainCharacterGreenSwordLeft = Toolkit.getDefaultToolkit().getImage("MainCharacterGreenSwordLeft.png");
  Image mainCharacterGreenSwordRight = Toolkit.getDefaultToolkit().getImage("MainCharacterGreenSwordRight.png");
  Image mainCharacterRedSwordUp = Toolkit.getDefaultToolkit().getImage("MainCharacterRedSwordUp.png");
  Image mainCharacterRedSwordDown = Toolkit.getDefaultToolkit().getImage("MainCharacterRedSwordDown.png");
  Image mainCharacterRedSwordLeft = Toolkit.getDefaultToolkit().getImage("MainCharacterRedSwordLeft.png");
  Image mainCharacterRedSwordRight = Toolkit.getDefaultToolkit().getImage("MainCharacterRedSwordRight.png");
  Image mainCharacterBlueWandUp = Toolkit.getDefaultToolkit().getImage("MainCharacterBlueWandUp.png");
  Image mainCharacterBlueWandDown = Toolkit.getDefaultToolkit().getImage("MainCharacterBlueWandDown.png");
  Image mainCharacterBlueWandLeft = Toolkit.getDefaultToolkit().getImage("MainCharacterBlueWandLeft.png");
  Image mainCharacterBlueWandRight = Toolkit.getDefaultToolkit().getImage("MainCharacterBlueWandRight.png");
  Image mainCharacterPinkWandUp = Toolkit.getDefaultToolkit().getImage("MainCharacterPinkWandUp.png");
  Image mainCharacterPinkWandDown = Toolkit.getDefaultToolkit().getImage("MainCharacterPinkWandDown.png");
  Image mainCharacterPinkWandLeft = Toolkit.getDefaultToolkit().getImage("MainCharacterPinkWandLeft.png");
  Image mainCharacterPinkWandRight = Toolkit.getDefaultToolkit().getImage("MainCharacterPinkWandRight.png");
  Image mainCharacterBowUp = Toolkit.getDefaultToolkit().getImage("MainCharacterBowUp.png");
  Image mainCharacterBowDown = Toolkit.getDefaultToolkit().getImage("MainCharacterBowDown.png");
  Image mainCharacterBowLeft = Toolkit.getDefaultToolkit().getImage("MainCharacterBowLeft.png");
  Image mainCharacterBowRight = Toolkit.getDefaultToolkit().getImage("MainCharacterBowRight.png");
  Image blueOrb = Toolkit.getDefaultToolkit().getImage("BlueOrb.png");
  Image pinkOrb = Toolkit.getDefaultToolkit().getImage("PinkOrb.png");
  Image arrowRight = Toolkit.getDefaultToolkit().getImage("ArrowRight.png");
  Image arrowLeft = Toolkit.getDefaultToolkit().getImage("ArrowLeft.png");
  Image arrowUp = Toolkit.getDefaultToolkit().getImage("ArrowUp.png");
  Image arrowDown = Toolkit.getDefaultToolkit().getImage("ArrowDown.png");
  Image throwBomb = Toolkit.getDefaultToolkit().getImage("ThrowBomb.png");
  Image[][] sprites = {{mainCharacterDown1,mainCharacterDown2,mainCharacterDown3,mainCharacterDown2},{mainCharacterUp1,
    mainCharacterUp2,mainCharacterUp3,mainCharacterUp2},{mainCharacterLeft1,mainCharacterLeft2,mainCharacterLeft3,mainCharacterLeft2},
    {mainCharacterRight1,mainCharacterRight2,mainCharacterRight3,mainCharacterRight2},{mainCharacterGreenSwordUp,mainCharacterGreenSwordRight,
      mainCharacterGreenSwordDown,mainCharacterGreenSwordLeft},{mainCharacterRedSwordUp,mainCharacterRedSwordRight,
      mainCharacterRedSwordDown,mainCharacterRedSwordLeft},{mainCharacterBlueWandUp,mainCharacterBlueWandRight,
      mainCharacterBlueWandDown,mainCharacterBlueWandLeft},{mainCharacterPinkWandUp,mainCharacterPinkWandRight,
      mainCharacterPinkWandDown,mainCharacterPinkWandLeft},{mainCharacterBowUp,mainCharacterBowRight,
      mainCharacterBowDown,mainCharacterBowLeft},{mainCharacterUp1,mainCharacterRight1,
      mainCharacterDown1,mainCharacterLeft1}};
  
  MainCharacter(int hp, int row, int col, int attackCD, int coins, String name) {
    super(hp,row,col,attackCD,coins,name,685,320);
    this.level = 1;
    this.mana = 140;
    this.armour = 50;
    this.skillPoints = 0;
    this.healthRegen = 0;
    this.baseHealth = 100;
    this.baseMana = 140;
    this.baseArmour = 280;
    this.baseDamage = 0;
    this.baseCoins = 0;
    this.baseRegen = 1;
    this.weapon = 0;
    this.bulletX = 0;
    this.bulletY = 0;
    this.collision = false;
  }
  
  /**
   * addCoins
   * This method accepts an integer value that represents the amount of money that needs to be added on 
   * to the current number of coins
   * @param amount, an int variable that represents the amount of coins that should be added
   */
  
  public void addCoins(int amount) {
    this.setCoins(this.getCoins()+amount);
  }
  
  /**
   * removeCoins
   * This method accepts an integer value that represents the total amount of money that needs to be removed from the current 
   * number of coins.
   * @param amount, an integer value that represents the amount of coins that should be removed
   */
  
  public void removeCoins(int amount) {
    this.setCoins(this.getCoins()-amount);
  }
  
  /**
   * draw
   * This method displays the player and the projectiles to the screen
   * @param g, the graphics variable
   */
  
  public void draw(Graphics g) {
    //Displaying and moving the projectiles
    if (this.getAttack()) {
      if (this.getDirection() == 1) {
        g.drawImage(sprites[this.weapon][this.getDirection()-1],xCoordinate,yCoordinate,null);
        if (this.weapon == 6 && !collision) {
          g.drawImage(blueOrb,xCoordinate+bulletX,yCoordinate+bulletY-15,null);
        } else if (this.weapon == 7 && !collision) {
          g.drawImage(pinkOrb,xCoordinate+bulletX,yCoordinate+bulletY-15,null);
        } else if (this.weapon == 8 && !collision) {
          g.drawImage(arrowUp,xCoordinate+bulletX,yCoordinate+bulletY-15,null);
        } else if (this.weapon == 9 && !collision) {
          g.drawImage(throwBomb,xCoordinate+bulletX+22,yCoordinate+bulletY-15,null);
        }
        bulletY-=3;
      } else if (this.getDirection() == 2) {
        g.drawImage(sprites[this.weapon][this.getDirection()-1],xCoordinate+5,yCoordinate+5,null);
        if (this.weapon == 6 && !collision) {
          g.drawImage(blueOrb,xCoordinate+bulletX+10,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 7 && !collision) {
          g.drawImage(pinkOrb,xCoordinate+bulletX+10,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 8 && !collision) {
          g.drawImage(arrowRight,xCoordinate+bulletX,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 9 && !collision) {
          g.drawImage(throwBomb,xCoordinate+bulletX,yCoordinate+bulletY+40,null);
        } 
        bulletX+=3;
      } else if (this.getDirection() == 3) {
        g.drawImage(sprites[this.weapon][this.getDirection()-1],xCoordinate-5,yCoordinate+13,null);
        if (this.weapon == 6 && !collision) {
          g.drawImage(blueOrb,xCoordinate+bulletX,yCoordinate+bulletY+11,null);
        } else if (this.weapon == 7 && !collision) {
          g.drawImage(pinkOrb,xCoordinate+bulletX,yCoordinate+bulletY+11,null);
        } else if (this.weapon == 8 && !collision) {
          g.drawImage(arrowDown,xCoordinate+bulletX,yCoordinate+bulletY,null);
        } else if (this.weapon == 9 && !collision) {
          g.drawImage(throwBomb,xCoordinate+bulletX+15,yCoordinate+bulletY+20,null);
        } 
        bulletY+=3;
      } else if (this.getDirection() == 4) {
        g.drawImage(sprites[this.weapon][this.getDirection()-1],xCoordinate-10,yCoordinate+5,null);
        if (this.weapon == 6 && !collision) {
          g.drawImage(blueOrb,xCoordinate+bulletX-10,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 7 && !collision) {
          g.drawImage(pinkOrb,xCoordinate+bulletX-10,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 8 && !collision) {
          g.drawImage(arrowLeft,xCoordinate+bulletX,yCoordinate+bulletY+20,null);
        } else if (this.weapon == 9 && !collision) {
          g.drawImage(throwBomb,xCoordinate+bulletX,yCoordinate+bulletY+40,null);
        } 
        bulletX-=3;
      }
      //Moving the player
    } else {
      if (this.getDirection() == 1) {
        if (this.getMotion()) {
          yCoordinate--;
        }
        g.drawImage(sprites[1][this.getCurrentSprite()],xCoordinate,yCoordinate,null);
      } else if (this.getDirection() == 3) {
        if (this.getMotion()) {
          yCoordinate++;
        }
        g.drawImage(sprites[0][this.getCurrentSprite()],xCoordinate,yCoordinate,null);
      } else if (this.getDirection() == 2) {
        if (this.getMotion()) {
          xCoordinate++;
        }
        g.drawImage(sprites[3][this.getCurrentSprite()],xCoordinate,yCoordinate,null);
      } else {
        if (this.getMotion()) {
          xCoordinate--;
        }
        g.drawImage(sprites[2][this.getCurrentSprite()],xCoordinate,yCoordinate,null);
      }
    }
  }
  
  /**
   * getLevel
   * This method returns the current level of the player
   * @return an int variable that represents the current level
   */
  
  public int getLevel() {
    return this.level;
  }
  
  /**
   * increaseLevel
   * This method increases the current level of the player by one
   * @return void
   */
  
  public void increaseLevel() {
    this.level++;
  }
  
  /**
   * getArmour
   * This method returns the current total armour that the player has
   * @return an int variable that represents the total armour
   */
  
  public int getArmour() {
    return this.armour;
  }
  public void changeArmour(int amount) {
    this.armour = amount;
  }
  public int getMana() {
    return this.mana;
  }
  public void changeMana(int amount) {
    this.mana = amount;
  }
  public int getSkillPoints() {
    return this.skillPoints;
  }
  public void changeSkillPoints(int amount) {
    this.skillPoints = amount;
  }
  public void updateRegen(double amount) {
    this.healthRegen += amount;
    this.manaRegen += amount;
  }
  public void restartHealth() {
    this.healthRegen = 0;
  }
  public void restartMana() {
    this.manaRegen = 0;
  }
  public double getHealthRegen() {
    return this.healthRegen;
  }
  public double getManaRegen() {
    return this.manaRegen;
  }
  public int getBaseHealth() {
    return this.baseHealth;
  }
  public void changeBaseHealth(int health) {
    this.baseHealth = health;
  }
  public int getBaseArmour() {
    return this.baseArmour;
  }
  public void changeBaseArmour(int armour) {
    this.baseArmour = armour;
  }
  public int getBaseMana() {
    return this.baseMana;
  }
  public void changeBaseMana(int mana) {
    this.baseMana = mana;
  }
  public int getBaseDamage() {
    return this.baseDamage;
  }
  public void changeBaseDamage(int damage) {
    this.baseDamage = damage;
  }
  public int getBaseCoins() {
    return this.baseCoins;
  }
  public void changeBaseCoins(int coins) {
    this.baseCoins = coins;
  }
  public int getBaseRegen() {
    return this.baseRegen;
  }
  public void changeBaseRegen(int regen) {
    this.baseRegen = regen;
  }
  public void changeWeapon(int weapon) {
    this.weapon = weapon;
  }
  public int getBulletX() {
    return this.bulletX;
  }
   public int getBulletY() {
    return this.bulletY;
  }
   public void changeBulletX(int x) {
     this.bulletX = x;
   }
   public void changeBulletY(int y) {
     this.bulletY = y;
   }
   public boolean getCollision() {
     return this.collision;
   }
   public void changeCollision(boolean collision) {
     this.collision = collision;
   }
   public int getWeapon() {
     return this.weapon;
   }
}