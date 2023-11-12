package org.example.Pieces;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.Color;
import org.example.service.BoardService.ChessBoardService;

@Data
@AllArgsConstructor
public class Rook implements Piece {
    private Color color;
    private char CoordinateLetter;
    private int CoordinateNumber;
    private final ChessBoardService chessBoardService;

    @Override
    public void Move(String start, String end) {

    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return false;
    }
}
