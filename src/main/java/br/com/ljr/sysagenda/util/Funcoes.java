package br.com.ljr.sysagenda.util;

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
}
