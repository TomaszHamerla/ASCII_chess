package org.example.Pieces;

import lombok.Data;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.service.ChessBoardService;

@Data
public abstract class PawnAbstract implements Piece {

    private final ChessBoard chessBoard;
    private final ChessBoardService chessBoardServiceImp;

    public PawnAbstract(ChessBoard chessBoard, ChessBoardService chessBoardServiceImp, Color color, char CoordinateLetter, int CoordinateNumber ) {
        this.chessBoard = chessBoard;
        this.chessBoardServiceImp = chessBoardServiceImp;
        this.color = color;
        this.ItsFirstMove = true;
        this.CoordinateLetter = CoordinateLetter;
        this.CoordinateNumber = CoordinateNumber;
        this.isCaptured = false;

    }

    private Color color;
    private boolean ItsFirstMove;
    private char CoordinateLetter;
    private int CoordinateNumber;
    // czy pionek został zbity
    private boolean isCaptured;
    @Override
    public void Move(String start, String end) {

        if (!isMoveValid(start, end))
        {
            throw new PawnException(PawnExceptionMessage.INVALID_MOVE);
        }
        else
        {
            chessBoardServiceImp.updatePosition(start, end);
            CoordinateLetter = end.charAt(0);
            CoordinateNumber = end.charAt(1)-'0';
            ItsFirstMove = false;
        }



    }
    private boolean isValidLetter (String start, String end) {
        return start.charAt(0) == end.charAt(0);
    }
    private boolean isValidNumber(String start, String end) {
        if (color == Color.WHITE) {
            return end.charAt(1) == start.charAt(1) + 1 || (ItsFirstMove && end.charAt(1) == start.charAt(1) + 2);
        } else {
            return end.charAt(1) == start.charAt(1) - 1 || (ItsFirstMove && end.charAt(1) == start.charAt(1) - 2);
        }
    }
    private boolean areFieldsOccupied (String start, String end) {
        for (int i = start.charAt(1); i <= end.charAt(1); i++) {
            if (chessBoardServiceImp.isFieldOccupied(start.charAt(0) + String.valueOf(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isMoveValid (String start, String end) {
        return isValidLetter(start, end) && isValidNumber(start, end) && areFieldsOccupied(start, end);
    }
}
