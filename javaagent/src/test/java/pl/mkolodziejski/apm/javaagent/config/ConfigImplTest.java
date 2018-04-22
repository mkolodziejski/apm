package pl.mkolodziejski.apm.javaagent.config;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConfigImplTest {

    @Test
    public void testDefaults() {
        // given
        String args = null;

        // when
        ConfigImpl config = ConfigImpl.parse(args);

        // then
        assertNotNull(config.getMetricsCollectionInterval());
        assertNotNull(config.getLogPath());
        assertNotNull(config.getInstrumentationPackage());
        assertNotNull(config.getInstrumentationAnnotatonClass());
        assertNotNull(config.isDebugOn());
    }


    @Test
    public void testSingleArg() {
        // given
        String args = "logPath=/a/b/c";

        // when
        ConfigImpl config = ConfigImpl.parse(args);

        // then
        assertEquals("/a/b/c", config.getLogPath());
    }


    @Test
    public void testAllArgs() {
        // given
        StringBuilder sb = new StringBuilder();
        sb.append("interval=123");
        sb.append(",logPath=/x/y/z");
        sb.append(",instrumentationPackage=a.b.c.d");
        sb.append(",instrumentationAnnotatonClass=x.y.z.Annotation");
        sb.append(",debugOn=false");
        String args = sb.toString();

        // when
        ConfigImpl config = ConfigImpl.parse(args);

        // then
        assertEquals(new Integer(123), config.getMetricsCollectionInterval());
        assertEquals("/x/y/z", config.getLogPath());
        assertEquals("a.b.c.d", config.getInstrumentationPackage());
        assertEquals("x.y.z.Annotation", config.getInstrumentationAnnotatonClass());
        assertEquals(false, config.isDebugOn());
    }


    @Test
    public void testInvalidArg() {
        // given
        String args = "xyz=/a/b/c";
        ConfigImpl defaultValuesConfig = ConfigImpl.parse("");

        // when
        ConfigImpl config = ConfigImpl.parse(args);

        // then
        assertEquals(defaultValuesConfig.getMetricsCollectionInterval(), config.getMetricsCollectionInterval());
        assertEquals(defaultValuesConfig.getLogPath(), config.getLogPath());
        assertEquals(defaultValuesConfig.getInstrumentationPackage(), config.getInstrumentationPackage());
        assertEquals(defaultValuesConfig.getInstrumentationAnnotatonClass(), config.getInstrumentationAnnotatonClass());
        assertEquals(defaultValuesConfig.isDebugOn(), config.isDebugOn());
    }


    @Test
    public void testInvalidArgValues() {
        // given
        String args = "interval=text,debugOn=text";

        // when
        ConfigImpl config = ConfigImpl.parse(args);

        // then
        assertEquals(new Integer(60), config.getMetricsCollectionInterval());
        assertEquals(false, config.isDebugOn());
    }
}