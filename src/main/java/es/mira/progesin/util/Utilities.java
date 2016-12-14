package es.mira.progesin.util;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("utilities")
public class Utilities {

	public Date getCurrentDate() {
		return new Date();
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

	// ************* Descripcion message error Progesin ********************
	/**
	 * @param e
	 * @return
	 */
	public static String messageError(Exception e) {
		String message = null;
		if (e.getMessage().length() > 4000) {
			message = Arrays.toString(e.getStackTrace()).substring(4000);
		}
		else {
			message = Arrays.toString(e.getStackTrace());
		}
		return message;
	}

	// ************* Descripcion message error Progesin END********************

}