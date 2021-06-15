//Importing all necessary files
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.util.Scanner;
import java.util.Random;
import java.util.*;
import javax.sound.sampled.*;

/*
 * [FinalSummativeGame.java]
 * Version 1.0
 * This program is the final summative game called "The Unforeseen Hero"
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* FinalSummativeGame 
* This class is the main program that holds all the necessary code in order for the game to work, including movement, attacks,
* interactions and spawning monsters.
*/

class FinalSummativeGame { 
  public static void main(String[] args) throws Exception { 
    //Intializing all files and scanner for input
    File file = new File("map.txt");
    File file2 = new File("Hub.txt");
    File file3 = new File("Conveyor Belt Maze 1.txt");
    File file4 = new File("Conveyor Belt Maze 2.txt");
    File file5 = new File("Conveyor Belt Maze 3.txt");
    File file6 = new File("Teleport Maze.txt");
    File file7 = new File("Portal Overlay 1.txt");
    File file8 = new File("Portal Overlay 2.txt");
    File file9 = new File("Portal Overlay 3.txt");
    File file10 = new File("Flower Puzzle.txt");
    File file11 = new File("Boss Entrance Hallway.txt");
    File file12 = new File("Boss Fight Layout.txt");
    
    Scanner input = new Scanner(file);
    Scanner input2 = new Scanner(file2);
    Scanner input3 = new Scanner(file3);
    Scanner input4 = new Scanner(file4);
    Scanner input5 = new Scanner(file5);
    Scanner input6 = new Scanner(file6);
    Scanner input7 = new Scanner(file7);
    Scanner input8 = new Scanner(file8);
    Scanner input9 = new Scanner(file9);
    Scanner input10 = new Scanner(file10);
    Scanner input11 = new Scanner(file11);
    Scanner input12 = new Scanner(file12);
    
    //Intiliazing all maps and variables
    int[][] map = new int[50][50];
    int[][] hub = new int[16][30];
    int[][] conveyor1 = new int[16][20];
    int[][] conveyor2 = new int[16][20];
    int[][] conveyor3 = new int[16][20];
    int[][] teleport = new int[16][20];
    int[][] teleport1 = new int[16][20];
    int[][] teleport2 = new int[16][20];
    int[][] teleport3 = new int[16][20];
    int[][] flower = new int[16][30];
    int[][] bossEntrance = new int[16][30];
    int[][] bossFight = new int[30][30];
    Monster[][] monsters = new Monster[50][50];
    int row;
    int col;
    
    //Reading from each file and storing it into the map array
    map = readFile(file,input,50,50);
    hub = readFile(file2,input2,16,30);
    conveyor1 = readFile(file3,input3,16,20);
    conveyor2 = readFile(file4,input4,16,20);
    conveyor3 = readFile(file5,input5,16,20);
    teleport = readFile(file6,input6,16,20);
    teleport1 = readFile(file7,input7,16,20);
    teleport2 = readFile(file8,input8,16,20);
    teleport3 = readFile(file9,input9,16,20);
    flower = readFile(file10,input10,16,30);
    bossEntrance = readFile(file11,input11,16,30);
    bossFight = readFile(file12,input12,30,30);
    
    GameWindow game= new GameWindow(map,hub,monsters,conveyor1,conveyor2,conveyor3,teleport,teleport1,teleport2,teleport3,flower,bossEntrance,bossFight);  
    
    int count = 0;
    int numberOfMonsters = 0;
    int MONSTER_SPAWN_RATE = 500;
    sound();
    boolean inMenu = true;
    
    //Spawning the wolves on the map once the spawn rate is reached and the number of wolves does not exceed the maximum limit
    while (true) {
      game.refresh();
      
      count++;

      try{ Thread.sleep(10); }catch(Exception e) {};
      
      //Counting the number of wolves on the map
      numberOfMonsters = 0;
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
          if (monsters[i][j] instanceof Wolf) {
            numberOfMonsters++;
          }
        }
      }
      
