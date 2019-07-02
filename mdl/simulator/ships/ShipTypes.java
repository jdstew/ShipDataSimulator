/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: ShipTypes.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.simulator.ships;

/**
 * The class represents basic ship simulation data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class ShipTypes {
   /** 378' WHEC high endurance cutter */
   public static final int WHEC378 = 378;
   /** 270' WMEC medium endurance cutter */
   public static final int WMEC270 = 270;
   /** 210' WMEC medium endurance cutter */
   public static final int WMEC210 = 210;
   /** 110' WPB patrol boat */
   public static final int WPB110 = 110;
   /** 140' WTGB ice breaking tugboat */
   public static final int WTGB140 = 140;
   
   /**
    * Get the supported ship types.
    *
    * @return References to this class' static ship types.
    */   
   public static int [] getShipTypes () {
      int [] shipTypes = {WPB110};
//      int [] shipTypes = {WHEC378,
//                          WMEC270,
//                          WMEC210,
//                          WPB110,
//                          WTGB140};
      return shipTypes;
   }
   
   /**
    * Get ship name by refernce type.
    *
    * @param shipType Reference to this class' static ship types.
    * @return Text description of the ship type.
    */   
   public static String getShipName (int shipType) {
      switch (shipType) {
         case WHEC378: {
            return "378' High Endurance Cutter (WHEC)";
         }
         case WMEC270: {
            return "270' Medium Endurance Cutter (WMEC)";
         }
         case WMEC210: {
            return "210' Medium Endurance Cutter (WMEC)";
         }
         case WPB110: {
            return "110' Patrol Boat (WPB)";
         }
         case WTGB140: {
            return "140' Icebreaking Tug (WTGB)";
         }
      }
      return "unknown";       
   }
   
   /**
    * Get a specific ship object, given a shipy type reference.
    *
    * @param shipType Reference to this class' static ship types.
    * @return A ship object used for simulation.
    */   
   public static AbstractShip getShipObject (int shipType) {
      switch (shipType) {
         case WHEC378: {
            return new DefaultShip();
         }
         case WMEC270: {
            return new DefaultShip();
         }
         case WMEC210: {
            return new DefaultShip();
         }
         case WPB110: {
            return new WPB110();
         }
         case WTGB140: {
            return new DefaultShip();
         }
         default: {
            return new DefaultShip();
         }
      } 
   }
}
