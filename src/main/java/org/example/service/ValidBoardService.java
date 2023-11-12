package org.example.service;

import org.example.model.Color;

public interface ValidBoardService {
    void validPawnLocation(char figure);
    void validLocations(String pawnLocation, String expectPawnLocation);
    boolean validTurn(Color color);
}
