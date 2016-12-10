package com.vroozi.customerui.upload.model;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
/**
 * Name: Sajid Akram Kashmiri
 */
public class FileObject {


  private String id;
  private String name;
  private long size;
  private FileObjectStatus status;
  private FileObjectStorage storage;
  private Date createdOn;
  private File file;
  private InputStream stream;
  private String requesterName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public FileObjectStatus getStatus() {
    return status;
  }

  public void setStatus(FileObjectStatus status) {
    this.status = status;
  }

  public FileObjectStorage getStorage() {
    return storage;
  }

  public void setStorage(FileObjectStorage storage) {
    this.storage = storage;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public InputStream getStream() {
    return stream;
  }

  public void setStream(InputStream stream) {
    this.stream = stream;
  }

  public String getRequesterName() {
    return requesterName;
  }

  public void setRequesterName(String requesterName) {
    this.requesterName = requesterName;
  }
}
