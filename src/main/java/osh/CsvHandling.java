package osh;

import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CsvHandling{
    static final String csvFilePath = "D:/Programming/Osh/Interview-task-data-osh.csv";
    private static final String jdbcUrl = "jdbc:sqlite::memory:";
    private static Connection con = null;

    private static String badDataPath = "D:/Programming/Osh/bad-data-";
    private static String badDataExt = ".csv";
    private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd--HH-mm");
    private static String badDataFilename = badDataPath + date.format(new Date()) + badDataExt;

    public static int recordsReceived = 0;
    public static int recordsSuccessful = 0;
    public static int recordsFailed = 0;

    private static String logFilePath = "D:/Programming/Osh/logFile.log";

    private static CellProcessor[] getProcessors(){
        final CellProcessor[] processors = new CellProcessor[]{
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional(),
            new Optional()
        };   
        return processors;
    }

    public static void openConnection() throws SQLException{
        if (con == null || con.isClosed()){
            con = DriverManager.getConnection(jdbcUrl);
        }
    }

    public static void closeConnection() throws SQLException 
    {
        con.close();
    }

    public static void createTable(){
        try{
            final Statement statement = con.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS X"
                + "(A TEXT," 
                + " B TEXT," 
                + " C TEXT," 
                + " D TEXT," 
                + " E TEXT," 
                + " F TEXT," 
                + " G TEXT," 
                + " H TEXT," 
                + " I TEXT," 
                + " J TEXT);");                 
        }   catch (SQLException e){
            e.getMessage();
        }
    }

    public static void insertData(PojoBean beanData) throws SQLException{
        PreparedStatement pstatement = con.prepareStatement("INSERT INTO X(A,B,C,D,E,F,G,H,I,J) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?);");

                    pstatement.setString(1, beanData.getA());
                    pstatement.setString(2, beanData.getB());
                    pstatement.setString(3, beanData.getC());
                    pstatement.setString(4, beanData.getD());
                    pstatement.setString(5, beanData.getE());
                    pstatement.setString(6, beanData.getF());
                    pstatement.setString(7, beanData.getG());
                    pstatement.setString(8, beanData.getH());
                    pstatement.setString(9, beanData.getI());
                    pstatement.setString(10, beanData.getJ());
                    pstatement.executeUpdate();   
    }

    public static void readData() throws SQLException, IOException
    {                 
        int expectedColumns = 10; 
        CsvPreference pref = CsvPreference.STANDARD_PREFERENCE;
        SkipBadColumnsTokenizer tokenizer = new SkipBadColumnsTokenizer(new FileReader(csvFilePath), pref, expectedColumns);
        final CellProcessor[] processors = getProcessors();
      
        try(ICsvBeanReader beanReader = new CsvBeanReader(tokenizer, pref);){
            final String[] headers = beanReader.getHeader(true);
                        
            PojoBean beanData = null;
            while ((beanData = beanReader.read(PojoBean.class, headers, processors)) != null) {    
                recordsReceived++;
                insertData(beanData);            
            }
            beanReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Could not find the CSV file: " + e);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e);            
        }   
        writeBadData(tokenizer.getIgnoredData());
        recordsFailed = tokenizer.getIgnoredData().size();
        recordsSuccessful = recordsReceived - recordsFailed;
    } 
    
    public static void testDb() {
        try {
            final Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM X;");
            ResultSetMetaData resMetaData = res.getMetaData();
            int numColumns = resMetaData.getColumnCount();
          
            while (res.next()){
                for (int i=1; i<=numColumns; i++){
                    String columnValue = res.getString(i);
                    System.out.print(columnValue + " ");
                }
            }
        } catch (SQLException e){
            e.getMessage();
        }   
    }

    public static void writeBadData(List<String> persons) throws IOException{
        CsvPreference pref = CsvPreference.EXCEL_PREFERENCE;
        ICsvListWriter listWriter = null;
        try{
            listWriter = new CsvListWriter(new FileWriter(badDataFilename), pref);
            listWriter.write(persons);
            listWriter.close();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logStats()
    {
        try
        {
            FileWriter log = new FileWriter(logFilePath);
            log.write("Records Received: " + recordsReceived + "\n");
            log.write("Records Successful: " + recordsSuccessful + "\n");
            log.write("Records Failed: " + recordsFailed);
            log.close();
        } catch (IOException e)
        {
            e.getMessage();
        }
    }
}