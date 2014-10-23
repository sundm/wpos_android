package com.zc.app.sebc.pboc2;

import com.zc.app.sebc.util.BasicUtil;

//see GB/T 2659-2000 (ISO 3166-1:1997)

public final class CountryCode {

	public final static String COUNTRY_UNKNOWN = "未知";
	
	public final static String COUNTRY_AFG = "AFG";
	public final static String COUNTRY_ALB = "ALB";
	public final static String COUNTRY_DZA = "DZA";
	public final static String COUNTRY_ASM = "ASM";
	public final static String COUNTRY_AND = "AND";
	public final static String COUNTRY_AGO = "AGO";
	public final static String COUNTRY_AIA = "AIA";
	public final static String COUNTRY_ATA = "ATA";
	public final static String COUNTRY_ATG = "ATG";
	public final static String COUNTRY_ARG = "ARG";
	public final static String COUNTRY_ARM = "ARM";
	public final static String COUNTRY_ABW = "ABW";
	public final static String COUNTRY_AUS = "AUS";
	public final static String COUNTRY_AUT = "AUT";
	public final static String COUNTRY_AZE = "AZE";
	public final static String COUNTRY_BHS = "BHS";
	public final static String COUNTRY_BHR = "BHR";
	public final static String COUNTRY_BGD = "BGD";
	public final static String COUNTRY_BRB = "BRB";
	public final static String COUNTRY_BLR = "BLR";
	public final static String COUNTRY_BEL = "BEL";
	public final static String COUNTRY_BLZ = "BLZ";
	public final static String COUNTRY_BEN = "BEN";
	public final static String COUNTRY_BMU = "BMU";
	public final static String COUNTRY_BTN = "BTN";
	public final static String COUNTRY_BOL = "BOL";
	public final static String COUNTRY_BIH = "BIH";
	public final static String COUNTRY_BWA = "BWA";
	public final static String COUNTRY_BVT = "BVT";
	public final static String COUNTRY_BRA = "BRA";
	public final static String COUNTRY_IOT = "IOT";
	public final static String COUNTRY_BRN = "BRN";
	public final static String COUNTRY_BGR = "BGR";
	public final static String COUNTRY_BFA = "BFA";
	public final static String COUNTRY_BDI = "BDI";
	public final static String COUNTRY_KHM = "KHM";
	public final static String COUNTRY_CMR = "CMR";
	public final static String COUNTRY_CAN = "CAN";
	public final static String COUNTRY_CPV = "CPV";
	public final static String COUNTRY_CYM = "CYM";
	public final static String COUNTRY_CAF = "CAF";
	public final static String COUNTRY_TCD = "TCD";
	public final static String COUNTRY_CHL = "CHL";
	public final static String COUNTRY_CHN = "CHN";
	public final static String COUNTRY_HKG = "HKG";
	public final static String COUNTRY_MAC = "MAC";
	public final static String COUNTRY_TWN = "TWN";
	public final static String COUNTRY_CXR = "CXR";
	public final static String COUNTRY_CCK = "CCK";
	public final static String COUNTRY_COL = "COL";
	public final static String COUNTRY_COM = "COM";
	public final static String COUNTRY_COG = "COG";
	public final static String COUNTRY_COD = "COD";
	public final static String COUNTRY_COK = "COK";
	public final static String COUNTRY_CRI = "CRI";
	public final static String COUNTRY_CIV = "CIV";
	public final static String COUNTRY_HRV = "HRV";
	public final static String COUNTRY_CUB = "CUB";
	public final static String COUNTRY_CYP = "CYP";
	public final static String COUNTRY_CZE = "CZE";
	public final static String COUNTRY_DNK = "DNK";
	public final static String COUNTRY_DJI = "DJI";
	public final static String COUNTRY_DMA = "DMA";
	public final static String COUNTRY_DOM = "DOM";
	public final static String COUNTRY_TMP = "TMP";
	public final static String COUNTRY_ECU = "ECU";
	public final static String COUNTRY_EGY = "EGY";
	public final static String COUNTRY_SLV = "SLV";
	public final static String COUNTRY_GNQ = "GNQ";
	public final static String COUNTRY_ERI = "ERI";
	public final static String COUNTRY_EST = "EST";
	public final static String COUNTRY_ETH = "ETH";
	public final static String COUNTRY_FLK = "FLK";
	public final static String COUNTRY_FRO = "FRO";
	public final static String COUNTRY_FJI = "FJI";
	public final static String COUNTRY_FIN = "FIN";
	public final static String COUNTRY_FRA = "FRA";
	public final static String COUNTRY_GUF = "GUF";
	public final static String COUNTRY_PYF = "PYF";
	public final static String COUNTRY_ATF = "ATF";
	public final static String COUNTRY_GAB = "GAB";
	public final static String COUNTRY_GMB = "GMB";
	public final static String COUNTRY_GEO = "GEO";
	public final static String COUNTRY_DEU = "DEU";
	public final static String COUNTRY_GHA = "GHA";
	public final static String COUNTRY_GIB = "GIB";
	public final static String COUNTRY_GRC = "GRC";
	public final static String COUNTRY_GRL = "GRL";
	public final static String COUNTRY_GRD = "GRD";
	public final static String COUNTRY_GLP = "GLP";
	public final static String COUNTRY_GUM = "GUM";
	public final static String COUNTRY_GTM = "GTM";
	public final static String COUNTRY_GIN = "GIN";
	public final static String COUNTRY_GNB = "GNB";
	public final static String COUNTRY_GUY = "GUY";
	public final static String COUNTRY_HTI = "HTI";
	public final static String COUNTRY_HMD = "HMD";
	public final static String COUNTRY_HND = "HND";
	public final static String COUNTRY_HUN = "HUN";
	public final static String COUNTRY_ISL = "ISL";
	public final static String COUNTRY_IND = "IND";
	public final static String COUNTRY_IDN = "IDN";
	public final static String COUNTRY_IRN = "IRN";
	public final static String COUNTRY_IRQ = "IRQ";
	public final static String COUNTRY_IRL = "IRL";
	public final static String COUNTRY_ISR = "ISR";
	public final static String COUNTRY_ITA = "ITA";
	public final static String COUNTRY_JAM = "JAM";
	public final static String COUNTRY_JPN = "JPN";
	public final static String COUNTRY_JOR = "JOR";
	public final static String COUNTRY_KAZ = "KAZ";
	public final static String COUNTRY_KEN = "KEN";
	public final static String COUNTRY_KIR = "KIR";
	public final static String COUNTRY_PRK = "PRK";
	public final static String COUNTRY_KOR = "KOR";
	public final static String COUNTRY_KWT = "KWT";
	public final static String COUNTRY_KGZ = "KGZ";
	public final static String COUNTRY_LAO = "LAO";
	public final static String COUNTRY_LVA = "LVA";
	public final static String COUNTRY_LBN = "LBN";
	public final static String COUNTRY_LSO = "LSO";
	public final static String COUNTRY_LBR = "LBR";
	public final static String COUNTRY_LBY = "LBY";
	public final static String COUNTRY_LIE = "LIE";
	public final static String COUNTRY_LTU = "LTU";
	public final static String COUNTRY_LUX = "LUX";
	public final static String COUNTRY_MKD = "MKD";
	public final static String COUNTRY_MDG = "MDG";
	public final static String COUNTRY_MWI = "MWI";
	public final static String COUNTRY_MYS = "MYS";
	public final static String COUNTRY_MDV = "MDV";
	public final static String COUNTRY_MLI = "MLI";
	public final static String COUNTRY_MLT = "MLT";
	public final static String COUNTRY_MHL = "MHL";
	public final static String COUNTRY_MTQ = "MTQ";
	public final static String COUNTRY_MRT = "MRT";
	public final static String COUNTRY_MUS = "MUS";
	public final static String COUNTRY_MYT = "MYT";
	public final static String COUNTRY_MEX = "MEX";
	public final static String COUNTRY_FSM = "FSM";
	public final static String COUNTRY_MDA = "MDA";
	public final static String COUNTRY_MCO = "MCO";
	public final static String COUNTRY_MNG = "MNG";
	public final static String COUNTRY_MSR = "MSR";
	public final static String COUNTRY_MAR = "MAR";
	public final static String COUNTRY_MOZ = "MOZ";
	public final static String COUNTRY_MMR = "MMR";
	public final static String COUNTRY_NAM = "NAM";
	public final static String COUNTRY_NRU = "NRU";
	public final static String COUNTRY_NPL = "NPL";
	public final static String COUNTRY_NLD = "NLD";
	public final static String COUNTRY_ANT = "ANT";
	public final static String COUNTRY_NCL = "NCL";
	public final static String COUNTRY_NZL = "NZL";
	public final static String COUNTRY_NIC = "NIC";
	public final static String COUNTRY_NER = "NER";
	public final static String COUNTRY_NGA = "NGA";
	public final static String COUNTRY_NIU = "NIU";
	public final static String COUNTRY_NFK = "NFK";
	public final static String COUNTRY_MNP = "MNP";
	public final static String COUNTRY_NOR = "NOR";
	public final static String COUNTRY_OMN = "OMN";
	public final static String COUNTRY_PAK = "PAK";
	public final static String COUNTRY_PLW = "PLW";
	public final static String COUNTRY_PSE = "PSE";
	public final static String COUNTRY_PAN = "PAN";
	public final static String COUNTRY_PNG = "PNG";
	public final static String COUNTRY_PRY = "PRY";
	public final static String COUNTRY_PER = "PER";
	public final static String COUNTRY_PHL = "PHL";
	public final static String COUNTRY_PCN = "PCN";
	public final static String COUNTRY_POL = "POL";
	public final static String COUNTRY_PRT = "PRT";
	public final static String COUNTRY_PRI = "PRI";
	public final static String COUNTRY_QAT = "QAT";
	public final static String COUNTRY_REU = "REU";
	public final static String COUNTRY_ROM = "ROM";
	public final static String COUNTRY_RUS = "RUS";
	public final static String COUNTRY_RWA = "RWA";
	public final static String COUNTRY_SHN = "SHN";
	public final static String COUNTRY_KNA = "KNA";
	public final static String COUNTRY_LCA = "LCA";
	public final static String COUNTRY_SPM = "SPM";
	public final static String COUNTRY_VCT = "VCT";
	public final static String COUNTRY_WSM = "WSM";
	public final static String COUNTRY_SMR = "SMR";
	public final static String COUNTRY_STP = "STP";
	public final static String COUNTRY_SAU = "SAU";
	public final static String COUNTRY_SEN = "SEN";
	public final static String COUNTRY_SYC = "SYC";
	public final static String COUNTRY_SLE = "SLE";
	public final static String COUNTRY_SGP = "SGP";
	public final static String COUNTRY_SVK = "SVK";
	public final static String COUNTRY_SVN = "SVN";
	public final static String COUNTRY_SLB = "SLB";
	public final static String COUNTRY_SOM = "SOM";
	public final static String COUNTRY_ZAF = "ZAF";
	public final static String COUNTRY_SGS = "SGS";
	public final static String COUNTRY_ESP = "ESP";
	public final static String COUNTRY_LKA = "LKA";
	public final static String COUNTRY_SDN = "SDN";
	public final static String COUNTRY_SUR = "SUR";
	public final static String COUNTRY_SJM = "SJM";
	public final static String COUNTRY_SWZ = "SWZ";
	public final static String COUNTRY_SWE = "SWE";
	public final static String COUNTRY_CHE = "CHE";
	public final static String COUNTRY_SYR = "SYR";
	public final static String COUNTRY_TJK = "TJK";
	public final static String COUNTRY_TZA = "TZA";
	public final static String COUNTRY_THA = "THA";
	public final static String COUNTRY_TGO = "TGO";
	public final static String COUNTRY_TKL = "TKL";
	public final static String COUNTRY_TON = "TON";
	public final static String COUNTRY_TTO = "TTO";
	public final static String COUNTRY_TUN = "TUN";
	public final static String COUNTRY_TUR = "TUR";
	public final static String COUNTRY_TKM = "TKM";
	public final static String COUNTRY_TCA = "TCA";
	public final static String COUNTRY_TUV = "TUV";
	public final static String COUNTRY_UGA = "UGA";
	public final static String COUNTRY_UKR = "UKR";
	public final static String COUNTRY_ARE = "ARE";
	public final static String COUNTRY_GBR = "GBR";
	public final static String COUNTRY_USA = "USA";
	public final static String COUNTRY_UMI = "UMI";
	public final static String COUNTRY_URY = "URY";
	public final static String COUNTRY_UZB = "UZB";
	public final static String COUNTRY_VUT = "VUT";
	public final static String COUNTRY_VAT = "VAT";
	public final static String COUNTRY_VEN = "VEN";
	public final static String COUNTRY_VNM = "VNM";
	public final static String COUNTRY_VGB = "VGB";
	public final static String COUNTRY_VIR = "VIR";
	public final static String COUNTRY_WLF = "WLF";
	public final static String COUNTRY_ESH = "ESH";
	public final static String COUNTRY_YEM = "YEM";
	public final static String COUNTRY_YUG = "YUG";
	public final static String COUNTRY_ZMB = "ZMB";
	public final static String COUNTRY_ZWE = "ZWE";

