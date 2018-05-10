package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class NewsListBean {

    public static class NewsBean{
        public String title;
        public String time;
        public String imageUrl;
    }

    public static ArrayList<NewsBean> getData(){
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i=0;i<12;i++){
            NewsBean newsBean = new NewsBean();
            newsBean.title="我校学子在“正大杯”全国大学生市场调查与分析大赛(安徽赛区)取得优异成绩";
            newsBean.time="2018-05-09";
            newsBean.imageUrl="http://www.aqnu.edu.cn/__local/B/88/0C/B5C7ACA5BF6C27178A0FE10BD10_F5DBA2F1_33E6C.jpg";
            arrayList.add(newsBean);
        }
        return  arrayList;
    }


}
