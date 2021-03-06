package cn.xiaokai.mis.form.openbt;

import java.io.File;
import java.util.HashMap;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.custom.open.ILikeLittleSisters;
import cn.xiaokai.mis.form.openbt.overfed.YuanLongPingOfRegret;
import cn.xiaokai.mis.msg.Message;
import cn.xiaokai.mis.shop.ShopMakeForm;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
public class OpenButton {
	private MiniatureS mis;
	private Player player;
	private HashMap<String, Object> Button;
	private Message msg;

	/**
	 * 当玩家点击了按钮之后要干的事情
	 * 
	 * @param player 点击按钮的玩家对象
	 * @param Button 按钮的数据，只是点击的那个按钮的
	 */
	public OpenButton(Player player, HashMap<String, Object> Button) {
		this.mis = MiniatureS.mis;
		this.Button = Button;
		this.player = player;
		this.msg = mis.getMessage();
	}

	/**
	 * 开始判断玩家点击的是什么类型的按钮，到底要干什么
	 */
	public void Switch() {
		switch (String.valueOf(Button.get("Type")).toLowerCase()) {
		case "custom":
		case "cu":
			PlayerClickCustomType();
			break;
		case "command":
		case "cmd":
			PlayerClickCommandType();
			break;
		case "transfer":
			PlayerClickTransferType();
			break;
		case "openshop":
		case "shop":
			PlayerClickOpenShopType();
			break;
		case "openwindows":
		case "open":
			PlayerClickOpenWindowsType();
			break;
		case "make":
		case "tip":
			PlayerClickMakeType();
			break;
		default:
			ServerStupid();
			break;
		}
	}

	/**
	 * 当玩家电机的橡木是自定义表单的按钮时要干的事情
	 */
	private void PlayerClickCustomType() {
		File file = new File(mis.getDataFolder() + MiniatureS.CustomConfigPath, String.valueOf(Button.get("FileName")));
		if (!file.exists()) {
			mis.getLogger().info("§4错误：§6" + file.getName() + "§4文件不存在！请检查您的配置！");
			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotData"));
			return;
		}
		Config config;
		try {
			config = new Config(file, Config.YAML);
		} catch (Exception e) {
			MiniatureS.mis.getLogger().info("\n§e" + MiniatureS.Title + e.getMessage() + MiniatureS.Title
					+ file.getName() + "§4表单文件格式配置错误！请检查");
			return;
		}
		if (!config.getBoolean("Enabled")) {
			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotEnabled"));
			return;
		}
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		(new ILikeLittleSisters(player, file)).startPY();
	}

	/**
	 * 当玩家点击的项目是一个点击后执行命令的按钮时要干的事情
	 */
	private void PlayerClickCommandType() {
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		String Command = Button.get("Command") == null ? null
				: mis.getMessage().getText(String.valueOf(Button.get("Command")));
		if (Command != null && !Command.isEmpty() && Command.contains("{msg}")) {
			HandsomeXiaoKai suaibiXiaoKai = new HandsomeXiaoKai();
			suaibiXiaoKai.player = player;
			suaibiXiaoKai.Button = Button;
			suaibiXiaoKai.Command = Command;
			suaibiXiaoKai.Money = Money;
			suaibiXiaoKai.Text = String.valueOf(Button.get("Text"));
			mis.sb.put(player.getName(), suaibiXiaoKai);
			(new YuanLongPingOfRegret(player)).goCrazy();
			return;
		}
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		if (Command != null && !Command.isEmpty())
			if (String.valueOf(Button.get("Commander")).toLowerCase().equals("console"))
				Server.getInstance().dispatchCommand(new ConsoleCommandSender(), Command);
			else
				Server.getInstance().dispatchCommand(player, Command);
	}

