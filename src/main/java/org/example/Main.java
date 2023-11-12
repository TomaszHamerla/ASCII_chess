package org.example;

import org.example.Pieces.Piece;
import org.example.Pieces.Pawn;
import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.service.ChessBoardService;
import org.example.service.ChessBoardServiceImp;
import org.example.service.ValidBoardService;
import org.example.service.ValidBoardServiceImp;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//Dependency injection
        ChessBoard chessBoard = new ChessBoard();
        ValidBoardService validBoardService = new ValidBoardServiceImp();
        ChessBoardService chessBoardService = new ChessBoardServiceImp(chessBoard, validBoardService);
        Game game = new Game(chessBoardService, chessBoard, validBoardService);
        List<Piece> test = List.of(
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'A', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'B', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'C', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'D', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'E', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'F', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'G', 2),
                new Pawn (chessBoard, chessBoardService, Color.WHITE,'H', 2),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'A', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'B', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'C', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'D', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'E', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'F', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'G', 7),
                new Pawn (chessBoard, chessBoardService, Color.BLACK,'H', 7)
        );
        chessBoard.setPieces(test);


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