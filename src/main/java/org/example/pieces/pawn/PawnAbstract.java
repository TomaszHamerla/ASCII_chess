package org.example.pieces.pawn;

import lombok.Data;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.king.King;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public abstract  class PawnAbstract implements Piece {

    private final ChessBoardService chessBoardServiceImp;

    private Color color;
    private boolean ItsFirstMove;
    private char CoordinateLetter;
    private int CoordinateNumber;


    public PawnAbstract(ChessBoardService chessBoardServiceImp, char CoordinateLetter, int CoordinateNumber, Color color) {
        this.chessBoardServiceImp = chessBoardServiceImp;
        this.color = color;
        this.ItsFirstMove = true;
        this.CoordinateLetter = CoordinateLetter;
        this.CoordinateNumber = CoordinateNumber;

    }

    @Override
    public void Move(String start, String end) {
        if (isMoveValid(start, end)) {
            if (itIsRegularCapture(start, end)){
                UtilsOperation.removePiece(end, chessBoardServiceImp);
            }
            chessBoardServiceImp.updatePositionArr(start, end);
            CoordinateLetter = end.charAt(0);
            CoordinateNumber = end.charAt(1) - '0';
            ItsFirstMove = false;
        }
        else {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        }
    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return (isValidLetter(start, end) && isValidNumber(start, end)) && (itIsRegularMove(start, end) || itIsCaptureMove(start, end));
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
    private boolean itIsRegularMove(String start, String end) {
        return !chessBoardServiceImp.isFieldOccupied(end) && start.charAt(0) == end.charAt(0);
    }
    private boolean itIsCaptureMove(String start, String end) {
        return  isValidCapture(start, end) && ( itIsRegularCapture(start, end) || itIsEnPassantCapture(start, end));
    }
    private boolean isValidCapture(String start, String end) {
        return start.charAt(0) != end.charAt(0) && (start.charAt(1) == end.charAt(1) + 1 || start.charAt(1) == end.charAt(1) - 1);
    }
    private boolean itIsRegularCapture(String start, String end) {
        boolean result = false;
        Piece pieceWithMakeMove = chessBoardServiceImp.getPiece(start).orElse(null);
        Piece pieceOnField = chessBoardServiceImp.getPiece(end).orElse(null);
        if (pieceWithMakeMove != null && pieceOnField != null && pieceWithMakeMove.getColor() != pieceOnField.getColor()) {
            result = true;
        }
        return result;
    }
    private  boolean itIsEnPassantCapture(String start, String end) {
        if(chessBoardServiceImp.getSavedMoves().isEmpty()) return false;
        String lastMove = chessBoardServiceImp.getSavedMoves().get(chessBoardServiceImp.getSavedMoves().size() - 1);
        String lastMoveStart = lastMove.substring(0, 2);
        String lastMoveEnd = lastMove.substring(3, 5);
        List<String> lastMoveFields = getFieldsBetween(lastMoveStart, lastMoveEnd);
        boolean itWasFirstMoveByTwo = lastMoveFields.size() == 2;
        boolean itWasPawn = chessBoardServiceImp.getPiece(lastMoveEnd).map(p -> p instanceof PawnAbstract).orElse(false);
        boolean itWasOpponentPawn = chessBoardServiceImp.getPiece(lastMoveEnd).map(p -> p.getColor() != color).orElse(false);
        boolean fieldIsBetween = !lastMoveFields.isEmpty() && lastMoveFields.indexOf(end) == 0;
        boolean isDestinationFieldEmpty = !chessBoardServiceImp.isFieldOccupied(end);
        boolean result = itWasFirstMoveByTwo && itWasPawn && itWasOpponentPawn && fieldIsBetween && isDestinationFieldEmpty;
        if (result) {
            Piece capturedPiece = chessBoardServiceImp.getPiece(lastMoveEnd).orElse(null);
            if (capturedPiece != null) {
                UtilsOperation.removePiece(lastMoveEnd, chessBoardServiceImp);
                UtilsOperation.removePieceArr(lastMoveEnd, chessBoardServiceImp);
            }
        }
        return result;
    }
    private List<String> getFieldsBetween(String startField, String endField) {
        Piece piece = chessBoardServiceImp.getPiece(startField).orElse(chessBoardServiceImp.getPiece(endField).orElse(null));
        if (piece == null) {
            return Collections.emptyList();
        }
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



}
