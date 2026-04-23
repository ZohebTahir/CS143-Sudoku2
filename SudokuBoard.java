import java.util.*;
import java.io.*;

public class SudokuBoard {
   private int[][] board;
   
   public SudokuBoard() {
      board = new int[9][9];
   } 
   
   public SudokuBoard(String fileName) {
      this();
      try {
        Scanner console = new Scanner(new File(fileName));
        for(int r = 0; r < board.length; r++){
            if(console.hasNext()) {
            String line = console.next();
            for(int c = 0; c < board[0].length; c++) {
               char value = line.charAt(c);
               if(value == '.') {
                  board[r][c] = 0;
               } else {
                  board[r][c] = value - '0';
                 }
            }
         }
      }
    } catch (FileNotFoundException e) {
        System.out.println("Error: File not found.");
    }
   }
   
   //helper for isValid() to validate rows
   private boolean validRows() {       
      for(int r = 0; r < board.length; r++) {
      Set<Integer> rowSet = new HashSet<>();
      
         for(int c = 0; c < board[r].length; c++) {
            int cell = board[r][c];
            
            //deals with illegal characters
            if (cell < 0 || cell > 9) {
               return false;
            }
            if (cell != 0) {
               if (rowSet.contains(cell)) {
                  //System.out.print("Duplicate in row");
                  return false; 
               }
            }
            rowSet.add(cell);
         }
      }
      return true;
   }
   
   //helper for isValid() to validate columns
   private boolean validCols() {
      for(int c = 0; c < board.length; c++) {
      Set<Integer> colSet = new HashSet<>(); 

         for(int r = 0; r < board[c].length; r++) {
            int cell = board[r][c];
            
            //deals with illegal characters
            if (cell < 0 || cell > 9) {
               return false;
            }
            if(cell != 0) {
               if (colSet.contains(cell)) {
                  //System.out.println("Duplicate in column");
                  return false;
               }
            } 
            colSet.add(cell);
         }
      }
      return true;
   }
   
   //helper for isValid() to validate 3x3s
   private boolean validMinis() {
      for(int i = 1; i <= 9; i++) {
         Set<Integer> miniSet = new HashSet<>();
         int[][] miniArr = miniSquare(i);
         
         for(int r = 0; r < miniArr.length; r++) {
            for(int c = 0; c < miniArr[r].length; c++) {
               int cell = miniArr[r][c];
                  
               //deals with illegal characters
               if (cell < 0 || cell > 9) {
                  return false;
               }
               if (cell != 0) {
                  if (miniSet.contains(cell)) {
                     //System.out.println("Duplicate in mini square");
                     return false;
                  }
               }
               miniSet.add(cell);
            }
         }   
      }  
      //System.out.print("is a Valid board!");
      return true;
   }
   
   //3x3 helper from Crystal
   private int[][] miniSquare(int spot) {
      int[][] mini = new int[3][3];
      for(int r = 0; r < 3; r++) {
         for(int c = 0; c < 3; c++) {
            // whoa - wild! This took me a solid hour to figure out (at least)
            // This translates between the "spot" in the 9x9 Sudoku board
            // and a new mini square of 3x3
            mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 * 3 + c];
         }
      }
      return mini;
   }
   
   public boolean isValid() {
      // Checking Rows and columns and mini squares
      return validRows() && validCols() && validMinis();         
   }
 
   public boolean isSolved() {
      Map<Integer, Integer> map = new HashMap<>();
      for (int i = 1; i < 10; i++) {
         map.put(i,0);
      }
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            int key = board[r][c];
            
            //deals with illegal characters
            if (key < 0 || key > 9) {
               return false;
            }
            if (key != 0) {
               int val = map.get(key);
               val++;
               map.put(key, val);   
            }
         }
      }
      for(int v: map.values()) {
         if(v != 9) {
            //System.out.println("Not Solved");
            return false;
         }
      }   
      return true; 
   } 
   
      
   public String toString() {
      String result = "";
      String line = "+-------+-------+-------+\n";

      for (int r = 0; r < 9; r++) {
         if (r % 3 == 0) {
            result = result + line;
         }
         for (int c = 0; c < 9; c++) {
            if (c % 3 == 0) {
               result = result + "| ";
            }
            if (board[r][c] == 0) {
                result = result + "- "; 
            } else {
                result = result + board[r][c] + " ";
            }
         }
        result = result + "|\n";
      }
      result = result + line;
      return result;
   }
}
