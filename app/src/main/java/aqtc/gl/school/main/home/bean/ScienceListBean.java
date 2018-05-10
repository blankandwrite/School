package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class ScienceListBean {

    public static class ScienceBean{
        public String title;
        public String time;

    }

    public static ArrayList<ScienceBean> getData(){
        ArrayList<ScienceBean> arrayList = new ArrayList<>();
        for (int i=0;i<12;i++){
            ScienceBean scienceBean = new ScienceBean();
            scienceBean.title="南开大学尹沧海教授举办题为《意象与移情——从徐淮地区中国绘画大写意地域风格谈起》的学术讲座";
            scienceBean.time="[2018-05-09]";
            arrayList.add(scienceBean);
        }
        return  arrayList;
    }


}
