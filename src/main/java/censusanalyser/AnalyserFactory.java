package censusanalyser;

public class AnalyserFactory {


    public static CensusAdapter createObject(CensusAnalyser.Country country) throws CensusAnalyserException {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter();
        else if(country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter();
        else
            throw new CensusAnalyserException("unknownCountry",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

}
