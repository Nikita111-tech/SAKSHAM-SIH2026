package com.sih.saksham.model;
import jakarta.persistence.*;
@Entity
@Table(name="users")
public class User {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String name; private String email;
 private int communicationScore; private int technicalScore; private int teamworkScore;
 @Column(length=5000) private String aiRoadmap;
 public Long getId(){return id;} public void setId(Long id){this.id=id;}
 public String getName(){return name;} public void setName(String n){this.name=n;}
 public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
 public int getCommunicationScore(){return communicationScore;} public void setCommunicationScore(int s){this.communicationScore=s;}
 public int getTechnicalScore(){return technicalScore;} public void setTechnicalScore(int s){this.technicalScore=s;}
 public int getTeamworkScore(){return teamworkScore;} public void setTeamworkScore(int s){this.teamworkScore=s;}
 public String getAiRoadmap(){return aiRoadmap;} public void setAiRoadmap(String r){this.aiRoadmap=r;}
}
