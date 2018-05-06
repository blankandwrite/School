package aqtc.gl.school.fragment.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;

import aqtc.gl.school.R;


public class SimpleImageBanner extends BaseIndicatorBanner<BannerBean.DataEntity, SimpleImageBanner> {

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        // final HomeBean.DataEntity.ListcarouselEntity item = mDatas.get(position);
        // tv.setText(item.getCreateTime());
        //  tv.setText(mDatas.get(position).title);
    }

    @Override
    public View onCreateItemView(final int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_image, null);
        ImageView iv = (ImageView) inflate.findViewById(R.id.iv);
        final int index = position;
        String url= mDatas.get(position).path;
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));


        Glide.with(mContext).load(url)
                .override(itemWidth, itemHeight)
                .placeholder(R.mipmap.no_image).error(R.mipmap.no_image).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {//点击轮播图跳转
            @Override
            public void onClick(View v) {

            }
        });

        return inflate;
    }
}
