package aqtc.gl.school.fragment.entity;

import java.util.List;

import aqtc.gl.school.base.BaseBean;

/**
 * @author gl
 * @date 2018/5/24
 * @desc
 */
public class CategoryBean extends BaseBean {

    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 1
         * school_id : 1
         * cover :
         * name : 师大要闻
         */

        public int id;
        public int school_id;
        public String cover;
        public String name;
    }
}
