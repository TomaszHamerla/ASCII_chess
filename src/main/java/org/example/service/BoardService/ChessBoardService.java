package org.example.service.BoardService;

import org.example.Pieces.Piece;

import java.util.List;
import java.util.Optional;

public interface ChessBoardService {
    void printChessBoard(char[][] chessBoard);
    void updatePosition(String pawnLocation, String expectPawnLocation);

    boolean isFieldOccupied(String pawnLocation);
    void movePiece(String pawnLocation, String expectPawnLocation);

  //  void validPawnLocation(String pawnLocation);

  //  void validExpectPawnLocation(String expectPawnLocation);
    List<Piece> getPieces();
    Optional<Piece> getPiece(String pawnLocation);
}
