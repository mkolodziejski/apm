package pl.mkolodziejski.apm.javaagent.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import pl.mkolodziejski.apm.javaagent.config.Config;
import pl.mkolodziejski.apm.javaagent.util.Debug;

class ReportingClassVisitor extends ClassVisitor {

    private final Config config;

    private String className;

    ReportingClassVisitor(Config config, final ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
        this.config = config;
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;

        super.visit(version, access, name, signature, superName, interfaces);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Debug.print("  ** Method: " + name + "; descriptor: " + desc + "; signature: " + signature);

        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if("<init>".equals(name) || "<clinit>".equals(name)) {
            return mv;
        }

        return new ReportingMethodVisitor(config, access, desc, className, name, mv);
    }
}