	public final static int COUNTRY_CODE_AFG = 4;
	public final static int COUNTRY_CODE_ALB = 8;
	public final static int COUNTRY_CODE_DZA = 12;
	public final static int COUNTRY_CODE_ASM = 16;
	public final static int COUNTRY_CODE_AND = 20;
	public final static int COUNTRY_CODE_AGO = 24;
	public final static int COUNTRY_CODE_AIA = 660;
	public final static int COUNTRY_CODE_ATA = 10;
	public final static int COUNTRY_CODE_ATG = 28;
	public final static int COUNTRY_CODE_ARG = 32;
	public final static int COUNTRY_CODE_ARM = 51;
	public final static int COUNTRY_CODE_ABW = 533;
	public final static int COUNTRY_CODE_AUS = 36;
	public final static int COUNTRY_CODE_AUT = 40;
	public final static int COUNTRY_CODE_AZE = 31;
	public final static int COUNTRY_CODE_BHS = 44;
	public final static int COUNTRY_CODE_BHR = 48;
	public final static int COUNTRY_CODE_BGD = 50;
	public final static int COUNTRY_CODE_BRB = 52;
	public final static int COUNTRY_CODE_BLR = 112;
	public final static int COUNTRY_CODE_BEL = 56;
	public final static int COUNTRY_CODE_BLZ = 84;
	public final static int COUNTRY_CODE_BEN = 204;
	public final static int COUNTRY_CODE_BMU = 60;
	public final static int COUNTRY_CODE_BTN = 64;
	public final static int COUNTRY_CODE_BOL = 68;
	public final static int COUNTRY_CODE_BIH = 70;
	public final static int COUNTRY_CODE_BWA = 72;
	public final static int COUNTRY_CODE_BVT = 74;
	public final static int COUNTRY_CODE_BRA = 76;
	public final static int COUNTRY_CODE_IOT = 86;
	public final static int COUNTRY_CODE_BRN = 96;
	public final static int COUNTRY_CODE_BGR = 100;
	public final static int COUNTRY_CODE_BFA = 854;
	public final static int COUNTRY_CODE_BDI = 108;
	public final static int COUNTRY_CODE_KHM = 116;
	public final static int COUNTRY_CODE_CMR = 120;
	public final static int COUNTRY_CODE_CAN = 124;
	public final static int COUNTRY_CODE_CPV = 132;
	public final static int COUNTRY_CODE_CYM = 136;
	public final static int COUNTRY_CODE_CAF = 140;
	public final static int COUNTRY_CODE_TCD = 148;
	public final static int COUNTRY_CODE_CHL = 152;
	public final static int COUNTRY_CODE_CHN = 156;
	public final static int COUNTRY_CODE_HKG = 344;
	public final static int COUNTRY_CODE_MAC = 446;
	public final static int COUNTRY_CODE_TWN = 158;
	public final static int COUNTRY_CODE_CXR = 162;
	public final static int COUNTRY_CODE_CCK = 166;
	public final static int COUNTRY_CODE_COL = 170;
	public final static int COUNTRY_CODE_COM = 174;
	public final static int COUNTRY_CODE_COG = 178;
	public final static int COUNTRY_CODE_COD = 180;
	public final static int COUNTRY_CODE_COK = 184;
	public final static int COUNTRY_CODE_CRI = 188;
	public final static int COUNTRY_CODE_CIV = 384;
	public final static int COUNTRY_CODE_HRV = 191;
	public final static int COUNTRY_CODE_CUB = 192;
	public final static int COUNTRY_CODE_CYP = 196;
	public final static int COUNTRY_CODE_CZE = 203;
	public final static int COUNTRY_CODE_DNK = 208;
	public final static int COUNTRY_CODE_DJI = 262;
	public final static int COUNTRY_CODE_DMA = 212;
	public final static int COUNTRY_CODE_DOM = 214;
	public final static int COUNTRY_CODE_TMP = 626;
	public final static int COUNTRY_CODE_ECU = 218;
	public final static int COUNTRY_CODE_EGY = 818;
	public final static int COUNTRY_CODE_SLV = 222;
	public final static int COUNTRY_CODE_GNQ = 226;
	public final static int COUNTRY_CODE_ERI = 232;
	public final static int COUNTRY_CODE_EST = 233;
	public final static int COUNTRY_CODE_ETH = 231;
	public final static int COUNTRY_CODE_FLK = 238;
	public final static int COUNTRY_CODE_FRO = 234;
	public final static int COUNTRY_CODE_FJI = 242;
	public final static int COUNTRY_CODE_FIN = 246;
	public final static int COUNTRY_CODE_FRA = 250;
	public final static int COUNTRY_CODE_GUF = 254;
	public final static int COUNTRY_CODE_PYF = 258;
	public final static int COUNTRY_CODE_ATF = 260;
	public final static int COUNTRY_CODE_GAB = 266;
	public final static int COUNTRY_CODE_GMB = 270;
	public final static int COUNTRY_CODE_GEO = 268;
	public final static int COUNTRY_CODE_DEU = 276;
	public final static int COUNTRY_CODE_GHA = 288;
	public final static int COUNTRY_CODE_GIB = 292;
	public final static int COUNTRY_CODE_GRC = 300;
	public final static int COUNTRY_CODE_GRL = 304;
	public final static int COUNTRY_CODE_GRD = 308;
	public final static int COUNTRY_CODE_GLP = 312;
	public final static int COUNTRY_CODE_GUM = 316;
	public final static int COUNTRY_CODE_GTM = 320;
	public final static int COUNTRY_CODE_GIN = 324;
	public final static int COUNTRY_CODE_GNB = 624;
	public final static int COUNTRY_CODE_GUY = 328;
	public final static int COUNTRY_CODE_HTI = 332;
	public final static int COUNTRY_CODE_HMD = 334;
	public final static int COUNTRY_CODE_HND = 340;
	public final static int COUNTRY_CODE_HUN = 348;
	public final static int COUNTRY_CODE_ISL = 352;
	public final static int COUNTRY_CODE_IND = 356;
	public final static int COUNTRY_CODE_IDN = 360;
	public final static int COUNTRY_CODE_IRN = 364;
	public final static int COUNTRY_CODE_IRQ = 368;
	public final static int COUNTRY_CODE_IRL = 372;
	public final static int COUNTRY_CODE_ISR = 376;
	public final static int COUNTRY_CODE_ITA = 380;
	public final static int COUNTRY_CODE_JAM = 388;
	public final static int COUNTRY_CODE_JPN = 392;
	public final static int COUNTRY_CODE_JOR = 400;
	public final static int COUNTRY_CODE_KAZ = 398;
	public final static int COUNTRY_CODE_KEN = 404;
	public final static int COUNTRY_CODE_KIR = 296;
	public final static int COUNTRY_CODE_PRK = 408;
	public final static int COUNTRY_CODE_KOR = 410;
	public final static int COUNTRY_CODE_KWT = 414;
	public final static int COUNTRY_CODE_KGZ = 417;
	public final static int COUNTRY_CODE_LAO = 418;
	public final static int COUNTRY_CODE_LVA = 428;
	public final static int COUNTRY_CODE_LBN = 422;
	public final static int COUNTRY_CODE_LSO = 426;
	public final static int COUNTRY_CODE_LBR = 430;
	public final static int COUNTRY_CODE_LBY = 434;
	public final static int COUNTRY_CODE_LIE = 438;
	public final static int COUNTRY_CODE_LTU = 440;
	public final static int COUNTRY_CODE_LUX = 442;
	public final static int COUNTRY_CODE_MKD = 807;
	public final static int COUNTRY_CODE_MDG = 450;
	public final static int COUNTRY_CODE_MWI = 454;
	public final static int COUNTRY_CODE_MYS = 458;
	public final static int COUNTRY_CODE_MDV = 462;
	public final static int COUNTRY_CODE_MLI = 466;
	public final static int COUNTRY_CODE_MLT = 470;
	public final static int COUNTRY_CODE_MHL = 584;
	public final static int COUNTRY_CODE_MTQ = 474;
	public final static int COUNTRY_CODE_MRT = 478;
	public final static int COUNTRY_CODE_MUS = 480;
	public final static int COUNTRY_CODE_MYT = 175;
	public final static int COUNTRY_CODE_MEX = 484;
	public final static int COUNTRY_CODE_FSM = 583;
	public final static int COUNTRY_CODE_MDA = 498;
	public final static int COUNTRY_CODE_MCO = 492;
	public final static int COUNTRY_CODE_MNG = 496;
	public final static int COUNTRY_CODE_MSR = 500;
	public final static int COUNTRY_CODE_MAR = 504;
	public final static int COUNTRY_CODE_MOZ = 508;
	public final static int COUNTRY_CODE_MMR = 104;
	public final static int COUNTRY_CODE_NAM = 516;
	public final static int COUNTRY_CODE_NRU = 520;
	public final static int COUNTRY_CODE_NPL = 524;
	public final static int COUNTRY_CODE_NLD = 528;
	public final static int COUNTRY_CODE_ANT = 530;
	public final static int COUNTRY_CODE_NCL = 540;
	public final static int COUNTRY_CODE_NZL = 554;
	public final static int COUNTRY_CODE_NIC = 558;
	public final static int COUNTRY_CODE_NER = 562;
	public final static int COUNTRY_CODE_NGA = 566;
	public final static int COUNTRY_CODE_NIU = 570;
	public final static int COUNTRY_CODE_NFK = 574;
	public final static int COUNTRY_CODE_MNP = 580;
	public final static int COUNTRY_CODE_NOR = 578;
	public final static int COUNTRY_CODE_OMN = 512;
	public final static int COUNTRY_CODE_PAK = 586;
	public final static int COUNTRY_CODE_PLW = 585;
	public final static int COUNTRY_CODE_PSE = 275;
	public final static int COUNTRY_CODE_PAN = 591;
	public final static int COUNTRY_CODE_PNG = 598;
	public final static int COUNTRY_CODE_PRY = 600;
	public final static int COUNTRY_CODE_PER = 604;
	public final static int COUNTRY_CODE_PHL = 608;
	public final static int COUNTRY_CODE_PCN = 612;
	public final static int COUNTRY_CODE_POL = 616;
	public final static int COUNTRY_CODE_PRT = 620;
	public final static int COUNTRY_CODE_PRI = 630;
	public final static int COUNTRY_CODE_QAT = 634;
	public final static int COUNTRY_CODE_REU = 638;
	public final static int COUNTRY_CODE_ROM = 642;
	public final static int COUNTRY_CODE_RUS = 643;
	public final static int COUNTRY_CODE_RWA = 646;
	public final static int COUNTRY_CODE_SHN = 654;
	public final static int COUNTRY_CODE_KNA = 659;
	public final static int COUNTRY_CODE_LCA = 662;
	public final static int COUNTRY_CODE_SPM = 666;
	public final static int COUNTRY_CODE_VCT = 670;
	public final static int COUNTRY_CODE_WSM = 882;
	public final static int COUNTRY_CODE_SMR = 674;
	public final static int COUNTRY_CODE_STP = 678;
	public final static int COUNTRY_CODE_SAU = 682;
	public final static int COUNTRY_CODE_SEN = 686;
	public final static int COUNTRY_CODE_SYC = 690;
	public final static int COUNTRY_CODE_SLE = 694;
	public final static int COUNTRY_CODE_SGP = 702;
	public final static int COUNTRY_CODE_SVK = 703;
	public final static int COUNTRY_CODE_SVN = 705;
	public final static int COUNTRY_CODE_SLB = 90;
	public final static int COUNTRY_CODE_SOM = 706;
	public final static int COUNTRY_CODE_ZAF = 710;
	public final static int COUNTRY_CODE_SGS = 239;
	public final static int COUNTRY_CODE_ESP = 724;
	public final static int COUNTRY_CODE_LKA = 144;
	public final static int COUNTRY_CODE_SDN = 736;
	public final static int COUNTRY_CODE_SUR = 740;
	public final static int COUNTRY_CODE_SJM = 744;
	public final static int COUNTRY_CODE_SWZ = 748;
	public final static int COUNTRY_CODE_SWE = 752;
	public final static int COUNTRY_CODE_CHE = 756;
	public final static int COUNTRY_CODE_SYR = 760;
	public final static int COUNTRY_CODE_TJK = 762;
	public final static int COUNTRY_CODE_TZA = 834;
	public final static int COUNTRY_CODE_THA = 764;
	public final static int COUNTRY_CODE_TGO = 768;
	public final static int COUNTRY_CODE_TKL = 772;
	public final static int COUNTRY_CODE_TON = 776;
	public final static int COUNTRY_CODE_TTO = 780;
	public final static int COUNTRY_CODE_TUN = 788;
	public final static int COUNTRY_CODE_TUR = 792;
	public final static int COUNTRY_CODE_TKM = 795;
	public final static int COUNTRY_CODE_TCA = 796;
	public final static int COUNTRY_CODE_TUV = 798;
	public final static int COUNTRY_CODE_UGA = 800;
	public final static int COUNTRY_CODE_UKR = 804;
	public final static int COUNTRY_CODE_ARE = 784;
	public final static int COUNTRY_CODE_GBR = 826;
	public final static int COUNTRY_CODE_USA = 840;
	public final static int COUNTRY_CODE_UMI = 581;
	public final static int COUNTRY_CODE_URY = 858;
	public final static int COUNTRY_CODE_UZB = 860;
	public final static int COUNTRY_CODE_VUT = 548;
	public final static int COUNTRY_CODE_VAT = 336;
	public final static int COUNTRY_CODE_VEN = 862;
	public final static int COUNTRY_CODE_VNM = 704;
	public final static int COUNTRY_CODE_VGB = 92;
	public final static int COUNTRY_CODE_VIR = 850;
	public final static int COUNTRY_CODE_WLF = 876;
	public final static int COUNTRY_CODE_ESH = 732;
	public final static int COUNTRY_CODE_YEM = 887;
	public final static int COUNTRY_CODE_YUG = 891;
	public final static int COUNTRY_CODE_ZMB = 894;
	public final static int COUNTRY_CODE_ZWE = 716;
	
