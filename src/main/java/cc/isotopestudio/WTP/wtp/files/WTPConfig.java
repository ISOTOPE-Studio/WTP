package cc.isotopestudio.WTP.wtp.files;

import cc.isotopestudio.WTP.wtp.WTP;

public class WTPConfig {

	public static double createFee;
	public static double aliasFee;
	public static double welcomeFee;
	public static double relocationFee;
	public static double deleteFee;
	public static int defaultLimit;
	public static int VIP1Limit;
	public static int VIP2Limit;
	public static int VIP3Limit;
	public static int VIP4Limit;
	public static int VIP5Limit;
	public static int adminLimit;

	public static void update(WTP plugin) {
		createFee = plugin.getConfig().getDouble("Price.create");
		aliasFee = plugin.getConfig().getDouble("Price.alias");
		welcomeFee = plugin.getConfig().getDouble("Price.welcome");
		relocationFee = plugin.getConfig().getDouble("Price.relocation");
		deleteFee = plugin.getConfig().getDouble("Price.delete");

		defaultLimit = plugin.getConfig().getInt("Limitation.default");
		VIP1Limit = plugin.getConfig().getInt("Limitation.create.VIP1");
		VIP2Limit = plugin.getConfig().getInt("Limitation.create.VIP2");
		VIP3Limit = plugin.getConfig().getInt("Limitation.create.VIP3");
		VIP4Limit = plugin.getConfig().getInt("Limitation.create.VIP4");
		VIP5Limit = plugin.getConfig().getInt("Limitation.create.VIP5");
		adminLimit = plugin.getConfig().getInt("Limitation.create.admin");
	}

	public static int getLimit(int type) {
		switch (type) {
		case (1): {
			return VIP1Limit;
		}
		case (2): {
			return VIP2Limit;
		}
		case (3): {
			return VIP3Limit;
		}
		case (4): {
			return VIP4Limit;
		}
		case (5): {
			return VIP5Limit;
		}
		case (6): {
			return adminLimit;
		}
		default: {
			return defaultLimit;
		}
		}
	}
}