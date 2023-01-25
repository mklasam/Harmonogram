package org.mycode.harmonogram;

public class ParseTaskClass {
    private String startDate;
    private String finishDate;
    private String hour;
    private String author;
    private String taskName;
    private String taskDescre;
    private int id;

    public ParseTaskClass(String startDate, String finishDate, String hour, String author, String taskName, String taskDescre, int id) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.hour = hour;
        this.author = author;
        this.taskName = taskName;
        this.taskDescre = taskDescre;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescre() {
        return taskDescre;
    }

    public void setTaskDescre(String taskDescre) {
        this.taskDescre = taskDescre;
    }
}
