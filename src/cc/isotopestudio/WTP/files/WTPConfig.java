/*
 * Copyright (c) 2016. ISOTOPE Studio
 */

package cc.isotopestudio.WTP.files;

import cc.isotopestudio.WTP.WTP;

import java.util.HashMap;
import java.util.Set;

import static cc.isotopestudio.WTP.WTP.plugin;

public class WTPConfig {

    public static double createFee;
    public static double aliasFee;
    public static double welcomeFee;
    public static double relocationFee;
    private static double deleteFee;
    public static HashMap<String, Integer> limitation;

    public static void update() {
        createFee = plugin.getConfig().getDouble("Price.create");
        aliasFee = plugin.getConfig().getDouble("Price.alias");
        welcomeFee = plugin.getConfig().getDouble("Price.welcome");
        relocationFee = plugin.getConfig().getDouble("Price.relocation");
        deleteFee = plugin.getConfig().getDouble("Price.delete");

        limitation = new HashMap<>();
        limitation.put("default", 0);
        limitation.put("admin", -1);
        Set<String> limitSet = plugin.getConfig().getKeys(true);
        for (String temp : limitSet) {
            String tempSplit[] = temp.split("[.]");
            if (tempSplit.length > 2 && tempSplit[0].equals("Limitation") && tempSplit[1].equals("create")) {
                plugin.getLogger().info(temp);
                int tempLimit = plugin.getConfig().getInt(temp);
                limitation.put(tempSplit[2], tempLimit);
            }
        }
    }

    public static int getLimit(String name) {
        try {
            return limitation.get(name);
        } catch (Exception e) {
            return 0;
        }
    }
}