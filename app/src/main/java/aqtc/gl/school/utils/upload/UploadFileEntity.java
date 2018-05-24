package aqtc.gl.school.utils.upload;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import aqtc.gl.school.utils.file.MimeTypeMap;

/**
 * @author gl
 * @date 2018/5/24
 * @desc
 */
public class UploadFileEntity {
    public String mimeType;
    public String url;
    public File file;
    public String parameterName;

    public UploadFileEntity(String url, String mimeType) {
        this.mimeType = mimeType;
        this.url = url;
        this.file = new File(url);
    }

    public void fill(String url){
        this.url = url;
        this.file = new File(url);
    }

    public File getFile(){
        return file;
    }

    public UploadFileEntity(String url) {
        this(url, MimeTypeMap.getFileMimeType(url));
    }

    public String getContentType() {
        return mimeType;
    }

    public String getFileName() {
        return file.getName();
    }

    public InputStream getInStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public long getFileSize() {
        return file.getTotalSpace();
    }

    public String getParameterName() {
        return parameterName;
    }

    @Override
    public String toString() {
        return "UploadFileEntity{" +
                "file=" + file +
                ", mimeType='" + mimeType + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
