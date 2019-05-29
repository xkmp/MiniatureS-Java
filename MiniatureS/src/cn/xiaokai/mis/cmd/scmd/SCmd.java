package cn.xiaokai.mis.cmd.scmd;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;

@SuppressWarnings("unchecked")
public class SCmd {
	/**
	 * 返回商店命令的帮助文本
	 * 
	 * @return
	 */
	public static String getHelpString() {
		return null;
	}

	public static String getShopShowList() {
		Map<String, Object> Config = MiniatureS.mis.ShopListConfig.getAll();
		if (Config.get("Buttons") != null && Config.get("Buttons") instanceof Map) {
			HashMap<String, Object> ButtonList = (HashMap<String, Object>) Config.get("Buttons");
			String s = "";
			for (String string : ButtonList.keySet()) {
				s += TextFormat.YELLOW + "|Key: ";
			}
			return s;
		} else {
			return TextFormat.RED + "当前暂无任何商店！";
		}
	}
}
