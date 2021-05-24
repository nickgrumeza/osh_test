package osh;

import java.io.*;
import java.sql.SQLException;


public class App 
{
    
    

    public static void main( String[] args ) throws IOException, SQLException
    {
        CsvHandling.openConnection();
        CsvHandling.createTable();
        CsvHandling.readData();
        // CsvHandling.testDb();
        CsvHandling.closeConnection();
        CsvHandling.logStats();
    }
}
