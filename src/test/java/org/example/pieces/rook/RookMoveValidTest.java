package org.example.pieces.rook;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookMoveValidTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
    }

    @Test
    void isMoveValid() {
        Rook rook = new Rook(chessBoardService, Color.BLACK,'A',1);
        Rook rook1 = new Rook(chessBoardService, Color.BLACK,'A',2);
        pieces.add(rook);
        pieces.add(rook1);
        chessBoard.setPieces(pieces);
        assertFalse(rook.isMoveValid("A1","A8"));
        assertTrue(rook1.isMoveValid("A2","E2"));
        assertFalse(rook.isMoveValid("A2","E8"));
    }
}