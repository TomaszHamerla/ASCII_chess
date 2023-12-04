package org.example;

import org.example.pieces.Piece;
import org.example.pieces.bishop.Bishop;
import org.example.pieces.king.King;
import org.example.pieces.knight.Knight;
import org.example.pieces.pawn.Pawn;
import org.example.pieces.queen.Queen;
import org.example.pieces.rook.Rook;
import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.service.BoardService.ChessBoardService;
import org.example.service.BoardService.ChessBoardServiceImp;
import org.example.service.BoardService.MoveToArchiveCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//Dependency injection
        UUID uuid = UUID.randomUUID();
        String gameId = uuid.toString();
        ChessBoard chessBoard = new ChessBoard();
        ChessBoardService chessBoardService = new ChessBoardServiceImp(chessBoard);
        MoveToArchiveCSV archive =  new MoveToArchiveCSV(chessBoardService, chessBoard, gameId);
        Game game = new Game(chessBoardService, chessBoard, archive);

        List<Piece> test =new ArrayList<>( List.of(
                new Pawn(chessBoardService, Color.WHITE,'B', 2),
                new Pawn(chessBoardService, Color.WHITE,'C', 2),
                new Pawn(chessBoardService, Color.WHITE,'A', 2),
                new Pawn(chessBoardService, Color.WHITE,'D', 2),
                new Pawn(chessBoardService, Color.WHITE,'E', 2),
                new Pawn(chessBoardService, Color.WHITE,'F', 2),
                new Pawn(chessBoardService, Color.WHITE,'G', 2),
                new Pawn(chessBoardService, Color.WHITE,'H', 2),
                new Pawn(chessBoardService, Color.BLACK,'A', 7),
                new Pawn(chessBoardService, Color.BLACK,'B', 7),
                new Pawn(chessBoardService, Color.BLACK,'C', 7),
                new Pawn(chessBoardService, Color.BLACK,'D', 7),
                new Pawn(chessBoardService, Color.BLACK,'E', 7),
                new Pawn(chessBoardService, Color.BLACK,'F', 7),
                new Pawn(chessBoardService, Color.BLACK,'G', 7),
                new Pawn(chessBoardService, Color.BLACK,'H', 7),
                new Rook(chessBoardService,Color.BLACK,'A',8),
                new Rook(chessBoardService,Color.BLACK,'H',8),
                new Rook(chessBoardService,Color.WHITE,'H',1),
                new Rook(chessBoardService,Color.WHITE,'A',1),
                new King(chessBoardService,Color.WHITE,'E',1),
                new King(chessBoardService,Color.BLACK,'E',8),
                new Knight(chessBoardService,Color.WHITE,'B',1),
                new Knight(chessBoardService,Color.WHITE,'G',1),
                new Knight(chessBoardService,Color.BLACK,'B',8),
                new Knight(chessBoardService,Color.BLACK,'G',8),
                new Queen(chessBoardService,Color.WHITE,'D',1),
                new Queen(chessBoardService,Color.BLACK,'D',8),
                new Bishop(chessBoardService, Color.WHITE,'C',1),
                new Bishop(chessBoardService, Color.WHITE,'F',1),
                new Bishop(chessBoardService, Color.BLACK,'C',8),
                new Bishop(chessBoardService, Color.BLACK,'F',8)
        ));
        chessBoard.setPieces(test);


//preparing game

//        System.out.print("The game running");
//        for (int i = 0; i < 5; i++) {
//            System.out.print(" .");
//            Thread.sleep(600);
//        }
//        System.out.println();
//start game
        game.run();

    }
}