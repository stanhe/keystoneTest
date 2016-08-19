package com.example.stan.keystonetest.model;

/**
 * Created by stan on 16/8/18.
 */

public class LoginResponse{

    public int status;
    public String message;
    public DataEntity data;

    public static class DataEntity {
        public String _id;
        public String playerName;
        public String token;
        public String lastRequestDate;
        public int playerGold;
        public int playerLevel;
        public int playerAction;
    }
}