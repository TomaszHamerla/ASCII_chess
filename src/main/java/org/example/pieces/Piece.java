package org.example.pieces;

import org.example.model.Color;

public interface Piece {
     void Move(String start, String end);
     char getCoordinateLetter();
     int getCoordinateNumber();
     Color getColor();
     boolean isMoveValid (String start, String end);
     void setCoordinateLetter(char letter);
     void setCoordinateNumber(int number);

}
