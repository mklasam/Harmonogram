package org.mycode.harmonogram;

import com.google.gson.annotations.SerializedName;

public class ParseTask {

    @SerializedName("author")
    public String author;

    @SerializedName("descriptionTask")
    public String descriptionTask;

    @SerializedName("finish_day")
    public String finish_day;

    @SerializedName("hour")
    public String hour;

    @SerializedName("id")
    public String id;

    @SerializedName("start_day")
    public String start_day;

    @SerializedName("taskName")
    public String taskName;
}
