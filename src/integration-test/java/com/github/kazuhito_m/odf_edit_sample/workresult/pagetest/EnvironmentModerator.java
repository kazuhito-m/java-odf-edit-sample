package com.github.kazuhito_m.odf_edit_sample.workresult.pagetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

public class EnvironmentModerator {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentModerator.class);

    public String appRootUrl() {
        String ip = selfIpV4();
        logger.info("自身のIPとして認識したもの:" + ip);
        return String.format("http://%s:8080/", ip);
    }

    private String selfIpV4() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces())
                    .stream()
                    .flatMap(nic -> Collections.list(nic.getInetAddresses()).stream())
                    .map(InetAddress::getHostAddress)
                    .filter(ip -> ip.startsWith("192") || ip.startsWith("172"))
                    .reduce((first, second) -> second)
                    .orElseThrow(IllegalStateException::new);
        } catch (SocketException e) {
            return "127.0.0.1";
        }
    }
}
