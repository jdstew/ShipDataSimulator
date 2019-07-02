/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 *
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: CharacterBuffer.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms;

/**
 * An object of this class is used to communicate packaged data between
 * objects.
 *
 * @author Jeff Stewart
 * @version 1.2.0.0, 2005-02-10
 */
public class CharacterBuffer {
   /** Default buffer size. */
   public static final int DEFAULT_BUFFER_SIZE = 96;
   
   char [] buffer;
   int index;
   
   /**
    * Initializes new CharacterBuffer object.
    */
   public CharacterBuffer () {
      buffer = new char[DEFAULT_BUFFER_SIZE];
      index = 0;
   }
   
   /**
    * Initializes new CharacterBuffer object.
    *
    * @param initialSize Initial character buffer size.
    */
   public CharacterBuffer (int initialSize) {
      if (initialSize > 0) {
         buffer = new char[initialSize];
      }
      else {
         buffer = new char[DEFAULT_BUFFER_SIZE];
      }
      index = 0;
   }
   
   /**
    * Clears the contents of the CharacterBuffer object.
    */
   public void clear () {
      index = 0;
   }
   
   /**
    * Gets the current length of the character buffer.
    *
    * @return Current length of character buffer.
    */
   public int length () {
      return index;
   }
   
   /**
    * Gets the capacity of the character buffer.
    *
    * @return Capacity of the character buffer.
    */
   public int capacity () {
      return buffer.length;
   }
   
   /**
    * Resizes the character buffer. Note, the existing character
    * buffer's contents will be lost.
    *
    * @param size The new character buffer size.
    */
   public void resize (int size) {
      buffer = new char[size];
   }
   
   /**
    * Append a character to the end of the character buffer's
    * content.
    *
    * @param c A char value to append.
    */
   public void append (char c) {
      if (index < capacity ()) {
         buffer[index] = c;
         index++;
      }
   }
   
   /**
    * Append a character array to the end of the character buffer's
    * content.
    *
    * @param charArray A char array of values to append.
    */
   public void append (char[] charArray) {
      for (int i = 0; i < charArray.length; i++) {
         append (charArray[i]);
      }
   }
   
   /**
    * Append a string to the end of the character buffer's
    * content.
    *
    * @param str A String object to append.
    */
   public void append (String str) {
      for (int i = 0; i < str.length (); i++) {
         append (str.charAt (i));
      }
   }
   
   /**
    * Gets the contents of the character buffer as a String object.
    *
    * @return String object of character buffer's contents.
    */
   public String toString () {
      return String.valueOf (toCharArray ());
   }
   
   /**
    * Gets the contents of the character buffer as a character array.
    *
    * @return Char array copy of character buffer's contents.
    */
   public char[] toCharArray () {
      
      char[] charArray = new char[index];
      
      for (int i = 0; i < index; i++) {
         charArray[i] = buffer[i];
      }
      
      return charArray;
   }
      
   /**
    * Gets the contents of the character buffer as a byte array.
    *
    * @return Byte array copy of character buffer's contents.
    */
   public byte[] toByteArray () {
      
      byte[] byteArray = new byte[index];
      
      for (int i = 0; i < index; i++) {
         byteArray[i] = (byte) buffer[i];
      }
      
      return byteArray;
   }
   
//   /**
//    * @param args the command line arguments
//    */
//   public static void main (String[] args) {
//      CharacterBuffer charBuffer = new CharacterBuffer();
//      long start = System.currentTimeMillis ();
//      for (int i = 0; i < 5000000; i++) {
//         charBuffer.clear ();
//         charBuffer.append ('x');
//         charBuffer.append ("string");
//         charBuffer.toCharArray ();
//         charBuffer.toString ();
//      }
//      System.out.println ("CharacterBuffer = " + (System.currentTimeMillis () - start));
//      
//      String str;
//      start = System.currentTimeMillis ();
//      for (int i = 0; i < 5000000; i++) {
//         str = "";
//         str = "x";
//         str += "string";
//         str.toCharArray ();
//      }
//      System.out.println ("String = " + (System.currentTimeMillis () - start));
//   }
}
/*
 * Version history:
 * 1.2.0.0 - added capability to set initial buffer size, which needed to be
 *           longer than 82 for RayNav750 (131).
 */
