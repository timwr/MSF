
package com.metasploit.msf.test;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

import utils.Network;
import utils.Shell;
import utils.Utils;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.test.ActivityInstrumentationTestCase2;

import com.metasploit.msf.MsfApplication;
import com.metasploit.msf.R;
import com.metasploit.msf.activities.SessionActivity;
import com.metasploit.msf.fragments.JobFragment;
import com.metasploit.msf.fragments.RpcFragment;
import com.metasploit.msf.fragments.SessionFragment;
import com.metasploit.msf.rpc.MsfController;

public class SessionActivityTest extends
        ActivityInstrumentationTestCase2<SessionActivity> {

    private SessionActivity sessionActivity;
    private RpcFragment<Object> rpcListFragment;

    public SessionActivityTest() {
        super(SessionActivity.class);
    }

    private MsfController msfController;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (msfController == null) {
            msfController = MsfController.getInstance();
            TestConstants.connect();
        }

        sessionActivity = (SessionActivity) getActivity();
    }

    public void testPluginModules() throws InterruptedException {
        rpcListFragment = (RpcFragment<Object>) sessionActivity.getFragmentManager().findFragmentById(R.id.fragment_plugin);
        assertNotNull(rpcListFragment);

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            if (rpcListFragment.getResult() != null) {
                break;
            }
        }

        assertTrue(rpcListFragment.getResult().toString().contains("msgrpc"));
    }

    public void testJobModules() throws InterruptedException {
        rpcListFragment = new JobFragment();

//        FragmentTransaction ft = sessionActivity.getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, rpcListFragment);
//        ft.commit();
//
//        for (int i = 0; i < 100; i++) {
//            Thread.sleep(100);
//            if (rpcListFragment.getResult() != null) {
//                break;
//            }
//        }
//
////        assertEquals(1,MsfModel.getInstance().getJobs());
//
//        Map<String, List> consoleMap = (Map<String, List>) rpcListFragment.getResult();
//        for (String key : consoleMap.keySet()) {
//            assertNotNull(consoleMap.get(key));
//        }
    }

    public void testSessionModules() throws InterruptedException {
        rpcListFragment = (SessionFragment) sessionActivity.getFragmentManager().findFragmentById(R.id.fragment_session);
        assertNotNull(rpcListFragment);

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            if (rpcListFragment.getResult() != null) {
                break;
            }
        }
        Map<String, List> consoleMap = (Map<String, List>) rpcListFragment.getResult();
        for (String key : consoleMap.keySet()) {
            Map<String, String> consoleResult = (Map<String, String>) consoleMap.get(key);
            assertNotNull(consoleResult.get("session_host"));
        }
    }


    public void testScan() throws IOException {
        Utils.unzipAsset(getActivity(), "nmap");
        String dir = MsfApplication.getApplication().getDir("nmap", Context.MODE_PRIVATE).getAbsolutePath();
        String scan = Shell.execCmd(dir + "/nmap-5.61/bin/nmap -v");
        assertTrue(scan.contains("Starting Nmap"));
        
        String networkMask = Network.getCurrentNetworkMask(getActivity());
        assertEquals("192.168.0.1/24", networkMask);
    }

}
