
package com.metasploit.msf.fragments;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.metasploit.msf.R;
import com.metasploit.msf.R.layout;
import com.metasploit.msf.activities.ModuleActivity;
import com.metasploit.msf.model.Session;
import com.metasploit.msf.rpc.RpcConstants;

public class SessionFragment extends RpcFragment<Object> {

    public ListView listviewModules;
    public List<Session> modules;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container);

        listviewModules = (ListView) view.findViewById(android.R.id.list);
        listviewModules.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Session session = modules.get(arg2);
                Intent intent = new Intent(getActivity(), ModuleActivity.class);
                intent.putExtra(Session.class.getSimpleName(), (Serializable) session);
                startActivity(intent);
            }
        });

        runCmd(RpcConstants.SESSION_LIST, null);
        return view;
    }

    @Override
    public void onLoaded(Object object) {
        modules = Session.getList(object);
        listviewModules.setAdapter(new ArrayAdapter<Session>(getActivity(), android.R.layout.simple_list_item_1, modules));

    }
}
