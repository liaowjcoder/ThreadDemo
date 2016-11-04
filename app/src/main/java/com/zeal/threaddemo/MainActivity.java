package com.zeal.threaddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        Callable<String> myCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "我是callable的计算结果";
            }
        };
        FutureTask<String> myFutureTask = new FutureTask<String>(myCallable);

        //（1）FutureTask应用于Thread中
        Thread thread2 = new Thread(myFutureTask);

        //（2）FutureTask应用于ExecutorService中
        ExecutorService service = Executors.newCachedThreadPool();
        Future<?> future = service.submit(myFutureTask);

        //Future可以操作线程的执行
        future.cancel(true);//取消任务的执行

        future.isCancelled();//判断任务是否被取消

        future.isDone();//判断任务是否执行完毕

        try {
            String s = (String) future.get();
            //获取任务返回的结果，这是一个阻塞的操作，直到call方法计算完毕
            //如果Future包装的是Runnable的话，get方法返回值是null
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
