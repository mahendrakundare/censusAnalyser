package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusList =null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(),false).
                                        forEach(indiaCensusCSV -> censusList.add(new IndiaCensusDAO(indiaCensusCSV)));
            return censusList.size();
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
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> indiaStateCodeCSVIterator = csvBuilder
                    .getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            return getCount(indiaStateCodeCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEateries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size()==0 ) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusCSVComparator= Comparator.comparing(census-> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusCSVComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO censusCSV1 = censusList.get(j);
                IndiaCensusDAO censusCSV2 = censusList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    censusList.set(j, censusCSV2);
                    censusList.set(j + 1, censusCSV1);
                }
            }
        }
    }
}