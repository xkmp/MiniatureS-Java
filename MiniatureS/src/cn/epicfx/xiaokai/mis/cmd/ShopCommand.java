package cn.epicfx.xiaokai.mis.cmd;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;

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
		return false;
	}
}
