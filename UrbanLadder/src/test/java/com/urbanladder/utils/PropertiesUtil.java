package com.urbanladder.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	private static Properties properties;
	
	public static Properties getProperties(String filePath) {
		
		if (properties == null) {

			properties = new Properties();

			try {
				FileInputStream inStream = new FileInputStream(filePath);
				properties.load(inStream);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return properties;
	}
}
