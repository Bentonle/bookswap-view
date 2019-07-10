package com.bookswap.model;

import java.util.ArrayList;
import java.util.List;

public class School {
    private String Name;
    private List<Campus> campus = new ArrayList<>();

    public School() {}

    public School(String name, List<Campus> campus) {
        super();
        Name = name;
        this.campus = campus;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public List<Campus> getCampus() {
        return campus;
    }
    public void setCampus(List<Campus> campus) {
        this.campus = campus;
    }
}
