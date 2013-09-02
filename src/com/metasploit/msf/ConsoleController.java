package com.metasploit.msf;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.metasploit.msf.rpc.MsfController;

import msf.MsgRpcImpl;
import android.util.Log;

public class ConsoleController {

	public static String getConsole() throws IOException {

		MsgRpcImpl msgRpcImpl = MsfController.getInstance().getMsgRpcImpl();
		Object consoles = msgRpcImpl.execute("console.list");
		Log.e("e", "c" + consoles);

		if (consoles != null) {
			HashMap<String, List> consoleMap = (HashMap<String, List>) consoles;
			List consoleList = consoleMap.get("consoles");
			if (consoleList == null || consoleList.size() == 0) {
				Object consoleId = msgRpcImpl.execute("console.create");
				Log.e("e", "consolseId" + consoleId);

				HashMap<String, String> consoleResult = (HashMap<String, String>) consoleId;
				return consoleResult.get("id");
			} else {

				HashMap<String, String> consoleResult = (HashMap<String, String>) consoleList.get(0);
				return consoleResult.get("id");
			}
		}
		return "0";
	}

	public static String getReadConsole(String consoleId) {
		MsgRpcImpl msgRpcImpl = MsfController.getInstance().getMsgRpcImpl();
		try {
			HashMap<String, String> consoleMap = (HashMap<String, String>) msgRpcImpl.execute("console.read", new Object[] { consoleId });
			Log.e("e", "c" + consoleMap);
			
			return consoleMap.get("data");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
