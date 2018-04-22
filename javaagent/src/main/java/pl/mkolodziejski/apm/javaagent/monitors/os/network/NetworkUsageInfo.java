package pl.mkolodziejski.apm.javaagent.monitors.os.network;

public class NetworkUsageInfo {

    private final long timestamp;
    private final String interfaceName;
    private final long bytesSent;
    private final long bytesReceived;

    public NetworkUsageInfo(long timestamp, String interfaceName, long bytesSent, long bytesReceived) {
        this.timestamp = timestamp;
        this.interfaceName = interfaceName;
        this.bytesSent = bytesSent;
        this.bytesReceived = bytesReceived;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public long getBytesReceived() {
        return bytesReceived;
    }


    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private long timestamp;
        private String interfaceName;
        private long bytesSent;
        private long bytesReceived;

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder setBytesSent(long bytesSent) {
            this.bytesSent = bytesSent;
            return this;
        }

        public Builder setBytesReceived(long bytesReceived) {
            this.bytesReceived = bytesReceived;
            return this;
        }

        public NetworkUsageInfo build() {
            return new NetworkUsageInfo(timestamp, interfaceName, bytesSent, bytesReceived);
        }
    }
}
