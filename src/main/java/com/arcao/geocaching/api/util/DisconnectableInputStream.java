package com.arcao.geocaching.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class DisconnectableInputStream extends InputStream {
  protected final InputStream in;
  protected final HttpURLConnection con;
  
  public DisconnectableInputStream(InputStream in, HttpURLConnection con) {
    this.in = in;
    this.con = con;
  }  

  @Override
  public int read() throws IOException {
    return in.read();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return in.read(b);
  }
  
  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return in.read(b, off, len);
  }
  
  @Override
  public void close() throws IOException {
    try {
      super.close();
    } finally {
      con.disconnect();
    }
  }
  
  @Override
  public int available() throws IOException {
    return in.available();
  }
  
  @Override
  public long skip(long n) throws IOException {
    return in.skip(n);
  }
  
  @Override
  public boolean markSupported() {
    return in.markSupported();
  }
  
  @Override
  public synchronized void mark(int readlimit) {
    in.mark(readlimit);
  }
  
  @Override
  public synchronized void reset() throws IOException {
    in.reset();
  }
}

