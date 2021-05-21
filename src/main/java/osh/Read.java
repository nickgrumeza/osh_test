package osh;

import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;
import java.io.*;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Read{
    static final String csvFilePath = "D:/Programming/Osh/Interview-task-data-osh.csv";
    private static final String jdbcUrl = "jdbc:sqlite::memory:";
    private static Connection con = null;

    public static int recordsRecieved = 0;
    public static int recordsSuccussful = 0;
    public static int recordsFailed = 0;
    public static String[] beanArr;

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

    public static void readAndInsert() throws SQLException, IOException
    {                 
        int expectedColumns = 10; 
        CsvPreference pref = CsvPreference.STANDARD_PREFERENCE;
        SkipBadColumnsTokenizer tokenizer = new SkipBadColumnsTokenizer(new FileReader(csvFilePath), pref, expectedColumns);
        PreparedStatement pstatement = con.prepareStatement("INSERT INTO X(A,B,C,D,E,F,G,H,I,J) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?);");
        CellProcessor[] processors = new CellProcessor[]{
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
      
        try(ICsvBeanReader beanReader = new CsvBeanReader(tokenizer, 
        CsvPreference.STANDARD_PREFERENCE);){
            final String[] headers = beanReader.getHeader(true);
                        
            PojoBean beanData = null;
            while ((beanData = beanReader.read(PojoBean.class, headers, processors)) != null) {                
                    // System.out.print(String.format("lineNo=%s, rowNo=%s, beanData=%s", beanReader.getLineNumber(),
                    //     beanReader.getRowNumber(), beanData));
                    if ((beanData.getA() != null) && (beanData.getB() != null) && (beanData.getC() != null)
                            && (beanData.getD() != null) && (beanData.getE() != null) && (beanData.getF() != null) 
                            && (beanData.getG() != null) && (beanData.getH() != null) && (beanData.getI() != null) 
                            && (beanData.getJ() != null))
                    {
                        recordsSuccussful++;
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
                    } else {
                        recordsFailed++;
                    }
                recordsRecieved++;
            }
            System.out.println(String.format("Ignored lines: %s", tokenizer.getIgnoredLines()));
            beanReader.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        }       
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
}