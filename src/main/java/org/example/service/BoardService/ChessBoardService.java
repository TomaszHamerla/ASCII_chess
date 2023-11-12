package org.example.service.BoardService;

public interface ChessBoardService {
    void printChessBoard(char[][] chessBoard);
    void updatePosition(String pawnLocation, String expectPawnLocation);

    boolean isFieldOccupied(String pawnLocation);
    void movePiece(String pawnLocation, String expectPawnLocation);

    void validPawnLocation(String pawnLocation);

    void validExpectPawnLocation(String expectPawnLocation);
}
