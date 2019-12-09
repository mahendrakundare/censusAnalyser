package censusanalyser;

public class CSVBuilderException extends Exception {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE
    }

   ExceptionType type;

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

