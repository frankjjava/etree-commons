package com.etree.commons.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;

import com.etree.commons.core.exception.EtreeCommonsException;


public class TranscientUidUtil {

	private static Map<String, TranscientUidDto> zonesConfig; 
	private static Map<String, Integer> zonesCounters; 
	private static int defaultUidTokenFullLength = 10;
	private static int defaultUidTokenSaltLength = 4;
	private static int defaultMaxSalt = 1000;
	
	public static void registerZone(TranscientUidDto transcientUidDto) {
		createOrResetZone(transcientUidDto, true);
	}

	public static void registerZone(String zoneName, int uidTokenFullLen, int uidTokenSaltLen, int maxSalt) {
		TranscientUidDto transcientUidDto = new TranscientUidDto();
		transcientUidDto.setZoneName(zoneName);
		transcientUidDto.setUidTokenFullLength(uidTokenFullLen);
		transcientUidDto.setUidTokenSaltLength(uidTokenSaltLen);
		transcientUidDto.setMaxSalt(maxSalt);
		registerZone(transcientUidDto);
	}

	public static void deRegisterZone(String zoneName) {
		if (zonesConfig == null || !zonesConfig.containsKey(zoneName)) {
			return;
		}
		zonesConfig.remove(zoneName.toUpperCase());
	}

	public static void refreshZone(TranscientUidDto transcientUidDto) {
		createOrResetZone(transcientUidDto, false);
	}
	
	private static void createOrResetZone(TranscientUidDto transcientUidDto, boolean isRegister) {
		if (transcientUidDto == null) {
			throw new EtreeCommonsException("",  "Zone config cannot be null!");
		}
		String zoneName = transcientUidDto.getZoneName();
		if (zoneName == null) {
			throw new EtreeCommonsException("",  "Zone-name cannot be null!");
		}
		if (zonesConfig == null) {
			zonesConfig = new HashMap<>();
		} else if (isRegister && zonesConfig.containsKey(zoneName)) {
			throw new EtreeCommonsException("",  "Transicent Uid Zone already registered!");
		}
		zonesConfig.put(zoneName.toUpperCase(), transcientUidDto);
	}
	
	public static boolean hasZone(String zoneName) {
		if (zonesConfig == null) {
			return false;
		}
		zoneName = zoneName.toUpperCase();
		return zonesConfig.containsKey(zoneName);
	}
	
	public static String createUidToken(String zoneName) {
		if (zoneName == null) {
			throw new EtreeCommonsException("",  "Zone-name cannot be null!");
		}
		zoneName = zoneName.toUpperCase();
		if (zonesConfig == null || !zonesConfig.containsKey(zoneName)) {
			return null;
		}
		TranscientUidDto transcientUidDto = zonesConfig.get(zoneName);
		int uidTokenFullLength = transcientUidDto.getUidTokenFullLength();
		if (uidTokenFullLength == 0) {
			uidTokenFullLength = defaultUidTokenFullLength;
		}
		int uidTokenSaltLength = transcientUidDto.getUidTokenSaltLength();
		if (uidTokenSaltLength == 0) {
			uidTokenSaltLength = defaultUidTokenSaltLength;
		}
		int maxSalt  = transcientUidDto.getMaxSalt();
		if (maxSalt == 0) {
			maxSalt = defaultMaxSalt;
		}
		if (zonesCounters == null) {
			synchronized (TranscientUidUtil.class) {
				if (zonesCounters == null) {
					zonesCounters = new HashMap<>();
				}
			}
		}
		Integer nextValue = 0;
		synchronized (zonesCounters) {
			nextValue = zonesCounters.get(zoneName);
			if (nextValue == null || nextValue == maxSalt) {
				nextValue = 0;
			} else {
				nextValue++;
			}
			zonesCounters.put(zoneName, nextValue);
		}
		int padLen = String.valueOf(nextValue).length();
		int uidLen = uidTokenFullLength - padLen;
		String uid = RandomStringUtils.randomAlphanumeric(uidLen);
		String uidToken = uid + nextValue;
		return uidToken;
	}
	
	public static String createUidTokenWithMillis(int len) {
		if (len < 14) {
			throw new EtreeCommonsException("",  "Cannot create UidToken! Length should be greater than 14.");
		}
		long nano = Math.abs(System.currentTimeMillis());
		String transactionIdentifier = "-" + nano;
		transactionIdentifier = RandomStringUtils.randomAlphanumeric(len - transactionIdentifier.length()) + transactionIdentifier;
		return transactionIdentifier;
	}

	public static class TranscientUidDto {
		private String zoneName;
		private int uidTokenFullLength;
		private int uidTokenSaltLength;
		private int maxSalt;
		
		public String getZoneName() {
			return zoneName;
		}
		public void setZoneName(String zoneName) {
			this.zoneName = zoneName;
		}
		public int getUidTokenFullLength() {
			return uidTokenFullLength;
		}
		public void setUidTokenFullLength(int uidTokenFullLength) {
			this.uidTokenFullLength = uidTokenFullLength;
		}
		public int getUidTokenSaltLength() {
			return uidTokenSaltLength;
		}
		public void setUidTokenSaltLength(int uidTokenSaltLength) {
			this.uidTokenSaltLength = uidTokenSaltLength;
		}
		public int getMaxSalt() {
			return maxSalt;
		}
		public void setMaxSalt(int maxSalt) {
			this.maxSalt = maxSalt;
		}
	}
}
