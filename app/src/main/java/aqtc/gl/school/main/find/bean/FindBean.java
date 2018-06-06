package aqtc.gl.school.main.find.bean;

import java.util.List;

/**
 * @author gl
 * @date 2018/6/6
 * @desc
 */
public class FindBean {

    /**
     * err : 0
     * name : 用户名
     * headImage : 头像
     * bgImage : 头部背景图
     * list : [{"id":"ff80808163b8fb390163d2d82e2e1c77","userId":"ff8080815ddf888d015ddf88ea040000","content":"好","time":"2018-06-06 10:06:13","name":"曹小亮","headImage":"","pathList":[{"original":"/file/sztx/180606/e917cc51.jpg","thumbnail":"/file/sztx/180606/e917cc51_thumb.jpg"},{"original":"/file/sztx/180606/ccc21b96.jpg","thumbnail":"/file/sztx/180606/ccc21b96_thumb.jpg"}],"type":0,"likeList":[{"id":"ff80808163b8fb390163d2d8b2ff1c7b","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":""},{"id":"ff80808163b8fb390163d2d919611c80","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":""}],"commentList":[{"id":"ff80808163b8fb390163d2d8ca641c7d","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":"","content":"好像","time":"2018-06-06 10:06:53","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d93e101c81","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"你好","time":"2018-06-06 10:07:22","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d953d91c84","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"哈哈哈","time":"2018-06-06 10:07:28","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"1"}]},{"id":"ff80808163b8fb390163d2bbaa6a1bd4","userId":"ff8080815ddf888d015ddf9c7c12000a","content":"哈哈哈哈","time":"2018-06-06 09:35:04","name":"用户名","headImage":"","pathList":[{"original":"/file/sztx/180606/b8f3c6fe.mp4","thumbnail":"/file/sztx/180606/b8f3c6fe.jpg"}],"type":1,"likeList":[],"commentList":[]},{"id":"ff80808163b8fb390163d2a7128b1b71","userId":"ff8080815ddf888d015ddf9c7c12000a","content":"和合肥","time":"2018-06-06 09:12:34","name":"用户名","headImage":"","pathList":[{"original":"/file/sztx/180606/d0034a02.jpg","thumbnail":"/file/sztx/180606/d0034a02_thumb.jpg"},{"original":"/file/sztx/180606/7cf0aeb4.jpg","thumbnail":"/file/sztx/180606/7cf0aeb4_thumb.jpg"},{"original":"/file/sztx/180606/13404f0b.jpg","thumbnail":"/file/sztx/180606/13404f0b_thumb.jpg"}],"type":0,"likeList":[{"id":"ff80808163b8fb390163d2abb1711b8a","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":""}],"commentList":[{"id":"ff80808163b8fb390163d2abf0b01b8d","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":"","content":"呵呵呵呵","time":"2018-06-06 09:17:53","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"0"},{"id":"ff80808163b8fb390163d2b99a931bd2","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"你好","time":"2018-06-06 09:32:49","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"1"}]}]
     */

    public int err;
    public String name;// 用户名
    public String headImage; //头像
    public String bgImage; //头部背景图
    public List<ListBean> list;  //发布内容集合

    public static class ListBean {
        /**
         * id : ff80808163b8fb390163d2d82e2e1c77
         * userId : ff8080815ddf888d015ddf88ea040000
         * content : 好
         * time : 2018-06-06 10:06:13
         * name : 小明
         * headImage :
         * pathList : [{"original":"/file/sztx/180606/e917cc51.jpg","thumbnail":"/file/sztx/180606/e917cc51_thumb.jpg"},{"original":"/file/sztx/180606/ccc21b96.jpg","thumbnail":"/file/sztx/180606/ccc21b96_thumb.jpg"}]
         * type : 0
         * likeList : [{"id":"ff80808163b8fb390163d2d8b2ff1c7b","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":""},{"id":"ff80808163b8fb390163d2d919611c80","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":""}]
         * commentList : [{"id":"ff80808163b8fb390163d2d8ca641c7d","userId":"ff8080815ddf888d015ddf9c7c12000a","name":"用户名","headImage":"","content":"好像","time":"2018-06-06 10:06:53","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d93e101c81","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"你好","time":"2018-06-06 10:07:22","targetId":"ff8080815ddf888d015ddf88ea040000","targetName":"曹小亮","type":"0"},{"id":"ff80808163b8fb390163d2d953d91c84","userId":"ff8080815ddf888d015ddf88ea040000","name":"曹小亮","headImage":"","content":"哈哈哈","time":"2018-06-06 10:07:28","targetId":"ff8080815ddf888d015ddf9c7c12000a","targetName":"用户名","type":"1"}]
         */

        public String id;  //列表条目id
        public String userId;//发布人id
        public String content;//发布文字内容
        public String time;// 发布时间
        public String name;// 发布人姓名
        public String headImage;// 发布人头像
        public int type; //      0图片 1视频 2链接
        public List<PathListBean> pathList; // 图片或视频集合
        public List<LikeListBean> likeList; //点赞集合
        public List<CommentListBean> commentList; //评论集合

        public static class PathListBean {
            /**
             * original : /file/sztx/180606/e917cc51.jpg
             * thumbnail : /file/sztx/180606/e917cc51_thumb.jpg
             */

            public String original; // 图片或视屏地址
            public String thumbnail; //经过处理后的地址（后台把图片压缩处理展示缩略图时使用（480*640即可,要是视屏可以截去视屏的某一帧处理成图片返回），要是麻烦可暂时不处理）

        }

        public static class LikeListBean {
            /**
             * id : ff80808163b8fb390163d2d8b2ff1c7b
             * userId : ff8080815ddf888d015ddf9c7c12000a
             * name : 用户名
             * headImage :
             */

            public String id; //点赞条目id
            public String userId;//点赞人id
            public String name; //点赞人姓名
            public String headImage; //点赞人头像
        }

        public static class CommentListBean {
            /**
             * id : ff80808163b8fb390163d2d8ca641c7d
             * userId : ff8080815ddf888d015ddf9c7c12000a
             * name : 用户名
             * headImage :
             * content : 好像
             * time : 2018-06-06 10:06:53
             * targetId : ff8080815ddf888d015ddf88ea040000
             * targetName : 曹小亮
             * int : 0
             */

            public String id; //评论条目id
            public String userId; //评论人id
            public String name; //评论人姓名
            public String headImage;//评论人头像
            public String content; //评论内容
            public String time; //评论时间
            public String targetId; //评论目标人的id
            public String targetName; //评论目标人的姓名
            public int type; //0为对动态进行评论   1为对人进行评论
        }
    }
}
