package org.example;

import lombok.RequiredArgsConstructor;
import org.example.model.ChessBoard;
import org.example.service.ChessBoardService;
import org.example.service.ValidBoardService;

import java.util.Scanner;

@RequiredArgsConstructor
public class Game {
    private final ChessBoardService chessBoardService;
    private final ChessBoard chessBoard;
    private final ValidBoardService validBoardService;

    //TODO List<Pionkow> ----!Maciek!
    // private List<"pionek">"pionki



    public void run() {

        chessBoardService.printChessBoard(chessBoard.getChessBoard());
       do {

           try {
               Scanner src = new Scanner(System.in);
               System.out.print("Enter pawn location: ");
               String pawnLocation= src.nextLine();
               System.out.print("Enter expect pawn location: ");
               String expectPawnLocation= src.nextLine();
               validBoardService.validLocations(pawnLocation,expectPawnLocation);
               //TODO TOMEK validacja do poprawy  sprawdz czy input to jest litera od A do H i cyfra od 1 do 8
               chessBoardService.movePiece(pawnLocation,expectPawnLocation);
               chessBoardService.printChessBoard(chessBoard.getChessBoard());
           }catch (Exception e){
               System.out.println(e.getMessage());
               System.out.println("Pleas try again");
           }
       }while(true);
    }
}
