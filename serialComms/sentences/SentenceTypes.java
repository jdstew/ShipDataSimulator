/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: SentenceTypes.java
 * Created: 2005-01-01, 12:01:01
 */
package serialComms.sentences;

import serialComms.sentences.specific.*;
/**
 * This class provides a translation capability from recieved talker and
 * sentence formatter values, and the functionality to determine if those
 * sentences are supported by this application.
 *
 * @author Jeff Stewart
 * @version 1.1.0.0, 2005-02-02
 */
public class SentenceTypes {
   /** Minimum sentence length is 11 characters. */
   public static final int MIN_SENTENCE_LENGTH = 11;
   /** Maximum sentence length is 82 characters. */
   public static final int MAX_SENTENCE_LENGTH = 82;
   
   /** NMEA version 1.5 */
   public static final int NMEA_VERSION_1PT50 = 150;
   /** NMEA version 2.00 */
   public static final int NMEA_VERSION_2PT00 = 200;
   /** NMEA version 2.01 */
   public static final int NMEA_VERSION_2PT01 = 201;
   /** NMEA version 2.10 */
   public static final int NMEA_VERSION_2PT10 = 210;
   /** NMEA version 2.20 */
   public static final int NMEA_VERSION_2PT20 = 220;
   /** NMEA version 2.30 */
   public static final int NMEA_VERSION_2PT30 = 230;
   /** NMEA version 3.00 */
   public static final int NMEA_VERSION_3PT00 = 300;
   /** NMEA version 3.01 */
   public static final int NMEA_VERSION_3PT01 = 301; 
   /** NMEA default version is 3.01 */
   public static final int NMEA_DEFAULT_VERSION = NMEA_VERSION_3PT01;
   
   /** Heading/track controller (autopilot), general [AG] */
   public static final int TALKER_AG = 0x4147;
   /** Heading/track controller (autopilot), magnetic [AP] */
   public static final int TALKER_AP = 0x4150;
   /** Automatic identification system [AI] */
   public static final int TALKER_AI = 0x4149;
   /** Communications, digital selective calling (DSC) [CD] */
   public static final int TALKER_CD = 0x4344;
   /** Communications, data receiver [CR] */
   public static final int TALKER_CR = 0x4352;
   /** Communications, satellite [CS] */
   public static final int TALKER_CS = 0x4353;
   /** Communications, radio-telephone (MF/HF) [CT] */
   public static final int TALKER_CT = 0x4354;
   /** Communications, radio-telephone (VHF) [CV] */
   public static final int TALKER_CV = 0x4356;
   /** Communications, scanning receiver [CX] */
   public static final int TALKER_CX = 0x4358;
   /** DECCA navigator [DE] */
   public static final int TALKER_DE = 0x4445;
   /** Direction finder [DF] */
   public static final int TALKER_DF = 0x4446;
   /** Electronic chart systems (ECS) [EC] */
   public static final int TALKER_EC = 0x4543;
   /** Electronic chart display and information system (ECDIS)  [EI] */
   public static final int TALKER_EI = 0x4549;
   /** Emergency position indicating radio beacon (EPIRB) [EP] */
   public static final int TALKER_EP = 0x4550;
   /** Engine room monitoring systems [ER] */
   public static final int TALKER_ER = 0x4552;
   /** Global positioning system (GPS) [GP] */
   public static final int TALKER_GP = 0x4750;
   /** GLONASS receiver  [GL] */
   public static final int TALKER_GL = 0x474C;
   /** Global navigation satellite system (GNSS) [GN] */
   public static final int TALKER_GN = 0x474E;
   /** Heading sensors, compass, magnetic  [HC] */
   public static final int TALKER_HC = 0x4843;
   /** Heading sensors, gyro, north seeking [HE] */
   public static final int TALKER_HE = 0x4845;
   /** Heading sensors, gyro, non-north seeking [HN] */
   public static final int TALKER_HN = 0x484E;
   /** Integrated instrumentation [II] */
   public static final int TALKER_II = 0x4949;
   /** Integrated navigation [IN] */
   public static final int TALKER_IN = 0x494E;
   /** LORAN, LORAN-C [LC] */
   public static final int TALKER_LC = 0x4C43;
   /** Proprietary sentence talker id. */
   public static final int TALKER_P = 0x50;
   /** Query sentence talker id. */
   public static final int TALKER_Q = 0x51;
   /** Radar and/or radar plotting [RA] */
   public static final int TALKER_RA = 0x5241;
   /** Sounder, depth [SD] */
   public static final int TALKER_SD = 0x5344;
   /** Electronic positioning system, other/general [SN] */
   public static final int TALKER_SN = 0x534E;
   /** Sounder, scanning [SS] */
   public static final int TALKER_SS = 0x5353;
   /** Turn rate indicator [TI] */
   public static final int TALKER_TI = 0x5449;
   /** Velocity sensors, Doppler, other/general [VD] */
   public static final int TALKER_VD = 0x5644;
   /** Velocity sensors, speed log, water, magnetic [VM] */
   public static final int TALKER_VM = 0x564D;
   /** Velocity sensors, speed log, water, mechanical [VW] */
   public static final int TALKER_VW = 0x5657;
   /** Voyage data recorder [VR] */
   public static final int TALKER_VR = 0x5652;
   /** Transducer [YX] */
   public static final int TALKER_YX = 0x5958;
   /**Timekeepers, time/date, atomic clock [ZA]  */
   public static final int TALKER_ZA = 0x5A41;
   /** Timekeepers, time/date, chronometer [ZC] */
   public static final int TALKER_ZC = 0x5A43;
   /** Timekeepers, time/date, quartz [ZQ] */
   public static final int TALKER_ZQ = 0x5A51;
   /** Timekeepers, time/date, radio update [ZV] */
   public static final int TALKER_ZV = 0x5A56;
   /** Weather instruments [WI] */
   public static final int TALKER_WI = 0x5749;
   
