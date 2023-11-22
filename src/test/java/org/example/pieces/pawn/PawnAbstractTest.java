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
    private List<String> moves;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard();
        chessBoardServiceImp = new ChessBoardServiceImp(chessBoard);
        list = new ArrayList<Piece>();
        moves = new ArrayList<String>();
    }

    @Test
    void movePawnOneField() {

        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "A3"));
    }

    @Test
    void movePawnTwoFieldsTrue() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "A4"));
    }

    @Test
    void movePawnTwoFieldsFalse() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        pawn.setItsFirstMove(false);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "A4"));
    }

    @Test
    void movePawnCaptureTrue() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'B', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertTrue(pawn.isMoveValid("A2", "B3"));
    }

    @Test
    void movePawnCaptureEmptyFieldFalse() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        list.add(pawn);
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "B3"));
    }

    @Test
    void movePawnCaptureAllayPawnFalse() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'B', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "B3"));
    }
    @Test
    void movePawnBlock() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 2);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'A', 3);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertFalse(pawn.isMoveValid("A2", "A3"));
    }
    @Test
    void moveElPassant_True() {
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 4);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'B', 4);
        moves.add("A2-A4");
        chessBoard.setMoves(moves);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertTrue(opponentPawn.isMoveValid("B4", "A3"));
    }
    @Test
    void moveElPassant_EmptyList_False(){
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 4);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'B', 4);
        list.addAll(Arrays.asList(pawn, opponentPawn));
        chessBoard.setPieces(list);
        assertFalse(opponentPawn.isMoveValid("B4", "A3"));
    }
    @Test
    void moveElPassant_LastMoveIsNotCorrect_False(){
        Pawn pawn = new Pawn(chessBoardServiceImp, Color.WHITE, 'A', 4);
        Pawn opponentPawn = new Pawn(chessBoardServiceImp, Color.BLACK, 'B', 4);
        Pawn placeholder = new Pawn(chessBoardServiceImp, Color.WHITE, 'C', 4);
        moves.add("C2-C4");
        chessBoard.setMoves(moves);
        list.addAll(Arrays.asList(pawn, opponentPawn, placeholder));
        chessBoard.setPieces(list);
        assertFalse(opponentPawn.isMoveValid("B4", "A3"));
    }


}