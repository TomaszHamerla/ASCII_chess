package org.example.pieces.moves;

public interface ValidatorForCrossMove {
   default boolean validateCrossMove(String start, String end){
       return Math.abs(start.charAt(0) - end.charAt(0)) == Math.abs(start.charAt(1) - end.charAt(1));
   }
}
