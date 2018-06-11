package aqtc.gl.school.main.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.base.RBasePresenter;
import aqtc.gl.school.widget.TitleView;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/27
 * @desc
 */
public class MyInfoEditActivity extends BaseActivity {

    @BindView(R.id.et_info)
    EditText etInfo;
    /**
     * type= 0 用户名，1 学院 2，级别 3，专业 4，班级 5，密码
     */
    private int type=0;
    private String hintText;
    private String title;

    public static void goMyInfoEditActivity(Context context,int type){
        Intent intent = new Intent(context,MyInfoEditActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.activity_myinfo_edit;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
     type = getIntent().getIntExtra("type",0);
     switch (type){
         // 0 用户名，1 学院 2，级别 3，专业 4，班级 5，密码
         case 0:
             hintText = "用户名";
             title = "更改用户名";
             break;
         case 1:
             hintText = "学院";
             title = "更改学院";
             break;
         case 2:
             hintText = "级别";
             title = "更改级别";
             break;
         case 3:
             hintText = "专业";
             title = "更改专业";
             break;
         case 4:
             hintText = "班级";
             title = "更改班级";
             break;
         case 5:
             hintText = "密码";
             title = "更改密码";
             break;
             default:
                 hintText = "";
                 title = "";
                 break;
     }
        etInfo.setHint(hintText);
     if (type==5){
         etInfo.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
     }
    }

    @Override
    public void findTitleViewId() {
        mTitleView =findViewById(R.id.titleView);
        mTitleView.setTitle(title);
        mTitleView.setRightTitle("提交");
        mTitleView.setRightTitleListener(new TitleView.RightTitleListener() {
            @Override
            public void rightTitleClick() {

            }
        });
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }
}
