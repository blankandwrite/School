package aqtc.gl.school.main.eventbus.find;


import aqtc.gl.school.main.find.bean.CircleItemServer;

/**
 * @author gl
 * @date 2018/6/12
 * @desc 刷新动态
 */
public class RefreshEvent {
    public static final int ADD= 0;
    public static final int DELETE = 1;
    public CircleItemServer.ListBean bean;
    //0新增1删除
    public int type;

    public RefreshEvent() {

    }

    public RefreshEvent(CircleItemServer.ListBean bean, int type) {
        this.bean = bean;
        this.type = type;
    }
}
