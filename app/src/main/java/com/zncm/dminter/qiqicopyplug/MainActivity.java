package com.zncm.dminter.qiqicopyplug;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zncm.dminter.qiqicopyplug.adapter.CopyAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Activity ctx;
    private Toolbar toolbar;
    CopyAdapter copyAdapter;
    ArrayList<Copy> list = new ArrayList<>();
    int page = 1;
    public TwinklingRefreshLayout refreshLayout;
    //    CustomPopWindow mCustomPopWindow;
    View mark;
    boolean isLike = false;
    AlertDialog dialogTag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_home);
        mark = findViewById(R.id.mark);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        isLike = SPHelper.getKey("like").equals("like");
        copyAdapter = new CopyAdapter(R.layout.item, null);
        recyclerView.setAdapter(copyAdapter);
        copyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("onItemClick");
                Copy copy = (Copy) adapter.getItem(position);
                initEditView(copy);

            }
        });

        copyAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                System.out.println("onItemLongClick");
                final Copy copy = (Copy) adapter.getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setItems(new String[]{"收藏"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        copy.setNumber(100);
                        readCopy(copy);
                    }
                });
                dialog.show();
                return true;
            }
        });

//        View view = LayoutInflater.from(ctx).inflate(R.layout.null_view, null);
//        copyAdapter.setEmptyView(view);
        copyAdapter.setUpFetchEnable(true);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(copyAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        copyAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, final int pos) {

                if (!Xutils.listNotNull(list) || pos >= list.size()) {
                    return;
                }
                Copy copy = list.get(pos);
                readCopy(copy);

            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

            }
        });
        copyAdapter.enableSwipeItem();
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

                loadMore(true);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                ++page;
                loadMore(false);
            }
        });
        loadMore(true);


    }


    private void loadMore(boolean flag) {
        if (flag) {
            page = 1;
            list = new ArrayList<>();
            refreshLayout.finishRefreshing();
            refreshLayout.setEnableLoadmore(true);
        }

        String url = MyApp.host + "/copy/findCopysByPage?uuid=" + MyApp.uuid + "&page=" + page + "&pageSize=15";
        if (isLike) {
            url = url + "&number=100";
        }
        Xutils.debug("log：" + new Date().toLocaleString() + " " + url);
        OkGo.<String>post(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Ret ret = JSON.parseObject(response.body(), Ret.class);
                List<Copy> copyList = JSON.parseArray(ret.getResult(), Copy.class);
                if (Xutils.listNotNull(copyList)) {
                    list.addAll(copyList);
                    copyAdapter.setNewData(list);
                }
                if (!Xutils.listNotNull(copyList) || copyList.size() < 15) {
                    refreshLayout.setEnableLoadmore(false);
                }
                Xutils.debug("response::" + response);
                refreshLayout.finishLoadmore();

            }
        });
    }


    private void readCopy(Copy copy) {
        String url = MyApp.host + "/copy/readCopy?id=" + copy.getId();
        if (copy.getNumber() != null) {
            url = url + "&number=" + copy.getNumber();
        }
        Xutils.debug("log：" + new Date().toLocaleString() + " " + url);
        OkGo.<String>post(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                loadMore(true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("setting").setIcon(getResources().getDrawable(R.mipmap.setting)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("like").setIcon(getResources().getDrawable(R.mipmap.like)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("add").setIcon(getResources().getDrawable(R.mipmap.add)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item == null || item.getTitle() == null) {
            return false;
        }
        if (item.getTitle().equals("setting")) {
            Intent intent = new Intent(ctx, SettingActivity.class);
            startActivity(intent);
        }

        if (item.getTitle().equals("add")) {
            initEditView(null);
        }
        if (item.getTitle().equals("like")) {
//            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
//            dialog.setItems(new String[]{"全部", "收藏"}, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    isLike = which == 1;
//                    loadMore(true);
//                }
//            });
//            dialog.show();

            if (SPHelper.getKey("like").equals("like")) {
                SPHelper.setKey("like", "notlike");
            } else {
                SPHelper.setKey("like", "like");
            }
            isLike = SPHelper.getKey("like").equals("like");
            loadMore(true);
        }

        return true;
    }

    private void initEditView(Copy copy) {
        dialogTag = new AlertDialog.Builder(ctx).create();
        View contentView = LayoutInflater.from(ctx).inflate(R.layout.bottom_view, null);
        final EditText contentInput = contentView.findViewById(R.id.content);
        TextView add = contentView.findViewById(R.id.add);
        TextView addNext = contentView.findViewById(R.id.addNext);
        if (copy != null && Xutils.notEmptyOrNull(copy.getText())) {
            contentInput.setText(copy.getText());
        }

//        contentView.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(ctx)
//                .setView(contentView)
//                .size(getWindowManager().getDefaultDisplay().getWidth(), Xutils.sp2px(120))
//                .enableBackgroundDark(true)
//                .setBgDarkAlpha(0.7f)
//                .create()
//                .showAsDropDown(mark, 0, 0);
        contentInput.setFocusable(true);//设置输入框可聚集
        contentInput.setFocusableInTouchMode(true);//设置触摸聚焦
        contentInput.requestFocus();//请求焦点
        contentInput.findFocus();//获取焦点
//        contentInput.setTextIsSelectable(true);
//        KeyboardUtils.toggleSoftInput(contentView);
        addNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentInput.getText().toString().trim();
                addCopy(content);
                contentInput.setText("");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentInput.getText().toString().trim();
                addCopy(content);
                dialogTag.dismiss();
            }
        });


        dialogTag.setView(contentView);
        dialogTag.show();
    }

    private void addCopy(String content) {
        if (!Xutils.notEmptyOrNull(content)) {
            return;
        }
        Xutils.sendTo(ctx, content);
        String url = MyApp.host + "/copy/addCopy?uuid=" + MyApp.uuid + "&text=" + content;
        Xutils.debug("log：" + new Date().toLocaleString() + " " + url);
        OkGo.<String>post(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                loadMore(true);
            }
        });

    }

}
