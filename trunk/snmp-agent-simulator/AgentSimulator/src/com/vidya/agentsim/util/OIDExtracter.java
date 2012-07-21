package com.mindtree.agentsim.util;

import java.util.StringTokenizer;

import com.mindtree.agentsim.common.OIDValidator;

public class OIDExtracter {

	private static String[] numberArray;
	private static int arraySize;
	private static int numberOfRecursiveSearch;
	private OIDValidator oidValidator;

	public OIDExtracter(OIDValidator oidValidator) {
		
		this.oidValidator = oidValidator;
	}
	/**
	 * @param args
	 */

	public String getNextOID(String oidToBeFetched) 
	{
		String childOfOidToBeFetched = oidToBeFetched + ".1";

		String OID = oidValidator.checkOid(childOfOidToBeFetched);
		if (null != OID) {
			return OID;
		} else {
			constructNumArray(oidToBeFetched);
			boolean oidFoundInCache = false;

			while (!oidFoundInCache && numberOfRecursiveSearch > 0) {

				String lastDigit = numberArray[numberArray.length - 1];
				Integer lastDigitIncremented = Integer.parseInt(lastDigit) + 1;
				String lastString = lastDigitIncremented.toString();

				String newStringToBESearchedInCache = new String();
				for (int k = 0; k < numberArray.length; k++) {
					if (k == numberArray.length - 1) {
						newStringToBESearchedInCache = newStringToBESearchedInCache
								.concat(lastString).concat(".");
					} else {
						newStringToBESearchedInCache = newStringToBESearchedInCache
								.concat(numberArray[k]).concat(".");
					}
				}
				String newStringToBESearchedInCacheNew = newStringToBESearchedInCache
						.substring(0, newStringToBESearchedInCache.length() - 1);

				String returnedString = oidValidator
						.checkOid(newStringToBESearchedInCacheNew);
				if (null == returnedString) {
					oidFoundInCache = false;
					numberOfRecursiveSearch--;
					String oidStringByRemovingLastDigit = removeLastDigit(newStringToBESearchedInCacheNew);
					if (oidStringByRemovingLastDigit.length() > 1) {
						String oidStringByRemovingLastDigitAndDot = oidStringByRemovingLastDigit
								.substring(0, oidStringByRemovingLastDigit
										.length() - 1);
						constructNumArray(oidStringByRemovingLastDigitAndDot);
					}
				} else {
					oidFoundInCache = true;
					System.out.println(" OID Found in Cache");
					System.out
							.println("VALUE OF THAT OID IS " + returnedString);
					return returnedString;
				}

			}

			return null;
		}
	}

	private String removeLastDigit(String newStringToBESearchedInCacheNew) {
		StringTokenizer strTokenizer = new StringTokenizer(
				newStringToBESearchedInCacheNew, ".");
		int arraySize = strTokenizer.countTokens();
		String[] newStringArray = new String[arraySize - 1];
		int a = 0;
		while (strTokenizer.hasMoreTokens() && arraySize - 1 > 0) {
			newStringArray[a++] = strTokenizer.nextToken();
			arraySize--;
		}
		StringBuffer stringBuffer = new StringBuffer();
		for (int x = 0; x < newStringArray.length; x++) {
			stringBuffer.append(newStringArray[x]).append(".");
		}
		return stringBuffer.toString();
	}

	private void constructNumArray(String oidToBeFetched) {
		StringTokenizer strTokenizer = new StringTokenizer(oidToBeFetched, ".");
		numberOfRecursiveSearch = strTokenizer.countTokens();
		arraySize = strTokenizer.countTokens();
		numberArray = new String[arraySize];

		int a = 0;
		while (strTokenizer.hasMoreTokens() && arraySize > 0) {
			numberArray[a++] = strTokenizer.nextToken();
			arraySize--;
		}
	}

}