   /** Waypoint arrival alarm [AAM] */
   public static final int FORMATTER_AAM = 0x41414D;
   /** Acknowledgement alarm [ACK] */
   public static final int FORMATTER_ACK = 0x41434B;
   /** GPS almanac data [ALM] */
   public static final int FORMATTER_ALM = 0x414C4D;
   /** Set alarm state [ALR] */
   public static final int FORMATTER_ALR = 0x414C52;
   /** Heading/track controller (Autopilot) sentence B [APB] */
   public static final int FORMATTER_APB = 0x415042;
   /** Optical Sighting System (OSS) control for BBG's DCU [BEC] */
   public static final int FORMATTER_BBG = 0x424247;
   /** Bearing and distance to waypoint, dead reckoning [BEC] */
   public static final int FORMATTER_BEC = 0x424543;
   /** Bearing, origin to destination [BOD] */
   public static final int FORMATTER_BOD = 0x424F44;
   /** Bearing and distance to waypoint [BWC] */
   public static final int FORMATTER_BWC = 0x425743;
   /** Bearing and distance to waypoint, rhumb line [BWR] */
   public static final int FORMATTER_BWR = 0x425752;
   /** Bearing, waypoint to waypoint [BWW] */
   public static final int FORMATTER_BWW = 0x425757;
   /** Depth below transducer [DBT] */
   public static final int FORMATTER_DBT = 0x444254;
   /** DECCA position [DCN] */
   public static final int FORMATTER_DCN = 0x44434E;
   /** Depth [DPT] */
   public static final int FORMATTER_DPT = 0x445054;
   /** Digital selective calling information [DSC] */
   public static final int FORMATTER_DSC = 0x445343;
   /** Expanded digital selective calling [DSE] */
   public static final int FORMATTER_DSE = 0x445345;
   /** DSC transponder initialise [DSI] */
   public static final int FORMATTER_DSI = 0x445349;
   /** DSC transponder response [DSR] */
   public static final int FORMATTER_DSR = 0x445352;
   /** Datum reference [DTM] */
   public static final int FORMATTER_DTM = 0x44544D;
   /** Frequency set information [FSI] */
   public static final int FORMATTER_FSI = 0x465349;
   /** GNSS Satellite fault detection [GBS] */
   public static final int FORMATTER_GBS = 0x474253;
   /** Global positioning system fix data [GGA] */
   public static final int FORMATTER_GGA = 0x474741;
   /** Geographic position, LORAN-C [GLC] */
   public static final int FORMATTER_GLC = 0x474C43;
   /** Geographic position, latitude/intitude [GLL] */
   public static final int FORMATTER_GLL = 0x474C4C;
   /** GNSS fix data [GNS] */
   public static final int FORMATTER_GNS = 0x474E53;
   /** GNSS range residuals [GRS] */
   public static final int FORMATTER_GRS = 0x475253;
   /** GNSS DOP and active satellites [GSA] */
   public static final int FORMATTER_GSA = 0x475341;
   /** GNSS pseudorange error statistics [GST] */
   public static final int FORMATTER_GST = 0x475354;
   /** GNSS satellites in view [GSV] */
   public static final int FORMATTER_GSV = 0x475356;
   /** Heading, deviation and variation [HDG] */
   public static final int FORMATTER_HDG = 0x484447;
   /** Heading, true [HDT] */
   public static final int FORMATTER_HDT = 0x484454;
   /** Heading monitor – receive [HMR] */
   public static final int FORMATTER_HMR = 0x484D52;
   /** Heading monitor – set [HMS] */
   public static final int FORMATTER_HMS = 0x484D53;
   /** Heading steering command [HSC] */
   public static final int FORMATTER_HSC = 0x485343;
   /** Heading/track control command [HTC] */
   public static final int FORMATTER_HTC = 0x485443;
   /** Heading/track control data [HTD] */
   public static final int FORMATTER_HTD = 0x485444;
   /** LORAN-C signal data [LCD] */
   public static final int FORMATTER_LCD = 0x4C4344;
   /** Glonass almanac data [MLA] */
   public static final int FORMATTER_MLA = 0x4D4C41;
   /** MSK receiver interface [MSK] */
   public static final int FORMATTER_MSK = 0x4D534B;
   /** MSK receiver signal status [MSS] */
   public static final int FORMATTER_MSS = 0x4D5353;
   /** Water temperature [MTW] */
   public static final int FORMATTER_MTW = 0x4D5457;
   /** Wind direction and speed [MWD] */
   public static final int FORMATTER_MWD = 0x4D5744;
   /** Wind speed and angle [MWV] */
   public static final int FORMATTER_MWV = 0x4D5756;
   /** Own ship data [OSD] */
   public static final int FORMATTER_OSD = 0x4F5344;
   /** Recommended minimum specific LORAN-C data [RMA] */
   public static final int FORMATTER_RMA = 0x524D41;
   /** Recommended minimum navigation information [RMB] */
   public static final int FORMATTER_RMB = 0x524D42;
   /** Recommended minimum specific GNSS data [RMC] */
   public static final int FORMATTER_RMC = 0x524D43;
   /** Rate of turn [ROT] */
   public static final int FORMATTER_ROT = 0x524F54;
   /** Revolutions [RPM] */
   public static final int FORMATTER_RPM = 0x52504D;
   /** Rudder sensor angle [RSA] */
   public static final int FORMATTER_RSA = 0x525341;
   /** Radar system data [RSD] */
   public static final int FORMATTER_RSD = 0x525344;
   /** Routes [RTE] */
   public static final int FORMATTER_RTE = 0x525445;
   /** Scanning frequency information [SFI] */
   public static final int FORMATTER_SFI = 0x534649;
   /** Multiple data ID [STN] */
   public static final int FORMATTER_STN = 0x53544E;
   /** Target label [TLB] */
   public static final int FORMATTER_TLB = 0x544C42;
   /** Target latitude and intitude [TLL] */
   public static final int FORMATTER_TLL = 0x544C4C;
   /** Tracked target message [TTM] */
   public static final int FORMATTER_TTM = 0x54544D;
   /** Text transmission [TXT] */
   public static final int FORMATTER_TXT = 0x545854;
   /** Dual ground/water speed [VBW] */
   public static final int FORMATTER_VBW = 0x564257;
   /** Set and drift [VDR] */
   public static final int FORMATTER_VDR = 0x564452;
   /** Water speed and heading [VHW] */
   public static final int FORMATTER_VHW = 0x564857;
   /** Distance travelled through the water [VLW] */
   public static final int FORMATTER_VLW = 0x564C57;
   /** Speed, measured parallel to wind [VPW] */
   public static final int FORMATTER_VPW = 0x565057;
   /** Course over ground and ground speed [VTG] */
   public static final int FORMATTER_VTG = 0x565447;
   /** Waypoint closure velocity [WCV] */
   public static final int FORMATTER_WCV = 0x574356;
   /** WNC Distance, waypoint to waypoint [WNC] */
   public static final int FORMATTER_WNC = 0x574E43;
   /** Waypoint location [WPL] */
   public static final int FORMATTER_WPL = 0x57504C;
   /** Transducer measurements [XDR] */
   public static final int FORMATTER_XDR = 0x584452;
   /** Cross-track error, measured [XTE] */
   public static final int FORMATTER_XTE = 0x585445;
   /** Cross-track error, dead reckoning [XTR] */
   public static final int FORMATTER_XTR = 0x585452;
   /** Time and date [ZDA] */
   public static final int FORMATTER_ZDA = 0x5A4441;
   /** Time and distance to variable point [ZDL] */
   public static final int FORMATTER_ZDL = 0x5A444C;
   /** UTC and time from origin waypoint [ZFO] */
   public static final int FORMATTER_ZFO = 0x5A464F;
   /** UTC and time to destination waypoint [ZTG] */
   public static final int FORMATTER_ZTG = 0x5A5447;
   /** Unknown formatter */
   public static final int FORMATTER_XXX = 0x000000;
   /** Free text sentence */
   public static final int FORMATTER_ZZZ = 0xFFFFFF;
   /** NMEA 0180 - Simple autopilot interface */
   public static final int FORMATTER_NMEA0180 = 0x000180;
   /** NMEA 0182 - Complex LORAN-C autopilot interface */
   public static final int FORMATTER_NMEA0182 = 0x000182;
   /** RayNav750 - Color plotter and remote interface */
   public static final int FORMATTER_RAYNAV750 = 0x000750;
   
   
   /** 
    * Provides a list of supported NMEA versions.  This is a comprehensive list. Some
    * sentence types may not be supported by this application.
    *
    * @return an array of NMEA versions (from the static numeric value list)
    */
   public static int [] getKnownSentenceVersions () {
      int [] versions = {
         NMEA_VERSION_3PT01,
         NMEA_VERSION_3PT00,
         NMEA_VERSION_2PT30,
         NMEA_VERSION_2PT20,
         NMEA_VERSION_2PT10,
         NMEA_VERSION_2PT01,
         NMEA_VERSION_2PT00,
         NMEA_VERSION_1PT50,
      };
      return versions;
   }
   
