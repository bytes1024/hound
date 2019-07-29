////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package cn.bytes1024.hound.commons.util;
//
//import io.netty.util.internal.PlatformDependent;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.*;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
//
//@Slf4j
//public class HashedWheelTimer implements Timer {
//    private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(HashedWheelTimer.class, 1, (long) Runtime.getRuntime().availableProcessors() * 4L);
//    private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
//    private final ResourceLeak leak;
//    private final HashedWheelTimer.Worker worker;
//    private final Thread workerThread;
//    public static final int WORKER_STATE_INIT = 0;
//    public static final int WORKER_STATE_STARTED = 1;
//    public static final int WORKER_STATE_SHUTDOWN = 2;
//    private volatile int workerState;
//    private final long tickDuration;
//    private final HashedWheelTimer.HashedWheelBucket[] wheel;
//    private final int mask;
//    private final CountDownLatch startTimeInitialized;
//    private final Queue<HashedWheelTimer.HashedWheelTimeout> timeouts;
//    private final Queue<HashedWheelTimer.HashedWheelTimeout> cancelledTimeouts;
//    private volatile long startTime;
//
//    public HashedWheelTimer() {
//        this(Executors.defaultThreadFactory());
//    }
//
//    public HashedWheelTimer(long tickDuration, TimeUnit unit) {
//        this(Executors.defaultThreadFactory(), tickDuration, unit);
//    }
//
//    public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel) {
//        this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
//    }
//
//    public HashedWheelTimer(ThreadFactory threadFactory) {
//        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
//    }
//
//    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
//        this(threadFactory, tickDuration, unit, 512);
//    }
//
//    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
//        this(threadFactory, tickDuration, unit, ticksPerWheel, true);
//    }
//
//    public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel, boolean leakDetection) {
//        this.worker = new HashedWheelTimer.Worker();
//        this.workerState = 0;
//        this.startTimeInitialized = new CountDownLatch(1);
//        this.timeouts = PlatformDependent.newMpscQueue();
//        this.cancelledTimeouts = PlatformDependent.newMpscQueue();
//        if (threadFactory == null) {
//            throw new NullPointerException("threadFactory");
//        } else if (unit == null) {
//            throw new NullPointerException("unit");
//        } else if (tickDuration <= 0L) {
//            throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
//        } else if (ticksPerWheel <= 0) {
//            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
//        } else {
//            this.wheel = createWheel(ticksPerWheel);
//            this.mask = this.wheel.length - 1;
//            this.tickDuration = unit.toNanos(tickDuration);
//            if (this.tickDuration >= 9223372036854775807L / (long) this.wheel.length) {
//                throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", tickDuration, 9223372036854775807L / (long) this.wheel.length));
//            } else {
//                this.workerThread = threadFactory.newThread(this.worker);
//                this.leak = !leakDetection && this.workerThread.isDaemon() ? null : leakDetector.open(this);
//            }
//        }
//    }
//
//    private static HashedWheelTimer.HashedWheelBucket[] createWheel(int ticksPerWheel) {
//        if (ticksPerWheel <= 0) {
//            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
//        } else if (ticksPerWheel > 1073741824) {
//            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
//        } else {
//            ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
//            HashedWheelTimer.HashedWheelBucket[] wheel = new HashedWheelTimer.HashedWheelBucket[ticksPerWheel];
//
//            for (int i = 0; i < wheel.length; ++i) {
//                wheel[i] = new HashedWheelTimer.HashedWheelBucket();
//            }
//
//            return wheel;
//        }
//    }
//
//    private static int normalizeTicksPerWheel(int ticksPerWheel) {
//        int normalizedTicksPerWheel;
//        for (normalizedTicksPerWheel = 1; normalizedTicksPerWheel < ticksPerWheel; normalizedTicksPerWheel <<= 1) {
//            ;
//        }
//
//        return normalizedTicksPerWheel;
//    }
//
//    public void start() {
//        switch (WORKER_STATE_UPDATER.get(this)) {
//            case 0:
//                if (WORKER_STATE_UPDATER.compareAndSet(this, 0, 1)) {
//                    this.workerThread.start();
//                }
//            case 1:
//                break;
//            case 2:
//                throw new IllegalStateException("cannot be started once stopped");
//            default:
//                throw new Error("Invalid WorkerState");
//        }
//
//        while (this.startTime == 0L) {
//            try {
//                this.startTimeInitialized.await();
//            } catch (InterruptedException var2) {
//                ;
//            }
//        }
//
//    }
//
//    public Set<Timeout> stop() {
//        if (Thread.currentThread() == this.workerThread) {
//            throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
//        } else if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 2)) {
//            WORKER_STATE_UPDATER.set(this, 2);
//            if (this.leak != null) {
//                this.leak.close();
//            }
//
//            return Collections.emptySet();
//        } else {
//            boolean interrupted = false;
//
//            while (this.workerThread.isAlive()) {
//                this.workerThread.interrupt();
//
//                try {
//                    this.workerThread.join(100L);
//                } catch (InterruptedException var3) {
//                    interrupted = true;
//                }
//            }
//
//            if (interrupted) {
//                Thread.currentThread().interrupt();
//            }
//
//            if (this.leak != null) {
//                this.leak.close();
//            }
//
//            return this.worker.unprocessedTimeouts();
//        }
//    }
//
//    public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
//        if (task == null) {
//            throw new NullPointerException("task");
//        } else if (unit == null) {
//            throw new NullPointerException("unit");
//        } else {
//            this.start();
//            long deadline = System.nanoTime() + unit.toNanos(delay) - this.startTime;
//            HashedWheelTimer.HashedWheelTimeout timeout = new HashedWheelTimer.HashedWheelTimeout(this, task, deadline);
//            this.timeouts.add(timeout);
//            return timeout;
//        }
//    }
//
//    static {
//        AtomicIntegerFieldUpdater<HashedWheelTimer> workerStateUpdater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.class, "workerState");
//        if (workerStateUpdater == null) {
//            workerStateUpdater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
//        }
//
//        WORKER_STATE_UPDATER = workerStateUpdater;
//    }
//
//    private static final class HashedWheelBucket {
//        private HashedWheelTimer.HashedWheelTimeout head;
//        private HashedWheelTimer.HashedWheelTimeout tail;
//
//        private HashedWheelBucket() {
//        }
//
//        public void addTimeout(HashedWheelTimer.HashedWheelTimeout timeout) {
//            assert timeout.bucket == null;
//
//            timeout.bucket = this;
//            if (this.head == null) {
//                this.head = this.tail = timeout;
//            } else {
//                this.tail.next = timeout;
//                timeout.prev = this.tail;
//                this.tail = timeout;
//            }
//
//        }
//
//        public void expireTimeouts(long deadline) {
//            HashedWheelTimer.HashedWheelTimeout next;
//            for (HashedWheelTimer.HashedWheelTimeout timeout = this.head; timeout != null; timeout = next) {
//                boolean remove = false;
//                if (timeout.remainingRounds <= 0L) {
//                    if (timeout.deadline > deadline) {
//                        throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", timeout.deadline, deadline));
//                    }
//
//                    timeout.expire();
//                    remove = true;
//                } else if (timeout.isCancelled()) {
//                    remove = true;
//                } else {
//                    --timeout.remainingRounds;
//                }
//
//                next = timeout.next;
//                if (remove) {
//                    this.remove(timeout);
//                }
//            }
//
//        }
//
//        public void remove(HashedWheelTimer.HashedWheelTimeout timeout) {
//            HashedWheelTimer.HashedWheelTimeout next = timeout.next;
//            if (timeout.prev != null) {
//                timeout.prev.next = next;
//            }
//
//            if (timeout.next != null) {
//                timeout.next.prev = timeout.prev;
//            }
//
//            if (timeout == this.head) {
//                if (timeout == this.tail) {
//                    this.tail = null;
//                    this.head = null;
//                } else {
//                    this.head = next;
//                }
//            } else if (timeout == this.tail) {
//                this.tail = timeout.prev;
//            }
//
//            timeout.prev = null;
//            timeout.next = null;
//            timeout.bucket = null;
//        }
//
//        public void clearTimeouts(Set<Timeout> set) {
//            while (true) {
//                HashedWheelTimer.HashedWheelTimeout timeout = this.pollTimeout();
//                if (timeout == null) {
//                    return;
//                }
//
//                if (!timeout.isExpired() && !timeout.isCancelled()) {
//                    set.add(timeout);
//                }
//            }
//        }
//
//        private HashedWheelTimer.HashedWheelTimeout pollTimeout() {
//            HashedWheelTimer.HashedWheelTimeout head = this.head;
//            if (head == null) {
//                return null;
//            } else {
//                HashedWheelTimer.HashedWheelTimeout next = head.next;
//                if (next == null) {
//                    this.tail = this.head = null;
//                } else {
//                    this.head = next;
//                    next.prev = null;
//                }
//
//                head.next = null;
//                head.prev = null;
//                head.bucket = null;
//                return head;
//            }
//        }
//    }
//
//    private static final class HashedWheelTimeout implements Timeout {
//        private static final int ST_INIT = 0;
//        private static final int ST_CANCELLED = 1;
//        private static final int ST_EXPIRED = 2;
//        private static final AtomicIntegerFieldUpdater<HashedWheelTimer.HashedWheelTimeout> STATE_UPDATER;
//        private final HashedWheelTimer timer;
//        private final TimerTask task;
//        private final long deadline;
//        private volatile int state = 0;
//        long remainingRounds;
//        HashedWheelTimer.HashedWheelTimeout next;
//        HashedWheelTimer.HashedWheelTimeout prev;
//        HashedWheelTimer.HashedWheelBucket bucket;
//
//        HashedWheelTimeout(HashedWheelTimer timer, TimerTask task, long deadline) {
//            this.timer = timer;
//            this.task = task;
//            this.deadline = deadline;
//        }
//
//        public Timer timer() {
//            return this.timer;
//        }
//
//        public TimerTask task() {
//            return this.task;
//        }
//
//        public boolean cancel() {
//            if (!this.compareAndSetState(0, 1)) {
//                return false;
//            } else {
//                this.timer.cancelledTimeouts.add(this);
//                return true;
//            }
//        }
//
//        void remove() {
//            HashedWheelTimer.HashedWheelBucket bucket = this.bucket;
//            if (bucket != null) {
//                bucket.remove(this);
//            }
//
//        }
//
//        public boolean compareAndSetState(int expected, int state) {
//            return STATE_UPDATER.compareAndSet(this, expected, state);
//        }
//
//        public int state() {
//            return this.state;
//        }
//
//        public boolean isCancelled() {
//            return this.state() == 1;
//        }
//
//        public boolean isExpired() {
//            return this.state() == 2;
//        }
//
//        public void expire() {
//            if (this.compareAndSetState(0, 2)) {
//                try {
//                    this.task.run(this);
//                } catch (Throwable var2) {
//                    if (log.isWarnEnabled()) {
//                        log.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', var2);
//                    }
//                }
//
//            }
//        }
//
//        @Override
//        public String toString() {
//            long currentTime = System.nanoTime();
//            long remaining = this.deadline - currentTime + this.timer.startTime;
//            StringBuilder buf = (new StringBuilder(192)).append(StringUtils.simpleClassName(this)).append('(').append("deadline: ");
//            if (remaining > 0L) {
//                buf.append(remaining).append(" ns later");
//            } else if (remaining < 0L) {
//                buf.append(-remaining).append(" ns ago");
//            } else {
//                buf.append("now");
//            }
//
//            if (this.isCancelled()) {
//                buf.append(", cancelled");
//            }
//
//            return buf.append(", task: ").append(this.task()).append(')').toString();
//        }
//
//        static {
//            AtomicIntegerFieldUpdater<HashedWheelTimer.HashedWheelTimeout> updater = PlatformDependent.newAtomicIntegerFieldUpdater(HashedWheelTimer.HashedWheelTimeout.class, "state");
//            if (updater == null) {
//                updater = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.HashedWheelTimeout.class, "state");
//            }
//
//            STATE_UPDATER = updater;
//        }
//    }
//
//    private final class Worker implements Runnable {
//        private final Set<Timeout> unprocessedTimeouts;
//        private long tick;
//
//        private Worker() {
//            this.unprocessedTimeouts = new HashSet();
//        }
//
//        public void run() {
//            HashedWheelTimer.this.startTime = System.nanoTime();
//            if (HashedWheelTimer.this.startTime == 0L) {
//                HashedWheelTimer.this.startTime = 1L;
//            }
//
//            HashedWheelTimer.this.startTimeInitialized.countDown();
//
//            int i$;
//            HashedWheelTimer.HashedWheelBucket bucket;
//            do {
//                long deadline = this.waitForNextTick();
//                if (deadline > 0L) {
//                    i$ = (int) (this.tick & (long) HashedWheelTimer.this.mask);
//                    this.processCancelledTasks();
//                    bucket = HashedWheelTimer.this.wheel[i$];
//                    this.transferTimeoutsToBuckets();
//                    bucket.expireTimeouts(deadline);
//                    ++this.tick;
//                }
//            } while (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 1);
//
//            HashedWheelTimer.HashedWheelBucket[] arr$ = HashedWheelTimer.this.wheel;
//            int len$ = arr$.length;
//
//            for (i$ = 0; i$ < len$; ++i$) {
//                bucket = arr$[i$];
//                bucket.clearTimeouts(this.unprocessedTimeouts);
//            }
//
//            while (true) {
//                HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll();
//                if (timeout == null) {
//                    this.processCancelledTasks();
//                    return;
//                }
//
//                if (!timeout.isCancelled()) {
//                    this.unprocessedTimeouts.add(timeout);
//                }
//            }
//        }
//
//        private void transferTimeoutsToBuckets() {
//            for (int i = 0; i < 100000; ++i) {
//                HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout) HashedWheelTimer.this.timeouts.poll();
//                if (timeout == null) {
//                    break;
//                }
//
//                if (timeout.state() != 1) {
//                    long calculated = timeout.deadline / HashedWheelTimer.this.tickDuration;
//                    timeout.remainingRounds = (calculated - this.tick) / (long) HashedWheelTimer.this.wheel.length;
//                    long ticks = Math.max(calculated, this.tick);
//                    int stopIndex = (int) (ticks & (long) HashedWheelTimer.this.mask);
//                    HashedWheelTimer.HashedWheelBucket bucket = HashedWheelTimer.this.wheel[stopIndex];
//                    bucket.addTimeout(timeout);
//                }
//            }
//
//        }
//
//        private void processCancelledTasks() {
//            while (true) {
//                HashedWheelTimer.HashedWheelTimeout timeout = (HashedWheelTimer.HashedWheelTimeout) HashedWheelTimer.this.cancelledTimeouts.poll();
//                if (timeout == null) {
//                    return;
//                }
//
//                try {
//                    timeout.remove();
//                } catch (Throwable var3) {
//                    if (HashedWheelTimer.logger.isWarnEnabled()) {
//                        HashedWheelTimer.logger.warn("An exception was thrown while process a cancellation task", var3);
//                    }
//                }
//            }
//        }
//
//        private long waitForNextTick() {
//            long deadline = HashedWheelTimer.this.tickDuration * (this.tick + 1L);
//
//            while (true) {
//                long currentTime = System.nanoTime() - HashedWheelTimer.this.startTime;
//                long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
//                if (sleepTimeMs <= 0L) {
//                    if (currentTime == -9223372036854775808L) {
//                        return -9223372036854775807L;
//                    }
//
//                    return currentTime;
//                }
//
//                if (PlatformDependent.isWindows()) {
//                    sleepTimeMs = sleepTimeMs / 10L * 10L;
//                }
//
//                try {
//                    Thread.sleep(sleepTimeMs);
//                } catch (InterruptedException var8) {
//                    if (HashedWheelTimer.WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == 2) {
//                        return -9223372036854775808L;
//                    }
//                }
//            }
//        }
//
//        public Set<Timeout> unprocessedTimeouts() {
//            return Collections.unmodifiableSet(this.unprocessedTimeouts);
//        }
//    }
//}
