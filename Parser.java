// (c) Harald Schilly 2013, License: Apache 2.0

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Reads the raw data and feeds it into the {@link Table} data structure.
 */
class Parser {

  private URL url;

  public Parser(String url) throws MalformedURLException {
    this.url = new URL(url);
  }

  public Table parse() throws Exception {
    Table tbl = new Table();
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