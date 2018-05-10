package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/10
 * @desc
 */
public class FaxListBean {

    public static class FaxBean{
        public String title;
        public String time;
        public String imageUrl;
    }

    public static ArrayList<FaxBean> getData(){
        ArrayList<FaxBean> arrayList = new ArrayList<>();
        for (int i=0;i<12;i++){
            FaxBean FaxBean = new FaxBean();
            FaxBean.title="882公里蓝色慢跑为自闭症儿童筹来千元善款";
            FaxBean.time="2018-05-08";
            FaxBean.imageUrl="http://www.aqnu.edu.cn/__local/1/0F/B8/865F6D5685B911AA63E5540E20A_26EC4B40_1CB26.jpg";
            arrayList.add(FaxBean);
        }
        return  arrayList;
    }


}
