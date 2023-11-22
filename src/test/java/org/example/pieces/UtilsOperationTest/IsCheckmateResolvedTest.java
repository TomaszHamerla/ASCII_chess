package org.example.pieces.UtilsOperationTest;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.king.King;
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

public class IsCheckmateResolvedTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;
    private King king;
    @BeforeEach
    void setUp(){
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
        king = getKing();
        pieces.add(king);
        chessBoard.setPieces(pieces);
    }
    @Test
    void isCheckmateResolved_whenChooseWrongLocation_returnFalse(){
        Queen queen = new Queen(chessBoardService, Color.WHITE, 'A', 8);
        pieces.add(queen);
        assertFalse(UtilsOperation.isCheckmateResolved("E8","D8",chessBoardService));
    }
    @Test
    void isCheckMateResolved_whenChooseCorrectLocation_returnTrue(){
        Rook rook = new Rook(chessBoardService, Color.WHITE, 'A', 8);
        pieces.add(rook);
        assertTrue(UtilsOperation.isCheckmateResolved("E8","E7",chessBoardService));
    }
    private King getKing() {
        return new King(chessBoardService, Color.BLACK, 'E', 8);
    }
}