   /**
    * Get the sentence version descriptive name.
    *
    * @param version Sentence version reference from this class.
    * @return Text description of that version.
    */   
   public static String getSentenceVersionName (int version) {
      switch (version) {
         case NMEA_VERSION_1PT50: {
            return "NMEA version 1.5 (Dec '87)";
         }
         case NMEA_VERSION_2PT00: {
            return "NMEA version 2.0 (Jan '92)";
         }
         case NMEA_VERSION_2PT01: {
            return "NMEA version 2.01 (Aug '94)";
         }
         case NMEA_VERSION_2PT10: {
            return "NMEA version 2.1 (Oct '95)";
         }
         case NMEA_VERSION_2PT20: {
            return "NMEA version 2.2 (Jan '97)";
         }
         case NMEA_VERSION_2PT30: { // equivalent to IEC_61162_1
            return "NMEA version 2.3 (Mar '98)";
         }
         case NMEA_VERSION_3PT00: {
            return "NMEA version 3.0 (Jul '00)";
         }
         case NMEA_VERSION_3PT01: {
            return "NMEA version 3.01 (Jan '02)";
         }
      }
      return null;
   }
   
   /** 
    * Provides a list of known talker IDs.  This is a comprehensive list. Some
    * sentence talker IDs may not be supported by this application.
    *
    * @return an array of talker IDs (from the static numeric value list)
    */
   public static int [] getKnownTalkerIDs () {
      int [] talkerIDs = {
         TALKER_AG,
         TALKER_AP,
         TALKER_AI,
         TALKER_CD,
         TALKER_CR,
         TALKER_CS,
         TALKER_CT,
         TALKER_CV,
         TALKER_CX,
         TALKER_DE,
         TALKER_DF,
         TALKER_EC,
         TALKER_EI,
         TALKER_EP,
         TALKER_ER,
         TALKER_GP,
         TALKER_GL,
         TALKER_GN,
         TALKER_HC,
         TALKER_HE,
         TALKER_HN,
         TALKER_II,
         TALKER_IN,
         TALKER_LC,
         TALKER_P,
         TALKER_Q,
         TALKER_RA,
         TALKER_SD,
         TALKER_SN,
         TALKER_SS,
         TALKER_TI,
         TALKER_VD,
         TALKER_VM,
         TALKER_VW,
         TALKER_VR,
         TALKER_YX,
         TALKER_ZA,
         TALKER_ZC,
         TALKER_ZQ,
         TALKER_ZV,
         TALKER_WI
      };
      return talkerIDs;
   }
   
