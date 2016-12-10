package com.vroozi.customerui.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by qureshit on 2/13/2016.
 */
public class PrettyPrintingMap<K,V> {
  private Map<K, V> map;

  public PrettyPrintingMap(Map<K, V> map) {
    this.map = map;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry<K, V> entry = iter.next();
      sb.append(entry.getKey());
      sb.append('=').append('"');
      sb.append(entry.getValue());
      sb.append('"');
      if (iter.hasNext()) {
        sb.append(',').append(' ');
      }
    }
    return sb.toString();

  }

}
