package org.example.Pieces;

public interface Piece {
     void Move(String start, String end);
     char getCoordinateLetter();
     int getCoordinateNumber();
}
