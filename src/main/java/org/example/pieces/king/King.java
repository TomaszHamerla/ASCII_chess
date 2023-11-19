package org.example.pieces.king;

import lombok.Data;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.UtilsOperation;
import org.example.pieces.moves.Castling;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;


@Data
public class King implements Piece, Castling {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;
    private boolean isFirstMove = false;

    public King(ChessBoardService chessBoardService, Color color, char coordinateLetter, int coordinateNumber) {
        this.chessBoardService = chessBoardService;
        this.color = color;
        this.coordinateLetter = coordinateLetter;
        this.coordinateNumber = coordinateNumber;
    }

    @Override
    public void Move(String start, String end) {
        if (isCastlingValid(start, end) && !UtilsOperation.isKingUnderAttack(chessBoardService)) {
            moveAfterCastle(start, end);
        } else if (!isMoveValid(start, end)) {
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        } else {
            move(start, end);
        }
    }


    @Override
    public boolean isMoveValid(String start, String end) {

        return checkCoordinate(start, end) && !isFieldsOccupied(start, end);
    }

    private void move(String start, String end) {
        isFirstMove = true;
        chessBoardService.updatePositionArr(start, end);
        coordinateLetter = end.charAt(0);
        coordinateNumber = end.charAt(1) - '0';
    }

    @Override
    public void moveAfterCastle(String pawnLocation, String expectPawnLocation) {
        if (expectPawnLocation.charAt(0) == 'H') {
            King king = (King) chessBoardService.getPiece(pawnLocation).get();
            Rook rook = (Rook) chessBoardService.getPiece(expectPawnLocation).get();
            String kingLocation = "G" + expectPawnLocation.charAt(1);
            king.move(pawnLocation, kingLocation);
            String rookLocation = "F" + expectPawnLocation.charAt(1);
            rook.moveAfterCastle(expectPawnLocation, rookLocation);
        } else {
            King king = (King) chessBoardService.getPiece(pawnLocation).get();
            Rook rook = (Rook) chessBoardService.getPiece(expectPawnLocation).get();
            String kingLocation = "C" + expectPawnLocation.charAt(1);
            king.move(pawnLocation, kingLocation);
            String rookLocation = "D" + expectPawnLocation.charAt(1);
            rook.moveAfterCastle(expectPawnLocation, rookLocation);
        }

    }

    public boolean isCastlingValid(String kingLocation, String rookLocation) {
        if (kingLocation.equals("E1")) {
            King king = (King) chessBoardService.getPiece(kingLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
            if (!king.isFirstMove()) {
                if (rookLocation.equals("A1")) {
                    Rook rook = (Rook) chessBoardService.getPiece(rookLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
                    if (!rook.isFirstMove()) {
                        if (!chessBoardService.isFieldOccupied("D1") && !chessBoardService.isFieldOccupied("C1") && !chessBoardService.isFieldOccupied("B1")) {
                            UtilsOperation.isMoveAllowed(kingLocation, "D1", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "C1", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "B1", chessBoardService);
                            return true;
                        }
                    }
                    return false;
                } else if (rookLocation.equals("H1")) {
                    Rook rook = (Rook) chessBoardService.getPiece(rookLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
                    if (!rook.isFirstMove()) {
                        if (!chessBoardService.isFieldOccupied("F1") && !chessBoardService.isFieldOccupied("G1")) {
                            UtilsOperation.isMoveAllowed(kingLocation, "F1", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "G1", chessBoardService);
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        if (kingLocation.equals("E8")) {
            King king = (King) chessBoardService.getPiece(kingLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
            if (!king.isFirstMove()) {
                if (rookLocation.equals("A8")) {
                    Rook rook = (Rook) chessBoardService.getPiece(rookLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
                    if (!rook.isFirstMove()) {
                        if (!chessBoardService.isFieldOccupied("D8") && !chessBoardService.isFieldOccupied("C8") && !chessBoardService.isFieldOccupied("B8")) {
                            UtilsOperation.isMoveAllowed(kingLocation, "D8", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "C8", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "B8", chessBoardService);
                            return true;
                        }
                    }
                    return false;
                } else if (rookLocation.equals("H8")) {
                    Rook rook = (Rook) chessBoardService.getPiece(rookLocation).orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
                    if (!rook.isFirstMove()) {
                        if (!chessBoardService.isFieldOccupied("F8") && !chessBoardService.isFieldOccupied("G8")) {
                            UtilsOperation.isMoveAllowed(kingLocation, "F8", chessBoardService);
                            UtilsOperation.isMoveAllowed(kingLocation, "G8", chessBoardService);
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkCoordinate(String start, String end) {
        if (validNumber(start.charAt(1) - '0', end.charAt(1) - '0')) {
            return validSecondCoordinate(start.charAt(0), end.charAt(0));
        } else if (validLetter(start.charAt(0), end.charAt(0))) {
            return validSecondCoordinate(start.charAt(1), end.charAt(1));
        } else return validCrossMove(start, end);
    }

    private boolean isFieldsOccupied(String start, String field) {
        return UtilsOperation.isTeamMatePieceAtLocation(start, field, chessBoardService);
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
