package aqtc.gl.school.fragment.banner;


import java.util.ArrayList;
import java.util.List;

/**
 * @author gl
 * @date 2018/5/6
 * @desc 首页轮播图bean
 */
public class BannerBean {

    public List<DataEntity> data;

    public static class DataEntity {
        public String path;
    }

    public static BannerBean getBannerBean(){
        BannerBean bannerBean = new BannerBean();
        List<DataEntity> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            DataEntity dataEntity = new DataEntity();
            if (i==0){
                dataEntity.path="http://www.aqnu.edu.cn/images/jiepai1.jpg";
            }else if(i==1){
                dataEntity.path="http://www.aqnu.edu.cn/images/16/04/29/126fbmr252/1.jpg";
            }else if (i==2){
                dataEntity.path="http://www.aqnu.edu.cn/images/16/04/29/126fbmr252/2.jpg";
            }else if (i==3){
                dataEntity.path="http://www.aqnu.edu.cn/images/16/04/29/126fbmr252/3.jpg";
            }else {
                dataEntity.path="http://www.aqnu.edu.cn/images/16/04/29/126fbmr252/10.jpg";
            }
            list.add(dataEntity);

        }
        bannerBean.data=list;
        return bannerBean;
    }

}
