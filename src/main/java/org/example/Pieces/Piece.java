package org.example.Pieces;

import org.example.model.Color;

public interface Piece {
     void Move(String start, String end);
     char getCoordinateLetter();
     int getCoordinateNumber();
     Color getColor();
}
