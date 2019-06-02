package cn.xiaokai.mis.form.custom;

import java.io.File;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.management.Smil;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
public class TheForce {
	private MiniatureS mis;

	private Player player;

	/**
	 * 处理自定义界面传来的数据
	 */
	public TheForce(Player player) {
		this.player = player;
		mis = MiniatureS.mis;
	}

	/**
	 * 处理数据
	 * 
	 * @param data 传递的数据
	 */
	public void startPY(FormResponseCustom data) {
		if (data.getResponse(0) == null || String.valueOf(data.getResponse(0)).isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String Button = String.valueOf(data.getResponse(0));
		String FileName = (data.getResponse(1) == null || String.valueOf(data.getResponse(1)).isEmpty())
				? MakeCustom.getConfigName(1)
				: String.valueOf(data.getResponse(1));
		if (data.getResponse(2) == null || String.valueOf(data.getResponse(2)).isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮费用！");
			return;
		}
		String moneyString = String.valueOf(data.getResponse(2));
		if (!Tool.isInteger(moneyString) || Float.valueOf(moneyString).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮费用(大于零的纯整数)！");
			return;
		}
		int Money = Float.valueOf(moneyString).intValue();
		Smil smil = new Smil(data, 3, 3);
		boolean ImageType = smil.getImageType();
		String ImagePath = smil.getPath();
		File file = mis.Custom.get(player.getName()).file;
		(new RollYourSister(player, file)).startPY(Button, FileName, Money, ImagePath, ImageType);
	}
}
