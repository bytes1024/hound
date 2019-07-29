package cn.bytes1024.hound.commons.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.atomic.AtomicLong;


public class ThreadPoolUtils {

    // 拒绝策略
    // ThreadPoolExecutor.AbortPolicy: 丢弃任务并抛出RejectedExecutionException异常。 (默认)
    // ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
    // ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
    // ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

    public static ThreadPoolExecutor newThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                   TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                                   RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,
                handler);
    }

    public static ScheduledThreadPoolExecutor newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory, handler);
    }

    public static ScheduledThreadPoolExecutor defaultScheduledThreadPool(int corePoolSize, String threadNamePrefix) {
        return newScheduledThreadPool(corePoolSize, new ThreadFactoryImpl(threadNamePrefix, false), new AbortPolicy());
    }

    /**
     * 创建一个单线程的线程池。这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。
     * 此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
     *
     * @param threadNamePrefix
     * @return
     */
    public static ThreadPoolExecutor newSingleThreadExecutor(String threadNamePrefix) {
        return newThreadPool(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
                new ThreadFactoryImpl(threadNamePrefix, false), new AbortPolicy());
    }

    /**
     * 创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
     * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程
     *
     * @param nThreads
     * @return
     */
    public static ThreadPoolExecutor newFixedThreadPool(int nThreads, String threadNamePrefix) {
        //Executors.newCachedThreadPool()
        return newThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactoryImpl(threadNamePrefix, false), new AbortPolicy());
    }

    /**
     * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
     * 那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
     * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小
     *
     * @param threadNamePrefix
     * @return
     */
    public static ThreadPoolExecutor newCachedThreadPool(String threadNamePrefix) {
        return newThreadPool(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                new ThreadFactoryImpl(threadNamePrefix, false), new AbortPolicy());
    }

    static class ThreadFactoryImpl implements ThreadFactory {
        private static final ThreadGroup threadGroup = new ThreadGroup("walk");
        private static Logger log = LoggerFactory.getLogger(ThreadFactoryImpl.class);
        private final AtomicLong threadNumber = new AtomicLong(1);
        private final String namePrefix;
        private final boolean daemon;

        private ThreadFactoryImpl(String namePrefix, boolean daemon) {
            this.namePrefix = namePrefix;
            this.daemon = daemon;
        }

        public static ThreadGroup getThreadGroup() {
            return threadGroup;
        }

        public static ThreadFactory create(String namePrefix, boolean daemon) {
            return new ThreadFactoryImpl(namePrefix, daemon);
        }

        public static boolean waitAllShutdown(int timeoutInMillis) {
            ThreadGroup group = getThreadGroup();
            Thread[] activeThreads = new Thread[group.activeCount()];
            group.enumerate(activeThreads);
            Set<Thread> alives = new HashSet<Thread>(Arrays.asList(activeThreads));
            Set<Thread> dies = new HashSet<Thread>();
            log.info("Current ACTIVE thread count is: {}", alives.size());
            long expire = System.currentTimeMillis() + timeoutInMillis;
            while (System.currentTimeMillis() < expire) {
                classify(alives, dies, new ClassifyStandard<Thread>() {
                    @Override
                    public boolean satisfy(Thread thread) {
                        return !thread.isAlive() || thread.isInterrupted() || thread.isDaemon();
                    }
                });
                if (alives.size() > 0) {
                    log.info("Alive  threads: {}", alives);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        // ignore
                    }
                } else {
                    log.info("All Alive  threads are shutdown.");
                    return true;
                }
            }
            log.warn("Some Alive  threads are still alive but expire time has reached, alive threads: {}", alives);
            return false;
        }

        private static <T> void classify(Set<T> src, Set<T> des, ClassifyStandard<T> standard) {
            Set<T> set = new HashSet<>();
            for (T t : src) {
                if (standard.satisfy(t)) {
                    set.add(t);
                }
            }
            src.removeAll(set);
            des.addAll(set);
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(threadGroup, runnable,
                    threadGroup.getName() + "-" + namePrefix + "-" + threadNumber.getAndIncrement());
            thread.setDaemon(daemon);
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }

        private static interface ClassifyStandard<T> {
            boolean satisfy(T thread);
        }
    }

}
