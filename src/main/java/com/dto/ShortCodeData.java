package com.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "shortcodedata")
@Table(name = "shortcodedata")
public class ShortCodeData implements Serializable {

    @Column
    private String original_url;

    @Id
    @Column
    private String short_code;

    @Column
    private Long expiry_time;

    @Column
    private String user_id;

    public ShortCodeData() {
    }

    public ShortCodeData(String original_url, String short_code, Long expiry_time, String userId) {
        this.original_url = original_url;
        this.short_code = short_code;
        this.expiry_time = expiry_time;
        this.user_id = userId;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public Long getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(Long expiry_time) {
        this.expiry_time = expiry_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ShortCodeData{" +
                "original_url='" + original_url + '\'' +
                ", short_code='" + short_code + '\'' +
                ", expiry_time=" + expiry_time +
                ", userId='" + user_id + '\'' +
                '}';
    }
}