package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public double populationDensity;
    public double totalArea;
    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea =indiaCensusCSV.areaInSqKm;
        populationDensity =indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode = censusCSV.stateId;
        population = censusCSV.population;
        populationDensity = censusCSV.populationDensity;
        totalArea=censusCSV.totalArea;
    }

    @Override
    public String toString() {
        return "IndiaCensusDAO{" +
                "population=" + population +
                ", densityPerSqKm=" + populationDensity +
                ", areaInSqKm=" + totalArea +
                ", state='" + state + '\'' +
                '}';
    }
}
