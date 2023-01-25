package org.mycode.harmonogram;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParseTasks {
    public List<ParseTask> tasks;

    @SerializedName("PK")
    public List<ParseTask> PK;
}
