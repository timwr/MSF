
package com.metasploit.msf.activities;

import android.app.Activity;
import android.os.Bundle;

import com.metasploit.msf.R;
import com.metasploit.msf.fragments.PluginFragment;
import com.metasploit.msf.fragments.SessionFragment;


public class SessionActivity extends Activity {

    private SessionFragment sessionFragment;
    private PluginFragment pluginFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        sessionFragment = (SessionFragment)getFragmentManager().findFragmentById(R.id.fragment_session);
        pluginFragment = (PluginFragment)getFragmentManager().findFragmentById(R.id.fragment_plugin);
//        JobFragment jobFragment = new JobFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container, jobFragment);
//        ft.commit();
    }

}
