package cn.xiaokai.mis.form.management;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.management.son.NewButton;

public class WhatThing {
	private MiniatureS mis;
	private Player player;
	private FormResponseSimple data;
	private int Key;
	private File file;

	public WhatThing(Player player, FormResponseSimple data, int Key, File file) {
		this.player = player;
		this.mis = MiniatureS.mis;
		this.Key = Key;
		this.data = data;
		this.file = file;
	}

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
