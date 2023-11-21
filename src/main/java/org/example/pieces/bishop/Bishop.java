package org.example.pieces.bishop;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.moves.ValidatorForCrossMove;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Bishop implements Piece, ValidatorForCrossMove {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;

    @Override
    public void Move(String start, String end) {
        if (isMoveValid(start, end)) {
            chessBoardService.updatePositionArr(start, end);
            coordinateLetter = end.charAt(0);
            coordinateNumber = end.charAt(1) - '0';
        } else {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        }
    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return validateCrossMove(start, end)  && !isFieldOccupied(start, end);
    }

    @Override
    public boolean validateCrossMove(String start, String end) {
        return Math.abs(start.charAt(0) - end.charAt(0)) == Math.abs(start.charAt(1) - end.charAt(1));
    }
    private boolean isFieldOccupied(String start, String end) {
        boolean result = true;
        if (validateCrossMove(start, end)) {
            result = isFieldOccupiedCross(start, end);
        }
        return result;
    }
    private boolean isFieldOccupiedCross(String start, String end) {
        for (String field : getFieldsBetweenCross(start, end)){
            if (end.equals(field)){
                return UtilsOperation.isTeamMatePieceAtLocation(start, end, chessBoardService);
            }
            if (chessBoardService.isFieldOccupied(field)){
                return true;
            }
        }
        return false;
    }
    private List<String> getFieldsBetweenCross(String start, String end){
        List<String> fieldsBetween = new ArrayList<>();
        if (start.charAt(0) < end.charAt(0) && start.charAt(1) < end.charAt(1)) {
            for (char row = (char) (start.charAt(0) + 1), col = (char) (start.charAt(1) + 1); row <= end.charAt(0) && col <= end.charAt(1); row++, col++) {
                fieldsBetween.add(String.valueOf(row) + col);
            }
        } else if (start.charAt(0) > end.charAt(0) && start.charAt(1) < end.charAt(1)) {
            for (char row = (char) (start.charAt(0) - 1), col = (char) (start.charAt(1) + 1); row >= end.charAt(0) && col <= end.charAt(1); row--, col++) {
                fieldsBetween.add(String.valueOf(row) + col);
            }
        } else if (start.charAt(0) < end.charAt(0) && start.charAt(1) > end.charAt(1)) {
            for (char row = (char) (start.charAt(0) + 1), col = (char) (start.charAt(1) - 1); row <= end.charAt(0) && col >= end.charAt(1); row++, col--) {
                fieldsBetween.add(String.valueOf(row) + col);
            }
        } else if (start.charAt(0) > end.charAt(0) && start.charAt(1) > end.charAt(1)) {
            for (char row = (char) (start.charAt(0) - 1), col = (char) (start.charAt(1) - 1); row >= end.charAt(0) && col >= end.charAt(1); row--, col--) {
                fieldsBetween.add(String.valueOf(row) + col);
            }
        }
        return fieldsBetween;
    }
}
