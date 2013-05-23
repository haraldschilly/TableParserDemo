// (c) Harald Schilly 2013, License: Apache 2.0

package at.univie.mat.pp.tableparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * intermediate abstract class that maps different input sources to an
 * {@link InputStream}
 */
public abstract class AbstractParser implements IParser {

  protected Class<? extends AbstractTable> tblClass;

  /** allows to read from a normal file */
  public AbstractTable parse(File f) throws Exception {
    FileInputStream stream = new FileInputStream(f);
    return parse(stream);
  }

  /** allows to read from a URL */
  public AbstractTable parse(URL url) throws IOException, Exception {
    InputStream stream = url.openStream();
    return parse(stream);
  }

}
