package com.marfarijj.buildingmanagement.Models;

public class Notice {
    String noticeFrom, noticeSubject, noticeDescription;


    public Notice() {
    }

    public Notice(String noticeFrom, String noticeSubject, String noticeDescription) {
        this.noticeFrom = noticeFrom;
        this.noticeSubject = noticeSubject;
        this.noticeDescription = noticeDescription;
    }

    public String getNoticeFrom() {
        return noticeFrom;
    }

    public String getNoticeSubject() {
        return noticeSubject;
    }

    public String getNoticeDescription() {
        return noticeDescription;
    }
}
