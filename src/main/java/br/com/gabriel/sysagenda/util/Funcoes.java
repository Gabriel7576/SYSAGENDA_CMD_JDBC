package br.com.gabriel.sysagenda.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Funcoes {

	public static boolean verificaNome(String nome) {
		boolean sn = false;
		for (int i = 0; i < nome.length(); i++) {
			char c = nome.charAt(i);
			if ('0' <= c && c <= '9') {
				sn = true;
			}
		}
		return sn;

	}

	public static boolean isNumeroInvalido(String num) {
		try {
			Long.parseLong(num);
			return false;
		}catch (NumberFormatException e) {
		}
		return true;
	}

	public static String[] separa(String palavra, String caractere) {
		String[] palavras = palavra.split(caractere);
		return palavras;
	}

	public static Date strToDate(String sData) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date data = null;
		try {
			data = sdf.parse(sData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static Date strToDH(String sData) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date data = null;
		try {
			data = sdf.parse(sData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static String dateToStr(Date data) {

		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
	}
}
