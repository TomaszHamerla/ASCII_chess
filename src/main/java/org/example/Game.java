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
               String pawnLocation= src.nextLine();
               String expectPawnLocation= src.nextLine();
               validBoardService.validLocations(pawnLocation,expectPawnLocation);
               chessBoardService.updatePosition(pawnLocation.toUpperCase(),expectPawnLocation.toUpperCase());

               chessBoardService.printChessBoard(chessBoard.getChessBoard());
           }catch (Exception e){
               System.out.println(e.getMessage());
               System.out.println("Pleas try again");
           }
       }while(true);
    }
}
