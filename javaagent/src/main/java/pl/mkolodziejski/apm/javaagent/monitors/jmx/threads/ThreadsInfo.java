package pl.mkolodziejski.apm.javaagent.monitors.jmx.threads;

public class ThreadsInfo {

    private final long timestamp;
    private final int threadCount;
    private final int peakThreadCount;

    private ThreadsInfo(long timestamp, int threadCount, int peakThreadCount) {
        this.timestamp = timestamp;
        this.threadCount = threadCount;
        this.peakThreadCount = peakThreadCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }


    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private long timestamp;
        private int threadCount;
        private int peakThreadCount;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setThreadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public Builder setPeakThreadCount(int peakThreadCount) {
            this.peakThreadCount = peakThreadCount;
            return this;
        }

        public ThreadsInfo build() {
            return new ThreadsInfo(timestamp, threadCount, peakThreadCount);
        }
    }
}
