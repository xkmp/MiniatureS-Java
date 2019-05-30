package cn.xiaokai.mis.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.myshop.MakeMyShopForm;

public class MyShopCommand {
	private MiniatureS mis;

	/**
	 * 处理“myshop”命令
	 * 
	 * @param mis 插件主累对象
	 */
	public MyShopCommand(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 开始处理对象
	 * 
	 * @param player 执行命令的玩家对象
	 * @param label
	 * @param a      命令参数
	 * @return 命令执行结果
	 */
	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1) {
			if (!player.isPlayer())
				return onCommand(player, label, new String[] { "help" });
			(new MakeMyShopForm((Player) player)).MakeMain();
			return true;
		}
		switch (a[0]) {
		case "make":
		case "ui":
		case "show":
		case "list":
		default:
			if (!player.isPlayer())
				return onCommand(player, label, new String[] { "help" });
			(new MakeMyShopForm((Player) player)).MakeMain();
			return true;
		}
	}
}
