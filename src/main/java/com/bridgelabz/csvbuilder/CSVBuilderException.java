package com.bridgelabz.csvbuilder;

import javax.tools.Tool;

public class CSVBuilderException extends Exception {

   public enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE,NO_CENSUS_DATA,DELIMITER_OR_HEADER_PROBLEM
    }

   public ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

