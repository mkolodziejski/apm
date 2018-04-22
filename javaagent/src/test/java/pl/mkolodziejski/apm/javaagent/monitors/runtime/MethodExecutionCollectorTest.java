package pl.mkolodziejski.apm.javaagent.monitors.runtime;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class MethodExecutionCollectorTest {

    @Test
    public void testNoExecutions() {
        // given
        // no executions

        // when
        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> methodExecutions =
                MethodExecutionCollector.getInstance().getMethodExecutions();

        // then
        assertEquals(0, methodExecutions.size());
    }

    @Test
    public void testSingleExecution() {
        // given
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod", 100L);

        // when
        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> methodExecutions =
                MethodExecutionCollector.getInstance().getMethodExecutions();

        // then
        assertEquals(1, methodExecutions.size());
        assertEquals("MyClass.myMethod", methodExecutions.keys().nextElement());
        assertEquals(1, methodExecutions.get("MyClass.myMethod").size());
        MethodExecutionInfo info = methodExecutions.get("MyClass.myMethod").poll();
        assertEquals("MyClass.myMethod", info.getSignature());
        assertEquals(100L, info.getExecutionTime());
    }

    @Test
    public void testMultipleExecutionsToOneMethod() {
        // given
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod", 100L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod", 111L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod", 123L);

        // when
        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> methodExecutions =
                MethodExecutionCollector.getInstance().getMethodExecutions();

        // then
        assertEquals(1, methodExecutions.size());
        assertEquals("MyClass.myMethod", methodExecutions.keys().nextElement());
        assertEquals(3, methodExecutions.get("MyClass.myMethod").size());
    }

    @Test
    public void testMultipleExecutionsToMultipleMethods() {
        // given
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod1", 100L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod2", 111L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod1", 123L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod2", 321L);
        MethodExecutionCollector.reportMethodExecution("MyClass", "myMethod2", 333L);

        // when
        ConcurrentHashMap<String, ConcurrentLinkedQueue<MethodExecutionInfo>> methodExecutions =
                MethodExecutionCollector.getInstance().getMethodExecutions();

        // then
        assertEquals(2, methodExecutions.size());
        assertEquals(2, methodExecutions.get("MyClass.myMethod1").size());
        assertEquals(3, methodExecutions.get("MyClass.myMethod2").size());
    }
}