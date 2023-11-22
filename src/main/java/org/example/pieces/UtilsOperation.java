package org.example.pieces;

import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.Color;
import org.example.pieces.bishop.Bishop;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.pieces.pawn.PawnAbstract;
import org.example.pieces.queen.Queen;
import org.example.pieces.rook.Rook;
import org.example.service.BoardService.ChessBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class UtilsOperation {
    public static boolean isTeamMatePieceAtLocation(String start, String end, ChessBoardService chessBoardService) {
        if (chessBoardService.isFieldOccupied(end)) {
            Piece piece = chessBoardService.getPiece(end).get();
            Piece pieceFromStartLocation = chessBoardService.getPiece(start).get();
            return piece.getColor() == pieceFromStartLocation.getColor();
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
        List<Piece> pieceWhoCanCheck = getPiecesWhoCanCheck(chessBoardService, color, kingPosition);
        if (!pieceWhoCanCheck.isEmpty()) {
            if (pieceWhoCanCheck.size() == 1 && getPPosition(pieceWhoCanCheck.get(0)).equals(kingPosition)) {
                Piece pieceWhoIscChecking = pieceWhoCanCheck.get(0);
                String pieceWhoIsCheckingPosition = getPPosition(pieceWhoCanCheck.get(0));
                setPieceCoordinate(pieceWhoIscChecking, "00");
                if (!getPiecesWhoCanCheck(chessBoardService, color, kingPosition).isEmpty()) {
                    setPieceCoordinate(piece, startLocation);
                    setPieceCoordinate(pieceWhoIscChecking, pieceWhoIsCheckingPosition);
                    throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
                }
                setPieceCoordinate(pieceWhoIscChecking, pieceWhoIsCheckingPosition);
                setPieceCoordinate(piece, startLocation);
            } else {
                setPieceCoordinate(piece, startLocation);
                throw new PieceException(PieceExceptionMessage.INVALID_MOVE);
            }
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
        List<Piece> piecesWhoCanCheck = getPiecesWhoCanCheck(chessBoardService, color, kingPosition);
        if (!piecesWhoCanCheck.isEmpty()) {
            for (Piece p : piecesWhoCanCheck) {
                if (getPPosition(p).equals(kingPosition)) {
                    setPieceCoordinate(piece, startLocation);
                    return true;
                }
            }
            setPieceCoordinate(piece, startLocation);
            return false;
        } else {
            setPieceCoordinate(piece, startLocation);
            return true;
        }
    }

// szach mat wystepuje wtedy gdy:
    //1. Nie ma mozliwosci zbica figury ktora szchuje
    //2. Nie ma mozliwosci zasloniecia figury szachujacej
    //3. Krol nie ma dostepnego pola gdzie moze uciec
    public static boolean isCheckmateSituation(ChessBoardService chessBoardService) {
        Color color = getCurrentColor(chessBoardService.getWhiteTurn());
        Piece king = getKing(chessBoardService, color);
        String kingPosition = getKingPosition(king);
        List<Piece> piecesWhoCanCheck = getPiecesWhoCanCheck(chessBoardService, color, kingPosition);
        List<Piece> pieces = chessBoardService.getPieces().stream()
                .filter(p -> p.getColor().equals(color)).toList();
        if (isCapturePossible(piecesWhoCanCheck, pieces)) { // 1
            return false;
        } else if (isPossibleToCover(piecesWhoCanCheck, pieces, kingPosition)) { //2
            return false;
        } else if (isPossibleToMoveKing(chessBoardService, king)) {         //3
            return false;
        } else {
            return true;
        }
    }

    private static boolean isPossibleToMoveKing(ChessBoardService chessBoardService, Piece king) {
        String kingPosition = getKingPosition(king);
        for (String position : getFieldsAroundKing(king)) {
            try {
                isMoveAllowed(kingPosition, position, chessBoardService);
                return true;
            } catch (PieceException ignored) {
            }
        }
        return false;
    }

    private static List<String> getFieldsAroundKing(Piece king) {
        List<String> fields = new ArrayList<>();
        List<String> allFields = getAllFields();
        for (String filed : allFields) {
            if (king.isMoveValid(getKingPosition(king), filed)) {
                fields.add(filed);
            }
        }


        return null;
    }

    private static List<String> getAllFields() {
        List<String> allFields = new ArrayList<>();
        for (char i = 'A'; i < 'I'; i++) {
            for (int j = 1; j < 9; j++) {
                allFields.add(String.valueOf(i + j));
            }
        }
        return allFields;
    }

    private static boolean isPossibleToCover(List<Piece> piecesWhoCanCheck, List<Piece> pieces, String kingPosition) {
        if (isAnyWhoHaveFieldsBetween(piecesWhoCanCheck)) {
            return false;
        }
        for (Piece p : piecesWhoCanCheck) {
            if (p instanceof Rook) {
                Rook rook = (Rook) p;
                List<String> fieldsBetween = rook.getFieldsBetween(getPPosition(rook), kingPosition);
                return checkFieldsBetween(fieldsBetween, pieces);
            } else if (p instanceof Bishop) {
                Bishop bishop = (Bishop) p;
                List<String> fieldsBetween = bishop.getFieldsBetweenCross(getPPosition(bishop), kingPosition);
                return checkFieldsBetween(fieldsBetween, pieces);
            } else if (p instanceof Queen) {
                if (((Queen) p).validateForwardMove(getPPosition(p), kingPosition)) {
                    Queen q = (Queen) p;
                    List<String> fieldsBetween = ((Queen) p).getFieldsBetweenForward(getPPosition(p), kingPosition);
                    return checkFieldsBetween(fieldsBetween, pieces);
                } else {
                    Queen q = (Queen) p;
                    List<String> fieldsBetween = ((Queen) p).getFieldsBetweenCross(getPPosition(p), kingPosition);
                    return checkFieldsBetween(fieldsBetween, pieces);
                }
            }
        }
        return false;
    }

    private static boolean checkFieldsBetween(List<String> fieldsBetween, List<Piece> pieces) {
        for (String field : fieldsBetween) {
            for (Piece p : pieces) {
                if (p.isMoveValid(getPPosition(p), field)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isAnyWhoHaveFieldsBetween(List<Piece> piecesWhoCanCheck) {
        return piecesWhoCanCheck.stream()
                .anyMatch(p -> p instanceof Knight || p instanceof PawnAbstract);
    }

    private static boolean isCapturePossible(List<Piece> piecesWhoCanCheck, List<Piece> pieces) {
        for (Piece value : piecesWhoCanCheck) {                                     //sprawdza
            for (Piece piece : pieces) {                                                //czy jest jakakolwiek figura ktora
                if (piece.isMoveValid(getPPosition(piece), getPPosition(value))) {          // moze zaatakowac figure szachujaca
                    return true;
                }
            }
        }
        return false;
    }

    public static void removePiece(String expectPawnLocation, ChessBoardService chessBoardService) {
        Piece enemy = chessBoardService.getPiece(expectPawnLocation).get();
        if (enemy instanceof King) {
            throw new PieceException(PieceExceptionMessage.INVALID_PIECE_REMOVE);
        }
        chessBoardService.getPieces().remove(enemy);
    }

    public static void removePieceArr(String expectPawnLocation, ChessBoardService chessBoardService) {
        char[][] chessBoardArr = chessBoardService.getChessBoardArr();
        int indexLetter = getIndexLetterArr(expectPawnLocation.charAt(0));
        int indexNumber = getIndexNumberArr(expectPawnLocation.charAt(1) - '0');
        chessBoardArr[indexNumber][indexLetter] = '-';
    }

    public static void addPieceArr(String expectPawnLocation, ChessBoardService chessBoardService, char figure) {
        char[][] chessBoardArr = chessBoardService.getChessBoardArr();
        int indexLetter = getIndexLetterArr(expectPawnLocation.charAt(0));
        int indexNumber = getIndexNumberArr(expectPawnLocation.charAt(1) - '0');
        chessBoardArr[indexNumber][indexLetter] = figure;
    }

    public static boolean isEnemyOnExpectLocation(Color color, String expectPawnLocation, ChessBoardService chessBoardService) {
        Optional<Piece> piece = chessBoardService.getPiece(expectPawnLocation);
        if (piece.isPresent()) {
            return color != piece.get().getColor();
        }
        return false;
    }

    private static List<Piece> getPiecesWhoCanCheck(ChessBoardService chessBoardService, Color color, String kingPosition) {
        return chessBoardService.getPieces().stream()
                .filter(p -> (p.getColor() != color)
                        && (p.isMoveValid(getPPosition(p), kingPosition)))
                .collect(Collectors.toList());
    }

    public static boolean isKingUnderAttack(ChessBoardService chessBoardService) {
        Color color = getCurrentColor(chessBoardService.getWhiteTurn());
        Piece king = getKing(chessBoardService, color);
        String kingPosition = getKingPosition(king);
        List<Piece> pieceWhoCanCheck = getPiecesWhoCanCheck(chessBoardService, color, kingPosition);
        return !pieceWhoCanCheck.isEmpty();
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
