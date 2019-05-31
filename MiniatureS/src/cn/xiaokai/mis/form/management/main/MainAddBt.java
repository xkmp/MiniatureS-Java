package cn.xiaokai.mis.form.management.main;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.msg.Message;
import cn.xiaokai.mis.tool.Tool;
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MainAddBt {
	private MiniatureS mis;

	/**
	 * 在主页添加按钮
	 */
	public MainAddBt() {
		this.mis = MiniatureS.mis;
	}

	/**
	 * 在主页添加一个点击后可以再次打开一个新界面的按钮
	 * 
	 * @param player    添加这个按钮的玩家对象对象
	 * @param Button    这个按钮的文本内容
	 * @param FileName  要打开的界面的配置文件名称（仅文件名）
	 * @param Money     点击这个按钮需要花费的钱
	 * @param Command   点击后会执行的命令
	 * @param ImageType 按钮的贴图类型
	 * @param ImagePath 按钮的贴图路径
	 */
	public void addOpenWindow(Player player, String Button, String FileName, int Money, String Command,
			boolean ImageType, String ImagePath) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("Text", Button);
		map.put("Type", "OpenWindows");
		map.put("Command", Command);
		map.put("FileName", FileName);
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, FileName);
		if (!file.exists()) {
			Config config = new Config(file, Config.YAML);
			map = new LinkedHashMap<String, Object>();
			map.put("Title", Button);
			map.put("Content", Button);
			map.put("Player", player.getName());
			map.put("Time", Tool.getDate() + " " + Tool.getTime());
			map.put("Buttons", new HashMap<String, Object>());
			config.setAll(map);
			config.save();
		}
		player.sendMessage(TextFormat.GREEN + "您成功创建一个点击后打开新界面的按钮！Key：" + Key);
	}

	/**
	 * 在主页添加一个点击后打开一个商店分页的按钮
	 * 
	 * @param player    添加这个按钮的玩家对象
	 * @param Button    按钮的文本内容
	 * @param Shop      商店的Key
	 * @param Money     点击后要扣除的钱，为空或小于等于零时不启用
	 * @param Command   点击后要执行的命令，为空时不启用
	 * @param ImageType 按钮的贴图类型
	 * @param ImagePath 按钮的贴图路径
	 */
	public void addOpenShop(Player player, String Button, String Shop, int Money, String Command, boolean ImageType,
			String ImagePath) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		HashMap<String, Object> map = new HashMap<>();
		map.put("Text", Button);
		map.put("Shop", Shop);
		map.put("Type", "OpenShop");
		map.put("Command", Command);
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		player.sendMessage(TextFormat.GREEN + "您成功创建一个点击后传送万家的按钮！Key：" + Key);
	}

	/**
	 * 在主页添加一个点击后传送玩家的按钮
	 * 
	 * @param player    添加这个按钮的玩家对象
	 * @param Button    按钮的文本内容
	 * @param x         X坐标
	 * @param y         Y坐标
	 * @param z         Z坐标
	 * @param Money     点击后要扣除的钱数量，为空或小于等于零时不启用
	 * @param Level     要传送的目标世界
	 * @param Command   点击后执行的命令，为空时不启用
	 * @param ImageType 按钮的贴图类型
	 * @param ImagePath 按钮的贴图路径
	 */
	public void addTransfer(Player player, String Button, int x, int y, int z, int Money, String Level, String Command,
			boolean ImageType, String ImagePath) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		HashMap<String, Object> map = new HashMap<>();
		map.put("Text", Button);
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
		map.put("Level", Level);
		map.put("Type", "Transfer");
		map.put("Command", Command);
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		player.sendMessage(TextFormat.GREEN + "您成功创建一个点击后传送万家的按钮！Key：" + Key);
	}

	/**
	 * 在主页添加一个点击后执行命令的按钮
	 * 
	 * @param player    添加这个按钮的玩家对象
	 * @param Command   点击后要执行的命令
	 * @param Msg       命令参数列表
	 * @param Money     点击要扣除的钱 为空或小于等于零时不启用
	 * @param ImageType 按钮贴图类型
	 * @param ImagePath 按钮贴图路径
	 * @param Commander 点击后执行命令的对象[Player|Console]
	 */
	public void addCommand(Player player, String Button, String Command, List<String> Msg, int Money, boolean ImageType,
			String ImagePath, String Commander) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		HashMap<String, Object> map = new HashMap<>();
		map.put("Text", Button);
		map.put("Type", "Command");
		map.put("Command", Command);
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Msg", Msg);
		map.put("Commander",
				((Commander.toLowerCase() != "player" && Commander.toLowerCase() != "console") ? "Player" : Commander));
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		player.sendMessage(TextFormat.GREEN + "您成功创建一个点击后执行命令的按钮！Key：" + Key);
	}

	/**
	 * 在主页添加一个点击后弹出提示的按钮
	 * 
	 * @param player    添加这个按钮的玩家对象
	 * @param Button    按钮的文本内容
	 * @param Contxt    弹出的提示窗的内容
	 * @param Bt1       弹出的提示窗按钮1的文本内容
	 * @param Bt2       弹出的提示窗按钮2的文本内容
	 * @param Money     点击后会扣除的钱 为空或小于等于零时不启用
	 * @param ImageType 按钮的贴图类型
	 * @param ImagePath 按钮的贴图路径
	 * @param Command   点击按钮后会执行的命令 为空时不启用
	 */
	public void addTip(Player player, String Button, String Contxt, String Bt1, String Bt2, int Money,
			boolean ImageType, String ImagePath, String Command) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		HashMap<String, Object> map = new HashMap<>();
		map.put("Type", "Make");
		map.put("Text", Message.reloadString(Button));
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Content", Message.reloadString(Contxt));
		map.put("bt1", Message.reloadString(Bt1));
		map.put("bt2", Message.reloadString(Bt2));
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Command", Command == "null" ? null : Message.reloadString(Command));
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		player.sendMessage(TextFormat.GREEN + "您成功创建一个提示框类型的按钮！Key：" + Key);
	}

	/**
	 * 随机获取一个不会重复的按钮key
	 * 
	 * @return 按钮的key
	 */
	public static String getButtonKey() {
		String Key = "";
		int lengtjh = Tool.getRand(5, 20);
		for (int i = 0; i < lengtjh; i++)
			Key += Tool.getRandString();
		if ((MiniatureS.mis.Menus.get("Buttons") instanceof Map)
				&& ((HashMap<String, Object>) MiniatureS.mis.Menus.get("Buttons")).containsKey(Key))
			return getButtonKey();
		return Key;
	}
}
