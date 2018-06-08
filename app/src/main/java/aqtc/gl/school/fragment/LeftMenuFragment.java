package aqtc.gl.school.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseFragment;
import aqtc.gl.school.common.Global;
import aqtc.gl.school.common.preload.Preloader;
import aqtc.gl.school.main.login.LoginInfoCache;
import aqtc.gl.school.utils.ToastUtils;
import aqtc.gl.school.utils.apputil.Apputil;
import butterknife.OnClick;

/**
 * @author gl
 * @date 2018/5/13
 * @desc
 */
public class LeftMenuFragment extends BaseFragment {


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Global.QUIT_CODE){
               if (Apputil.checkLogin(LeftMenuFragment.this)){
                   ToastUtils.showMsg(mContext,"退出失败");
               }else {
                   getActivity().finish();
               }
            }
        }
    };

    public static LeftMenuFragment newInatace(){
        return new LeftMenuFragment();
    }
    @Override
    public void initView(View rootView) {

    }

    @Override
    public int getFragmentViewId() {
        return R.layout.fragment_menu_left;
    }

    @OnClick(R.id.btn_quit)
    void quit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Preloader.getInstance(mContext).reset();
                LoginInfoCache.getInstance().clear(mContext);
                mHandler.sendEmptyMessage(Global.QUIT_CODE);
            }
        }).start();


    }
}