      //Spawning it at a random tile that is not occupied by an obstacle or another wolf
      if (count % MONSTER_SPAWN_RATE == 0 && numberOfMonsters <= 10) {
        do {
          row = (int)(Math.random()*50);
          col = (int)(Math.random()*50);
        } while (monsters[row][col] != null || isObstacle(map[row][col]));
        
        int coins = (int)(Math.random()*19)+1;
        monsters[row][col] = new Wolf(row+1,col+1,coins);
      }
    }
  }
  
  /**
   * sound
   * This method plays the background music for the game
   * @return void
   */
  
  public static void sound() {
    try {
      File audioFile = new File("Hackerland.wav");
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
      DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.addLineListener(new SoundListener());
      clip.open(audioStream);
      clip.start();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * SoundListener
   * This class indicates when the sound effect is finished
   * @return void
   */
    
  static class SoundListener implements LineListener {
    public void update(LineEvent event) {
      if (event.getType() == LineEvent.Type.STOP) {
        event.getLine().close();
        sound();
      }
    }
  }
  
  /**
   * isObstacle
   * This method takes in an int value and determines whether the number is an obstacle or not
   * @param x, represents the integer value that is being checked
   * @return true if it is an obstacle, false otherwise
   */
  
  public static boolean isObstacle(int x) {
    if (x == 0 ||  x == 3 || x == 4 || x == 5 || x == 6 || x == 13 || x == 14 || x == 15) {
      return true;
      }
    return false;
  }
  
  /**
   * readFile
   * This method reads the file given the scanner and stores it into a 2D array
   * @param file, represents the file
   * @param input, represents the scanner for input
   * @param row, represents the total number of rows
   * @param col, represents the total number of columns
   * @return a 2D int array that represents the map
   */
  
  public static int[][] readFile(File file, Scanner input, int row, int col) {
    int count = 0;
    int[][] map = new int[row][col];
    while (input.hasNext()) {
      String str = input.nextLine();
      String[] array = str.split(" ");
      for (int i = 0; i < array.length; i++) {
        map[count][i] = Integer.parseInt(array[i]);
      }
      count++;
    }
    return map;
  }
}

//This class represents the game window
class GameWindow extends JFrame { 
  //Initializing all variables
  static int skillOption;
  static MainCharacter player;
  static boolean skill;
  static boolean inventory;
  static boolean tinyMap;
  static int inventoryOption;
  static int quickInventoryOption;
  static Item[] inventoryItems;
  static Shop shop;
  static int questOption;
  static boolean[] skillOptions;
  static int[] switches;
  static String monsterName;
  static int monsterHealth;
  static boolean inMenu;
  static boolean controls;
  static int menuButton;
  
  //Window constructor
  public GameWindow(int[][] grid,int[][] hub,Monster[][] monsters,int[][] conveyor1,int[][] conveyor2,int[][] conveyor3,int[][] teleport,int[][] teleport1,int[][] teleport2,int[][] teleport3,int[][] flower,int[][] bossEntrance,int[][] bossFight) throws Exception{ 
    setTitle("The Unforeseen Hero");
    setSize(1280,1024); 
    setResizable(false); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    getContentPane().add( new GamePanel(grid,hub,monsters,conveyor1,conveyor2,conveyor3,teleport,teleport1,teleport2,teleport3,flower,bossEntrance,bossFight));
    pack(); //makes the frame fit the contents
    setVisible(true);
    skillOption = 0;
    skill = false;
    this.add(new MatrixPanel()); 
    player = new MainCharacter(100,9,16,2,100,"Alucard");
    inventory = false;
    inventoryOption = 0;
    quickInventoryOption = 0;
    inventoryItems = new Item[15];
    shop = new Shop();
    questOption = 0;
    skillOptions = new boolean[15];
    switches = new int[10];
    monsterName = "";
    monsterHealth = 0;
    tinyMap = false;
    inMenu = true;
    controls = false;
    menuButton = 0;
  }
  
  /**
   * refresh
   * This method updates and repaints the entire screen
   * @return void
   */
  
  public void refresh() {
    this.repaint();
  }
  
  /**
   * MatrixPanel
   * This class extends JPanel and intializes the mouse controls
   */
  
  class MatrixPanel extends JPanel { 
   
    MatrixPanel() {  
      addMouseListener(new MatrixPanelMouseListener()); 
      addMouseMotionListener(new MatrixPanelMouseListener()); 
    }
  }


// An inner class representing the panel on which the game takes place
  static class GamePanel extends JPanel implements KeyListener {
  
    //Intializing all variables
    //MovingBox box;
    //FrameRate frameRate;
    Clock clock;
    Map map;
    Hub hub;
    Maze maze;
    Boss boss;
    
    Color LIGHT_BROWN = new Color(222,190,136);
    Color BROWN = new Color(154,119,83);
    Color DARK_BROWN = new Color(38,33,25);
    Color BEIGE = new Color(237,220,160);
    Color GOLD = new Color(239,186,23);
    Color DARK_GREY = new Color(42,39,34);
    Color LIGHT_GREY = new Color(88,96,98);
    Color NAVY = new Color(21,39,43);
    Color LIGHT_NAVY = new Color(71,95,99);
    Color LIGHT_BLUE = new Color(190,226,240);
    Color LIGHT_GREEN = new Color(53,200,0);
    Color DARK_GREEN = new Color(0,100,0);
    Color TURQUOISE = new Color(6,166,164);
    Color LIGHT_RED = new Color(197,104,99);
    Color DARK_RED = new Color(88,0,0);
    Color MINT = new Color(169,207,168);
    
    Image coin2 = Toolkit.getDefaultToolkit().getImage("Coin2.png");
    Image xButton = Toolkit.getDefaultToolkit().getImage("XButton.png");
    Image xButton2 = Toolkit.getDefaultToolkit().getImage("XButton2.png");
    Image backpack = Toolkit.getDefaultToolkit().getImage("Backpack.png");
    Image smallMap = Toolkit.getDefaultToolkit().getImage("CartoonMap.png");
    Image settings = Toolkit.getDefaultToolkit().getImage("Settings.png");
    Image emeraldSword = Toolkit.getDefaultToolkit().getImage("SmallEmeraldSword.png");
    Image rubySword = Toolkit.getDefaultToolkit().getImage("SmallRubySword.png");
    Image sapphireWand = Toolkit.getDefaultToolkit().getImage("SmallSapphireWand.png");
    Image crystalWand = Toolkit.getDefaultToolkit().getImage("SmallCrystalWand.png");
    Image bow = Toolkit.getDefaultToolkit().getImage("SmallBowAndArrow.png");
    Image bomb = Toolkit.getDefaultToolkit().getImage("SmallBomb.png");
    Image helmet = Toolkit.getDefaultToolkit().getImage("SmallHelmet.png");
    Image chestplate = Toolkit.getDefaultToolkit().getImage("SmallChestplate.png");
    Image pants = Toolkit.getDefaultToolkit().getImage("SmallPants.png");
    Image healthPot = Toolkit.getDefaultToolkit().getImage("SmallHealthPot.png");
    Image manaPot = Toolkit.getDefaultToolkit().getImage("SmallManaPot.png");
    Image bloodDrop = Toolkit.getDefaultToolkit().getImage("BloodDrop.png");
    Image waterDrop = Toolkit.getDefaultToolkit().getImage("WaterDrop.png");
    Image shield = Toolkit.getDefaultToolkit().getImage("Shield.png");
    Image health = Toolkit.getDefaultToolkit().getImage("Health.png");
    Image redBlueHeart = Toolkit.getDefaultToolkit().getImage("RedAndBlueHeart.png");
    Image lightPost = Toolkit.getDefaultToolkit().getImage("LightPost.png");
    Image tree = Toolkit.getDefaultToolkit().getImage("Tree.png");
    Image wizard = Toolkit.getDefaultToolkit().getImage("Wizard.png");
    Image knight = Toolkit.getDefaultToolkit().getImage("Knight.png");
    Image farmer = Toolkit.getDefaultToolkit().getImage("Farmer.png");
    Image roughMap = Toolkit.getDefaultToolkit().getImage("SmallMap.png");
    Image shrub = Toolkit.getDefaultToolkit().getImage("Shrub.png");
    Image controls1 = Toolkit.getDefaultToolkit().getImage("Controls1.png");
    Image controls2 = Toolkit.getDefaultToolkit().getImage("Controls2.png");
    Image play1 = Toolkit.getDefaultToolkit().getImage("Play1.png");
    Image play2 = Toolkit.getDefaultToolkit().getImage("Play2.png");
    Image quit1 = Toolkit.getDefaultToolkit().getImage("Quit1.png");
    Image quit2 = Toolkit.getDefaultToolkit().getImage("Quit2.png");
    Image title = Toolkit.getDefaultToolkit().getImage("Title.png");
    Image controlsScreen = Toolkit.getDefaultToolkit().getImage("Controls Screen.png");
    
    boolean inHub;
    boolean done;
    int bossRoom;
    
    Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
    Font  f2  = new Font(Font.SANS_SERIF, Font.BOLD,  10);
    Font  f3  = new Font(Font.SANS_SERIF, Font.BOLD,  45);
    Font  f4  = new Font(Font.SANS_SERIF, Font.BOLD,  20);
    
    int[][] worldMap;
    int[][] hubGrid;
    int[][] conveyorMaze1;
    int[][] conveyorMaze2;
    int[][] conveyorMaze3;
    int[][] teleportMaze;
    int[][] teleportMaze1;
    int[][] teleportMaze2;
    int[][] teleportMaze3;
    int[][] flowerPuzzle;
    Monster[][] monstersMap;
    int[][] bossEntranceMap;
    int[][] bossFightMap;
    
    int originalX;
    int originalY;
    int originalRow;
    int originalCol;
    int[] correctFlower = {1,0,1,0,0,0,0,1,1,0};
    Image[] items = {emeraldSword,rubySword,sapphireWand,crystalWand,bow,bomb,helmet,chestplate,pants,healthPot,manaPot};
    String message;
    
    //constructor
    public GamePanel(int[][] grid, int[][] matrix, Monster[][] monsters, int[][] conveyor1, int[][] conveyor2, int[][] conveyor3, int[][] teleport, int[][] teleport1, int[][] teleport2, int[][] teleport3, int[][] flower, int[][] bossEntrance, int[][] bossFight) { 
      setPreferredSize(new Dimension(1440,768));
      addKeyListener(this);
      setFocusable(true);
      requestFocusInWindow();
      //frameRate = new FrameRate();
      clock = new Clock();
      map = new Map(1024,768,grid,monsters);
      hub = new Hub(matrix);
      monstersMap = monsters;
      worldMap = grid;
      hubGrid = matrix;
      conveyorMaze1 = conveyor1;
      conveyorMaze2 = conveyor2;
      conveyorMaze3 = conveyor3;
      teleportMaze = teleport;
      teleportMaze1 = teleport1;
      teleportMaze2 = teleport2;
      teleportMaze3 = teleport3;
      flowerPuzzle = flower;
      bossEntranceMap = bossEntrance;
      bossFightMap = bossFight;
      boss = new Boss();
      maze = new Maze(teleportMaze);
      inHub = true;
      message = "";
      done = false;
      originalX = 0;
      originalY = 0;
      originalRow = 0;
      originalCol = 0;
      bossRoom = 0;
    }
  
    /**
     * paintComponent
     * This method paints the entire screen
     * @param g, the graphics variable that displays images to the screen
     */
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      //Displaying the main menu
      if (inMenu) {
        g.setColor(LIGHT_BLUE);
        g.fillRect(0,0,1440,768);
        g.drawImage(play1,463,320,514,102,null);
        g.drawImage(controls1,463,446,514,102,null);
        g.drawImage(quit1,463,572,514,102,null);
        if (menuButton == 1) {
          g.drawImage(play2,463,320,514,102,null);
        } else if (menuButton == 2) {
          g.drawImage(controls2,463,446,514,102,null);
        } else if (menuButton == 3) {
          g.drawImage(quit2,463,572,514,102,null);
        }
        g.drawImage(title,325,25,790,270,null);
        
        //Displaying the controls screen
        if (controls) {
          g.drawImage(controlsScreen,0,0,1440,768,null);
        }
      } else {
        //Updating the clock with the current elapsed time
        clock.update();
        
        //Updating player regen time and attack cooldown time
        if (player != null && clock != null) {
          player.updateRegen(clock.getElapsedTime());
          player.updateAttackCD(clock.getElapsedTime());
        }
        
        //Updating boss attack cooldown time
        if (bossRoom == 2) {
          boss.updateAttackCD(clock.getElapsedTime());
          if (boss.getAttackCD() >= 8) {
            boss.attack(bossFightMap);
            boss.restartAttackCD();
          }
        }
        
        //Displaying the hub
        if (inHub && bossRoom == 0) {
          hub.draw(g);
          if (player != null && player.getRow() >= 1 && player.getRow() <= 16 && player.getCol() >= 1 && player.getCol() <= 30 && hubGrid[player.getRow()-1][player.getCol()-1] == 11) {
            if (player.getRow() == 15) {
              //Player exits the hub
              player.xCoordinate = 685;
              player.yCoordinate = 320;
              player.setRow(16);
              player.setCol(16);
              map.direction = 0;
              inHub = false;
              door();
            } else if (player.getCol() == 22) {
              //Player enters the shop
              if (!shop.getShop()) {
                door();
              }
              shop.changeShop(true);
            } else if (player.getCol() == 9) {
              //Player enters the skill tree
              if (!skill) {
                door();
              }
              skill = true;
            } else if (player.getRow() == 2) {
              if (player.getLevel() >= 4) {
                //Player enters the boss arena
                door();
                changePosition(15,16,695,640);
                bossRoom = 1;
              } else {
                message = "Must complete atleast 3 quests to fight the boss!";
                clock.restart();
              }
            }
          }
          //Displaying the map
        } else if (questOption == 0 && bossRoom == 0) {
          map.draw(g);
          if (player.getRow() == 15 && (player.getCol() == 16 || player.getCol() == 17)) {
            //Player enters the hub
            player.xCoordinate = 685;
            player.yCoordinate = 555;
            map.dirX = 0;
            map.dirY = 0;
            map.playerX = 0;
            map.playerY = 6;
            player.setRow(14);
            player.setCol(16);
            inHub = true;
            door();
          } else if (player.getRow() == 1 && player.getCol() >= 15 && player.getCol() <= 18) {
            //Player enters quest number 7
            questOption = 7;
          }
        }
        
        //Displaying the map of each quest
        if (questOption != 0) {
          int[][] matrix = new int[16][20];
          if (questOption == 1) {
            maze.draw(g,conveyorMaze1,questOption);
            matrix = conveyorMaze1;
          } else if (questOption == 2) {
            maze.draw(g,conveyorMaze2,questOption);
            matrix = conveyorMaze2;
          } else if (questOption == 3) {
            maze.draw(g,conveyorMaze3,questOption);
            matrix = conveyorMaze3;
          } else if (questOption == 4) {
            maze.draw(g,teleportMaze1,questOption);
            matrix = teleportMaze1;
          } else if (questOption == 5) {
            maze.draw(g,teleportMaze2,questOption);
            matrix = teleportMaze2;
          } else if (questOption == 6) {
            maze.draw(g,teleportMaze3,questOption);
            matrix = teleportMaze3;
          } else if (questOption == 7) {
            maze.draw(g,flowerPuzzle,questOption);
            matrix = flowerPuzzle;
            maze.changeSwitch(switches);
            if (Arrays.equals(switches,correctFlower)) {
              flowerPuzzle[15][13] = 0;
              flowerPuzzle[15][14] = 0;
              message = "You have completed the flower puzzle. Exit to gain a skill point!";
              clock.restart();
            } else {
              flowerPuzzle[15][13] = 8;
              flowerPuzzle[15][14] = 8;
            }
          }
          //Changing the position of the player based on each quest
          if (!done) {
            originalX = player.xCoordinate;
            originalY = player.yCoordinate;
            originalRow = player.getRow();
            originalCol = player.getCol();
            done = true;
            if (questOption <= 3) {
              changePosition(8,2,230,280);
            } else if (questOption == 4) {
              changePosition(15,11,680,630);
            } else if (questOption == 5) {
              changePosition(8,9,580,280);
            } else if (questOption == 6) {
              changePosition(13,15,880,530);
            } else if (questOption == 7) {
              changePosition(15,15,650,610);
            }
          }
          
          //Player completes one of the quests
          if (questOption <= 4 && player.getCol() >= 10 && player.getCol() <= 11 && player.getRow() >= 8 && player.getRow() <= 9) {
            questOption = 0;
            done = false;
            changePosition(originalRow,originalCol,originalX,originalY);
            player.changeSkillPoints(player.getSkillPoints()+1);
            player.increaseLevel();
            player.changeMotion(false);
            player.setCoins(player.getCoins()+100);
            victory();
            return;
          } else if (questOption == 5 && player.getCol() >= 16 && player.getCol() <= 17 && player.getRow() >= 13 && player.getRow() <= 14) {
            questOption = 0;
            done = false;
            changePosition(originalRow,originalCol,originalX,originalY);
            player.changeSkillPoints(player.getSkillPoints()+1);
            player.increaseLevel();
            player.changeMotion(false);
            player.setCoins(player.getCoins()+100);
            victory();
            return;
          } else if (questOption == 6 && player.getCol() >= 16 && player.getCol() <= 17 && player.getRow() >= 3 && player.getRow() <= 4) {
            questOption = 0;
            done = false;
            changePosition(originalRow,originalCol,originalX,originalY);
            player.changeSkillPoints(player.getSkillPoints()+1);
            player.increaseLevel();
            player.changeMotion(false);
            player.setCoins(player.getCoins()+100);
            victory();
            return;
          } else if (questOption == 7 && player.getCol() >= 14 && player.getCol() <= 15 && player.getRow() == 16 && flowerPuzzle[player.getRow()-1][player.getCol()-1] == 0) {
            questOption = 0;
            done = false;
            changePosition(originalRow+1,originalCol,originalX,originalY);
            player.changeSkillPoints(player.getSkillPoints()+1);
            player.increaseLevel();
            player.changeMotion(false);
            map.changeMap(0,14,0);
            map.changeMap(0,15,0);
            map.changeMap(0,16,0);
            map.changeMap(0,17,0);
            player.setCoins(player.getCoins()+100);
            victory();
            return;
          }
          
          //Teleportation maze and the portals that each of them travels to
          if (questOption == 4) {
            if (player.getRow() == 3 && player.getCol() == 4) {
              changePosition(8,4,330,280);
            } else if (player.getRow() == 4 && player.getCol() == 4) {
              changePosition(3,15,880,30);
            } else if (player.getRow() == 3 && player.getCol() == 10) {
              changePosition(13,15,880,530);
            } else if (player.getRow() == 4 && player.getCol() == 9) {
              changePosition(8,15,880,280);
            } else if (player.getRow() == 3 && player.getCol() == 17) {
              changePosition(8,15,880,280);
            } else if (player.getRow() == 5 && player.getCol() == 18) {
              changePosition(13,9,580,530);
            } else if (player.getRow() == 8 && player.getCol() == 3) {
              changePosition(8,15,880,280);
            } else if (player.getRow() == 10 && player.getCol() == 3) {
              changePosition(13,3,280,530);
            } else if (player.getRow() == 8 && player.getCol() == 18) {
              changePosition(13,15,880,530);
            } else if (player.getRow() == 10 && player.getCol() == 18) {
              changePosition(3,9,580,30);
            } else if (player.getRow() == 14 && player.getCol() == 3) {
              changePosition(8,9,580,280);
            } else if (player.getRow() == 15 && player.getCol() == 4) {
              changePosition(3,9,580,30);
            } else if (player.getRow() == 14 && player.getCol() == 11) {
              changePosition(8,4,330,280);
            } else if (player.getRow() == 14 && player.getCol() == 12) {
              changePosition(13,15,880,530);
            } else if (player.getRow() == 14 && player.getCol() == 16) {
              changePosition(3,15,880,30);
            } else if (player.getRow() == 15 && player.getCol() == 15) {
              changePosition(3,3,280,30);
            } 
          } else if (questOption == 5) {
            if (player.getRow() == 3 && player.getCol() == 5) {
              changePosition(14,3,280,580);
            } else if (player.getRow() == 4 && player.getCol() == 5) {
              changePosition(13,9,580,530);
            } else if (player.getRow() == 3 && player.getCol() == 10) {
              changePosition(8,15,880,280);
            } else if (player.getRow() == 5 && player.getCol() == 12) {
              changePosition(3,3,280,30);
            } else if (player.getRow() == 4 && player.getCol() == 15) {
              changePosition(8,9,580,280);
            } else if (player.getRow() == 4 && player.getCol() == 17) {
              changePosition(3,9,580,30);
            } else if (player.getRow() == 8 && player.getCol() == 4) {
              changePosition(3,15,880,30);
            } else if (player.getRow() == 10 && player.getCol() == 3) {
              changePosition(13,15,880,530);
            } else if (player.getRow() == 9 && player.getCol() == 11) {
              changePosition(3,9,580,30);
            } else if (player.getRow() == 10 && player.getCol() == 9) {
              changePosition(13,9,580,530);
            } else if (player.getRow() == 9 && player.getCol() == 16) {
              changePosition(8,3,280,280);
            } else if (player.getRow() == 10 && player.getCol() == 18) {
              changePosition(3,15,880,30);
            } else if (player.getRow() == 13 && player.getCol() == 3) {
              changePosition(8,9,580,280);
            } else if (player.getRow() == 13 && player.getCol() == 4) {
              changePosition(3,9,580,30);
            } else if (player.getRow() == 13 && player.getCol() == 10) {
              changePosition(3,3,280,30);
            } else if (player.getRow() == 15 && player.getCol() == 11) {
              changePosition(8,15,880,280);
            }
          } else if (questOption == 6) {
            if (player.getRow() == 3 && player.getCol() == 4) {
              changePosition(4,9,580,80);
            } else if (player.getRow() == 3 && player.getCol() == 5) {
              changePosition(8,3,280,280);
            } else if (player.getRow() == 3 && player.getCol() == 9) {
              changePosition(13,9,580,530);
            } else if (player.getRow() == 3 && player.getCol() == 10) {
              changePosition(8,15,880,280);
            } else if (player.getRow() == 8 && player.getCol() == 5) {
              changePosition(3,3,280,30);
            } else if (player.getRow() == 9 && player.getCol() == 6) {
              changePosition(8,9,580,280);
            } else if (player.getRow() == 8 && player.getCol() == 10) {
              changePosition(8,3,280,280);
            } else if (player.getRow() == 10 && player.getCol() == 9) {
              changePosition(13,9,580,530);
            } else if (player.getRow() == 8 && player.getCol() == 16) {
              changePosition(3,3,280,30);
            } else if (player.getRow() == 9 && player.getCol() == 15) {
              changePosition(3,15,880,30);
            } else if (player.getRow() == 13 && player.getCol() == 3) {
              changePosition(8,3,280,280);
            } else if (player.getRow() == 15 && player.getCol() == 4) {
              changePosition(8,9,580,280);
            } else if (player.getRow() == 13 && player.getCol() == 10) {
              changePosition(4,9,580,80);
            } else if (player.getRow() == 14 && player.getCol() == 10) {
              changePosition(13,15,880,530);
            } else if (player.getRow() == 14 && player.getCol() == 15) {
              changePosition(4,9,580,80);
            } else if (player.getRow() == 15 && player.getCol() == 16) {
              changePosition(14,3,280,580);
            }
          } 
          //Moving the player on the conveyor
          if (player.getRow() >= 1 && player.getRow() <= 16 && player.getCol() >= 1 && player.getCol() <= 20) {
            if (matrix[player.getRow()-1][player.getCol()-1] == 12 && !player.getMotion()) {
              player.moveLeft();
              player.changeMotion(true);
            } else if (matrix[player.getRow()-1][player.getCol()-1] == 11 && !player.getMotion()) {
              player.moveDown();
              player.changeMotion(true);
            } else if (matrix[player.getRow()-1][player.getCol()-1] == 13 && !player.getMotion()) {
              player.moveRight();
              player.changeMotion(true);
            } else if (matrix[player.getRow()-1][player.getCol()-1] == 10 && !player.getMotion()) {
              player.moveUp();
              player.changeMotion(true);
            }
          }
          //Displaying the hallway before the boss fight
        } else if (bossRoom == 1) {
          boss.draw(g,bossEntranceMap,bossRoom,player,clock);
          if (player.getRow() == 16) {
            bossRoom = 0;
            door();
            changePosition(3,16,685,27);
            player.moveDown();
            player.changeMotion(true);
          } else if (player.getRow() == 1) {
            changePosition(28,16,685,565);
            bossRoom = 2;
          }
          //Displaying the final boss arena
        } else if (bossRoom == 2) {
          boss.draw(g,bossFightMap,bossRoom,player,clock);
        }
        
        //Changing the sprite image of the player
        if (map.direction != 0) {
          if (map.count % 8 == 0) {
            player.changeCurrentSprite((player.getCurrentSprite()+1)%4);
          }
        }
        if (boss.direction != 0) {
          if (boss.count % 8 == 0) {
            player.changeCurrentSprite((player.getCurrentSprite()+1)%4);
          }
        }
        
        //Changing the sprite image of the player and the direction count of its movement
        if (player != null && player.getMotion()) {
          player.changeDirectionCount(player.getDirectionCount()+1);
          if (player.getDirectionCount() % 8 == 0) {
            player.changeCurrentSprite((player.getCurrentSprite()+1)%4);
          }
          if (questOption == 0 && bossRoom != 2) {
            if (player.getDirectionCount() >= 48) {
              player.changeMotion(false);
              player.changeDirectionCount(0);
            }
          } else if (bossRoom == 2) {
            if (player.getDirectionCount() >= 50) {
              player.changeMotion(false);
              player.changeDirectionCount(0);
            }
          } else {
            if (player.getDirectionCount() >= 50) {
              player.changeMotion(false);
              player.changeDirectionCount(0);
              if (questOption >= 4 && questOption <= 6) {
                if (player.getDirection() == 1) {
                  player.setRow(player.getRow()-1);
                } else if (player.getDirection() == 2) {
                  player.setCol(player.getCol()+1);
                } else if (player.getDirection() == 3) {
                  player.setRow(player.getRow()+1);
                } else if (player.getDirection() == 4) {
                  player.setCol(player.getCol()-1);
                }
              }
            }
          }
          //Changing the attacking motion of the player once attack cooldown timer runs out
        } else if (player != null && player.getAttack()) {
          if (player.getAttackCD() >= 1) {
            player.changeAttack(false);
          } 
        }
        
        //Drawing the overlay of the map that represents the foreground
        if (player != null) {
          int tile = worldMap[player.getRow()][player.getCol()-1];
          if (tile == 13 || tile == 0 || tile == 5 || tile == 6 || tile == 15 || worldMap[player.getRow()-1][player.getCol()-1] == 11) {
            player.draw(g);
            if (!inHub && questOption == 0) {
              drawOverlay(g);
            }
          } else {
            if (!inHub && questOption == 0) {
              drawOverlay(g);
            }
            player.draw(g);
          }
        }
        
        //Displaying the hub
        drawHUD(g);
        
        //Displaying the message that shows up on the screen
        g.setColor(GOLD);
        g.setFont(f1);
        if (message != "" && clock.getTotalTime() <= 2) {
          g.drawString(message,530,460);
        }
        
        //Changing the number of coins in the shop
        shop.changeCoins(player.getCoins());
        
        //Displaying the inventory and the map
        if (inventory) {
          drawInventory(g);
        } else if (tinyMap) {
          drawMap(g);
        }
        
        //Spawning the player back in the hub and reducing their coins when they die
        if (player.getHealth() <= 0) {
          changePosition(9,16,685,320);
          inHub = true;
          player.setCoins(player.getCoins()/2);
          player.changeHealth(player.getBaseHealth());
          player.changeMana(player.getBaseMana());
          player.changeMotion(false);
          map.dirX = 0;
          map.dirY = 0;
          map.playerX = 0;
          map.playerY = 6;
          bossRoom = 0; 
        }
        
        //Displaying the shop and the skill tree
        if (shop.getShop()) {
          shop.draw(g);
        } else if (skill) {
          drawSkill(g);
        }
        
        //Moving the monsters around the map
        if (!inHub) {
          for (int i = 0; i < worldMap.length; i++) {
            for (int j = 0; j < worldMap[i].length; j++) {
              int direction = (int)(Math.random()*5);
              if (monstersMap[i][j] instanceof Wolf && questOption == 0 && monstersMap[i][j].getRow() != -1) {
                monstersMap[i][j].updateAttackCD(clock.getElapsedTime());
                monstersMap[i][j].updateMovementSpeed(clock.getElapsedTime());
                if (monstersMap[i][j].getMotion()) {
                  if (monstersMap[i][j].getDirectionCount() % 8 == 0) {
                    monstersMap[i][j].changeCurrentSprite((monstersMap[i][j].getCurrentSprite()+1)%4);
                  }
                  monstersMap[i][j].changeDirectionCount(monstersMap[i][j].getDirectionCount()+1);
                  if (monstersMap[i][j].getDirectionCount() >= 50) {
                    monstersMap[i][j].changeMotion(false);
                    monstersMap[i][j].changeDirectionCount(0);
                    if (monstersMap[i][j].getDirection() == 1) {
                      monstersMap[i-1][j] = monstersMap[i][j];
                      monstersMap[i][j] = null;
                    } else if (monstersMap[i][j].getDirection() == 2) {
                      monstersMap[i][j+1] = monstersMap[i][j];
                      monstersMap[i][j] = null;
                    } else if (monstersMap[i][j].getDirection() == 3) {
                      monstersMap[i+1][j] = monstersMap[i][j];
                      monstersMap[i][j] = null;
                    } else if (monstersMap[i][j].getDirection() == 4) {
                      monstersMap[i][j-1] = monstersMap[i][j];
                      monstersMap[i][j] = null;
                    }
                    if (monstersMap[player.getRow()-1][player.getCol()-1] instanceof Wolf && monstersMap[player.getRow()-1][player.getCol()-1].getAttackCD() >= 1) {
                      if (monstersMap[player.getRow()-1][player.getCol()-1].getRow() != -1) {
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
                        monstersMap[player.getRow()-1][player.getCol()-1].restartAttackCD();
                      }
                    }
                  }
                  //If the player walks into the radius of the monster, they immediately chase them down
                } else if (Math.abs(player.getRow() - i) + Math.abs(player.getCol() - j) <= 4) {
                  monstersMap[i][j].changeSpeed(1);
                  if (Math.abs((player.getRow() - i)) > Math.abs((player.getCol() - j-1))) {
                    if ((player.getRow() - i) < 0 && !isObstacle(worldMap[i-1][j]) && monstersMap[i-1][j] == null) {
                      monstersMap[i][j].moveUp();
                      monstersMap[i-1][j] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i][j].restartMovementSpeed();
                    } else if (!isObstacle(worldMap[i+1][j])&& monstersMap[i+1][j] == null) {
                      monstersMap[i][j].moveDown();
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i+1][j] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].restartMovementSpeed();
                    }
                  } else if (Math.abs((player.getRow() - i)) < Math.abs((player.getCol() - j - 1))) {
                    if ((player.getCol() - j - 1) < 0 && !isObstacle(worldMap[i][j-1]) && monstersMap[i][j-1] == null) {
                      monstersMap[i][j].moveLeft();
                      monstersMap[i][j-1] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i][j].restartMovementSpeed();
                    } else if (!isObstacle(worldMap[i][j+1])&& monstersMap[i][j+1] == null) {
                      monstersMap[i][j].moveRight();
                      monstersMap[i][j+1] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i][j].restartMovementSpeed();
                    }
                  } else {
                    if (player.getRow() < i + 1 && !isObstacle(worldMap[i-1][j]) && monstersMap[i-1][j] == null) {
                      monstersMap[i][j].moveUp();
                      monstersMap[i-1][j] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i][j].restartMovementSpeed();
                    } else if (!isObstacle(worldMap[i+1][j])&& monstersMap[i+1][j] == null) {
                      monstersMap[i][j].moveDown();
                      monstersMap[i+1][j] = new Wolf(-1,-1,-1);
                      monstersMap[i][j].changeMotion(true);
                      monstersMap[i][j].restartMovementSpeed();
                    }
                  }
                  //Monster moves randomly
                } else if (monstersMap[i][j].getMovementSpeed() >= monstersMap[i][j].getSpeed()) {
                  monstersMap[i][j].changeSpeed(2);
                  if (direction == 0 && monstersMap[i][j].getRow() > 1 && i - 1 >= 0 && !isObstacle(worldMap[i-1][j]) && monstersMap[i-1][j] == null) {
                    monstersMap[i][j].moveUp();
                    monstersMap[i][j].changeMotion(true);
                    monstersMap[i][j].restartMovementSpeed();
                  } else if (direction == 1 && monstersMap[i][j].getCol() < worldMap[0].length && j + 1 < worldMap.length && !isObstacle(worldMap[i][j+1]) && monstersMap[i][j+1] == null) {
                    monstersMap[i][j].moveRight();
                    monstersMap[i][j].changeMotion(true);
                    monstersMap[i][j].restartMovementSpeed();
                  } else if (direction == 2 && monstersMap[i][j].getRow() < worldMap.length && i + 1 < worldMap.length && !isObstacle(worldMap[i+1][j]) && monstersMap[i+1][j] == null) {
                    monstersMap[i][j].moveDown();
                    monstersMap[i][j].changeMotion(true);
                    monstersMap[i][j].restartMovementSpeed();
                  } else if (direction == 3 && monstersMap[i][j].getCol() > 1 && j - 1 >= 0 && !isObstacle(worldMap[i][j-1]) && monstersMap[i][j-1] == null) {
                    monstersMap[i][j].moveLeft();
                    monstersMap[i][j].changeMotion(true);
                    monstersMap[i][j].restartMovementSpeed();
                  }
                }
              }
            }
          }
        }
        
        //Get the health and name of the boss
        if (bossRoom == 2) {
          monsterName = boss.getName();
          monsterHealth = boss.getHealth();
          
          //End the game once the boss is defeated
          if (boss.getBossHealth() <= 0) {
            System.exit(0);
          }
        }
        
        //Checking for collision with the projectiles and obstacles/monsters
        if ((player.getBulletX() != 0 || player.getBulletY() != 0) && !player.getCollision() && bossRoom != 2) {
          if (player.getRow()-1+(player.getBulletY()/48) >= 0 && player.getRow()-1+(player.getBulletY()/48) <= 49 && 
              player.getCol()-1+(player.getBulletX()/48) >= 0 && player.getCol()-1+(player.getBulletX()/48) <= 49 && 
              isObstacle(worldMap[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)])) {
            player.changeCollision(true);
          } else if (player.getRow()-1+(player.getBulletY()/48) >= 0 && player.getRow()-1+(player.getBulletY()/48) <= 49 && 
                     player.getCol()-1+(player.getBulletX()/48) >= 0 && player.getCol()-1+(player.getBulletX()/48) <= 49 && 
                     monstersMap[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)] instanceof Wolf) {
            Wolf wolf = (Wolf)(monstersMap[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)]);
            if (wolf.getRow() != -1) {
              player.changeCollision(true);
              monsterName = wolf.getName();
              if (player.getWeapon() == 6) {
                wolf.changeHealth(wolf.getHealth()-30-player.getBaseDamage());
              } else if (player.getWeapon() == 7) {
                wolf.changeHealth(wolf.getHealth()-60-player.getBaseDamage());
              } else if (player.getWeapon() == 8) {
                wolf.changeHealth(wolf.getHealth()-10-player.getBaseDamage());
              } else if (player.getWeapon() == 9) {
                wolf.changeHealth(wolf.getHealth()-80-player.getBaseDamage());
              }
              if (wolf.getHealth() <= 0) {
                monstersMap[player.getRow()-1+(player.getBulletY()/48)][player.getCol()-1+(player.getBulletX()/48)] = null;
                player.setCoins(player.getCoins()+wolf.getCoins());
                message = "You received " + wolf.getCoins() + " coins!";
                clock.restart();
                monsterName = "";
              } else {
                monsterHealth = wolf.getHealth();
              }
              player.changeBulletX(0);
              player.changeBulletY(0);
            }
          }
        }
        
        //Regenerate health over time if the player is out of combat
        if (player.getHealthRegen() >= 1) {
          if (player.getHealth() <= player.getBaseHealth()-player.getBaseRegen()) {
            player.changeHealth(player.getHealth()+player.getBaseRegen());
          } else {
            player.changeHealth(player.getBaseHealth());
          }
          player.restartHealth();
        }
        if (player.getManaRegen() >= 1) {
          if (player.getMana() <= player.getBaseMana()-player.getBaseRegen()) {
            player.changeMana(player.getMana()+player.getBaseRegen());
          } else {
            player.changeMana(player.getBaseMana());
          }
          player.restartMana();
        }
      }
    }
    
    /**
   * drawOverlay
   * This method displays the foreground of the map
   * @return void
   */
    
    public void drawOverlay(Graphics g) {
      for (int i = 0; i < worldMap.length; i++) {
        for (int j = 0; j < worldMap[i].length; j++) {
          if (worldMap[i][j] == 0) {
            g.drawImage(tree,j*map.boxWidth - map.dirX - 25,i*map.boxHeight - map.dirY - 321,null);
          } else if (worldMap[i][j] == 13) {
            g.drawImage(lightPost,j*map.boxWidth - map.dirX - 62,i*map.boxHeight - map.dirY - 365,null);
          } else if (worldMap[i][j] == 5) {
            g.drawImage(wizard,j*map.boxWidth - map.dirX - 33,i*map.boxHeight - map.dirY - 355,null);
          } else if (worldMap[i][j] == 6) {
            g.drawImage(knight,j*map.boxWidth - map.dirX - 23,i*map.boxHeight - map.dirY - 355,null);
          } else if (worldMap[i][j] == 15) {
            g.drawImage(farmer,j*map.boxWidth - map.dirX - 23,i*map.boxHeight - map.dirY - 345,null);
          } else if (worldMap[i][j] == 11) {
            g.drawImage(shrub,j*map.boxWidth - map.dirX - 33,i*map.boxHeight - map.dirY - 321,null);
          }
        }
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
   * door
   * This method plays the sound effect when a door is being opened
   * @return void
   */
    
    public static void door() {
      try {
        File audioFile = new File("Door.wav");
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
   * victory
   * This method plays the sound effect when the player has completed a quest
   * @return void
   */
    
    public static void victory() {
      try {
        File audioFile = new File("Victory.wav");
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
    
    /**
   * drawMap
   * This method displays the overall map layout
   * @param g, the graphics variable that displays images to the screen
   */
    
    public void drawMap(Graphics g) {
      g.setColor(DARK_BROWN);
      g.fillRect(510,250,450,350);
      g.setColor(BROWN);
      g.fillRect(820,300,120,280);
      g.drawRect(510,250,450,350);
      g.drawImage(roughMap,520,300,null);
      g.setColor(LIGHT_BROWN);
      g.setFont(f4);
      g.drawString("MAP",700,280);
      g.drawImage(xButton2,935,250,null);
      g.setColor(Color.YELLOW);
      g.fillRect(835,320,30,30);
      g.setColor(Color.RED);
      g.fillRect(835,370,30,30);
      g.setColor(Color.WHITE);
      g.setFont(f1);
      g.drawString("= NPC", 875,340);
      g.drawString("= Chest", 870,390);
    }
    
    /**
   * changePosition
   * This method changes the position of the player
   * @param row, the new row
   * @param col, the new col
   * @param x, the new x coordinate
   * @param y, the new y coordinate
   */
    
    public void changePosition(int row, int col, int x, int y) {
      player.setRow(row);
      player.setCol(col);
      player.xCoordinate = x;
      player.yCoordinate = y;
      player.changeMotion(false);
    }
    
    /**
   * drawHUD
   * This method displays the overall HUD of the game, including health bar, inventory, etc.
   * @param g, the graphics variable that displays images to the screen
   */
    
    public void drawHUD(Graphics g) {
      //Displaying the health bar, armor bar, and mana bar
      if (questOption != 7) {
        g.setColor(LIGHT_BROWN);
        g.fillRect(10,10,300,130);
        g.setColor(BROWN);
        g.fillRect(20,20,280,25);
        g.setColor(BEIGE);
        g.fillRect(20,55,280,20);
        g.fillRect(20,82,280,20);
        g.fillRect(20,109,280,20);
        g.setColor(Color.WHITE);
        g.setFont(f1);
        g.drawString(player.getName(),30,38);
        g.drawString("Lvl. " + player.getLevel(),250,38);
        g.setColor(Color.RED);
        g.fillRect(20,55,(int)(280/(player.getBaseHealth()*1.0/player.getHealth())),20);
        g.setColor(GOLD);
        g.fillRect(20,82,(int)(280/(player.getBaseArmour()*1.0/player.getArmour())),20);
        g.setColor(Color.BLUE);
        g.fillRect(20,109,(int)(280/(player.getBaseMana()*1.0/player.getMana())),20);
        g.setColor(Color.WHITE);
        g.setFont(f2);
        g.drawString(player.getHealth() + "/" + player.getBaseHealth(),28,70);
        g.drawString(player.getArmour() + "/" + player.getBaseArmour(),28,97);
        g.drawString(player.getMana() + "/" + player.getBaseMana(),28,124);
        
        g.setColor(DARK_BROWN);
        g.drawRect(10,10,300,130);
        g.drawRect(20,20,280,25);
        g.drawRect(20,55,280,20);
        g.drawRect(20,82,280,20);
        g.drawRect(20,109,280,20);
      }
      g.setColor(LIGHT_BROWN);
      g.fillRect(1150,10,290,80);
      g.setColor(BROWN);
      g.fillRect(1160,20,270,30);
      g.setColor(BEIGE);
      g.fillRect(1160,60,270,20);
      
      g.setColor(Color.WHITE);
      g.setFont(f1);
      g.drawString(monsterName,1170,40);
      
      if (monsterName != "") {
        g.setColor(Color.RED);
        if (monsterName.equals("Boss")) {
          g.fillRect(1160,60,(int)(270/(500*1.0/monsterHealth)),20);
        } else {
          g.fillRect(1160,60,(int)(270/(120*1.0/monsterHealth)),20);
        }
        g.setColor(Color.WHITE);
        g.setFont(f2);
        if (monsterName.equals("Boss")) {
          g.drawString(monsterHealth + "/500",1170,75);
        } else {
          g.drawString(monsterHealth + "/120",1170,75);
        }
      }
      
      g.setColor(DARK_BROWN);
      g.drawRect(1150,10,290,80);
      g.drawRect(1160,20,270,30);
      g.drawRect(1160,60,270,20);
      
      //Displaying the inventory slot
      if (questOption == 0) {
        g.setColor(LIGHT_BROWN);
        g.fillRect(500,708,470,60);
        g.setColor(BROWN);
        g.fillRect(505,713,460,50);
        g.setColor(DARK_BROWN);
        g.drawRect(500,708,470,60);
        g.drawRect(505,713,460,50);
        g.setColor(GOLD);
        if (quickInventoryOption != 0) {
          g.fillRect(512 + (quickInventoryOption-1)*50,715,46,46);
        }
        g.setColor(BEIGE);
        for (int i = 515, cnt = 0; i < 960; i += 50, cnt++) {
          g.fillRect(i,718,40,40);
          if (inventoryItems[cnt] != null) {
            g.drawImage(items[inventoryItems[cnt].getId()-1],i-8,710,null);
          }
        }
        g.setColor(DARK_BROWN);
        for (int i = 515; i < 960; i += 50) {
          g.drawRect(i,718,40,40);
        }
      }
      
      //Displaying the task menu bar
      g.setColor(LIGHT_BROWN);
      g.fillRect(1150,672,290,95);
      g.setColor(BROWN);
      g.fillRect(1170,682,250,75);
      g.setColor(LIGHT_BROWN);
      g.fillRect(1195,692,55,55);
      g.fillRect(1265,692,55,55);
      g.fillRect(1335,692,55,55);
      g.setColor(DARK_BROWN);
      g.drawRect(1150,672,290,95);
      g.drawRect(1170,682,250,75);
      g.drawRect(1195,692,55,55);
      g.drawRect(1265,692,55,55);
      g.drawRect(1335,692,55,55);
      g.drawImage(backpack,1197,694,null);
      g.drawImage(smallMap,1270,698,null);
      g.drawImage(settings,1340,696,null);
    }
    
    /**
   * drawInventory
   * This method displays the inventory
   * @param g, the graphics variable that displays images to the screen
   */
    
    public void drawInventory(Graphics g) {
      int[] xCoordinates = {555,630,705,780,855,555,630,705,780,855,555,630,705,780,855};
      int[] yCoordinates = {360,360,360,360,360,430,430,430,430,430,500,500,500,500,500};
      g.setColor(DARK_BROWN);
      g.fillRect(510,250,450,350);
      g.setColor(BROWN);
      g.fillRect(530,350,410,230);
      g.drawRect(510,250,450,350);
      g.setColor(GOLD);
      if (inventoryOption != 0) {
        g.fillRect(xCoordinates[inventoryOption-1],yCoordinates[inventoryOption-1],65,65);
      }
      g.setColor(LIGHT_BROWN);
      g.setFont(f4);
      g.drawString("INVENTORY",670,290);
      g.drawRect(530,350,410,230);
      for (int i = 365; i <= 505; i+=70) {
        for (int j = 560; j <= 860; j+=75) {
          g.fillRect(j,i,55,55);
        }
      }
      g.setColor(DARK_BROWN);
      for (int i = 365; i <= 505; i+=70) {
        for (int j = 560; j <= 860; j+=75) {
          g.drawRect(j,i,55,55);
        }
      }
      g.setColor(LIGHT_GREEN);
      g.fillRect(770,305,80,30);
      g.setColor(DARK_GREEN);
      g.drawRect(770,305,80,30);
      g.setColor(Color.RED);
      g.fillRect(865,305,80,30);
      g.setColor(DARK_RED);
      g.drawRect(865,305,80,30);
      g.setColor(Color.BLACK);
      g.setFont(f1);
      g.drawString("USE",795,325);
      g.drawString("SELL",890,325);
      g.drawImage(coin2,530,305,null);
      g.setColor(Color.WHITE);
      g.drawString(player.getCoins() + " coins",570,330);
      g.drawImage(xButton2,935,250,null);
      
      for (int i = 365,count = 0; i <= 505; i+=70) {
        for (int j = 560; j <= 860; j+=75,count++) {
          if (inventoryItems[count] != null) {
            g.drawImage(items[inventoryItems[count].getId()-1],j,i,null);
          }
        }
      }
    }
    
    /**
   * drawSkill
   * This method displays the skill tree
   * @param g, the graphics variable that displays images to the screen
   */
    
    public void drawSkill(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g.setColor(DARK_GREY);
      g.fillRect(200,100,1050,600);
      g.setColor(NAVY);
      g.fillRect(230,160,990,510);
      g.setColor(LIGHT_NAVY);
      g.fillRect(255,220,300,320);
      g.fillRect(575,220,300,320);
      g.fillRect(895,220,300,320);
      g.fillRect(255,560,550,80);
      g.setColor(LIGHT_GREY);
      g.drawRect(230,160,990,510);
      g.drawRect(200,100,1050,600);
      g.setColor(LIGHT_BLUE);
      g.setFont(f3);
      g.drawString("SKILL TREE",580,147);
      g.drawRect(255,220,300,320);
      g.drawRect(575,220,300,320);
      g.drawRect(895,220,300,320);
      g.drawRect(255,560,550,80);
      g.setColor(GOLD);
      g.setFont(f1);
      g.fillRect(900,570,230,60);
      if (skillOption == 1) {
        g.fillRect(355,235,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Every weapon deals 5 additional damage per hit!",345,620);
      } else if (skillOption == 2) {
        g.fillRect(295,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Every weapon deals 10 additional damage per hit!",345,620);
      } else if (skillOption == 3) {
        g.fillRect(415,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 20 base mana!",380,620);
      } else if (skillOption == 4) {
        g.fillRect(295,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Every weapon deals 15 additional damage per hit!",345,620);
      } else if (skillOption == 5) {
        g.fillRect(415,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 40 base mana!",380,620);
      } else if (skillOption == 6) {
        g.fillRect(685,235,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 20 base health!",380,620);
      } else if (skillOption == 7) {
        g.fillRect(625,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 30 base health!",380,620);
      } else if (skillOption == 8) {
        g.fillRect(745,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 15 base armour!",380,620);
      } else if (skillOption == 9) {
        g.fillRect(625,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 40 base health!",380,620);
      } else if (skillOption == 10) {
        g.fillRect(745,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain an additional 30 base armour!",380,620);
      } else if (skillOption == 11) {
        g.fillRect(1005,235,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain 5 more coins from wolves and chests!",360,620);
      } else if (skillOption == 12) {
        g.fillRect(945,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain 10 more coins from wolves and chests!",360,620);
      } else if (skillOption == 13) {
        g.fillRect(1065,335,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Health and mana regen increases by two every second!",325,620);
      } else if (skillOption == 14) {
        g.fillRect(945,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Gain 15 more coins from wolves and chests!",360,620);
      } else if (skillOption == 15) {
        g.fillRect(1065,435,90,90);
        g.setColor(Color.WHITE);
        g.drawString("Health and mana regen increases by three every second!",320,620);
      }
      g.setColor(Color.BLACK);
      if (skillOptions[0]) {
        g.fillRect(355,235,90,90);
      } if (skillOptions[1]) {
        g.fillRect(295,335,90,90);
      } if (skillOptions[2]) {
        g.fillRect(415,335,90,90);
      } if (skillOptions[3]) {
        g.fillRect(295,435,90,90);
      } if (skillOptions[4]) {
        g.fillRect(415,435,90,90);
      } if (skillOptions[5]) {
        g.fillRect(685,235,90,90);
      } if (skillOptions[6]) {
        g.fillRect(625,335,90,90);
      } if (skillOptions[7]) {
        g.fillRect(745,335,90,90);
      } if (skillOptions[8]) {
        g.fillRect(625,435,90,90);
      } if (skillOptions[9]) {
        g.fillRect(745,435,90,90);
      } if (skillOptions[10]) {
        g.fillRect(1005,235,90,90);
      } if (skillOptions[11]) {
        g.fillRect(945,335,90,90);
      } if (skillOptions[12]) {
        g.fillRect(1065,335,90,90);
      } if (skillOptions[13]) {
        g.fillRect(945,435,90,90);
      } if (skillOptions[14]) {
        g.fillRect(1065,435,90,90);
      }
      g.setColor(Color.RED);
      g.setFont(f4);
      g.drawString("ATTACK",350,200);
      g.fillRect(360,240,80,80);
      g.fillRect(300,340,80,80);
      g.fillRect(420,340,80,80);
      g.fillRect(300,440,80,80);
      g.fillRect(420,440,80,80);
      g2.setStroke(new BasicStroke(6));
      g.drawLine(360,320,340,340);
      g.drawLine(440,320,460,340);
      g.drawLine(340,420,340,440);
      g.drawLine(460,420,460,440);
      g.setColor(LIGHT_GREEN);
      g.drawString("DEFENCE",670,200);
      g.fillRect(690,240,80,80);
      g.fillRect(630,340,80,80);
      g.fillRect(750,340,80,80);
      g.fillRect(630,440,80,80);
      g.fillRect(750,440,80,80);
      g.drawLine(690,320,670,340);
      g.drawLine(770,320,790,340);
      g.drawLine(670,420,670,440);
      g.drawLine(790,420,790,440);
      g.setColor(TURQUOISE);
      g.drawString("UTILITY",990,200);
      g.fillRect(1010,240,80,80);
      g.fillRect(950,340,80,80);
      g.fillRect(1070,340,80,80);
      g.fillRect(950,440,80,80);
      g.fillRect(1070,440,80,80);
      g.drawLine(1010,320,990,340);
      g.drawLine(1090,320,1110,340);
      g.drawLine(990,420,990,440);
      g.drawLine(1110,420,1110,440);
      g.setColor(LIGHT_RED);
      g.fillRect(370,250,60,60);
      g.drawImage(bloodDrop,385,255,null);
      g.fillRect(310,350,60,60);
      g.drawImage(bloodDrop,325,355,null);
      g.fillRect(430,350,60,60);
      g.drawImage(waterDrop,445,357,null);
      g.fillRect(310,450,60,60);
      g.drawImage(bloodDrop,325,455,null);
      g.fillRect(430,450,60,60);
      g.drawImage(waterDrop,445,457,null);
      g.setColor(MINT);
      g.fillRect(700,250,60,60);
      g.drawImage(health,710,260,null);
      g.fillRect(640,350,60,60);
      g.drawImage(health,650,360,null);
      g.fillRect(760,350,60,60);
      g.drawImage(shield,767,357,null);
      g.fillRect(640,450,60,60);
      g.drawImage(health,650,460,null);
      g.fillRect(760,450,60,60);
      g.drawImage(shield,767,457,null);
      g.setColor(LIGHT_BLUE);
      g.fillRect(1020,250,60,60);
      g.drawImage(coin2,1027,260,null);
      g.fillRect(960,350,60,60);
      g.drawImage(coin2,967,360,null);
      g.fillRect(1080,350,60,60);
      g.drawImage(redBlueHeart,1087,360,null);
      g.fillRect(960,450,60,60);
      g.drawImage(coin2,967,460,null);
      g.fillRect(1080,450,60,60);
      g.drawImage(redBlueHeart,1087,460,null);
      g.setColor(Color.WHITE);
      g.drawString("Skill points: " + player.getSkillPoints(),245,140);
      g.drawImage(xButton,1200,107,null);
      g.drawString("Description", 450,585);
      g.setColor(Color.BLACK);
      g.drawString("CONFIRM",960,605);
    }
    
    /**
   * isObstacle
   * This method accepts an integer value that determines whether the number is an obstacle or not
   * @param x, the number being checked
   * @return true if the number is an obstacle, false otherwise
   */
    
    public boolean isObstacle(int x) {
      if (x == 0 ||  x == 3 || x == 4 || x == 5 || x == 6 || x == 13 || x == 14 || x ==15) {
        return true;
      }
      return false;
    }
    
    /**
   * keyPressed
   * This method detects if a key has been pressed
   * @param e, a KeyEvent variable
   */
    
    public void keyPressed(KeyEvent e) {
      if (!shop.getShop() && !skill && !inventory && !inMenu) {
        //Movement in the hub
        if (inHub && bossRoom == 0) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && (hubGrid[player.getRow()-1][player.getCol()-2] == 1 || hubGrid[player.getRow()-1][player.getCol()-2] == 11) && !player.getMotion()) {
            player.moveLeft();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && (hubGrid[player.getRow()][player.getCol()-1] == 1 || hubGrid[player.getRow()][player.getCol()-1] == 11 || hubGrid[player.getRow()][player.getCol()-1] == 10)  && !player.getMotion()) {
            player.moveDown();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && (hubGrid[player.getRow()-1][player.getCol()] == 1 || hubGrid[player.getRow()-1][player.getCol()] == 11)  && !player.getMotion()) {
            player.moveRight();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && (hubGrid[player.getRow()-2][player.getCol()-1] == 1 || hubGrid[player.getRow()-2][player.getCol()-1] == 11)  && !player.getMotion()) {
            player.moveUp();
            player.changeMotion(true);
          }
          //Movement outside the hub
        } else if (questOption == 0 && bossRoom == 0) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && map.playerX > 0 && player.getCol() <= 36 && !isObstacle(worldMap[player.getRow()-1][player.getCol()-2])  && !player.getMotion() && map.direction == 0) {   
            map.playerX--;
            player.moveLeft();
            map.direction = 4;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_LEFT && (map.playerX <= 0 || player.getCol() > 36) && !isObstacle(worldMap[player.getRow()-1][player.getCol()-2])  && !player.getMotion() && map.direction == 0) {
            player.moveLeft();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && map.playerY < 34 && player.getRow() >= 9 && !isObstacle(worldMap[player.getRow()][player.getCol()-1]) && !player.getMotion() && map.direction == 0) {
            map.playerY++;
            player.moveDown();
            map.direction = 3;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && (map.playerY >= 34 || player.getRow() < 9) && !isObstacle(worldMap[player.getRow()][player.getCol()-1]) && !player.getMotion() && map.direction == 0) {
            player.moveDown();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && map.playerX < 20 && player.getCol() >= 15 && !isObstacle(worldMap[player.getRow()-1][player.getCol()]) && !player.getMotion() && map.direction == 0) {
            map.playerX++;
            player.moveRight();
            map.direction = 2;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && (map.playerX >= 20 || player.getCol() < 16) && !isObstacle(worldMap[player.getRow()-1][player.getCol()]) && !player.getMotion() && map.direction == 0) {
            player.moveRight();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && map.playerY > 0 && player.getRow() <= 43 && !isObstacle(worldMap[player.getRow()-2][player.getCol()-1]) && !player.getMotion() && map.direction == 0) {
            map.playerY--;
            player.moveUp();
            map.direction = 1;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && (map.playerY <= 0 || player.getRow() > 43) && !isObstacle(worldMap[player.getRow()-2][player.getCol()-1]) && !player.getMotion() && map.direction == 0) {
            player.moveUp();
            player.changeMotion(true);
            player.changeAttack(false);
            //Interacting with chests and NPCs
          } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Interactable interact = map.checkInteract(player.getRow(),player.getCol());
            player.changeAttack(false);
            if (interact instanceof Chest) {
              player.setCoins(player.getCoins() + ((Chest)interact).getCoins() + player.getBaseCoins());
              message = interact.displayMessage();
              clock.restart();
              if (((Chest)interact).getItem() != null && !isInventoryFull(inventoryItems)) {
                for (int i = 0; i < 15; i++) {
                  if (inventoryItems[i] == null) {
                    inventoryItems[i] = ((Chest)interact).getItem();
                    break;
                  }
                }
              }
            } else if (interact instanceof NPC) {
              if (((NPC)interact).getName().equals("Greg")) {
                questOption = 1;
              } else if (((NPC)interact).getName().equals("Calvin")) {
                questOption = 2;
              } else if (((NPC)interact).getName().equals("Henry")) {
                questOption = 3;
              } else if (((NPC)interact).getName().equals("Thomas")) {
                questOption = 4;
              } else if (((NPC)interact).getName().equals("Aiden")) {
                questOption = 5;
              } else if (((NPC)interact).getName().equals("Dylan")) {
                questOption = 6;
              } else if (((NPC)interact).getName().equals("Peter")) {
                message = "There is a rumor that there's a secret passage behind the hub...";
                clock.restart();
              }
            }
            //Attack commands
          } else if (e.getKeyChar() == 'w' && player != null && !player.getMotion() && map.direction == 0 && !player.getAttack() && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(1);
            swordAttack(player.getRow()-2,player.getCol()-1);
          } else if (e.getKeyChar() == 'a' && player != null && !player.getMotion() && !player.getAttack() && map.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(4);
            swordAttack(player.getRow()-1,player.getCol()-2);
          } else if (e.getKeyChar() == 's' && player != null && !player.getMotion() && !player.getAttack() && map.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(3);
            swordAttack(player.getRow(),player.getCol()-1);
          } else if (e.getKeyChar() == 'd' && player != null && !player.getMotion() && !player.getAttack() && map.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(2);
            swordAttack(player.getRow()-1,player.getCol());
          }
          //Movement in quests
        } else if (questOption >= 1 && questOption <= 6) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getMotion() && player.getCol() > 1 && (questOption <= 3 || teleportMaze[player.getRow()-1][player.getCol()-2] == 0)) {
            if (questOption <= 3) {
              player.moveLeft();
            } else {
              player.changeDirection(4);
              player.changeCurrentSprite((player.getCurrentSprite() + 1) % 4);
            }
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getMotion() && player.getRow() < 15 && (questOption <= 3 || teleportMaze[player.getRow()][player.getCol()-1] == 0)) {
            if (questOption <= 3) {
              player.moveDown();
            } else {
              player.changeDirection(3);
              player.changeCurrentSprite((player.getCurrentSprite() + 1) % 4);
            }
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getMotion() && player.getCol() < 19 && (questOption <= 3 || teleportMaze[player.getRow()-1][player.getCol()] == 0)) {
            if (questOption <= 3) {
              player.moveRight();
            } else {
              player.changeDirection(2);
              player.changeCurrentSprite((player.getCurrentSprite() + 1) % 4);
            }
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && !player.getMotion() && player.getRow() > 1 && (questOption <= 3 || teleportMaze[player.getRow()-2][player.getCol()-1] == 0)) {
            if (questOption <= 3) {
              player.moveUp();
            } else {
              player.changeDirection(1);
              player.changeCurrentSprite((player.getCurrentSprite() + 1) % 4);
            }
            player.changeMotion(true);
          }
        } else if (questOption == 7) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getMotion() && flowerPuzzle[player.getRow()-1][player.getCol()-2] != 1 && flowerPuzzle[player.getRow()-1][player.getCol()-2] <= 6) {
            player.moveLeft();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getMotion() && flowerPuzzle[player.getRow()][player.getCol()-1] != 1 && flowerPuzzle[player.getRow()][player.getCol()-1] <= 6) {
            player.moveDown();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getMotion() && flowerPuzzle[player.getRow()-1][player.getCol()] != 1 && flowerPuzzle[player.getRow()-1][player.getCol()] <= 6) {
            player.moveRight();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && !player.getMotion() && flowerPuzzle[player.getRow()-2][player.getCol()-1] != 1 && flowerPuzzle[player.getRow()-2][player.getCol()-1] <= 6) {
            player.moveUp();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getRow() == 4 && player.getCol() >= 6 && player.getCol() <= 24 && player.getCol() % 2 == 0) {
            switches[player.getCol()/2 - 3] = (switches[player.getCol()/2 - 3] + 1) % 2;
          }
          //Movement in boss hallway
        } else if (bossRoom == 1) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getMotion() && bossEntranceMap[player.getRow()-1][player.getCol()-2] != 1 && bossEntranceMap[player.getRow()-1][player.getCol()-2] != 5) {
            player.moveLeft();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getMotion() && bossEntranceMap[player.getRow()][player.getCol()-1] != 1 && bossEntranceMap[player.getRow()][player.getCol()-1] != 5) {
            player.moveDown();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getMotion() && bossEntranceMap[player.getRow()-1][player.getCol()] != 1 && bossEntranceMap[player.getRow()-1][player.getCol()] != 5) {
            player.moveRight();
            player.changeMotion(true);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && !player.getMotion() && bossEntranceMap[player.getRow()-2][player.getCol()-1] != 1 && bossEntranceMap[player.getRow()-2][player.getCol()-1] != 5) {
            player.moveUp();
            player.changeMotion(true);
          }
          //Movement in boss arena
        } else if (bossRoom == 2) {
          if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getMotion() && boss.direction == 0 && bossFightMap[player.getRow()-1][player.getCol()-2] != 1 && bossFightMap[player.getRow()-1][player.getCol()-2] != 6) {
            player.moveLeft();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getMotion() && boss.direction == 0 && player.getRow() > 8 && player.getRow() < 22 && player.getRow() != 21 && bossFightMap[player.getRow()][player.getCol()-1] != 1 && bossFightMap[player.getRow()][player.getCol()-1] != 6) {
            player.moveDown();
            boss.direction = 3;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getMotion() && boss.direction == 0 && bossFightMap[player.getRow()][player.getCol()-1] != 1 && bossFightMap[player.getRow()][player.getCol()-1] != 6) {
            player.moveDown();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getMotion() && boss.direction == 0 && bossFightMap[player.getRow()-1][player.getCol()] != 1 && bossFightMap[player.getRow()-1][player.getCol()] != 6) {
            player.moveRight();
            player.changeMotion(true);
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && !player.getMotion() && boss.direction == 0 && player.getRow() > 8 && player.getRow() < 22 && player.getRow() != 9 && bossFightMap[player.getRow()-2][player.getCol()-1] != 1 && bossFightMap[player.getRow()-2][player.getCol()-1] != 6) {
            player.moveUp();
            boss.direction = 1;
            player.changeAttack(false);
          } else if (e.getKeyCode() == KeyEvent.VK_UP && !player.getMotion() && boss.direction == 0 && bossFightMap[player.getRow()-2][player.getCol()-1] != 1 && bossFightMap[player.getRow()-2][player.getCol()-1] != 6) {
            player.moveUp();
            player.changeMotion(true);
            player.changeAttack(false);
            //Attack command
          } else if (e.getKeyChar() == 'w' && player != null && !player.getMotion() && boss.direction == 0 && !player.getAttack() && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(1);
            boss.swordAttack(player.getRow()-2,player.getCol()-1,bossFightMap,player,clock,inventoryItems,quickInventoryOption);
          } else if (e.getKeyChar() == 'a' && player != null && !player.getMotion() && !player.getAttack() && boss.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(4);
            boss.swordAttack(player.getRow()-1,player.getCol()-2,bossFightMap,player,clock,inventoryItems,quickInventoryOption);
          } else if (e.getKeyChar() == 's' && player != null && !player.getMotion() && !player.getAttack() && boss.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(3);
            boss.swordAttack(player.getRow(),player.getCol()-1,bossFightMap,player,clock,inventoryItems,quickInventoryOption);
          } else if (e.getKeyChar() == 'd' && player != null && !player.getMotion() && !player.getAttack() && boss.direction == 0 && quickInventoryOption != 0 && inventoryItems[quickInventoryOption-1] != null && inventoryItems[quickInventoryOption-1].getId() >= 1 && inventoryItems[quickInventoryOption-1].getId() <= 6) {
            attackControls(2);
            boss.swordAttack(player.getRow()-1,player.getCol(),bossFightMap,player,clock,inventoryItems,quickInventoryOption);
          }
        }
      } 
      //Inventory command
      if (e.getKeyChar() >= '1' && e.getKeyChar() <= '9' && !inMenu) {
        quickInventoryOption = e.getKeyChar() - '0';
        //Main menu command
      } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
        inMenu = true;
      }
    }
    
    /**
   * keyTyped
   * This method detects if a key has been typed
   * @param e, a KeyEvent variable
   */
    
    public void keyTyped(KeyEvent e) {
      //System.out.println("pressed");
    }
    
    /**
   * keyReleased
   * This method detects if a key has been released
   * @param e, a KeyEvent variable
   */
    
    public void keyReleased(KeyEvent e) {          
      //System.out.println("released");
    }
    
    /**
   * isInventoryFull
   * This method checks if the inventory is full
   * @param inventoryItems, a Item array
   * @ return true if the inventory is full, false otherwise
   */
    
    public boolean isInventoryFull(Item[] inventoryItems) {
      for (int i = 0; i < inventoryItems.length; i++) {
        if (inventoryItems[i] == null) {
          return false;
        }
      }
      return true;
    }
    
    /**
   * swordAttack
   * This method detects for sword attacks and if it makes a connection with a monster
   * @param row, the row number of the sword
   * @param col, the column number of the sword
   */
    
    public void swordAttack(int row, int col) {
      Wolf wolf = (Wolf)(monstersMap[row][col]);
      if (wolf != null && wolf.getRow() != -1) {
        if (inventoryItems[quickInventoryOption-1].getId() == 1) {
          wolf.changeHealth(wolf.getHealth()-20-player.getBaseDamage());
        } else if (inventoryItems[quickInventoryOption-1].getId() == 2) {
          wolf.changeHealth(wolf.getHealth()-40-player.getBaseDamage());
        }
        monsterName = wolf.getName();
        if (wolf.getHealth() <= 0) {
          player.setCoins(player.getCoins()+wolf.getCoins());
          monstersMap[row][col] = null;
          message = "You received " + wolf.getCoins() + " coins!";
          clock.restart();
          monsterName = "";
        } else {
          monsterHealth = wolf.getHealth();
        }
      }
    }
    
    /**
   * attackControls
   * This method does the necessary controls for each attack command based on the direction of the player
   * @param direction, an int variable that represents the direction of the attack
   */
    
    public void attackControls(int direction) {
      if (inventoryItems[quickInventoryOption-1].getId() == 3 && player.getMana() >= 20) {
        player.changeAttack(true);
        player.changeDirection(direction);
        player.restartAttackCD();
        player.changeWeapon(inventoryItems[quickInventoryOption-1].getId()+3);
        player.changeBulletX(0);
        player.changeBulletY(0);
        player.changeMana(player.getMana()-20);
        player.changeCollision(false);
      } else if (inventoryItems[quickInventoryOption-1].getId() == 4 && player.getMana() >= 30) {
        player.changeAttack(true);
        player.changeDirection(direction);
        player.restartAttackCD();
        player.changeWeapon(inventoryItems[quickInventoryOption-1].getId()+3);
        player.changeBulletX(0);
        player.changeBulletY(0);
        player.changeMana(player.getMana()-30);
        player.changeCollision(false);
      } else if (inventoryItems[quickInventoryOption-1].getId() != 3 && inventoryItems[quickInventoryOption-1].getId() != 4) {
        player.changeAttack(true);
        player.changeDirection(direction);
        player.restartAttackCD();
        player.changeWeapon(inventoryItems[quickInventoryOption-1].getId()+3);
        player.changeBulletX(0);
        player.changeBulletY(0);
        player.changeCollision(false);
      } else {
        message = "Not enough mana!";
        clock.restart();
      }
      if (inventoryItems[quickInventoryOption-1].getId() == 6) {
        inventoryItems[quickInventoryOption-1] = null;
      }
    }
  }
  
  /**
   * MatrixPanelMouseListener
   * This class detects if the mouse has been used
   */
  
  class MatrixPanelMouseListener implements MouseListener, MouseMotionListener{ 
    
    /**
   * mousePressed
   * This method detects if the mouse has been pressed
   * @param e, a MouseEvent variable
   */
    
    public void mousePressed(MouseEvent e) {
      if (!inMenu) {
        //Shop menu
        if (shop.getShop()) {
          if (e.getPoint().x >= 270 && e.getPoint().x <= 470 && e.getPoint().y >= 200 & e.getPoint().y <= 260) {
            shop.changeShopOption(1);
            shop.changeItemOption(0);
          } else if (e.getPoint().x >= 270 && e.getPoint().x <= 470 && e.getPoint().y >= 280 & e.getPoint().y <= 340) {
            shop.changeShopOption(2);
            shop.changeItemOption(0);
          } else if (e.getPoint().x >= 270 && e.getPoint().x <= 470 && e.getPoint().y >= 360 & e.getPoint().y <= 420) {
            shop.changeShopOption(3);
            shop.changeItemOption(0);
          }
          
          if (e.getPoint().x >= 525 && e.getPoint().x <= 735 && e.getPoint().y >= 195 && e.getPoint().y <= 365) {
            shop.changeItemOption(1);
          } else if (e.getPoint().x >= 750 && e.getPoint().x <= 960 && e.getPoint().y >= 195 && e.getPoint().y <= 365) {
            shop.changeItemOption(2);
          } else if (e.getPoint().x >= 975 && e.getPoint().x <= 1185 && e.getPoint().y >= 195 && e.getPoint().y <= 365 && shop.getShopOption() != 3) {
            shop.changeItemOption(3);
          } else if (e.getPoint().x >= 525 && e.getPoint().x <= 735 && e.getPoint().y >= 380 && e.getPoint().y <= 550 && shop.getShopOption() == 1) {
            shop.changeItemOption(4);
          } else if (e.getPoint().x >= 750 && e.getPoint().x <= 960 && e.getPoint().y >= 380 && e.getPoint().y <= 550 && shop.getShopOption() == 1) {
            shop.changeItemOption(5);
          } else if (e.getPoint().x >= 975 && e.getPoint().x <= 1185 && e.getPoint().y >= 380 && e.getPoint().y <= 550 && shop.getShopOption() == 1) {
            shop.changeItemOption(6);
          }
          
          if ((e.getPoint().x - 1200)*(e.getPoint().x-1200) + (e.getPoint().y-107)*(e.getPoint().y-107) <= 2025) {
            shop.changeShop(false);
            shop.changeItemOption(0);
            shop.changeShopOption(1);
            player.moveLeft();
            player.changeMotion(true);
          } else if (e.getPoint().x >= 270 && e.getPoint().x <= 470 && e.getPoint().y >= 570 && e.getPoint().y <= 630) {
            if (!isInventoryFull(inventoryItems) && shop.getItemOption() != 0 && player.getCoins() >= shop.getItem().getCost()) {
              player.removeCoins(shop.getItem().getCost());
              for (int i = 0; i < inventoryItems.length; i++) {
                if (inventoryItems[i] == null) {
                  inventoryItems[i] = shop.getItem();
                  break;
                }
              }
            }
          }
          //Skill tree menu
        } else if (skill) { 
          if (e.getPoint().x >= 360 && e.getPoint().x <= 440 && e.getPoint().y >= 240 & e.getPoint().y <= 320) {
            skillOption = 1;
          } else if (e.getPoint().x >= 300 && e.getPoint().x <= 380 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 2;
          } else if (e.getPoint().x >= 420 && e.getPoint().x <= 500 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 3;
          } else if (e.getPoint().x >= 300 && e.getPoint().x <= 380 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 4;
          } else if (e.getPoint().x >= 420 && e.getPoint().x <= 500 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 5;
          } else if (e.getPoint().x >= 690 && e.getPoint().x <= 780 && e.getPoint().y >= 240 & e.getPoint().y <= 320) {
            skillOption = 6;
          } else if (e.getPoint().x >= 630 && e.getPoint().x <= 710 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 7;
          } else if (e.getPoint().x >= 750 && e.getPoint().x <= 830 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 8;
          } else if (e.getPoint().x >= 630 && e.getPoint().x <= 710 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 9;
          } else if (e.getPoint().x >= 750 && e.getPoint().x <= 830 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 10;
          } else if (e.getPoint().x >= 1010 && e.getPoint().x <= 1090 && e.getPoint().y >= 240 & e.getPoint().y <= 320) {
            skillOption = 11;
          } else if (e.getPoint().x >= 950 && e.getPoint().x <= 1030 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 12;
          } else if (e.getPoint().x >= 1070 && e.getPoint().x <= 1150 && e.getPoint().y >= 340 & e.getPoint().y <= 420) {
            skillOption = 13;
          } else if (e.getPoint().x >= 950 && e.getPoint().x <= 1030 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 14;
          } else if (e.getPoint().x >= 1070 && e.getPoint().x <= 1150 && e.getPoint().y >= 440 & e.getPoint().y <= 520) {
            skillOption = 15;
          }
          
          if ((e.getPoint().x - 1200)*(e.getPoint().x-1200) + (e.getPoint().y-107)*(e.getPoint().y-107) <= 2025) {
            skill = false;
            player.moveRight();
            player.changeMotion(true);
          } else if (e.getPoint().x >= 900 && e.getPoint().x <= 1130 && e.getPoint().y >= 570 & e.getPoint().y <= 630) {
            if (player.getSkillPoints() >= 1) {
              if (skillOption == 1 && !skillOptions[0]) {
                player.changeBaseDamage(player.getBaseDamage()+5);
                skillOptions[0] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 2 && player.getBaseDamage() == 5 && !skillOptions[1]) {
                player.changeBaseDamage(player.getBaseDamage()+10);
                skillOptions[1] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 3 && player.getBaseDamage() >= 5 && !skillOptions[2]) {
                player.changeBaseMana(player.getBaseMana()+20);
                skillOptions[2] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 4 && player.getBaseDamage() == 15 && !skillOptions[3]) {
                player.changeBaseDamage(player.getBaseDamage()+15);
                skillOptions[3] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 5 && player.getBaseMana() == 160 && !skillOptions[4]) {
                player.changeBaseMana(player.getBaseMana()+40);
                skillOptions[4] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 6 && !skillOptions[5]) {
                player.changeBaseHealth(player.getBaseHealth()+20);
                skillOptions[5] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 7 && player.getBaseHealth() == 120 && !skillOptions[6]) {
                player.changeBaseHealth(player.getBaseHealth()+30);
                skillOptions[6] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 8 && player.getBaseHealth() >= 120 && !skillOptions[7]) {
                player.changeBaseArmour(player.getBaseArmour()+15);
                skillOptions[7] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 9 && player.getBaseHealth() == 150 && !skillOptions[8]) {
                player.changeBaseHealth(player.getBaseHealth()+40);
                skillOptions[8] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 10 && player.getBaseArmour() == 295 && !skillOptions[9]) {
                player.changeBaseArmour(player.getBaseArmour()+30);
                skillOptions[9] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 11 && !skillOptions[10]) {
                player.changeBaseCoins(player.getBaseCoins()+5);
                skillOptions[10] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 12 && player.getBaseCoins() == 5 && !skillOptions[11]) {
                player.changeBaseCoins(player.getBaseCoins()+10);
                skillOptions[11] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 13 && player.getBaseCoins() >= 5 && !skillOptions[12]) {
                player.changeBaseRegen(2);
                skillOptions[12] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 14 && player.getBaseCoins() == 15 && !skillOptions[13]) {
                player.changeBaseCoins(player.getBaseCoins()+15);
                skillOptions[13] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              } else if (skillOption == 15 && player.getBaseRegen() == 2 && !skillOptions[14]) {
                player.changeBaseRegen(3);
                skillOptions[14] = true;
                player.changeSkillPoints(player.getSkillPoints()-1);
              }
            }
          }
          //Inventory menu
        } else if (inventory) {
          if (e.getPoint().x >= 560 && e.getPoint().x <= 615 && e.getPoint().y >= 365 && e.getPoint().y <= 420) {
            inventoryOption = 1;
          } else if (e.getPoint().x >= 635 && e.getPoint().x <= 690 && e.getPoint().y >= 365 && e.getPoint().y <= 420) {
            inventoryOption = 2;
          } else if (e.getPoint().x >= 710 && e.getPoint().x <= 765 && e.getPoint().y >= 365 && e.getPoint().y <= 420) {
            inventoryOption = 3;
          } else if (e.getPoint().x >= 785 && e.getPoint().x <= 840 && e.getPoint().y >= 365 && e.getPoint().y <= 420) {
            inventoryOption = 4;
          } else if (e.getPoint().x >= 860 && e.getPoint().x <= 915 && e.getPoint().y >= 365 && e.getPoint().y <= 420) {
            inventoryOption = 5;
          } else if (e.getPoint().x >= 560 && e.getPoint().x <= 615 && e.getPoint().y >= 435 && e.getPoint().y <= 490) {
            inventoryOption = 6;
          } else if (e.getPoint().x >= 635 && e.getPoint().x <= 690 && e.getPoint().y >= 435 && e.getPoint().y <= 490) {
            inventoryOption = 7;
          } else if (e.getPoint().x >= 710 && e.getPoint().x <= 765 && e.getPoint().y >= 435 && e.getPoint().y <= 490) {
            inventoryOption = 8;
          } else if (e.getPoint().x >= 785 && e.getPoint().x <= 840 && e.getPoint().y >= 435 && e.getPoint().y <= 490) {
            inventoryOption = 9;
          } else if (e.getPoint().x >= 860 && e.getPoint().x <= 915 && e.getPoint().y >= 435 && e.getPoint().y <= 490) {
            inventoryOption = 10;
          } else if (e.getPoint().x >= 560 && e.getPoint().x <= 615 && e.getPoint().y >= 505 && e.getPoint().y <= 560) {
            inventoryOption = 11;
          } else if (e.getPoint().x >= 635 && e.getPoint().x <= 690 && e.getPoint().y >= 505 && e.getPoint().y <= 560) {
            inventoryOption = 12;
          } else if (e.getPoint().x >= 710 && e.getPoint().x <= 765 && e.getPoint().y >= 505 && e.getPoint().y <= 560) {
            inventoryOption = 13;
          } else if (e.getPoint().x >= 785 && e.getPoint().x <= 840 && e.getPoint().y >= 505 && e.getPoint().y <= 560) {
            inventoryOption = 14;
          } else if (e.getPoint().x >= 860 && e.getPoint().x <= 915 && e.getPoint().y >= 505 && e.getPoint().y <= 560) {
            inventoryOption = 15;
          }
          
          if (e.getPoint().x >= 935 && e.getPoint().x <= 965 && e.getPoint().y >= 250 && e.getPoint().y <= 280) {
            inventory = false;
            inventoryOption = 0;
          } else if (e.getPoint().x >= 770 && e.getPoint().x <= 850 && e.getPoint().y >= 305 && e.getPoint().y <= 335) {
            if (inventoryOption != 0 && inventoryItems[inventoryOption-1] != null) {
              if (inventoryItems[inventoryOption-1].getId() == 10) {
                if (player.getHealth() >= player.getBaseHealth()-20) {
                  player.changeHealth(player.getBaseHealth());
                } else {
                  player.changeHealth(player.getHealth()+20);
                }
                inventoryItems[inventoryOption-1] = null;
              } else if (inventoryItems[inventoryOption-1].getId() == 11) {
                if (player.getMana() >= player.getBaseMana() - 30) {
                  player.changeMana(player.getBaseMana());
                } else {
                  player.changeMana(player.getMana()+30);
                }
                inventoryItems[inventoryOption-1] = null;
              } else if (inventoryItems[inventoryOption-1].getId() == 7) {
                if (player.getArmour() >= player.getBaseArmour()-40) {
                  player.changeArmour(player.getBaseArmour());
                } else {
                  player.changeArmour(player.getArmour()+40);
                }
                inventoryItems[inventoryOption-1] = null;
              } else if (inventoryItems[inventoryOption-1].getId() == 8) {
                if (player.getArmour() >= player.getBaseArmour()-100) {
                  player.changeArmour(player.getBaseArmour());
                } else {
                  player.changeArmour(player.getArmour()+100);
                }
                inventoryItems[inventoryOption-1] = null;
              } else if (inventoryItems[inventoryOption-1].getId() == 9) {
                if (player.getArmour() >= player.getBaseArmour()-60) {
                  player.changeArmour(player.getBaseArmour()); 
                } else {
                  player.changeArmour(player.getArmour()+60);
                }
                inventoryItems[inventoryOption-1] = null;
              }
            }
          } else if (e.getPoint().x >= 865 && e.getPoint().x <= 945 && e.getPoint().y >= 305 && e.getPoint().y <= 335) {
            if (inventoryOption != 0 && inventoryItems[inventoryOption-1] != null) {
              player.setCoins(player.getCoins()+inventoryItems[inventoryOption-1].getCost());
              inventoryItems[inventoryOption-1] = null;
            }
          }
          //Map menu
        } else if (tinyMap) {
          if (e.getPoint().x >= 935 && e.getPoint().x <= 965 && e.getPoint().y >= 250 && e.getPoint().y <= 280) {
            tinyMap = false;
          }
        }
        if (e.getPoint().x >= 1195 && e.getPoint().x <= 1250 && e.getPoint().y >= 692 && e.getPoint().y <= 747) {
          inventory = true;
          tinyMap = false;
        } else if (e.getPoint().x >= 1265 && e.getPoint().x <= 1320 && e.getPoint().y >= 692 && e.getPoint().y <= 747) {
          tinyMap = true;
          inventory = false;
        } else if (e.getPoint().x >= 1335 && e.getPoint().x <= 1390 && e.getPoint().y >= 692 && e.getPoint().y <= 747) {
          inMenu = true;
        }
        //Main menu
      } else if (!controls) {
        if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 320 && e.getY() <= 422) {
          inMenu = false;
        } else if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 446 && e.getY() <= 548) {
          controls = true;
        } else if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 572 && e.getY() <= 674) {
          System.exit(0);
        }
        //Controls menu
      } else {
        if (e.getX() >= 0 && e.getX() <= 250 && e.getY() >= 0 && e.getY() <= 110) {
          controls = false;
        }
      }
    }
    
    /**
   * mouseMoved
   * This method detects if the mouse has been moved
   * @param e, a MouseEvent variable
   */
    
    public void mouseMoved(MouseEvent e) {
      //Main menu hovering over buttons
      if (inMenu && !controls) {
        if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 320 && e.getY() <= 422) {
          menuButton = 1;
        } else if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 446 && e.getY() <= 548) {
          menuButton = 2;
        } else if (e.getX() >= 463 && e.getX() <= 977 && e.getY() >= 572 && e.getY() <= 674) {
          menuButton = 3;
        } else {
          menuButton = 0;
        }
      }
    }
    
    /**
   * mouseDragged
   * This method detects if the mouse has been dragged
   * @param e, a MouseEvent variable
   */
    
    public void mouseDragged(MouseEvent e) {
      //System.out.println("Mouse dragged");
    }
    
    /**
   * isInventoryFull
   * This method detects if the inventory is full
   * @param inventoryItems, an Item array
   * @return true if the inventory is full, false otherwise
   */
    
    public boolean isInventoryFull(Item[] inventoryItems) {
      for (int i = 0; i < inventoryItems.length; i++) {
        if (inventoryItems[i] == null) {
          return false;
        }
      }
      return true;
    }
 
    /**
   * mouseReleased
   * This method detects if the mouse has been released
   * @param e, a MouseEvent variable
   */
    
    public void mouseReleased(MouseEvent e) { 
      //System.out.println("Mouse released; # of clicks: " + e.getClickCount()); 
    } 
    
    /**
   * mouseEntered
   * This method detects if the mouse has entered
   * @param e, a MouseEvent variable
   */
    
    public void mouseEntered(MouseEvent e) { 
      //System.out.println("Mouse entered"); 
    } 
    
    /**
   * mouseExited
   * This method detects if the mouse has exited
   * @param e, a MouseEvent variable
   */
    
    public void mouseExited(MouseEvent e) { 
      //System.out.println("Mouse exited"); 
    } 
    
    /**
   * mouseClicked
   * This method detects if the mouse has been clicked
   * @param e, a MouseEvent variable
   */
    
    public void mouseClicked(MouseEvent e) { 
      //System.out.println("Number of zombies: " + numberOfZombies); 
    } 
  }
}

/**
 * Clock
 * This class is the clock class that holds the current time that has passed
 */

class Clock {
  //Initialize all variables
  long elapsedTime;
  long lastTimeCheck;
  long totalTime;
  
  public Clock() { 
    lastTimeCheck=System.nanoTime();
    elapsedTime=0;
    totalTime = 0;
  }
  
  /**
   * update
   * This method updates the total time with the time passed
   * @return void
   */
  
  public void update() {
    long currentTime = System.nanoTime(); 
    elapsedTime = currentTime - lastTimeCheck;
    lastTimeCheck=currentTime;
    totalTime += elapsedTime;
  }
  
  /**
   * restart
   * This method restarts the total time
   * @return void
   */
  
  public void restart() {
    totalTime = 0;
  }
  
  /**
   * getTotalTime
   * This method returns the total time passed
   * @return a double variable that represents the total time
   */
  
  public double getTotalTime() {
    return totalTime/1.0E9;
  }
  
  /**
   * getElapsedTime
   * This method returns the double value of the elapsed time
   * @return a double value that represents the total time elapsed
   */
  
  public double getElapsedTime() {
    return elapsedTime/1.0E9;
  }
}
