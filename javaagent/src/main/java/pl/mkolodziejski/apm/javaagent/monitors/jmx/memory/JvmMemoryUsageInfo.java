package pl.mkolodziejski.apm.javaagent.monitors.jmx.memory;

public class JvmMemoryUsageInfo {

    private final long timestamp;
    private final long init;
    private final long used;
    private final long committed;
    private final long max;

    private JvmMemoryUsageInfo(long timestamp, long init, long used, long committed, long max) {
        this.timestamp = timestamp;
        this.init = init;
        this.used = used;
        this.committed = committed;
        this.max = max;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getInit() {
        return init;
    }

    public long getUsed() {
        return used;
    }

    public long getCommitted() {
        return committed;
    }

    public long getMax() {
        return max;
    }


    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private long timestamp;
        private long init;
        private long used;
        private long committed;
        private long max;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setInit(long init) {
            this.init = init;
            return this;
        }

        public Builder setUsed(long used) {
            this.used = used;
            return this;
        }

        public Builder setCommitted(long committed) {
            this.committed = committed;
            return this;
        }

        public Builder setMax(long max) {
            this.max = max;
            return this;
        }

        public JvmMemoryUsageInfo build() {
            return new JvmMemoryUsageInfo(timestamp, init, used, committed, max);
        }
    }
}
