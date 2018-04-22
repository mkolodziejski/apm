package pl.mkolodziejski.apm.javaagent.monitors.os.cpu;

public class CpuUsageInfo {

    private final long timestamp;
    private final int avgUse;

    public CpuUsageInfo(long timestamp, int avgUse) {
        this.timestamp = timestamp;
        this.avgUse = avgUse;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getAvgUse() {
        return avgUse;
    }


    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private long timestamp;
        private int avgUse;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setAvgUse(int avgUse) {
            this.avgUse = avgUse;
            return this;
        }

        public CpuUsageInfo build() {
            return new CpuUsageInfo(timestamp, avgUse);
        }
    }
}
