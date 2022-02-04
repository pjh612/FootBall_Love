package com.deu.football_love.exception;


public class LikeDuplicatedException extends RuntimeException{

    public LikeDuplicatedException() {
    }

    public LikeDuplicatedException(String message) {
        super(message);
    }
}
