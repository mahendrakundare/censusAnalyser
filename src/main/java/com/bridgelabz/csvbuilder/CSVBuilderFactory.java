package com.bridgelabz.csvbuilder;

public class CSVBuilderFactory {

    public static ICSVBuilder createCSVBuilder() {
        return  new OpenCSVBuilder();
//        return new CommonCSVBuilder();
    }
}
