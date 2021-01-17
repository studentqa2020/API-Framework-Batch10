package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseConfig {

	public static String getConfigValue(String value) throws IOException {
		String filepath = "./Config.properties";
		FileInputStream fis = new FileInputStream(filepath);

		Properties pro = new Properties();
		pro.load(fis);

		//System.out.println(pro.getProperty(value));

		return pro.getProperty(value);
	}

	public static void main(String[] args) throws IOException {
		getConfigValue("URL");
	}
}
