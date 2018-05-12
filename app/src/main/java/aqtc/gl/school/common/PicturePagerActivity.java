package aqtc.gl.school.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import aqtc.gl.school.R;
import aqtc.gl.school.base.BaseActivity;
import aqtc.gl.school.utils.image.ImageLoad;
import aqtc.gl.school.widget.PhotoViewPager;
import butterknife.BindView;

/**
 * @author gl
 * @date 2018/5/9
 * @desc 图片浏览
 */
public class PicturePagerActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    PhotoViewPager mViewPager;
    private ArrayList<String> mUrlList = new ArrayList<>();
    private int position;
    private String title;

    public static void goPicturePagerActivity(Context context, ArrayList<String> urlList,int position,String title){
        Intent intent = new Intent(context, PicturePagerActivity.class);
        intent.putStringArrayListExtra("urlList",urlList);
        intent.putExtra("position",position);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }

    @Override
    public int getActivityViewById() {
        return R.layout.activity_scence_detail;
    }

    @Override
    public void initView() {
        mUrlList.clear();
        mUrlList.addAll(getIntent().getStringArrayListExtra("urlList"));
        position = getIntent().getIntExtra("position",0);
        title = getIntent().getStringExtra("title");
        mViewPager.setAdapter(new PhotoViewPagerAdapter(mUrlList,mContext));
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void findTitleViewId() {
        mTitleView = findViewById(R.id.titleView);
        mTitleView.setTitle(title);

    }

    class PhotoViewPagerAdapter extends PagerAdapter{
        private ArrayList<String> urlList;
        private Context context;

        public PhotoViewPagerAdapter(ArrayList<String> urlList, Context context) {
            this.urlList = urlList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return urlList.size();
        }

        @NonNull
        @Override
        public View instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_photo_viewpager,null,false);
            if (null != view){
                PhotoView photoView = view.findViewById(R.id.photoView);
                ImageLoad.loadImageFit(context,urlList.get(position),photoView,R.mipmap.no_image);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }
    }
}
