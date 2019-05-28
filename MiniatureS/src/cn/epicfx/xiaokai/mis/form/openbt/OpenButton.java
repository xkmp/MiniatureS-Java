package cn.epicfx.xiaokai.mis.form.openbt;

import java.io.File;
import java.util.HashMap;
import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.msg.Message;
import cn.epicfx.xiaokai.mis.shop.ShopMakeForm;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;

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
	 * 当玩家点击的项目是一个点击后执行命令的按钮时要干的事情
	 */
	private void PlayerClickCommandType() {
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player,
						TextFormat.RED + "噢！\n\n" + Tool.getColorFont("很抱歉！该按钮禁止穷逼使用！") + "\n\n" + TextFormat.YELLOW
								+ "若想使用，请在准备" + TextFormat.GOLD + (Money - EconomyAPI.getInstance().myMoney(player))
								+ TextFormat.YELLOW + mis.getMoneyName()

				);
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(Tool.getColorFont("已扣除使用该按钮的费用：" + Money));
		}
		String Command = Button.get("Command") == null ? null : String.valueOf(Button.get("Command"));
		if (Command != null && !Command.isEmpty())
			Server.getInstance().dispatchCommand(player, Command);
	}

	/**
	 * 当玩家点击的项目是一个点击后传送万家的按钮时要干的事情
	 */
	private void PlayerClickTransferType() {
		String levelName = String.valueOf(Button.get("Level"));
		Level level = Server.getInstance().getLevelByName(levelName);
		if (level == null) {
			MakeForm.makeTip(player, TextFormat.RED + "无法传送您到目标点！\n\n" + TextFormat.YELLOW + "地图" + TextFormat.AQUA
					+ levelName + TextFormat.AQUA + "不存在或未加载！");
			return;
		}
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player,
						TextFormat.RED + "噢！\n\n" + Tool.getColorFont("很抱歉！该按钮禁止穷逼使用！") + "\n\n" + TextFormat.YELLOW
								+ "若想使用，请在准备" + TextFormat.GOLD + (Money - EconomyAPI.getInstance().myMoney(player))
								+ TextFormat.YELLOW + mis.getMoneyName()

				);
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(Tool.getColorFont("已扣除使用该按钮的费用：" + Money));
		}
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
		String ShopKey = String.valueOf(Button.get("Shop"));
		(new ShopMakeForm(mis)).MakeShowShopShow(player, ShopKey);
	}

	/**
	 * 当玩家点击的按钮类型为点击后打开一个新的界面的时候要干的事情
	 */
	private void PlayerClickOpenWindowsType() {
		if (Button.get("FileName") == null) {
			MakeForm.makeTip(player, TextFormat.RED + "打开失败(1)！无法获取对应的菜单文件！请联系管理员！");
			return;
		}
		String fileName = (String) Button.get("FileName");
		if (fileName.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "打开失败(2)！无法获取对应的菜单文件！请联系管理员！");
			return;
		}
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, fileName);
		if (!file.exists()) {
			MakeForm.makeTip(player, TextFormat.RED + "打开失败！不存在的数据！");
			return;
		}
		int Money = Button.get("Money") == null ? 0
				: Float.valueOf(
						String.valueOf(Button.get("Money")).isEmpty() ? "0" : String.valueOf(Button.get("Money")))
						.intValue();
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player,
						TextFormat.RED + "噢！\n\n" + Tool.getColorFont("很抱歉！该按钮禁止穷逼使用！") + "\n\n" + TextFormat.YELLOW
								+ "若想使用，请在准备" + TextFormat.GOLD + (Money - EconomyAPI.getInstance().myMoney(player))
								+ TextFormat.YELLOW + mis.getMoneyName()

				);
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(Tool.getColorFont("已扣除使用该按钮的费用：" + Money));
		}
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
				MakeForm.makeTip(player,
						TextFormat.RED + "噢！\n\n" + Tool.getColorFont("很抱歉！该按钮禁止穷逼使用！") + "\n\n" + TextFormat.YELLOW
								+ "若想使用，请在准备" + TextFormat.GOLD + (Money - EconomyAPI.getInstance().myMoney(player))
								+ TextFormat.YELLOW + mis.getMoneyName()

				);
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(Tool.getColorFont("已扣除使用该按钮的费用：" + Money));
		}
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
		String zz_clicl_don_know_what_tip_String = Tool
				.getColorFont("亲！\n您可能点击了一个数据已经损坏了的按钮！\n\n我们无法判断这个按钮的类型！请联系管理员处理！\n\n");
		for (String zz_click_map_key : Button.keySet())
			zz_clicl_don_know_what_tip_String += Tool.getRandColor() + zz_click_map_key + Tool.getRandColor() + " : "
					+ Tool.getRandColor() + String.valueOf(Button.get(zz_click_map_key)) + "\n";
		MakeForm.makeTip(player, zz_clicl_don_know_what_tip_String);
	}
}
