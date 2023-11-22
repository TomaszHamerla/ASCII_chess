package org.example.pieces.UtilsOperationTest;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.pieces.queen.Queen;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsCheckmateSituationTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;
    private King king;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
        king = getKing();
        pieces.add(king);
        chessBoard.setPieces(pieces);
        chessBoard.setWhiteTurn(false);
    }

    @Test
    void isCheckmateSituation_whenIsCheckmate_returnTrue() {
        Queen queen = new Queen(chessBoardService, Color.WHITE, 'A', 8);
        Rook rook = new Rook(chessBoardService, Color.WHITE, 'H', 7);
        pieces.add(rook);
        pieces.add(queen);
        assertTrue(UtilsOperation.isCheckmateSituation(chessBoardService));
    }
    @Test
    void isCheckmateSituation_whenIsNotMate_returnFalse(){
        Knight knight = new Knight(chessBoardService,Color.WHITE,'C',7);
        pieces.add(knight);
        assertFalse(UtilsOperation.isCheckmateSituation(chessBoardService));
    }

    private King getKing() {
        return new King(chessBoardService, Color.BLACK, 'E', 8);
    }
}
