package core;

import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFilesReader {
	private static final String CONFIGURATION_PATH = "configuration/Configuration.properties";

	/**
	 * Load configuration and application properties files in the system
	 * 
	 * @param clazz The class that use the properties
	 */
	public static void loadPropertiesFromFile(Class<?> clazz) {
		addToSystemProperties(clazz, CONFIGURATION_PATH);
	}

	/**
	 * Add the properties to the system
	 * 
	 * @param clazz    The class that use the properties
	 * @param filePath The path of the properties file
	 */
	private static void addToSystemProperties(Class<?> clazz, String filePath) {
		System.setProperties(getProperties(clazz, filePath));
	}

	/**
	 * Get the properties to load from the configuration file
	 * 
	 * @param clazz    The class that use the properties
	 * @param filePath The path of the properties file
	 * @return The properties to be loaded
	 */
	private static Properties getProperties(Class<?> clazz, String filePath) {
		try {
			final InputStream stream = clazz.getClassLoader().getResourceAsStream(filePath);
			Properties properties = System.getProperties();
			properties.load(stream);
			return properties;
		} catch (Exception e) {
			String errorMessage = String.format("----------- Property was not found. on %s file.", filePath);
			throw new Error(errorMessage);
		}
	}

	/**
	 * Get the value for the selected property and return it in string
	 * 
	 * @param propertyName The property name
	 * @return The value's property
	 */
	public static String getStringProperty(String propertyName) {
		String propertyValue = System.getProperty(propertyName);
		if (propertyValue != null) {
			return propertyValue;
		} else {
			String errorMessage = String
					.format("----------- Property %s not specified in the Configuration.properties file", propertyName);
			throw new Error(errorMessage);
		}
	}

	/**
	 * Get the value for the selected property and return it in long
	 * 
	 * @param propertyName The property name
	 * @return The value's property
	 */
	public static Long getLongProperty(String propertyName) {
		String propertyValue = System.getProperty(propertyName);
		if (propertyValue != null) {
			try {
				return Long.parseLong(propertyValue);
			} catch (NumberFormatException nfe) {
				String errorMessage = String.format("----------- Property %s not a long data type", propertyName);
				throw new Error(errorMessage);
			}
		} else {
			String errorMessage = String
					.format("----------- Property %s not specified in the Configuration.properties file", propertyName);
			throw new Error(errorMessage);
		}
	}
}
