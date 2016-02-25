package com.naio.mmeteoserver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyManager 
{
	private static final Logger LOGGER = Logger.getLogger(PropertyManager.class);
	
	public static final String JDBC_DB_CONNECTION_STRING = "JDBC_DB_CONNECTION_STRING";
	public static final String JDBC_DB_USER = "JDBC_DB_USER"; 
	public static final String JDBC_DB_PASSWORD = "JDBC_DB_PASSWORD";
			
	Properties prop = new Properties();
	
	InputStream input = null;
		
	public PropertyManager()
	{
	}
	
	public void load(String propertyFilePath)
	{
		try
		{
			this.input = new FileInputStream( propertyFilePath );
			
			this.prop = new Properties();

			this.prop.load(input);
		}
		catch (IOException ex)
		{
			LOGGER.error(ex.getMessage());
			
			ex.printStackTrace();
		} 
		finally 
		{
			if (input != null) 
			{
				try 
				{
					input.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
					
					LOGGER.error(e.getMessage());
				}
			}
		}
	}
	
	public String getValue(String key)
	{
		String value = null;
		
		value = this.prop.getProperty(key);
		
		if( value != null )
		{
			value = value.trim();
		}
		
		return value;
	}
	
}
