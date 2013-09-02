
package com.metasploit.msf.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.metasploit.msf.GCMController;
import com.metasploit.msf.MsfApplication;
import com.metasploit.msf.R;
import com.metasploit.msf.model.MsfModel;
import com.metasploit.msf.model.Plugin;
import com.metasploit.msf.rpc.RpcConstants;

public class PluginFragment extends RpcFragment<Object> {

    public ListView listviewModules;
    private List<Plugin> plugins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container);

        listviewModules = (ListView) view.findViewById(android.R.id.list);
        listviewModules.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Plugin item = plugins.get(arg2);
                Object[] options = new Object[] {
                        item.id
                };

                if (item.enabled) {
                    runCmd(RpcConstants.PLUGIN_UNLOAD, options);
                } else {
                    if (item.id.equals("gcm_notify")) {
                        Map<String, String> pluginoptions = new HashMap<String, String>();
                        pluginoptions.put(GCMController.PROPERTY_API_KEY, GCMController.API_KEY);
                        pluginoptions.put(GCMController.PROPERTY_REG_ID,
                                GCMController.getRegistrationId(MsfApplication.getApplication()));

                        runCmd(RpcConstants.PLUGIN_LOAD, new Object[] {
                                "gcm_notify", pluginoptions
                        });
                    } else if (item.id.equals("handler")) {

                        Map pluginoptions = new HashMap();
                        pluginoptions.put("PAYLOAD", "generic/shell_reverse_tcp");
                        pluginoptions.put("LHOST", "192.168.0.3");
                        pluginoptions.put("LPORT", "4003");
                        pluginoptions.put("ExitOnSession", "false");
                        Object[] args = new Object[] {
                                "exploit", "multi/handler", pluginoptions
                        };
                        runCmd(RpcConstants.MODULE_EXECUTE, args);

                    } else {
                        runCmd(RpcConstants.PLUGIN_LOAD, options);
                    }
                }

                runCmd(RpcConstants.PLUGIN_LOADED, null);
            }
        });

        runCmd(RpcConstants.PLUGIN_LOADED, null);
        return view;
    }

    @Override
    public void onLoaded(Object object) {
        plugins = MsfModel.getInstance().getPlugins();
        listviewModules.setAdapter(new ArrayAdapter<Plugin>(getActivity(), android.R.layout.simple_list_item_activated_1, plugins) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Plugin p = plugins.get(position);
                TextView textView = ((TextView) view.findViewById(android.R.id.text1));
                textView.setText(p.description);
                textView.setEnabled(p.enabled);
                return view;
            }
        });

    }
}
