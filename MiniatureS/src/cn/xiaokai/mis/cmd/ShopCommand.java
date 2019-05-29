package cn.xiaokai.mis.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.window.FormWindowSimple;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.cmd.scmd.SCmd;
import cn.xiaokai.mis.tool.Tool;

public class ShopCommand {
	private MiniatureS mis;

	/**
	 * 处理“shop”命令
	 * 
	 * @param mis 插件主类对象
	 */
	public ShopCommand(MiniatureS mis) {
		this.mis = mis;
	}

	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1) {
			if (player.isPlayer()) {
				mis.shopMakeForm.MakeMain((Player) player);
				return true;
			} else {
				onCommand(player, label, new String[] { "help" });
				return true;
			}
		}
		switch (a[0].toLowerCase()) {
		case "show":
		case "ui":
		case "gui":
			if (player.isPlayer()) {
				mis.shopMakeForm.MakeMain((Player) player);
				return true;
			}
			player.sendMessage(SCmd.getShopShowList());
			return true;
		case "help":
		default:
			if (a.length >= 2 && player.isPlayer()) {
				((Player) player).showFormWindow(new FormWindowSimple(
						Tool.getColorFont(mis.getName() + "-" + player.getName() + "-Help"), SCmd.getHelpString()));
				return true;
			}
			player.sendMessage(SCmd.getHelpString());
			return true;
		}
	}
}
