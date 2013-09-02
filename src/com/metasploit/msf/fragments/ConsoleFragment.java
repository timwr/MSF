package com.metasploit.msf.fragments;

import java.io.IOException;

import com.metasploit.msf.ConsoleController;
import com.metasploit.msf.R;
import com.metasploit.msf.R.id;
import com.metasploit.msf.R.layout;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConsoleFragment extends Fragment {

	private AsyncTask<Void, Void, String> updateTask;
	private TextView textviewConsole;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_console, container);
		textviewConsole = (TextView) view.findViewById(R.id.textview_console);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (updateTask == null) {
			updateTask = new AsyncTask<Void, Void, String>() {
				
				@Override
				protected String doInBackground(Void... params) {
					String consoleId = null;
					try {
						consoleId = ConsoleController.getConsole();
					} catch (IOException e) {
						e.printStackTrace();
					}

					if (consoleId == null) {
						return null;
					}

					return ConsoleController.getReadConsole(consoleId);
				}

				@Override
				protected void onPostExecute(String result) {
					Log.e("e", "onPostExecute" + result);
					if (result != null) {
						String append = textviewConsole.getText().toString();
						textviewConsole.setText(append + result);
					}
					updateTask = null;
				};

			};
			updateTask.execute();
		}
	}

}
