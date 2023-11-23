package org.example.pieces.UtilsOperationTest;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsStalemateTest {
    private ChessBoard chessBoard;
    private ChessBoardService chessBoardService;
    private List<Piece> pieces;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
        chessBoardService = new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
        chessBoard.setPieces(pieces);
        chessBoard.setWhiteTurn(false);
    }
    @Test
    void isRoyalPairPresent_withOnly2kings_returnsTrue(){
        King whiteKing = new King(chessBoardService, Color.WHITE,'E',5);
        King blackKing = new King(chessBoardService, Color.BLACK,'E',4);
        pieces.add(blackKing);
        pieces.add(whiteKing);
        assertTrue(UtilsOperation.isStalemate(chessBoardService));
    }
    @Test
    void isRoyalPairPresent_withMoreThen2Kings_returnFalse(){
        King whiteKing = new King(chessBoardService, Color.WHITE,'E',5);
        King blackKing = new King(chessBoardService, Color.BLACK,'E',4);
        Knight knight = new Knight(chessBoardService, Color.BLACK,'D',4);
        pieces.add(knight);
        pieces.add(blackKing);
        pieces.add(whiteKing);
        assertFalse(UtilsOperation.isStalemate(chessBoardService));
    }
}
