package aqtc.gl.school.main.find.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aqtc.gl.school.main.find.bean.CircleItem;
import aqtc.gl.school.main.find.bean.CommentItem;
import aqtc.gl.school.main.find.bean.FavortItem;
import aqtc.gl.school.main.find.bean.PhotoInfo;
import aqtc.gl.school.main.find.bean.User;

/**
 * @author gl
 * @date 2018/5/12
 * @desc
 */
public class DatasUtil {
	public static final String[] CONTENTS = { "",
			"安庆师范大学坐落在国家历史文化名城、国家园林城市、中国优秀旅游城市——安庆。这里是桐城派的故里、黄梅戏的故乡，有着“千年古城、文化之邦、百年省会、戏剧之乡”的美誉。学校傍依浩瀚长江，" +
					"毗邻宁安高铁、合安九高铁，地理位置优越，水、陆、空交通便利，是皖西南地区唯一的省属师范大学。",
			"学校办学历史悠久，是安徽近代高等教育的发源地。1897年，清代著名省学敬敷书院在此办学，揭开了百年育人的序幕。" +
					"1901年，敬敷书院与求是学堂合并成立安徽大学堂，后更名为安徽高等学堂。1928年省立安徽大学在此创办，1946年改为国立安徽大学。" +
					"菱湖校区现存的敬敷书院和国立安徽大学红楼，作为全国重点文物保护单位见证了学校百年发展历程。1980年，经国务院批准成立安庆师范学院。" +
					"2006年，学校获批硕士学位授予权。2007年获得教育部本科教学工作水平评估“优秀”等次。" +
					"2016年，经教育部批准更名为安庆师范大学。2017年，安庆师范大学进入安徽省一本招生行列。",

			"我勒个去" };

	public static final String[] HEADIMG = {
			"http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg",
			"http://bnxs.aqnu.edu.cn/__local/B/73/C4/240FC7118DAAC706BC59B9128BB_F7FE4A13_17B69.jpg",
			"http://bnxs.aqnu.edu.cn/__local/F/A6/7B/523A36E971010C1C555E98145AD_45A09D2E_16C3B.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0fecc3e83ef586034a85edf723d.jpg",
			"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg",
			"http://www.aqnu.edu.cn/dfiles/8857/scene/images/33.jpg",
			"http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg"};

	public static List<User> users = new ArrayList<User>();
	public static List<PhotoInfo> PHOTOS = new ArrayList<>();
	/**
	 * 动态id自增长
	 */
	private static int circleId = 0;
	/**
	 * 点赞id自增长
	 */
	private static int favortId = 0;
	/**
	 * 评论id自增长
	 */
	private static int commentId = 0;
	public static final User curUser = new User("0", "自己", HEADIMG[0]);
	static {
		User user1 = new User("1", "张三", HEADIMG[1]);
		User user2 = new User("2", "李四", HEADIMG[2]);
		User user3 = new User("3", "王五", HEADIMG[3]);
		User user4 = new User("4", "小明", HEADIMG[4]);
		User user5 = new User("5", "田七", HEADIMG[5]);
		User user6 = new User("6", "大明", HEADIMG[6]);
		User user7 = new User("7", "小明小明小明，哈哈！因为我是用来测试换行的", HEADIMG[7]);

		users.add(curUser);
		users.add(user1);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		users.add(user5);
		users.add(user6);
		users.add(user7);

		PhotoInfo p1 = new PhotoInfo();
		p1.url = "http://bnxs.aqnu.edu.cn/__local/9/1B/76/F53A0110055BD8236C81CC4695B_15413E88_12F84.jpg";
		p1.w = 640;
		p1.h = 792;

		PhotoInfo p2 = new PhotoInfo();
		p2.url = "http://bnxs.aqnu.edu.cn/dfiles/8857/bainianxiaoshi/right.jpg";
		p2.w = 640;
		p2.h = 792;

		PhotoInfo p3 = new PhotoInfo();
		p3.url = "http://www.aqnu.edu.cn/dfiles/8857/scene/images/14.jpg";
		p3.w = 950;
		p3.h = 597;

		PhotoInfo p4 = new PhotoInfo();
		p4.url = "http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg";
		p4.w = 533;
		p4.h = 800;

		PhotoInfo p5 = new PhotoInfo();
		p5.url = "http://b.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134e61e0f90211f95cad1c85e36.jpg";
		p5.w = 700;
		p5.h = 467;

		PhotoInfo p6 = new PhotoInfo();
		p6.url = "http://www.aqnu.edu.cn/dfiles/8857/scene/images/04.jpg";
		p6.w = 700;
		p6.h = 467;

		PhotoInfo p7 = new PhotoInfo();
		p7.url = "http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg";
		p7.w = 1024;
		p7.h = 640;

		PhotoInfo p8 = new PhotoInfo();
		p8.url = "http://www.aqnu.edu.cn/dfiles/8857/scene/images/21.jpg";
		p8.w = 1024;
		p8.h = 768;

		PhotoInfo p9 = new PhotoInfo();
		p9.url = "http://pic4.nipic.com/20091203/1295091_123813163959_2.jpg";
		p9.w = 1024;
		p9.h = 640;

		PhotoInfo p10 = new PhotoInfo();
		p10.url = "http://www.aqnu.edu.cn/dfiles/8857/scene/images/33.jpg";
		p10.w = 1024;
		p10.h = 768;

		PHOTOS.add(p1);
		PHOTOS.add(p2);
		PHOTOS.add(p3);
		PHOTOS.add(p4);
		PHOTOS.add(p5);
		PHOTOS.add(p6);
		PHOTOS.add(p7);
		PHOTOS.add(p8);
		PHOTOS.add(p9);
		PHOTOS.add(p10);
	}

