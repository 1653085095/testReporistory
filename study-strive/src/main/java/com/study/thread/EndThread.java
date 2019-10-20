package com.study.thread;

public class EndThread {
	public static class UseThread implements Runnable{
		@Override
		public void run() {
			String name = Thread.currentThread().getName();
			System.out.println(name+" 开始 interrupt = "+Thread.currentThread().isInterrupted());
			
			while(!Thread.currentThread().isInterrupted()) {
				System.out.println(name+" 运行时 interrupt = "+Thread.currentThread().isInterrupted());
			}
				
			System.out.println(name+" 结束 interrupt = "+Thread.currentThread().isInterrupted());
			
		}
	}
	
	public static void main(String[] args) {
		Thread end = new Thread(new UseThread());
		end.start();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			System.out.println("error message = "+e);
		}
		end.interrupt();
	}
	
}
