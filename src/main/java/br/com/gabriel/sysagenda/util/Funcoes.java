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

	public static boolean verificaNumero(String num) {
		boolean sn = false;
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
				sn = true;
			}
		}
		return sn;
	}

	public static String[] separa(String palavra, String caractere) {
		String[] palavras = palavra.split(caractere);
		return palavras;
	}

	public static Date strToDate(String sData) {
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