   /**
    * Provides a string description of a talker ID.
    * 
    * @param talkerID the numeric value of a talker ID
    * @return the description of the talker ID
    */
   public static String getTalkerIDName (int talkerID) {
      switch (talkerID) {
         case TALKER_AG: {
            return "Heading/track controller (autopilot), general [AG]";
         }
         case TALKER_AP: {
            return "Heading/track controller (autopilot), magnetic [AP]";
         }
         case TALKER_AI: {
            return "Automatic identification system [AI]";
         }
         case TALKER_CD: {
            return "Communications, digital selective calling (DSC) [CD]";
         }
         case TALKER_CR: {
            return "Communications, data receiver [CR]";
         }
         case TALKER_CS: {
            return "Communications, satellite [CS]";
         }
         case TALKER_CT: {
            return "Communications, radio-telephone (MF/HF) [CT]";
         }
         case TALKER_CV: {
            return "Communications, radio-telephone (VHF) [CV]";
         }
         case TALKER_CX: {
            return "Communications, scanning receiver [CX]";
         }
         case TALKER_DE: {
            return "DECCA navigator [DE]";
         }
         case TALKER_DF: {
            return "Direction finder [DF]";
         }
         case TALKER_EC: {
            return "Electronic chart systems (ECS) [EC]";
         }
         case TALKER_EI: {
            return "Electronic chart display and information system (ECDIS)  [EI]";
         }
         case TALKER_EP: {
            return "Emergency position indicating radio beacon (EPIRB) [EP]";
         }
         case TALKER_ER: {
            return "Engine room monitoring systems [ER]";
         }
         case TALKER_GP: {
            return "Global positioning system (GPS) [GP]";
         }
         case TALKER_GL: {
            return "GLONASS receiver  [GL]";
         }
         case TALKER_GN: {
            return "Global navigation satellite system (GNSS) [GN]";
         }
         case TALKER_HC: {
            return "Heading sensors, compass, magnetic  [HC]";
         }
         case TALKER_HE: {
            return "Heading sensors, gyro, north seeking [HE]";
         }
         case TALKER_HN: {
            return "Heading sensors, gyro, non-north seeking [HN]";
         }
         case TALKER_II: {
            return "Integrated instrumentation [II]";
         }
         case TALKER_IN: {
            return "Integrated navigation [IN]";
         }
         case TALKER_LC: {
            return "LORAN, LORAN-C [LC]";
         }
         case TALKER_P: {
            return "Proprietory message source [P]";
         }
         case TALKER_Q: {
            return "Query message type [Q]";
         }         case TALKER_RA: {
            return "Radar and/or radar plotting [RA]";
         }
         case TALKER_SD: {
            return "Sounder, depth [SD]";
         }
         case TALKER_SN: {
            return "Electronic positioning system, other/general [SN]";
         }
         case TALKER_SS: {
            return "Sounder, scanning [SS]";
         }
         case TALKER_TI: {
            return "Turn rate indicator [TI]";
         }
         case TALKER_VD: {
            return "Velocity sensors, Doppler, other/general [VD]";
         }
         case TALKER_VM: {
            return "Velocity sensors, speed log, water, magnetic [VM]";
         }
         case TALKER_VW: {
            return "Velocity sensors, speed log, water, mechanical [VW]";
         }
         case TALKER_VR: {
            return "Voyage data recorder [VR]";
         }
         case TALKER_YX: {
            return "Transducer [YX]";
         }
         case TALKER_ZA: {
            return "Timekeepers, time/date, atomic clock [ZA]";
         }
         case TALKER_ZC: {
            return "Timekeepers, time/date, chronometer [ZC]";
         }
         case TALKER_ZQ: {
            return "Timekeepers, time/date, quartz [ZQ]";
         }
         case TALKER_ZV: {
            return "Timekeepers, time/date, radio update [ZV]";
         }
         case TALKER_WI: {
            return "Weather instruments [WI]";
         } 
      }
      return null;
   }
  
