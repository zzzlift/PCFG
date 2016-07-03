package com.zhy.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;



public class FileOperator {
	/**
	 * write to file
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(f, true), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(content);
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			System.out.println("write error");
			e.printStackTrace();
		}
	}	
	
	/**
	 * read one File to stream,return BufferedReader br
	 * the you use br.readLine()
	 * 
	 */
	public static BufferedReader readFile (String filePath) {
		BufferedReader br = null;
		String line  = null;
		try {
			File f = new File(filePath);
			if(!f.exists()) {
System.out.print("file loss");
			}
			FileInputStream inStream = new FileInputStream(f);
			InputStreamReader in = new InputStreamReader(inStream,"UTF-8");
			br = new BufferedReader(in);
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//fileinputStream
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//inputStreamReader
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			//readLine
			e.printStackTrace();
		}
		return br;
	}
	/**
	 * find all the files in a directory
	 * @param dir:directory
	 * @param type:txt,xml,html...
	 * @param writeFile:the file that you want to save the result
	 */
	public static void getDirFile (String dir, String type, String writeFile) {
		//clear static List<String> or it will save all 
		fileList.clear();
		List<String> arrayList =
				getListFiles(
						dir,	//1st para
						type, true);
//System.out.println(arrayList.size());
				for(int i=0;i<arrayList.size();i++){
					String filePath = arrayList.get(i);
					generate(filePath, writeFile);
					System.out.println("now we are finding: " + i + " of " + arrayList.size());
				}
	}
	public static void generate(String filePath,String writeFile){
		File file = 
			new File(filePath);  //1st para
		
		BufferedReader br = null;
		InputStreamReader read = null;
		try { 
			String line;
			read = new InputStreamReader(new FileInputStream(file),"UTF-8"); 
			br = new BufferedReader(read);
			
			while((line=br.readLine())!=null){
				String tempLine = line.trim();
				if(tempLine.equals("")) continue;
				writeFile(tempLine , writeFile);
				System.out.println("now we are writing " + filePath);
				
			}
			br.close();
			read.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static List<String> getListFiles(String path, String suffix, boolean isdepth) {//杩斿洖suffix鎸囧畾鏍煎紡鐨勬枃浠剁殑缁濆璺緞鎴栬�呯洰褰曚笅鍖呭惈鐨勬墍鏈塻uffix鏂囦欢鐨勭粷瀵硅矾寰�
		File file = new File(path);
		return listFile(file, suffix, isdepth);
	}
	public static List<String> fileList = new ArrayList<String>();
	public static List<String> listFile(File f, String suffix, boolean isdepth) {//杩斿洖鏂囦欢鐨勭粷瀵硅矾寰勬垨鑰呯洰褰曚笅鍖呭惈鐨勬墍鏈夋枃浠剁殑缁濆璺緞
		//
		
		if (f.isDirectory() && isdepth == true) {
			File[] t = f.listFiles();//杩斿洖鏂囦欢澶逛笅鍖呭惈鐨勬墍鏈夋枃浠惰矾寰勫悕锛屼繚瀛樻垚鐭╅樀褰㈠紡
			for (int i = 0; i < t.length; i++) {
				listFile(t[i], suffix, isdepth);
			}
		} else {//濡傛灉瀹冩槸涓枃浠惰�屼笉鏄洰褰�
			String filePath = f.getAbsolutePath();//杩斿洖鏂囦欢f鐨勭粷瀵硅矾寰�

			if (suffix != null) {//濡傛灉鎴戜滑璁惧畾浜嗗笇鏈涚殑鍚庣紑鍚�
				int begIndex = filePath.lastIndexOf(".");//杩斿洖鏈�鍚庝竴涓偣鍙风殑鍧愭爣
				String tempsuffix = "";

				if (begIndex != -1)//濡傛灉鏈夌偣璇佹槑杩欎釜鏂囦欢鏈夊悗缂�鍚�
				{
					tempsuffix = filePath.substring(begIndex + 1, filePath
							.length()); //杩斿洖鏂囦欢鐨勫悗缂�鍚�
				}

				if (tempsuffix.equals(suffix)) {//濡傛灉鏂囦欢鐨勫悗缂�鍚嶄负suffix鎸囧畾鐨勫唴瀹�
					fileList.add(filePath);//灏辨妸鏂囦欢鐨勭粷瀵硅矾寰勫瓨杩涘幓
				}
			} else {//濡傛灉鎴戜滑娌¤瀹氬笇鏈涚殑鍚庣紑鍚�

				fileList.add(filePath);//涔熷瓨杩涘幓
			}

		}

		return fileList;//杩斿洖鏂囦欢鐨勮矾寰�
	}

}
