package censusanalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndianCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndianStateCensus.txt";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";
    private static final String INDIA_WRONG_DELIMITER_CSV="./src/test/resources/IndiaStateCensusWrongDelimiter.csv";
    private static final String INDIA_CENSUS_CSV_FILE_WITH_INVALID_HEADER="./src/test/resources/IndiaStateCensusDataInvalidHeader.csv";
    @Test
    public void givenIndianCensusCSVDataFromAdapterShouldReturnRecords() {
        try {
            CensusAdapter indiaCensusAdapter= new IndiaCensusAdapter();
            Map<String ,CensusDAO> resultMap =indiaCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
            Assert.assertEquals(29,resultMap.size());
            } catch (CensusAnalyserException e) { }
        }

    @Test
    public void givenData_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            CensusAdapter indiaCensusAdapter= new IndiaCensusAdapter();
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.STATE);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData,CensusDAO[].class);
            Assert.assertEquals("",censusDAOS[0].state);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusCSVWrongFile_ShouldThrowException() {
        try {
            USCensusAdapter usCensusAdapter = new USCensusAdapter();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            usCensusAdapter.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFileWithWrongExtension_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFileWithDelimiterProblem_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(INDIA_WRONG_DELIMITER_CSV);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusCSVFileWithIncorrectHeader_ShouldThrowException() {
        USCensusAdapter usCensusAdapter = new USCensusAdapter();
        try {
            usCensusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_WITH_INVALID_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM,e.type);
        }
    }
}
