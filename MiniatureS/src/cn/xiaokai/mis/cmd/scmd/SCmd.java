package cn.xiaokai.mis.cmd.scmd;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class SCmd {
	/**
	 * 返回商店命令的帮助文本
	 * 
	 * @return
	 */
	public static String getHelpString() {
		return TextFormat.YELLOW + "===" + TextFormat.GOLD + "===" + TextFormat.WHITE + "[" + TextFormat.BLUE + "Help"
				+ TextFormat.WHITE + "]" + TextFormat.GOLD + "===\n" + TextFormat.YELLOW + "===" + TextFormat.WHITE
				+ "/ms help " + TextFormat.YELLOW + "打开商店帮助\n" + TextFormat.WHITE + "/ms " + TextFormat.YELLOW
				+ "打开商店主页(游戏内)\n" + TextFormat.WHITE + "/ms ui " + TextFormat.YELLOW + "查看商店列表\n" + TextFormat.YELLOW
				+ "===" + TextFormat.GOLD + "===" + TextFormat.WHITE + "[" + TextFormat.BLUE + "Help" + TextFormat.WHITE
				+ "]" + TextFormat.GOLD + "===" + TextFormat.YELLOW + "===";
	}

	public static String getShopShowList() {
		Map<String, Object> Config = MiniatureS.mis.ShopListConfig.getAll();
		if (Config.get("Buttons") != null && Config.get("Buttons") instanceof Map) {
			HashMap<String, Object> ButtonList = (HashMap<String, Object>) Config.get("Buttons");
			String s = "";
			for (String string : ButtonList.keySet())
				s += TextFormat.YELLOW + "|" + TextFormat.WHITE + "Key: " + TextFormat.BLUE
						+ ((HashMap<String, Object>) ButtonList.get(string)).get("Text") + "\n";
			String st = Tool.getColorFont("==========");
			s = st + TextFormat.WHITE + "[" + Tool.getColorFont(MiniatureS.mis.getName()) + TextFormat.WHITE + "-"
					+ TextFormat.BLUE + "Shop" + TextFormat.WHITE + "]" + st + "\n" + s + st + TextFormat.WHITE + "["
					+ Tool.getColorFont(MiniatureS.mis.getName()) + TextFormat.BLUE + "Shop" + TextFormat.WHITE + "]"
					+ st;
			return s;
		} else {
			return TextFormat.RED + "当前暂无任何商店！";
		}
	}
}
