package org.example.pieces;

import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.Color;
import org.example.pieces.king.King;
import org.example.service.BoardService.ChessBoardService;

import java.util.List;
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
        Piece king = getKing(chessBoardService, piece);
        String kingPosition = getKingPosition(king);
        Optional<Piece> canCheck = chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() != color)
                        && !(p instanceof King)
                        && (p.isMoveValid(getPPosition(p), kingPosition)))
                .findFirst();
        if (canCheck.isPresent()) {
            setPieceCoordinate(piece, startLocation);
            throw new PawnException(PawnExceptionMessage.INVALID_MOVE);
        } else {
            setPieceCoordinate(piece, startLocation);
        }

    }

    private static String getKingPosition(Piece king) {
        return king.getCoordinateLetter() + Integer.toString(king.getCoordinateNumber());
    }

    private static String getPPosition(Piece p) {
        return p.getCoordinateLetter() + Integer.toString(p.getCoordinateNumber());
    }

    private static Piece getKing(ChessBoardService chessBoardService, Piece piece) {
        Color color = piece.getColor();
        Piece king = chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() == color)
                        && (p instanceof King))
                .findFirst()
                .orElseThrow(() -> new PawnException(PawnExceptionMessage.PIECE_NOT_FOUND));
        return king;
    }

    private static void setPieceCoordinate(Piece piece, String coordinate) {
        piece.setCoordinateLetter(coordinate.charAt(0));
        piece.setCoordinateNumber(coordinate.charAt(1) - '0');
    }
}
