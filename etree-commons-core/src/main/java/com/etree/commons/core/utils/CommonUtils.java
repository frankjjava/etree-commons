/**
* Copyright © 2020 elasticTree Technologies Pvt. Ltd.
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-11-04 
*/
package com.etree.commons.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.SystemPropertyUtils;

import com.etree.commons.core.CommonsSupportConstants;
import com.etree.commons.core.dto.Errors;
import com.etree.commons.core.dto.MessageDto;
import com.etree.commons.core.exception.EtreeCommonsException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	private static ObjectMapper objectMapper;

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
   
    public static String getClassPathLocation() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
	    Resource resource = null;
    	resource = loader.getResource(CommonsSupportConstants.CLASSPATH);
    	String path = null;
		try {
			URL url = resource.getURL();
			path = url.getPath();
		} catch (IOException e) {
			throw new EtreeCommonsException("", e);
		}
		return path;
	}

	public static String getClassPathLocation(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			throw new EtreeCommonsException("", "Filename cannot be null / empty !");
		}
		String classpath = getClassPathLocation();
		String absolutePath = classpath + File.separator + fileName;
		return absolutePath;
	}

	public static String loadFileFromClasspath(String fileName) {
		String absolutePath = getClassPathLocation(fileName);
		return loadFileFromAbsolutePath(absolutePath);
	}
	
	public static String loadFileFromAbsolutePath(String absolutePath) {
		String contents = null;
		try {
			contents = new String(Files.readAllBytes(Paths.get(absolutePath)));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new EtreeCommonsException("", e);
		} 
		return contents;
	}
	
	public static FilenameFilter createFilenameFilter(final String ext) {
		FilenameFilter filenameFilter = new FilenameFilter() {
            public boolean accept(File file, String name) {
            	if (name.endsWith(ext)) {
            		return true;
            	} 
            	return false;
            }
         };
         return filenameFilter;
	}

	public static String resolveBasePackage(String basePackage) {
	    return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	public static Class<?> loadClass(String clsName) {
		if (clsName == null) {
			throw new EtreeCommonsException("");
		}
		Class<?> cls = null;
		try {
			cls = Class.forName(clsName);
		} catch (ClassNotFoundException e) {
			throw new EtreeCommonsException("", e);
		}
		return cls;
	}
	
	public static Object createInstance(Class<?> cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
			throw new EtreeCommonsException("", e);
		}
		return obj;
	}
	
	public static <T> T cloneObject(T value) {
		if (value == null) {
			return null;
		}
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		T clone = null;
		try {
			clone = (T) objectMapper.readValue(objectMapper.writeValueAsString(value), value.getClass());
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new EtreeCommonsException("", e);
		}
		return clone;
	}
	
	
	public static List<String> findTypeNamesInPackage(String basePackage) {
	    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
	    List<String> lstClassNames = new ArrayList<>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;
	    if (basePackage != null) {
	    	basePackage = basePackage.trim();
	    }
	    basePackage = CommonUtils.resolveBasePackage(basePackage);
	    packageSearchPath = packageSearchPath.concat(basePackage).concat("/**/*.class");
	    Resource[] resources;
		try {
			resources = resourcePatternResolver.getResources(packageSearchPath);
		    for (Resource resource : resources) {
		        if (resource.isReadable()) {
		            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	                lstClassNames.add(metadataReader.getClassMetadata().getClassName());
		        }
		    }
		} catch (IOException e) {
			throw new EtreeCommonsException("", e);
		}
	    return lstClassNames;
	}
	
	public static List<Class<?>> findTypesInPackage(String basePackage) {
		List<String> lstClsNames = findTypeNamesInPackage(basePackage);
		if (lstClsNames != null && !lstClsNames.isEmpty()) {
			List<Class<?>> lstCls = new ArrayList<>();
			for (String clsName : lstClsNames) {
				Class<?> cls = CommonUtils.loadClass(clsName);
				lstCls.add(cls);
			}
			return lstCls;
		}
		return null;
	}

	public static File createFile(String path, String fileName, String lines) {
		File targetDirectory = new File(path);
		if (!targetDirectory.exists()) {
			targetDirectory.mkdir();
		} else if (!targetDirectory.isDirectory()) {
			LOGGER.warn("Cannot create. Expected a folder but a file by name '" + path +  "' already exists!");
		}
		boolean didRename = renameFilesIfRequired(targetDirectory, path, fileName);
		if (!path.endsWith(File.separator)) {
			path = path.concat(File.separator);
		}
		File file = new File(path.concat(fileName));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(lines.getBytes());
		} catch (IOException e) {
			throw new EtreeCommonsException("", e);
		}
		return file;
	}
	
	public static File createFile(String path, String fileName, List<String> lines) {
		File targetDirectory = new File(path);
		if (!targetDirectory.exists()) {
			targetDirectory.mkdir();
		} else if (!targetDirectory.isDirectory()) {
			LOGGER.warn("Cannot create. Expected a folder but a file by name '" + path +  "' already exists!");
		}
		boolean didRename = renameFilesIfRequired(targetDirectory, path, fileName);
		if (!path.endsWith(File.separator)) {
			path = path.concat(File.separator);
		}
		File file = new File(path.concat(fileName));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			for(String line : lines) {
				fos.write(line.getBytes());
				fos.write("\n".getBytes());
			}
			fos.close();
		} catch (IOException e) {
			throw new EtreeCommonsException("", e);
		}
		return file;
	}
	
	private static boolean renameFilesIfRequired(File targetDirectory, String path, String fileName) {
		boolean didRename = false;
		if (path == null) {
			path = targetDirectory.getPath();
		}
		File[] files = targetDirectory.listFiles();
		if (files == null || files.length == 0) {
			return didRename;
		}
		for (File existingFle : files) {
			String existingFleName = existingFle.getName();
			if (existingFleName.equals(fileName)) {
				//rename original file
				int idx = 0;
				while(true) {
					File newFile = new File(path.concat(existingFleName.concat("."+idx)));
					if (newFile.exists()) {
						idx++;
						continue;
					}
					existingFle.renameTo(newFile);
					didRename = true;
					break;
				}
			}
		}
		return didRename;
	}
	
	public static String createUniqueIdWithNanoTime(int len) {
		long nano = Math.abs(System.nanoTime());
		String transactionIdentifier = "" + nano;
		transactionIdentifier = RandomStringUtils.randomAlphanumeric(len - transactionIdentifier.length()) + nano;
		return transactionIdentifier;
	}
	
	public static StringBuilder buildCsvMessage(StringBuilder msgBuilder, String msg) {
		if (msgBuilder == null) {
			msgBuilder = new StringBuilder();
		} else {
			msgBuilder.append(", ");
		}
		msgBuilder.append(msg);
		return msgBuilder;
	}
	
	public static long convertDaysToMilis(int days){
		int multiplier = 86400000; // 24 * 60 * 60 * 1000
	    Long result = Long.valueOf(days * multiplier);
	    return result;
	}
	
	public static String retrieve(Map<String, String> params, String key) {
		if (params == null || params.isEmpty() || key == null) {
			LOGGER.warn("Nothing to retrieve! One of parameters may be null or empty");
			return null;
		}
		String value = null;
		for (Entry<String, String> entry : params.entrySet()) {
			if (entry.getKey().equals(key)) {
				value = entry.getValue();
				break;
			}
		}
		return value;
	}

	
	public static Response createResponse(Object value) {
		Response response = null;
		if (value != null) {
			if (value instanceof List) {
				GenericEntity<?> entity = new GenericEntity<List<?>>((List<?>) value) {};
				response = Response.ok(entity).build();
			} else {
				response = Response.ok(value).build();
			}
		}
		return response;
	}
	
	public static boolean hasEntityOfType(Response response, Class<?> type) {
		if (!response.hasEntity()) {
			return false;
		}
		try {
			Object value = response.readEntity(type);
		} catch (ProcessingException | IllegalStateException e) {
			 return false;			
		}
		return true;
	}
	
	public static Errors createErrors(Throwable ex) {
		String errCode = null;
		String errMsg = ex.getMessage();
		if (ex instanceof EtreeCommonsException) {
			EtreeCommonsException etreeCommonsException = (EtreeCommonsException) ex;
			errCode = "" + etreeCommonsException.getErrorCode();
		}
		return createErrors(errCode, errMsg);
	}
	
	public static MessageDto createMessageDto(Throwable ex) {
		MessageDto messageDto = new MessageDto();
		messageDto.setErrors(createErrors(ex));
		return messageDto;
	}
	
	public static MessageDto createMessageDto(Errors errors) {
		MessageDto messageDto = new MessageDto();
		messageDto.setErrors(errors);
		return messageDto;
	}

	public static Errors createErrors(String errCode, String errMsg) {
		Errors errors = new Errors();
		List<com.etree.commons.core.dto.Errors.Error> lstError = errors.getError();
		com.etree.commons.core.dto.Errors.Error error = new com.etree.commons.core.dto.Errors.Error();
		lstError.add(error);
		return errors;
	}

	public static String convertCollectionToCsv(Collection<String> lst) {
		if (lst == null || lst.isEmpty()) {
			return null;
		}
		String csv = lst.toString().replace("[", "").replace("]", "");
		return csv;
	}
	
	public static void changeFieldValue(Class<?> cls, String fieldName, Object targetObject, Object value) {
		if (cls == null || fieldName == null || targetObject == null) {
			if (cls == null) {
				LOGGER.warn("Class is null! Cannot change field-value.");
			}
			if (fieldName == null) {
				LOGGER.warn("Fieldname is null! Cannot change field-value.");
			}
			if (targetObject == null) {
				LOGGER.warn("Target object is null! Cannot change field-value.");
			}
			return;
		}
		Field field = ReflectionUtils.findField(cls, fieldName);
		field.setAccessible(true);
		ReflectionUtils.setField(field, targetObject, value);
		field.setAccessible(false);
	}
	
	public static byte[] createSha1Hash(String value) {
		byte[] digest = null;
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("SHA-1");
			digest = msgDigest.digest(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new EtreeCommonsException("", e);
		}
		return digest;
	}
	
	public static String createSha1HashString(String value) {
		if (value == null) {
			return null;
		}
		return new String(createSha1Hash(value));
	}
}
