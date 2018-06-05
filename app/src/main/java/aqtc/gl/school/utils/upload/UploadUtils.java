package aqtc.gl.school.utils.upload;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import aqtc.gl.school.net.okhttp.OkHttpUtil;
import aqtc.gl.school.net.okhttp.callback.OnResponse;
import aqtc.gl.school.utils.LogX;
import aqtc.gl.school.utils.bitmap.BitmapCompresser;
import aqtc.gl.school.utils.file.MimeTypeMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author gl
 * @date 2018/5/24
 * @desc 图片上传
 */
public class UploadUtils {
    private static OkHttpClient mOkHttpClient;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private BitmapCompresser compresser;

    public UploadUtils(Context context) {
        mOkHttpClient = OkHttpUtil.getInstance(context).getOkHttpClient();
        compresser = BitmapCompresser.getDefault(context);
    }

    /**
     * 上传多张图片及参数
     * http://blog.csdn.net/dipolar/article/details/50435463
     * https://segmentfault.com/q/1010000005148150
     * http://blog.csdn.net/king866/article/details/52526901
     *
     * @param reqUrl URL地址
     * @param texts 文本map
     * @param filesMap 文件map
     */
    public void sendMultipart(String reqUrl, Map<String, String> texts, Map<String, List<File>> filesMap,
                              final OnResponse<String> onResponse) {
        LogX.e("sendMultipart", "sendMultipart");
        MultiPartUploadEntity uploadEntity = new MultiPartUploadEntity();

        if (filesMap != null) {
            for (Map.Entry<String, List<File>> entry : filesMap.entrySet()) {
                List<File> files = entry.getValue();
                for (File file : files) {
                    UploadFileEntity entity = new UploadFileEntity(file.getAbsolutePath());
                    System.out.println("#file entity:" + entity.toString());
                    uploadEntity.addUploadFile(entry.getKey(), entity);
                }
            }

        }

        final MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (texts != null) {
            for (String key : texts.keySet()) {
                builder.addFormDataPart(key, texts.get(key));
            }
        }
        //先进行压缩
        uploadEntity.addBodyCallback(new MultiPartUploadEntity.BodyCallback<UploadFileEntity>() {
            @Override
            public void callback(String param, List<UploadFileEntity> array) {
                for (UploadFileEntity fileEntity : array) {
                    if (MimeTypeMap.isImage(fileEntity.url)) {
                        if (compresser.isNeedCompress(fileEntity.url)) {
                            String compressPath = compresser.compress(fileEntity.url,
                                    fileEntity.getFileName());
                            fileEntity.fill(compressPath);
                            System.out.println("#fileEntity.url:" + fileEntity.url);
                        }
                    }
                }
            }
        });
        uploadEntity.addBodyCallback(new MultiPartUploadEntity.BodyCallback<UploadFileEntity>() {
            @Override
            public void callback(String param, List<UploadFileEntity> array) {
                for (UploadFileEntity fileEntity : array) {
                    builder.addPart(Headers.of("Content-Disposition",
                            "form-data; name=\"" + param + "\"; filename=\"" + fileEntity.getFileName() + "\""),
                            RequestBody.create(MediaType.parse(fileEntity.getContentType()), fileEntity.getFile()));
                }
            }
        });
        //构建请求体
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(reqUrl)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onResponse.responseFail("请求超时，请重试");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseStr = response.body().string();
                    final JSONObject jsonObject = new JSONObject(responseStr);
                    System.out.println("#response:" + jsonObject.toString());
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    onResponse.responseOk(responseStr);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        final String error = jsonObject.getString("msg");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    onResponse.responseFail(error);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onResponse.responseFail("访问失败，请重试");
                        }
                    });
                }
            }
        });

    }
}
