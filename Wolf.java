/*
 * [Wolf.java]
 * Version 1.0
 * This program represents a type of monster called the wolf
 * Authour: Braydon Wang
 * Date: 12/01/2020
*/


/**
* Wolf
* This class holds the key variables for every wolf object.
*/

class Wolf extends Monster {
  Wolf(int row, int col, int coins) {
    super(120,row,col,2,coins,"Wolf");
  }
}