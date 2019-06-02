package cn.xiaokai.mis.form.custom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;

/**
 * <b>PS：</b>万恶的FFF又出馊主意
 * 
 * @author Winfxk</br>
 */
public class MakeCustom {
	/**
	 * 创建自定义按钮的UI
	 * 
	 * @param player 创建按钮的玩家对象
	 * @param file   要添加按钮的配置文件的文件对象
	 */
	public void startPY(Player player, File file) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, MiniatureS.mis.getMessage().getSon("Custom", "SBPlayerNotOP"));
			return;
		}
		List<Element> list = new ArrayList<Element>();
		list.add(new ElementInput(TextFormat.YELLOW + "请输入按钮文本内容", TextFormat.GREEN + "例如：" + TextFormat.RED + "FFF SB",
				""));
		list.add(new ElementInput(TextFormat.WHITE + "请输入自定义按钮的配置文件名",
				TextFormat.GREEN + "例如：" + TextFormat.RED + "10086sb.yaml", getConfigName(1)));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + MiniatureS.mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.00/10010.png 或 石头 或 textures/blocks/sapling_jungle.png", sb));
		CustomData data = MiniatureS.mis.Custom.get(player.getName()) == null ? new CustomData()
				: MiniatureS.mis.Custom.get(player.getName());
		data.file = file;
		MiniatureS.mis.Custom.put(player.getName(), data);
		player.showFormWindow(
				new FormWindowCustom(Tool.getColorFont(MiniatureS.mis.getName()), list, new ElementButtonImageData(
						ElementButtonImageData.IMAGE_DATA_TYPE_PATH, ItemIDSunName.getRandPath())),
				MakeID.MakeCustom.getID());
	}

	/**
	 * 获取一个不重复的自定义按钮配置文件名
	 * 
	 * @return
	 */
	public static String getConfigName(int JJLength) {
		String name = "";
		JJLength = JJLength < 1 ? 1 : JJLength;
		for (int i = 0; i < JJLength; i++)
			name += Tool.getRandString();
		if ((new File(MiniatureS.mis.getDataFolder() + MiniatureS.CustomConfigPath, name + ".yml")).exists())
			return getConfigName(JJLength++);
		return name;
	}
}
