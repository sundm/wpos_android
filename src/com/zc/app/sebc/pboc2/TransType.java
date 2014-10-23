package com.zc.app.sebc.pboc2;

import com.zc.app.sebc.util.BasicUtil;

//see GB/T 15150-94, ISO8583-1987

public final class TransType {

	public final static int TYPE_00 = 0;
	public final static int TYPE_01 = 1;
	public final static int TYPE_02 = 2;
	public final static int TYPE_03 = 3;
	public final static int TYPE_04 = 4;
	public final static int TYPE_05 = 5;
	public final static int TYPE_06 = 6;
	public final static int TYPE_07 = 7;
	public final static int TYPE_08 = 8;
	public final static int TYPE_09 = 9;
	public final static int TYPE_10 = 10;
	public final static int TYPE_11 = 11;
	public final static int TYPE_12 = 12;
	public final static int TYPE_13 = 13;
	public final static int TYPE_14 = 14;
	public final static int TYPE_15 = 15;
	public final static int TYPE_16 = 16;
	public final static int TYPE_17 = 17;
	public final static int TYPE_18 = 18;
	public final static int TYPE_19 = 19;
	public final static int TYPE_20 = 20;
	public final static int TYPE_21 = 21;
	public final static int TYPE_22 = 22;
	public final static int TYPE_23 = 23;
	public final static int TYPE_24 = 24;
	public final static int TYPE_25 = 25;
	public final static int TYPE_26 = 26;
	public final static int TYPE_27 = 27;
	public final static int TYPE_28 = 28;
	public final static int TYPE_29 = 29;
	public final static int TYPE_30 = 30;
	public final static int TYPE_31 = 31;
	public final static int TYPE_32 = 32;
	public final static int TYPE_33 = 33;
	public final static int TYPE_34 = 34;
	public final static int TYPE_35 = 35;
	public final static int TYPE_36 = 36;
	public final static int TYPE_37 = 37;
	public final static int TYPE_38 = 38;
	public final static int TYPE_39 = 39;
	public final static int TYPE_40 = 40;
	public final static int TYPE_41 = 41;
	public final static int TYPE_42 = 42;
	public final static int TYPE_43 = 43;
	public final static int TYPE_44 = 44;
	public final static int TYPE_45 = 45;
	public final static int TYPE_46 = 46;
	public final static int TYPE_47 = 47;
	public final static int TYPE_48 = 48;
	public final static int TYPE_49 = 49;
	public final static int TYPE_50 = 50;
	public final static int TYPE_51 = 51;
	public final static int TYPE_52 = 52;
	public final static int TYPE_53 = 53;
	public final static int TYPE_54 = 54;
	public final static int TYPE_55 = 55;
	public final static int TYPE_56 = 56;
	public final static int TYPE_57 = 57;
	public final static int TYPE_58 = 58;
	public final static int TYPE_59 = 59;
	public final static int TYPE_60 = 60;
	public final static int TYPE_61 = 61;
	public final static int TYPE_62 = 62;
	public final static int TYPE_63 = 63;
	public final static int TYPE_64 = 64;
	public final static int TYPE_65 = 65;
	public final static int TYPE_66 = 66;
	public final static int TYPE_67 = 67;
	public final static int TYPE_68 = 68;
	public final static int TYPE_69 = 69;
	public final static int TYPE_70 = 70;
	public final static int TYPE_71 = 71;
	public final static int TYPE_72 = 72;
	public final static int TYPE_73 = 73;
	public final static int TYPE_74 = 74;
	public final static int TYPE_75 = 75;
	public final static int TYPE_76 = 76;
	public final static int TYPE_77 = 77;
	public final static int TYPE_78 = 78;
	public final static int TYPE_79 = 79;
	public final static int TYPE_80 = 80;
	public final static int TYPE_81 = 81;
	public final static int TYPE_82 = 82;
	public final static int TYPE_83 = 83;
	public final static int TYPE_84 = 84;
	public final static int TYPE_85 = 85;
	public final static int TYPE_86 = 86;
	public final static int TYPE_87 = 87;
	public final static int TYPE_88 = 88;
	public final static int TYPE_89 = 89;
	public final static int TYPE_90 = 90;
	public final static int TYPE_91 = 91;
	public final static int TYPE_92 = 92;
	public final static int TYPE_93 = 93;
	public final static int TYPE_94 = 94;
	public final static int TYPE_95 = 95;
	public final static int TYPE_96 = 96;
	public final static int TYPE_97 = 97;
	public final static int TYPE_98 = 98;
	public final static int TYPE_99 = 99;
	
