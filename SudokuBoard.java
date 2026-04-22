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

   public boolean isValid() {
      Set<Integer> isUniqueValid = new TreeSet<>();
      int row = board.length; 
      // Checking Rows
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            if(!isUniqueValid.contains(board[r][c]) && board[r][c] != 0) {
               isUniqueValid.add(board[r][c]);
            } else {
               System.out.print("Not valid row!");
               return false; 
            }
         }
      }
      // Checking Columns
      Set<Integer> isUniqueColumn = new TreeSet<>(); 
      for(int c = 0; c < board.length; c++) {
         for(int r = 0; r < board[c].length; r++) {
            if(!isUniqueColumn.contains(board[r][c]) && board[r][c] != 0) {
               isUniqueColumn.add(board[r][c]);
            } else {
               System.out.print("Not valid column!");
               return false; 
            }
         }
      }
      
      // Checking Mini Squares
      
      for(int i = 0; i < 9; i++) {
        Set<Integer> isUniqueMiniSquare = new TreeSet<>();
        int[][] miniArr = miniSquare(i);
        for(int r = 0; r < miniArr.length; r++) {
            for(int c = 0; c < miniArr[r].length; c++) {
               if (!isUniqueMiniSquare.contains(miniArr[r][c]) && miniArr[r][c] != 0) {
                  isUniqueMiniSquare.add(miniArr[r][c]);
               } else {
                  System.out.println("Not valid mini!");
                  return false;
               }
            }
        }   
       }
      
    System.out.print("is Valid");
    return true; 
   }
   
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
   
   public boolean isSolved() {
      if(isValid()){
      Map<Integer, Integer> solvedMap = new TreeMap<>();
      for (int i = 1; i < 10; i++) {
         solvedMap.put(i,0);
      }
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            if (board[r][c] != 0) {
               int value = solvedMap.get(board[r][c]);
               solvedMap.put(board[r][c], value++);   
            }
         }
      }
      for(int v: solvedMap.values()) {
         if(v != 9) {
            System.out.println("Not Solved");
            return false;
         }
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
