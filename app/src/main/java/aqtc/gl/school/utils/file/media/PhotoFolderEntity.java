package aqtc.gl.school.utils.file.media;

import android.net.Uri;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import aqtc.gl.school.utils.file.MimeTypeMap;


/**
 * @author gl
 * @date 2018/5/14
 * @desc 图片选择文件实体类
 */
public class PhotoFolderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static FilenameFilter imageFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".jpg") || filename.endsWith(".png")
                    || filename.endsWith(".jpeg");
        }
    };
    private static FilenameFilter allFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            return filename.endsWith(".jpg") || filename.endsWith(".png")
                    || filename.endsWith(".jpeg") || MimeTypeMap.isVideo(filename);
        }
    };
    private static FilenameFilter videoFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            return MimeTypeMap.isVideo(filename);
        }
    };

    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;
    private boolean allImage;

    public void setAllImage(boolean allImage) {
        this.allImage = allImage;
    }

    public boolean isAllImage() {
        return allImage;
    }

    public enum FileType {
        ALL, ALL_VIDEO, IMAGE;

        public boolean isAll() {
            return this == ALL;
        }

        public boolean isAllVideo() {
            return this == ALL_VIDEO;
        }

        public boolean isImage() {
            return this == IMAGE;
        }
    }

    private FileType mType = FileType.ALL;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf + 1);
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setCount(List<PhotoFolderEntity> imageFolders) {
        for (PhotoFolderEntity folderEntity : imageFolders) {
            count += folderEntity.getCount();
        }
    }

    public List<String> getListImage() {
        List<String> list = new ArrayList<String>();
        File mImgDir = new File(dir);
        if (mImgDir.exists() && mImgDir.isDirectory()) {
            File[] tempFile = mImgDir.listFiles(imageFilter);
            for (File file : tempFile) {
                list.add(Uri.decode(Uri.fromFile(file).toString()));
            }
        }
        return list;
    }

    public List<String> getListImage(List<PhotoFolderEntity> list) {
        List<String> retList = new ArrayList<String>();
        //retList.add("drawable://" + R.drawable.find_photo_camera_icon);
        // retList.add(Uri.parse(
        // "android.resource://com.sallerengine/"
        // + R.drawable.photo_camera_icon).toString());
        for (PhotoFolderEntity entity : list) {
            retList.addAll(entity.getListImage());
        }
        return retList;
    }

    public List<String> getAllList(List<PhotoFolderEntity> list) {
        List<String> retList = new ArrayList<String>();
        //retList.add("drawable://" + R.drawable.find_photo_camera_icon);
        // retList.add(Uri.parse(
        // "android.resource://com.sallerengine/"
        // + R.drawable.photo_camera_icon).toString());
        for (PhotoFolderEntity entity : list) {
            retList.addAll(entity.getVideoList());
            retList.addAll(entity.getListImage());
        }
        return retList;
    }

    public List<String> getVideoList() {
        List<String> list = new ArrayList<String>();
        File mImgDir = new File(dir);
        if (mImgDir.exists() && mImgDir.isDirectory()) {
            File[] tempFile = mImgDir.listFiles(videoFilter);
            for (File file : tempFile) {
                list.add(Uri.decode(Uri.fromFile(file).toString()));
            }
        }
        return list;
    }

    public FileType getType() {
        return mType;
    }

    public void setType(FileType type) {
        this.mType = type;
    }
}
