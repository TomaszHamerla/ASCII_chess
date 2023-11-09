package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum PawnExceptionMessage {
    PAWN_LOCATION_NOT_FOUND("Pawn with given location not found !"),
    Pawn_OUT_OF_CHESSBOARD("Pawn with given location not found");

    private String message;

    PawnExceptionMessage(String message) {
        this.message=message;
    }



}