	public static List<CircleItem> createCircleDatas() {
		List<CircleItem> circleDatas = new ArrayList<CircleItem>();
		for (int i = 0; i < 15; i++) {
			CircleItem item = new CircleItem();
			User user = getUser();
			item.setId(String.valueOf(circleId++));
			item.setUser(user);
			item.setContent(getContent());
			item.setCreateTime("12月24日");

			item.setFavorters(createFavortItemList());
			item.setComments(createCommentItemList());
			int type = getRandomNum(10) % 2;
			if (type == 0) {
				item.setType("1");// 链接
				item.setLinkImg("http://pics.sc.chinaz.com/Files/pic/icons128/2264/%E8%85%BE%E8%AE%AFQQ%E5%9B%BE%E6%A0%87%E4%B8%8B%E8%BD%BD1.png");
				item.setLinkTitle("百度一下，你就知道");
			} else if(type == 1){
				item.setType("2");// 图片
				item.setPhotos(createPhotos());
			}else {
				item.setType("3");// 视频
				String videoUrl = "http://yiwcicledemo.s.qupai.me/v/80c81c19-7c02-4dee-baca-c97d9bbd6607.mp4";
                String videoImgUrl = "http://yiwcicledemo.s.qupai.me/v/80c81c19-7c02-4dee-baca-c97d9bbd6607.jpg";
				item.setVideoUrl(videoUrl);
                item.setVideoImgUrl(videoImgUrl);
			}
			circleDatas.add(item);
		}

		return circleDatas;
	}

	public static User getUser() {
		return users.get(getRandomNum(users.size()));
	}

	public static String getContent() {
		return CONTENTS[getRandomNum(CONTENTS.length)];
	}

	public static int getRandomNum(int max) {
		Random random = new Random();
		int result = random.nextInt(max);
		return result;
	}

	public static List<PhotoInfo> createPhotos() {
		List<PhotoInfo> photos = new ArrayList<PhotoInfo>();
		int size = getRandomNum(PHOTOS.size());
		if (size > 0) {
			if (size > 9) {
				size = 9;
			}
			for (int i = 0; i < size; i++) {
				PhotoInfo photo = PHOTOS.get(getRandomNum(PHOTOS.size()));
				if (!photos.contains(photo)) {
					photos.add(photo);
				} else {
					i--;
				}
			}
		}
		return photos;
	}

	public static List<FavortItem> createFavortItemList() {
		int size = getRandomNum(users.size());
		List<FavortItem> items = new ArrayList<FavortItem>();
		List<String> history = new ArrayList<String>();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				FavortItem newItem = createFavortItem();
				String userid = newItem.getUser().getId();
				if (!history.contains(userid)) {
					items.add(newItem);
					history.add(userid);
				} else {
					i--;
				}
			}
		}
		return items;
	}

	public static FavortItem createFavortItem() {
		FavortItem item = new FavortItem();
		item.setId(String.valueOf(favortId++));
		item.setUser(getUser());
		return item;
	}
	
	public static FavortItem createCurUserFavortItem() {
		FavortItem item = new FavortItem();
		item.setId(String.valueOf(favortId++));
		item.setUser(curUser);
		return item;
	}

	public static List<CommentItem> createCommentItemList() {
		List<CommentItem> items = new ArrayList<CommentItem>();
		int size = getRandomNum(10);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				items.add(createComment());
			}
		}
		return items;
	}

	public static CommentItem createComment() {
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent("哈哈");
		User user = getUser();
		item.setUser(user);
		if (getRandomNum(10) % 2 == 0) {
			while (true) {
				User replyUser = getUser();
				if (!user.getId().equals(replyUser.getId())) {
					item.setToReplyUser(replyUser);
					break;
				}
			}
		}
		return item;
	}
	
	/**
	 * 创建发布评论
	 * @return
	 */
	public static CommentItem createPublicComment(String content){
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		return item;
	}
	
	/**
	 * 创建回复评论
	 * @return
	 */
	public static CommentItem createReplyComment(User replyUser, String content){
		CommentItem item = new CommentItem();
		item.setId(String.valueOf(commentId++));
		item.setContent(content);
		item.setUser(curUser);
		item.setToReplyUser(replyUser);
		return item;
	}
	
	
	public static CircleItem createVideoItem(String videoUrl, String imgUrl){
		CircleItem item = new CircleItem();
		item.setId(String.valueOf(circleId++));
		item.setUser(curUser);
		//item.setContent(getContent());
		item.setCreateTime("12月24日");

		//item.setFavorters(createFavortItemList());
		//item.setComments(createCommentItemList());
        item.setType("3");// 图片
        item.setVideoUrl(videoUrl);
        item.setVideoImgUrl(imgUrl);
		return item;
	}
}
