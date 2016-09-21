package es.mira.progesin.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.stereotype.Component;


@Component
public class Utilities {


	/**
	 * @author STAD Method for getting current timestamp in a 16 char
	 * @return
	 */
	public Timestamp getCurrentTimestamp() {
		java.util.Date today = new java.util.Date();
		Timestamp ts = new java.sql.Timestamp(today.getTime());
		return ts;
	}

	/**
	 * Method for converting string to date
	 * @param input
	 * @return
	 * @author STAD
	 */
	@SuppressWarnings("deprecation")
	public Date stringToDate(String input) {
		String[] spart = input.split(".");
		Date date = new Date(Integer.parseInt(spart[0]), Integer.parseInt(spart[0]), Integer.parseInt(spart[0]));
		return date;
	}

	/**
	 * Method for converting string to timestamp
	 * @param input
	 * @return
	 * @throws ParseException
	 * @author STAD
	 */

	public Timestamp stringToTimestamp(String input) throws ParseException {
		if (!input.equals("")) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsedDate = dateFormat.parse(input);
			java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		}
		else {
			return null;
		}
	}

	/**
	 * Method for getting first 16 characters of timestamp
	 * @param input
	 * @return
	 * @author STAD
	 */
	public String cutTimestamp(Timestamp input) {
		return input.toString().substring(0, 16);
	}

	/**
	 * Metoda care extrage continutul de la o eticheta xml
	 * 
	 * @param inReq
	 * @param prefix
	 * @param parametru
	 * @return
	 */
	public static String parseInReq(String inReq, String prefijo, String parameter) {
		String resul = "";

		int ini = inReq.indexOf("<" + prefijo + parameter + ">");
		int fin = inReq.indexOf("</" + prefijo + parameter + ">");
		if ((ini == -1) || (fin == -1)) {
			resul = "";
		}
		else {
			resul = inReq.substring(ini + (prefijo + parameter).length() + 2, fin).trim();
		}
		return resul;
	}

	/**
	 * 
	 * @param lant
	 * @return
	 */
	public static String validarParameterBusqueda(String cadena) {
		String retorno = new String();
		if ((cadena == null) || (cadena.equalsIgnoreCase(""))) {
			retorno = new String("");
		}
		else {
			retorno = new String(cadena.toUpperCase(Locale.getDefault()));
		}
		return retorno;
	}

	// ************* Generating new password Progesin ********************//
	/**
	 * @author EZENTIS STAD
	 * @param getPassword
	 * @return
	 * @comment getPassword
	 */
	public static String getPassword() {
		String pswd = getPinLetters() + getPinNumber();
		return pswd;
	}
	/**
	 * @author EZENTIS STAD
	 * @param getPassword
	 * @return
	 * @comment getPassword
	 */
	public static String codePassword(String password ) {
		String passwordCode = codeMD5(password);
		return passwordCode;
	}
	public static String NUMBARS = "0123456789";

	public static String LETTERS = "ABCDEFGHIJKLMNOPRSTUVXYZWQ";

	public static String getPinNumber() {
		return getRandomNumber(NUMBARS, 4);
	}

	public static String getPinLetters() {
		return getRandomLetters(LETTERS, 4);
	}

	public static String getRandomNumber(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	public static String getRandomLetters(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

	// ************* Generating new password Progesin END ********************//



	// ************* Validate Email Progesin ********************
	/**
	 * @comment Metodo que valida un correo Debe cumplir las siguentes condiciones - Que no sea vacia o nula. -Debe
	 * contener un caracter @. . (@x.es)
	 * @author EZENTIS STAD
	 * @param email
	 * @return true | false
	 */
	public static boolean validateEmail(String email) {
		if ((email != null) && (email != "") && (email.lastIndexOf("@") > 0)
				&& (email.lastIndexOf("@") < email.length() - 4)) {
			Pattern p = Pattern.compile("@");
			Matcher m = p.matcher(email);
			int count = 0;
			while (m.find()) {
				count++;
			}
			switch (count) {
			case 1: {
				// El caractero @ aparece solo una vez. Ok
				return true;
			}
			default: {
				// El caractero @ aparece mas veces . KO
				return false;
			}

			}
		}
		return false;
	}

	// ************* Validate Email Progesin ********************

	// ************* CodeMD5 for password Progesin ********************
	/**
	 * @comment Metodo que codifica password a md5
	 * @param password
	 * @return password
	 */
	public static String codeMD5(String password) {
		String pass = password;
		String p = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(pass.getBytes(), 0, pass.length());
			p = new BigInteger(1, m.digest()).toString(16);
			while (p.length() < 32) {
				p = "0" + p;
			}
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return p;
	}

	// ************* CodeMD5 for password Progesin END********************

	
	

	
}