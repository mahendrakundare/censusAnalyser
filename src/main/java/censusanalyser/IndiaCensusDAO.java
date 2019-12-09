package censusanalyser;

import java.util.Iterator;

public class IndiaCensusDAO {


    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public IndiaCensusDAO(IndiaCensusCSV csvFileIterator) {
        state = csvFileIterator.state;
        areaInSqKm=csvFileIterator.areaInSqKm;
        densityPerSqKm=csvFileIterator.densityPerSqKm;
        population=csvFileIterator.population;
    }

    @Override
    public String toString() {
        return "IndiaCensusDAO{" +
                "population=" + population +
                ", densityPerSqKm=" + densityPerSqKm +
                ", areaInSqKm=" + areaInSqKm +
                ", state='" + state + '\'' +
                '}';
    }
}
