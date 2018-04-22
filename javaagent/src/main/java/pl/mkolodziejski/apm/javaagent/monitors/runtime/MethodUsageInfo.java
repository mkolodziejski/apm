package pl.mkolodziejski.apm.javaagent.monitors.runtime;

public class MethodUsageInfo {

    private final long timestamp;
    private final String signature;
    private final long count;
    private final long minTime;
    private final long maxTime;
    private final long avgTime;

    MethodUsageInfo(long timestamp, String signature, long count, long minTime, long maxTime, long avgTime) {
        this.timestamp = timestamp;
        this.signature = signature;
        this.count = count;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.avgTime = avgTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public long getCount() {
        return count;
    }

    public long getMinTime() {
        return minTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public long getAvgTime() {
        return avgTime;
    }


    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private String signature;
        private long count;
        private long minTime;
        private long maxTime;
        private long avgTime;
        private long timestamp;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder setCount(long count) {
            this.count = count;
            return this;
        }

        public Builder setMinTime(long minTime) {
            this.minTime = minTime;
            return this;
        }

        public Builder setMaxTime(long maxTime) {
            this.maxTime = maxTime;
            return this;
        }

        public Builder setAvgTime(long avgTime) {
            this.avgTime = avgTime;
            return this;
        }

        public MethodUsageInfo build() {
            return new MethodUsageInfo(timestamp, signature, count, minTime, maxTime, avgTime);
        }
    }
}
