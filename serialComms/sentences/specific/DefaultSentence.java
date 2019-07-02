/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: DefaultSentence.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences.specific;

import serialComms.*;
import serialComms.sentences.*;
/**
 *
 * @author Jeff Stewart
 * @version 1.0.0.1, 2005-01-31
 */
public class DefaultSentence extends AbstractSentence {
   final static int ESTIMATED_SENTENCE_LENGTH = 82;
      
   /**
    * Get this sentence's estimated length in bytes.
    *
    * @return Estimated sentence length in bytes.
    */
   public int getEstimatedLength () {
      return ESTIMATED_SENTENCE_LENGTH;
   }
   
   /**
    * Get the sentence formatter ID.
    *
    * @return Sentence formatter ID from SentenceTypes class.
    */
   public int getSentenceFormatter () {
      return SentenceTypes.FORMATTER_XXX;
   }
   
   /**
    * Validates an acceptable talker ID for this sentence formatter.
    * 
    * @param id Talker ID to validate.
    * @return True if valide talker ID.
    */
   public boolean isSentenceIDValid (int id) {
      return true;
   }
   
   public String getSimulatorSentence() {
      return getRandomSentence();
   }

   /**
    * Get sentence based upon simulator data.
    *
    * @return Valid simulation-based sentence.
    */
   public String getManualSentence () {
      
      
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT00) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT01) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT10) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT20) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT21) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT30) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_3PT00) {
//
//      }
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_3PT01) {
//
//      }
        // Add checksum if required if not previously required
//      if (super.sentenceVersion >= SentenceTypes.NMEA_VERSION_2PT20) {
//
//      }
      
      return getRandomSentence();
   }
   
   /**
    * Get test sentence data. Note, the last digit of select values within
    * the sentence is random.
    *
    * @return Valid test sentence.
    */
   public String getRandomSentence () {
      int testVal = SentenceTools.getRandomDigit ();
      String testString = new String ("YYXXX,THIS,IS,THE,DEFAULT,SENTENCE,TALKING_" + testVal);
      return new String("$" + testString + '*' + SentenceTools.getChecksum(testString)
         + SentenceTools.CR + SentenceTools.LF);
   }
   
   /**
    * Process a received sentence.
    *
    * @param receivedSentence A ReceivedSentence.
    */
   public void processReceivedSentence (ReceivedSentence receivedSentence) {
      
   }
}
/*
 * Revision History:
 *
 * 1.0.0.1 - Modified processReceivedSentence() to receive a ReceivedSentence
 *    object.
 */