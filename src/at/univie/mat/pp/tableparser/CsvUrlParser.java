// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reads the raw data from a given URL and feeds it into a data structure that
 * satisfies the {@link AbstractTable} interface. The only included example is
 * the {@link SoccerTable}.
 */
public class CsvUrlParser implements IParser {

  private String                         delimiter;
  private Class<? extends AbstractTable> tblClass;

  public CsvUrlParser(Class<? extends AbstractTable> tblClass) throws MalformedURLException {
    this(tblClass, ",");
  }

  public CsvUrlParser(Class<? extends AbstractTable> tblClass, String delimiter) throws MalformedURLException {
    this.tblClass = tblClass;
    this.delimiter = delimiter;
  }

  @Override
  public AbstractTable parse(String urlstr) throws Exception {
    AbstractTable tbl = tblClass.newInstance();
    URL url = new URL(urlstr);
    InputStreamReader input = new InputStreamReader(url.openStream());
    BufferedReader buffer = new BufferedReader(input);
    int lineNo = 0;
    for (String line; (line = buffer.readLine()) != null;) {
      String[] tokens = line.split(delimiter);
      if (lineNo == 0) {
        tbl.checkFirstRow(tokens);
      } else {
        tbl.parseRow(tokens);
      }
      lineNo++;
    }
    return tbl;
  }
}