package com.parser.exception;
public class ParsingException extends Exception
{
	private static final long serialVersionUID = 7263448955078038962L;

  public ParsingException(String message){
	  super();
  }
  
  public ParsingException(String message, Exception exception)
  {
	 
    super(message, exception);
  }
}