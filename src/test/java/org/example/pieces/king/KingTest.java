package org.example.pieces.king;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class KingTest {


    private King king = new King(new ChessBoardServiceImp(new ChessBoard()), Color.BLACK, 'a', 1);

    @Test
    public void testValidLettersOpportunitiesMethod() {
        assertTrue(king.validSecondCoordinate('A', 'B'));
        assertTrue(king.validSecondCoordinate('B', 'A'));
        assertTrue(king.validSecondCoordinate('2', '3'));
        assertTrue(king.validSecondCoordinate('B', 'C'));
        assertFalse(king.validSecondCoordinate('A','C'));
        assertFalse(king.validSecondCoordinate('C','A'));
        assertFalse(king.validSecondCoordinate('1','3'));
    }
    @Test
    public void testIsMoveValidMethod(){
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
    public void testValidCrossMoveMethod(){
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