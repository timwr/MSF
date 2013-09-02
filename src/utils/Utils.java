package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.metasploit.msf.MsfApplication;

import android.content.Context;
import android.util.Log;

public class Utils {
    
    public static void unzipTool(Context context) {

        String dir = MsfApplication.getApplication().getDir("nmap", Context.MODE_PRIVATE).getAbsolutePath();
        
        Utils.unzipAsset(context, "nmap");
        Shell.execCmd("chmod 777 " + dir + "/nmap-5.61/bin/nmap -v");
        Shell.execCmd(dir + "/nmap-5.61/bin/nmap -v");
    }
    public static void unzipAsset(Context context, String name) {
        ZipInputStream zin = null;
        try {
            final File outPath = context.getDir(name, Context.MODE_PRIVATE);
            if (new File(outPath, "nmap-5.61").isDirectory()) {
                return;
            }
            final String zipPath = name + ".zip";
            zin = new ZipInputStream(context.getAssets().open(zipPath));
            for (ZipEntry entry = zin.getNextEntry(); entry != null; entry = zin.getNextEntry()) {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.isDirectory()) {
                        new File(outPath, ze.getName()).mkdirs();
                    } else {
                        byte[] Unzipbuffer = new byte[1024];
                        FileOutputStream fout = new FileOutputStream(
                            new File(outPath, ze.getName()));
                        int Unziplength = 0;
                        while ((Unziplength = zin.read(Unzipbuffer)) > 0) {
                            fout.write(Unzipbuffer, 0, Unziplength);
                        }
                        zin.closeEntry();
                        fout.close();
                    }
                }
                zin.close();
            }
        } catch (IOException e) {
            Log.e(Utils.class.getSimpleName(), e.getMessage());
        } finally {
            try {
                if (zin != null) {
                    zin.close();
                }
            } catch (IOException ignored) {
            }
            zin = null;
        }
    }
}
