package com.vroozi.customerui.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Muhammad Haris
 *
 */
public class MapUtil {

  public static final String ASCENDING = "ASC";
  public static final String DESCENDING = "DESC";

  public static Map sortOnValues(Map unsortMap, String sortDirection) {
    List list = new LinkedList(unsortMap.entrySet());

    Collections.sort(list, new ValueComparator(unsortMap, sortDirection));

    Map sortedMap = new LinkedHashMap();
    for (Iterator it = list.iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry) it.next();
      sortedMap.put(entry.getKey(), entry.getValue());
    }

    return sortedMap;
  }


}


class ValueComparator implements Comparator {
  Map map;
  String sortDirection;

  public ValueComparator(Map map, String sortDirection) {
    this.map = map;
    this.sortDirection = sortDirection;
  }

  public int compare(Object keyA, Object keyB) {
    Comparable valueA = (Comparable) map.get(((Map.Entry) keyA).getKey());
    Comparable valueB = (Comparable) map.get(((Map.Entry) keyB).getKey());

    if (sortDirection.equals("ASC"))
      return valueA.compareTo(valueB);
    else
      return valueB.compareTo(valueA);
  }
}
