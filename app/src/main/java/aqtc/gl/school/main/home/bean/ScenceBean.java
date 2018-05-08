package aqtc.gl.school.main.home.bean;

import java.util.ArrayList;

/**
 * @author gl
 * @date 2018/5/8
 * @desc
 */
public class ScenceBean {
    public static class ScenceEntity{
        public String title;
        public String url;
    }
    public static ArrayList<ScenceEntity> getData(){
        ArrayList<ScenceEntity> arrayList = new ArrayList<>();
        for (int i=0;i<5;i++){
            ScenceEntity seeneList = new ScenceEntity();
            if (i==0){
                seeneList.title="春";
                seeneList.url="http://www.aqnu.edu.cn/dfiles/8857/scene/images/01.jpg";
            }else if (i==1){
                seeneList.title="夏";
                seeneList.url="http://www.aqnu.edu.cn/dfiles/8857/scene/images/16.jpg";
            }else if (i==2){
                seeneList.title="秋";
                seeneList.url="http://www.aqnu.edu.cn/dfiles/8857/scene/images/02.jpg";
            }else if (i==3){
                seeneList.title="冬";
                seeneList.url="http://www.aqnu.edu.cn/dfiles/8857/scene/images/08.jpg";
            }else {
                seeneList.title="其他";
                seeneList.url="http://www.aqnu.edu.cn/dfiles/8857/scene/images/21.jpg";
            }
            arrayList.add(seeneList);
        }
        return  arrayList;
    }
}
