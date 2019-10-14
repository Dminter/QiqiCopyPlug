package com.zncm.dminter.qiqicopyplug;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Date;
import java.util.UUID;


public class MyApp extends Application {

    public static MyApp instance;
    public Context ctx;
    ClipboardManager cb;
    private String myLastClipboard = "";
    public static    String host = "http://114.55.170.228:8090";
//public static     String host = "http://172.16.10.150:8090";
  String url;
    public static   String uuid;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ctx = this;
        OkGo.getInstance().init(this);
        uuid = SPHelper.getKey("UUID");
        if (!Xutils.notEmptyOrNull(uuid)) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
            SPHelper.setKey("UUID", "021f34430717471795505cf29c55cd5e");
        }
        cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String content = cb.getText().toString();
//                String pkgName = Xutils.getLastApp(ctx);
                if (Xutils.notEmptyOrNull(content) && !content.equals(myLastClipboard)) {
                    if (!Xutils.notEmptyOrNull(SPHelper.getFrom()) || Xutils.notEmptyOrNull(SPHelper.getFrom())) {
                        url = host + "/copy/addCopy?uuid=" + uuid + "&text=" + content;
                        Xutils.debug("add-log：" + new Date().toLocaleString() + " " + url);
                        OkGo.<String>post(url).execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Xutils.debug("response::" + response);
                                Xutils.tShort("Cliped.");
                            }
                        });
                        Xutils.sendTo(ctx, content);
                    }
                    myLastClipboard = content;
                }
            }
        });
    }

}
