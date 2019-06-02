package cn.xiaokai.mis.form.custom.form;

import java.io.File;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;

/**
 * @author Winfxk
 */
public class ModalType extends SB10000 {
	/**
	 * 当玩家点击的按钮指向的表单为选择型表单
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param file   表单文件得文件对象
	 * @param config 表单配置文件对西安
	 */
	public ModalType(Player player, File file, Config config) {
		super(player, file, config);
	}

	/**
	 * 开始PY
	 */
	public void startPY() {
	}
}
