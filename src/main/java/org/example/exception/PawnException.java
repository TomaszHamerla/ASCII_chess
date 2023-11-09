package org.example.exception;

import lombok.Getter;

@Getter
public class PawnException extends RuntimeException{
    private PawnExceptionMessage pawnExceptionMessage;
    //klasa odwolujaca sie do wyjatkow dotyczacych pionkow, aby dodac kolejne przejdz do enuma PawnExceptionMessage

    public PawnException(PawnExceptionMessage message) {
        super(message.getMessage());
        this.pawnExceptionMessage =message;
    }
}
