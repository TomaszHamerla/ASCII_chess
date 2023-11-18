package org.example.pieces.pawn;

import lombok.Data;
import org.example.pieces.Piece;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class PawnAbstract implements Piece {

    private final ChessBoardService chessBoardServiceImp;

    public PawnAbstract(ChessBoardService chessBoardServiceImp, Color color, char CoordinateLetter, int CoordinateNumber) {
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
        if (!isMoveValid(start, end)) {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        } else {
            chessBoardServiceImp.updatePosition(start, end);
            CoordinateLetter = end.charAt(0);
            CoordinateNumber = end.charAt(1) - '0';
            ItsFirstMove = false;
        }
    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return (isValidLetter(start, end) && isValidNumber(start, end)) && isFieldsNotOccupied(start, end);
    }

    private boolean isValidLetter(String start, String end) {
        return start.charAt(0) == end.charAt(0) || start.charAt(0) == end.charAt(0) + 1 || start.charAt(0) == end.charAt(0) - 1;
    }

    private boolean isValidNumber(String start, String end) {
        if (color == Color.WHITE) {
            return end.charAt(1) == start.charAt(1) + 1 || (ItsFirstMove && end.charAt(1) == start.charAt(1) + 2);
        } else {
            return end.charAt(1) == start.charAt(1) - 1 || (ItsFirstMove && end.charAt(1) == start.charAt(1) - 2);
        }
    }
    private boolean isFieldsNotOccupied(String start, String end) {
        for (String field : getFieldsBetween(start, end)) {
            if (start.charAt(0) != end.charAt(0)) {
                if (chessBoardServiceImp.isFieldOccupied(field)) {
                    return itIsCorrectCapture(start, end, field);
                } else  {
                    return !chessBoardServiceImp.getSavedMoves().isEmpty() && isEnPassantCapture(end, field);
                }
            } else if (chessBoardServiceImp.isFieldOccupied(field)) {
                return false;
            }
        }
        return true;
    }

    private List<String> getFieldsBetween(String startField, String endField) {
        Piece piece = chessBoardServiceImp.getPiece(startField).get();
        if (startField.charAt(0) != endField.charAt(0)) {
            return List.of(endField);
        }

        List<String> fieldsToValidate = new ArrayList<>();
        int startRow = Integer.parseInt(startField.substring(1));
        int endRow = Integer.parseInt(endField.substring(1));

        if (piece.getColor() == Color.WHITE) {
            for (int i = startRow + 1; i <= endRow; i++) {
                fieldsToValidate.add(startField.charAt(0) + String.valueOf(i));
            }
        } else {
            for (int i = startRow - 1; i >= endRow; i--) {
                fieldsToValidate.add(startField.charAt(0) + String.valueOf(i));
            }
        }
        return fieldsToValidate;
    }

    private boolean itIsCorrectCapture(String start, String end, String field) {
        boolean result = false;
        Piece pieceWithMakeMove = chessBoardServiceImp.getPiece(start).get();
        Piece pieceOnField = chessBoardServiceImp.getPiece(field).get();
        if (pieceWithMakeMove.getColor() != pieceOnField.getColor() && start.charAt(0) != end.charAt(0)) {
            result = true;
        }
        return result;
    }

    private boolean isEnPassantCapture(String end, String field) {
        boolean result = false;
        String lastMove = chessBoardServiceImp.getSavedMoves().get(chessBoardServiceImp.getSavedMoves().size() - 1);
        String lastMoveStart = lastMove.substring(0, 2);
        String lastMoveEnd = lastMove.substring(2, 4);
        List<String> lastMoveFields = getFieldsBetween(lastMoveStart, lastMoveEnd);
        boolean itWasFirstMoveByTwo = lastMoveFields.size() == 2;
        boolean itWasPawn = chessBoardServiceImp.getPiece(lastMoveEnd).get() instanceof Pawn;
        boolean itWasOpponentPawn = chessBoardServiceImp.getPiece(lastMoveEnd).get().getColor() != color;
        boolean fieldIsBetween = lastMoveFields.indexOf(field) == 0;
        boolean isDestinationFieldEmpty = !chessBoardServiceImp.isFieldOccupied(end);
        if (itWasFirstMoveByTwo && itWasPawn && itWasOpponentPawn && fieldIsBetween && isDestinationFieldEmpty) {
            result = true;
        }
        return result;
    }

}
