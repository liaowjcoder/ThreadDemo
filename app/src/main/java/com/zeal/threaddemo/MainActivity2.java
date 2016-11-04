package com.zeal.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @作者 廖伟健
 * @创建时间 2016/11/4 11:10
 * @描述 ${TODO} 
 */

/**
 * 1、当使用ExecutorService时，就不需要将Callable封
 * 装成FutureTask了，因为submit方法会返回一个FutureTask
 * 2、Future#get方法不可以在主线程中直接调用，因为它是一个阻塞式的方法
 * 正确的方法是在是自定义一个FutureTask并重写done方法，在done通过get获取计算返回的结果。当然done方法是在子线程运行的，是不能直接更新UI的。
 */
public class MainActivity2 extends Activity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);


    }

    public void click(View view) {

        Callable<String> callable = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                return "我是call()耗时操作返回的结果";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        MyFutureTask myFutureTask = new MyFutureTask(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result = (String) msg.obj;
                text.setText("任务被开始于" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + ":" + result);
            }
        }, callable);

        //com.zeal.threaddemo.MyFutureTask@41a119a0
        //java.util.concurrent.FutureTask@41a150c8
        //两个并不是同一个对象
        Log.e("zeal", myFutureTask.toString());
        Log.e("zeal", service.submit(myFutureTask).toString());
        text.setText("任务被开始于" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
    }
}
