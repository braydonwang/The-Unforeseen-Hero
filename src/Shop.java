import java.awt.*;

/*
 * [Shop.java]
 * Version 1.0
 * This program represents the shop menu in the game
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Shop
* This class holds the design of the shop and how to draw it, as well as all the crucial variables
*/

class Shop {
  //Initialize all variables
  private int shopOption;
  private int itemOption;
  private boolean inShop;
  private int coins;
  static Item[][] shopItems;
  
  //Initialize all colours
  Color LIGHT_BROWN = new Color(222,190,136);
  Color BROWN = new Color(154,119,83);
  Color DARK_BROWN = new Color(38,33,25);
  Color GOLD = new Color(239,186,23);
  Color DARK_GREY = new Color(42,39,34);
  Color GREY = new Color(54,54,54);
  
  //Initialize all fonts
  Font  f3  = new Font(Font.SANS_SERIF, Font.BOLD,  45);
  Font  f4  = new Font(Font.SANS_SERIF, Font.BOLD,  20);
  
  //Initialize all images
  Image coin = Toolkit.getDefaultToolkit().getImage("Coin.png");
  Image xButton = Toolkit.getDefaultToolkit().getImage("XButton.png");
  Image emeraldSword = Toolkit.getDefaultToolkit().getImage("EmeraldSword.png");
  Image rubySword = Toolkit.getDefaultToolkit().getImage("RubySword.png");
  Image sapphireWand = Toolkit.getDefaultToolkit().getImage("SapphireWand.png");
  Image crystalWand = Toolkit.getDefaultToolkit().getImage("CrystalWand.png");
  Image bow = Toolkit.getDefaultToolkit().getImage("BowAndArrow.png");
  Image bomb = Toolkit.getDefaultToolkit().getImage("Bomb.png");
  Image helmet = Toolkit.getDefaultToolkit().getImage("Helmet.png");
  Image chestplate = Toolkit.getDefaultToolkit().getImage("Chestplate.png");
  Image pants = Toolkit.getDefaultToolkit().getImage("Pants.png");
  Image healthPot = Toolkit.getDefaultToolkit().getImage("HealthPot.png");
  Image manaPot = Toolkit.getDefaultToolkit().getImage("ManaPot.png");
  
  Shop() {
    this.shopOption = 1;
    this.itemOption = 0;
    this.inShop = false;
    this.shopItems = new Item[3][];
    this.coins = 0;
  }
  
  /**
   * getShopOption
   * This method returns the current shop option that the player is on
   * @return an integer value that represents the shop option
   */
  
  public int getShopOption() {
    return this.shopOption;
  }
  
  /**
   * changeShopOption
   * This method accepts an integer value that represents the new shop option and changes the old one with it
   * @param option, an integer value that represents the new shop option
   */
  
  public void changeShopOption(int option) {
    this.shopOption = option;
  }
  
  /**
   * getItemOption
   * This method returns the current item option that the player is on
   * @return an integer value that represents the current item option
   */
  
  public int getItemOption() {
    return this.itemOption;
  }
  
  /**
   * changeItemOption
   * This method accepts an integer value that represents the new item option and changes the old one
   * @param option, an integer value that represents the new item option
   */
  
  public void changeItemOption(int option) {
    this.itemOption = option;
  }
  
  /**
   * getShop
   * This method returns a boolean variable that represents whether the player is in the shop or not
   * @return true if the player is in the shop, false otherwise
   */
  
  public boolean getShop() {
    return this.inShop;
  }
  
  /**
   * changeShop
   * This method accepts a boolean variable that represents the new shop variable and changes the old one
   * @param inShop, a boolean variable that represents the new shop variable
   */
  
  public void changeShop(boolean inShop) {
    this.inShop = inShop;
  }
  
  /**
   * changeCoins
   * This method accepts an integer value that represents the new total coins and changes the old one
   * @param coins, an integer value that represents the new total coins
   */
  
  public void changeCoins(int coins) {
    this.coins = coins;
  }
  
  /**
   * getItem
   * This method returns the current item that the player is selecting in the shop
   * @return an Item value that represents the current item
   */
  
  public Item getItem() {
    return shopItems[this.shopOption - 1][this.itemOption - 1];
  }
  
  /**
   * draw
   * This method displays the shop menu
   * @param g, the graphics variable
   */
  
