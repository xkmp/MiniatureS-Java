package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.Tool;

@SuppressWarnings("unchecked")
public class MakeMyShopForm {
	private Player player;
	private MiniatureS mis;

	/**
	 * 创建个人商店的UI
	 * 
	 * @param player 要显示UI的玩家对象
	 */
	public MakeMyShopForm(Player player) {
		this.player = player;
		this.mis = MiniatureS.mis;
	}

	/**
	 * 个人商店主页
	 */
	public void MakeMain() {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		File file = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath);
		List<ElementButton> buttons = new ArrayList<ElementButton>();
		ArrayList<File> files = new ArrayList<>();
		for (String f : file.list()) {
			File fx = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath, f);
			Config config = new Config(fx, Config.YAML);
			if (!(config.get("Items") instanceof Map)) {
				fx.delete();
				continue;
			}
			buttons.add(new ElementButton(Tool.getRandColor() + config.getString("Player") + TextFormat.WHITE + " 商品："
					+ TextFormat.YELLOW + ((HashMap<String, Object>) config.get("Items")).size()));
			files.add(fx);
		}
		mis.MyShopPlayerList.put(player.getName(), files);
		buttons.add(new ElementButton("上架物品"));
		player.showFormWindow(
				new FormWindowSimple(
						Tool.getColorFont(mis.getName()) + TextFormat.WHITE + "-" + TextFormat.YELLOW + "MyShop",
						buttons.size() < 1 ? (TextFormat.RED + "暂无任何人上架物品！快来添加一个吧~") : "", buttons),
				MakeID.MyShopMain.getID());
	}
}
