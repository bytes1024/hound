package cn.bytes1024.hound.collect.test;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class NettyTimerWheelTest implements TimerTask {


    public static void main(String[] args) {
        /**
         * tick: 时间轮里每一格;
         * tickDuration: 每一格的时长;
         * ticksPerWheel: 时间轮总共有多少格.
         * newTimeout: 定时任务分配到时间轮
         */
        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(3, TimeUnit.SECONDS, 10);
        NettyTimerWheelTest nettyTimerWheelTest = new NettyTimerWheelTest();

        while (true) {
            hashedWheelTimer.newTimeout(nettyTimerWheelTest, 5, TimeUnit.SECONDS);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(Timeout timeout) throws Exception {

        System.out.println(timeout.isCancelled());


    }
}
