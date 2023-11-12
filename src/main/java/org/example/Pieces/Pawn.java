package org.example.Pieces;

import lombok.Data;
import lombok.Getter;
import org.example.model.ChessBoard;
import org.example.model.Color;
import org.example.service.ChessBoardService;

public class Pawn extends PawnAbstract
{
    public Pawn(ChessBoard chessBoard, ChessBoardService chessBoardService, Color color, char CoordinateLetter, int CoordinateNumber) {
        super(chessBoard, chessBoardService, color, CoordinateLetter, CoordinateNumber);
    }

}
