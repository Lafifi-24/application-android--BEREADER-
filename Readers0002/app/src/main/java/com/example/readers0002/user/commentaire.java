package com.example.readers0002.user;

public class commentaire {
    String date,username,commentairetext,icon;
    int iduser,idbook,idcommentaire;
    public commentaire(int iduser,int idcommentaire,String username,String commentaire,int idbook){
        this.iduser=iduser;

        this.username=username;
        this.commentairetext=commentaire;
        this.idbook=idbook;
        this.idcommentaire=idcommentaire;
    }
    public commentaire(String username,String commentaire,String date){


        this.username=username;
        this.commentairetext=commentaire;
        this.date=date;
    }
    public commentaire(int iduser,int idcommentaire,String date,String username,String commentaire){
        this.iduser=iduser;
        this.date=date;
        this.username=username;
        this.idcommentaire=idcommentaire;
        this.commentairetext=commentaire;

    }
    public String getDate(){
      return this.date;
    }
    public String getUsername(){
        return this.username;
    }
    public String getCommentairetext(){
        return this.commentairetext;
    }
    public int getIdcommentaire(){
        return this.idcommentaire;
    }
    public String getIcon(){
        return this.icon;
    }
    public int getIduser(){
        return this.iduser;
    }
}
