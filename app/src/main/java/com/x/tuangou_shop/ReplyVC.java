package com.x.tuangou_shop;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.bigkoo.svprogresshud.listener.OnDismissListener;
import com.x.adapter.CommentAdapter;
import com.x.model.CommentModel;
import com.x.net.XAPPUtil;
import com.x.net.XActivityindicator;
import com.x.net.XNetUtil;
import com.x.util.BaseActivity;
import com.x.util.DataCache;
import com.x.util.MyEventBus;

import org.greenrobot.eventbus.EventBus;

import static com.x.util.ApplicationClass.APPService;

/**
 * Created by x on 2017/6/6.
 */

public class ReplyVC extends BaseActivity {

    EditText content;
    CommentModel model;
    @Override
    protected void setupUi() {
        setContentView(R.layout.comment_reply);
        content = (EditText) findViewById(R.id.comment_reply_content);
        model = (CommentModel) getIntent().getSerializableExtra("model");

        content.setText(model.getReply_content());

    }

    @Override
    protected void setupData() {

    }

    public void do_submit(View v)
    {
        if(!XAPPUtil.isNull(content))
        {
            return;
        }

        InputMethodManager imm = (InputMethodManager) v
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


        XActivityindicator.create().show();

        String sid = DataCache.getInstance().user.getSid();
        String aid = DataCache.getInstance().user.getId();
        String data_id = model.getId();
        final String cstr = content.getText().toString().trim();

        XNetUtil.Handle(APPService.dp_submit(sid, aid, data_id, cstr), "回复提交成功", null, new XNetUtil.OnHttpResult<Boolean>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(Boolean aBoolean) {

                if(aBoolean)
                {
                    if(CommentAdapter.ReplyModel != null)
                    {
                        CommentAdapter.ReplyModel.setReply_content(cstr);
                    }

                    EventBus.getDefault().post(new MyEventBus("ReplySuccess"));
                    XActivityindicator.getHud().setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(SVProgressHUD hud) {
                            back();
                        }
                    });
                }

            }
        });


    }

}
