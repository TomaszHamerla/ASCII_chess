package org.example.Pieces;

import org.example.model.Color;
import org.example.service.BoardService.ChessBoardService;

public class Pawn extends PawnAbstract
{
    public Pawn( ChessBoardService chessBoardService, Color color, char CoordinateLetter, int CoordinateNumber) {
        super( chessBoardService, color, CoordinateLetter, CoordinateNumber);
    }

}
