package com.zc.app.sebc.pboc2;

import com.zc.app.sebc.util.BasicUtil;

//see GB/T 12406-2008/ISO 4217:2001

public final class CurrencyCode {

	public final static String CURRENCY_UNKNOWN = "未知";
	
	public final static String CURRENCY_AFA = "AFA";
	public final static String CURRENCY_ALL = "ALL";
	public final static String CURRENCY_DZD = "DZD";
	public final static String CURRENCY_USD = "USD";
	public final static String CURRENCY_EUR = "EUR";
	public final static String CURRENCY_ESP = "ESP";
	public final static String CURRENCY_FRF = "FRF";
	public final static String CURRENCY_ADP = "ADP";
	public final static String CURRENCY_AOA = "AOA";
	public final static String CURRENCY_XCD = "XCD";
	public final static String CURRENCY_ARS = "ARS";
	public final static String CURRENCY_AMD = "AMD";
	public final static String CURRENCY_AWG = "AWG";
	public final static String CURRENCY_AUD = "AUD";
	public final static String CURRENCY_ATS = "ATS";
	public final static String CURRENCY_AZM = "AZM";
	public final static String CURRENCY_BSD = "BSD";
	public final static String CURRENCY_BHD = "BHD";
	public final static String CURRENCY_BDT = "BDT";
	public final static String CURRENCY_BBD = "BBD";
	public final static String CURRENCY_BYR = "BYR";
	public final static String CURRENCY_BEF = "BEF";
	public final static String CURRENCY_BZD = "BZD";
	public final static String CURRENCY_XOF = "XOF";
	public final static String CURRENCY_BMD = "BMD";
	public final static String CURRENCY_INR = "INR";
	public final static String CURRENCY_BTN = "BTN";
	public final static String CURRENCY_BOB = "BOB";
	public final static String CURRENCY_BOV = "BOV";
	public final static String CURRENCY_BAM = "BAM";
	public final static String CURRENCY_BWP = "BWP";
	public final static String CURRENCY_NOK = "NOK";
	public final static String CURRENCY_BRL = "BRL";
	public final static String CURRENCY_BND = "BND";
	public final static String CURRENCY_BGL = "BGL";
	public final static String CURRENCY_BGN = "BGN";
	public final static String CURRENCY_BIF = "BIF";
	public final static String CURRENCY_KHR = "KHR";
	public final static String CURRENCY_XAF = "XAF";
	public final static String CURRENCY_CAD = "CAD";
	public final static String CURRENCY_CVE = "CVE";
	public final static String CURRENCY_KYD = "KYD";
	public final static String CURRENCY_CLP = "CLP";
	public final static String CURRENCY_CLF = "CLF";
	public final static String CURRENCY_CNY = "CNY";
	public final static String CURRENCY_COP = "COP";
	public final static String CURRENCY_KMF = "KMF";
	public final static String CURRENCY_CDF = "CDF";
	public final static String CURRENCY_NZD = "NZD";
	public final static String CURRENCY_CRC = "CRC";
	public final static String CURRENCY_HRK = "HRK";
	public final static String CURRENCY_CUP = "CUP";
	public final static String CURRENCY_CYP = "CYP";
	public final static String CURRENCY_CZK = "CZK";
	public final static String CURRENCY_DKK = "DKK";
	public final static String CURRENCY_DJF = "DJF";
	public final static String CURRENCY_DOP = "DOP";
	public final static String CURRENCY_TPE = "TPE";
	public final static String CURRENCY_IDR = "IDR";
	public final static String CURRENCY_EGP = "EGP";
	public final static String CURRENCY_SVC = "SVC";
	public final static String CURRENCY_ERN = "ERN";
	public final static String CURRENCY_EEK = "EEK";
	public final static String CURRENCY_ETB = "ETB";
	public final static String CURRENCY_FKP = "FKP";
	public final static String CURRENCY_FJD = "FJD";
	public final static String CURRENCY_FIM = "FIM";
	public final static String CURRENCY_XPF = "XPF";
	public final static String CURRENCY_GMD = "GMD";
	public final static String CURRENCY_GEL = "GEL";
	public final static String CURRENCY_DEM = "DEM";
	public final static String CURRENCY_GHC = "GHC";
	public final static String CURRENCY_GIP = "GIP";
	public final static String CURRENCY_GRD = "GRD";
	public final static String CURRENCY_GTQ = "GTQ";
	public final static String CURRENCY_GNF = "GNF";
	public final static String CURRENCY_GWP = "GWP";
	public final static String CURRENCY_GYD = "GYD";
	public final static String CURRENCY_HTG = "HTG";
	public final static String CURRENCY_ITL = "ITL";
	public final static String CURRENCY_HNL = "HNL";
	public final static String CURRENCY_HKD = "HKD";
	public final static String CURRENCY_HUF = "HUF";
	public final static String CURRENCY_ISK = "ISK";
	public final static String CURRENCY_XDR = "XDR";
	public final static String CURRENCY_IRR = "IRR";
	public final static String CURRENCY_IQD = "IQD";
	public final static String CURRENCY_IEP = "IEP";
	public final static String CURRENCY_ILS = "ILS";
	public final static String CURRENCY_JMD = "JMD";
	public final static String CURRENCY_JPY = "JPY";
	public final static String CURRENCY_JOD = "JOD";
	public final static String CURRENCY_KZT = "KZT";
	public final static String CURRENCY_KES = "KES";
	public final static String CURRENCY_KPW = "KPW";
	public final static String CURRENCY_KRW = "KRW";
	public final static String CURRENCY_KWD = "KWD";
	public final static String CURRENCY_KGS = "KGS";
	public final static String CURRENCY_LAK = "LAK";
	public final static String CURRENCY_LVL = "LVL";
	public final static String CURRENCY_LBP = "LBP";
	public final static String CURRENCY_ZAR = "ZAR";
	public final static String CURRENCY_LSL = "LSL";
	public final static String CURRENCY_LRD = "LRD";
	public final static String CURRENCY_LYD = "LYD";
	public final static String CURRENCY_CHF = "CHF";
	public final static String CURRENCY_LTL = "LTL";
	public final static String CURRENCY_LUF = "LUF";
	public final static String CURRENCY_MOP = "MOP";
	public final static String CURRENCY_MKD = "MKD";
	public final static String CURRENCY_MGF = "MGF";
	public final static String CURRENCY_MWK = "MWK";
	public final static String CURRENCY_MYR = "MYR";
	public final static String CURRENCY_MVR = "MVR";
	public final static String CURRENCY_MTL = "MTL";
	public final static String CURRENCY_MRO = "MRO";
	public final static String CURRENCY_MUR = "MUR";
	public final static String CURRENCY_MXN = "MXN";
	public final static String CURRENCY_MXV = "MXV";
	public final static String CURRENCY_MDL = "MDL";
	public final static String CURRENCY_MNT = "MNT";
	public final static String CURRENCY_MAD = "MAD";
	public final static String CURRENCY_MZM = "MZM";
	public final static String CURRENCY_MMK = "MMK";
	public final static String CURRENCY_NAD = "NAD";
	public final static String CURRENCY_NPR = "NPR";
	public final static String CURRENCY_NLG = "NLG";
	public final static String CURRENCY_ANG = "ANG";
	public final static String CURRENCY_NIO = "NIO";
	public final static String CURRENCY_NGN = "NGN";
	public final static String CURRENCY_OMR = "OMR";
	public final static String CURRENCY_PKR = "PKR";
	public final static String CURRENCY_PAB = "PAB";
	public final static String CURRENCY_PGK = "PGK";
	public final static String CURRENCY_PYG = "PYG";
	public final static String CURRENCY_PEN = "PEN";
	public final static String CURRENCY_PHP = "PHP";
	public final static String CURRENCY_PLN = "PLN";
	public final static String CURRENCY_PTE = "PTE";
	public final static String CURRENCY_QAR = "QAR";
	public final static String CURRENCY_ROL = "ROL";
	public final static String CURRENCY_RUR = "RUR";
	public final static String CURRENCY_RUB = "RUB";
	public final static String CURRENCY_RWF = "RWF";
	public final static String CURRENCY_SHP = "SHP";
	public final static String CURRENCY_WST = "WST";
	public final static String CURRENCY_STD = "STD";
	public final static String CURRENCY_SAR = "SAR";
	public final static String CURRENCY_SCR = "SCR";
	public final static String CURRENCY_SLL = "SLL";
	public final static String CURRENCY_SGD = "SGD";
	public final static String CURRENCY_SKK = "SKK";
	public final static String CURRENCY_SIT = "SIT";
	public final static String CURRENCY_SBD = "SBD";
	public final static String CURRENCY_SOS = "SOS";
	public final static String CURRENCY_LKR = "LKR";
	public final static String CURRENCY_SDD = "SDD";
	public final static String CURRENCY_SRG = "SRG";
	public final static String CURRENCY_SZL = "SZL";
	public final static String CURRENCY_SEK = "SEK";
	public final static String CURRENCY_SYP = "SYP";
	public final static String CURRENCY_TWD = "TWD";
	public final static String CURRENCY_TJS = "TJS";
	public final static String CURRENCY_TZS = "TZS";
	public final static String CURRENCY_THB = "THB";
	public final static String CURRENCY_TOP = "TOP";
	public final static String CURRENCY_TTD = "TTD";
	public final static String CURRENCY_TND = "TND";
	public final static String CURRENCY_TRL = "TRL";
	public final static String CURRENCY_TMM = "TMM";
	public final static String CURRENCY_UGX = "UGX";
	public final static String CURRENCY_UAH = "UAH";
	public final static String CURRENCY_AED = "AED";
	public final static String CURRENCY_GBP = "GBP";
	public final static String CURRENCY_USS = "USS";
	public final static String CURRENCY_USN = "USN";
	public final static String CURRENCY_UYU = "UYU";
	public final static String CURRENCY_UZS = "UZS";
	public final static String CURRENCY_VUV = "VUV";
	public final static String CURRENCY_VND = "VND";
	public final static String CURRENCY_YER = "YER";
	public final static String CURRENCY_YUM = "YUM";
	public final static String CURRENCY_ZMK = "ZMK";
	public final static String CURRENCY_ZWD = "ZWD";
	//public final static String CURRENCY_XAU = "XAU";
	//public final static String CURRENCY_XXX = "XXX";
	
