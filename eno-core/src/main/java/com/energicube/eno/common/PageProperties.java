package com.energicube.eno.common;

import com.energicube.eno.monitor.model.NoticeBoard;

import java.util.ArrayList;
import java.util.List;

public class PageProperties {

    public static final int NoticeBoard_Page = 18;
    public static final int User_Page = 15;

    public static int getNbEndIndex(List<NoticeBoard> noticeBoardListByTimeList) {
        int endIndex = 0;
        if (noticeBoardListByTimeList.size() > 0) {
            endIndex = noticeBoardListByTimeList.size() / NoticeBoard_Page;
            if (noticeBoardListByTimeList.size() % NoticeBoard_Page != 0) {
                endIndex = endIndex + 1;
            }
        }
        return endIndex;
    }

    public static List<NoticeBoard> getNbStartPageList(List<NoticeBoard> noticeBoardListByTimeList) {
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        if (noticeBoardListByTimeList.size() <= NoticeBoard_Page) {
            noticeBoardListPageList = noticeBoardListByTimeList;
        } else {
            for (int k = 0; k < NoticeBoard_Page; k++) {
                noticeBoardListPageList.add(noticeBoardListByTimeList
                        .get(k));
            }
        }
        return noticeBoardListPageList;
    }

    public static List<NoticeBoard> getNbPagefList(List<NoticeBoard> noticeBoardListByTimeList, int pageNumber) {
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        if (noticeBoardListByTimeList.size() <= NoticeBoard_Page) {
            noticeBoardListPageList = noticeBoardListByTimeList;
        } else {
            for (int k = NoticeBoard_Page * (pageNumber - 1); k < NoticeBoard_Page * (pageNumber - 1)
                    + NoticeBoard_Page
                    && k < noticeBoardListByTimeList.size(); k++) {
                noticeBoardListPageList.add(noticeBoardListByTimeList
                        .get(k));
            }
        }
        return noticeBoardListPageList;
    }
}
