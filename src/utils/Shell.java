
package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import com.metasploit.msf.MsfApplication;

public class Shell {

    private static Process exec(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder().command(command)
                .directory(MsfApplication.getApplication().getFilesDir());
        return builder.start();
    }

    public static String execCmd(String command) {
        StringBuffer buffer = new StringBuffer();
        DataOutputStream writer = null;
        BufferedReader reader = null;
        try {
            Process process = exec("sh");
            writer = new DataOutputStream(process.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            writer.writeBytes(command);
            writer.writeBytes("\nexit\n");
            writer.flush();
            writer.close();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
                Log.e("CMD", line);
            }
            reader.close();
            
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return buffer.toString();
    }
}
