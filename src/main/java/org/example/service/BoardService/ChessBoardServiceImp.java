package org.example.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.exception.PieceException;
import org.example.exception.PieceExceptionMessage;
import org.example.model.ChessBoard;
import org.example.pieces.UtilsOperation;
import org.example.pieces.pawn.Pawn;
import org.example.pieces.pawn.PawnAbstract;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

public class ChessBoardServiceImp implements ChessBoardService {
    private final ChessBoard chessBoard;

    @Override
    public void printChessBoardArr(char[][] chessBoard) {
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
                .orElseThrow(() -> new PieceException(PieceExceptionMessage.PIECE_NOT_FOUND));
        if (!UtilsOperation.isCheckmateResolved(pawnLocation, expectPawnLocation, this)) {
            throw new PieceException(PieceExceptionMessage.CHESS_CHECK_EXCEPTION);
        }
        UtilsOperation.isMoveAllowed(pawnLocation, expectPawnLocation, this);
        if (!(piece instanceof Pawn) && UtilsOperation.isEnemyOnExpectLocation(piece.getColor(), expectPawnLocation, this)) {
            UtilsOperation.removePiece(expectPawnLocation, this);
        }
        piece.Move(pawnLocation, expectPawnLocation);
    }

    @Override
    public void updatePositionArr(String pawnLocation, String expectPawnLocation) {
        char figure = getFigureArr(pawnLocation);
        removePawnArr(pawnLocation); //zamienia pionke na puste pole '-'
        int expectIndexLetter = UtilsOperation.getIndexLetterArr(expectPawnLocation.charAt(0));
        int expectIndexNumber = UtilsOperation.getIndexNumberArr(expectPawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[expectIndexNumber][expectIndexLetter] = figure;
    }

    @Override
    public boolean isFieldOccupied(String pawnLocation) {
        return chessBoard.getPieces()
                .stream()
                .anyMatch(piece -> piece.getCoordinateLetter() == pawnLocation.charAt(0) && piece.getCoordinateNumber() == pawnLocation.charAt(1) - '0');
//TODO -> moze isc do serwisu opartego o sama liste pionkow
    }

    @Override
    public char[][] getChessBoardArr() {
        return chessBoard.getChessBoard();
    }

    //TODO unmute
    @Override
    public void validWhiteTurn(String pawnLocation) {
           Optional<Piece> piece = getPiece(pawnLocation);
        if (!validTurn(piece.get().getColor())) {
            throw new PieceException(PieceExceptionMessage.INVALID_TURN_MOVE);
        }
    }
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

    @Override
    public void saveMove(String start, String end) {
        String move = start + "-" + end;
        chessBoard.getMoves().add(move);
    }

    @Override
    public List<String> getSavedMoves() {
        return chessBoard.getMoves();
    }

    private boolean validTurn(Color color) {
        if (chessBoard.isWhiteTurn() && Color.WHITE.equals(color)) {
            return true;

        } else if (!chessBoard.isWhiteTurn() && Color.BLACK.equals(color)) {
            return true;
        }
        return false;
    }

    private char getFigureArr(String pawnLocation) {
        int indexLetter = UtilsOperation.getIndexLetterArr(pawnLocation.charAt(0));
        int indexNumber = UtilsOperation.getIndexNumberArr(pawnLocation.charAt(1) - '0');
        return chessBoard.getChessBoard()[indexNumber][indexLetter];
    }

    private void removePawnArr(String pawnLocation) {
        int indexLetter = UtilsOperation.getIndexLetterArr(pawnLocation.charAt(0));
        int indexNumber = UtilsOperation.getIndexNumberArr(pawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[indexNumber][indexLetter] = '-';
    }
}