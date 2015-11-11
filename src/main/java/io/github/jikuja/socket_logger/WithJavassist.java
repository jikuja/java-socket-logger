package io.github.jikuja.socket_logger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class WithJavassist {
    /**
     * Modifies java.net.Socket class and writes modified into c:\newrt or
     * to location given in args[0]
     *
     * @param args Command line arguments
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
        String s = (args.length >0 ? args[0] : null);
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

    /**
     * Prepends simple logging into method cm
     *
     * @param cm
     * @throws Exception
     */
    public static void prepend(CtMethod cm) throws Exception {
        cm.insertBefore(""
                + "System.out.println(\"SocketLogger\");"
                + "System.out.println(\"Thread: \" + Thread.currentThread().getName());"
                + "new Exception().printStackTrace();"
                + "");
    }
}