	public final static String TYPE_NAME_34 = "34";
	
	public final static String READ_TYPE_00 = "商品和服务";
	public final static String READ_TYPE_01 = "提款/现金付款";
	public final static String READ_TYPE_02 = "调整";
	public final static String READ_TYPE_03 = "担保支票";
	public final static String READ_TYPE_04 = "核实支票";
	public final static String READ_TYPE_05 = "欧洲支票";
	public final static String READ_TYPE_06 = "旅行支票";
	public final static String READ_TYPE_07 = "信用证";
	public final static String READ_TYPE_08 = "直接转帐";
	public final static String READ_TYPE_09 = "伴有现金支付的商品和服务";
	public final static String READ_TYPE_10_13 = "保留给ISO使用";
	public final static String READ_TYPE_14_16 = "保留给国家使用";	
	public final static String READ_TYPE_17_19 = "保留给民间使用";	
	public final static String READ_TYPE_20 = "还款";
	public final static String READ_TYPE_21 = "存款";
	public final static String READ_TYPE_22 = "调整";
	public final static String READ_TYPE_23 = "支票存款担保";
	public final static String READ_TYPE_24 = "支票存款";
	public final static String READ_TYPE_25_26 = "保留给ISO使用";	
	public final static String READ_TYPE_27 = "保留给国家使用";	
	public final static String READ_TYPE_28_29 = "保留给民间使用";
	public final static String READ_TYPE_30 = "查询可用资金";
	public final static String READ_TYPE_31 = "查询余额";
	public final static String READ_TYPE_32_35 = "保留给ISO使用";
	public final static String READ_TYPE_36_37 = "保留给国家使用";	
	public final static String READ_TYPE_38_39 = "保留给民间使用";
	public final static String READ_TYPE_40 = "持卡人帐户转帐";
	public final static String READ_TYPE_41_45 = "保留给ISO使用";
	public final static String READ_TYPE_46_47 = "保留给国家使用";	
	public final static String READ_TYPE_48_49 = "保留给民间使用";
	public final static String READ_TYPE_50_79 = "保留给ISO使用";
	public final static String READ_TYPE_80_89 = "保留给国家使用";	
	public final static String READ_TYPE_90_99 = "保留给民间使用";
	
	public final static String READ_TYPE_UNKNOWN = "未知";
	
	//based on practical test on CCB(China Construction Bank) card
	public final static String READ_TYPE_60 = "电子现金圈存";
	
