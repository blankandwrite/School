package aqtc.gl.school.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gl
 * @date 2018/5/14
 * @desc 文件类型map
 */
public class MimeTypeMap {
    private static Map<String, List<String>> typeMap = new HashMap<>();
    private static List<String> allList = new ArrayList();
    private static List<String> videoList = new ArrayList<>();
    private static List<String> imageList = new ArrayList<>();

    static {
        videoList.add("mp4");
        videoList.add("3gp");
        videoList.add("wmv");
        videoList.add("ts");
        videoList.add("rmvb");
        videoList.add("mov");
        videoList.add("m4v");
        videoList.add("avi");
        videoList.add("m3u8");
        videoList.add("3gpp");
        videoList.add("3gpp2");
        videoList.add("mkv");
        videoList.add("flv");
        videoList.add("divx");
        videoList.add("f4v");
        videoList.add("rm");
        videoList.add("asf");
        videoList.add("ram");
        videoList.add("mpg");
        videoList.add("v8");
        videoList.add("swf");
        videoList.add("m2v");
        videoList.add("asx");
        videoList.add("ra");
        videoList.add("ndivx");
        videoList.add("xvid");

        imageList.add("jpg");
        imageList.add("png");
        imageList.add("jpeg");
        imageList.add("bmp");

        allList.addAll(videoList);
        allList.addAll(imageList);
        typeMap.put("video", videoList);
        typeMap.put("image", imageList);
    }

    public static String getFileMimeType(String path) {
        File file = new File(path);
        String fileName = file.getName();
        int nameIndex = fileName.lastIndexOf(".");
        if (nameIndex != -1) {
            int index = allList.indexOf(fileName.substring(nameIndex + 1));
            if (index != -1) {
                if (isVideo(fileName)) {
                    return "video/" + allList.get(index);
                } else {
                    return "image/" + allList.get(index);
                }
            }
        }
        return null;
    }

    public static boolean isVideo(String fileName) {
        List<String> videos = typeMap.get("video");
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return videos.contains(fileName.substring(index + 1));
        }
        return false;
    }

    public static boolean isImage(String fileName) {
        List<String> videos = typeMap.get("image");
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return videos.contains(fileName.substring(index + 1));
        }
        return false;
    }

}
