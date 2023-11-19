package org.example.pieces.knight;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.service.BoardService.ChessBoardService;

@Data
@AllArgsConstructor
public class Knight implements Piece {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;

    @Override
    public void Move(String start, String end) {
        if (!isMoveValid(start, end)) {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        } else {
            chessBoardService.updatePositionArr(start, end);
            coordinateLetter = end.charAt(0);
            coordinateNumber = end.charAt(1) - '0';
        }
    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return validExpectPosition(start, end) && !isFieldOccupied(start, end);
    }

    private boolean isFieldOccupied(String start, String field) {
        return UtilsOperation.isTeamMatePieceAtLocation(start, field, chessBoardService);
    }

    private boolean validExpectPosition(String start, String end) {
        char letterStart = start.charAt(0);
        char letterEnd = end.charAt(0);
        int numberStart = start.charAt(1) - '0';
        int numberEnd = end.charAt(1) - '0';
        if (letterStart + 2 == letterEnd || letterStart - 2 == letterEnd) {
            return validNumberPlus1(numberStart, numberEnd);
        } else if (letterStart + 1 == letterEnd || letterStart - 1 == letterEnd) {
            return validNumberPlus2(numberStart, numberEnd);
        } else {
            return false;
        }
    }

    private boolean validNumberPlus2(int numberStart, int numberEnd) {
        return (numberStart + 2 == numberEnd || numberStart - 2 == numberEnd);
    }

    private boolean validNumberPlus1(int start, int end) {
        return (start + 1 == end || start - 1 == end);
    }

}
