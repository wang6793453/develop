package com.fanwe.www.develop.business;

import com.fanwe.lib.develop.business.FBusiness;
import com.fanwe.lib.develop.callback.FBSProgressCallback;
import com.fanwe.lib.http.IRequest;
import com.fanwe.lib.http.Request;
import com.fanwe.lib.http.RequestManager;
import com.fanwe.lib.http.callback.StringRequestCallback;
import com.fanwe.lib.http.impl.httprequest.PostRequest;

/**
 * Created by Administrator on 2017/10/31.
 */
public class MainBusiness extends FBusiness<MainBusiness.Callback>
{

    public void requestInitAndShow()
    {
        Request request = new PostRequest();
        request.setUrl("http://ilvbt3.fanwe.net/mapi/index.php");
        request.getParams().put("ctl", "app").put("act", "init");
        request.execute(new StringRequestCallback()
        {
            @Override
            public void onPrepare(IRequest request)
            {
                super.onPrepare(request);
                request.setTag(MainBusiness.class.getName());
            }

            @Override
            public void onStart()
            {
                super.onStart();
                showProgress("请稍后");
            }

            @Override
            public void onSuccess()
            {
                getCallback().onBsShowInitResult(getResult());
            }

            @Override
            public void onFinish()
            {
                super.onFinish();
                hideProgress();
            }
        });
    }

    @Override
    public void onDestroy()
    {
        RequestManager.getInstance().cancelTag(MainBusiness.class.getName());
    }

    public interface Callback extends FBSProgressCallback
    {
        /**
         * 显示初始化接口的结果
         *
         * @param result
         */
        void onBsShowInitResult(String result);
    }
}
