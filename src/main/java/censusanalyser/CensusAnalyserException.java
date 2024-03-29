package censusanalyser;

public class CensusAnalyserException extends Exception {
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    enum ExceptionType {CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, NO_CENSUS_DATA, DELIMITER_OR_HEADER_PROBLEM, INVALID_COUNTRY}

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
