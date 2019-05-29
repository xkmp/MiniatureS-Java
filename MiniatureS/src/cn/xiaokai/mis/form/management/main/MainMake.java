package cn.xiaokai.mis.form.management.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;

@SuppressWarnings("unchecked")
public class MainMake {
	private MiniatureS mis;

	/**
	 * 当主页要添加按钮时，选择好了按钮类型后创建对应的界面
	 * 
	 * @param mis 插件主类对象
	 */
	public MainMake(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 在主页创建一个点击后打开一个新的界面的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addOpenWindows(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击打开丑逼专属界面"));
		list.add(new ElementDropdown(TextFormat.WHITE + "要打开的文件名(当自定义文件名不为空时本项不生效)：", getFileList(), 0));
		list.add(
				new ElementInput(TextFormat.WHITE + "当本项不为空时，上方选项将不启用！", TextFormat.WHITE + "请输入您想要新建或使用的菜单配置文件名", ""));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementInput(TextFormat.WHITE + "点击按钮后要执行的命令", "为空时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.sb/10010.png、石头 或 textures/blocks/sapling_jungle.png", sb));
		player.showFormWindow(new FormWindowCustom("Main-Open", list), MakeID.MainAddOpenWindow.getID());
	}

	/**
	 * 在主页添加一个点击后打开上的商店分页的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addOpenShop(Player player) {
		if (!(mis.ShopListConfig.get("Buttons") instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "您当前还没有任何一个商店！快去添加一个吧！");
			return;
		}
		List<String> strings = new ArrayList<>();
		HashMap<String, String> map = new HashMap<>();
		HashMap<String, Object> FFF_RZ = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		for (String string : FFF_RZ.keySet()) {
			String xxx;
			xxx = TextFormat.WHITE + String.valueOf(((HashMap<String, Object>) FFF_RZ.get(string)).get("Text"))
					+ TextFormat.GOLD + "(" + TextFormat.GREEN + string + TextFormat.GOLD + ")";
			strings.add(xxx);
			map.put(xxx, string);
		}
		if (strings.size() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "您当前还没有任何一个商店！快去添加一个吧！");
			return;
		}
		mis.PlayerAddButtonByOpenShop.put(player.getName(), map);
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击打开丑逼专属商店"));
		list.add(new ElementDropdown(TextFormat.WHITE + "您想打开那一个商店？", strings, Tool.getRand(0, strings.size() - 1)));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementInput(TextFormat.WHITE + "点击按钮后要执行的命令", "为空时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.sb/10010.png、石头 或 textures/blocks/sapling_jungle.png", sb));
		player.showFormWindow(new FormWindowCustom("Main-Open Show", list), MakeID.MainAddOpenShow.getID());
	}

	/**
	 * 点击后在主页添加一个可以传送玩家的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addTransfer(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击传送您到丑逼齐聚的地方"));
		list.add(new ElementInput(TextFormat.WHITE + "目的地X坐标", TextFormat.YELLOW + "例如：" + TextFormat.GREEN + "10086",
				String.valueOf(player.getX())));
		list.add(new ElementInput(TextFormat.WHITE + "目的地Y坐标", TextFormat.YELLOW + "例如：" + TextFormat.GREEN + "10010",
				String.valueOf(player.getY())));
		list.add(new ElementInput(TextFormat.WHITE + "目的地Z坐标", TextFormat.YELLOW + "例如：" + TextFormat.GREEN + "10000",
				String.valueOf(player.getZ())));
		Map<Integer, Level> Fuck_FFF = Server.getInstance().getLevels();
		List<String> FFF_SB = new ArrayList<>();
		int FFF_ZZ = 0;
		int FFF_ZZSB = 0;
		for (int i : Fuck_FFF.keySet()) {
			FFF_SB.add(Fuck_FFF.get(i).getFolderName());
			if (player.getLevel().getFolderName() == Fuck_FFF.get(i).getFolderName())
				FFF_ZZSB = FFF_ZZ;
			FFF_ZZ++;
		}
		list.add(new ElementDropdown(TextFormat.WHITE + "目标世界", FFF_SB, FFF_ZZSB));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementInput(TextFormat.WHITE + "点击按钮后要执行的命令", "为空时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.sb/10010.png 或 石头 或 textures/blocks/sapling_jungle.png", sb));
		player.showFormWindow(new FormWindowCustom("Main-传送", list), MakeID.MainAddTransferForm.getID());
	}

	/**
	 * 添加一个点击后执行命令的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addCommand(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击变丑"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入玩家需要执行的命令", "使用{msg}来增加参数", "me 大家好，我已经成功便成臭逼！请多多关照！"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入参数输入框提示文字", "多个使用;分割您输入的每一个参数日后都会以编辑框的形式展现，请输入其编辑框的提示字符", ""));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.sb/10010.png 或 石头 或 textures/blocks/sapling_jungle.png", sb));
		list.add(new ElementStepSlider(TextFormat.WHITE + "执行这个命令的对象", Arrays.asList(FormStatic.AddCommandPlayer), 0));
		player.showFormWindow(new FormWindowCustom("Main-命令", list, ((sb == null || sb == "") ? (null) : sb)),
				MakeID.MainAddCommadnForm.getID());
	}

	/**
	 * 创建一个提示窗类型的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addTip(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击变丑"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入点击后弹出窗口的内容",
				TextFormat.AQUA + "例如：" + TextFormat.GREEN + "恭喜！您已经成功变成丑逼！"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入弹出窗口内的按钮1文本内容", "默认为：确定", TextFormat.WHITE + "谢谢(*╹▽╹*)"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入弹出窗口内的按钮2文本内容", "默认为：取消", TextFormat.WHITE + "我是丑逼o(╥﹏╥)o"));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementInput(TextFormat.WHITE + "点击按钮后要执行的命令", "为空时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.00/10010.png 或 石头 或 textures/blocks/sapling_jungle.png", sb));
		player.showFormWindow(new FormWindowCustom("Main-Tip", list, ((sb == null || sb == "") ? (null) : sb)),
				MakeID.MainAddTipForm.getID());
	}

	/**
	 * 获取已经存在的菜单配置文件列表
	 * 
	 * @return
	 */
	private List<String> getFileList() {
		List<String> list = new ArrayList<>();
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath);
		for (String string : file.list()) {
			File file2 = new File(file, string);
			if (file2.isFile())
				list.add(string);
		}
		if (list.size() < 1)
			list.add("连个屁都没有");
		return list;
	}
}
