import java.awt.*;

/*
 * [Maze.java]
 * Version 1.0
 * This program represents the main map of each quest maze
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Maze
* This class holds the design of the maze and how to draw it
*/

class Maze {
  //Initializing all images
  Image down1 = Toolkit.getDefaultToolkit().getImage("Down1.png");
  Image down2 = Toolkit.getDefaultToolkit().getImage("Down2.png");
  Image down3 = Toolkit.getDefaultToolkit().getImage("Down3.png");
  Image down4 = Toolkit.getDefaultToolkit().getImage("Down4.png");
  Image down5 = Toolkit.getDefaultToolkit().getImage("Down5.png");
  Image down6 = Toolkit.getDefaultToolkit().getImage("Down6.png");
  Image down7 = Toolkit.getDefaultToolkit().getImage("Down7.png");
  Image down8 = Toolkit.getDefaultToolkit().getImage("Down8.png");
  Image down9 = Toolkit.getDefaultToolkit().getImage("Down9.png");
  Image down10 = Toolkit.getDefaultToolkit().getImage("Down10.png");
  Image up1 = Toolkit.getDefaultToolkit().getImage("Up1.png");
  Image up2 = Toolkit.getDefaultToolkit().getImage("Up2.png");
  Image up3 = Toolkit.getDefaultToolkit().getImage("Up3.png");
  Image up4 = Toolkit.getDefaultToolkit().getImage("Up4.png");
  Image up5 = Toolkit.getDefaultToolkit().getImage("Up5.png");
  Image up6 = Toolkit.getDefaultToolkit().getImage("Up6.png");
  Image up7 = Toolkit.getDefaultToolkit().getImage("Up7.png");
  Image up8 = Toolkit.getDefaultToolkit().getImage("Up8.png");
  Image up9 = Toolkit.getDefaultToolkit().getImage("Up9.png");
  Image up10 = Toolkit.getDefaultToolkit().getImage("Up10.png");
  Image right1 = Toolkit.getDefaultToolkit().getImage("Right1.png");
  Image right2 = Toolkit.getDefaultToolkit().getImage("Right2.png");
  Image right3 = Toolkit.getDefaultToolkit().getImage("Right3.png");
  Image right4 = Toolkit.getDefaultToolkit().getImage("Right4.png");
  Image right5 = Toolkit.getDefaultToolkit().getImage("Right5.png");
  Image right6 = Toolkit.getDefaultToolkit().getImage("Right6.png");
  Image right7 = Toolkit.getDefaultToolkit().getImage("Right7.png");
  Image right8 = Toolkit.getDefaultToolkit().getImage("Right8.png");
  Image right9 = Toolkit.getDefaultToolkit().getImage("Right9.png");
  Image right10 = Toolkit.getDefaultToolkit().getImage("Right10.png");
  Image left1 = Toolkit.getDefaultToolkit().getImage("Left1.png");
  Image left2 = Toolkit.getDefaultToolkit().getImage("Left2.png");
  Image left3 = Toolkit.getDefaultToolkit().getImage("Left3.png");
  Image left4 = Toolkit.getDefaultToolkit().getImage("Left4.png");
  Image left5 = Toolkit.getDefaultToolkit().getImage("Left5.png");
  Image left6 = Toolkit.getDefaultToolkit().getImage("Left6.png");
  Image left7 = Toolkit.getDefaultToolkit().getImage("Left7.png");
  Image left8 = Toolkit.getDefaultToolkit().getImage("Left8.png");
  Image left9 = Toolkit.getDefaultToolkit().getImage("Left9.png");
  Image left10 = Toolkit.getDefaultToolkit().getImage("Left10.png");
  Image main = Toolkit.getDefaultToolkit().getImage("Main.png");
  Image top = Toolkit.getDefaultToolkit().getImage("Top Wall.png");
  Image bottom = Toolkit.getDefaultToolkit().getImage("Bottom Wall.png");
  Image left = Toolkit.getDefaultToolkit().getImage("Left Wall.png");
  Image right = Toolkit.getDefaultToolkit().getImage("Right Wall.png");
  Image topRight = Toolkit.getDefaultToolkit().getImage("Top Right.png");
  Image topLeft = Toolkit.getDefaultToolkit().getImage("Top Left.png");
  Image bottomRight = Toolkit.getDefaultToolkit().getImage("Bottom Right.png");
  Image bottomLeft = Toolkit.getDefaultToolkit().getImage("Bottom Left.png");
  Image walking = Toolkit.getDefaultToolkit().getImage("Walking.png");
  Image teleportTopRight = Toolkit.getDefaultToolkit().getImage("Big Teleporter Top Right.png");
  Image teleportTopLeft = Toolkit.getDefaultToolkit().getImage("Big Teleporter Top Left.png");
  Image teleportBottomRight = Toolkit.getDefaultToolkit().getImage("Big Teleporter Bottom Right.png");
  Image teleportBottomLeft = Toolkit.getDefaultToolkit().getImage("Big Teleporter Bottom Left.png");
  Image teleporter = Toolkit.getDefaultToolkit().getImage("Teleporter.png");
  Image tree = Toolkit.getDefaultToolkit().getImage("Tree.png");
  Image red = Toolkit.getDefaultToolkit().getImage("Red Flower.png");
  Image orange = Toolkit.getDefaultToolkit().getImage("Orange Flower.png");
  Image yellow = Toolkit.getDefaultToolkit().getImage("Yellow Flower.png");
  Image blue = Toolkit.getDefaultToolkit().getImage("Blue Flower.png");
  Image white = Toolkit.getDefaultToolkit().getImage("White Flower.png");
  Image switchOff = Toolkit.getDefaultToolkit().getImage("Switch Off.png");
  Image switchOn = Toolkit.getDefaultToolkit().getImage("Switch On.png");
  Image grass = Toolkit.getDefaultToolkit().getImage("Grass.png");
  Image[] down = {down1,down2,down3,down4,down5,down6,down7,down8,down9,down10};
  Image[] up = {up1,up2,up3,up4,up5,up6,up7,up8,up9,up10};
  Image[] east = {right1,right2,right3,right4,right5,right6,right7,right8,right9,right10};
  Image[] west = {left1,left2,left3,left4,left5,left6,left7,left8,left9,left10};
  
