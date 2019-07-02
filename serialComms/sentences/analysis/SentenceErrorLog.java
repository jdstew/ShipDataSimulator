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

import java.util.*;
/**
 * An error log object is used to collect problems found during the 
 * processing of a sentence.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class SentenceErrorLog implements Cloneable {
   
   Vector<SentenceError> sentenceErrors;
   int errorIndex;
   int overallQuality;
   
   /** Creates a new instance of SentenceErrorLog */
   public SentenceErrorLog () {
      sentenceErrors = new Vector<SentenceError>();
      errorIndex = 0;
      overallQuality = SentenceErrorTypes.VALID_SENTENCE; 
   }
   
   /** 
    * Clears the list of errors in the ErrorLog
    */
   public void clear () {
      sentenceErrors.clear ();
      errorIndex = 0;
      overallQuality = SentenceErrorTypes.VALID_SENTENCE; 
   }
   
   /**
    * Adds a sentence error to the log.
    *
    * @param sentenceError a particular processing error.
    */
   public void addError (SentenceError sentenceError) {
      int errorQuality = SentenceErrorTypes.getValidity (sentenceError.getErrorCode ());
      if (errorQuality < overallQuality) {
         overallQuality = errorQuality;
      }
      sentenceErrors.add (sentenceError);
   }
   
   /**
    * Get the number of processing errors.
    *
    * @return the number of sentence errors logged.
    */
   public int getErrorCount () {
      return sentenceErrors.size ();
   }
   
   /**
    * Get the summary quality of the sentence based upon errors logged.
    *
    * @return the 'poorest' quality maximum of errors logged.
    */
   public int getQualityValue () {
      return overallQuality;
   }
   
   /**
    * Prints the contents of the error log to the console.
    *
    * @return Cumulative text of errors in the error log.
    */
   public String printErrorLog () {
      int errorCount = sentenceErrors.size ();
      String errorList = "[" + errorCount + "] problem(s):\n";
      
      SentenceError thisError;
      if (errorCount > 0) {
         for (int i = 0; i < errorCount; i++) {
            thisError = (SentenceError) sentenceErrors.get(i);
            errorList += SentenceErrorTypes.getMessage(thisError.getErrorCode ());
            if (thisError.getErrorMessage () != null) {
               errorList += thisError.getErrorMessage () + "\n";
            }
            if (thisError.getErrorCharacter () > 0) {
               errorList += " Character '" + thisError.getErrorCharacter () + "' in error.\n";
            }
         }
      }
      return errorList;
   }
   
   /**
    * Used to duplicate the current state of SentenceErrorLog.
    *
    * @return Reference to new SentenceErrorLog object.
    */
   public Object clone () {
      try {
         return super.clone();
      }
      catch (CloneNotSupportedException e) {
         return null;
      }
   }
}
