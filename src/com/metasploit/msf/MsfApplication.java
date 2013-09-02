package com.metasploit.msf;


import android.app.Application;

public class MsfApplication extends Application {
	
	private static MsfApplication sInstance;

	public MsfApplication() {
		sInstance = this;
	}

	public static MsfApplication getApplication() {
		return sInstance;
	}
	
}