	/**
	 * 当玩家点击的项目是一个点击后传送万家的按钮时要干的事情
	 */
	private void PlayerClickTransferType() {
		String levelName = String.valueOf(Button.get("Level"));
		Level level = Server.getInstance().getLevelByName(levelName);
		if (level == null) {

			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "ErrorPos", new String[] { "{LevelName}" },
					new Object[] { levelName }));
			return;
		}
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		String Command = Button.get("Command") == null ? null : String.valueOf(Button.get("Command"));
		if (Command != null && !Command.isEmpty())
			Server.getInstance().dispatchCommand(player, Command);
		if (player.getLevel().getFolderName() != levelName)
			player.teleport(new Position(Float.valueOf(String.valueOf(Button.get("X"))),
					Float.valueOf(String.valueOf(Button.get("Y"))), Float.valueOf(String.valueOf(Button.get("Z"))),
					level));
		else
			player.teleport(new Vector3(Float.valueOf(String.valueOf(Button.get("X"))),
					Float.valueOf(String.valueOf(Button.get("Y"))), Float.valueOf(String.valueOf(Button.get("Z")))));
	}

	/**
	 * 当玩家点击的项目是一个点击后打开一个商店的按钮时要干的事情
	 */
	private void PlayerClickOpenShopType() {
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		String Command = Button.get("Command") == null ? null : String.valueOf(Button.get("Command"));
		if (Command != null && !Command.isEmpty())
			Server.getInstance().dispatchCommand(player, Command);
		String ShopKey = String.valueOf(Button.get("Shop"));
		(new ShopMakeForm(mis)).MakeShowShopShow(player, ShopKey);
	}

	/**
	 * 当玩家点击的按钮类型为点击后打开一个新的界面的时候要干的事情
	 */
	private void PlayerClickOpenWindowsType() {
		if (Button.get("FileName") == null) {
			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotFileError") + "(1)");
			return;
		}
		String fileName = (String) Button.get("FileName");
		if (fileName.isEmpty()) {
			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotFileError") + "(2)");
			return;
		}
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, fileName);
		if (!file.exists()) {
			MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotData"));
			mis.getLogger().info("§4错误：§6" + file.getName() + "§4文件不存在！请检查您的配置！");
			return;
		}
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		String Command = Button.get("Command") == null ? null : String.valueOf(Button.get("Command"));
		if (Command != null && !Command.isEmpty())
			Server.getInstance().dispatchCommand(player, Command);
		(new MakeWindow(player, file)).MakeForm();
	}

	/**
	 * 但玩家点击的按钮为点击后打开提速窗口类型的按钮的时候会干的事情
	 */
	private void PlayerClickMakeType() {
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player, msg.getSurname("UI", "Click", "NotMoney", new String[] { "{Money}" },
						new Object[] { (EconomyAPI.getInstance().myMoney(player) - Money) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(msg.getSurname("UI", "Click", "reClickButtonMoney", new String[] { "{Money}" },
					new Object[] { Money }));
		} else if (ePlugin == null || !ePlugin.isEnabled())
			mis.getLogger().info("§4警告：§6EconomyAPI§4未安装或未启用！本次忽略§9" + player.getName() + "§4的扣费！");
		String Command = Button.get("Command") == null ? null : String.valueOf(Button.get("Command"));
		if (Command != null && !Command.isEmpty())
			Server.getInstance().dispatchCommand(player, Command);
		player.showFormWindow(new FormWindowModal(msg.getText(String.valueOf(Button.get("Text"))),
				msg.getText(String.valueOf(Button.get("Content"))), msg.getText(String.valueOf(Button.get("bt1"))),
				msg.getText(String.valueOf(Button.get("bt2")))));
	}

	/**
	 * 当玩家点击了一个不知道是什么类型的按钮的时候 ,会提示的东西
	 */
	private void ServerStupid() {
		String zz_clicl_don_know_what_tip_String = msg.getSurname("UI", "Click", "DataSB");
		for (String zz_click_map_key : Button.keySet())
			zz_clicl_don_know_what_tip_String += Tool.getRandColor() + zz_click_map_key + Tool.getRandColor() + " : "
					+ Tool.getRandColor() + String.valueOf(Button.get(zz_click_map_key)) + "\n";
		MakeForm.makeTip(player, zz_clicl_don_know_what_tip_String);
	}
}
