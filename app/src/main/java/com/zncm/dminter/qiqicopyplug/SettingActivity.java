package com.zncm.dminter.qiqicopyplug;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.kenumir.materialsettings.MaterialSettings;
import com.kenumir.materialsettings.items.DividerItem;
import com.kenumir.materialsettings.items.TextItem;
import com.kenumir.materialsettings.storage.StorageInterface;

public class SettingActivity extends MaterialSettings {
    private Activity ctx;

    @Override
    public StorageInterface initStorageInterface() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;

        addItem(new TextItem(ctx, "").setTitle("ClipboardManager Easy To Share.").setSubtitle("current Version：" + Xutils.getAppVersion(ctx.getPackageName())).setOnclick(new TextItem.OnClickListener() {
            public void onClick(TextItem textItem) {
            }
        }));

        addItem(new DividerItem(ctx));
        addItem(new TextItem(ctx, "").setTitle("To").setSubtitle(SPHelper.getTo()).setOnclick(new TextItem.OnClickListener() {
            public void onClick(final TextItem textItem) {
                AlertDialog.Builder dialogEdit = new AlertDialog.Builder(ctx);
                final EditText editTitle = new EditText(ctx);
                dialogEdit.setView(editTitle);
                if (Xutils.notEmptyOrNull(SPHelper.getTo())) {
                    editTitle.setText(SPHelper.getTo());
                }
                dialogEdit.setTitle("To")
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String to = editTitle.getText().toString().trim();
                                SPHelper.setTo(ctx, to);
                                textItem.updateSubTitle(to);
                            }
                        });
                dialogEdit.show();

            }
        }));


        addItem(new DividerItem(ctx));
        addItem(new TextItem(ctx, "").setTitle("Support Me.").setSubtitle("A Donation Is A Gift.").setOnclick(new TextItem.OnClickListener() {
            public void onClick(TextItem textItem) {
                if (AlipayZeroSdk.hasInstalledAlipayClient(ctx)) {
                    AlipayZeroSdk.startAlipayClient(ctx, "aex02461t5uptlcygocfsbc");
                } else {
                    Xutils.tShort("Please Install AliPay First.");
                }
            }
        }));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("back").setIcon(getResources().getDrawable(R.mipmap.back)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item == null || item.getTitle() == null) {
            return false;
        }
        if (item.getTitle().equals("back")) {
          finish();
        }

        return true;
    }
}
