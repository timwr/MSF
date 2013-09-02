
package com.metasploit.msf.fragments;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.metasploit.msf.R;
import com.metasploit.msf.rpc.RpcConstants;

public class ModuleListFragment extends RpcFragment<List<String>> {

    public ListView listviewModules;
    public ModuleFragment moduleFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modulelist, container);

        listviewModules = (ListView) view.findViewById(android.R.id.list);
        listviewModules.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                String module = (String) listviewModules.getAdapter().getItem(arg2);
                ModuleFragment moduleFragment = ModuleFragment.newInstance("exploit", module);
                moduleFragment.show(getFragmentManager(), "module");
            }
        });

        runCmd(RpcConstants.MODULE_EXPLOITS, null);
        return view;
    }

    @Override
    public void onLoaded(List<String> object) {
        listviewModules.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, object));
    }

}
