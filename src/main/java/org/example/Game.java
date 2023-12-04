package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.model.ChessBoard;
import org.example.service.BoardService.ChessBoardService;

import java.util.Scanner;

import static org.example.pieces.UtilsOperation.*;

@RequiredArgsConstructor
@Data
public class Game {
    private final ChessBoardService chessBoardService;
    private final ChessBoard chessBoard;
    private boolean gameOver = true;

    public void run() {

        chessBoardService.printChessBoardArr(chessBoard.getChessBoard());
        do {

            try {
                printTurn();
                Scanner src = new Scanner(System.in);
                if (isKingUnderAttack(chessBoardService)) {
                    //TODO
                    if (isCheckmateSituation(chessBoardService)) {
                        System.out.println("Game Over !");
                        printWinner();
                        gameOver = false;
                        break;
                    }
                    System.out.println("King is in check !");
                }
                if(isStalemate(chessBoardService)){
                    System.out.println("Game over, is stalemate !");
                    gameOver=false;
                    break;
                }
                System.out.print("Enter pawn location: ");
                String pawnLocation = src.nextLine();
                //TODO unmute WHITE TURN
               // chessBoardService.validWhiteTurn(pawnLocation.toUpperCase());
                System.out.print("Enter expect pawn location: ");
                String expectPawnLocation = src.nextLine();

                chessBoardService.movePiece(pawnLocation.toUpperCase(), expectPawnLocation.toUpperCase());
                chessBoardService.saveMove(pawnLocation.toUpperCase(), expectPawnLocation.toUpperCase());
                chessBoardService.printChessBoardArr(chessBoard.getChessBoard());
                chessBoard.setWhiteTurn(!chessBoard.isWhiteTurn());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Pleas try again");
            }
        } while (gameOver);
    }

    private void printTurn() {
        if (chessBoard.isWhiteTurn()) {
            System.out.println("Its whites turn");
        } else
            System.out.println("Its blacks turn");
    }

    private void printWinner() {
        if (chessBoard.isWhiteTurn()) {
            System.out.println("Whites");
        } else
            System.out.println("Blacks");
    }
}
