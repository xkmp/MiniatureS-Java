package cn.xiaokai.mis.form.openbt;

import java.io.File;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.management.WhatThing;
import cn.xiaokai.mis.tool.Tool;

@SuppressWarnings("unchecked")
public class DealWith {
	private Player player;
	private FormResponseSimple data;
	private MiniatureS mis;

	/**
	 * 处理玩家点击菜单UI项目的事件
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   表单发回的数据
	 */
	public DealWith(Player player, FormResponseSimple data) {
		this.player = player;
		this.mis = MiniatureS.mis;
		this.data = data;
	}

	/**
	 * 开始处♂理
	 */
	public void startPy() {
		int JJLength = data.getClickedButtonId();
		if (JJLength >= mis.PlayerMenu.get(player.getName()).size()) {
			if (player.isOp()) {
				(new WhatThing(player, data, mis.PlayerMenu.get(player.getName()).size(), new File(
						mis.getDataFolder() + MiniatureS.MenuConfigPath, mis.PlayerMenuBack.get(player.getName()))))
								.Switch();
				return;
			}
			MakeForm.makeTip(player, TextFormat.RED + "错误！\n" + Tool.getColorFont("检测到您正在修改本业！当您的权限不足！"));
			return;
		}
		String PyName = mis.PlayerMenu.get(player.getName()).get(JJLength);
		HashMap<String, Object> Py = (HashMap<String, Object>) mis.PlayerMenuData.get(player.getName()).get(PyName);
		KickDownTheLadder();
		(new OpenButton(player, Py)).Switch();
	}

	/**
	 * 既然P♂Y完了，该处理后事了！免得怀孕就不好了！咳咳咳而且不收拾残局被警察看到怎么办...
	 */
	protected void KickDownTheLadder() {
		mis.PlayerMenuData.remove(player.getName());
		mis.PlayerMenuBack.remove(player.getName());
		mis.PlayerMenu.remove(player.getName());
	}
}