   /** 
    * Provides a list of known formatters.  This is a comprehensive list. Some
    * sentence types may not be supported by this application.
    *
    * @return an array of talker IDs (from the static numeric value list)
    */
   public static int [] getKnownFormatters () {
      int [] formatters = {
         FORMATTER_AAM,
         FORMATTER_ACK,
         FORMATTER_ALM,
         FORMATTER_ALR,
         FORMATTER_APB,
         FORMATTER_BBG,
         FORMATTER_BEC,
         FORMATTER_BOD,
         FORMATTER_BWC,
         FORMATTER_BWR,
         FORMATTER_BWW,
         FORMATTER_DBT,
         FORMATTER_DCN,
         FORMATTER_DPT,
         FORMATTER_DSC,
         FORMATTER_DSE,
         FORMATTER_DSI,
         FORMATTER_DSR,
         FORMATTER_DTM,
         FORMATTER_FSI,
         FORMATTER_GBS,
         FORMATTER_GGA,
         FORMATTER_GLC,
         FORMATTER_GLL,
         FORMATTER_GNS,
         FORMATTER_GRS,
         FORMATTER_GSA,
         FORMATTER_GST,
         FORMATTER_GSV,
         FORMATTER_HDG,
         FORMATTER_HDT,
         FORMATTER_HMR,
         FORMATTER_HMS,
         FORMATTER_HSC,
         FORMATTER_HTC,
         FORMATTER_HTD,
         FORMATTER_LCD,
         FORMATTER_MLA,
         FORMATTER_MSK,
         FORMATTER_MSS,
         FORMATTER_MTW,
         FORMATTER_MWD,
         FORMATTER_MWV,
         FORMATTER_OSD,
         FORMATTER_RMA,
         FORMATTER_RMB,
         FORMATTER_RMC,
         FORMATTER_ROT,
         FORMATTER_RPM,
         FORMATTER_RSA,
         FORMATTER_RSD,
         FORMATTER_RTE,
         FORMATTER_SFI,
         FORMATTER_STN,
         FORMATTER_TLB,
         FORMATTER_TLL,
         FORMATTER_TTM,
         FORMATTER_TXT,
         FORMATTER_VBW,
         FORMATTER_VDR,
         FORMATTER_VHW,
         FORMATTER_VLW,
         FORMATTER_VPW,
         FORMATTER_VTG,
         FORMATTER_WCV,
         FORMATTER_WNC,
         FORMATTER_WPL,
         FORMATTER_XDR,
         FORMATTER_XTE,
         FORMATTER_XTR,
         FORMATTER_ZDA,
         FORMATTER_ZDL,
         FORMATTER_ZFO,
         FORMATTER_ZTG,
         FORMATTER_ZZZ,
         FORMATTER_NMEA0180,
         FORMATTER_NMEA0182,
         FORMATTER_RAYNAV750,
      };
      return formatters;
   }
   
