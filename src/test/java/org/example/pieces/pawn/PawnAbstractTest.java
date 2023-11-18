package org.example.pieces.pawn;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnAbstractTest {


    private ChessBoard chessBoard;
    private ChessBoardService chessBoardServiceImp;
    private List<Piece> list;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard();
        chessBoardServiceImp = new ChessBoardServiceImp(chessBoard);
        list = new ArrayList<Piece>();
    }

    @Test
    void movePawnOneField() {

        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "A3"));
    }

    @Test
    void movePawnTwoFieldsTrue() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "A4"));
    }

    @Test
    void movePawnTwoFieldsFalse() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        pawn.setItsFirstMove(false);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "A4"));
    }

    @Test
    void movePawnCaptureTrue() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        PawnAbstract opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'B', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "B3"));
    }

    @Test
    void movePawnCaptureEmptyFieldFalse() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "B3"));
    }

    @Test
    void movePawnCaptureAllayPawnFalse() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        PawnAbstract opponentPawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'B', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "B3"));
    }
    @Test
    void movePawnBlock() {
        PawnAbstract pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        PawnAbstract opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'A', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "A3"));
    }


}