	public final static int CURRENCY_CODE_AFA = 4;
	public final static int CURRENCY_CODE_ALL = 8;
	public final static int CURRENCY_CODE_DZD = 12;	
	public final static int CURRENCY_CODE_USD = 840;
	public final static int CURRENCY_CODE_EUR = 978;
	public final static int CURRENCY_CODE_ESP = 724;
	public final static int CURRENCY_CODE_FRF = 250;
	public final static int CURRENCY_CODE_ADP = 20;
	public final static int CURRENCY_CODE_AOA = 973;
	public final static int CURRENCY_CODE_XCD = 951;
	public final static int CURRENCY_CODE_ARS = 32;
	public final static int CURRENCY_CODE_AMD = 51;
	public final static int CURRENCY_CODE_AWG = 533;
	public final static int CURRENCY_CODE_AUD = 36;
	public final static int CURRENCY_CODE_ATS = 40;
	public final static int CURRENCY_CODE_AZM = 31;
	public final static int CURRENCY_CODE_BSD = 44;
	public final static int CURRENCY_CODE_BHD = 48;
	public final static int CURRENCY_CODE_BDT = 50;
	public final static int CURRENCY_CODE_BBD = 52;
	public final static int CURRENCY_CODE_BYR = 974;
	public final static int CURRENCY_CODE_BEF = 56;
	public final static int CURRENCY_CODE_BZD = 84;
	public final static int CURRENCY_CODE_XOF = 952;
	public final static int CURRENCY_CODE_BMD = 60;
	public final static int CURRENCY_CODE_INR = 356;
	public final static int CURRENCY_CODE_BTN = 64;
	public final static int CURRENCY_CODE_BOB = 68;
	public final static int CURRENCY_CODE_BOV = 984;
	public final static int CURRENCY_CODE_BAM = 977;
	public final static int CURRENCY_CODE_BWP = 72;
	public final static int CURRENCY_CODE_NOK = 578;
	public final static int CURRENCY_CODE_BRL = 986;
	public final static int CURRENCY_CODE_BND = 96;
	public final static int CURRENCY_CODE_BGL = 100;
	public final static int CURRENCY_CODE_BGN = 975;
	public final static int CURRENCY_CODE_BIF = 108;
	public final static int CURRENCY_CODE_KHR = 116;
	public final static int CURRENCY_CODE_XAF = 950;
	public final static int CURRENCY_CODE_CAD = 124;
	public final static int CURRENCY_CODE_CVE = 132;
	public final static int CURRENCY_CODE_KYD = 136;
	public final static int CURRENCY_CODE_CLP = 152;
	public final static int CURRENCY_CODE_CLF = 990;
	public final static int CURRENCY_CODE_CNY = 156;
	public final static int CURRENCY_CODE_COP = 170;
	public final static int CURRENCY_CODE_KMF = 174;
	public final static int CURRENCY_CODE_CDF = 976;
	public final static int CURRENCY_CODE_NZD = 554;
	public final static int CURRENCY_CODE_CRC = 188;
	public final static int CURRENCY_CODE_HRK = 191;
	public final static int CURRENCY_CODE_CUP = 192;
	public final static int CURRENCY_CODE_CYP = 196;
	public final static int CURRENCY_CODE_CZK = 203;
	public final static int CURRENCY_CODE_DKK = 208;
	public final static int CURRENCY_CODE_DJF = 262;
	public final static int CURRENCY_CODE_DOP = 214;
	public final static int CURRENCY_CODE_TPE = 626;
	public final static int CURRENCY_CODE_IDR = 360;
	public final static int CURRENCY_CODE_EGP = 818;
	public final static int CURRENCY_CODE_SVC = 222;
	public final static int CURRENCY_CODE_ERN = 232;
	public final static int CURRENCY_CODE_EEK = 233;
	public final static int CURRENCY_CODE_ETB = 230;
	public final static int CURRENCY_CODE_FKP = 238;
	public final static int CURRENCY_CODE_FJD = 242;
	public final static int CURRENCY_CODE_FIM = 246;
	public final static int CURRENCY_CODE_XPF = 953;
	public final static int CURRENCY_CODE_GMD = 270;
	public final static int CURRENCY_CODE_GEL = 981;
	public final static int CURRENCY_CODE_DEM = 276;
	public final static int CURRENCY_CODE_GHC = 288;
	public final static int CURRENCY_CODE_GIP = 292;
	public final static int CURRENCY_CODE_GRD = 300;
	public final static int CURRENCY_CODE_GTQ = 320;
	public final static int CURRENCY_CODE_GNF = 324;
	public final static int CURRENCY_CODE_GWP = 624;
	public final static int CURRENCY_CODE_GYD = 328;
	public final static int CURRENCY_CODE_HTG = 332;
	public final static int CURRENCY_CODE_ITL = 380;
	public final static int CURRENCY_CODE_HNL = 340;
	public final static int CURRENCY_CODE_HKD = 344;
	public final static int CURRENCY_CODE_HUF = 348;
	public final static int CURRENCY_CODE_ISK = 352;
	public final static int CURRENCY_CODE_XDR = 960;
	public final static int CURRENCY_CODE_IRR = 364;
	public final static int CURRENCY_CODE_IQD = 368;
	public final static int CURRENCY_CODE_IEP = 372;
	public final static int CURRENCY_CODE_ILS = 376;
	public final static int CURRENCY_CODE_JMD = 388;
	public final static int CURRENCY_CODE_JPY = 392;
	public final static int CURRENCY_CODE_JOD = 400;
	public final static int CURRENCY_CODE_KZT = 398;
	public final static int CURRENCY_CODE_KES = 404;
	public final static int CURRENCY_CODE_KPW = 408;
	public final static int CURRENCY_CODE_KRW = 410;
	public final static int CURRENCY_CODE_KWD = 414;
	public final static int CURRENCY_CODE_KGS = 417;
	public final static int CURRENCY_CODE_LAK = 418;
	public final static int CURRENCY_CODE_LVL = 428;
	public final static int CURRENCY_CODE_LBP = 422;
	public final static int CURRENCY_CODE_ZAR = 710;
	public final static int CURRENCY_CODE_LSL = 426;
	public final static int CURRENCY_CODE_LRD = 430;
	public final static int CURRENCY_CODE_LYD = 434;
	public final static int CURRENCY_CODE_CHF = 756;
	public final static int CURRENCY_CODE_LTL = 440;
	public final static int CURRENCY_CODE_LUF = 442;
	public final static int CURRENCY_CODE_MOP = 446;
	public final static int CURRENCY_CODE_MKD = 807;
	public final static int CURRENCY_CODE_MGF = 450;
	public final static int CURRENCY_CODE_MWK = 454;
	public final static int CURRENCY_CODE_MYR = 458;
	public final static int CURRENCY_CODE_MVR = 462;
	public final static int CURRENCY_CODE_MTL = 470;
	public final static int CURRENCY_CODE_MRO = 478;
	public final static int CURRENCY_CODE_MUR = 480;
	public final static int CURRENCY_CODE_MXN = 484;
	public final static int CURRENCY_CODE_MXV = 979;
	public final static int CURRENCY_CODE_MDL = 498;
	public final static int CURRENCY_CODE_MNT = 496;
	public final static int CURRENCY_CODE_MAD = 504;
	public final static int CURRENCY_CODE_MZM = 508;
	public final static int CURRENCY_CODE_MMK = 104;
	public final static int CURRENCY_CODE_NAD = 516;
	public final static int CURRENCY_CODE_NPR = 524;
	public final static int CURRENCY_CODE_NLG = 528;
	public final static int CURRENCY_CODE_ANG = 532;
	public final static int CURRENCY_CODE_NIO = 558;
	public final static int CURRENCY_CODE_NGN = 566;
	public final static int CURRENCY_CODE_OMR = 512;
	public final static int CURRENCY_CODE_PKR = 586;
	public final static int CURRENCY_CODE_PAB = 590;
	public final static int CURRENCY_CODE_PGK = 598;
	public final static int CURRENCY_CODE_PYG = 600;
	public final static int CURRENCY_CODE_PEN = 604;
	public final static int CURRENCY_CODE_PHP = 608;
	public final static int CURRENCY_CODE_PLN = 985;
	public final static int CURRENCY_CODE_PTE = 620;
	public final static int CURRENCY_CODE_QAR = 634;
	public final static int CURRENCY_CODE_ROL = 642;
	public final static int CURRENCY_CODE_RUR = 810;
	public final static int CURRENCY_CODE_RUB = 643;
	public final static int CURRENCY_CODE_RWF = 646;
	public final static int CURRENCY_CODE_SHP = 654;
	public final static int CURRENCY_CODE_WST = 882;
	public final static int CURRENCY_CODE_STD = 678;
	public final static int CURRENCY_CODE_SAR = 682;
	public final static int CURRENCY_CODE_SCR = 690;
	public final static int CURRENCY_CODE_SLL = 694;
	public final static int CURRENCY_CODE_SGD = 702;
	public final static int CURRENCY_CODE_SKK = 703;
	public final static int CURRENCY_CODE_SIT = 705;
	public final static int CURRENCY_CODE_SBD = 90;
	public final static int CURRENCY_CODE_SOS = 706;
	public final static int CURRENCY_CODE_LKR = 144;
	public final static int CURRENCY_CODE_SDD = 736;
	public final static int CURRENCY_CODE_SRG = 740;
	public final static int CURRENCY_CODE_SZL = 748;
	public final static int CURRENCY_CODE_SEK = 752;
	public final static int CURRENCY_CODE_SYP = 760;
	public final static int CURRENCY_CODE_TWD = 901;
	public final static int CURRENCY_CODE_TJS = 972;
	public final static int CURRENCY_CODE_TZS = 834;
	public final static int CURRENCY_CODE_THB = 764;
	public final static int CURRENCY_CODE_TOP = 776;
	public final static int CURRENCY_CODE_TTD = 780;
	public final static int CURRENCY_CODE_TND = 788;
	public final static int CURRENCY_CODE_TRL = 792;
	public final static int CURRENCY_CODE_TMM = 795;
	public final static int CURRENCY_CODE_UGX = 800;
	public final static int CURRENCY_CODE_UAH = 980;
	public final static int CURRENCY_CODE_AED = 784;
	public final static int CURRENCY_CODE_GBP = 826;
	public final static int CURRENCY_CODE_USS = 998;
	public final static int CURRENCY_CODE_USN = 997;
	public final static int CURRENCY_CODE_UYU = 858;
	public final static int CURRENCY_CODE_UZS = 860;
	public final static int CURRENCY_CODE_VUV = 548;
	public final static int CURRENCY_CODE_VND = 704;
	public final static int CURRENCY_CODE_YER = 886;
	public final static int CURRENCY_CODE_YUM = 891;
	public final static int CURRENCY_CODE_ZMK = 894;
	public final static int CURRENCY_CODE_ZWD = 716;
	//public final static int CURRENCY_CODE_XAU = 959;
	//public final static int CURRENCY_CODE_XXX = 999;
	
