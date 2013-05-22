package at.univie.mat.pp.tableparser;
// (c) Harald Schilly 2013, License: Apache 2.0

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reads the raw data and feeds it into a data structure that satisfies the
 * {@link ITable} interface. The only included example is the
 * {@link SoccerTable}.
 */
class Parser {

  private URL                     url;
  private Class<? extends ITable> tblClass;

  public Parser(String url, Class<? extends ITable> tblClass) throws MalformedURLException {
    this.url = new URL(url);
    this.tblClass = tblClass;
  }

  public ITable parse() throws Exception {
    ITable tbl = tblClass.newInstance();
    InputStreamReader input = new InputStreamReader(url.openStream());
    BufferedReader buffer = new BufferedReader(input);
    int lineNo = 0;
    for (String line; (line = buffer.readLine()) != null;) {
      String[] tokens = line.split(",");
      if (lineNo == 0) {
        Columns.checkFirstRow(tokens);
      } else {
        tbl.addRow(tokens);
      }
      lineNo++;
    }
    return tbl;
  }
}