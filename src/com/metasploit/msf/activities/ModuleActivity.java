
package com.metasploit.msf.activities;

import android.app.Activity;
import android.os.Bundle;

import com.metasploit.msf.R;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

public class ModuleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        
    }
    

}
