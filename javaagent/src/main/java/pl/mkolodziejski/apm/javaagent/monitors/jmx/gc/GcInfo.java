package pl.mkolodziejski.apm.javaagent.monitors.jmx.gc;

public class GcInfo {

    private final long timestamp;
    private final String gcName;
    private final long collectionCount;
    private final long collectionTime;

    private GcInfo(long timestamp, String gcName, long collectionCount, long collectionTime) {
        this.timestamp = timestamp;
        this.gcName = gcName;
        this.collectionCount = collectionCount;
        this.collectionTime = collectionTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getGcName() {
        return gcName;
    }

    public long getCollectionCount() {
        return collectionCount;
    }

    public long getCollectionTime() {
        return collectionTime;
    }



    public static Builder builder() {
        return new Builder();
    }


    static class Builder {
        private long timestamp;
        private String gcName;
        private long collectionCount;
        private long collectionTime;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setGcName(String gcName) {
            this.gcName = gcName;
            return this;
        }

        public Builder setCollectionCount(long collectionCount) {
            this.collectionCount = collectionCount;
            return this;
        }

        public Builder setCollectionTime(long collectionTime) {
            this.collectionTime = collectionTime;
            return this;
        }

        public GcInfo build() {
            return new GcInfo(timestamp, gcName, collectionCount, collectionTime);
        }
    }
}
