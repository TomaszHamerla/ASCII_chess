package org.example.exception;

import lombok.Getter;

@Getter
public enum PawnExceptionMessage {
    PAWN_LOCATION_NOT_FOUND("Pawn with given location not found !"),
    PAWN_OUT_OF_CHESSBOARD("Pawn with given location not found !"),
    INVALID_MOVE("Invalid move !"),
    INVALID_TURN_MOVE("Its turn from enemy !"),
    INVALID_OPERATION("You can not hit your piece !"),
    PAWN_NOT_FOUND("Pawn not found !");

    private String message;

    PawnExceptionMessage(String message) {
        this.message = message;
    }


}
