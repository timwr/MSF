package com.metasploit.msf.test;

import com.metasploit.msf.rpc.MsfController;

public class TestConstants {

    private static final String HOST = "127.0.0.1";
    public static void connect() {
        MsfController.getInstance().connect(HOST, 55553, "msf", "msf", true);
    }
    
}

