package com.heath.playlocalvideo;

public class ItemBean {

    public int backImgSrc;
    public String titleStr;
    public String sourceStr;

    public ItemBean (int backImgSrc, String titleStr, String sourceStr) {
        this.backImgSrc = backImgSrc;
        this.titleStr = titleStr;
        this.sourceStr = sourceStr;
    }

    public int getBackImgStr() {
        return backImgSrc;
    }

    public void setBackImgStr(int backImgStr) {
        this.backImgSrc = backImgStr;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getSourceStr() {
        return sourceStr;
    }

    public void setSourceStr(String sourceStr) {
        this.sourceStr = sourceStr;
    }
}
