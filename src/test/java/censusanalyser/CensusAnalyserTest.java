package censusanalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndianStateCensus.txt";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";
    private static final String COMMON_FIELD_FILE="./src/test/resources/commonfileds.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            int numberOfStateCode = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            Assert.assertEquals(29, numberOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.STATE);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongDelimiter_ShouldeThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndinCensuData_WithWrongType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.POPULATION);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("Uttar Pradesh", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.DENSITY);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("Bihar", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.AREA);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("Rajasthan", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectRecord() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            int censusDataCount = censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE);
            Assert.assertEquals(51, censusDataCount);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.POPULATION);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("California", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
        try {
            censusAnalyser.loadCensusData(US_CENSUS_CSV_FILE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.DENSITY);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("District of Columbia", censusDAOS[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        try {
            censusAnalyser.loadCensusData(COMMON_FIELD_FILE,INDIAN_STATE_CODE);
            String sortedData = censusAnalyser.getSortedCensusData(SortingFileds.fields.POPULATION,SortingFileds.fields.DENSITY);
            CensusDAO[] censusDAOS = new Gson().fromJson(sortedData, CensusDAO[].class);
            Assert.assertEquals("Bihar",censusDAOS[0].state);
        } catch (CensusAnalyserException e) { }
    }
}