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
        Read.testDb();
        Read.closeConnection();
        System.out.println("Records recieved = " + Read.recordsRecieved);
        System.out.println("Records successful = " + Read.recordsSuccussful);
        System.out.println("Records failed = " + Read.recordsFailed);
    }
}
