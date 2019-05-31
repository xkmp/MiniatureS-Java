package cn.xiaokai.mis.myshop.seek;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.myshop.TonsFx;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MySeek {
	private MiniatureS mis;
	private Player player;

	/**
	 * 个人商店搜索商品
	 * 
	 * @param player 要搜索的玩家对象
	 */
	public MySeek(Player player) {
		this.player = player;
		this.mis = MiniatureS.mis;
	}

	/**
	 * 开始搜索，并且将搜索结果已文本的方式返回
	 * 
	 * @param player   控制台对象
	 * @param ID       要搜索的物品ID
	 * @param ShopType 要搜索的商品的类型
	 */
	public static void MsgSeek(CommandSender player, String ID, String ShopType) {
		File Dir = new File(MiniatureS.mis.getDataFolder() + MiniatureS.MyShopConfigPath);
		String SB_FFF = "";
		int Count = 0;
		for (String fileName : Dir.list()) {
			File file = new File(Dir, fileName);
			Config config = new Config(file, Config.YAML);
			if (!(config.get("Items") instanceof Map)) {
				file.delete();
				continue;
			}
			HashMap<String, Object> items = (HashMap<String, Object>) config.get("Items");
			for (String ItemKey : items.keySet()) {
				HashMap<String, Object> item = (HashMap<String, Object>) items.get(ItemKey);
				if ((ShopType.toLowerCase().equals("all")
						|| ShopType.toLowerCase().equals(String.valueOf(item.get("Type")).toLowerCase()))
						&& (Tool.isMateID(ID, String.valueOf(item.get("Item"))))) {
					Count++;
					SB_FFF += TextFormat.BLUE + "店家：" + TextFormat.DARK_AQUA + String.valueOf(item.get("Player"))
							+ TextFormat.LIGHT_PURPLE + " 商品：" + TextFormat.YELLOW
							+ ItemIDSunName.getIDByName(String.valueOf(item.get("Item"))) + TextFormat.LIGHT_PURPLE
							+ " 价格：" + TextFormat.DARK_BLUE
							+ Float.valueOf(String.valueOf(item.get("Money"))).intValue() + TextFormat.LIGHT_PURPLE
							+ "  类型：" + TextFormat.GOLD
							+ (String.valueOf(item.get("Type")).toLowerCase().equals("sell") ? "出售" : "回收") + "\n";
				}
			}
		}
		if (Count < 1) {
			player.sendMessage(TextFormat.RED + "未找到相关项目！请更改搜索条件重试！");
			return;
		}
		player.sendMessage(TextFormat.GREEN + "===" + TextFormat.GOLD + "===" + TextFormat.YELLOW + "==="
				+ TextFormat.WHITE + "[" + Tool.getColorFont("搜索结果") + TextFormat.WHITE + TextFormat.YELLOW + "==="
				+ TextFormat.GOLD + "===" + TextFormat.GREEN + "===\n" + SB_FFF + "\n" + TextFormat.GREEN + "==="
				+ TextFormat.GOLD + "===" + TextFormat.YELLOW + "===" + TextFormat.WHITE + "["
				+ Tool.getColorFont("搜索结果") + TextFormat.WHITE + "]" + TextFormat.YELLOW + "===" + TextFormat.GOLD
				+ "===" + TextFormat.GREEN + "===\n" + TextFormat.GREEN + "若想交易请在游戏内使用UI完成！");
	}

	/**
	 * 开始搜索，并且将搜索结果已UI的方式返回
	 * 
	 * @param ID       要搜索的物品ID
	 * @param ShopType 要搜索的商品的类型
	 */
	public void FormSeek(String ID, String ShopType) {
		File Dir = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath);
		TonsFx fx = new TonsFx();
		ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
		List<ElementButton> list = new ArrayList<ElementButton>();
		for (String fileName : Dir.list()) {
			File file = new File(Dir, fileName);
			Config config = new Config(file, Config.YAML);
			if (!(config.get("Items") instanceof Map)) {
				file.delete();
				continue;
			}
			HashMap<String, Object> items = (HashMap<String, Object>) config.get("Items");
			for (String ItemKey : items.keySet()) {
				HashMap<String, Object> item = (HashMap<String, Object>) items.get(ItemKey);
				if ((ShopType.toLowerCase().equals("all")
						|| ShopType.toLowerCase().equals(String.valueOf(item.get("Type")).toLowerCase()))
						&& (Tool.isMateID(ID, String.valueOf(item.get("Item"))))) {
					item.put("FFF_SB", file);
					maps.add(item);
					list.add(
							new ElementButton(
									TextFormat.DARK_AQUA + String.valueOf(item.get("Player")) + TextFormat.LIGHT_PURPLE
											+ "|" + TextFormat.YELLOW
											+ ItemIDSunName.getIDByName(String.valueOf(item.get("Item")))
											+ TextFormat.LIGHT_PURPLE + "|" + TextFormat.DARK_BLUE
											+ Float.valueOf(String.valueOf(item.get("Money"))).intValue()
											+ TextFormat.LIGHT_PURPLE + "|" + TextFormat.GOLD
											+ (String.valueOf(item.get("Type")).toLowerCase().equals("sell") ? "出售"
													: "回收"),
									new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
											ItemIDSunName.getIDByPath(String.valueOf(item.get("Item"))))));
				}
			}
		}
		if (list.size() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "未找到相关项目！请更改搜索条件重试！");
			return;
		}
		fx.Seek = maps;
		mis.MyShopData.put(player.getName(), fx);
		player.showFormWindow(new FormWindowSimple(
				Tool.getColorFont(mis.getName()) + TextFormat.WHITE + "-" + Tool.getColorFont(player.getName())
						+ TextFormat.WHITE + "-" + Tool.getColorFont("搜索结果"),
				TextFormat.GREEN + "搜索完毕！共搜索到 " + TextFormat.WHITE + list.size() + TextFormat.GREEN + " 个项目", list),
				MakeID.MyShopFormSeek.getID());
	}
}
