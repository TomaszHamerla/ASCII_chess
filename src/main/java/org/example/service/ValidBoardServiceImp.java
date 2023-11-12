package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.DataException;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;
import org.example.model.ChessBoard;
import org.example.model.Color;
@RequiredArgsConstructor
public class ValidBoardServiceImp implements ValidBoardService {
    private final ChessBoard chessBoard;

    @Override
    public void validLocations(String pawnLocation, String expectPawnLocation) {
        if (pawnLocation.length() > 2 || expectPawnLocation.length() > 2) {
            throw new DataException("Wrong location !");
        }

    }

    @Override
    public void validPawnLocation(char figure) {
        if (figure == '-') {
            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
        }

    }

    @Override
    public boolean validTurn(Color color) {
        if (chessBoard.isWhiteTurn() && Color.WHITE.equals(color)) {
            return true;

        } else if (!chessBoard.isWhiteTurn()&&Color.BLACK.equals(color)) {
            return true;
        }
        return false;
    }
}