  public void draw(Graphics g) {
    g.setColor(DARK_GREY);
    g.fillRect(200,100,1050,600);
    g.setColor(GREY);
    g.fillRect(230,160,990,510);
    g.setColor(DARK_BROWN);
    g.fillRect(250,180,240,470);
    g.fillRect(510,180,690,470);
    g.setColor(GOLD);
    g.setFont(f3);
    g.drawString("SHOP",680,147);
    
    //Displaying the selection rectangle around the selected button
    if (shopOption == 1) {
      g.fillRect(265,195,210,70);
    } else if (shopOption == 2) {
      g.fillRect(265,275,210,70);
    } else {
      g.fillRect(265,355,210,70);
    }
    if (itemOption == 1) {
      g.fillRect(520,190,220,180);
    } else if (itemOption == 2) {
      g.fillRect(745,190,220,180);
    } else if (itemOption == 3) {
      g.fillRect(970,190,220,180);
    } else if (itemOption == 4) {
      g.fillRect(520,375,220,180);
    } else if (itemOption == 5) {
      g.fillRect(745,375,220,180);
    } else if (itemOption == 6) {
      g.fillRect(970,375,220,180);
    }
    
    g.fillRect(270,570,200,60);
    g.setColor(LIGHT_BROWN);
    g.fillRect(525,195,210,170);
    g.fillRect(750,195,210,170);
    if (shopOption <= 2) {
      g.fillRect(975,195,210,170);
    }
    if (shopOption == 1) {
      g.fillRect(525,380,210,170);
      g.fillRect(750,380,210,170);
      g.fillRect(975,380,210,170);
      g.drawImage(emeraldSword,530,180,null);
      g.drawImage(rubySword,755,180,null);
      g.drawImage(sapphireWand,980,190,null);
      g.drawImage(crystalWand,530,375,null);
      g.drawImage(bow,755,375,null);
      g.drawImage(bomb,980,375,null);
    } else if (shopOption == 2) {
      g.drawImage(helmet,540,200,null);
      g.drawImage(chestplate,755,180,null);
      g.drawImage(pants,980,190,null);
    } else if (shopOption == 3) {
      g.drawImage(healthPot,530,180,null);
      g.drawImage(manaPot,755,180,null);
    }
    g.setColor(BROWN);
    g.fillRect(270,200,200,60);
    g.fillRect(270,280,200,60);
    g.fillRect(270,360,200,60);
    g.setColor(DARK_BROWN);
    g.drawRect(525,195,210,170);
    g.drawRect(750,195,210,170);
    g.drawRect(975,195,210,170);
    g.drawRect(525,380,210,170);
    g.drawRect(750,380,210,170);
    g.drawRect(975,380,210,170);
    g.setColor(Color.WHITE);
    g.setFont(f4);
    g.drawString("WEAPONS",315,235);
    g.drawString("ARMOUR",315,315);
    g.drawString("CONSUMABLES",292,395);
    g.setColor(Color.BLACK);
    g.drawRect(200,100,1050,600);
    g.drawRect(230,160,990,510);
    g.drawRect(250,180,240,470);
    g.drawRect(270,200,200,60);
    g.drawRect(270,280,200,60);
    g.drawRect(270,360,200,60);
    g.drawRect(510,180,690,470);
    g.drawString("BUY NOW",320,605);
    
    g.drawImage(coin,240,470,null);
    g.drawImage(xButton,1200,107,null);
    g.setColor(GOLD);
    g.setFont(f3);
    g.drawString(this.coins + "",340,530);
      
    //Adding every shop item into the shop item array
    shopItems[0] = new Item[6];
    shopItems[0][0] = new Item(1,100);
    shopItems[0][1] = new Item(2,300);
    shopItems[0][2] = new Item(3,200);
    shopItems[0][3] = new Item(4,500);
    shopItems[0][4] = new Item(5,250);
    shopItems[0][5] = new Item(6,40);
    shopItems[1] = new Item[3];
    shopItems[1][0] = new Item(7,160);
    shopItems[1][1] = new Item(8,400);
    shopItems[1][2] = new Item(9,240);
    shopItems[2] = new Item[2];
    shopItems[2][0] = new Item(10,75);
    shopItems[2][1] = new Item(11,50);
    
    //Displaying the costs of each item
    g.setColor(Color.BLACK);
    g.setFont(f4);
    if (shopOption == 1) {
      g.drawString(shopItems[0][0].getCost() + "", 685,355);
      g.drawString(shopItems[0][1].getCost() + "", 910,355);
      g.drawString(shopItems[0][2].getCost() + "", 1135,355);
      g.drawString(shopItems[0][3].getCost() + "", 685,540);
      g.drawString(shopItems[0][4].getCost() + "", 910,540);
      g.drawString(shopItems[0][5].getCost() + "", 1145,540);
    } else if (shopOption == 2) {
      g.drawString(shopItems[1][0].getCost() + "", 685,355);
      g.drawString(shopItems[1][1].getCost() + "", 910,355);
      g.drawString(shopItems[1][2].getCost() + "", 1135,355);
    } else {
      g.drawString(shopItems[2][0].getCost() + "", 685,355);
      g.drawString(shopItems[2][1].getCost() + "", 910,355);
    }
    
    //Displaying the description of every item in the shop
    g.setColor(Color.WHITE);
    g.setFont(f4);
    if (itemOption == 1) {
      if (shopOption == 1) {
        g.drawString("The Emerald Sword deals 20 damage per attack!",590,600);
      } else if (shopOption == 2) {
        g.drawString("The Amethyst Helmet grants 40 armour when equipped!", 570,600);
      } else {
        g.drawString("Health pots grant 20 health when consumed!",600,600);
      }
    } else if (itemOption == 2) {
      if (shopOption == 1) {
        g.drawString("The Ruby Sword deals 40 damage per attack!",610,600);
      } else if (shopOption == 2) {
        g.drawString("The Amethyst Chestplate grants 100 armour when equipped!", 540,600);
      } else {
        g.drawString("Mana pots grant 30 mana when consumed!",620,600);
      }
    } else if (itemOption == 3) {
      if (shopOption == 1) {
        g.drawString("The Sapphire Wand deals 30 damage and drains 20 mana!",560,600);
      } else if (shopOption == 2) {
        g.drawString("The Amethyst Pants grants 60 armour when equipped!",570,600);
      }
    } else if (itemOption == 4) {
      g.drawString("The Crystal Wand deals 60 damage and drains 30 mana!",560,600);
    } else if (itemOption == 5) {
      g.drawString("The Bow and Arrow deals 10 damage per shot!",600,600);
    } else if (itemOption == 6) {
      g.drawString("Bombs deal 80 damage when they explode!",620,600);
    }
  }
}