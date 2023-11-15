package org.example.exception;

import lombok.Getter;

@Getter
public enum PawnExceptionMessage {
    PIECE_LOCATION_NOT_FOUND("Piece with given location not found !"),
    PIECE_OUT_OF_CHESSBOARD("Piece with given location not found !"),
    INVALID_MOVE("Invalid move !"),
    INVALID_TURN_MOVE("Its turn from enemy !"),
    INVALID_OPERATION("You can not hit your piece !"),
    PIECE_NOT_FOUND("Piece not found !");

    private String message;

    PawnExceptionMessage(String message) {
        this.message = message;
    }


}
