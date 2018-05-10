package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NoticeListBean {

    public static class NoticeBean{
        public String title;
        public String day;
        public String year;
    }

    public static ArrayList<NoticeBean> getData(){
        ArrayList<NoticeBean> arrayList = new ArrayList<>();
        for (int i=0;i<12;i++){
            NoticeBean noticeBean = new NoticeBean();
            noticeBean.title="国家艺术基金2017年度艺术人才培养资助项目 “黄梅戏青年旦角演员培养”招生简章";
            noticeBean.day="05-08";
            noticeBean.year="2018";
            arrayList.add(noticeBean);
        }
        return  arrayList;
    }


}
