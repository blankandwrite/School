package aqtc.gl.school.main.login.entity;

import aqtc.gl.school.base.BaseBean;

/**
 *登录实体类
 */
public class LoginBean extends BaseBean {


    /**
     * data : {"token":"eyJpZCI6Ijk4YTRkYjA5MmVhZjQ5YzNiNWE3ZTg0MmUzMWUwZTYxIiwic3R1ZGVudE5hbWUiOiLlp5rpo54iLCJjbGF6eklkIjoiMGVjYzUyYzgzNzE1NGQ2MWE2NTBlMWEyYzk0MmJhOWMiLCJwaG9uZSI6IjE4MzAxMDA2Mzc3Iiwic3R1ZGVudENvZGUiOiJ5YW9mZWkifQ==","clazzId":"0ecc52c837154d61a650e1a2c942ba9c","clazzName":"2班","phone":"18301006377","imUser":"98a4db092eaf49c3b5a7e842e31e0e61","imPwd":"123456","studentCode":"yaofei","tags":["STUDENT","STUDENT0ecc52c837154d61a650e1a2c942ba9c","STUDENTc1934a6625d849dd954f63ffe2ea4092"]}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * token : eyJpZCI6Ijk4YTRkYjA5MmVhZjQ5YzNiNWE3ZTg0MmUzMWUwZTYxIiwic3R1ZGVudE5hbWUiOiLlp5rpo54iLCJjbGF6eklkIjoiMGVjYzUyYzgzNzE1NGQ2MWE2NTBlMWEyYzk0MmJhOWMiLCJwaG9uZSI6IjE4MzAxMDA2Mzc3Iiwic3R1ZGVudENvZGUiOiJ5YW9mZWkifQ==
         * clazzId : 0ecc52c837154d61a650e1a2c942ba9c
         * clazzName : 2班
         * studentCode : yaofei
         */

        public String token;
        public String clazzId;
        public String clazzName;
        public String studentCode;

    }
}
