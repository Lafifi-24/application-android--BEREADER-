package com.example.readers0002.user;

public class user {

    private static String UserName;
    private static int id;
    private static int signale;
    private static String Email;
    private static String Country;
    private static String Nphone;

    public user(){
    }
    public user(int id,String Username,String Email,String Country,String Nphone,int signale){
        this.Email=Email;
        this.Nphone = Nphone;
        this.Country=Country;
        this.UserName=Username;
        this.id=id;
        this.signale=signale;

    }
    public user(int id,String Username,String Email,int signale){
        this.Email=Email;
        this.UserName=Username;
        this.id=id;
        this.signale=signale;

    }

    public static String getUserName() {
        return UserName;
    }

    public static String getEmail() {
        return Email;
    }

    public static String getCountry() {
        return Country;
    }

    public static String getNphone() {
        return Nphone;
    }
    public static int getId() {
        return id;
    }
    public static int getSignale() {
        return signale;
    }

    public void inisial(){
        this.Email="";
        this.Nphone = "";
        this.Country="";
        this.UserName="";
        id=-1;
        signale=-1;

    }
}
