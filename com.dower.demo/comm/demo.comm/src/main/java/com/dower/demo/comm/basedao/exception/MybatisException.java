package com.dower.demo.comm.basedao.exception;

import org.apache.log4j.Logger;

public class MybatisException extends Exception{
	protected Logger log=Logger.getLogger(this.getClass());
	
	public MybatisException(){
		super();
	}
	
	public void columnErrorException(String message){
		log.error(message);
	}
	
	public void tableErrorException(String message){
		log.error(message);
	}
	
	public void errorException(String message){
		log.error(message);
	}
}
