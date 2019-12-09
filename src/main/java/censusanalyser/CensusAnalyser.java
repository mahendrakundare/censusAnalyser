package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusCSV> indiaCensusCSVList =null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            indiaCensusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return indiaCensusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
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
        if (indiaCensusCSVList == null ||indiaCensusCSVList.size()==0 ) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusCSVComparator= Comparator.comparing(census-> census.state);
        this.sort(censusCSVComparator);
        String sortedStateCensusJson = new Gson().toJson(indiaCensusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusCSV> censusCSVComparator) {
        for (int i = 0; i < indiaCensusCSVList.size() - 1; i++) {
            for (int j = 0; j < indiaCensusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV censusCSV1 = indiaCensusCSVList.get(j);
                IndiaCensusCSV censusCSV2 = indiaCensusCSVList.get(j + 1);
                if (censusCSVComparator.compare(censusCSV1, censusCSV2) > 0) {
                    indiaCensusCSVList.set(j, censusCSV2);
                    indiaCensusCSVList.set(j + 1, censusCSV1);
                }
            }
        }
    }
}