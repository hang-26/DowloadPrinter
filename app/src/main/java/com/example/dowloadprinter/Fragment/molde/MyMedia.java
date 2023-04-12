package com.example.dowloadprinter.Fragment.molde;

enum TypeMedia {
    VIEDEO, PHOTO, GIFT,STORAGE;
}

public  abstract class MyMedia {
    public TypeMedia typeMedia;
    public int quality;
    public String qualityString;
    public String link;
    public String title;
}

