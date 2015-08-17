package org.rascalmpl.repl;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class LimitedWriter extends FilterWriter {

  private final long limit;
  private long written;

  protected LimitedWriter(Writer out, long limit) {
    super(out);
    this.limit = limit;
    this.written = 0;
  }
  
  @Override
  public void write(int c) throws IOException {
    if (written < limit) {
      out.write(c);
      written ++;
      if (written == limit) {
        out.write("...");
      }
    }
  }
  
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if (limit == written) {
      return;
    }
    if (limit + len >= written) {
      len = (int)(written - limit);
    }
    out.write(cbuf, off, len);
    written += len;
    if (written == limit) {
      out.write("...");
    }
  }
  
  @Override
  public void write(String str, int off, int len) throws IOException {
    if (limit == written) {
      return;
    }
    if (limit + len >= written) {
      len = (int)(written - limit);
    }
    out.write(str, off, len);
    written += len;
    if (written == limit) {
      out.write("...");
    }
  }

}
