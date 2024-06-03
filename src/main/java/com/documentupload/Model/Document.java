package com.documentupload.Model;

public class Document {

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String fileName;
    private String url;

    public Document (String fileName, String url){
        this.fileName = fileName;
        this.url = url;
    }


}
