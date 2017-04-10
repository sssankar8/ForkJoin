package com.jinx;

import java.util.concurrent.ForkJoinPool;
import java.io.File;
import java.lang.Runtime;

public class Explode {
	private String path;
	private ForkJoinPool mainPool;
	
	public Explode() {
		this("H:\\test");		
	}
	public Explode(String path) {
		super();
		this.path = path;
		this.mainPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
	}
	
	private void startexploding(File f) {
		mainPool.invoke(new WordCount(f));		
    }
	
	public void printStats()
	{
		System.out.println(mainPool.toString());
	}
	public static void main(String[] args) throws InterruptedException {
		
		Explode xpld= new Explode();
		File dir = new File(xpld.path);
		if(dir.exists())
			for(File f:dir.listFiles()){
				xpld.startexploding(f);
				
			}
		xpld.printStats();
		while(xpld.mainPool.getQueuedTaskCount()!=0)
		xpld.printStats();
		xpld.mainPool.shutdown();
		Thread.sleep(2000);
		xpld.printStats();
	}

}
