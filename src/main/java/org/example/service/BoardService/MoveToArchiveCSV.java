package org.example.service.BoardService;

import lombok.Data;
import org.example.model.ChessBoard;
import org.example.pieces.Piece;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@Data
public class MoveToArchiveCSV implements IMoveToArchiveCSV {
    private final ChessBoardService chessBoardService;
    private final ChessBoard chessBoard;
    private String gameId;

    public MoveToArchiveCSV(ChessBoardService chessBoardService, ChessBoard chessBoard, String gameId) {
        this.chessBoardService = chessBoardService;
        this.chessBoard = chessBoard;
        this.gameId = gameId;
    }


    @Override
    public void saveMove(String start, String end) {
        String move = start + "-" + end;
        convertMoveToChessNotation(end);
        chessBoard.getMoves().add(move);

    }

    private void convertMoveToChessNotation(String end) {
        Piece piece = chessBoardService.getPiece(end).get();
        char figureSymbol = mapPieceToChessNotation(piece);
        String move = figureSymbol + end;
        saveMoveToCSV(move);
    }

    private char mapPieceToChessNotation(Piece piece) {
        String pieceType = piece.getClass().getSimpleName();
        return switch (pieceType) {
            case "Bishop" -> 'B';
            case "King" -> 'K';
            case "Knight" -> 'N';
            case "Pawn" -> ' ';
            case "Queen" -> 'Q';
            case "Rook" -> 'R';
            default -> throw new IllegalArgumentException("Save move to file failed");
        };
    }

    private void saveMoveToCSV(String move) {
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + File.separator + "ASCII_chessMoves" + File.separator + gameId + ".csv";

        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(move + "\n");
            } catch (IOException e) {
                System.out.println( e.getMessage());
            }
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }
}
