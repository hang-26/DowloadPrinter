package com.example.dowloadprinter.recycleview;

public class MyModel {
    private String nameP;
    private  String sizeP;
    private  String timeP;
    private String type;
    public String imageP;
    public  MyModel( String name, String size, String time, String type){
        this.nameP = name;
        this.sizeP = size;
        this.timeP = time;
        this.type = type;
        //this.imageP = imP;
    }

    public String getNameP(){
        return this.nameP;
    }

    public String getSizeP(){
        return this.sizeP;
    }

    public String getTimeP(){
        return this.timeP;
    }
    public String getType(){
        return  this.type;
    }
}
