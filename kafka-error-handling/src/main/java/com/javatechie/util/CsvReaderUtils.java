package com.javatechie.util;

import com.javatechie.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CsvReaderUtils {
    // this class method readDataFromCsv() converts users.csv to List<User> object and returns it
    // this class is for converting users.cvs in resources folder to java object

    public static List<User> readDataFromCsv() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader
                (new ClassPathResource("users.csv").getInputStream()))) {
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)   // convert csv to java object bean using CsvToBeanBuilder builder
                    .build();

            return csvToBean.parse();   // parse the csv data to java object and return object
        }
        catch (IOException e) { // unable to parse csv data so exception is raised
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }
}