	public static String toReadableCountry(String Country) {
		
		if(BasicUtil.isEmptyString(Country)) {
			return null;
		}
		
		if(Country.length() > 4) {
			return null;
		}
		
		int counId = Integer.parseInt(Country);
		
		switch(counId) {
		case COUNTRY_CODE_AFG:
			return COUNTRY_AFG;
		case COUNTRY_CODE_ALB:
			return COUNTRY_ALB;
		case COUNTRY_CODE_DZA:
			return COUNTRY_DZA;
		case COUNTRY_CODE_ASM:
			return COUNTRY_ASM;
		case COUNTRY_CODE_AND:
			return COUNTRY_AND;
		case COUNTRY_CODE_AGO:                         
			return COUNTRY_AGO;
		case COUNTRY_CODE_AIA:                     
			return COUNTRY_AIA;
		case COUNTRY_CODE_ATA:
			return COUNTRY_ATA;
		case COUNTRY_CODE_ATG:                 
			return COUNTRY_ATG;
		case COUNTRY_CODE_ARG:
			return COUNTRY_ARG;
		case COUNTRY_CODE_ARM:
			return COUNTRY_ARM;
		case COUNTRY_CODE_ABW:
			return COUNTRY_ABW;
		case COUNTRY_CODE_AUS:    
			return COUNTRY_AUS;
		case COUNTRY_CODE_AUT:
			return COUNTRY_AUT;
		case COUNTRY_CODE_AZE:
			return COUNTRY_AZE;
		case COUNTRY_CODE_BHS:
			return COUNTRY_BHS;
		case COUNTRY_CODE_BHR:
			return COUNTRY_BHR;
		case COUNTRY_CODE_BGD:
			return COUNTRY_BGD;
		case COUNTRY_CODE_BRB:
			return COUNTRY_BRB;
		case COUNTRY_CODE_BLR:
			return COUNTRY_BLR;
		case COUNTRY_CODE_BEL:
			return COUNTRY_BEL;
		case COUNTRY_CODE_BLZ:
			return COUNTRY_BLZ;
		case COUNTRY_CODE_BEN:
			return COUNTRY_BEN;
		case COUNTRY_CODE_BMU:
			return COUNTRY_BMU;
		case COUNTRY_CODE_BTN:
			return COUNTRY_BTN;
		case COUNTRY_CODE_BOL:
			return COUNTRY_BOL;
		case COUNTRY_CODE_BIH:
			return COUNTRY_BIH;
		case COUNTRY_CODE_BWA:
			return COUNTRY_BWA;
		case COUNTRY_CODE_BVT:
			return COUNTRY_BVT;
		case COUNTRY_CODE_BRA:
			return COUNTRY_BRA;
		case COUNTRY_CODE_IOT:
			return COUNTRY_IOT;
		case COUNTRY_CODE_BRN:
			return COUNTRY_BRN;
		case COUNTRY_CODE_BGR:
			return COUNTRY_BGR;
		case COUNTRY_CODE_BFA:
			return COUNTRY_BFA;
		case COUNTRY_CODE_BDI:
			return COUNTRY_BDI;
		case COUNTRY_CODE_KHM:
			return COUNTRY_KHM;
		case COUNTRY_CODE_CMR:
			return COUNTRY_CMR;
		case COUNTRY_CODE_CAN:
			return COUNTRY_CAN;
		case COUNTRY_CODE_CPV:
			return COUNTRY_CPV;
		case COUNTRY_CODE_CYM:
			return COUNTRY_CYM;
		case COUNTRY_CODE_CAF:
			return COUNTRY_CAF;
		case COUNTRY_CODE_TCD:
			return COUNTRY_TCD;
		case COUNTRY_CODE_CHL:
			return COUNTRY_CHL;
		case COUNTRY_CODE_CHN:
			return COUNTRY_CHN;
		case COUNTRY_CODE_HKG:
			return COUNTRY_HKG;
		case COUNTRY_CODE_MAC:
			return COUNTRY_MAC;
		case COUNTRY_CODE_TWN:
			return COUNTRY_TWN;
		case COUNTRY_CODE_CXR:
			return COUNTRY_CXR;
		case COUNTRY_CODE_CCK:
			return COUNTRY_CCK;
		case COUNTRY_CODE_COL:
			return COUNTRY_COL;
		case COUNTRY_CODE_COM:
			return COUNTRY_COM;
		case COUNTRY_CODE_COG:
			return COUNTRY_COG;
		case COUNTRY_CODE_COD:
			return COUNTRY_COD;
		case COUNTRY_CODE_COK:
			return COUNTRY_COK;
		case COUNTRY_CODE_CRI:
			return COUNTRY_CRI;
		case COUNTRY_CODE_CIV:
			return COUNTRY_CIV;
		case COUNTRY_CODE_HRV:
			return COUNTRY_HRV;
		case COUNTRY_CODE_CUB:
			return COUNTRY_CUB;
		case COUNTRY_CODE_CYP:
			return COUNTRY_CYP;
		case COUNTRY_CODE_CZE:
			return COUNTRY_CZE;
		case COUNTRY_CODE_DNK:
			return COUNTRY_DNK;
		case COUNTRY_CODE_DJI:
			return COUNTRY_DJI;
		case COUNTRY_CODE_DMA:
			return COUNTRY_DMA;
		case COUNTRY_CODE_DOM:
			return COUNTRY_DOM;
		case COUNTRY_CODE_TMP:
			return COUNTRY_TMP;
		case COUNTRY_CODE_ECU:
			return COUNTRY_ECU;
		case COUNTRY_CODE_EGY:
			return COUNTRY_EGY;
		case COUNTRY_CODE_SLV:
			return COUNTRY_SLV;
		case COUNTRY_CODE_GNQ:
			return COUNTRY_GNQ;
		case COUNTRY_CODE_ERI:
			return COUNTRY_ERI;
		case COUNTRY_CODE_EST:
			return COUNTRY_EST;
		case COUNTRY_CODE_ETH:
			return COUNTRY_ETH;
		case COUNTRY_CODE_FLK:
			return COUNTRY_FLK;
		case COUNTRY_CODE_FRO:
			return COUNTRY_FRO;
		case COUNTRY_CODE_FJI:
			return COUNTRY_FJI;
		case COUNTRY_CODE_FIN:
			return COUNTRY_FIN;
		case COUNTRY_CODE_FRA:
			return COUNTRY_FRA;
		case COUNTRY_CODE_GUF:
			return COUNTRY_GUF;
		case COUNTRY_CODE_PYF:
			return COUNTRY_PYF;
		case COUNTRY_CODE_ATF:
			return COUNTRY_ATF;
		case COUNTRY_CODE_GAB:
			return COUNTRY_GAB;
		case COUNTRY_CODE_GMB:
			return COUNTRY_GMB;
		case COUNTRY_CODE_GEO:
			return COUNTRY_GEO;
		case COUNTRY_CODE_DEU:
			return COUNTRY_DEU;
		case COUNTRY_CODE_GHA:
			return COUNTRY_GHA;
		case COUNTRY_CODE_GIB:
			return COUNTRY_GIB;
		case COUNTRY_CODE_GRC:
			return COUNTRY_GRC;
		case COUNTRY_CODE_GRL:
			return COUNTRY_GRL;
		case COUNTRY_CODE_GRD:
			return COUNTRY_GRD;
		case COUNTRY_CODE_GLP:
			return COUNTRY_GLP;
		case COUNTRY_CODE_GUM:
			return COUNTRY_GUM;
		case COUNTRY_CODE_GTM:
			return COUNTRY_GTM;
		case COUNTRY_CODE_GIN:
			return COUNTRY_GIN;
		case COUNTRY_CODE_GNB:
			return COUNTRY_GNB;
		case COUNTRY_CODE_GUY:
			return COUNTRY_GUY;
		case COUNTRY_CODE_HTI:
			return COUNTRY_HTI;
		case COUNTRY_CODE_HMD:
			return COUNTRY_HMD;
		case COUNTRY_CODE_HND:
			return COUNTRY_HND;
		case COUNTRY_CODE_HUN:
			return COUNTRY_HUN;
		case COUNTRY_CODE_ISL:
			return COUNTRY_ISL;
		case COUNTRY_CODE_IND:
			return COUNTRY_IND;
		case COUNTRY_CODE_IDN:
			return COUNTRY_IDN;
		case COUNTRY_CODE_IRN:
			return COUNTRY_IRN;
		case COUNTRY_CODE_IRQ:
			return COUNTRY_IRQ;
		case COUNTRY_CODE_IRL:
			return COUNTRY_IRL;
		case COUNTRY_CODE_ISR:
			return COUNTRY_ISR;
		case COUNTRY_CODE_ITA:
			return COUNTRY_ITA;
		case COUNTRY_CODE_JAM:
			return COUNTRY_JAM;
		case COUNTRY_CODE_JPN:
			return COUNTRY_JPN;
		case COUNTRY_CODE_JOR:
			return COUNTRY_JOR;
		case COUNTRY_CODE_KAZ:
			return COUNTRY_KAZ;
		case COUNTRY_CODE_KEN:
			return COUNTRY_KEN;
		case COUNTRY_CODE_KIR:
			return COUNTRY_KIR;
		case COUNTRY_CODE_PRK:
			return COUNTRY_PRK;
		case COUNTRY_CODE_KOR:
			return COUNTRY_KOR;
		case COUNTRY_CODE_KWT:
			return COUNTRY_KWT;
		case COUNTRY_CODE_KGZ:
			return COUNTRY_KGZ;
		case COUNTRY_CODE_LAO:
			return COUNTRY_LAO;
		case COUNTRY_CODE_LVA:
			return COUNTRY_LVA;
		case COUNTRY_CODE_LBN:
			return COUNTRY_LBN;
		case COUNTRY_CODE_LSO:
			return COUNTRY_LSO;
		case COUNTRY_CODE_LBR:
			return COUNTRY_LBR;
		case COUNTRY_CODE_LBY:
			return COUNTRY_LBY;
		case COUNTRY_CODE_LIE:
			return COUNTRY_LIE;
		case COUNTRY_CODE_LTU:
			return COUNTRY_LTU;
		case COUNTRY_CODE_LUX:
			return COUNTRY_LUX;
		case COUNTRY_CODE_MKD:
			return COUNTRY_MKD;
		case COUNTRY_CODE_MDG:
			return COUNTRY_MDG;
		case COUNTRY_CODE_MWI:
			return COUNTRY_MWI;
		case COUNTRY_CODE_MYS:
			return COUNTRY_MYS;
		case COUNTRY_CODE_MDV:
			return COUNTRY_MDV;
		case COUNTRY_CODE_MLI:
			return COUNTRY_MLI;
		case COUNTRY_CODE_MLT:
			return COUNTRY_MLT;
		case COUNTRY_CODE_MHL:
			return COUNTRY_MHL;
		case COUNTRY_CODE_MTQ:
			return COUNTRY_MTQ;
		case COUNTRY_CODE_MRT:
			return COUNTRY_MRT;
		case COUNTRY_CODE_MUS:
			return COUNTRY_MUS;
		case COUNTRY_CODE_MYT:
			return COUNTRY_MYT;
		case COUNTRY_CODE_MEX:
			return COUNTRY_MEX;
		case COUNTRY_CODE_FSM:
			return COUNTRY_FSM;
		case COUNTRY_CODE_MDA:
			return COUNTRY_MDA;
		case COUNTRY_CODE_MCO:
			return COUNTRY_MCO;
		case COUNTRY_CODE_MNG:
			return COUNTRY_MNG;
		case COUNTRY_CODE_MSR:
			return COUNTRY_MSR;
		case COUNTRY_CODE_MAR:
			return COUNTRY_MAR;
		case COUNTRY_CODE_MOZ:
			return COUNTRY_MOZ;
		case COUNTRY_CODE_MMR:
			return COUNTRY_MMR;
		case COUNTRY_CODE_NAM:
			return COUNTRY_NAM;
		case COUNTRY_CODE_NRU:
			return COUNTRY_NRU;
		case COUNTRY_CODE_NPL:
			return COUNTRY_NPL;
		case COUNTRY_CODE_NLD:
			return COUNTRY_NLD;
		case COUNTRY_CODE_ANT:
			return COUNTRY_ANT;
		case COUNTRY_CODE_NCL:
			return COUNTRY_NCL;
		case COUNTRY_CODE_NZL:
			return COUNTRY_NZL;
		case COUNTRY_CODE_NIC:
			return COUNTRY_NIC;
		case COUNTRY_CODE_NER:
			return COUNTRY_NER;
		case COUNTRY_CODE_NGA:
			return COUNTRY_NGA;
		case COUNTRY_CODE_NIU:
			return COUNTRY_NIU;
		case COUNTRY_CODE_NFK:
			return COUNTRY_NFK;
		case COUNTRY_CODE_MNP:
			return COUNTRY_MNP;
		case COUNTRY_CODE_NOR:
			return COUNTRY_NOR;
		case COUNTRY_CODE_OMN:
			return COUNTRY_OMN;
		case COUNTRY_CODE_PAK:
			return COUNTRY_PAK;
		case COUNTRY_CODE_PLW:
			return COUNTRY_PLW;
		case COUNTRY_CODE_PSE:
			return COUNTRY_PSE;
		case COUNTRY_CODE_PAN:
			return COUNTRY_PAN;
		case COUNTRY_CODE_PNG:
			return COUNTRY_PNG;
		case COUNTRY_CODE_PRY:
			return COUNTRY_PRY;
		case COUNTRY_CODE_PER:
			return COUNTRY_PER;
		case COUNTRY_CODE_PHL:
			return COUNTRY_PHL;
		case COUNTRY_CODE_PCN:
			return COUNTRY_PCN;
		case COUNTRY_CODE_POL:
			return COUNTRY_POL;
		case COUNTRY_CODE_PRT:
			return COUNTRY_PRT;
		case COUNTRY_CODE_PRI:
			return COUNTRY_PRI;
		case COUNTRY_CODE_QAT:
			return COUNTRY_QAT;
		case COUNTRY_CODE_REU:
			return COUNTRY_REU;
		case COUNTRY_CODE_ROM:
			return COUNTRY_ROM;
		case COUNTRY_CODE_RUS:
			return COUNTRY_RUS;
		case COUNTRY_CODE_RWA:
			return COUNTRY_RWA;
		case COUNTRY_CODE_SHN:
			return COUNTRY_SHN;
		case COUNTRY_CODE_KNA:
			return COUNTRY_KNA;
		case COUNTRY_CODE_LCA:
			return COUNTRY_LCA;
		case COUNTRY_CODE_SPM:
			return COUNTRY_SPM;
		case COUNTRY_CODE_VCT:
			return COUNTRY_VCT;
		case COUNTRY_CODE_WSM:
			return COUNTRY_WSM;
		case COUNTRY_CODE_SMR:
			return COUNTRY_SMR;
		case COUNTRY_CODE_STP:
			return COUNTRY_STP;
		case COUNTRY_CODE_SAU:
			return COUNTRY_SAU;
		case COUNTRY_CODE_SEN:
			return COUNTRY_SEN;
		case COUNTRY_CODE_SYC:
			return COUNTRY_SYC;
		case COUNTRY_CODE_SLE:
			return COUNTRY_SLE;
		case COUNTRY_CODE_SGP:
			return COUNTRY_SGP;
		case COUNTRY_CODE_SVK:
			return COUNTRY_SVK;
		case COUNTRY_CODE_SVN:
			return COUNTRY_SVN;
		case COUNTRY_CODE_SLB:
			return COUNTRY_SLB;
		case COUNTRY_CODE_SOM:
			return COUNTRY_SOM;
		case COUNTRY_CODE_ZAF:
			return COUNTRY_ZAF;
		case COUNTRY_CODE_SGS:
			return COUNTRY_SGS;
		case COUNTRY_CODE_ESP:
			return COUNTRY_ESP;
		case COUNTRY_CODE_LKA:
			return COUNTRY_LKA;
		case COUNTRY_CODE_SDN:
			return COUNTRY_SDN;
		case COUNTRY_CODE_SUR:
			return COUNTRY_SUR;
		case COUNTRY_CODE_SJM:
			return COUNTRY_SJM;
		case COUNTRY_CODE_SWZ:
			return COUNTRY_SWZ;
		case COUNTRY_CODE_SWE:
			return COUNTRY_SWE;
		case COUNTRY_CODE_CHE:
			return COUNTRY_CHE;
		case COUNTRY_CODE_SYR:
			return COUNTRY_SYR;
		case COUNTRY_CODE_TJK:
			return COUNTRY_TJK;
		case COUNTRY_CODE_TZA:
			return COUNTRY_TZA;
		case COUNTRY_CODE_THA:
			return COUNTRY_THA;
		case COUNTRY_CODE_TGO:
			return COUNTRY_TGO;
		case COUNTRY_CODE_TKL:
			return COUNTRY_TKL;
		case COUNTRY_CODE_TON:
			return COUNTRY_TON;
		case COUNTRY_CODE_TTO:
			return COUNTRY_TTO;
		case COUNTRY_CODE_TUN:
			return COUNTRY_TUN;
		case COUNTRY_CODE_TUR:
			return COUNTRY_TUR;
		case COUNTRY_CODE_TKM:
			return COUNTRY_TKM;
		case COUNTRY_CODE_TCA:
			return COUNTRY_TCA;
		case COUNTRY_CODE_TUV:
			return COUNTRY_TUV;
		case COUNTRY_CODE_UGA:
			return COUNTRY_UGA;
		case COUNTRY_CODE_UKR:
			return COUNTRY_UKR;
		case COUNTRY_CODE_ARE:
			return COUNTRY_ARE;
		case COUNTRY_CODE_GBR:
			return COUNTRY_GBR;
		case COUNTRY_CODE_USA:
			return COUNTRY_USA;
		case COUNTRY_CODE_UMI:
			return COUNTRY_UMI;
		case COUNTRY_CODE_URY:
			return COUNTRY_URY;
		case COUNTRY_CODE_UZB:
			return COUNTRY_UZB;
		case COUNTRY_CODE_VUT:
			return COUNTRY_VUT;
		case COUNTRY_CODE_VAT:
			return COUNTRY_VAT;
		case COUNTRY_CODE_VEN:
			return COUNTRY_VEN;
		case COUNTRY_CODE_VNM:
			return COUNTRY_VNM;
		case COUNTRY_CODE_VGB:
			return COUNTRY_VGB;
		case COUNTRY_CODE_VIR:
			return COUNTRY_VIR;
		case COUNTRY_CODE_WLF:
			return COUNTRY_WLF;
		case COUNTRY_CODE_ESH:
			return COUNTRY_ESH;
		case COUNTRY_CODE_YEM:
			return COUNTRY_YEM;
		case COUNTRY_CODE_YUG:
			return COUNTRY_YUG;
		case COUNTRY_CODE_ZMB:
			return COUNTRY_ZMB;
		case COUNTRY_CODE_ZWE:
			return COUNTRY_ZWE;
		default:
			return COUNTRY_UNKNOWN;
		}
	}
}