  //Determing the maximum y coordinate in relation to the screen
  final int maxY = Toolkit.getDefaultToolkit().getScreenSize().height; 
  final int GridToScreenRatio = maxY / 18;
  
  //Initializing all variables
  int currentSprite;
  int[][] teleport = new int[16][20];
  int[][] otherMap = new int[16][20];
  int[] switches = new int[10];
  
  Maze(int[][] teleport) {
    this.currentSprite = 0;
    this.teleport = teleport;
  }
  
  /**
   * changeSwitch
   * This method changes the current switch array with the parameter
   * @param switches, the int array that represents the current position of the switches (on/off)
   */
  
  public void changeSwitch(int[] switches) {
    this.switches = switches;
  }
  
  /**
   * draw
   * This method displays the map layout to the screen
   * @param g, the graphics variable
   * @param map, the 2D int array for the map
   * @param questOption, the current quest option
   */
  
  public void draw(Graphics g, int[][] map, int questOption) {
    //Display teleport maze overlay
    if (questOption >= 4 && questOption <= 6) {
      otherMap = map;
      map = teleport;
    }
    
    //Displaying the flower puzzle garden
    if (questOption == 7) {
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          g.drawImage(grass,GridToScreenRatio*j-50,GridToScreenRatio*i-40,107,107,null);
          if (map[i][j] == 1) {
            if (switches[j/2-2] == 0) {
              g.drawImage(switchOff,GridToScreenRatio*j-50,GridToScreenRatio*i-40,100,100,null);
            } else {
              g.drawImage(switchOn,GridToScreenRatio*j-50,GridToScreenRatio*i-40,100,100,null);
            }
          } else if (map[i][j] == 2) {
            g.drawImage(red,GridToScreenRatio*j-20,GridToScreenRatio*i-40,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 3) {
            g.drawImage(yellow,GridToScreenRatio*j-20,GridToScreenRatio*i-40,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 4) {
            g.drawImage(orange,GridToScreenRatio*j-20,GridToScreenRatio*i-40,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 5) {
            g.drawImage(blue,GridToScreenRatio*j-20,GridToScreenRatio*i-40,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 6) {
            g.drawImage(white,GridToScreenRatio*j-20,GridToScreenRatio*i-40,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 7) {
            g.drawImage(tree,GridToScreenRatio*j-20,GridToScreenRatio*i-40,50,50,null);
          }
        }
      }
      //Displaying the conveyor and teleportation maze
    } else {
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (map[i][j] == 0) {
            g.drawImage(main,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 1) {
            g.drawImage(topLeft,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 2) {
            g.drawImage(topRight,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 3) {
            g.drawImage(bottomLeft,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 4) {
            g.drawImage(bottomRight,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 5) {
            g.drawImage(top,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 6) {
            g.drawImage(bottom,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 7) {
            g.drawImage(left,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 8) {
            g.drawImage(right,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 9) {
            g.drawImage(walking,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 10) {
            g.drawImage(up[this.currentSprite/2],GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 11) {
            g.drawImage(down[this.currentSprite/2],GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 12) {
            g.drawImage(west[this.currentSprite/2],GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          } else if (map[i][j] == 13) {
            g.drawImage(east[this.currentSprite/2],GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          }
        }
      }
    }
    
    //Displaying the large teleporters
    if (questOption >= 4 && questOption <= 6) {
      for (int i = 0; i < otherMap.length; i++) {
        for (int j = 0; j < otherMap[i].length; j++) {
          if (otherMap[i][j] == 1) {
            g.drawImage(teleporter,GridToScreenRatio*j+200,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
          }
        }
      }
    }
    
    //Displaying the large portals based on the quest that the player is on
    if (questOption <= 4) {
      g.drawImage(teleportTopLeft,GridToScreenRatio*9+200,GridToScreenRatio*7,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportTopRight,GridToScreenRatio*10+200,GridToScreenRatio*7,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomLeft,GridToScreenRatio*9+200,GridToScreenRatio*8,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomRight,GridToScreenRatio*10+200,GridToScreenRatio*8,GridToScreenRatio,GridToScreenRatio,null);
    } else if (questOption == 5) {
      g.drawImage(teleportTopLeft,GridToScreenRatio*15+200,GridToScreenRatio*12,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportTopRight,GridToScreenRatio*16+200,GridToScreenRatio*12,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomLeft,GridToScreenRatio*15+200,GridToScreenRatio*13,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomRight,GridToScreenRatio*16+200,GridToScreenRatio*13,GridToScreenRatio,GridToScreenRatio,null);
    } else if (questOption == 6) {
      g.drawImage(teleportTopLeft,GridToScreenRatio*15+200,GridToScreenRatio*2,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportTopRight,GridToScreenRatio*16+200,GridToScreenRatio*2,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomLeft,GridToScreenRatio*15+200,GridToScreenRatio*3,GridToScreenRatio,GridToScreenRatio,null);
      g.drawImage(teleportBottomRight,GridToScreenRatio*16+200,GridToScreenRatio*3,GridToScreenRatio,GridToScreenRatio,null);
    }
    
    //Changing the current sprite image of the conveyor belts
    this.currentSprite = (this.currentSprite + 1) % 20;
  }
}