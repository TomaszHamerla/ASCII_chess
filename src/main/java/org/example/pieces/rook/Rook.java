package org.example.pieces.rook;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.pieces.Piece;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.Color;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Rook implements Piece {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;

    @Override
    public void Move(String start, String end) {
        if (!isMoveValid(start, end)) {
            throw new PawnException(PawnExceptionMessage.INVALID_MOVE);
        } else
            chessBoardService.updatePosition(start, end);
        coordinateLetter = end.charAt(0);
        coordinateNumber = end.charAt(1) - '0';

    }

    @Override
    public boolean isMoveValid(String start, String end) {
        if ((validLetter(start.charAt(0), end.charAt(0)) || validNumber(start.charAt(1) - '0', end.charAt(1) - '0')) && isFieldsOccupied(start, end)) {
            return true;
        }
        return false;

    }

    private boolean isFieldsOccupied(String start, String end) {
        for (String field : getFieldsBetween(start, end)) {
            if (end.equals(field)){
                if (chessBoardService.isFieldOccupied(field)){
                    Piece piece = getChessBoardService().getPiece(field).get();
                    Piece pieceFromStartLocation = chessBoardService.getPiece(start).get();
                    if (piece.getColor()!=pieceFromStartLocation.getColor()){
                        return true;
                    }
                    throw new PawnException(PawnExceptionMessage.INVALID_OPERATION);
                }return true;

            }
            if ( chessBoardService.isFieldOccupied(field)){
                throw new PawnException(PawnExceptionMessage.INVALID_MOVE);
            }

        }return false;
    }

    private List<String> getFieldsBetween(String start, String end) {
        List<String> fieldsBetween = new ArrayList<>();
        if (validLetter(start.charAt(0), end.charAt(0))) {
            if (start.charAt(1) - '0' < end.charAt(1) - '0') {
                for (int i = start.charAt(1) - '0' + 1; i <= end.charAt(1) - '0'; i++)
                    fieldsBetween.add(start.charAt(0) + String.valueOf(i));
            } else{
                for (int i = start.charAt(1) - '0' - 1; i >= end.charAt(1) - '0'; i--){

                    fieldsBetween.add(start.charAt(0) + String.valueOf(i));
                }
            }


        } if (start.charAt(0) < end.charAt(0)) {
            for (char c = (char) (start.charAt(0) + 1); c <= end.charAt(0); c++) {
                fieldsBetween.add(String.valueOf(c) + start.charAt(1));
            }
        } else
            for (char c = (char) (start.charAt(0) - 1); c >= end.charAt(0); c--) {
                fieldsBetween.add(String.valueOf(c) + start.charAt(1));

            }
        return fieldsBetween;
    }

    private boolean validNumber(int start, int end) {
        return start == end;
    }

    private boolean validLetter(char start, char end) {
        return start == end;
    }


}
