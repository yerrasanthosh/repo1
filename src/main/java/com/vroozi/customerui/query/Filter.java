package com.vroozi.customerui.query;

public class Filter {

  private final String fieldName;

  private final Object matchValue;
  
  private final boolean exact;
  
  private final boolean inArray;

  private final boolean textSearch;
  
  private final String[] textSearchFields;
  /**
   * Exact Match Filter
   * @param fieldName
   * @param matchValue
   */
  public Filter(String fieldName, Object matchValue) {
    this(fieldName, matchValue, true);
  }
  
  /**
   * Regex Filter
   * @param fieldName
   * @param matchValue
   * @param requireExactMatch
   */
  public Filter(String fieldName, Object matchValue, boolean requireExactMatch) {
    this(fieldName, matchValue, requireExactMatch, false);
  }

  /**
   * Regex Filter
   * @param fieldName
   * @param matchValue
   * @param requireExactMatch
   * @param isInArray
   */
  public Filter(String fieldName, Object matchValue, boolean requireExactMatch, boolean isInArray) {
    this.fieldName = fieldName;
    this.matchValue = matchValue;
    this.exact = requireExactMatch;
    this.textSearch = false;
    this.textSearchFields = null;
    this.inArray = isInArray;
  }
  
  /**
   * Text Search Filter
   * @param textSearchFields Text fields used in text search.
   * @param matchValue
   */
  public Filter(String[] textSearchFields, Object matchValue) {
    super();
    this.fieldName = null;
    this.matchValue = matchValue;
    this.exact = false;
    this.textSearch = true;
    this.textSearchFields = textSearchFields;
    this.inArray = false;
  }
  
  public String getFieldName() {
    return fieldName;
  }

  public Object getMatchValue() {
    return matchValue;
  }

  public boolean isExactMatchRequired() {
    return exact;
  }

  public boolean isInArray() {
    return inArray;
  }

  public boolean isTextSearch() {
    return textSearch;
  }

  public String[] getTextSearchFields() {
    return textSearchFields;
  }

}
