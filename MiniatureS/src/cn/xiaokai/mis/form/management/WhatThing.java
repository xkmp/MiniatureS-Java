package cn.xiaokai.mis.form.management;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.management.son.NewButton;

/**
 * @author Winfxk
 */
public class WhatThing {
	private MiniatureS mis;
	private Player player;
	private FormResponseSimple data;
	private int Key;
	private File file;

	/**
	 * 配置界面
	 * 
	 * @param player 要配置界面的玩家对象
	 * @param data   配置过程中发回的数据
	 * @param Key    要配置的键值
	 * @param file   要配置的文件对象
	 */
	public WhatThing(Player player, FormResponseSimple data, int Key, File file) {
		this.player = player;
		this.mis = MiniatureS.mis;
		this.Key = Key;
		this.data = data;
		this.file = file;
	}

	/**
	 * 开始配置
	 */
	public void Switch() {
		switch (data.getClickedButtonId() - Key) {
		case 1:
			(new MakeManagFrom(mis)).MakeRemoveButton(player, file);
			break;
		case 0:
		default:
			(new NewButton(player, file)).Make();
			break;
		}
	}
}
