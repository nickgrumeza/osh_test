package osh;

import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;
import java.io.*;
import java.sql.*;

public class Read{
    private final String jdbcUrl = "jdbc:sqlite::memory:";
    private Connection con = null;

    public static void readCSVFile(String csvFilePath){
        
        CellProcessor[] processors = new CellProcessor[] {
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(new ParseBool()),
            new Optional(new ParseBool()),
            new Optional()
        };        
      
        try(ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(csvFilePath), 
        CsvPreference.STANDARD_PREFERENCE);)
        {
            final String[] headers = beanReader.getHeader(true);
            Data dataBean = null;
            while ((dataBean = beanReader.read(Data.class, headers, processors)) != null) {
                System.out.printf("%s %s %s %s %s %s %s %s %s %s",
                dataBean.getA(), dataBean.getB(), dataBean.getC(), 
                dataBean.getD(), dataBean.getE(), dataBean.getF(),
                dataBean.getG(), dataBean.getH(), dataBean.getI(),
                dataBean.getJ());
                System.out.println();
            }
            
            beanReader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        }       
    }     
}