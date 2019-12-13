package censusanalyser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortingFileds {

    static Map<fields, Comparator> sortByFields = new HashMap<>();

    enum fields {
        STATE, POPULATION, DENSITY, AREA
    }

    public static Comparator getParameter(SortingFileds.fields parameter) {
        Comparator<CensusDAO> stateComparator = Comparator.comparing(census -> census.state);
        Comparator<CensusDAO> areaComparator = Comparator.comparing(census -> census.totalArea, Comparator.reverseOrder());
        Comparator<CensusDAO> populationComparator = Comparator.comparing(census -> census.population, Comparator.reverseOrder());
        Comparator<CensusDAO> densityComparator = Comparator.comparing(census -> census.populationDensity, Comparator.reverseOrder());
        sortByFields.put(fields.STATE, stateComparator);
        sortByFields.put(fields.AREA, areaComparator);
        sortByFields.put(fields.POPULATION, populationComparator);
        sortByFields.put(fields.DENSITY, densityComparator);
        Comparator<CensusDAO> comparator = sortByFields.get(parameter);
        return comparator;
    }

//    public static Comparator getParam(SortingFileds.fields field1, SortingFileds.fields field2) {
//        Comparator<CensusDAO> stateComparator = Comparator.comparing()
//    }
}
