package org.example.service;

import org.example.exception.DataException;
import org.example.exception.PawnException;
import org.example.exception.PawnExceptionMessage;

public class ValidBoardServiceImp implements ValidBoardService{
    @Override
    public void validLocations(String pawnLocation, String expectPawnLocation) {
        if (pawnLocation.length()>2||expectPawnLocation.length()>2){
            throw new DataException("Wrong location !");
        }
    }

    @Override
    public void validPawnLocation(char figure) {
        if (figure=='-'){
            throw new PawnException(PawnExceptionMessage.PAWN_LOCATION_NOT_FOUND);
        }

    }
}
