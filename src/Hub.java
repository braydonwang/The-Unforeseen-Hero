//Importing all files
import java.io.File;
import java.util.Scanner;
import java.awt.*;

/*
 * [Hub.java]
 * Version 1.0
 * This program holds all the key components of the main hub
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Hub
* This class accepts a 2D int array as the map and performs all the necessary functions that are located in the hub.
* @param grid, A 2D int array that holds data representing the hub
*/

class Hub {
  //Matrix holds the int array that represents the layout of the hub
  private int[][] matrix = new int[16][30];
  //Represents the size of each tile
  private int boxWidth;
  private int boxHeight;
  
  //Initializing all images
  Image woodFloor = Toolkit.getDefaultToolkit().getImage("WoodFloor.png");
  Image woodWallRight = Toolkit.getDefaultToolkit().getImage("WoodWallRight.png");
  Image woodWallLeft = Toolkit.getDefaultToolkit().getImage("WoodWallLeft.png");
  Image woodWallUp = Toolkit.getDefaultToolkit().getImage("WoodWallUp.png");
  Image woodWallDown = Toolkit.getDefaultToolkit().getImage("WoodWallDown.png");
  Image woodWallTopLeft = Toolkit.getDefaultToolkit().getImage("WoodWallTopLeft.png");
  Image woodWallBottomLeft = Toolkit.getDefaultToolkit().getImage("WoodWallBottomLeft.png");
  Image woodWallTopRight = Toolkit.getDefaultToolkit().getImage("WoodWallTopRight.png");
  Image woodWallBottomRight = Toolkit.getDefaultToolkit().getImage("WoodWallBottomRight.png");
  Image shopRug = Toolkit.getDefaultToolkit().getImage("ShopRug.png");
  Image skillRug = Toolkit.getDefaultToolkit().getImage("SkillRug.png");
  Image fightRug = Toolkit.getDefaultToolkit().getImage("FightRug.png");
  Image dirt = Toolkit.getDefaultToolkit().getImage("Dirt.png");
  
  //Determining the maximum x and y coordinates of the screen
  final int maxY = Toolkit.getDefaultToolkit().getScreenSize().height; 
  final int maxX = Toolkit.getDefaultToolkit().getScreenSize().width; 
  
  Hub(int[][] grid) {
    this.matrix = grid;
    this.boxWidth = 48;
    this.boxHeight = 48;
  }
  
  /**
   * draw 
   * This method accepts a graphic as the parameter and draws the entire design of the hub using previously initiated images.
   * @param g, A Graphics variable that allows the method to draw to the screen
   * @return void
   */

  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,maxX,maxY);
    
    //Displaying the background floor of the hub
    for (int i = 0; i < 8; i++) {
      g.drawImage(woodFloor,i*boxWidth+488,43,null);
    }
    for (int i = 0; i < 8; i++) {
      g.drawImage(woodFloor,i*boxWidth+488,602,null);
    }
    
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == 1) {
          g.drawImage(woodFloor,j*boxWidth-38,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 10) {
          g.drawImage(dirt,j*boxWidth-32,i*(boxWidth-5)+10,null);
        }
      }
    }
    
    //Displaying the outside walls of the hub
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (matrix[i][j] == 2) {
          g.drawImage(woodWallDown,j*boxWidth-38,i*(boxWidth-5)-15,null);
        } else if (matrix[i][j] == 3) {
          g.drawImage(woodWallLeft,j*boxWidth-32,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 4) {
          g.drawImage(woodWallUp,j*boxWidth-38,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 5) {
          g.drawImage(woodWallRight,j*boxWidth-43,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 6) {
          g.drawImage(woodWallBottomLeft,j*boxWidth-38,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 7) {
          g.drawImage(woodWallTopLeft,j*boxWidth-38,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 8) {
          g.drawImage(woodWallTopRight,j*boxWidth-38,i*(boxWidth-5),null);
        } else if (matrix[i][j] == 9) {
          g.drawImage(woodWallBottomRight,j*boxWidth-38,i*(boxWidth-5),null);
        } 
      }
    }
    
    g.drawImage(woodWallBottomRight,570,27,null);
    g.drawImage(woodWallTopRight,570,602,null);
    g.drawImage(woodWallBottomRight,340,240,null);
    g.drawImage(woodWallTopRight,340,400,null);
    g.drawImage(woodWallBottomLeft,750,27,null);
    g.drawImage(woodWallTopLeft,750,602,null);
    g.drawImage(woodWallBottomLeft,975,240,null);
    g.drawImage(woodWallTopLeft,975,400,null);
    
    
    //Displaying the labelled and coloured rugs
    g.drawImage(shopRug,820,300,null);
    g.drawImage(skillRug,430,300,null);
    g.drawImage(fightRug,610,120,null);
  }
}