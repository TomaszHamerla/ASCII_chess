package org.example.pieces.knight;

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

class KnightTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;
    private Knight knight;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
        knight = getKnight();
        pieces.add(knight);
        chessBoard.setPieces(pieces);
    }

    @Test
    void isMoveValid_withCorrectDate() {
        assertTrue(knight.isMoveValid("B1", "A3"));
        assertTrue(knight.isMoveValid("D4", "B5"));
        assertTrue(knight.isMoveValid("G7", "H5"));
        assertTrue(knight.isMoveValid("E5", "F7"));
        assertTrue(knight.isMoveValid("C3", "D5"));
        assertTrue(knight.isMoveValid("H2", "F3"));
        assertTrue(knight.isMoveValid("A1", "B3"));
        assertTrue(knight.isMoveValid("F1", "D2"));
        assertTrue(knight.isMoveValid("C8", "B6"));
        assertTrue(knight.isMoveValid("H8", "G6"));
    }

    @Test
    void isMoveValid_withUnCorrectData() {
        assertFalse(knight.isMoveValid("E4", "F8"));
        assertFalse(knight.isMoveValid("D2", "E2"));
        assertFalse(knight.isMoveValid("G3", "G6"));
        assertFalse(knight.isMoveValid("B7", "C8"));
        assertFalse(knight.isMoveValid("H1", "H3"));
        assertFalse(knight.isMoveValid("F4", "G5"));
        assertFalse(knight.isMoveValid("C1", "D4"));
        assertFalse(knight.isMoveValid("G1", "E3"));
    }

    private Knight getKnight() {
        return new Knight(chessBoardService, Color.WHITE, 'B', 1);
    }
}