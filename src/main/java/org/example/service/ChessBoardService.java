package org.example.service;

public interface ChessBoardService {
    void printChessBoard(char[][] chessBoard);
    void updatePosition(String pawnLocation, String expectPawnLocation);
    void removePawn(String pawnLocation);
    char getFigure(String pawnLocation);
    int getIndexLetter(char letter);
    int getIndexNumber(int num);
    boolean isFieldOccupied(String pawnLocation);
    void movePiece(String pawnLocation, String expectPawnLocation);
}
