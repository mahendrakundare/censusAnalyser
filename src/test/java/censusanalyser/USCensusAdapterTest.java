package censusanalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class USCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndianStateCensus.txt";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";
    private static final String US_WRONG_CSV_FILE="./src/test/resources/USCensusData102.csv";
    private static final String US_WRONG_CSV_EXT="./src/test/resources/USCensusData.txt";
    private static final String US_WRONG_DELIMITER_CSV="./src/test/resources/USCensusDataDelimiter.csv";
    private static final String US_CENSUS_CSV_FILE_WITH_INVALID_HEADER="./src/test/resources/USCensusDataInvalidHeader.csv";

    @Test
    public void givenUSCensusCSVDataFromAdapterShouldReturnRecords() {
        try {
            CensusAdapter censusAdapter = new USCensusAdapter();
            Map<String, CensusDAO> numOfRecords= censusAdapter.loadCensusData(US_CENSUS_CSV_FILE);
            Assert.assertEquals(51, numOfRecords.size());
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSCensusCSVWrongFile_ShouldThrowException() {
        try {
            USCensusAdapter usCensusAdapter = new USCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            usCensusAdapter.loadCensusData(US_WRONG_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFileWithWrongExtension_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(US_WRONG_CSV_EXT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFileWithDelimiterProblem_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(US_WRONG_DELIMITER_CSV);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM,e.type);
        }
    }

    @Test
    public void givenUSCensusCSVFileWithIncorrectHeader_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(US_CENSUS_CSV_FILE_WITH_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM,e.type);
        }
    }
}

