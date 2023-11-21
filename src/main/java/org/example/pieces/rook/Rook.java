package org.example.pieces.rook;

import lombok.Data;
import org.example.pieces.Piece;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;

import org.example.pieces.UtilsOperation;
import org.example.pieces.moves.Castling;
import org.example.pieces.moves.ValidatorForForwardMove;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.List;

@Data
public class Rook implements Piece, ValidatorForForwardMove, Castling {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;
    private boolean isFirstMove = false;

    public Rook(ChessBoardService chessBoardService, Color color, char coordinateLetter, int coordinateNumber) {
        this.chessBoardService = chessBoardService;
        this.color = color;
        this.coordinateLetter = coordinateLetter;
        this.coordinateNumber = coordinateNumber;
    }

    @Override
    public void Move(String start, String end) {
        if (!isMoveValid(start, end)) {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        } else {
            move(start, end);
        }
    }

    @Override
    public void moveAfterCastle(String start, String end) {
        move(start, end);
    }


    @Override
    public boolean isMoveValid(String start, String end) {
        if ((validateForwardMove(start, end)) && !isFieldsOccupied(start, end)) {
            return true;
        }
        return false;

    }

    private void move(String start, String end) {
        isFirstMove = true;
        chessBoardService.updatePositionArr(start, end);
        coordinateLetter = end.charAt(0);
        coordinateNumber = end.charAt(1) - '0';
    }

    private boolean isFieldsOccupied(String start, String end) {
        for (String field : getFieldsBetween(start, end)) {
            if (end.equals(field)) {
                return UtilsOperation.isTeamMatePieceAtLocation(start, end, chessBoardService);
            }
            if (chessBoardService.isFieldOccupied(field)) {
                return true;
            }

        }
        return false;
    }

    public List<String> getFieldsBetween(String start, String end) {
        List<String> fieldsBetween = new ArrayList<>();
        if (validLetter(start.charAt(0), end.charAt(0))) {
            if (start.charAt(1) - '0' < end.charAt(1) - '0') {
                for (int i = start.charAt(1) - '0' + 1; i <= end.charAt(1) - '0'; i++)
                    fieldsBetween.add(start.charAt(0) + String.valueOf(i));
            } else {
                for (int i = start.charAt(1) - '0' - 1; i >= end.charAt(1) - '0'; i--) {

                    fieldsBetween.add(start.charAt(0) + String.valueOf(i));
                }
            }


        }
        if (start.charAt(0) < end.charAt(0)) {
            for (char c = (char) (start.charAt(0) + 1); c <= end.charAt(0); c++) {
                fieldsBetween.add(String.valueOf(c) + start.charAt(1));
            }
        } else
            for (char c = (char) (start.charAt(0) - 1); c >= end.charAt(0); c--) {
                fieldsBetween.add(String.valueOf(c) + start.charAt(1));

            }
        return fieldsBetween;
    }

    private boolean validLetter(char start, char end) {
        return start == end;
    }


}
