package com.naio.mmeteoserver;

import java.util.UUID;

public class DataBean
{
	private UUID id;
	private String owner;
	private Long ts;
	private String phoneNumber;
	private Long dataTime;
	private Double dataTemperature;
	private Double dataHumidity;
	private String mmeteoSoftVersion;
	private String mmeteoDateVersion;
	private String imei;
	private String clientPhone1;
	private String clientPhone2;
	private String clientPhone3;
	private Integer temperatureMin;
	private Integer temperatureMax;
	private Integer humidityMin;
	private Integer humidityMax;
	private Integer smsReceived;
	private Integer smsSent;
	private Integer bytesReceived;
	private Integer bytesSent;
	private Integer signalStrength;
	
	public Integer getSmsReceived() {
		return smsReceived;
	}

	public void setSmsReceived(Integer smsReceived) {
		this.smsReceived = smsReceived;
	}

	public Integer getSmsSent() {
		return smsSent;
	}

	public void setSmsSent(Integer smsSent) {
		this.smsSent = smsSent;
	}

	public Integer getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(Integer bytesReceived) {
		this.bytesReceived = bytesReceived;
	}

	public Integer getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(Integer bytesSent) {
		this.bytesSent = bytesSent;
	}

	public Integer getTemperatureMin() {
		return temperatureMin;
	}

	public void setTemperatureMin(Integer temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public Integer getTemperatureMax() {
		return temperatureMax;
	}

	public void setTemperatureMax(Integer temperatureMax) {
		this.temperatureMax = temperatureMax;
	}

	public Integer getHumidityMin() {
		return humidityMin;
	}

	public void setHumidityMin(Integer humidityMin) {
		this.humidityMin = humidityMin;
	}

	public Integer getHumidityMax() {
		return humidityMax;
	}

	public void setHumidityMax(Integer humidityMax) {
		this.humidityMax = humidityMax;
	}

	public DataBean()
	{
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getDataTime() {
		return dataTime;
	}

	public void setDataTime(Long dataTime) {
		this.dataTime = dataTime;
	}

	public Double getDataTemperature() {
		return dataTemperature;
	}

	public void setDataTemperature(Double dataTemperature) {
		this.dataTemperature = dataTemperature;
	}

	public Double getDataHumidity() {
		return dataHumidity;
	}

	public void setDataHumidity(Double dataHumidity) {
		this.dataHumidity = dataHumidity;
	}

	public String getMmeteoSoftVersion() {
		return mmeteoSoftVersion;
	}

	public void setMmeteoSoftVersion(String mmeteoSoftVersion) {
		this.mmeteoSoftVersion = mmeteoSoftVersion;
	}

	public String getMmeteoDateVersion() {
		return mmeteoDateVersion;
	}

	public void setMmeteoDateVersion(String mmeteoDateVersion) {
		this.mmeteoDateVersion = mmeteoDateVersion;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getClientPhone1() {
		return clientPhone1;
	}

	public void setClientPhone1(String clientPhone1) {
		this.clientPhone1 = clientPhone1;
	}

	public String getClientPhone2() {
		return clientPhone2;
	}

	public void setClientPhone2(String clientPhone2) {
		this.clientPhone2 = clientPhone2;
	}

	public String getClientPhone3() {
		return clientPhone3;
	}

	public void setClientPhone3(String clientPhone3) {
		this.clientPhone3 = clientPhone3;
	}

	public Integer getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(Integer signalStrength) {
		this.signalStrength = signalStrength;
	}
	
	
}
