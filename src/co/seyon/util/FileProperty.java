package co.seyon.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileProperty {
	private static final String PROPERTY_FILE_NAME = "config.properties";
	private static final FileProperty PROPERTY_UTIL;
	private static Properties PROPERTIES;

	public static final String SAVE_PATH = "FILE_SAVE_PATH";
	
	static {
		PROPERTY_UTIL = new FileProperty();
	}

	private FileProperty() {
		InputStream inputStream = null;
		try {
			PROPERTIES = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME);
			if (inputStream != null) {
				PROPERTIES.load(inputStream);
			} else {
				throw new FileNotFoundException(
						"property file '" + PROPERTY_FILE_NAME + "' not found in the classpath");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static FileProperty getInstance() {
		return PROPERTY_UTIL;
	}

	public String getProperty(String propertyName) {
		return PROPERTIES.getProperty(propertyName);
	}
}
