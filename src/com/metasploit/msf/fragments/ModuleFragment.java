
package com.metasploit.msf.fragments;

import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.metasploit.msf.R;
import com.metasploit.msf.model.ModuleOption;
import com.metasploit.msf.model.MsfModel;
import com.metasploit.msf.rpc.MsfController;
import com.metasploit.msf.rpc.RpcConstants;

public class ModuleFragment extends DialogFragment {

    private String type;
    private String module;

    private Button buttonExecute;
    private TextView textviewInfo;
    private TextView textviewDescription;
    private ListView listviewOptions;

    public static ModuleFragment newInstance(String type, String module) {
        ModuleFragment moduleFragment = new ModuleFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("module", module);
        moduleFragment.setArguments(args);
        return moduleFragment;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        type = getArguments().getString("type");
        module = getArguments().getString("module");
        
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = View.inflate(getActivity(), R.layout.fragment_module, null);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        textviewInfo = (TextView) view.findViewById(R.id.textview_info);
        textviewDescription = (TextView) view.findViewById(R.id.textview_description);
        listviewOptions = (ListView) view.findViewById(R.id.listview_options);
        buttonExecute = (Button) view.findViewById(R.id.button_execute);
        buttonExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        MsfController.registerListener(getActivity(), getClass().getSimpleName(), mMessageReceiver);
        Object[] args = new Object[] {
                type, module
        };
        textviewInfo.setText("Module: " + module);
        textviewInfo.setText(textviewInfo.getText().toString() + "\nType: " + type);
        runCmd(RpcConstants.MODULE_INFO, args);
        runCmd(RpcConstants.MODULE_OPTIONS, args);

        if (type.equals("exploit")) {
            Object[] pargs = new Object[] {
                    module
            };
            runCmd(RpcConstants.MODULE_COMPATIBLE_PAYLOADS, pargs);
        }
        return dialog;
    }
    
    private void setAdapter(List<ModuleOption> options) {
        listviewOptions.setAdapter(new ModuleOptionAdapter(getActivity(), R.layout.view_module_option, options));
        
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

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Object object = MsfModel.getInstance().updateModel(intent);
            if (RpcConstants.MODULE_INFO.equals(intent.getStringExtra(MsfController.CMD))) {
                MsfModel.getInstance().getModule().info.get("description");
                Map info = (Map) object;
                textviewDescription.setText(info.get("description").toString());
            } else if (RpcConstants.MODULE_COMPATIBLE_PAYLOADS.equals(intent.getStringExtra(MsfController.CMD))) {
                setAdapter(MsfModel.getInstance().getModule().options);
            } else if (RpcConstants.MODULE_OPTIONS.equals(intent.getStringExtra(MsfController.CMD))) {
                setAdapter(MsfModel.getInstance().getModule().options);
            }
        }
    };
}
