package aqtc.gl.school.utils.upload;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gl
 * @date 2018/5/24
 * @desc
 */
public class MultiPartUploadEntity {
    public LinkedHashMap<String, List<String>> textsMap = new LinkedHashMap<>();
    public LinkedHashMap<String, List<UploadFileEntity>> filesMap = new LinkedHashMap<>();

    public void addUploadFiles(String param, List<UploadFileEntity> files) {
        filesMap.put(param, files);
    }

    public void addUploadFile(String param, UploadFileEntity file) {
        List<UploadFileEntity> files = filesMap.get(param);
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
        filesMap.put(param, files);
    }

    public void addUploadTexts(String param, List<String> texts) {
        textsMap.put(param, texts);
    }

    public void addUploadText(String param, String text) {
        List<String> texts = textsMap.get(param);
        if (texts == null) {
            texts = new ArrayList<>();
        }
        texts.add(text);
        textsMap.put(param, texts);
    }

    public Map<String, List<UploadFileEntity>> getUploadFiles() {
        return filesMap;
    }

    public Map<String, List<String>> getUploadTexts() {
        return textsMap;
    }

    public boolean isEmpty() {
        return textsMap.isEmpty() && filesMap.isEmpty();
    }

    public void addBodyCallback(BodyCallback<UploadFileEntity> fileCallback) {
        Map<String, List<UploadFileEntity>> fileUpload = getUploadFiles();
        for (Map.Entry<String, List<UploadFileEntity>> entry : fileUpload.entrySet()) {
            String param = entry.getKey();
            List<UploadFileEntity> files = entry.getValue();
            fileCallback.callback(param, files);
        }
    }

    public void addTextCallback(BodyCallback<String> fileCallback) {
        Map<String, List<String>> textUpload = getUploadTexts();
        for (Map.Entry<String, List<String>> entry : textUpload.entrySet()) {
            String param = entry.getKey();
            List<String> texts = entry.getValue();
            fileCallback.callback(param, texts);
        }
    }

    public static interface BodyCallback<T> {
        public void callback(String param, List<T> array);
    }

}