   /**
    * Provides a string description of a sentence formatter.
    * 
    * @param sentenceFormatter the numeric value of a sentence formatter.
    * @return the description of the sentence formatter
    */
   public static String getFormatterIDName (int sentenceFormatter) {
      switch (sentenceFormatter) {
         case FORMATTER_AAM: {
            return "Waypoint arrival alarm [AAM]";
         }
         case FORMATTER_ACK: {
            return "Acknowledgement alarm [ACK]";
         }
         case FORMATTER_ALM: {
            return "GPS almanac data [ALM]";
         }
         case FORMATTER_ALR: {
            return "Set alarm state [ALR]";
         }
         case FORMATTER_APB: {
            return "Heading/track controller (Autopilot) sentence B [APB]";
         }
         case FORMATTER_BBG: {
            return "Optical Sighting System (OSS) control for BBG's DCU [BBG]";
         }
         case FORMATTER_BEC: {
            return "Bearing and distance to waypoint, dead reckoning [BEC]";
         }
         case FORMATTER_BOD: {
            return "Bearing, origin to destination [BOD]";
         }
         case FORMATTER_BWC: {
            return "Bearing and distance to waypoint [BWC]";
         }
         case FORMATTER_BWR: {
            return "Bearing and distance to waypoint, rhumb line [BWR]";
         }
         case FORMATTER_BWW: {
            return "Bearing, waypoint to waypoint [BWW]";
         }
         case FORMATTER_DBT: {
            return "Depth below transducer [DBT]";
         }
         case FORMATTER_DCN: {
            return "DECCA position [DCN]";
         }
         case FORMATTER_DPT: {
            return "Depth [DPT]";
         }
         case FORMATTER_DSC: {
            return "Digital selective calling information [DSC]";
         }
         case FORMATTER_DSE: {
            return "Expanded digital selective calling [DSE]";
         }
         case FORMATTER_DSI: {
            return "DSC transponder initialise [DSI]";
         }
         case FORMATTER_DSR: {
            return "DSC transponder response [DSR]";
         }
         case FORMATTER_DTM: {
            return "Datum reference [DTM]";
         }
         case FORMATTER_FSI: {
            return "Frequency set information [FSI]";
         }
         case FORMATTER_GBS: {
            return "GNSS Satellite fault detection [GBS]";
         }
         case FORMATTER_GGA: {
            return "Global positioning system fix data [GGA]";
         }
         case FORMATTER_GLC: {
            return "Geographic position, LORAN-C [GLC]";
         }
         case FORMATTER_GLL: {
            return "Geographic position, latitude/intitude [GLL]";
         }
         case FORMATTER_GNS: {
            return "GNSS fix data [GNS]";
         }
         case FORMATTER_GRS: {
            return "GNSS range residuals [GRS]";
         }
         case FORMATTER_GSA: {
            return "GNSS DOP and active satellites [GSA]";
         }
         case FORMATTER_GST: {
            return "GNSS pseudorange error statistics [GST]";
         }
         case FORMATTER_GSV: {
            return "GNSS satellites in view [GSV]";
         }
         case FORMATTER_HDG: {
            return "Heading, deviation and variation [HDG]";
         }
         case FORMATTER_HDT: {
            return "Heading, true [HDT]";
         }
         case FORMATTER_HMR: {
            return "Heading monitor – receive [HMR]";
         }
         case FORMATTER_HMS: {
            return "Heading monitor – set [HMS]";
         }
         case FORMATTER_HSC: {
            return "Heading steering command [HSC]";
         }
         case FORMATTER_HTC: {
            return "Heading/track control command [HTC]";
         }
         case FORMATTER_HTD: {
            return "Heading/track control data [HTD]";
         }
         case FORMATTER_LCD: {
            return "LORAN-C signal data [LCD]";
         }
         case FORMATTER_MLA: {
            return "Glonass almanac data [MLA]";
         }
         case FORMATTER_MSK: {
            return "MSK receiver interface [MSK]";
         }
         case FORMATTER_MSS: {
            return "MSK receiver signal status [MSS]";
         }
         case FORMATTER_MTW: {
            return "Water temperature [MTW]";
         }
         case FORMATTER_MWD: {
            return "Wind direction and speed [MWD]";
         }
         case FORMATTER_MWV: {
            return "Wind speed and angle [MWV]";
         }
         case FORMATTER_OSD: {
            return "Own ship data [OSD]";
         }
         case FORMATTER_RMA: {
            return "Recommended minimum specific LORAN-C data [RMA]";
         }
         case FORMATTER_RMB: {
            return "Recommended minimum navigation information [RMB]";
         }
         case FORMATTER_RMC: {
            return "Recommended minimum specific GNSS data [RMC]";
         }
         case FORMATTER_ROT: {
            return "Rate of turn [ROT]";
         }
         case FORMATTER_RPM: {
            return "Revolutions [RPM]";
         }
         case FORMATTER_RSA: {
            return "Rudder sensor angle [RSA]";
         }
         case FORMATTER_RSD: {
            return "Radar system data [RSD]";
         }
         case FORMATTER_RTE: {
            return "Routes [RTE]";
         }
         case FORMATTER_SFI: {
            return "Scanning frequency information [SFI]";
         }
         case FORMATTER_STN: {
            return "Multiple data ID [STN]";
         }
         case FORMATTER_TLB: {
            return "Target label [TLB]";
         }
         case FORMATTER_TLL: {
            return "Target latitude and intitude [TLL]";
         }
         case FORMATTER_TTM: {
            return "Tracked target message [TTM]";
         }
         case FORMATTER_TXT: {
            return "Text transmission [TXT]";
         }
         case FORMATTER_VBW: {
            return "Dual ground/water speed [VBW]";
         }
         case FORMATTER_VDR: {
            return "Set and drift [VDR]";
         }
         case FORMATTER_VHW: {
            return "Water speed and heading [VHW]";
         }
         case FORMATTER_VLW: {
            return "Distance travelled through the water [VLW]";
         }
         case FORMATTER_VPW: {
            return "Speed, measured parallel to wind [VPW]";
         }
         case FORMATTER_VTG: {
            return "Course over ground and ground speed [VTG]";
         }
         case FORMATTER_WCV: {
            return "Waypoint closure velocity [WCV]";
         }
         case FORMATTER_WNC: {
            return "WNC Distance, waypoint to waypoint [WNC]";
         }
         case FORMATTER_WPL: {
            return "Waypoint location [WPL]";
         }
         case FORMATTER_XDR: {
            return "Transducer measurements [XDR]";
         }
         case FORMATTER_XTE: {
            return "Cross-track error, measured [XTE]";
         }
         case FORMATTER_XTR: {
            return "Cross-track error, dead reckoning [XTR]";
         }
         case FORMATTER_ZDA: {
            return "Time and date [ZDA]";
         }
         case FORMATTER_ZDL: {
            return "Time and distance to variable point [ZDL]";
         }
         case FORMATTER_ZFO: {
            return "UTC and time from origin waypoint [ZFO]";
         }
         case FORMATTER_ZTG: {
            return "UTC and time to destination waypoint [ZTG]";
         }
         case FORMATTER_ZZZ: {
            return "Free text sentence (using misc manual data)";
         }
         case FORMATTER_NMEA0180: {
            return "NMEA 0180 - Simple autopilot interface";
         }
         case FORMATTER_NMEA0182: {
            return "NMEA 0182 - Complex LORAN-C autopilot interface";
         }
         case FORMATTER_RAYNAV750: {
            return "RayNav750 - Color plotter and remote interface";
         }
      }
      return "Unrecognized";  
   }
   
