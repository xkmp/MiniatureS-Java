package cn.epicfx.xiaokai.mis.cmd;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.nukkit.command.CommandSender;

public class MainCommand {
	private MiniatureS mis;

	/**
	 * 处理“mis”命令
	 * 
	 * @param mis 插件主类对象
	 */
	public MainCommand(MiniatureS mis) {
		this.mis = mis;
	}

	public boolean onCommand(CommandSender player, String label, String[] a) {
		return false;
	}
}
