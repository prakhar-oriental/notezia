package com.example.notezia;

public class model2 {
    String notetitle;
    String notedesc;

    public model2() {
    }

    public model2(String notetitle, String notedesc) {
        this.notetitle = notetitle;
        this.notedesc = notedesc;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedesc() {
        return notedesc;
    }

    public void setNotedesc(String notedesc) {
        this.notedesc = notedesc;
    }
}
