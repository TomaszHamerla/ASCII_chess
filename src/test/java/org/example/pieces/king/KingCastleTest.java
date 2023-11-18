package org.example.pieces.king;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KingCastleTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;
    @BeforeEach
    void setUp(){
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
    }
    @Test
    void isCastlingValidMethodTest(){
        King king = new King(chessBoardService, Color.WHITE,'E',1);
        Rook rook = new Rook(chessBoardService, Color.WHITE,'H',1);
        king.setFirstMove(true);
        pieces.add(king);
        pieces.add(rook);
        chessBoard.setPieces(pieces);
        assertFalse(king.isCastlingValid("E1","H1"));
    }
}
