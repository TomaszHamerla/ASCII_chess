package org.example.pieces.queen;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.Color;
import org.example.pieces.Piece;
import org.example.pieces.moves.ValidatorForCrossMove;
import org.example.pieces.moves.ValidatorForForwardMove;
import org.example.service.BoardService.ChessBoardService;
@Data
@AllArgsConstructor
public class Queen implements Piece, ValidatorForCrossMove, ValidatorForForwardMove {
    private final ChessBoardService chessBoardService;
    private Color color;
    private char coordinateLetter;
    private int coordinateNumber;

    @Override
    public void Move(String start, String end) {

    }

    @Override
    public boolean isMoveValid(String start, String end) {
        return false;
    }

    @Override
    public boolean validateCrossMove(String start, String end) {
        return false;
    }

    @Override
    public boolean validateForwardMove(String start, String end) {
        return false;
    }
}
