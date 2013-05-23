// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Reads the raw data from a given URL and feeds it into a data structure that
 * satisfies the {@link AbstractTable} interface. The only included example is
 * the {@link SoccerTable}.
 */
public class CsvParser extends AbstractParser implements IParser {

  private String delimiter;

  public CsvParser(Class<? extends AbstractTable> tblClass) throws MalformedURLException {
    this(tblClass, ",");
  }

  public CsvParser(Class<? extends AbstractTable> tblClass, String delimiter) throws MalformedURLException {
    this.tblClass = tblClass;
    this.delimiter = delimiter;
  }

  /** this tokenizes one single line of the input into an array of Strings */
  private String[] tokenize(String line) {
    // FIXME in reality, some CSV files are more complicated.
    // e.g. escaped delimiters ("\,") or delimiters in quotes must be ignored
    return line.split(delimiter);
  }

  @Override
  public AbstractTable parse(InputStream stream) throws Exception {
    AbstractTable tbl = tblClass.newInstance();
    InputStreamReader isr = new InputStreamReader(stream);
    BufferedReader buffer = new BufferedReader(isr);
    String line = buffer.readLine();
    tbl.checkFirstRow(tokenize(line)); // assumes first line is not empty
    while ((line = buffer.readLine()) != null) {
      tbl.parseRow(tokenize(line));
    }
    return tbl;
  }
}