package com.abdelouahad.mustapha.topquiz.model;

/**
 * Created by Mustapha ABDELOUAHAD - Openclassrooms on 02/04/2018.
 */
public class User {

    private String mFirstname;



    public String getFirstname() {
        return mFirstname;
    }

    public void setFirstname(String firstname) {
        mFirstname = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "mFirstname='" + mFirstname + '\'' +
                '}';
    }
}
