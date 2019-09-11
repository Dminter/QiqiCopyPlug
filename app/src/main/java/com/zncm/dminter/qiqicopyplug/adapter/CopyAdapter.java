package com.zncm.dminter.qiqicopyplug.adapter;


import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zncm.dminter.qiqicopyplug.Copy;
import com.zncm.dminter.qiqicopyplug.MyApp;
import com.zncm.dminter.qiqicopyplug.R;

import java.util.List;


/**
 * by jmx
 * on 2019/3/20 13:40
 */
public class CopyAdapter  extends BaseItemDraggableAdapter<Copy, BaseViewHolder> {
    public CopyAdapter(int layoutResId, List<Copy> copyList) {
        super(layoutResId,copyList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Copy item) {
        if (item != null) {
            helper.setText(R.id.content, item.getText());
            if (item.getNumber()!=null&&item.getNumber()>0){
                helper.setTextColor(R.id.content, MyApp.getInstance().ctx.getResources().getColor(R.color.colorAccent));
            }else {
                helper.setTextColor(R.id.content, MyApp.getInstance().ctx.getResources().getColor(R.color.material_blue_grey_500));
            }

        }
    }


}
