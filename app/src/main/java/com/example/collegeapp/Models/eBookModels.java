package com.example.collegeapp.Models;

public class eBookModels {
    String coursetitle,pdfUrl;


    public eBookModels(String coursetitle, String pdfUrl) {
        this.coursetitle = coursetitle;
        this.pdfUrl = pdfUrl;
    }

    public eBookModels(){}


    public String getCoursetitle() {
        return coursetitle;
    }

    public void setCoursetitle(String coursetitle) {
        this.coursetitle = coursetitle;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}
