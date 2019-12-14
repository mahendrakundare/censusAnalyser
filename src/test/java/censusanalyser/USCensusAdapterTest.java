package censusanalyser;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class USCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndianStateCensus.txt";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenUSCensusCSVDataFromAdapterShouldReturnRecords() {
        try {
            CensusAdapter censusAdapter = new USCensusAdapter();
            Map<String, CensusDAO> numOfRecords= censusAdapter.loadCensusData(US_CENSUS_CSV_FILE);
            Assert.assertEquals(51, numOfRecords.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM, e.type);
        }
    }
}
