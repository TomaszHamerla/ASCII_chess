package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.Pieces.Piece;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.ChessBoard;

import java.awt.dnd.InvalidDnDOperationException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

public class ChessBoardServiceImp implements ChessBoardService {
    private final ChessBoard chessBoard;
    private final ValidBoardService validBoardService;


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
        pawnLocation = pawnLocation.toUpperCase();
        expectPawnLocation = expectPawnLocation.toUpperCase();
        char cordLetter = pawnLocation.charAt(0);
        int cordNumber = pawnLocation.charAt(1) - '0';
        List<Piece> pieces = chessBoard.getPieces();
        Piece piece = pieces.stream()
                .filter(p -> p.getCoordinateLetter() == cordLetter && p.getCoordinateNumber() == cordNumber)
                .findFirst()
                .orElseThrow(() -> new PawnException(PawnExceptionMessage.PAWN_NOT_FOUND));
        if (validBoardService.validTurn(piece.getColor())){
            piece.Move(pawnLocation,expectPawnLocation);
        } else  {
            System.out.println("You can not use the opponent piece !");
        }
    }


    @Override
    public void updatePosition(String pawnLocation, String expectPawnLocation) {
        char figure = getFigure(pawnLocation); //pobiera figure albo rzuca wyjatek --sprawdz package exception
        removePawn(pawnLocation); //zamienia pionke na puste pole '-'
        int expectIndexLetter = getIndexLetter(expectPawnLocation.charAt(0));
        int expectIndexNumber = getIndexNumber(expectPawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[expectIndexNumber][expectIndexLetter] = figure;
    }

    @Override
    public void removePawn(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1) - '0');
        chessBoard.getChessBoard()[indexNumber][indexLetter] = '-';
    }

    @Override
    public char getFigure(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1) - '0');
        char figure = chessBoard.getChessBoard()[indexNumber][indexLetter];
        validBoardService.validPawnLocation(figure);
        return figure;
    }
    public boolean isFieldOccupied(String pawnLocation) {
        return chessBoard.getPieces().stream().anyMatch(piece -> piece.getCoordinateLetter() == pawnLocation.charAt(0) && piece.getCoordinateNumber() == pawnLocation.charAt(1) - '0');

    }
    @Override
    public int getIndexLetter(char letter) {
        switch (letter) {
            case 'A' -> letter = '0';
            case 'B' -> letter = '1';
            case 'C' -> letter = '2';
            case 'D' -> letter = '3';
            case 'E' -> letter = '4';
            case 'F' -> letter = '5';
            case 'G' -> letter = '6';
            case 'H' -> letter = '7';
            default -> throw new PawnException(PawnExceptionMessage.PAWN_OUT_OF_CHESSBOARD);
        }
        return letter - '0';
    }

    @Override
    public int getIndexNumber(int num) {
        switch (num) {
            case 8 -> num = 0;
            case 7 -> num = 1;
            case 6 -> num = 2;
            case 5 -> num = 3;
            case 4 -> num = 4;
            case 3 -> num = 5;
            case 2 -> num = 6;
            case 1 -> num = 7;
            default -> throw new PawnException(PawnExceptionMessage.PAWN_OUT_OF_CHESSBOARD);
        }
        return num;
    }
}
