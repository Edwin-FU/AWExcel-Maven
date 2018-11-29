package com.carrefour.AWExcel;

import java.io.File;
import java.io.IOException;

public class ReadFile {
	public static void main(String[] args) throws IOException {
		App app = new App();
//		System.out.println(app.readFileByByte());
//		System.out.println(app.readFileByChar());
//		System.out.println(app.readFileByReader()+"zz");
		String str = "OPT10";
		String[] arrs = str.split("\\.");
		System.out.println(arrs[0].length());
		app.writeFile(new File(System.getProperty("user.dir")+"\\download_OPT\\20181113OPT"+10));
	}
}
