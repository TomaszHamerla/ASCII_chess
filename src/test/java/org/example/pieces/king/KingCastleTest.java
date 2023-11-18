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
    void isCastlingValidMethod_withKingFirstMoveIsTrue_shouldReturnFalse(){
        King king = getWhiteKing();
        king.setFirstMove(true);
        pieces.add(king);
        chessBoard.setPieces(pieces);
        assertFalse(king.isCastlingValid("E1","H1"));
    }

    @Test
    void isCastValidMethod_withKingFirstMoveIsFalseAndRookFirstMoveIsTrue_shouldReturnFalse(){
        King king = getWhiteKing();
        Rook rook = getWhiteRook();
        rook.setFirstMove(true);
        pieces.add(king);
        pieces.add(rook);
        chessBoard.setPieces(pieces);
        assertFalse(king.isCastlingValid("E1","A1"));
    }
    @Test
    void isCastValidMethod_withKingFirstMoveIsFalseAndRookFirstMoveIsFalseForWhites_shouldReturnTrue(){
        King king = getWhiteKing();
        Rook rook = getWhiteRook();
        Rook rook1 = getWhiteRook();
        rook1.setCoordinateLetter('H');
        pieces.add(king);
        pieces.add(rook);
        pieces.add(rook1);
        chessBoard.setPieces(pieces);
        assertTrue(king.isCastlingValid("E1","A1"));
        assertTrue(king.isCastlingValid("E1","H1"));
    }
    @Test
    void isCastValidMethod_withKingFirstMoveIsFalseAndRookFirstMoveIsFalseForBlacks_shouldReturnTrue(){
        King king = getBlackKing();
        Rook rook = getBlackRook();
        Rook rook1 = getBlackRook();
        rook1.setCoordinateLetter('H');
        pieces.add(king);
        pieces.add(rook);
        pieces.add(rook1);
        chessBoard.setPieces(pieces);
        assertTrue(king.isCastlingValid("E8","A8"));
        assertTrue(king.isCastlingValid("E8","H8"));
    }

    private King getWhiteKing() {
        return new King(chessBoardService, Color.WHITE, 'E', 1);
    }
    private Rook getWhiteRook() {
        return new Rook(chessBoardService, Color.WHITE, 'A', 1);
    }
    private King getBlackKing() {
        return new King(chessBoardService, Color.BLACK, 'E', 8);
    }
    private Rook getBlackRook() {
        return new Rook(chessBoardService, Color.BLACK, 'A', 8);
    }
}
