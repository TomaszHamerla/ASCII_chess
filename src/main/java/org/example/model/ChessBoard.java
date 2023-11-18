package org.example.model;

import lombok.Data;
import org.example.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChessBoard {
    private char[][] chessBoard=  {
        {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'},
        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        {'-', '-', '-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-', '-', '-'},
        {'-', '-', '-', '-', '-', '-', '-', '-'},
        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
        {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'}
    };
    private List<Piece> pieces;
    private boolean whiteTurn= true;
    private List<String> moves = new ArrayList<>();
}
