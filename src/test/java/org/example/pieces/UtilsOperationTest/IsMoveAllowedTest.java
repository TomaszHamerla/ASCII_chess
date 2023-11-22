package org.example.pieces.UtilsOperationTest;

import org.example.exception.PieceException;
import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;


public class IsMoveAllowedTest {
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
    void isMoveAllowed_whenKingGoingToFieldWhereIsCheck_shouldTrowPieceException(){
        Rook rook = new Rook(chessBoardService, Color.WHITE, 'A', 7);
        pieces.add(rook);
        Exception exception = assertThrows(PieceException.class, () -> UtilsOperation.isMoveAllowed("E8", "E7", chessBoardService));
        assertThat(exception).isInstanceOf(PieceException.class);
        assertThat(exception).hasMessage("Invalid move !");
    }
    @Test
    void isMoveAllowed_whenPieceMoveAndOpenKing_shouldTrowPieceException(){
        Rook rook = new Rook(chessBoardService, Color.WHITE, 'A', 8);
        Knight knight = new Knight(chessBoardService, Color.BLACK, 'B', 8);
        pieces.add(rook);
        pieces.add(knight);
        Exception exception = assertThrows(PieceException.class, () -> UtilsOperation.isMoveAllowed("B8", "A6", chessBoardService));
        assertThat(exception).isInstanceOf(PieceException.class);
        assertThat(exception).hasMessage("Invalid move !");
    }
    private King getKing() {
        return new King(chessBoardService, Color.BLACK, 'E', 8);
    }
}
