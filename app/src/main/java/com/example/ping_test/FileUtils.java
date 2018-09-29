package com.example.ping_test;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileUtils {
	public String SDPATH;

	public String getSDPATH() {
		return SDPATH;
	}

	public void setSDPATH(String sDPATH) {
		SDPATH = sDPATH;
	}

	public FileUtils() {
		// �õ���ǰ�ⲿ�洢�豸��Ŀ¼
		// SDCARD
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	// ��SD���ϴ����ļ�
	public File creatSDfile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	// ��SD���ϴ���Ŀ¼
	public File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}

	// �ж�SD���ϵ��ļ��Ƿ����
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	// ��InputStream�������д�뵽SD����ȥ
	public File writeToSDFromIuput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = creatSDfile(path + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return file;
	}
	
	public void writeToSDFromStr(String path,String fileName,String str){
		File file=null;
		FileOutputStream fos=null;
		try {
			file=new File(path, fileName);
			fos=new FileOutputStream(file);
			
//			fos.write(str.getBytes());
//			fos.write("\r\n".getBytes());
//			fos.write("I am lilu".getBytes());
//			fos.close();
			PrintWriter pw=new PrintWriter(fos,true);
			pw.println(str);;
			pw.close();
			Log.i("TAG", "====����ɹ�====:");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
