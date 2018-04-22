package pl.mkolodziejski.apm.javaagent.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;
import pl.mkolodziejski.apm.javaagent.config.Config;
import pl.mkolodziejski.apm.javaagent.monitors.runtime.MethodExecutionCollector;
import pl.mkolodziejski.apm.javaagent.util.Debug;

import static org.objectweb.asm.Opcodes.*;

class ReportingMethodVisitor extends LocalVariablesSorter {

    private final String className;
    private final String methodName;
    private final String annotationClass;

    private boolean modifyMethod = false;
    private int t1LocalVar;

    ReportingMethodVisitor(Config config, int access, String desc, String className, String methodName, MethodVisitor mv) {
        super(Opcodes.ASM5, access, desc, mv);
        this.className = className;
        this.methodName = methodName;

        this.annotationClass = "L" + config.getInstrumentationAnnotatonClass().replaceAll("\\.", "/") + ";";
    }


    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        Debug.print("Annotation: " + desc + ", visible: " + visible);

        if(visible && annotationClass.equals(desc)) {
            Debug.print("    * Adding custom counter and method reporter");
            modifyMethod = true;
        }

        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        super.visitCode();

        if(!modifyMethod) {
            return;
        }

        t1LocalVar = newLocal(Type.LONG_TYPE);

        mv.visitMethodInsn(INVOKESTATIC,
                Type.getInternalName(System.class),
                "currentTimeMillis",
                Type.getMethodDescriptor(Type.LONG_TYPE), false);

        mv.visitVarInsn(LSTORE, t1LocalVar);
    }


    @Override
    public void visitInsn(int opcode) {

        if(!modifyMethod) {
            super.visitInsn(opcode);
            return;
        }

        if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
            int diffLocalVar = newLocal(Type.LONG_TYPE);

            mv.visitMethodInsn(INVOKESTATIC,
                    Type.getInternalName(System.class),
                    "currentTimeMillis",
                    Type.getMethodDescriptor(Type.LONG_TYPE),
                    false);

            mv.visitVarInsn(LLOAD, t1LocalVar);
            mv.visitInsn(LSUB);
            mv.visitVarInsn(LSTORE, diffLocalVar);

            mv.visitLdcInsn(className.replaceAll("/", "."));
            mv.visitLdcInsn(methodName);
            mv.visitVarInsn(LLOAD, diffLocalVar);

            visitMethodInsn(INVOKESTATIC,
                    Type.getInternalName(MethodExecutionCollector.class),
                    "reportMethodExecution",
                    Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType(String.class), Type.getType(String.class), Type.LONG_TYPE),
                    false);
        }

        super.visitInsn(opcode);
    }
}