   /**
    * Determines whether a particular sentence formatte is supported by this
    * application.
    *
    * @param sentenceFormatter the static class value of a particular sentence formatter
    * @return An extended AbstractSentence object.
    */
   public static AbstractSentence getSentenceObject (int sentenceFormatter) {
      switch (sentenceFormatter) {
         case FORMATTER_AAM: {
            return new DefaultSentence();
         }
         case FORMATTER_ACK: {
            return new DefaultSentence();
         }
         case FORMATTER_ALM: {
            return new DefaultSentence();
         }
         case FORMATTER_ALR: {
            return new DefaultSentence();
         }
         case FORMATTER_APB: {
            return new DefaultSentence();
         }
         case FORMATTER_BBG: {
            return new USCG_DCU_BBG();
         }
         case FORMATTER_BEC: {
            return new DefaultSentence();
         }
         case FORMATTER_BOD: {
            return new DefaultSentence();
         }
         case FORMATTER_BWC: {
            return new DefaultSentence();
         }
         case FORMATTER_BWR: {
            return new DefaultSentence();
         }
         case FORMATTER_BWW: {
            return new DefaultSentence();
         }
         case FORMATTER_DBT: {
            return new DepthDBT();
         }
         case FORMATTER_DCN: {
            return new DefaultSentence();
         }
         case FORMATTER_DPT: {
            return new DepthDPT();
         }
         case FORMATTER_DSC: {
            return new DefaultSentence();
         }
         case FORMATTER_DSE: {
            return new DefaultSentence();
         }
         case FORMATTER_DSI: {
            return new DefaultSentence();
         }
         case FORMATTER_DSR: {
            return new DefaultSentence();
         }
         case FORMATTER_DTM: {
            return new DatumDTM();
         }
         case FORMATTER_FSI: {
            return new DefaultSentence();
         }
         case FORMATTER_GBS: {
            return new DefaultSentence();
         }
         case FORMATTER_GGA: {
            return new PositionGGA();
         }
         case FORMATTER_GLC: {
            return new LoranTDsGLC();
         }
         case FORMATTER_GLL: {
            return new PositionGLL();
         }
         case FORMATTER_GNS: {
            return new DefaultSentence();
         }
         case FORMATTER_GRS: {
            return new DefaultSentence();
         }
         case FORMATTER_GSA: {
            return new DefaultSentence();
         }
         case FORMATTER_GST: {
            return new DefaultSentence();
         }
         case FORMATTER_GSV: {
            return new DefaultSentence();
         }
         case FORMATTER_HDG: {
            return new HeadingHDG();
         }
         case FORMATTER_HDT: {
            return new HeadingHDT();
         }
         case FORMATTER_HMR: {
            return new DefaultSentence();
         }
         case FORMATTER_HMS: {
            return new DefaultSentence();
         }
         case FORMATTER_HSC: {
            return new DefaultSentence();
         }
         case FORMATTER_HTC: {
            return new DefaultSentence();
         }
         case FORMATTER_HTD: {
            return new DefaultSentence();
         }
         case FORMATTER_LCD: {
            return new LoranSignalLCD();
         }
         case FORMATTER_MLA: {
            return new DefaultSentence();
         }
         case FORMATTER_MSK: {
            return new DefaultSentence();
         }
         case FORMATTER_MSS: {
            return new DefaultSentence();
         }
         case FORMATTER_MTW: {
            return new DefaultSentence();
         }
         case FORMATTER_MWD: {
            return new DefaultSentence();
         }
         case FORMATTER_MWV: {
            return new WindMWV();
         }
         case FORMATTER_OSD: {
            return new DefaultSentence();
         }
         case FORMATTER_RMA: {
            return new DefaultSentence();
         }
         case FORMATTER_RMB: {
            return new DefaultSentence();
         }
         case FORMATTER_RMC: {
            return new DefaultSentence();
         }
         case FORMATTER_ROT: {
            return new RateOfTurnROT();
         }
         case FORMATTER_RPM: {
            return new DefaultSentence();
         }
         case FORMATTER_RSA: {
            return new DefaultSentence();
         }
         case FORMATTER_RSD: {
            return new DefaultSentence();
         }
         case FORMATTER_RTE: {
            return new RoutesRTE();
         }
         case FORMATTER_SFI: {
            return new DefaultSentence();
         }
         case FORMATTER_STN: {
            return new DefaultSentence();
         }
         case FORMATTER_TLB: {
            return new DefaultSentence();
         }
         case FORMATTER_TLL: {
            return new DefaultSentence();
         }
         case FORMATTER_TTM: {
            return new TargetTTM();
         }
         case FORMATTER_TXT: {
            return new DefaultSentence();
         }
         case FORMATTER_VBW: {
            return new SpeedVBW();
         }
         case FORMATTER_VDR: {
            return new SetDriftVDR();
         }
         case FORMATTER_VHW: {
            return new HeadingSpeedVHW();
         }
         case FORMATTER_VLW: {
            return new DefaultSentence();
         }
         case FORMATTER_VPW: {
            return new DefaultSentence();
         }
         case FORMATTER_VTG: {
            return new COGSOGVTG();
         }
         case FORMATTER_WCV: {
            return new DefaultSentence();
         }
         case FORMATTER_WNC: {
            return new DefaultSentence();
         }
         case FORMATTER_WPL: {
            return new WaypointWPL();
         }
         case FORMATTER_XDR: {
            return new DefaultSentence();
         }
         case FORMATTER_XTE: {
            return new DefaultSentence();
         }
         case FORMATTER_XTR: {
            return new DefaultSentence();
         }
         case FORMATTER_ZDA: {
            return new TimeDateZDA();
         }
         case FORMATTER_ZDL: {
            return new DefaultSentence();
         }
         case FORMATTER_ZFO: {
            return new DefaultSentence();
         }
         case FORMATTER_ZTG: {
            return new DefaultSentence();
         }
         case FORMATTER_ZZZ: {
            return new FreeTextSentence();
         }
         case FORMATTER_NMEA0180: {
            return new SimpleNMEA0180();
         }
         case FORMATTER_NMEA0182: {
            return new ComplexNMEA0182();
         }
         case FORMATTER_RAYNAV750: {
            return new RayNav750();
         }
         default: {
            return new DefaultSentence();
         }
      }
   }
   

