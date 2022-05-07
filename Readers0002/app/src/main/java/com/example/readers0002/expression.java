package com.example.readers0002;

import java.util.regex.Pattern;

public class expression {
    private String text;
    public expression(String text){
        this.text=text;
    }
    public String type(){
        boolean pattern;
        pattern= Pattern.matches("[a-zA-Z]+[a-zA-Z0-9_.]*[@][a-zA-Z]+[.][a-zA-Z]+", text);
        if(pattern) return "email";
        pattern= Pattern.matches("[a-zA-Z0-9_]+[a-zA-Z0-9_ ]*", text);
        if(pattern)return "text";
        pattern= Pattern.matches("[0-9]+", text);
        if(pattern)return "number";
        return "";
    }
    public int length(){
        return text.length();
    }
    public boolean test(){
        if(type().equals(""))return false;
        return true;
    }
    public boolean test_text(){
        if(type().equals("text"))return true;
        return false;
    }
}
