package org.example.exception;

import lombok.Getter;

@Getter
public class PieceException extends RuntimeException{
    private PieceExceptionMessage pawnExceptionMessage;
    //klasa odwolujaca sie do wyjatkow dotyczacych pionkow, aby dodac kolejne przejdz do enuma PawnExceptionMessage

    public PieceException(PieceExceptionMessage message) {
        super(message.getMessage());
        this.pawnExceptionMessage =message;
    }
}
