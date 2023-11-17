package org.example.pieces;

import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.king.King;
import org.example.service.BoardService.ChessBoardService;

import java.util.Optional;

public class PieceValidator {
    public static boolean isTeamMatePieceAtLocation(String start, String end, ChessBoardService chessBoardService) {
        if (chessBoardService.isFieldOccupied(end)) {
            Piece piece = chessBoardService.getPiece(end).get();
            Piece pieceFromStartLocation = chessBoardService.getPiece(start).get();
            if (piece.getColor() != pieceFromStartLocation.getColor()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // The method checks whether your king is in check after you make a move.
    public static void isMoveAllowed(String startLocation, String expectLocation, ChessBoardService chessBoardService) {
        Piece piece = chessBoardService.getPiece(startLocation).get();
        setPieceCoordinate(piece, expectLocation);
        Color color = piece.getColor();
        Piece king = getKing(chessBoardService, color);
        String kingPosition = getKingPosition(king);
        Optional<Piece> canCheck = getPieceWhoCanCheck(chessBoardService, color, kingPosition);
        if (canCheck.isPresent()) {
            setPieceCoordinate(piece, startLocation);
            throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
        } else {
            setPieceCoordinate(piece, startLocation);
        }

    }


    public static boolean isCheckmateResolved(String startLocation, String expectLocation, ChessBoardService chessBoardService) {
        Piece piece = chessBoardService.getPiece(startLocation).get();
        setPieceCoordinate(piece, expectLocation);
        Color color = piece.getColor();
        Piece king = getKing(chessBoardService, color);
        String kingPosition = getKingPosition(king);
        Optional<Piece> pieceWhoCanCheck = getPieceWhoCanCheck(chessBoardService, color, kingPosition);
        if (pieceWhoCanCheck.isPresent()) {
            setPieceCoordinate(piece, startLocation);
            return false;
        } else {
            setPieceCoordinate(piece, startLocation);
            return true;
        }
    }
    public static boolean isCheckmateSituation(boolean whiteTurn,ChessBoardService chessBoardService){
        return false;
    }

    private static Optional<Piece> getPieceWhoCanCheck(ChessBoardService chessBoardService, Color color, String kingPosition) {
        Optional<Piece> canCheck = chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() != color)
                        && (p.isMoveValid(getPPosition(p), kingPosition)))
                .findFirst();
        return canCheck;
    }

    public static boolean isKingUnderAttack(boolean whiteTurn, ChessBoardService chessBoardService) {
        Color color = getCurrentColor(whiteTurn);
        Piece king = getKing(chessBoardService, color);
        String kingPosition = getKingPosition(king);
        Optional<Piece> isCheck = chessBoardService.getPieces().stream()
                .filter(p -> p.getColor() != color
                        && p.isMoveValid(getPPosition(p), kingPosition))
                .findFirst();
        return isCheck.isPresent();

    }

    private static Color getCurrentColor(boolean whiteTurn) {
        Color color;
        if (whiteTurn) {
            color = Color.WHITE;
        } else {
            color = Color.BLACK;
        }
        return color;
    }

    private static String getKingPosition(Piece king) {
        return king.getCoordinateLetter() + Integer.toString(king.getCoordinateNumber());
    }

    private static String getPPosition(Piece p) {  /// method using to help valid every Piece in stream
        return p.getCoordinateLetter() + Integer.toString(p.getCoordinateNumber());
    }

    private static Piece getKing(ChessBoardService chessBoardService, Color color) {
        return chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() == color)
                        && (p instanceof King))
                .findFirst()
                .orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
    }

    private static void setPieceCoordinate(Piece piece, String coordinate) {
        piece.setCoordinateLetter(coordinate.charAt(0));
        piece.setCoordinateNumber(coordinate.charAt(1) - '0');
    }
}
