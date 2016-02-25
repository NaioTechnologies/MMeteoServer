package com.naio.mmeteoserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class MMeteoController
{
	private static final Logger logger = Logger.getLogger(MMeteoController.class);
	
	PropertyManager _PropertyManager = new PropertyManager();
	
	BufferedWriter _BufferedWriter;
	BufferedReader _BufferedReader;
	Connection _Connection;
	String _Url;
	
	public MMeteoController()
	{
		
	}

	public void doStuff( String propertyFilePath )
	{
		try
		{
			logger.info(MMeteoController.class.getPackage().getImplementationVersion());
		
			_PropertyManager.load(propertyFilePath);
			
			this._BufferedReader = new BufferedReader(new InputStreamReader(System.in));
			this._BufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
						
			this._Url =  _PropertyManager.getValue( PropertyManager.JDBC_DB_CONNECTION_STRING ); // "jdbc:postgresql://private.naio-technologies.com:5433/mmeteodb";

			Class.forName( "org.postgresql.Driver" );
			
			this.outputData();
			
			DataBean dataBean = this.inputData();
			
			if( dataBean != null )
			{
				this._Connection = DriverManager.getConnection( this._Url , _PropertyManager.getValue( PropertyManager.JDBC_DB_USER ), _PropertyManager.getValue( PropertyManager.JDBC_DB_PASSWORD ) );
			
				if( this._Connection != null )
				{
					this.insertDataBean(this._Connection, dataBean);
				}
				else
				{
					logger.warn("Cannot get connection : " + this._Url);
				}
			}
			
			this._BufferedWriter.close();
			this._BufferedReader.close();

		}
		catch(Exception e1)
		{
			logger.error("doStuff : " + e1.getMessage());
		}
		finally
		{
			try
			{
				if (this._Connection != null)
				{
					this._Connection.close();
				}
			}
			catch(Exception e2)
			{
				logger.error("doStuff : " + e2.getMessage());
			}
		}
	}


	private DataBean inputData()
	{
		DataBean dataBean = null;
		
		try
		{
			String input;
			int lineRead = 0;
			
			while((input=this._BufferedReader.readLine())!=null &&  lineRead < 3)
			{
				if( lineRead == 0 )
				{
					dataBean = this.parseData(input);
				}
				
				logger.info(input);
				lineRead++;
				
				break;
			}
			
		}
		catch(Exception e)
		{
			logger.error("inputData : " + e.getMessage());
		}
		
		return dataBean;
	}
	
	private void insertDataBean(Connection connection, DataBean dataBean)
	{
		try
		{
			 String query = "INSERT INTO public.meteo_data(id, owner, ts, phone_number, data_time, data_temperature, data_humidity, mmeteo_soft_version, mmeteo_date_version, imei, client_phone1, client_phone2, client_phone3, temperature_min, temperature_max, humidity_min, humidity_max, sms_received, sms_sent, bytes_received, bytes_sent, signal_strength ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  )";
			 
			 PreparedStatement stmt = connection.prepareStatement(query);
			 
			 stmt.setObject(1, dataBean.getId());
			 stmt.setString(2, dataBean.getOwner());
			 stmt.setLong(3, dataBean.getTs());
			 stmt.setString(4, dataBean.getPhoneNumber());
			 stmt.setLong(5, dataBean.getDataTime());
			 stmt.setDouble(6, dataBean.getDataTemperature());
			 stmt.setDouble(7, dataBean.getDataHumidity());			 
			 stmt.setString(8, dataBean.getMmeteoSoftVersion());
			 stmt.setString(9, dataBean.getMmeteoDateVersion());
			 stmt.setString(10, dataBean.getImei());
			 stmt.setString(11, dataBean.getClientPhone1());
			 stmt.setString(12, dataBean.getClientPhone2());
			 stmt.setString(13, dataBean.getClientPhone3());
			 stmt.setInt(14, dataBean.getTemperatureMin());
			 stmt.setInt(15, dataBean.getTemperatureMax());
			 stmt.setInt(16, dataBean.getHumidityMin());
			 stmt.setInt(17, dataBean.getHumidityMax());			 
			 stmt.setInt(18, dataBean.getSmsReceived());
			 stmt.setInt(19, dataBean.getSmsSent());
			 stmt.setInt(20, dataBean.getBytesReceived());
			 stmt.setInt(21, dataBean.getBytesSent());
			 stmt.setInt(22, dataBean.getSignalStrength());
			 
			 stmt.executeUpdate();
			 
			 stmt.close();
		}
		catch(Exception e)
		{
			logger.error("insertDataBean : " + e.getMessage());
		}	
	}
	
	private DataBean parseData(String line)
	{
		DataBean dataBean = null;
		
		String currentStep = "ID";
		
		try
		{
			// 0607753407 2014 05 05 14 03 25.5 50
			// 0607753407 2014 05 05 14 03 25.5 30
			//sprintf(monSMS,"%s %04d %02d %02d %02d %02d %d.%d %d",_OWN_NUM_,year(),month(),day(),hour(),minute(),(int)etat.temp, ((int)(etat.temp*10))%10,(int)etat.humid);
			String[] splittedData = line.split(" ");
			
			if( splittedData.length < 21)
			{
				throw new Exception("Bad format sent : " + line );
			}
			else
			{
				DateTime dateTime = new DateTime(DateTimeZone.UTC);
				
				dataBean = new DataBean();
				
				// ID
				dataBean.setId(UUID.randomUUID());
				
				// Owner
				currentStep = "Owner";
				dataBean.setOwner("MMeteoController");
				
				// TS
				currentStep = "ts";
				dataBean.setTs(dateTime.toDate().getTime());

				// Number Phone
				currentStep = "Number phone";
				dataBean.setPhoneNumber(splittedData[0]);
				
				// DataDate
				currentStep = "Data date";
				String dataDateString = splittedData[1] + "/" + splittedData[2] +"/" + splittedData[3] + " " + splittedData[4] + ":" + splittedData[5];

				java.util.Locale locale = java.util.Locale.US;
				DateTimeFormatter formatter = DateTimeFormat.forPattern( "yyyy/MM/dd HH:mm" ).withZone( DateTimeZone.UTC  ).withLocale( locale );
				DateTime dataDate = formatter.parseDateTime( dataDateString );
				dataBean.setDataTime(dataDate.toDate().getTime());
				
				// temperature
				currentStep = "Temperature";
				try
				{
					dataBean.setDataTemperature( Double.parseDouble( splittedData[6].replace(".", ",")) );	
				}
				catch(Exception e1)
				{
					dataBean.setDataTemperature( Double.parseDouble( splittedData[6].replace(",", ".")) );
				}
				
				// humidity
				currentStep = "Humidity";
				try
				{
					dataBean.setDataHumidity( Double.parseDouble( splittedData[7].replace(".", ",")) );	
				}
				catch(Exception e1)
				{
					dataBean.setDataHumidity( Double.parseDouble( splittedData[7].replace(",", ".")) );
				}
				
				// soft_version
				currentStep = "Soft Version";
				dataBean.setMmeteoSoftVersion(splittedData[8]);
				
				// date_version
				currentStep = "Date Version";
				dataBean.setMmeteoDateVersion(splittedData[9]);
				
				//imei
				currentStep = "Imei";
				dataBean.setImei(splittedData[10]);
				
				currentStep = "client phone 1";
				dataBean.setClientPhone1(splittedData[11]);
				
				currentStep = "client phone 2";
				dataBean.setClientPhone2(splittedData[12]);
				
				currentStep = "client phone 1";
				dataBean.setClientPhone3(splittedData[13]);
				
				currentStep = "min temperature";
				dataBean.setTemperatureMin(Integer.parseInt(splittedData[14]));

				currentStep = "max temperature";
				dataBean.setTemperatureMax(Integer.parseInt(splittedData[15]));
				
				currentStep = "min humidity";
				dataBean.setHumidityMin(Integer.parseInt(splittedData[16]));

				currentStep = "max humidity";
				dataBean.setHumidityMax(Integer.parseInt(splittedData[17]));
				
				currentStep = "sms received";
				dataBean.setSmsReceived(Integer.parseInt(splittedData[18]));
				
				currentStep = "sms sent";
				dataBean.setSmsSent(Integer.parseInt(splittedData[19]));
				
				currentStep = "bytes received";
				dataBean.setBytesReceived(Integer.parseInt(splittedData[20]));
				
				currentStep = "bytes sent";
				dataBean.setBytesSent(Integer.parseInt(splittedData[21]));
				
				currentStep = "signal Strength";
				try
				{
					dataBean.setSignalStrength( Integer.parseInt(splittedData[22]));
				}
				catch(Exception e1)
				{
					dataBean.setSignalStrength( 666 );
				}
				
			}
		}
		catch(Exception e)
		{
			logger.error("parseData current step ( " + currentStep + " ) : "  + e.getMessage());
		}
		
		return dataBean;
	}
	
	private void outputData()
	{
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date date = new Date();
			
			String output = dateFormat.format(date) + "\n";

			_BufferedWriter.write(output);
			
			_BufferedWriter.flush();
			
			logger.info( "Data sent to mmeteo : " + output);
		}
		catch(Exception e)
		{
			logger.error("outputData : " + e.getMessage());
		}
	}
}
