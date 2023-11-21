package org.example.pieces.moves;

public interface ValidatorForForwardMove {
    default boolean validateForwardMove(String start, String end){
        return (start.charAt(0) == end.charAt(0) || start.charAt(1) == end.charAt(1));
    }
}
