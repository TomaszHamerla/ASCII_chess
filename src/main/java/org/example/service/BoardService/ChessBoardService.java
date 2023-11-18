package org.example.service.BoardService;

import org.example.pieces.Piece;

import java.util.List;
import java.util.Optional;

public interface ChessBoardService {
    void printChessBoardArr(char[][] chessBoard);
    void updatePositionArr(String pawnLocation, String expectPawnLocation);

    boolean isFieldOccupied(String pawnLocation);
    void movePiece(String pawnLocation, String expectPawnLocation);

  //  void validPawnLocation(String pawnLocation);

  //  void validExpectPawnLocation(String expectPawnLocation);
    List<Piece> getPieces();
    Optional<Piece> getPiece(String pawnLocation);
    void saveMove(String start, String end);
    List<String> getSavedMoves();
}
