package aqtc.gl.school.utils.file.media;

import android.graphics.Bitmap;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 文件信息
 */
public class SdcardFileInfo {
    private int id;
    private String filePath;
    private String fileName;
    private Bitmap thumbnail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

}
