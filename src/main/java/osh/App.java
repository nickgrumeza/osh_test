package osh;

import java.io.*;

import osh.Read;

public class App 
{
    
    static final String csvFilePath = "D:/Programming/Osh/Interview-task-data-osh.csv";
    

    public static void main( String[] args ) throws IOException
    {
        Read.readCSVFile(csvFilePath);
       
    }
}
