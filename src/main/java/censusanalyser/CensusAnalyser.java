package censusanalyser;

import com.bridgelabz.csvbuilder.CSVBuilderException;
import com.bridgelabz.csvbuilder.ICSVBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.bridgelabz.csvbuilder.CSVBuilderFactory.createCSVBuilder;


public class CensusAnalyser {

    Map<String, IndiaCensusDAO> censusStateMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<String, IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false).
                    forEach(indiaCensusCSV -> censusStateMap.put(indiaCensusCSV.state, new IndiaCensusDAO(indiaCensusCSV)));
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_OR_HEADER_PROBLEM);
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = createCSVBuilder();
            Iterator<IndiaStateCodeDAO> csvFileIterator = csvBuilder
                    .getCSVFileIterator(reader, IndiaStateCodeDAO.class);
            while (csvFileIterator.hasNext()) {
                IndiaStateCodeDAO stateCodeCSV = csvFileIterator.next();
                IndiaCensusDAO censusDAO = censusStateMap.get(stateCodeCSV.state);
                if (censusDAO == null) continue;
                censusDAO.stateCode = stateCodeCSV.stateCode;
            }
            return censusStateMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = censusDAOS.get(j);
                IndiaCensusDAO censusCSV2 = censusDAOS.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusDAOS.set(j, censusCSV2);
                    censusDAOS.set(j + 1, censusCSV1);
                }
            }
        }
    }

    public String getPopulationSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO>censusDAOComparator = Comparator.comparing(census -> census.population);
        List<IndiaCensusDAO>censusDAOS= censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusDAOComparator);
        String sortedPopulationJson = new Gson().toJson(censusDAOS);
        return sortedPopulationJson;
    }

    public String getDensitySortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO>censusDAOComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<IndiaCensusDAO>censusDAOS= censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusDAOComparator);
        String sortedDensityJson = new Gson().toJson(censusDAOS);
        return sortedDensityJson;
    }

    public String getAreaSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size()==0) {
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO>censusDAOComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<IndiaCensusDAO>censusDAOS= censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusDAOComparator);
        String sortedAreaJson = new Gson().toJson(censusDAOS);
        return sortedAreaJson;
    }
}