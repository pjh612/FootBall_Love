package com.deu.football_love.exception;

public class NotTeamMemberException  extends RuntimeException{
    public NotTeamMemberException(String message) {
        super(message);
    }

    public NotTeamMemberException() {
    }
}
