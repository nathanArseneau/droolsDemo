package ca.nathan.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Facts {
    public int phmAge;
    public int shmAge;

    public int phmAssets;
    public int shmAssets;

    public String maritalStatus;

    public Date date;
}
