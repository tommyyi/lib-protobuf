package com.laser.api360.net;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

/*测试基类
【1】启动activity
【2】获取mContext
【3】提供等待函数*/

@RunWith(AndroidJUnit4.class)
public class TestBase
{
    public Context mContext;

    public void setUp() throws Exception
    {
        mContext = InstrumentationRegistry.getTargetContext();
    }

    private final Object mObject = new Object();

    public void over()
    {
        synchronized (mObject)
        {
            mObject.notify();
        }
    }

    public void waiting() throws Exception
    {
        synchronized (mObject)
        {
            mObject.wait();
        }
    }
}
