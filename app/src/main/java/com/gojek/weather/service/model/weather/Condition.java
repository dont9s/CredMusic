
package com.gojek.weather.service.model.weather;

import com.google.gson.annotations.Expose;


public class Condition {

    @Expose
    private Long code;
    @Expose
    private String icon;
    @Expose
    private String text;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
