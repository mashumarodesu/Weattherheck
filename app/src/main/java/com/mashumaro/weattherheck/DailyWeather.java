package com.mashumaro.weattherheck;

public class DailyWeather {
    private String date;
    private String maxTemp;
    private String minTemp;
    private String condition;
    private String conditionIcon;
    private String timezone;

    public DailyWeather(String date, String maxTemp, String minTemp, String condition, String conditionIcon, String timezone) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.conditionIcon = conditionIcon;
        this.timezone = timezone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public void setConditionIcon(String conditionIcon) {
        this.conditionIcon = conditionIcon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
