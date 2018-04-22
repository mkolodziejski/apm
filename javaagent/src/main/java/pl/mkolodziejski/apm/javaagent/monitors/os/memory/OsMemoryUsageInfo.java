package pl.mkolodziejski.apm.javaagent.monitors.os.memory;

public class OsMemoryUsageInfo {

    private final long timestamp;
    private final long memoryTotal;
    private final long memoryFree;
    private final long memoryAvailable;
    private final long swapTotal;
    private final long swapFree;

    public OsMemoryUsageInfo(long timestamp, long memoryTotal, long memoryFree, long memoryAvailable, long swapTotal, long swapFree) {
        this.timestamp = timestamp;
        this.memoryTotal = memoryTotal;
        this.memoryFree = memoryFree;
        this.memoryAvailable = memoryAvailable;
        this.swapTotal = swapTotal;
        this.swapFree = swapFree;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getMemoryTotal() {
        return memoryTotal;
    }

    public long getMemoryFree() {
        return memoryFree;
    }

    public long getMemoryAvailable() {
        return memoryAvailable;
    }

    public long getSwapTotal() {
        return swapTotal;
    }

    public long getSwapFree() {
        return swapFree;
    }


    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private long timestamp;
        private long memoryTotal;
        private long memoryFree;
        private long memoryAvailable;
        private long swapTotal;
        private long swapFree;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setMemoryTotal(long memoryTotal) {
            this.memoryTotal = memoryTotal;
            return this;
        }

        public Builder setMemoryFree(long memoryFree) {
            this.memoryFree = memoryFree;
            return this;
        }

        public Builder setMemoryAvailable(long memoryAvailable) {
            this.memoryAvailable = memoryAvailable;
            return this;
        }

        public Builder setSwapTotal(long swapTotal) {
            this.swapTotal = swapTotal;
            return this;
        }

        public Builder setSwapFree(long swapFree) {
            this.swapFree = swapFree;
            return this;
        }

        public OsMemoryUsageInfo build() {
            return new OsMemoryUsageInfo(timestamp, memoryTotal, memoryFree, memoryAvailable, swapTotal, swapFree);
        }
    }
}
