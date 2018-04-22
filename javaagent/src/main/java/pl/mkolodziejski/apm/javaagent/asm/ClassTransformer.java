package pl.mkolodziejski.apm.javaagent.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import pl.mkolodziejski.apm.javaagent.config.Config;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {

    private final Config config;

    public ClassTransformer(Config config) {
        this.config = config;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        String instrumentationPackage = config.getInstrumentationPackage().replaceAll("\\.", "/");
        if(className == null || !className.startsWith(instrumentationPackage)) {
            return null;
        }

        Debug.print(" * Class: " + className);

        byte[] bytecode;

        try {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            ReportingClassVisitor classV = new ReportingClassVisitor(config, cw);

            cr.accept(classV, ClassReader.EXPAND_FRAMES);

            bytecode = cw.toByteArray();

        } catch (RuntimeException e) {
            Debug.dumpStacktrace(e);
            throw e;
        }

        return bytecode;
    }
}
