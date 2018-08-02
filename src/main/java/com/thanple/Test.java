package com.thanple;

import com.google.common.base.Strings;
import org.apache.hadoop.net.DNS;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Create by Thanple at 2018/1/8 上午9:00
 */
public class Test {

    public static void main(String[] args) throws UnknownHostException {
        InetSocketAddress initialIsa = new InetSocketAddress("localhost.0.0.127.in-addr.arpa", 0);
        System.out.println(initialIsa.getAddress());
        System.out.println("Failed resolve of " + initialIsa);

//        System.out.println(InetAddress.getByName("localhost.0.0.127.in-addr.arpa"));


        System.out.println(DNS.getDefaultHost("lo0", "lo0"));

    }
}
