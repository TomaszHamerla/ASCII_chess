package org.example.pieces.king;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class KingMoveValidTest {

    private ChessBoard chessBoard;// = new ChessBoard();
    private ChessBoardService chessBoardService;// = new ChessBoardServiceImp(chessBoard);
    private  List<Piece>pieces ;//= new ArrayList<>() ;

    @BeforeEach
    public void setUp(){
        chessBoard=new ChessBoard();
        chessBoardService=new ChessBoardServiceImp(chessBoard);
        pieces = new ArrayList<>();
    }

    @Test
     void testValidLettersOpportunitiesMethod() {
        King king = new King(chessBoardService,Color.BLACK,'A',1);;
        assertTrue(king.validSecondCoordinate('A', 'B'));
        assertTrue(king.validSecondCoordinate('B', 'A'));
        assertTrue(king.validSecondCoordinate('2', '3'));
        assertTrue(king.validSecondCoordinate('B', 'C'));
        assertFalse(king.validSecondCoordinate('A','C'));
        assertFalse(king.validSecondCoordinate('C','A'));
        assertFalse(king.validSecondCoordinate('1','3'));
    }
    @Test
     void testIsMoveValidMethod(){
        King king = new King(chessBoardService,Color.BLACK,'A',1);;
        pieces.add(king);
        chessBoard.setPieces(pieces);
        assertTrue(king.isMoveValid("A2","A3"));
        assertFalse(king.isMoveValid("H3","D3"));
        assertTrue(king.isMoveValid("C2","B2"));
        assertTrue(king.isMoveValid("C2","C3"));
        assertTrue(king.isMoveValid("B3","B2"));
        assertTrue(king.isMoveValid("A2","B3"));
        assertTrue(king.isMoveValid("E2","D3"));
    }
    //written by TDD method ->
    @Test
     void testValidCrossMoveMethod(){
        King king = new King(chessBoardService,Color.BLACK,'A',1);;
        assertTrue(king.validCrossMove("B2","A3"));
        assertFalse(king.validCrossMove("B2","A5"));
        assertTrue(king.validCrossMove("E4","D5"));
        assertTrue(king.validCrossMove("E4","D3"));
        assertTrue(king.validCrossMove("E4","F3"));
        assertTrue(king.validCrossMove("E4","F5"));
        assertFalse(king.validCrossMove("E4","F2"));
        assertTrue(king.validCrossMove("B3","A4"));
    }
}