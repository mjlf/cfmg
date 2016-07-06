package com.mjlf.cfmg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

public class FileOp {
	
	/**
	 * 读取文件
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public synchronized String readFile(String path) throws IOException{
		String info = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String iffo = null;
			while((iffo = reader.readLine()) != null){
				info += iffo;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if(reader != null){
				reader.close();
			}
		}
		return info;
	}
	
	/**
	 * 写文件操作
	 * @param info 写入信息
	 * @param path 文件路径
	 * @throws IOException
	 */
	public synchronized void write(String info, String path) throws IOException{
		Writer writer = null;
		try {
			writer = new FileWriter(path);
			writer.write(info);
		} catch (Exception e) {
			throw e;
		}finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	/**
	 * 删除指定文件
	 * @param path 文件路径
	 */
	public synchronized void deleteFile(String path){
		try {
			File file = new File(path);
			if(file.exists() && file.isFile()){
				file.delete();
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
