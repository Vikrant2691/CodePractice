package com.code.sapsystems;


import java.util.Arrays;
import java.util.TreeSet;

public class SAPSystemGenerator {

    private final TreeSet<String> SAPTables = new TreeSet<String>(Arrays.asList("EKKO", "EKPO", "AFRU", "AFKO"));
    private final TreeSet<String> SAPSystems = new TreeSet<String>(Arrays.asList("DE3", "CHP_200"));


    TreeSet<String> tablesGenerator(String tableOptions) {
        TreeSet<String> tablesList = new TreeSet<String>();

        if (tableOptions.contains(",")) {
            String[] options = tableOptions.split(",");
            for (String option : options) {
                tablesList.addAll(getSAPTablesList(option));
            }

        } else{
            tablesList.addAll(getSAPTablesList(tableOptions));
        }
        return tablesList;
    }

    TreeSet<String> getSAPTablesList(String option) {
        TreeSet<String> tablesList = new TreeSet<String>();

        if (SAPTables.contains(option)) {
            //it is SAPTable
            for (String sapSystem : SAPSystems) {
                tablesList.add(sapSystem + "__" + option);
            }

        } else if (SAPSystems.contains(option)) {
            //it is SAPSystem
            for (String sapTable : SAPTables) {
                tablesList.add(option + "__" + sapTable);
            }

        } else if (option.contains("__")) {
            if (SAPSystems.contains(option.split("__")[0]) && SAPSystems.contains(option.split("__")[0])) {
                tablesList.add(option);
            } else {
                System.out.println("Table Name invalid");
            }
        }

        return tablesList;
    }


    public static void main(String[] args) {
        SAPSystemGenerator sapSystemGenerator= new SAPSystemGenerator();
        System.out.println(sapSystemGenerator.tablesGenerator("EKKO,EKPO,CHP_200__AFRU"));
    }


}
