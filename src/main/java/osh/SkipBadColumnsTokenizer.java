package osh;

import org.supercsv.io.Tokenizer;
import org.supercsv.prefs.CsvPreference;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SkipBadColumnsTokenizer extends Tokenizer{   
    private final int expectedColumns;

    private final List<String> ignoredData = new ArrayList<>();

    public SkipBadColumnsTokenizer(Reader reader, 
            CsvPreference preferences, int expectedColumns) {
        super(reader, preferences);
        this.expectedColumns = expectedColumns;
    }    

    @Override
    public boolean readColumns(List<String> columns) throws IOException {
        boolean moreInputExists;
        while ((moreInputExists = super.readColumns(columns)) && 
            columns.size() != this.expectedColumns){
            ignoredData.add(getUntokenizedRow());
        }        
        return moreInputExists;
    }

    public List<String> getIgnoredData(){
        return this.ignoredData;
    }
}