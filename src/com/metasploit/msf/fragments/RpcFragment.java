
package com.metasploit.msf.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.metasploit.msf.model.MsfModel;
import com.metasploit.msf.rpc.MsfController;

public abstract class RpcFragment<T> extends Fragment {

    private T result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MsfController.registerListener(getActivity(), getClass().getSimpleName(), mMessageReceiver);
    }

    public void runCmd(String cmd, Object[] args) {
        Intent cmdIntent = MsfController.getIntent(cmd, args, getClass().getSimpleName());
        MsfController.runCmdAsync(getActivity(), cmdIntent);
    }

    @Override
    public void onDestroy() {
        MsfController.unregisterListener(getActivity(), mMessageReceiver);
        super.onDestroy();
    }
    
    public void updateModel(Intent intent) {
        Object object = MsfModel.getInstance().updateModel(intent);
        if (object != null) {
            result = (T) object;
            onLoaded(result);
        }
    }

    public void onLoaded(T object) {
        result = object;
    }

    public T getResult() {
        return result;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateModel(intent);
        }
    };
}
