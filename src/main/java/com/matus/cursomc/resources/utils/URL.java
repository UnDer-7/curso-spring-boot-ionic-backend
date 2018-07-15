package com.matus.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodeParam(String s) {
		try {
		//Quando uma String com espaços em branco é passado na URL ela pode vir "estranha", Ex: "TV Led", vai ficar "TV%20Led". URLDecoder trata isso.
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	//Como os parâmetros do ProdutoResouce.findPage são passados em String é necessários converte-los para Integer	
	public static List<Integer> decodeIntList(String s){
		//split() ==> Recorta a String baseado no caractere passado.
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		//Faz a mesma coisa, só que em uma única linha 
//		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
