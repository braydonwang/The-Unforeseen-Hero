//Importing all files
import java.lang.Math;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.sound.sampled.*;

/*
 * [Boss.java]
 * Version 1.0
 * This program represents the final boss fight and all the methods needed to run it.
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Boss
* This class holds all the necessary functions needed for the boss fight to happen, including drawing it, 
* attacking and moving.
*/

class Boss {
  //Initializing all images
  Image redCarpet = Toolkit.getDefaultToolkit().getImage("Red Carpet.png");
  Image yellowCarpet = Toolkit.getDefaultToolkit().getImage("Yellow Carpet.png");
  Image stoneBrick = Toolkit.getDefaultToolkit().getImage("Stone Brick.png");
  Image pillar = Toolkit.getDefaultToolkit().getImage("Pillar.png");
  Image finalBoss1 = Toolkit.getDefaultToolkit().getImage("FinalBoss1.png");
  Image finalBoss2 = Toolkit.getDefaultToolkit().getImage("FinalBoss2.png");
  Image finalBoss3 = Toolkit.getDefaultToolkit().getImage("FinalBoss3.png");
  Image finalBoss4 = Toolkit.getDefaultToolkit().getImage("FinalBoss4.png");
  Image finalBoss5 = Toolkit.getDefaultToolkit().getImage("FinalBoss5.png");
  Image finalBoss6 = Toolkit.getDefaultToolkit().getImage("FinalBoss6.png");
  Image[][] sprite = {{finalBoss1,finalBoss2,finalBoss3},{finalBoss4,finalBoss5,finalBoss6}};
  
  //Determing the maximum y coordinate and image size based on the screen size
  final int maxY = Toolkit.getDefaultToolkit().getScreenSize().height; 
  final int GridToScreenRatio = maxY / 18;
  
  //Initializing all variables
  private double attackCD;
  private int monsterHealth;
  private int currentSprite;
  private int spriteCount;
  int dirY;
  int count;
  int direction;
  int originalY;
  int health;
  int row;
  int col;
  String monsterName;
  Monster[][] monsters = new Monster[30][30];
  
  Boss() {
    this.dirY = 0;
    this.count = 0;
    this.direction = 0;
    this.originalY = 0;
    this.attackCD = 0;
    this.health = 500;
    this.row = 0;
    this.col = 0;
    monsters[15][15] = new Wolf(16,16,20);
    monsters[14][14] = new Wolf(15,15,20);
    this.monsterName = "";
    this.monsterHealth = 0;
    this.currentSprite = 0;
    this.spriteCount = 0;
  }
  
  /**
   * draw
   * This method displays the layout of the boss fight
   * @param g, Graphics variable that displays all images to the screen
   * @param map, a 2D int array that holds the map of the boss fight
   * @param bossRoom, an int variable that determines which map of the boss arena to use
   * @param player, a MainCharacter variable that represents the player
   * @param clock, a Clock variable that represents the total amount of time that has passed
   */
  
