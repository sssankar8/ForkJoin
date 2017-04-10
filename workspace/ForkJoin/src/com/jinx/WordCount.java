package com.jinx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.RecursiveAction;

public class WordCount extends RecursiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WordCount(String file) {
		super();
		this.file = new File(file);
	}
	
	public WordCount(File file) {
		super();
		this.file = file;
	}

	private File file;

	public static void main(String[] args) {
		new WordCount("C:\\Users\\476147\\workspace\\ForkJoin\\src\\com\\jinx\\WordCount.java").compute();
		//System.out.println((new File("C:\\Users\\476147\\workspace\\ForkJoin\\src\\com\\jinx\\WordCount.java")).getAbsoluteFile());
	}

	@Override
	protected void compute() {
		try {
			String temp;
			Integer count;
			HashMap<String, Integer> wc = new HashMap<String, Integer>();
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((temp = br.readLine()) != null) {
				for (String s : temp.split("\\b")) {
					s=s.trim();
					if ((count = wc.put(s, 1)) != null)
						wc.put(s, count + 1);
				}
			}
			br.close();
			System.out.println("*********************************************");
			System.out.println(file.getName());
			System.out.println("*********************************************");
			for (Entry<String, Integer> wces : wc.entrySet()) {
				System.out.println(wces.getKey()+" : "+wces.getValue());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}

}
