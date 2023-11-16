package org.example.pieces.king;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.PieceValidator;
import org.example.service.BoardService.ChessBoardService;

@AllArgsConstructor
@Data
public class King implements Piece {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;

    @Override
    public void Move(String start, String end) {
        if (!isMoveValid(start, end)){
            throw new PawnException(PawnExceptionMessage.INVALID_MOVE);
        } else
            chessBoardService.updatePosition(start, end);
        coordinateLetter = end.charAt(0);
        coordinateNumber = end.charAt(1) - '0';
    }

    @Override
    public boolean isMoveValid(String start, String end) {

        return checkCoordinate(start,end) || !isFliedOccupied(start,end);
    }
    private boolean checkCoordinate(String start,String end){
        if (validNumber(start.charAt(1) - '0', end.charAt(1) - '0')) {
            return validSecondCoordinate(start.charAt(0), end.charAt(0));
        } else if (validLetter(start.charAt(0), end.charAt(0))) {
            return validSecondCoordinate(start.charAt(1), end.charAt(1));
        } else return validCrossMove(start, end);
    }

    private boolean isFliedOccupied(String start, String field) {
        return PieceValidator.isTeamMatePieceAtLocation(start, field, chessBoardService);
    }

    public boolean validCrossMove(String start, String end) {
        return (start.charAt(0) + 1 == end.charAt(0) || start.charAt(0) - 1 == end.charAt(0)) && (start.charAt(1) - 1 == end.charAt(1) || start.charAt(1) + 1 == end.charAt(1));
    }

    public boolean validSecondCoordinate(char start, char end) {
        if (start - 1 == end) {
            return true;
        } else if (start + 1 == end) {
            return true;
        } else
            return false;
    }

    private boolean validNumber(int start, int end) {
        return start == end;
    }

    private boolean validLetter(char start, char end) {
        return start == end;
    }
}
