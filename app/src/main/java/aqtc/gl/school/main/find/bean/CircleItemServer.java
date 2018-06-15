package aqtc.gl.school.main.find.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.main.login.LoginInfoCache;
import aqtc.gl.school.utils.StringUtils;
import aqtc.gl.school.utils.apputil.Apputil;

/**
 * @author gl
 * @date 2018/6/6
 * @desc
 */
public class CircleItemServer {

    /**
     * err : 0
     * name : 用户名
     * headImage : 头像
     * bgImage : 头部背景图
     * list : [{"id":"ff80808163b8fb390163d2d82e2e1c77","userId":"ff8080815ddf888d015ddf88ea040000","content":"好","time":"2018-06-06 10:06:13","name":"曹小亮","headImage":"","pathList":[{"original":"/file/sztx/180606/e917cc51.jpg","thumbnail":"/file/sztx/180606/e917cc51_thumb.jpg"},{"original":"/file/sztx/180606/ccc21b96.jpg","thumbnail":"/file/sztx/180606/ccc21b96_thumb.jpg"}],"type":0,"likeList":[{"id":"ff80808163b8fb390163d2d8b2ff1c7b","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":""},{"id":"ff80808163b8fb390163d2d919611c80","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":""}],"commentList":[{"id":"ff80808163b8fb390163d2d8ca641c7d","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":"","content":"好像","time":"2018-06-06 10:06:53","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d93e101c81","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"你好","time":"2018-06-06 10:07:22","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d953d91c84","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"哈哈哈","time":"2018-06-06 10:07:28","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"1"}]},{"id":"ff80808163b8fb390163d2bbaa6a1bd4","userId":"ff8080815ddf888d015ddf9c7c12000a","content":"哈哈哈哈","time":"2018-06-06 09:35:04","name":"用户名","headImage":"","pathList":[{"original":"/file/sztx/180606/b8f3c6fe.mp4","thumbnail":"/file/sztx/180606/b8f3c6fe.jpg"}],"type":1,"likeList":[],"commentList":[]},{"id":"ff80808163b8fb390163d2a7128b1b71","userId":"ff8080815ddf888d015ddf9c7c12000a","content":"和合肥","time":"2018-06-06 09:12:34","name":"用户名","headImage":"","pathList":[{"original":"/file/sztx/180606/d0034a02.jpg","thumbnail":"/file/sztx/180606/d0034a02_thumb.jpg"},{"original":"/file/sztx/180606/7cf0aeb4.jpg","thumbnail":"/file/sztx/180606/7cf0aeb4_thumb.jpg"},{"original":"/file/sztx/180606/13404f0b.jpg","thumbnail":"/file/sztx/180606/13404f0b_thumb.jpg"}],"type":0,"likeList":[{"id":"ff80808163b8fb390163d2abb1711b8a","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":""}],"commentList":[{"id":"ff80808163b8fb390163d2abf0b01b8d","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":"","content":"呵呵呵呵","time":"2018-06-06 09:17:53","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"0"},{"id":"ff80808163b8fb390163d2b99a931bd2","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"你好","time":"2018-06-06 09:32:49","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"1"}]}]
     */
    public String error;
    public String bgImage;
    public String headImage;
    public String name;
    public List<ListBean> list;

    public String getBgImage() {
        return Apputil.checkPath(bgImage);
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getHeadImage() {
        return Apputil.checkPath(headImage);
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Comparable, Serializable {
        /**
         * commentList : [{"content":"食尸鬼是当时德国","headImage":"/file/sztx/170323/50b12ea6.jpg","id":"402849d25afeba00015aff6353970008","name":"童无敌","targetId":"402881005ad49eb0015ad4a154480000","targetName":"402881005ad49eb0015ad4a154480000","time":"2017-03-24 16:16:23","userId":"402881005acf8959015ad0cbdfba0138"}]
         * content :  水果吧杀死
         * headImage : /file/sztx/170324/44f72d4e.png
         * id : 402881005af442be015af55716960045
         * likeList : [{"headImage":"/file/sztx/170327/8e30a2c0_thumb.png","name":"骆鹏鹏","userId":"402881005af381ac015af418e91c000a"}]
         * name : 李东
         * pathList : [{"original":"测试内容6jii","thumbnail":"测试内容f132"}]
         * time : 2017-03-22 17:26:49
         * type : 78408
         * userId : 402881005ad49eb0015ad4a154480000
         */

        public String content = "";
        public String headImage;
        public String id = "";
        public String name;
        public String time = "";
        public int type;
        public String userId="userId";
        public List<CommentListBean> commentList;
        public List<LikeListBean> likeList;
        public List<PathListBean> pathList;
        //***************************手动添加其他为工具生成***************************//
        public boolean isExpand;
        public String linkUrl;
        public String linkImg;
        public String linkTitle;


        public void setExpand(boolean isExpand) {
            this.isExpand = isExpand;
        }

        public boolean isExpand() {
            return this.isExpand;
        }

        public boolean isEmpty() {
            return TextUtils.isEmpty(id) || TextUtils.isEmpty(userId) || TextUtils.isEmpty(time);
        }

        public boolean isLocal() {
            return TextUtils.isEmpty(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListBean listBean = (ListBean) o;

            if (type != listBean.type) return false;
            if (!content.equals(listBean.content)) return false;
            if (!time.equals(listBean.time)) return false;
            return userId.equals(listBean.userId);

        }

        public User getUser() {
            return new User(userId, name, headImage);
        }

        public void setUser(User user) {
            userId = user.getId();
            name = user.getName();
            headImage = user.getHeadUrl();
        }

        public boolean hasFavort() {
            if (likeList != null && likeList.size() > 0) {
                return true;
            }
            return false;
        }

        public boolean hasComment() {
            if (commentList != null && commentList.size() > 0) {
                return true;
            }
            return false;
        }

        public String getCurUserFavortId(String curUserId) {
            String favortid = "";
            if (!TextUtils.isEmpty(curUserId) && hasFavort()) {
                if (likeList != null) {
                    for (LikeListBean item : likeList) {
                        if (curUserId.equals(item.getUser().getId())) {
                            favortid = item.getId();
                            return favortid;
                        }
                    }
                }
            }
            return favortid;
        }

        public boolean isFavortSelf() {
            if (likeList == null || likeList.isEmpty())
                return false;
            for (LikeListBean favortItem : likeList) {
                if (LoginInfoCache.getMyUser().id.equals(favortItem.getUser().getId())) {
                    return true;
                }
            }
            return false;
        }

        public String getLinkUrl() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0).original : null;
        }


        public String getLinkImg() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0).getThumbnail() : null;
        }

