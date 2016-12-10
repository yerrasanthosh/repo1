package com.vroozi.customerui.query;

public class DataError {

  private String id;
  private String code;
  private String message;

  public DataError() {
    super();
  }

  public DataError(String code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

  public DataError(String id, String code, String message) {
    super();
    this.id = id;
    this.code = code;
    this.message = message;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    DataError other = (DataError) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Error [code=" + code + ", message=" + message + "]";
  }

}
