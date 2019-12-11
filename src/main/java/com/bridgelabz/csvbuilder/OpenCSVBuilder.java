package com.bridgelabz.csvbuilder;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class  OpenCSVBuilder<E> implements ICSVBuilder {

    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
           return this.getCSVBean(reader ,csvClass).iterator();
    }

    @Override
    public List<E> getCSVFileList(Reader reader, Class csvClass) throws CSVBuilderException {
        return this.getCSVBean(reader ,csvClass).parse();
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            csvToBeanBuilder.withSeparator(',');
            return  csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }

    }
}