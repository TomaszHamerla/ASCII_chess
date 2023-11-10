package org.example.service;

public interface ValidBoardService {
    void validPawnLocation(char figure);
    void validLocations(String pawnLocation, String expectPawnLocation);
}