	public static String toReadableTransactionType(String type) {
		
		if(BasicUtil.isEmptyString(type)) {
			return null;
		}
		
		if(type.length() > 2) {
			return READ_TYPE_UNKNOWN;
		}
		
		int typeId = Integer.parseInt(type);
		
		switch(typeId) {
		case TransType.TYPE_00:
			return TransType.READ_TYPE_00;
		case TransType.TYPE_01:
			return TransType.READ_TYPE_01;
		case TransType.TYPE_02:
			return TransType.READ_TYPE_02;
		case TransType.TYPE_03:
			return TransType.READ_TYPE_03;
		case TransType.TYPE_04:
			return TransType.READ_TYPE_04;
		case TransType.TYPE_05:
			return TransType.READ_TYPE_05;
		case TransType.TYPE_06:
			return TransType.READ_TYPE_06;
		case TransType.TYPE_07:
			return TransType.READ_TYPE_07;
		case TransType.TYPE_08:
			return TransType.READ_TYPE_08;
		case TransType.TYPE_09:
			return TransType.READ_TYPE_09;
			
		case TransType.TYPE_10:
		case TransType.TYPE_11:
		case TransType.TYPE_12:
		case TransType.TYPE_13:
			return READ_TYPE_10_13;
			
		case TransType.TYPE_14:
		case TransType.TYPE_15:
		case TransType.TYPE_16:
			return READ_TYPE_14_16;
		
		case TransType.TYPE_17:
		case TransType.TYPE_18:
		case TransType.TYPE_19:
			return READ_TYPE_17_19;	
			
		case TransType.TYPE_20:
			return TransType.READ_TYPE_20;
		case TransType.TYPE_21:
			return TransType.READ_TYPE_21;
		case TransType.TYPE_22:
			return TransType.READ_TYPE_22;
		case TransType.TYPE_23:
			return TransType.READ_TYPE_23;
		case TransType.TYPE_24:
			return TransType.READ_TYPE_24;
			
		case TransType.TYPE_25:
		case TransType.TYPE_26:
			return READ_TYPE_25_26;	
			
		case TransType.TYPE_27:
			return READ_TYPE_27;	
		
		case TransType.TYPE_28:
		case TransType.TYPE_29:
			return READ_TYPE_28_29;
			
		case TransType.TYPE_30:
			return TransType.READ_TYPE_30;
		case TransType.TYPE_31:
			return TransType.READ_TYPE_31;
			
		case TransType.TYPE_32:
		case TransType.TYPE_33:
		case TransType.TYPE_34:
		case TransType.TYPE_35:
			return READ_TYPE_32_35;
			
		case TransType.TYPE_36:
		case TransType.TYPE_37:
			return READ_TYPE_36_37;
			
		case TransType.TYPE_38:
		case TransType.TYPE_39:
			return READ_TYPE_38_39;
			
		case TransType.TYPE_40:
			return TransType.READ_TYPE_40;
		
		case TransType.TYPE_41:
		case TransType.TYPE_42:
		case TransType.TYPE_43:
		case TransType.TYPE_44:
		case TransType.TYPE_45:
			return TransType.READ_TYPE_41_45;
		
		case TransType.TYPE_46:
		case TransType.TYPE_47:
			return TransType.READ_TYPE_46_47;
		
		case TransType.TYPE_48:
		case TransType.TYPE_49:
			return TransType.READ_TYPE_48_49;
			
		case TransType.TYPE_50:
		case TransType.TYPE_51:
		case TransType.TYPE_52:
		case TransType.TYPE_53:
		case TransType.TYPE_54:
		case TransType.TYPE_55:
		case TransType.TYPE_56:
		case TransType.TYPE_57:
		case TransType.TYPE_58:
		case TransType.TYPE_59:
		//case TransType.TYPE_60:
		case TransType.TYPE_61:
		case TransType.TYPE_62:
		case TransType.TYPE_63:
		case TransType.TYPE_64:
		case TransType.TYPE_65:
		case TransType.TYPE_66:
		case TransType.TYPE_67:
		case TransType.TYPE_68:
		case TransType.TYPE_69:
		case TransType.TYPE_70:
		case TransType.TYPE_71:
		case TransType.TYPE_72:
		case TransType.TYPE_73:
		case TransType.TYPE_74:
		case TransType.TYPE_75:
		case TransType.TYPE_76:
		case TransType.TYPE_77:
		case TransType.TYPE_78:
		case TransType.TYPE_79:
			return TransType.READ_TYPE_50_79;
			
		case TransType.TYPE_80:
		case TransType.TYPE_81:
		case TransType.TYPE_82:
		case TransType.TYPE_83:
		case TransType.TYPE_84:
		case TransType.TYPE_85:
		case TransType.TYPE_86:
		case TransType.TYPE_87:
		case TransType.TYPE_88:
		case TransType.TYPE_89:
			return TransType.READ_TYPE_80_89;
			
		case TransType.TYPE_90:
		case TransType.TYPE_91:
		case TransType.TYPE_92:
		case TransType.TYPE_93:
		case TransType.TYPE_94:
		case TransType.TYPE_95:
		case TransType.TYPE_96:
		case TransType.TYPE_97:
		case TransType.TYPE_98:
		case TransType.TYPE_99:
			return TransType.READ_TYPE_90_99;	
			
		//based on practical test
		case TransType.TYPE_60:
			return TransType.READ_TYPE_60;
			
		default:
			return TransType.READ_TYPE_UNKNOWN;
		}
	}
}
