package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.ChessBoard;
import org.example.service.ChessBoardService;
import org.example.service.ValidBoardService;

import java.util.Scanner;

@RequiredArgsConstructor
@Data
public class Game {
    private final ChessBoardService chessBoardService;
    private final ChessBoard chessBoard;
    private final ValidBoardService validBoardService;
    private boolean gameOver=true;


    //TODO List<Pionkow> ----!Maciek!
    // private List<"pionek">"pionki



    public void run() {

        chessBoardService.printChessBoard(chessBoard.getChessBoard());
       do {

           try {
               printTurn();
               Scanner src = new Scanner(System.in);
               System.out.print("Enter pawn location: ");
               String pawnLocation= src.nextLine();
               System.out.print("Enter expect pawn location: ");
               String expectPawnLocation= src.nextLine();
               validBoardService.validLocations(pawnLocation,expectPawnLocation);
               chessBoardService.movePiece(pawnLocation,expectPawnLocation);
               chessBoardService.printChessBoard(chessBoard.getChessBoard());
           }catch (Exception e){
               System.out.println(e.getMessage());
               System.out.println("Pleas try again");
           }
       }while(gameOver);
    }
    void printTurn(){
        if (chessBoard.isWhiteTurn()){
            System.out.println("Its whites turn");
        }else
            System.out.println("Its blacks turn");
    }
}
