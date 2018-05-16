package aqtc.gl.school.main.find.widgets.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.R;
import aqtc.gl.school.main.find.adapter.CommonAdapter;
import aqtc.gl.school.utils.file.media.PhotoFolderEntity;


/**
 * 图片选择弹框
 */
public class PhotoFolderPopWindow extends PopupWindow {
	/**
	 * 布局文件的最外层View
	 */
	protected View mContentView;
	protected Context context;
	/**
	 * ListView的数据集
	 */
	protected List<PhotoFolderEntity> mDatas;
	private int mCurrentPosition = 0;

	private ListView mListDir;

	public PhotoFolderPopWindow(int width, int height,
                                List<PhotoFolderEntity> list, View convertView) {
		this(convertView, width, height, true, list);
	}

	public PhotoFolderPopWindow(View contentView, int width, int height,
                                boolean focusable) {
		this(contentView, width, height, focusable, null);
	}

	public PhotoFolderPopWindow(View contentView, int width, int height,
                                boolean focusable, List<PhotoFolderEntity> list) {
		this(contentView, width, height, focusable, list, new Object[0]);

	}

	public PhotoFolderPopWindow(View contentView, int width, int height,
                                boolean focusable, List<PhotoFolderEntity> list, Object... params) {
		super(contentView, width, height, focusable);
		this.mContentView = contentView;
		context = contentView.getContext();
		mDatas = list;
		if (mDatas == null)
			this.mDatas = new ArrayList<PhotoFolderEntity>();

		setBackgroundDrawable(new BitmapDrawable());
		setTouchable(true);
		setOutsideTouchable(true);
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initViews();
		initEvents();
	}

	public void initViews() {
		mListDir = (ListView) findViewById(R.id.id_list_dir);
		mListDir.setAdapter(new CommonAdapter<PhotoFolderEntity>(context,
				mDatas, R.layout.find_photo_select_list_item) {
			@Override
			public void convert(ViewHolder helper, PhotoFolderEntity item) {
				if (helper.getPosition() == mCurrentPosition) {
					helper.getView(R.id.photo_list_item_select).setVisibility(
							View.VISIBLE);
				} else {
					helper.getView(R.id.photo_list_item_select).setVisibility(
							View.GONE);
				}
				setText(helper, R.id.photo_list_item_name, item.getName());
				setImageByUrl(
						(ImageView) helper.getView(R.id.photo_list_item_image),
						item.getFirstImagePath());
				setText(helper, R.id.id_dir_item_count, item.getCount() + "张");
			}
		});
	}

	public interface OnImageDirSelected {
		void selectFolder(PhotoFolderEntity folder);
	}

	private OnImageDirSelected mImageDirSelected;

	public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
		this.mImageDirSelected = mImageDirSelected;
	}

	public void initEvents() {
		mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				mCurrentPosition = position;
				((BaseAdapter) parent.getAdapter()).notifyDataSetChanged();
				if (mImageDirSelected != null) {
					mImageDirSelected.selectFolder(mDatas.get(position));
				}
			}
		});
	}

	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}
}
