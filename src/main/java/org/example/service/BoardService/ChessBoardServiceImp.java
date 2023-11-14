package org.example.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.example.Pieces.Piece;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.ChessBoard;
import org.example.model.Color;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

public class ChessBoardServiceImp implements ChessBoardService {
    private final ChessBoard chessBoard;
    @Override
    public void printChessBoard(char[][] chessBoard) {
        int a = 8;
        for (int i = 0; i < chessBoard.length; i++) {
            System.out.print(a + "    ");

            a--;
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("     ");
        for (char c = 'A'; c < 'I'; c++) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
    @Override
    public void movePiece(String pawnLocation, String expectPawnLocation) {
        Piece piece = getPiece(pawnLocation)
                .orElseThrow(() -> new PawnException(PawnExceptionMessage.PAWN_NOT_FOUND));
        if (isEnemyOnExpectLocation(piece.getColor(),expectPawnLocation)){
            removePiece(expectPawnLocation);
        }
        piece.Move(pawnLocation, expectPawnLocation);
    }
    @Override
    public void updatePosition(String pawnLocation, String expectPawnLocation) {
        char figure = getFigure(pawnLocation);
        removePawn(pawnLocation); //zamienia pionke na puste pole '-'
        int expectIndexLetter = getIndexLetter(expectPawnLocation.charAt(0));
        int expectIndexNumber = getIndexNumber(expectPawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[expectIndexNumber][expectIndexLetter] = figure;
    }
    @Override
    public boolean isFieldOccupied(String pawnLocation) {
        return chessBoard.getPieces()
                .stream()
                .anyMatch(piece -> piece.getCoordinateLetter() == pawnLocation.charAt(0) && piece.getCoordinateNumber() == pawnLocation.charAt(1) - '0');
//TODO -> moze isc do serwisu opartego o sama liste pionkow
    }

//    @Override
//    public void validPawnLocation(String pawnLocation) {
//        validLocations(pawnLocation);
//        char figure = getFigure(pawnLocation);
//        Optional<Piece> piece = getPiece(pawnLocation);
//        if (piece.isEmpty()){
//            throw new PawnException(PawnExceptionMessage.PAWN_NOT_FOUND);
//        }
//        if (!validTurn(piece.get().getColor())) {
//            throw new PawnException(PawnExceptionMessage.INVALID_TURN_MOVE);
//        }
//        if (figure == '-') {
//            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
//        }
//
//    }

//    @Override
//    public void validExpectPawnLocation(String expectPawnLocation) {
//        validLocations(expectPawnLocation);
//        char figure = getFigure(expectPawnLocation);
//
//        if (figure!='-'){ //TODO tutaj dodac || flaga canHit zeby moc zbic pionka ktorego chcemy
//            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
//        }
//    }

    @Override
    public List<Piece> getPieces() {
        return chessBoard.getPieces();
    }

    @Override
    public Optional<Piece> getPiece(String pawnLocation) {
        pawnLocation = pawnLocation.toUpperCase();
        char cordLetter = pawnLocation.charAt(0);
        int cordNumber = pawnLocation.charAt(1) - '0';
        List<Piece> pieces = chessBoard.getPieces();
        return pieces.stream()
                .filter(p -> p.getCoordinateLetter() == cordLetter && p.getCoordinateNumber() == cordNumber)
                .findFirst();
    }

    //    private boolean validTurn(Color color) {
//        if (chessBoard.isWhiteTurn() && Color.WHITE.equals(color)) {
//            return true;
//
//        } else if (!chessBoard.isWhiteTurn() && Color.BLACK.equals(color)) {
//            return true;
//        }
//        return false;
//    }
//    private void validLocations(String location) {
//        if (location.length() > 2) {
//            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
//        }
//
//    }
    private void removePiece(String expectPawnLocation) {
        Piece enemy = getPiece(expectPawnLocation).get();
        chessBoard.getPieces().remove(enemy);
    }
    private boolean isEnemyOnExpectLocation(Color color, String expectPawnLocation) {
        Optional<Piece> piece = getPiece(expectPawnLocation);
        if (piece.isPresent()){
            return color != piece.get().getColor();
        }
        return false;
    }
    private char getFigure(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1) - '0');
        return chessBoard.getChessBoard()[indexNumber][indexLetter];
    }
    private int getIndexLetter(char letter) {
        switch (letter) {
            case 'A' -> letter = '0';
            case 'B' -> letter = '1';
            case 'C' -> letter = '2';
            case 'D' -> letter = '3';
            case 'E' -> letter = '4';
            case 'F' -> letter = '5';
            case 'G' -> letter = '6';
            case 'H' -> letter = '7';
            default -> throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
        }
        return letter - '0';
    }
    private int getIndexNumber(int num) {
        switch (num) {
            case 8 -> num = 0;
            case 7 -> num = 1;
            case 6 -> num = 2;
            case 5 -> num = 3;
            case 4 -> num = 4;
            case 3 -> num = 5;
            case 2 -> num = 6;
            case 1 -> num = 7;
            default -> throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
        }
        return num;
    }
    private void removePawn(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[indexNumber][indexLetter] = '-';
    }
}