package osh;

import org.supercsv.io.Tokenizer;
import org.supercsv.prefs.CsvPreference;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SkipBadColumnsTokenizer extends Tokenizer{   
    private final int expectedColumns;

    private final List<Integer> ignoredLines = new ArrayList<>();

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
            System.out.println(String.format("Ignoring line %s with %d columns: %s", getLineNumber(), columns.size(), getUntokenizedRow()));
            ignoredLines.add(getLineNumber());
        }

        return moreInputExists;

    }

    public List<Integer> getIgnoredLines(){
        return this.ignoredLines;
    }
}