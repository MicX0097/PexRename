package com.micx.pexrename.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PermissionsUtils {

    private static final Map<Character, String> formatPermissions = new HashMap<>();

    static {
        formatPermissions.put('l', "pex.rename.format.bold");
        formatPermissions.put('m', "pex.rename.format.strikethrough");
        formatPermissions.put('n', "pex.rename.format.underline");
        formatPermissions.put('o', "pex.rename.format.italic");
        formatPermissions.put('k', "pex.rename.format.obfuscated");
        formatPermissions.put('r', "pex.rename.format.reset");

        for (char i = '0'; i <= 'f'; i++) {
            formatPermissions.put(i, "pex.rename.color." + i);
        }
    }

    public static boolean hasPermissionForFormatting(Player player, String text) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&' && i + 1 < text.length()) {
                char code = text.charAt(i + 1);
                if (formatPermissions.containsKey(code) && !player.hasPermission(formatPermissions.get(code))) {
                    return false;
                }
            }
        }
        return true;
    }
}
