package org.example;

import lombok.Data;
import org.example.exception.DataException;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;

import java.util.Scanner;


public class Game implements Runnable {
    private boolean isGameOver = false;
    //TODO List<Pionkow> ----!Maciek!
    // private List<"pionek">"pionki
    private char[][] board = {
            {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'-', '-', '-', '-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-', '-', '-', '-'},
            {'-', '-', '-', '-', '-', '-', '-', '-'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}
    };

    @Override
    public void run() {
        printBoard();
       do {
           try {
               Scanner src = new Scanner(System.in);
               String pawnLocation= src.nextLine();
               String expectPawnLocation= src.nextLine();
               validLocations(pawnLocation,expectPawnLocation);
               switchBoard(pawnLocation.toUpperCase(),expectPawnLocation.toUpperCase());

               printBoard();
           }catch (Exception e){
               System.out.println(e.getMessage());
               System.out.println("Pleas try again");
           }
       }while(!isGameOver);
    }

    private void validLocations(String pawnLocation, String expectPawnLocation) {
        if (pawnLocation.length()>2||expectPawnLocation.length()>2){
            throw new DataException("Wrong location !");
        }
    }

    private void switchBoard(String pawnLocation, String expectPawnLocation) {
        char figure = getFigure(pawnLocation); //pobiera figure albo rzuca wyjatek --sprawdz package exception
        removePawn(pawnLocation); //zamienia pionke na puste pole '-'
        //TODO !Maciek! - validacja do pionka czy moze sie tak poruszac - ja troche rozbilem te metody wyzej , metoda getFigure przyda sie -mozna ja zmienic
        int expectIndexLetter = getIndexLetter(expectPawnLocation.charAt(0));
        int expectIndexNumber = getIndexNumber(expectPawnLocation.charAt(1)-'0');
        board[expectIndexNumber][expectIndexLetter] = figure;
    }

    private void removePawn(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1)-'0');
        board[indexNumber][indexLetter] = '-';
    }

    private char getFigure(String pawnLocation) {
        int indexLetter = getIndexLetter(pawnLocation.charAt(0));
        int indexNumber = getIndexNumber(pawnLocation.charAt(1)-'0');
        char figure = board[indexNumber][indexLetter];
       validPawnLocation(figure);
        return figure;

    }

    private void validPawnLocation(char figure) {

        if (figure=='-'){
            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
        }
    }

    private void printBoard() {
        int a = 8;
        for (int i = 0; i < board.length; i++) {
            System.out.print(a + "    ");
            a--;
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("     ");
        for (char c = 'A'; c < 'I'; c++) {
            System.out.print(c + " ");
        }
    }

    private int getIndexLetter(char letter) {
        switch (letter) {
            case 'A':
                letter = '0';
                break;
            case 'B':
                letter = '1';
                break;
            case 'C':
                letter = '2';
                break;
            case 'D':
                letter = '3';
                break;
            case 'E':
                letter = '4';
                break;
            case 'F':
                letter = '5';
                break;
            case 'G':
                letter = '6';
                break;
            case 'H':
                letter = '7';
                break;
            default:
                throw new PawnException(PawnExceptionMessage.Pawn_OUT_OF_CHESSBOARD);

        }
        return letter - '0';
    }

    private int getIndexNumber(int num) {
        switch (num) {
            case 8:
                num = 0;
                break;
            case 7:
                num = 1;
                break;
            case 6:
                num = 2;
                break;
            case 5:
                num = 3;
                break;
            case 4:
                num = 4;
                break;
            case 3:
                num = 5;
                break;
            case 2:
                num = 6;
                break;
            case 1:
                num = 7;
                break;
            default:
                throw new PawnException(PawnExceptionMessage.Pawn_OUT_OF_CHESSBOARD);
        }
        return num;
    }
}
