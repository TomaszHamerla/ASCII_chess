package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.ChessBoard;
import org.example.service.BoardService.ChessBoardService;

import java.util.Scanner;

@RequiredArgsConstructor
@Data
public class Game {
    private final ChessBoardService chessBoardService;
    private final ChessBoard chessBoard;
    private boolean gameOver=true;

    public void run() {

        chessBoardService.printChessBoard(chessBoard.getChessBoard());
       do {

           try {
               printTurn();
               Scanner src = new Scanner(System.in);
               System.out.print("Enter pawn location: ");
               String pawnLocation= src.nextLine();
              // chessBoardService.validPawnLocation(pawnLocation.toUpperCase());

               System.out.print("Enter expect pawn location: ");
               String expectPawnLocation= src.nextLine();
              // chessBoardService.validExpectPawnLocation(expectPawnLocation.toUpperCase());

               chessBoardService.movePiece(pawnLocation.toUpperCase(),expectPawnLocation.toUpperCase());

               chessBoardService.printChessBoard(chessBoard.getChessBoard());

               chessBoard.setWhiteTurn(!chessBoard.isWhiteTurn());
           }catch (Exception e){
               System.out.println(e.getMessage());
               System.out.println("Pleas try again");
           }
       }while(gameOver);
    }
   private void printTurn(){
        if (chessBoard.isWhiteTurn()){
            System.out.println("Its whites turn");
        }else
            System.out.println("Its blacks turn");
    }
}
