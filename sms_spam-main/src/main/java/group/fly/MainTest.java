package group.fly;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import group.fly.crypto.TripleDESEncryption;

public class MainTest {
	
	private static void testDate()
	{
		int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		if(today == Calendar.SATURDAY ||today == Calendar.SUNDAY)
		{
			System.out.println("check success------------"+ today);
			return;
		}
		else
			System.out.println("check date success----"+today);
	}
	
	public static void main(String[] args) {
		testDate();
//		String filePath = "./config/tmp/tmp_data.txt";
//
//		String key = "+iiUEfrnloQM4vK/KxRulb3ZtY7E4w8T";
//		try {
//			String clearKey = TripleDESEncryption.decrypt("vnptepay_ats_hyweb_itopup", key);
//
//			System.out.println("clear key:" + clearKey);
//			ArrayList<String> lstData = readTextFile(filePath);
//
//			StringBuffer stbuff = new StringBuffer();
//
//			System.out.println("Tong data:" + lstData.size());
//			for (String string : lstData) {
//				System.out.println();
//				stbuff.append("'" + TripleDESEncryption.decrypt(clearKey, string) + "\r\n");
//			}
//			System.out.println(stbuff.toString());
//			writeFile1(stbuff.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	private static ArrayList<String> readTextFile(String filePath) {
		try {
			Scanner s = new Scanner(new File(filePath));
			ArrayList<String> list = new ArrayList<String>();
			while (s.hasNext()) {
				list.add(s.next());
			}
			s.close();

			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static void writeFile1(String input) throws IOException {
		File fout = new File("./config/tmp/out.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		bw.write(input);
		bw.newLine();

		bw.close();
	}
}