	public static String toReadableCurrency(String currency) {
		
		if(BasicUtil.isEmptyString(currency)) {
			return null;
		}
		
		if(currency.length() > 4) {
			return null;
		}
		
		int curId = Integer.parseInt(currency);
		
		switch(curId) {
		case CURRENCY_CODE_AFA:
			return CURRENCY_AFA;
		case CURRENCY_CODE_ALL:
			return CURRENCY_ALL;
		case CURRENCY_CODE_DZD:
			return CURRENCY_DZD;
		case CURRENCY_CODE_USD:
			return CURRENCY_USD;
		case CURRENCY_CODE_EUR:
			return CURRENCY_EUR;
		case CURRENCY_CODE_ESP:
			return CURRENCY_ESP;
		case CURRENCY_CODE_FRF:
			return CURRENCY_FRF;
		case CURRENCY_CODE_ADP:
			return CURRENCY_ADP;
		case CURRENCY_CODE_AOA:
			return CURRENCY_AOA;
		case CURRENCY_CODE_XCD:
			return CURRENCY_XCD;
		case CURRENCY_CODE_ARS:
			return CURRENCY_ARS;
		case CURRENCY_CODE_AMD:
			return CURRENCY_AMD;
		case CURRENCY_CODE_AWG:
			return CURRENCY_AWG;
		case CURRENCY_CODE_AUD:
			return CURRENCY_AUD;
		case CURRENCY_CODE_ATS:
			return CURRENCY_ATS;
		case CURRENCY_CODE_AZM:
			return CURRENCY_AZM;
		case CURRENCY_CODE_BSD:
			return CURRENCY_BSD;
		case CURRENCY_CODE_BHD:
			return CURRENCY_BHD;
		case CURRENCY_CODE_BDT:
			return CURRENCY_BDT;
		case CURRENCY_CODE_BBD:
			return CURRENCY_BBD;
		case CURRENCY_CODE_BYR:
			return CURRENCY_BYR;
		case CURRENCY_CODE_BEF:
			return CURRENCY_BEF;
		case CURRENCY_CODE_BZD:
			return CURRENCY_BZD;
		case CURRENCY_CODE_XOF:
			return CURRENCY_XOF;
		case CURRENCY_CODE_BMD:
			return CURRENCY_BMD;
		case CURRENCY_CODE_INR:
			return CURRENCY_INR;
		case CURRENCY_CODE_BTN:
			return CURRENCY_BTN;
		case CURRENCY_CODE_BOB:
			return CURRENCY_BOB;
		case CURRENCY_CODE_BOV:
			return CURRENCY_BOV;
		case CURRENCY_CODE_BAM:
			return CURRENCY_BAM;
		case CURRENCY_CODE_BWP:
			return CURRENCY_BWP;
		case CURRENCY_CODE_NOK:
			return CURRENCY_NOK;
		case CURRENCY_CODE_BRL:
			return CURRENCY_BRL;
		case CURRENCY_CODE_BND:
			return CURRENCY_BND;
		case CURRENCY_CODE_BGL:
			return CURRENCY_BGL;
		case CURRENCY_CODE_BGN:
			return CURRENCY_BGN;
		case CURRENCY_CODE_BIF:
			return CURRENCY_BIF;
		case CURRENCY_CODE_KHR:
			return CURRENCY_KHR;
		case CURRENCY_CODE_XAF:
			return CURRENCY_XAF;
		case CURRENCY_CODE_CAD:
			return CURRENCY_CAD;
		case CURRENCY_CODE_CVE:
			return CURRENCY_CVE;
		case CURRENCY_CODE_KYD:
			return CURRENCY_KYD;
		case CURRENCY_CODE_CLP:
			return CURRENCY_CLP;
		case CURRENCY_CODE_CLF:
			return CURRENCY_CLF;
		case CURRENCY_CODE_CNY:
			return CURRENCY_CNY;
		case CURRENCY_CODE_COP:
			return CURRENCY_COP;
		case CURRENCY_CODE_KMF:
			return CURRENCY_KMF;
		case CURRENCY_CODE_CDF:
			return CURRENCY_CDF;
		case CURRENCY_CODE_NZD:
			return CURRENCY_NZD;
		case CURRENCY_CODE_CRC:
			return CURRENCY_CRC;
		case CURRENCY_CODE_HRK:
			return CURRENCY_HRK;
		case CURRENCY_CODE_CUP:
			return CURRENCY_CUP;
		case CURRENCY_CODE_CYP:
			return CURRENCY_CYP;
		case CURRENCY_CODE_CZK:
			return CURRENCY_CZK;
		case CURRENCY_CODE_DKK:
			return CURRENCY_DKK;
		case CURRENCY_CODE_DJF:
			return CURRENCY_DJF;
		case CURRENCY_CODE_DOP:
			return CURRENCY_DOP;
		case CURRENCY_CODE_TPE:
			return CURRENCY_TPE;
		case CURRENCY_CODE_IDR:
			return CURRENCY_IDR;
		case CURRENCY_CODE_EGP:
			return CURRENCY_EGP;
		case CURRENCY_CODE_SVC:
			return CURRENCY_SVC;
		case CURRENCY_CODE_ERN:
			return CURRENCY_ERN;
		case CURRENCY_CODE_EEK:
			return CURRENCY_EEK;
		case CURRENCY_CODE_ETB:
			return CURRENCY_ETB;
		case CURRENCY_CODE_FKP:
			return CURRENCY_FKP;
		case CURRENCY_CODE_FJD:
			return CURRENCY_FJD;
		case CURRENCY_CODE_FIM:
			return CURRENCY_FIM;
		case CURRENCY_CODE_XPF:
			return CURRENCY_XPF;
		case CURRENCY_CODE_GMD:
			return CURRENCY_GMD;
		case CURRENCY_CODE_GEL:
			return CURRENCY_GEL;
		case CURRENCY_CODE_DEM:
			return CURRENCY_DEM;
		case CURRENCY_CODE_GHC:
			return CURRENCY_GHC;
		case CURRENCY_CODE_GIP:
			return CURRENCY_GIP;
		case CURRENCY_CODE_GRD:
			return CURRENCY_GRD;
		case CURRENCY_CODE_GTQ:
			return CURRENCY_GTQ;
		case CURRENCY_CODE_GNF:
			return CURRENCY_GNF;
		case CURRENCY_CODE_GWP:
			return CURRENCY_GWP;
		case CURRENCY_CODE_GYD:
			return CURRENCY_GYD;
		case CURRENCY_CODE_HTG:
			return CURRENCY_HTG;
		case CURRENCY_CODE_ITL:
			return CURRENCY_ITL;
		case CURRENCY_CODE_HNL:
			return CURRENCY_HNL;
		case CURRENCY_CODE_HKD:
			return CURRENCY_HKD;
		case CURRENCY_CODE_HUF:
			return CURRENCY_HUF;
		case CURRENCY_CODE_ISK:
			return CURRENCY_ISK;
		case CURRENCY_CODE_XDR:
			return CURRENCY_XDR;
		case CURRENCY_CODE_IRR:
			return CURRENCY_IRR;
		case CURRENCY_CODE_IQD:
			return CURRENCY_IQD;
		case CURRENCY_CODE_IEP:
			return CURRENCY_IEP;
		case CURRENCY_CODE_ILS:
			return CURRENCY_ILS;
		case CURRENCY_CODE_JMD:
			return CURRENCY_JMD;
		case CURRENCY_CODE_JPY:
			return CURRENCY_JPY;
		case CURRENCY_CODE_JOD:
			return CURRENCY_JOD;
		case CURRENCY_CODE_KZT:
			return CURRENCY_KZT;
		case CURRENCY_CODE_KES:
			return CURRENCY_KES;
		case CURRENCY_CODE_KPW:
			return CURRENCY_KPW;
		case CURRENCY_CODE_KRW:
			return CURRENCY_KRW;
		case CURRENCY_CODE_KWD:
			return CURRENCY_KWD;
		case CURRENCY_CODE_KGS:
			return CURRENCY_KGS;
		case CURRENCY_CODE_LAK:
			return CURRENCY_LAK;
		case CURRENCY_CODE_LVL:
			return CURRENCY_LVL;
		case CURRENCY_CODE_LBP:
			return CURRENCY_LBP;
		case CURRENCY_CODE_ZAR:
			return CURRENCY_ZAR;
		case CURRENCY_CODE_LSL:
			return CURRENCY_LSL;
		case CURRENCY_CODE_LRD:
			return CURRENCY_LRD;
		case CURRENCY_CODE_LYD:
			return CURRENCY_LYD;
		case CURRENCY_CODE_CHF:
			return CURRENCY_CHF;
		case CURRENCY_CODE_LTL:
			return CURRENCY_LTL;
		case CURRENCY_CODE_LUF:
			return CURRENCY_LUF;
		case CURRENCY_CODE_MOP:
			return CURRENCY_MOP;
		case CURRENCY_CODE_MKD:
			return CURRENCY_MKD;
		case CURRENCY_CODE_MGF:
			return CURRENCY_MGF;
		case CURRENCY_CODE_MWK:
			return CURRENCY_MWK;
		case CURRENCY_CODE_MYR:
			return CURRENCY_MYR;
		case CURRENCY_CODE_MVR:
			return CURRENCY_MVR;
		case CURRENCY_CODE_MTL:
			return CURRENCY_MTL;
		case CURRENCY_CODE_MRO:
			return CURRENCY_MRO;
		case CURRENCY_CODE_MUR:
			return CURRENCY_MUR;
		case CURRENCY_CODE_MXN:
			return CURRENCY_MXN;
		case CURRENCY_CODE_MXV:
			return CURRENCY_MXV;
		case CURRENCY_CODE_MDL:
			return CURRENCY_MDL;
		case CURRENCY_CODE_MNT:
			return CURRENCY_MNT;
		case CURRENCY_CODE_MAD:
			return CURRENCY_MAD;
		case CURRENCY_CODE_MZM:
			return CURRENCY_MZM;
		case CURRENCY_CODE_MMK:
			return CURRENCY_MMK;
		case CURRENCY_CODE_NAD:
			return CURRENCY_NAD;
		case CURRENCY_CODE_NPR:
			return CURRENCY_NPR;
		case CURRENCY_CODE_NLG:
			return CURRENCY_NLG;
		case CURRENCY_CODE_ANG:
			return CURRENCY_ANG;
		case CURRENCY_CODE_NIO:
			return CURRENCY_NIO;
		case CURRENCY_CODE_NGN:
			return CURRENCY_NGN;
		case CURRENCY_CODE_OMR:
			return CURRENCY_OMR;
		case CURRENCY_CODE_PKR:
			return CURRENCY_PKR;
		case CURRENCY_CODE_PAB:
			return CURRENCY_PAB;
		case CURRENCY_CODE_PGK:
			return CURRENCY_PGK;
		case CURRENCY_CODE_PYG:
			return CURRENCY_PYG;
		case CURRENCY_CODE_PEN:
			return CURRENCY_PEN;
		case CURRENCY_CODE_PHP:
			return CURRENCY_PHP;
		case CURRENCY_CODE_PLN:
			return CURRENCY_PLN;
		case CURRENCY_CODE_PTE:
			return CURRENCY_PTE;
		case CURRENCY_CODE_QAR:
			return CURRENCY_QAR;
		case CURRENCY_CODE_ROL:
			return CURRENCY_ROL;
		case CURRENCY_CODE_RUR:
			return CURRENCY_RUR;
		case CURRENCY_CODE_RUB:
			return CURRENCY_RUB;
		case CURRENCY_CODE_RWF:
			return CURRENCY_RWF;
		case CURRENCY_CODE_SHP:
			return CURRENCY_SHP;
		case CURRENCY_CODE_WST:
			return CURRENCY_WST;
		case CURRENCY_CODE_STD:
			return CURRENCY_STD;
		case CURRENCY_CODE_SAR:
			return CURRENCY_SAR;
		case CURRENCY_CODE_SCR:
			return CURRENCY_SCR;
		case CURRENCY_CODE_SLL:
			return CURRENCY_SLL;
		case CURRENCY_CODE_SGD:
			return CURRENCY_SGD;
		case CURRENCY_CODE_SKK:
			return CURRENCY_SKK;
		case CURRENCY_CODE_SIT:
			return CURRENCY_SIT;
		case CURRENCY_CODE_SBD:
			return CURRENCY_SBD;
		case CURRENCY_CODE_SOS:
			return CURRENCY_SOS;
		case CURRENCY_CODE_LKR:
			return CURRENCY_LKR;
		case CURRENCY_CODE_SDD:
			return CURRENCY_SDD;
		case CURRENCY_CODE_SRG:
			return CURRENCY_SRG;
		case CURRENCY_CODE_SZL:
			return CURRENCY_SZL;
		case CURRENCY_CODE_SEK:
			return CURRENCY_SEK;
		case CURRENCY_CODE_SYP:
			return CURRENCY_SYP;
		case CURRENCY_CODE_TWD:
			return CURRENCY_TWD;
		case CURRENCY_CODE_TJS:
			return CURRENCY_TJS;
		case CURRENCY_CODE_TZS:
			return CURRENCY_TZS;
		case CURRENCY_CODE_THB:
			return CURRENCY_THB;
		case CURRENCY_CODE_TOP:
			return CURRENCY_TOP;
		case CURRENCY_CODE_TTD:
			return CURRENCY_TTD;
		case CURRENCY_CODE_TND:
			return CURRENCY_TND;
		case CURRENCY_CODE_TRL:
			return CURRENCY_TRL;
		case CURRENCY_CODE_TMM:
			return CURRENCY_TMM;
		case CURRENCY_CODE_UGX:
			return CURRENCY_UGX;
		case CURRENCY_CODE_UAH:
			return CURRENCY_UAH;
		case CURRENCY_CODE_AED:
			return CURRENCY_AED;
		case CURRENCY_CODE_GBP:
			return CURRENCY_GBP;
		case CURRENCY_CODE_USS:
			return CURRENCY_USS;
		case CURRENCY_CODE_USN:
			return CURRENCY_USN;
		case CURRENCY_CODE_UYU:
			return CURRENCY_UYU;
		case CURRENCY_CODE_UZS:
			return CURRENCY_UZS;
		case CURRENCY_CODE_VUV:
			return CURRENCY_VUV;
		case CURRENCY_CODE_VND:
			return CURRENCY_VND;
		case CURRENCY_CODE_YER:
			return CURRENCY_YER;
		case CURRENCY_CODE_YUM:
			return CURRENCY_YUM;
		case CURRENCY_CODE_ZMK:
			return CURRENCY_ZMK;
		case CURRENCY_CODE_ZWD:
			return CURRENCY_ZWD;
		//case CURRENCY_CODE_XAU:
			//return CURRENCY_XAU;
		//case CURRENCY_CODE_XXX:
			//return CURRENCY_XXX;
		default:
			return CURRENCY_UNKNOWN;
		}
	}
}
