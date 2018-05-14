package aqtc.gl.school.utils.file.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;



import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 图片视频查询工具类
 */
public class SdcardFileQueryUtil {

    public static ArrayList<SdcardFileInfo> queryAllVideo(final Context context) {
        if (context == null) { //判断传入的参数的有效性
            return null;
        }
        ArrayList<SdcardFileInfo> videos = new ArrayList<SdcardFileInfo>();
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media.MIME_TYPE
                    + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE
                    + "=?", new String[]{"video/mp4"}, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SdcardFileInfo video = new SdcardFileInfo();
                    video.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID))); //获取唯一id
                    video.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))); //文件路径
                    video.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))); //文件名
                  /*
                   * 可以访问android.provider.MediaStore.Video.Thumbnails查询图片缩略图
                   * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                   */
                   /* Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(resolver, video.getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
                    video.setThumbnail(thumbnail);*/
                    videos.add(video);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videos;
    }

    public static List<String> queryAllImages(Context context) {
        ContentResolver mContentResolver = context.getContentResolver();
        // 查询图片
        Cursor cursor = mContentResolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null, MediaStore.Images.Media.MIME_TYPE
                                + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE
                                + "=?", new String[]{"image/jpeg", "image/jpg"},
                        MediaStore.Images.Media.DATE_MODIFIED);

        List<String> imagePaths = new ArrayList<String>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 获取图片的路径
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                path = path.replace("file://", "");
                imagePaths.add(path);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return imagePaths;
    }

    public static List<PhotoFolderEntity> makeAll(Context context, List<String> images, List<SdcardFileInfo> videos) {
        List<PhotoFolderEntity> imageFolders = new ArrayList<PhotoFolderEntity>();
        imageFolders.addAll(makeVideos(context, videos));
        imageFolders.addAll(makeImages(context, images));
        return imageFolders;
    }

    public static List<PhotoFolderEntity> makeImages(Context context, List<String> images) {
        if (images == null) {
            images = queryAllImages(context);
        }
        List<PhotoFolderEntity> imageFolders = new ArrayList<PhotoFolderEntity>();
        PhotoFolderEntity folderEntity = null;
        HashSet<String> dirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描
        for (String path : images) {
            folderEntity = getPhotoFolderEntity(PhotoFolderEntity.FileType.IMAGE, dirPaths, path);
            if (folderEntity == null)
                continue;
            imageFolders.add(folderEntity);
        }
        return imageFolders;
    }

    public static List<PhotoFolderEntity> makeVideos(Context context, List<SdcardFileInfo> videos) {
        if (videos == null) {
            videos = SdcardFileQueryUtil.queryAllVideo(context);
        }
        List<PhotoFolderEntity> imageFolders = new ArrayList<PhotoFolderEntity>();
        PhotoFolderEntity folderEntity = null;
        HashSet<String> dirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描
        for (SdcardFileInfo fileInfo : videos) {
            folderEntity = getPhotoFolderEntity(PhotoFolderEntity.FileType.ALL_VIDEO, dirPaths, fileInfo.getFilePath());
            if (folderEntity == null)
                continue;
            imageFolders.add(folderEntity);
        }
        return imageFolders;
    }

    public static PhotoFolderEntity getPhotoFolderEntity(PhotoFolderEntity.FileType type, HashSet<String> dirPaths, String path) {
        PhotoFolderEntity imageFolder = null;
        File file = new File(path);
        if (file.getParentFile() == null) {
            return null;
        }
        // 获取该图片的父路径名
        String dirPath = file.getParentFile().getAbsolutePath();
        // 利用一个HashSet防止多次扫描同一个文件夹
        if (!dirPaths.contains(dirPath)) {
            dirPaths.add(dirPath);
            imageFolder = new PhotoFolderEntity();// 初始化imageFolder
            imageFolder.setDir(dirPath);
            List<String> files = null;
            if (type.isImage()) {
                files = imageFolder.getListImage();
            } else if (type.isAllVideo()) {
                files = imageFolder.getVideoList();
            }
            imageFolder.setType(type);
            if (files != null && files.size() > 0) {
                // 拿到第一张图片的路径
                imageFolder.setFirstImagePath(files.get(0));
                imageFolder.setCount(files.size());
            }
        }
        return imageFolder;
    }

}
