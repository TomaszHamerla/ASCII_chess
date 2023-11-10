package org.example;

import org.example.model.ChessBoard;
import org.example.service.ChessBoardService;
import org.example.service.ChessBoardServiceImp;
import org.example.service.ValidBoardService;
import org.example.service.ValidBoardServiceImp;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//Dependency injection
        ChessBoard chessBoard = new ChessBoard();
        ValidBoardService validBoardService = new ValidBoardServiceImp();
        ChessBoardService chessBoardService = new ChessBoardServiceImp(chessBoard, validBoardService);
        Game game = new Game(chessBoardService, chessBoard, validBoardService);

//preparing game
        System.out.print("The game running");
        for (int i = 0; i < 5; i++) {
            System.out.print(" .");
            Thread.sleep(600);
        }
        System.out.println();
//start game
        game.run();

    }
}