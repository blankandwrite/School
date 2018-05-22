package aqtc.gl.school.main.home.model;

import java.util.List;

import aqtc.gl.school.base.BaseBean;

/**
 * @author gl
 * @date 2018/5/22
 * @desc
 */
public class MediaListEntity extends BaseBean {
    /**
     * err : 0
     * msg : ok
     * data : {"current_page":1,"first_page_url":"http://106.14.8.195:8800/api/v1/app/article/pages?page=1",
     * "from":1,"last_page":2,"last_page_url":"http://106.14.8.195:8800/api/v1/app/article/pages?page=2",
     * "next_page_url":"http://106.14.8.195:8800/api/v1/app/article/pages?page=2",
     * "path":"http://106.14.8.195:8800/api/v1/app/article/pages",
     * "per_page":20,"prev_page_url":null,"to":20,"total":25,
     * "list":[{"id":615,"school_id":1,"category_id":4,"title":"江淮晨报：长江安庆段实施刀鲚栖息地生态修复工程","author":"","publish_time":"2018-04-16"}]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * current_page : 1
         * first_page_url : http://106.14.8.195:8800/api/v1/app/article/pages?page=1
         * from : 1
         * last_page : 2
         * last_page_url : http://106.14.8.195:8800/api/v1/app/article/pages?page=2
         * next_page_url : http://106.14.8.195:8800/api/v1/app/article/pages?page=2
         * path : http://106.14.8.195:8800/api/v1/app/article/pages
         * per_page : 20
         * prev_page_url : null
         * to : 20
         * total : 25
         * list : [{"id":602,"school_id":1,"category_id":4,"title":"中青在线：安庆师范大学志愿者四年爱心接力温情助力单亲双胞胎兄弟成长","author":"","publish_time":"2018-03-15"},{"id":603,"school_id":1,"category_id":4,"title":"安徽教育网：全省教育系统广大干部师生对十三届全国人大一次会议胜利闭幕反响热烈","author":"","publish_time":"2018-03-22"},{"id":604,"school_id":1,"category_id":4,"title":"中青在线：安庆师大一班级集体无偿献血，将所得牛奶赠保洁门卫","author":"","publish_time":"2018-03-27"},{"id":605,"school_id":1,"category_id":4,"title":"安徽青年报：与单亲双胞胎兄弟共成长","author":"","publish_time":"2018-03-27"},{"id":606,"school_id":1,"category_id":4,"title":"新安晚报：学长手写150封信提醒新生\u201c少走弯路\u201d","author":"","publish_time":"2018-03-27"},{"id":607,"school_id":1,"category_id":4,"title":"中青在线：安庆师范大学白鲸文学社连续25年赴海子故居缅怀诗人","author":"","publish_time":"2018-03-29"},{"id":608,"school_id":1,"category_id":4,"title":"皖江晚报：安庆师大校友夫妇将价值3万元树木赠母校","author":"","publish_time":"2018-04-02"},{"id":609,"school_id":1,"category_id":4,"title":"人民网：朝七晚九安庆师大一学院施行最严\u201c早晨读晚自习\u201d制度","author":"","publish_time":"2018-04-04"},{"id":610,"school_id":1,"category_id":4,"title":"新安晚报：45名大学生挑战\u201c一元钱过一天\u201d","author":"","publish_time":"2018-04-10"},{"id":611,"school_id":1,"category_id":4,"title":"中国科学报：长江安庆段实施刀鲚栖息地生态修复工程","author":"","publish_time":"2018-04-10"},{"id":612,"school_id":1,"category_id":4,"title":"梨视频：大学生9年接力,助力脑瘫小伙做训练","author":"","publish_time":"2018-04-11"},{"id":613,"school_id":1,"category_id":4,"title":"皖江晚报：寒假期间不慎摔倒致使腿部骨折安庆师大汪博武老师架双拐上课","author":"","publish_time":"2018-04-12"},{"id":614,"school_id":1,"category_id":4,"title":"安徽日报：种植竹柳修复刀鲚栖息地","author":"","publish_time":"2018-04-12"},{"id":615,"school_id":1,"category_id":4,"title":"江淮晨报：长江安庆段实施刀鲚栖息地生态修复工程","author":"","publish_time":"2018-04-16"},{"id":616,"school_id":1,"category_id":4,"title":"梨视频：大学老师制绘本送人,2小时抢光百本","author":"","publish_time":"2018-04-16"},{"id":617,"school_id":1,"category_id":4,"title":"新安晚报：雅化的黄梅戏还得\u201c养戏于俗\u201d","author":"","publish_time":"2018-04-18"},{"id":618,"school_id":1,"category_id":4,"title":"梨视频：课太难！大学生为听懂,提前1天占座","author":"","publish_time":"2018-04-23"},{"id":619,"school_id":1,"category_id":4,"title":"新安晚报：大学才女作品亮相插画界\u201c奥斯卡\u201d","author":"","publish_time":"2018-04-23"},{"id":620,"school_id":1,"category_id":4,"title":"人民网：安庆师大举行\u201c五四\u201d优秀青年表彰大会","author":"","publish_time":"2018-05-08"},{"id":621,"school_id":1,"category_id":4,"title":"新安晚报：妻子患病如孩童，丈夫相守16载","author":"","publish_time":"2018-05-08"}]
         */

        public int current_page;
        public String first_page_url;
        public int from;
        public int last_page;
        public String last_page_url;
        public String next_page_url;
        public String path;
        public int per_page;
        public Object prev_page_url;
        public int to;
        public int total;
        public List<DataBean.ListBean> list;

        public static class ListBean {
            /**
             * id : 602
             * school_id : 1
             * category_id : 4
             * title : 中青在线：安庆师范大学志愿者四年爱心接力温情助力单亲双胞胎兄弟成长
             * author :
             * publish_time : 2018-03-15
             */

            public int id;
            public int school_id;
            public int category_id;
            public String title;
            public String author;
            public String publish_time;
        }
    }
}
