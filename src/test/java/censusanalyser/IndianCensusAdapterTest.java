package censusanalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import java.util.Map;

public class IndianCensusAdapterTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATE_CODE = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_FILE_TYPE = "./src/test/resources/IndianStateCensus.txt";
    private static final String US_CENSUS_CSV_FILE = "./src/test/resources/USCensusData.csv";

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
}
