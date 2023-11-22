package org.example.pieces.UtilsOperationTest;

import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.bishop.Bishop;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.pieces.pawn.Pawn;
import org.example.pieces.queen.Queen;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IsKingUnderAttackTest {
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
        chessBoard.setWhiteTurn(false);
    }

    @Test
    void isKingUnderAttack_whenQueenCanCheck() {
        Queen queen = new Queen(chessBoardService,Color.WHITE,'E',1);
        pieces.add(queen);

        Assertions.assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        queen.setCoordinateLetter('A');
        queen.setCoordinateNumber(4);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        queen.setCoordinateLetter('E');
        queen.setCoordinateNumber(1);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));
    }
    @Test
    void isKingUnderAttack_whenBishopCanCheck(){
        Bishop bishop = new Bishop(chessBoardService,Color.WHITE,'G',6);
        pieces.add(bishop);

        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        bishop.setCoordinateLetter('B');
        bishop.setCoordinateNumber(5);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));
    }
    @Test
    void isKingUnderAttack_whenKnightCanCheck(){
        Knight knight = new Knight(chessBoardService,Color.WHITE,'C',7);
        pieces.add(knight);

        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        knight.setCoordinateLetter('G');
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        knight.setCoordinateLetter('D');
        knight.setCoordinateNumber(6);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        knight.setCoordinateLetter('F');
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));
    }
    @Test
    void isKingUnderAttack_whenRookCanCheck(){
        Rook rook = new Rook(chessBoardService,Color.WHITE,'B',8);
        pieces.add(rook);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        rook.setCoordinateLetter('H');
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        rook.setCoordinateLetter('E');
        rook.setCoordinateNumber(1);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));
    }
    @Test
    void isKingUnderAttack_whenPawnCanCheck(){
        Pawn pawn = new Pawn(chessBoardService,Color.WHITE,'D',7);
        pieces.add(pawn);
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));

        pawn.setCoordinateLetter('F');
        assertTrue(UtilsOperation.isKingUnderAttack(chessBoardService));
    }
    private King getKing() {
        return new King(chessBoardService, Color.BLACK, 'E', 8);
    }
}