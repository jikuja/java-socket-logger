package io.github.jikuja.socket_logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Example {
    public static void main (String[] s) throws Exception{
        foo();
    }

    public static void foo () throws Exception{
        BufferedReader ipFile = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com/").openStream()));
        String ip = ipFile.readLine();
        ipFile.close();

        System.out.println(ip);
    }
}
