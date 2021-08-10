/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-commons.
* 
*  The etree-commons is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The etree-commons is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with etree-commons project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package com.etree.commons.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.RandomStringUtils;
import com.etree.commons.core.exception.EtreeCommonsException;

public class CommonUtils {
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String createUniqueIdWithNanoTime(int len) {
		long nano = Math.abs(System.nanoTime());
		String transactionIdentifier = "" + nano;
		transactionIdentifier = RandomStringUtils.randomAlphanumeric(len - transactionIdentifier.length()) + nano;
		return transactionIdentifier;
	}

	public static long convertDaysToMilis(int days) {
		int multiplier = 86400000; // 24 * 60 * 60 * 1000
		Long result = Long.valueOf(days * multiplier);
		return result;
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
