package com.utils.exception;

public class ChargeTest {
	public int shang(int x,int y) throws LingException,FuException {
		if(y==0){
			throw new LingException("除数不可为0");
		}
		if(y<0){
			throw new FuException("除数为"+y+",小于0!");
		}
		return x/y;
	}

}
