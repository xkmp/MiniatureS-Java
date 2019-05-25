package cn.epicfx.xiaokai.mis.form.management.main;

import java.util.ArrayList;
import java.util.List;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.TextFormat;

public class MainMake {
	private MiniatureS mis;

	/**
	 * 当主页要添加按钮时，选择好了按钮类型后创建对应的界面
	 * 
	 * @param mis 插件主类对象
	 */
	public MainMake(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 添加一个点击后执行命令的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addCommand(Player player) {
		List<Element> list = new ArrayList<>();

	}

	/**
	 * 创建一个提示窗类型的按钮
	 * 
	 * @param player 触发这个事件的玩家对象
	 */
	public void addTip(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入按钮显示的内容",
				TextFormat.AQUA + "例如: " + TextFormat.GREEN + "点击变丑"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入点击后弹出窗口的内容",
				TextFormat.AQUA + "例如：" + TextFormat.GREEN + "恭喜！您已经成功变成丑逼！"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入弹出窗口内的按钮1文本内容", "默认为：确定", TextFormat.WHITE + "谢谢(*╹▽╹*)"));
		list.add(new ElementInput(TextFormat.WHITE + "请输入弹出窗口内的按钮2文本内容", "默认为：取消", TextFormat.WHITE + "我是丑逼o(╥﹏╥)o"));
		list.add(new ElementInput(TextFormat.WHITE + "点击该按钮需要花费的" + mis.getMoneyName(), "为空或小于等于零时不启用该功能"));
		list.add(new ElementInput(TextFormat.WHITE + "点击按钮后要执行的命令", "为空时不启用该功能"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "贴图类型: " + TextFormat.AQUA, FormStatic.getButtonImageType(),
				0));
		String sb = ItemIDSunName.getIDByPath(
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: "");
		list.add(new ElementInput(TextFormat.WHITE + "请输入贴图路径",
				"例如：http://10086.00/10010.png 或 石头 或 textures/blocks/sapling_jungle.png", sb));
		player.showFormWindow(new FormWindowCustom("Main", list, ((sb == null || sb == "") ? (null) : sb)),
				MakeID.MainAddTipForm.getID());
	}
}
