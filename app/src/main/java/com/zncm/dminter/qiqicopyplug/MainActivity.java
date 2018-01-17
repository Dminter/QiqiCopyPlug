package com.zncm.dminter.qiqicopyplug;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.kenumir.materialsettings.MaterialSettings;
import com.kenumir.materialsettings.items.DividerItem;
import com.kenumir.materialsettings.items.TextItem;
import com.kenumir.materialsettings.storage.StorageInterface;

public class MainActivity extends MaterialSettings {
    private Activity ctx;

    @Override
    public StorageInterface initStorageInterface() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        addItem(new DividerItem(ctx));


        addItem(new TextItem(ctx, "").setTitle("ClipboardManager Easy To Share.").setSubtitle("current Version：" + Xutils.getAppVersion(ctx.getPackageName())).setOnclick(new TextItem.OnClickListener() {
            public void onClick(TextItem textItem) {
            }
        }));


        addItem(new DividerItem(ctx));

        addItem(new TextItem(ctx, "").setTitle("From").setSubtitle(SPHelper.getFrom()).setOnclick(new TextItem.OnClickListener() {
            public void onClick(final TextItem textItem) {
                AlertDialog.Builder dialogEdit = new AlertDialog.Builder(ctx);
                final EditText editTitle = new EditText(ctx);
                dialogEdit.setView(editTitle);
                if (Xutils.notEmptyOrNull(SPHelper.getFrom())) {
                    editTitle.setText(SPHelper.getFrom());
                }
                dialogEdit.setTitle("From")
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String from = editTitle.getText().toString().trim();
                                SPHelper.setFrom(ctx, from);
                            }
                        });
                dialogEdit.show();

            }
        }));


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


//        addItem(new CheckboxItem(this).setTitle("雪花").setOnCheckedChangeListener(new CheckboxItem.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChange(CheckboxItem checkboxItem, boolean b) {
//                SPHelper.setIsSnowFall(ctx, b);
//            }
//        }).setDefaultValue(SPHelper.isSnowFall(ctx)));
//
//
//        addItem(new DividerItem(ctx));
//        addItem(new TextItem(this, "").setTitle("抽屉网格大小-列数").setSubtitle(SPHelper.getGridColumns(ctx) + "").setOnclick(new TextItem.OnClickListener() {
//            public void onClick(final TextItem textItem) {
//                final String[] items = {"3", "4", "5", "6", "7", "8"};
//                int select = SPHelper.getGridColumns(ctx) - 3;
//                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
//                dialog.setTitle("抽屉网格大小-列数")
//                        .setSingleChoiceItems(items, select,
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        SPHelper.setGridColumns(ctx, which + 3);
//                                        dialog.dismiss();
//                                    }
//                                });
//                dialog.show();
//            }
//        }));


//        addItem(new DividerItem(ctx));
//        addItem(new CheckboxItem(this, "").setTitle("Root工作模式").setOnCheckedChangeListener(new CheckboxItem.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChange(CheckboxItem checkboxItem, boolean b) {
//                SPHelper.setIsRootMode(ctx, b);
//            }
//        }).setDefaultValue(SPHelper.isRootMode(ctx)));

//
//        addItem(new TextItem(ctx, "").setTitle("更新").setSubtitle("当前版本：" + Xutils.getAppVersion(ctx.getPackageName())).setOnclick(new TextItem.OnClickListener() {
//            public void onClick(TextItem textItem) {
//                Xutils.openUrl(Constant.update_url);
//            }
//        }));

    }
}
