package io.github.jikuja.socket_logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class WithJavassist {
    public static void main (String[] args) throws Exception {
        String s = args[0];
        String targetDir;
        if (s != null && !s.isEmpty()) {
            targetDir = s;
        } else {
            targetDir = "C:\\newrt";
        }


        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("java.net.Socket");

        CtMethod cm = cc.getMethod("connect", "(Ljava/net/SocketAddress;)V");
        System.out.println("Prepending: " + cm.getSignature());
        prepend(cm);

        // use target directory with -Xbootclasspath/p
        cc.writeFile(targetDir);
    }

    private static void prepend(CtMethod cm) throws Exception {
        cm.insertBefore(""
                + "System.out.println(\"ScoketLogger\");"
                + "System.out.println(\"Thread: \" + Thread.currentThread().getName());"
                + "new Exception().printStackTrace();"
                + "");
    }
}
