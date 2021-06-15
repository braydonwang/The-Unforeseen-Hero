//Importing all files
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;

/*
 * [Map.java]
 * Version 1.0
 * This program represents the main map outside of the hub
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Map
* This class holds the design of the map and how to draw it
*/

class Map { 
  int boxWidth;
  int boxHeight;
  int visibleWidth, visibleHeight;
  int playerX, playerY;
  int map[][] = new int[50][50];
  Interactable[][] interactMap = new Interactable[50][50];
  int dirX;
  int dirY;
  int direction;
  int count;
  Monster[][] monsters;
  
  Image grass = Toolkit.getDefaultToolkit().getImage("Grass.png");
  Image boulder = Toolkit.getDefaultToolkit().getImage("Boulder.png");
  Image dirt = Toolkit.getDefaultToolkit().getImage("Dirt.png");
  Image dirtBottomLeft = Toolkit.getDefaultToolkit().getImage("GrassDirtBottomLeft.png");
  Image dirtBottomRight = Toolkit.getDefaultToolkit().getImage("GrassDirtBottomRight.png");
  Image dirtTopLeft = Toolkit.getDefaultToolkit().getImage("GrassDirtTopLeft.png");
  Image dirtTopRight = Toolkit.getDefaultToolkit().getImage("GrassDirtTopRight.png");
  Image rock = Toolkit.getDefaultToolkit().getImage("Rock.png");
  Image hub = Toolkit.getDefaultToolkit().getImage("Hub.png");
  Image chest = Toolkit.getDefaultToolkit().getImage("Chest.png");
  Image openChest = Toolkit.getDefaultToolkit().getImage("OpenChest.png");
  Image wizard = Toolkit.getDefaultToolkit().getImage("Wizard.png");
  Image knight = Toolkit.getDefaultToolkit().getImage("Knight.png");
  
   public Map(int xResolution,int yResolution,int[][] map, Monster[][] monsters) { 
      this.visibleWidth=20;
      this.visibleHeight=16;  
      
      this.boxWidth = 768/visibleHeight;
      this.boxHeight = 768/visibleHeight;
      
      this.playerX = 0;
      this.playerY = 6;
      
     this.map = map;
     this.dirX = 0;
     this.dirY = 0;
     this.count = 0;
     this.monsters = monsters;
     
     interactMap[20][10] = new NPC("Greg");
     interactMap[10][28] = new NPC("Calvin");
     interactMap[38][22] = new NPC("Henry");
     interactMap[39][5] = new NPC("Thomas");
     interactMap[6][42] = new NPC("Aiden");
     interactMap[33][37] = new NPC("Dylan");
     interactMap[14][5] = new NPC("Peter");
   }
   
   /**
   * checkInteract
   * This method checks if the tile around the player is an interactable item or not
   * @param row, the row number
   * @param col, the column number
   * @return the interactable object
   */
   
   public Interactable checkInteract(int row, int col) {
     if (row - 2 >= 0 && interactMap[row-2][col-1] instanceof Interactable && !interactMap[row-2][col-1].getInteracted()) {
       interactMap[row-2][col-1].changeInteracted(true);
       return interactMap[row-2][col-1];
     } else if (row < 50 && interactMap[row][col-1] instanceof Interactable && !interactMap[row][col-1].getInteracted()) {
       interactMap[row][col-1].changeInteracted(true);
       return interactMap[row][col-1];
     } else if (col - 2 >= 0 && interactMap[row-1][col-2] instanceof Interactable && !interactMap[row-1][col-2].getInteracted()) {
       interactMap[row-1][col-2].changeInteracted(true);
       return interactMap[row-1][col-2];
     } else if (col < 50 && interactMap[row-1][col] instanceof Interactable && !interactMap[row-1][col].getInteracted()) {
       interactMap[row-1][col].changeInteracted(true);
       return interactMap[row-1][col];
     } else {
       return null;
     }
   }
   
   /**
   * changeMap
   * This method changes the numbers at coordinates i and j
   * @param i, the row number
   * @param j, the col number
   */
   
   public void changeMap(int i, int j, int change) {
     map[i][j] = change;
   }
   
   /**
   * draw
   * This method displays the map layout to the screen
   * @param g, the graphics variable
   */
   
   public void draw(Graphics g) {
     for (int i = 0; i < map.length; i++) {
       for (int j = 0; j < map[i].length; j++) {
         if (map[i][j]==0 || map[i][j] == 1 || map[i][j] == 5 || map[i][j] == 6 || map[i][j] == 15 || map[i][j] == 11) {
           g.drawImage(grass,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 2 || map[i][j] == 3) {
           g.drawImage(dirt,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 4) {
           g.drawImage(grass,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
           if (interactMap[i][j] == null) {
             interactMap[i][j] = new Chest();
           }
           if (interactMap[i][j].getInteracted()) {
             g.drawImage(openChest,j*boxWidth - dirX - 25,i*boxHeight - dirY - 310,null);
           } else {
             g.drawImage(chest,j*boxWidth - dirX - 25,i*boxHeight - dirY - 310,null);
           }
         } else if (map[i][j] == 7) {
           g.drawImage(dirtBottomLeft,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 8) {
           g.drawImage(dirtBottomRight,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 9) {
           g.drawImage(dirtTopLeft,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 10) {
           g.drawImage(dirtTopRight,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 12) {
           g.drawImage(rock,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 13) {
           g.drawImage(grass,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
         } else if (map[i][j] == 14) {
           g.drawImage(dirt,j*boxWidth - dirX - 33,i*boxHeight - dirY - 321,null);
           g.drawImage(boulder,j*boxWidth - dirX - 10,i*boxHeight - dirY - 305,null);
         }
       }
     }
     
     //Displaying the monsters
     for (int i = 0; i < map.length; i++) {
       for (int j = 0; j < map[i].length; j++) {
         if (monsters[i][j] instanceof Wolf && monsters[i][j].getRow() != -1) {
           monsters[i][j].draw(monsters[i][j].getName(),g,dirX,dirY);
         }
       }
     }
     
     //Displaying the hub
     g.drawImage(hub,290-dirX,-288-dirY,null);
     
     //Moving the map based on the player's controls
     if (this.direction == 1) {
       this.dirY--;
       this.count++;
     } else if (this.direction == 2) {
       this.dirX++;
       this.count++;
     } else if (this.direction == 3) {
       this.dirY++;
       this.count++;
     } else if (this.direction == 4) {
       this.dirX--;
       this.count++;
     }
     
     //Stopping the map movement once a tile has been reached
     if (this.count >= 48) {
       this.direction = 0;
       this.count = 0;
     }
   }
 }