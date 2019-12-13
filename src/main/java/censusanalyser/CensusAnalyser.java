package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    private Country country;

    public enum Country {INDIA, US}

    Map<String, CensusDAO> censusStateMap = null;

    public CensusAnalyser() {
    }

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        CensusAdapter censusAdapter = AnalyserFactory.createObject(country);
        censusStateMap = censusAdapter.loadCensusData(csvFilePath);
        return censusStateMap.size();
    }

    public String getSortedCensusData(SortingFileds.fields fields) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = SortingFileds.getParameter(fields);
        ArrayList censusDTO = censusStateMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }
}