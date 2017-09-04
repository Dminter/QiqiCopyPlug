package com.zncm.dminter.qiqicopyplug;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;


public class MyApp extends Application {

    public static MyApp instance;
    public Context ctx;
    ClipboardManager cb;
    private String myLastClipboard = "";

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ctx = this;
        cb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cb.setPrimaryClip(ClipData.newPlainText("", ""));
        cb.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String content = cb.getText().toString();
                if (Xutils.notEmptyOrNull(content) && !content.equals(myLastClipboard)) {
                    Xutils.sendTo(ctx, content);
                    myLastClipboard = content;
                }
            }
        });
    }

}
