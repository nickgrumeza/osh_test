package osh;

import java.io.*;
import java.sql.SQLException;


public class App 
{
    
    

    public static void main( String[] args ) throws IOException, SQLException
    {
        Read.openConnection();
        Read.createTable();
        Read.readAndInsert();
        // Read.testDb();
        Read.closeConnection();
        System.out.println(Read.recordsRecieved + " of records received");
        System.out.println(Read.recordsSuccussful + " of records successful");
        System.out.println(Read.recordsFailed + " of records failed");
    }
}