   /**
    * Determines whether a particular sentence formatte is supported by this
    * application.
    *
    * @param sentenceFormatter the static class value of a particular sentence formatter
    * @return An extended AbstractSentence object.
    */
   public static boolean isSupported (int sentenceFormatter) {
      switch (sentenceFormatter) {
         case FORMATTER_AAM: {
            return false;
         }
         case FORMATTER_ACK: {
            return false;
         }
         case FORMATTER_ALM: {
            return false;
         }
         case FORMATTER_ALR: {
            return false;
         }
         case FORMATTER_APB: {
            return false;
         }
         case FORMATTER_BBG: {
            return true;
         }
         case FORMATTER_BEC: {
            return false;
         }
         case FORMATTER_BOD: {
            return false;
         }
         case FORMATTER_BWC: {
            return false;
         }
         case FORMATTER_BWR: {
            return false;
         }
         case FORMATTER_BWW: {
            return false;
         }
         case FORMATTER_DBT: {
            return true;
         }
         case FORMATTER_DCN: {
            return false;
         }
         case FORMATTER_DPT: {
            return true;
         }
         case FORMATTER_DSC: {
            return false;
         }
         case FORMATTER_DSE: {
            return false;
         }
         case FORMATTER_DSI: {
            return false;
         }
         case FORMATTER_DSR: {
            return false;
         }
         case FORMATTER_DTM: {
            return true;
         }
         case FORMATTER_FSI: {
            return false;
         }
         case FORMATTER_GBS: {
            return false;
         }
         case FORMATTER_GGA: {
            return true;
         }
         case FORMATTER_GLC: {
            return true;
         }
         case FORMATTER_GLL: {
            return true;
         }
         case FORMATTER_GNS: {
            return false;
         }
         case FORMATTER_GRS: {
            return false;
         }
         case FORMATTER_GSA: {
            return false;
         }
         case FORMATTER_GST: {
            return false;
         }
         case FORMATTER_GSV: {
            return false;
         }
         case FORMATTER_HDG: {
            return true;
         }
         case FORMATTER_HDT: {
            return true;
         }
         case FORMATTER_HMR: {
            return false;
         }
         case FORMATTER_HMS: {
            return false;
         }
         case FORMATTER_HSC: {
            return false;
         }
         case FORMATTER_HTC: {
            return false;
         }
         case FORMATTER_HTD: {
            return false;
         }
         case FORMATTER_LCD: {
            return true;
         }
         case FORMATTER_MLA: {
            return false;
         }
         case FORMATTER_MSK: {
            return false;
         }
         case FORMATTER_MSS: {
            return false;
         }
         case FORMATTER_MTW: {
            return false;
         }
         case FORMATTER_MWD: {
            return false;
         }
         case FORMATTER_MWV: {
            return true;
         }
         case FORMATTER_OSD: {
            return false;
         }
         case FORMATTER_RMA: {
            return false;
         }
         case FORMATTER_RMB: {
            return false;
         }
         case FORMATTER_RMC: {
            return false;
         }
         case FORMATTER_ROT: {
            return true;
         }
         case FORMATTER_RPM: {
            return false;
         }
         case FORMATTER_RSA: {
            return false;
         }
         case FORMATTER_RSD: {
            return false;
         }
         case FORMATTER_RTE: {
            return true;
         }
         case FORMATTER_SFI: {
            return false;
         }
         case FORMATTER_STN: {
            return false;
         }
         case FORMATTER_TLB: {
            return false;
         }
         case FORMATTER_TLL: {
            return false;
         }
         case FORMATTER_TTM: {
            return true;
         }
         case FORMATTER_TXT: {
            return false;
         }
         case FORMATTER_VBW: {
            return true;
         }
         case FORMATTER_VDR: {
            return true;
         }
         case FORMATTER_VHW: {
            return true;
         }
         case FORMATTER_VLW: {
            return false;
         }
         case FORMATTER_VPW: {
            return false;
         }
         case FORMATTER_VTG: {
            return true;
         }
         case FORMATTER_WCV: {
            return false;
         }
         case FORMATTER_WNC: {
            return false;
         }
         case FORMATTER_WPL: {
            return true;
         }
         case FORMATTER_XDR: {
            return false;
         }
         case FORMATTER_XTE: {
            return false;
         }
         case FORMATTER_XTR: {
            return false;
         }
         case FORMATTER_ZDA: {
            return true;
         }
         case FORMATTER_ZDL: {
            return false;
         }
         case FORMATTER_ZFO: {
            return false;
         }
         case FORMATTER_ZTG: {
            return false;
         }
         case FORMATTER_ZZZ: {
            return true;
         }
         case FORMATTER_NMEA0180: {
            return true;
         }
         case FORMATTER_NMEA0182: {
            return true;
         }
         case FORMATTER_RAYNAV750: {
            return true;
         }
         default: {
            return false;
         }
      }
   }
}
/* 
 * Version history:
 *
 * 1.1.0.0 - Added NMEA 0180 and 0182 sentence formatters.
 * 1.2.0.0 - Added RayNav750 formatter.
 */
