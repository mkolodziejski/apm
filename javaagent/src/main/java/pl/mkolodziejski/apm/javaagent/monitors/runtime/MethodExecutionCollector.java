package pl.mkolodziejski.apm.javaagent.monitors.runtime;

import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MethodExecutionCollector {

    // singleton instance
    private static final MethodExecutionCollector instance;

    // collection of all method invocations
    private ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> signatureToUsagesMap;

    // lock for safe metrics flushing
    private ReadWriteLock lock;


    static {
        instance = new MethodExecutionCollector();
    }


    private MethodExecutionCollector() {
        signatureToUsagesMap = new ConcurrentHashMap<>();
        lock = new ReentrantReadWriteLock();
    }



    /*
     * Static methods
     */

    static MethodExecutionCollector getInstance() {
        return instance;
    }

    public static void reportMethodExecution(String className, String methodName, long time) {
        String signature = getSignature(className, methodName);
        Debug.print(String.format("Method %s executed in %d [ms]", signature, time));

        MethodExecutionInfo methodExecutionInfo = MethodExecutionInfo.builder()
                .setSignature(signature)
                .setExecutionTime(time)
                .build();

        getInstance().reportExecution(methodExecutionInfo);
    }

    private static String getSignature(String className, String methodName) {
        return className + "." + methodName;
    }



    /*
     * Instance methods
     */

    private void reportExecution(MethodExecutionInfo methodExecutionInfo) {
        Lock readLock = lock.readLock();
        readLock.lock();

        try {
            signatureToUsagesMap.putIfAbsent(methodExecutionInfo.getSignature(), new ConcurrentLinkedQueue<>());
            signatureToUsagesMap.get(methodExecutionInfo.getSignature()).offer(methodExecutionInfo);
        } finally {
            readLock.unlock();
        }
    }


    ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> getMethodExecutions() {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> old;

        Lock writeLock = lock.writeLock();
        writeLock.lock();

        try {
            old = signatureToUsagesMap;
            signatureToUsagesMap = new ConcurrentHashMap<>();
        } finally {
            writeLock.unlock();
        }

        return old;
    }

}
