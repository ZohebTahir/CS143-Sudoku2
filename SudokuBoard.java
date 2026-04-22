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
      Set<Integer> isUnique = new TreeSet<>(); 
      
      for(int r = 0; r < board.length; r++) {
         for(int c = 0; c < board[r].length; c++) {
            if(!isUnique.contains(board[r][c]) && board[r][c] != 0) {
               isUnique.add(board[r][c]);
            } else {
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
