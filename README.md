# What is this
Simple project which rewrites Java's Socket class to log information when Socket.connect() is executed

# Why
Why not? E.g. for MC mods this can be used to check if any mod does version checking or downloads something else in the main thread

# Still WHY? Have you ever heard about wireshark?
Wireshark does not give stack or name of the thread

# Have not you heard about solution X?
Please open a ticket and explain it or give some links to documetations.

# How does it work
This project uses Javassist to prepend logging into Socket.connect() method. Then target JVM must be configured to use new class.

# Usage new javaagent
1. Compile sources with `gradlew shadowjar`
2. Configure JVM
   * Add `-javaagent:<path to>/socket-logger-1.0-SNAPSHOT-all.jar` in your java arguments

# Usage (old static class changing, not recommended to use)
1. Compile sources with `gradlew shadowjar`
2. Execute jar with target JRE/JVM
   * e.g. `java -jar socket-logger-1.0-SNAPSHOT-all.jar` will create new Socket class into c:\newrt
   * e.g. `java -jar socket-logger-1.0-SNAPSHOT-all.jar $HOME/foo` will create new Socket class into $HOME/foo
3. Test new Socket class
   * e.g. `java -Xbootclasspath/p:c:\newrt -cp socket-logger-1.0-SNAPSHOT-all.jar io.github.jikuja.socket_logger.Example`
4. Configure your application to use new class(es)
   * add `java -Xbootclasspath/p:c:\newrt` into application's JVM arguments
   
Notice usage of `-Xbootclasspath/p:` when using rewritten version of Socket class