        public String getLinkTitle() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0).getTitle() : null;
        }


        public String getVideoImgUrl() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0).getThumbnail() : null;
        }

        public PathListBean getVideoBean() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0) : null;
        }

        public String getVideoUrl() {
            List<PathListBean> paths = getPathList();
            return paths.size() > 0 ? paths.get(0).getOriginal() : null;
        }


        //******************************************************//

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHeadImage() {
            return Apputil.checkPath(headImage);
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<CommentListBean> getCommentList() {
            if (commentList == null) {
                commentList = new ArrayList<>();
            }
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public List<LikeListBean> getLikeList() {
            if (likeList == null) {
                likeList = new ArrayList<>();
            }
            return likeList;
        }

        public void setLikeList(List<LikeListBean> likeList) {
            this.likeList = likeList;
        }

        public List<PathListBean> getPathList() {
            if (pathList == null) {
                pathList = new ArrayList<>();
            }
            return pathList;
        }

        public void setPathList(List<PathListBean> pathList) {
            this.pathList = pathList;
        }

        @Override
        public int compareTo(Object obj) {
            ListBean another = (ListBean) obj;
            int compare = StringUtils.compareString(another.getTime(), getTime());
            return compare;
        }


        public static class CommentListBean implements Serializable {
            /**
             * content : 食尸鬼是当时德国
             * headImage : /file/sztx/170323/50b12ea6.jpg
             * id : 402849d25afeba00015aff6353970008
             * name : 童无敌
             * targetId : 402881005ad49eb0015ad4a154480000
             * targetName : 402881005ad49eb0015ad4a154480000
             * time : 2017-03-24 16:16:23
             * userId : 402881005acf8959015ad0cbdfba0138
             */
            public String id;
            public String content;
            public String headImage;
            public String name;
            public String userId;
            public String targetId;
            public String targetName;
            public String time;
            public int type;

            //******************手动添加*****************//
            public User getUser() {
                return new User(userId, name, headImage);
            }

            public User getToReplyUser() {
                return new User(targetId, targetName, "");
            }

            //******************手动添加*****************//
            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHeadImage() {
                return Apputil.checkPath(headImage);
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

            public String getTargetName() {
                return targetName;
            }

            public void setTargetName(String targetName) {
                this.targetName = targetName;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class LikeListBean implements Serializable {
            /**
             * headImage : /file/sztx/170327/8e30a2c0_thumb.png
             * name : 骆鹏鹏
             * userId : 402881005af381ac015af418e91c000a
             */

            public String id;
            public String headImage;
            public String name;
            public String userId;
            //*****************手动添加*******************//

            public User getUser() {
                return new User(userId, name, headImage);
            }

            //*****************手动添加*******************//

            public String getHeadImage() {
                return Apputil.checkPath(headImage);
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        public static class PathListBean implements Serializable {
            /**
             * original : 测试内容6jii
             * thumbnail : 测试内容f132
             */

            //如果是链接类型，此为链接地址
            public String original;
            //如果是链接类型，此为链接缩略图
            public String thumbnail;
            //如果是链接类型，才有此属性
            public String title;
            //手动添加
            public int w;
            public int h;

            public boolean isHasSize() {
                return w > 0 && h > 0;
            }

            public PathListBean(String original, String thumbnail) {
                this.original = original;
                this.thumbnail = thumbnail;
            }

            public String getTitle() {
                return title;
            }

            public String getOriginal() {
                return Apputil.checkPath(original);
            }

            public String getThumbnail() {
                return Apputil.checkPath(thumbnail);
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public static List<String> convertListString(List<PathListBean> photoInfos) {
                List<String> urls = new ArrayList<>();
                for (PathListBean info : photoInfos) {
                    urls.add(info.getOriginal());
                }
                return urls;
            }
        }
    }

}
