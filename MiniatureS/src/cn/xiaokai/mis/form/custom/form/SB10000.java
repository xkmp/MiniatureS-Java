package cn.xiaokai.mis.form.custom.form;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.msg.Message;

/**
 * @author Winfxk
 */
public abstract class SB10000 {
	public Player player;
	public File file;
	public Config config;
	public MiniatureS mis;

	public SB10000(Player player, File file, Config config) {
		this.file = file;
		this.config = config;
		this.player = player;
		mis = MiniatureS.mis;
	}

	public Message getMessage() {
		return mis.getMessage();
	}
}
