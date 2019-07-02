/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceErrorLog.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.analysis;

/**
 * An object of this class represents a sentence processing error.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SentenceError {

   int errorCode = -1;
   String errorMessage;
   char errorCharacter = 0;
   
   
   /**
    * Create an error by error type code.
    *
    * @param code Error code from SentenceErrorTypes.
    */   
   public SentenceError (int code) {
      errorCode = code;
   }
   
   /**
    * Create an error by error type code with a specific error message.
    *
    * @param code Error code from SentenceErrorTypes.
    * @param message The specific error message.
    */   
   public SentenceError (int code, String message) {
      errorCode = code;
      errorMessage = message;
   }
   
   /**
    * Create an error by error type code with a specific error message, and
    * the offending character.
    *
    * @param code Error code from SentenceErrorTypes.
    * @param message The specific error message.
    * @param character The character that caused the error.
    */   
   public SentenceError (int code, String message, char character) {
      errorCode = code;
      errorMessage = message;
      errorCharacter = character;
   }
   
   /**
    * Set the specific error code.
    *
    * @param code Error code from SentenceErrorTypes.
    */   
   public void setErrorCode (int code) {
      errorCode = code;
   }
   
   /**
    * Get the specific error code.
    *
    * @return Error code from SentenceErrorTypes.
    */   
   public int getErrorCode() {
      return errorCode;
   }
   
   
   /**
    * Set the error message.
    *
    * @param message The specific error message.
    */   
   public void setErrorMessage (String message) {
      errorMessage = message;
   }
   
   /**
    * Get the error message.
    *
    * @return The specific error message.
    */   
   public String getErrorMessage() {
      return errorMessage;
   }
    
   /**
    * Set the character in error.
    *
    * @param character The character that caused the error.
    */   
   public void setErrorCharacter (char character) {
      errorCharacter = character;
   }
   
   /**
    * Get the character in error.
    *
    * @return The character that caused the error.
    */   
   public char getErrorCharacter() {
      return errorCharacter;
   }
}
