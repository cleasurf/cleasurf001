package com.utils.exception;

public class Main {
	public static void main(String[] args){
		ChargeTest c=new ChargeTest();
		try {
			c.shang(9, 0);
			c.shang(9, -1);
		} catch (LingException e) {
			// TODO Auto-generated catch block
			System.out.println("[alert]"+e.getMessage()); 
			e.printStackTrace();
		} catch (FuException e) {
			// TODO Auto-generated catch block
			System.out.println("[alert]"+e.getMessage()); 
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("[alert]"+e.getMessage()); 
			e.printStackTrace();
		}finally{
			System.out.print("异常捕获完毕！");
		}
		
		
	}

}
