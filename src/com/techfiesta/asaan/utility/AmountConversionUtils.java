package com.techfiesta.asaan.utility;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import android.text.TextUtils;

public class AmountConversionUtils {
	private static final NumberFormat FORMAT_CURRENCY = NumberFormat.getCurrencyInstance();

	/**
	 * Parses an amount into cents.
	 * 
	 * @param p_value
	 *            Amount formatted using the default currency.
	 * @return Value as cents.
	 * @throws java.text.ParseException
	 */
	public static long parseAmountToCents(String p_value) {
		if (TextUtils.isEmpty(p_value) == true)
			return 0;
		try {
			Number v_value = FORMAT_CURRENCY.parse(p_value);
			BigDecimal v_bigDec = new BigDecimal(v_value.doubleValue());
			v_bigDec = v_bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
			return v_bigDec.movePointRight(2).intValueExact();
		} catch (ParseException p_ex) {
			try {
				// p_value doesn't have a currency format.
				BigDecimal v_bigDec = new BigDecimal(p_value);
				v_bigDec = v_bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
				return v_bigDec.movePointRight(2).intValueExact();
			} catch (NumberFormatException p_ex1) {
				return -1;
			}
		}
	}

	/**
	 * Formats cents into a valid amount using the default currency.
	 * 
	 * @param p_value
	 *            Value as cents
	 * @return Amount formatted using a currency.
	 */
	public static String formatCentsToAmount(long p_value) {
		BigDecimal v_bigDec = new BigDecimal(p_value);
		v_bigDec = v_bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
		v_bigDec = v_bigDec.movePointLeft(2);
		String v_currency = FORMAT_CURRENCY.format(v_bigDec.doubleValue());
		return v_currency.replace(FORMAT_CURRENCY.getCurrency().getSymbol(), "").replace(",", "");
	}

	/**
	 * Formats cents into a valid amount using the default currency.
	 * 
	 * @param p_value
	 *            Value as cents
	 * @return Amount formatted using a currency.
	 */
	public static String formatCentsToCurrency(long p_value) {
		BigDecimal v_bigDec = new BigDecimal(p_value);
		v_bigDec = v_bigDec.setScale(2, BigDecimal.ROUND_HALF_UP);
		v_bigDec = v_bigDec.movePointLeft(2);
		return FORMAT_CURRENCY.format(v_bigDec.doubleValue());
	}

}
