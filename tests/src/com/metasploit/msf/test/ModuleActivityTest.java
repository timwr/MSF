
package com.metasploit.msf.test;

import android.test.ActivityInstrumentationTestCase2;

import com.metasploit.msf.activities.ModuleActivity;
import com.metasploit.msf.fragments.ModuleListFragment;
import com.metasploit.msf.fragments.RpcFragment;
import com.metasploit.msf.rpc.MsfController;
import com.metasploit.msf.rpc.RpcConstants;
import com.metasploit.msf.R;

public class ModuleActivityTest extends
        ActivityInstrumentationTestCase2<ModuleActivity> {

    private ModuleActivity moduleActivity;
    private RpcFragment<Object> rpcListFragment;

    public ModuleActivityTest() {
        super(ModuleActivity.class);
    }

    private MsfController msfController;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        if (msfController == null) {
            msfController = MsfController.getInstance();
            TestConstants.connect();
        }

        moduleActivity = (ModuleActivity) getActivity();
    }

//    public void testPluginModules() throws InterruptedException {
//        rpcListFragment = (RpcFragment) moduleActivity.getFragmentManager().findFragmentById(R.id.fragment_module);
//        assertNotNull(moduleActivity);
//        assertNotNull(rpcListFragment);
//
//        rpcListFragment.runCmd(RpcConstants.PLUGIN_LOADED, null, "rpc");
//
//        for (int i = 0; i < 100; i++) {
//            Thread.sleep(100);
//            if (rpcListFragment.getResult() != null) {
//                break;
//            }
//        }
//        assertNotNull(rpcListFragment.getResult());
//        assertEquals("gcm_notify", rpcListFragment.getResult());
//    }
}
