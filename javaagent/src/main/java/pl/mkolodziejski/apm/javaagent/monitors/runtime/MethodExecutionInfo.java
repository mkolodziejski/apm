package pl.mkolodziejski.apm.javaagent.monitors.runtime;

import java.time.LocalDateTime;

class MethodExecutionInfo {
    private final String signature;
    private final long executionTime;

    private MethodExecutionInfo(String signature, long executionTime) {
        this.signature = signature;
        this.executionTime = executionTime;
    }

    public String getSignature() {
        return signature;
    }

    public long getExecutionTime() {
        return executionTime;
    }



    public static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private String signature;
        private long executionTime;

        public Builder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public Builder setExecutionTime(long executionTime) {
            this.executionTime = executionTime;
            return this;
        }

        public MethodExecutionInfo build() {
            return new MethodExecutionInfo(signature, executionTime);
        }
    }
}
