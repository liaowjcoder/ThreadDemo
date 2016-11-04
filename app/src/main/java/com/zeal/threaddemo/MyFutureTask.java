package com.zeal.threaddemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @作者 廖伟健
 * @创建时间 2016/11/4 10:42
 * @描述 ${TODO} 
 */

/**
 * 1、Future是用于包装Callable和Runnable的类，提供了取消，获取结果，判断是否取消，和判断是否线程是否运行结果的方法
 * 他是一个接口
 * 实现类：FutureTask
 * 并且FutureTask实现了RunnableFuture
 * RunnableFuture接口继承了Future和Runnable
 * 可以得出一个结论：
 * FutureTask即可以有Runnable的功能也有Future的功能
 * ===它可以确保程序员可以准确的知道什么时候任务被计算完毕====。
 * 2、从1可以知道，FutureTask即可以用于Thread中，也可以用于ExecutorService中
 * 3、Callable具有返回值，但是不能Runnable中运行在Thread中，它只能运行在ExecutorService中
 * 4、因此使用FutureTask去包装Callable，就可以实现在Thread中的应用【MainActivity】

 */
public class MyFutureTask extends FutureTask<String> {
    Handler mHandler;

    public MyFutureTask(Handler handler, Callable callable) {
        super(callable);
        this.mHandler = handler;

    }

    /**
     * result是任务完成后需要返回的结果
     *
     * @param runnable
     * @param result
     */
    public MyFutureTask(Runnable runnable, String result) {
        super(runnable, result);
    }

    @Override
    protected void done() {
        super.done();
        //该方法是在子线程中运行的
        Log.e("zeal", Thread.currentThread().getName());// pool-1-thread-1
        try {
            String s = this.get();
            Message msg = new Message();
            msg.what = 1;
            msg.obj = s;
            mHandler.sendMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