  public void draw(Graphics g, int[][] map, int bossRoom, MainCharacter player, Clock clock) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,1440,768);
    
    if (bossRoom == 2) {
      this.spriteCount++;
      if (this.spriteCount % 5 == 0) {
        this.currentSprite = (this.currentSprite + 1) % 3;
      }
      this.originalY = 672;
    } else {
      this.originalY = 0;
    }
    
    //Displaying the images for the layout of the stadium
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] == 2) {
          g.drawImage(stoneBrick,GridToScreenRatio*j-20,GridToScreenRatio*i-40-dirY-originalY,GridToScreenRatio,GridToScreenRatio,null);
        } else if (map[i][j] == 3 || map[i][j] == 6) {
          g.drawImage(yellowCarpet,GridToScreenRatio*j-20,GridToScreenRatio*i-40-dirY-originalY,GridToScreenRatio,GridToScreenRatio,null);
        } else if (map[i][j] == 4) {
          g.drawImage(redCarpet,GridToScreenRatio*j-20,GridToScreenRatio*i-40-dirY-originalY,GridToScreenRatio,GridToScreenRatio,null);
        } else if (map[i][j] == 5) {
          g.drawImage(stoneBrick,GridToScreenRatio*j-20,GridToScreenRatio*i-40-dirY-originalY,GridToScreenRatio,GridToScreenRatio,null);
          g.drawImage(pillar,GridToScreenRatio*j-35,GridToScreenRatio*i-35-dirY-originalY,GridToScreenRatio+30,GridToScreenRatio+30,null);
        } 
      }
    }
    
    //Displaying the images for the boss
    if (bossRoom == 2) {
      if (this.health > 200) {
        g.drawImage(sprite[0][this.currentSprite],GridToScreenRatio*13-20,GridToScreenRatio*13-60-dirY-originalY,null);
      } else {
        g.drawImage(sprite[1][this.currentSprite],GridToScreenRatio*13-20,GridToScreenRatio*13-60-dirY-originalY,null);
      }
      
      //Displaying the images for the wolves that spawn 
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (monsters[i][j] instanceof Wolf && monsters[i][j].getRow() != -1) {
            monsters[i][j].draw(monsters[i][j].getName(),g,0,dirY+400);
          }
        }
      }
      
      //Changing the y coordinates of the map based on its direction
      if (this.direction == 1) {
        this.dirY--;
        this.count++;
      } else if (this.direction == 3) {
        this.dirY++;
        this.count++;
      }
      
      //If the direction count is greater than or equal to the size of one tile, stop it from moving
      if (this.count >= 48) {
        this.direction = 0;
        this.count = 0;
      }
      
      //Moving the monsters based on their position on the screen
      for (int i = 0; i < monsters.length; i++) {
        for (int j = 0; j < monsters[i].length; j++) {
          if (monsters[i][j] instanceof Wolf && monsters[i][j].getRow() != -1) {
            //Update attack cooldown and movement speed based on elapsed time
            monsters[i][j].updateAttackCD(clock.getElapsedTime());
            monsters[i][j].updateMovementSpeed(clock.getElapsedTime());
            //Changing the current sprite images of the monster
            if (monsters[i][j].getMotion()) {
              if (monsters[i][j].getDirectionCount() % 8 == 0) {
                monsters[i][j].changeCurrentSprite((monsters[i][j].getCurrentSprite()+1)%4);
              }
              monsters[i][j].changeDirectionCount(monsters[i][j].getDirectionCount()+1);
              //If the monsters movement count is greater than the size of one tile, change motion to be false and create another monster at the tile it is on
              if (monsters[i][j].getDirectionCount() >= 48) {
                monsters[i][j].changeMotion(false);
                monsters[i][j].changeDirectionCount(0);
                if (monsters[i][j].getDirection() == 1) {
                  monsters[i-1][j] = monsters[i][j];
                  monsters[i][j] = null;
                } else if (monsters[i][j].getDirection() == 2) {
                  monsters[i][j+1] = monsters[i][j];
                  monsters[i][j] = null;
                } else if (monsters[i][j].getDirection() == 3) {
                  monsters[i+1][j] = monsters[i][j];
                  monsters[i][j] = null;
                } else if (monsters[i][j].getDirection() == 4) {
                  monsters[i][j-1] = monsters[i][j];
                  monsters[i][j] = null;
                }
                //Monster attacks player
                if (monsters[player.getRow()-1][player.getCol()-1] instanceof Wolf && monsters[player.getRow()-1][player.getCol()-1].getAttackCD() >= 1) {
                  if (monsters[player.getRow()-1][player.getCol()-1].getRow() != -1) {
                    if (player.getArmour() >= 10) {
                      player.changeArmour(player.getArmour()-10);
                    } else if (player.getArmour() > 0) {
                      player.changeHealth(player.getHealth()-(10-player.getArmour()));
                      player.changeArmour(0);
                    } else {
                      player.changeHealth(player.getHealth()-10);
                    }   
                    player.restartHealth();
                    hurt();
                    monsters[player.getRow()-1][player.getCol()-1].restartAttackCD();
                  }
                }
              }
            } else {
              //Move the monster based on its positioning to the player
              monsters[i][j].changeSpeed(1);
              if (Math.abs((player.getRow() - i)) > Math.abs((player.getCol() - j-1))) {
                if ((player.getRow() - i) < 0 && !isObstacle(map[i-1][j]) && monsters[i-1][j] == null) {
                  monsters[i][j].moveUp();
                  monsters[i-1][j] = new Wolf(-1,-1,-1);
                  monsters[i][j].changeMotion(true);
                  monsters[i][j].restartMovementSpeed();
                } else if (!isObstacle(map[i+1][j])&& monsters[i+1][j] == null) {
                  monsters[i][j].moveDown();
                  monsters[i][j].changeMotion(true);
                  monsters[i+1][j] = new Wolf(-1,-1,-1);
                  monsters[i][j].restartMovementSpeed();
                }
              } else if (Math.abs((player.getRow() - i)) < Math.abs((player.getCol() - j - 1))) {
                if ((player.getCol() - j - 1) < 0 && !isObstacle(map[i][j-1]) && monsters[i][j-1] == null) {
                  monsters[i][j].moveLeft();
                  monsters[i][j-1] = new Wolf(-1,-1,-1);
                  monsters[i][j].changeMotion(true);
                  monsters[i][j].restartMovementSpeed();
                } else if (!isObstacle(map[i][j+1])&& monsters[i][j+1] == null) {
                  monsters[i][j].moveRight();
                  monsters[i][j+1] = new Wolf(-1,-1,-1);
                  monsters[i][j].changeMotion(true);
                  monsters[i][j].restartMovementSpeed();
                }
              } else {
                if (player.getRow() < i + 1 && !isObstacle(map[i-1][j]) && monsters[i-1][j] == null) {
                  monsters[i][j].moveUp();
                  monsters[i-1][j] = new Wolf(-1,-1,-1);
                  monsters[i][j].changeMotion(true);
                  monsters[i][j].restartMovementSpeed();
                } else if (!isObstacle(map[i+1][j])&& monsters[i+1][j] == null) {
                  monsters[i][j].moveDown();
                  monsters[i+1][j] = new Wolf(-1,-1,-1);
                  monsters[i][j].changeMotion(true);
                  monsters[i][j].restartMovementSpeed();
                }
              }
            }
          }
        }
      }
      
      //Checking for collision with the projectiles and obstacles/monsters
      if ((player.getBulletX() != 0 || player.getBulletY() != 0) && !player.getCollision()) {
        if (player.getRow()-1+(player.getBulletY()/48) >= 0 && player.getRow()-1+(player.getBulletY()/48) <= 49 && 
            player.getCol()-1+(player.getBulletX()/48) >= 0 && player.getCol()-1+(player.getBulletX()/48) <= 49 && 
            isObstacle(map[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)])) {
          player.changeCollision(true);
        } else if (player.getRow()-1+(player.getBulletY()/48) >= 0 && player.getRow()-1+(player.getBulletY()/48) <= 49 && 
                   player.getCol()-1+(player.getBulletX()/48) >= 0 && player.getCol()-1+(player.getBulletX()/48) <= 49 && 
                   monsters[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)] instanceof Wolf) {
          Wolf wolf = (Wolf)(monsters[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)]);
          if (wolf.getRow() != -1) {
            player.changeCollision(true);
            this.monsterName = wolf.getName();
            
            //Changing the health of the wolf based on the weapon the player is using
            if (player.getWeapon() == 6) {
              wolf.changeHealth(wolf.getHealth()-30-player.getBaseDamage());
            } else if (player.getWeapon() == 7) {
              wolf.changeHealth(wolf.getHealth()-60-player.getBaseDamage());
            } else if (player.getWeapon() == 8) {
              wolf.changeHealth(wolf.getHealth()-10-player.getBaseDamage());
            } else if (player.getWeapon() == 9) {
              wolf.changeHealth(wolf.getHealth()-80-player.getBaseDamage());
            }
            
            //Player earns coin and wolf disappears if its health drops below zero
            if (wolf.getHealth() <= 0) {
              monsters[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)] = null;
              player.setCoins(player.getCoins()+wolf.getCoins());
              clock.restart();
              this.monsterName = "";
            } else {
              this.monsterHealth = wolf.getHealth();
            }
            player.changeBulletX(0);
            player.changeBulletY(0);
          }
        } else if (player.getRow()-1+(player.getBulletY()/48) >= 0 && player.getRow()-1+(player.getBulletY()/48) <= 49 && 
                   player.getCol()-1+(player.getBulletX()/48) >= 0 && player.getCol()-1+(player.getBulletX()/48) <= 49 && 
                   map[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)] == 6) {
          //Collision between projectiles and the boss
          player.changeCollision(true);
          this.monsterName = "Boss";
          if (player.getWeapon() == 6) {
            this.health = this.health-30-player.getBaseDamage();
          } else if (player.getWeapon() == 7) {
            this.health = this.health-60-player.getBaseDamage();
          } else if (player.getWeapon() == 8) {
            this.health = this.health-10-player.getBaseDamage();
          } else if (player.getWeapon() == 9) {
            this.health = this.health-80-player.getBaseDamage();
          }
          player.changeBulletX(0);
          player.changeBulletY(0);
        }
      }
    }
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
  
  /**
   * attack
   * This method accepts the map of the boss fight arena and determines where to spawn the wolves
   * @return void
   */
  
  public void attack(int[][] room) {
    do {
      row = (int)(Math.random()*30);
      col = (int)(Math.random()*30);
    } while (monsters[row][col] != null || isObstacle(room[row][col]));
        
    int coins = (int)(Math.random()*19)+1;
    monsters[row][col] = new Wolf(row+1,col+1,coins);
  }
  
  /**
   * isObstacle
   * This method accepts an integer number and determines whether the number represents an obstacles or not
   * @return true if it is an obstacle, false otherwise
   */
  
  public boolean isObstacle(int num) {
    if (num == 0 || num == 1 || num == 6) {
      return true;
    }
    return false;
  }
  
  /**
   * getName
   * This method returns the name of the monster
   * @return a String value that represents the name
   */
  
  public String getName() {
    return this.monsterName;
  }
  
  /**
   * getHealth
   * This method returns the health of the monster or the boss
   * @return an int value that represents the health
   */
  
  public int getHealth() {
    if (this.monsterName.equals("Wolf")) {
      return this.monsterHealth;
    } else {
      return this.health;
    }
  }
  
  /**
   * getBossHealth
   * This method returns the health of the boss
   * @return an int value that represents the current health of the boss
   */
  public int getBossHealth() {
    return this.health;
  }
  
  /**
   * swordAttack
   * This method determines whether the sword attack done by the player makes a connection with a monster or boss
   * @param row, an int value that represents the sword's row number
   * @param col, an int value that represents the sword's column number
   * @param map, a 2D array that represents the map of the boss fight arena
   * @param clock, a Clock variable that holds the total time that has elapsed
   * @param inventoryItems, an Item array that holds the player's current inventory
   * @param quickInventoryOption, an int variable that represents the current inventory option that the player is currently choosing
   */
  
  public void swordAttack(int row, int col, int[][] map, MainCharacter player, Clock clock, Item[] inventoryItems, int quickInventoryOption) {
    Wolf wolf = (Wolf)(monsters[row][col]);
    
    //Apply the necessary damage
    if (wolf != null && wolf.getRow() != -1) {
      if (inventoryItems[quickInventoryOption-1].getId() == 1) {
        wolf.changeHealth(wolf.getHealth()-20-player.getBaseDamage());
      } else if (inventoryItems[quickInventoryOption-1].getId() == 2) {
        wolf.changeHealth(wolf.getHealth()-40-player.getBaseDamage());
      }
      this.monsterName = wolf.getName();
      if (wolf.getHealth() <= 0) {
        player.setCoins(player.getCoins()+wolf.getCoins());
        monsters[row][col] = null;
        this.monsterName = "";
      } else {
        monsterHealth = wolf.getHealth();
      }
    } else if (map[row][col] == 6) {
      //Damage done to the boss
      if (inventoryItems[quickInventoryOption-1].getId() == 1) {
        this.health = this.health - 20 - player.getBaseDamage();
      } else if (inventoryItems[quickInventoryOption-1].getId() == 2) {
        this.health = this.health - 40 - player.getBaseDamage();
      }
      this.monsterName = "Boss";
      monsterHealth = this.health;
    }
  }
  
  /**
   * hurt
   * This method plays the sound effect when the player is being hit
   * @return void
   */
  
  public static void hurt() {
    try {
      File audioFile = new File("Hurt.wav");
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.addLineListener(new HurtListener());
      clip.open(audioStream);
      clip.start();
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * HurtListener
   * This class closes the sound effect when the hurt sound is finished
   * @return void
   */
  
  static class HurtListener implements LineListener {
    public void update(LineEvent event) {
      if (event.getType() == LineEvent.Type.STOP) {
        event.getLine().close();
      }
    }
  }
}
