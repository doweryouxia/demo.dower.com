package com.dower.demo.app.core.dto;

/**
 * Created by wang on 2017/6/14.
 */
public class UploadFile {
    private int id;
    private String file_name;
    private String file_path;
    private int file_type;

    public int getId() {
        return id;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "id=" + id +
                ", file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_type=" + file_type +
                '}';
    }
}
