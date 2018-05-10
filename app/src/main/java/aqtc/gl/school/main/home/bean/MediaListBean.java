package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class MediaListBean {

    public static class MediaBean{
        public String title;
        public String time;
        public String imageUrl;
    }

    public static ArrayList<MediaBean> getData(){
        ArrayList<MediaBean> arrayList = new ArrayList<>();
        for (int i=0;i<12;i++){
            MediaBean MediaBean = new MediaBean();
            MediaBean.title="新安晚报：安庆师大一老师用歌唱方式教授古典诗词";
            MediaBean.time="2018-05-08";
            MediaBean.imageUrl="http://www.aqnu.edu.cn/__local/A/89/FC/848657064810485F7CFF2C4C7A2_93E9D458_12596.jpg";
            arrayList.add(MediaBean);
        }
        return  arrayList;
    }


}
