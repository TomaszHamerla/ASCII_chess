package org.example.pieces;

import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.king.King;
import org.example.service.BoardService.ChessBoardService;

import java.util.Optional;

public class UtilsOperation {
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

    //TODO
    public static boolean isCheckmateSituation(boolean whiteTurn, ChessBoardService chessBoardService) {

        return false;
    }

    public static void removePiece(String expectPawnLocation, ChessBoardService chessBoardService) {
        Piece enemy = chessBoardService.getPiece(expectPawnLocation).get();
        chessBoardService.getPieces().remove(enemy);
    }

    public static void removePieceArr(String expectPawnLocation, ChessBoardService chessBoardService) {
        char[][] chessBoardArr = chessBoardService.getChessBoardArr();
        int indexLetter = getIndexLetterArr(expectPawnLocation.charAt(0));
        int indexNumber = getIndexNumberArr(expectPawnLocation.charAt(1) - '0');
        chessBoardArr[indexNumber][indexLetter] = '-';
    }


    public static boolean isEnemyOnExpectLocation(Color color, String expectPawnLocation, ChessBoardService chessBoardService) {
        Optional<Piece> piece = chessBoardService.getPiece(expectPawnLocation);
        if (piece.isPresent()) {
            return color != piece.get().getColor();
        }
        return false;
    }

    private static Optional<Piece> getPieceWhoCanCheck(ChessBoardService chessBoardService, Color color, String kingPosition) {
        return chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() != color)
                        && (p.isMoveValid(getPPosition(p), kingPosition)))
                .findFirst();
    }

    public static boolean isKingUnderAttack( ChessBoardService chessBoardService) {
        Color color = getCurrentColor(chessBoardService.getWhiteTurn());
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

    public static int getIndexLetterArr(char letter) {
        switch (letter) {
            case 'A' -> letter = '0';
            case 'B' -> letter = '1';
            case 'C' -> letter = '2';
            case 'D' -> letter = '3';
            case 'E' -> letter = '4';
            case 'F' -> letter = '5';
            case 'G' -> letter = '6';
            case 'H' -> letter = '7';
            default -> throw new PieceException(PieceExceptionMessage.PIECE_LOCATION_NOT_FOUND);
        }
        return letter - '0';
    }

    public static int getIndexNumberArr(int num) {
        switch (num) {
            case 8 -> num = 0;
            case 7 -> num = 1;
            case 6 -> num = 2;
            case 5 -> num = 3;
            case 4 -> num = 4;
            case 3 -> num = 5;
            case 2 -> num = 6;
            case 1 -> num = 7;
            default -> throw new PieceException(PieceExceptionMessage.PIECE_LOCATION_NOT_FOUND);
        }
        return num;
    }
}
