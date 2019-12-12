package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortingFileds {

    static Map<fields, Comparator> sortByFields = new HashMap<>();
    enum fields{
        STATE,POPULATION,DENSITY,AREA
    }

    public static Comparator getParameter(SortingFileds.fields parameter) {

        Comparator<IndiaCensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<IndiaCensusDAO> areaComparator = Comparator.comparing(census -> census.areaInSqKm);
        Comparator<IndiaCensusDAO> populationComparator = Comparator.comparing(census -> census.population);
        Comparator<IndiaCensusDAO> densityComparator = Comparator.comparing(census -> census.densityPerSqKm);
        sortByFields.put(fields.STATE, stateComparator);
        sortByFields.put(fields.AREA, areaComparator);
        sortByFields.put(fields.POPULATION, populationComparator);
        sortByFields.put(fields.DENSITY, densityComparator);
        Comparator<IndiaCensusDAO> comparator = sortByFields.get(parameter);
        return comparator;
    }
}
