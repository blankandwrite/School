package aqtc.gl.school.widget.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.utils.Utils;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 图片选择弹框
 */
public class SelectPicPopWindow extends PopupWindow{
    private List<String> mStringList;
    private Context mContext;
    private OnIetmSelectListener mOnIetmSelectListener;

    public SelectPicPopWindow(Context context, List<String> stringList) {
        super(context);
        mContext = context;
        mStringList = stringList;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.select_pic_list_pop, null);
        ListView listView= (ListView) view.findViewById(R.id.listView);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.fly_popu);
        LinearLayout layContent = (LinearLayout) view.findViewById(R.id.lay_content);
        SelectAdapter selectAdapter =  new SelectAdapter(context, mStringList);
        listView.setAdapter(selectAdapter);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        lp.setMargins(Utils.getScreenWidth(context) / 6, 0, Utils.getScreenWidth(context) / 6, 0);
        layContent.setLayoutParams(lp);

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShowing()) {
                    dismiss();
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != mOnIetmSelectListener){
                    mOnIetmSelectListener.select(i);
                }
            }
        });


        setContentView(view);

    }




    public interface  OnIetmSelectListener{
        void select(int posotion);
    }

    public void setOnIetmSelectListener(OnIetmSelectListener onIetmSelectListener) {
        mOnIetmSelectListener = onIetmSelectListener;
    }

    private class SelectAdapter extends BaseAdapter{
        private Context mContext;
        private List<String> mStringList;

        public SelectAdapter(Context context, List<String> stringList) {
            mContext = context;
            mStringList = stringList;
        }

        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int i) {
            return mStringList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view){
                view = View.inflate(mContext,R.layout.item_select_pic,null);
            }
            TextView textView = view.findViewById(R.id.tv_name);
            textView.setText(mStringList.get(i));

            return view;
        }
    }
}
