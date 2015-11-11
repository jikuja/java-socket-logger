package io.github.jikuja.socket_logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyFileClassTransformer implements ClassFileTransformer {
    @Override public byte[] transform (ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        if (className.equals("java/net/Socket")) {
            System.out.println("Modifying java.net.Socket");
            return doClass(className, classBeingRedefined, classfileBuffer);
        } else {
            return classfileBuffer;
        }
    }

    private byte[] doClass (String className, Class classBeingRedefined, byte[] classfileBuffer) {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = null;
        CtMethod cm = null;
        byte[] output =null;

        try {
            cc = cp.makeClass(new ByteArrayInputStream(classfileBuffer));
            cm = cc.getMethod("connect", "(Ljava/net/SocketAddress;)V");
            WithJavassist.prepend(cm);
            output = cc.toBytecode();
        } catch (Exception e) {
            System.out.println("Instrumentation fail:");
            e.printStackTrace();
        } finally {
            if (cc != null) {
                cc.detach();
            }
        }
        return output;
    }
}