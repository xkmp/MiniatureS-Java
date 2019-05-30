package cn.xiaokai.mis.form.openbt.overfed;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.openbt.HandsomeXiaoKai;
import cn.xiaokai.mis.tool.Tool;

public class YuanLongPingOfRegret {
	private Player player;
	private MiniatureS mis;
	private HandsomeXiaoKai sb10086;

	/**
	 * <b> 吾乃吾辈之帅逼也</br>
	 * 勿崇我， 吾观少年汝吃多矣，</br>
	 * 食为贵之， 当惜， 不费，</br>
	 * 袁隆平皆悔令汝饱，</br>
	 * 勉行善积德，</br>
	 * 勿复瞎搞矣，</br>
	 * 此乃吾等之模楷也，</br>
	 * 袁隆平可喜也，</br>
	 * 如不听劝，</br>
	 * 即乱屌打死亦不可超生也</br>
	 * 如少年心有不服</br>
	 * 那就滚你妹的 </b>
	 * 
	 * @param player 汝之对象也
	 */
	public YuanLongPingOfRegret(Player player) {
		mis = MiniatureS.mis;
		this.player = player;
		sb10086 = mis.sb.get(player.getName());
	}

	/**
	 * 少年吃多矣 且看吾将少年变傻逼也
	 */
	@SuppressWarnings("unchecked")
	public void goCrazy() {
		String YouSB = sb10086.Command;
		List<Element> FuckYou = new ArrayList<Element>();
		String youAreSB = Tool.getRandColor();
		FuckYou.add(new ElementLabel(Tool.getColorFont("Command: ") + youAreSB
				+ YouSB.replace("{msg}", Tool.getRandColor() + "{用户参数}" + youAreSB)));
		int SBCount = (YouSB.split("\\{msg\\}")).length - 1;
		sb10086.SBCount = SBCount;
		List<String> ZZ = new ArrayList<>();
		ZZ.add("不知道是啥东西");
		if (sb10086.Button.get("Msg") instanceof List)
			ZZ = (List<String>) sb10086.Button.get("Msg");
		String LaoCan = "不知道是啥东西";
		for (int i = 0; i < SBCount; i++) {
			if ((ZZ.size() - 1) >= i)
				LaoCan = ZZ.get(i);
			FuckYou.add(new ElementInput(LaoCan, Tool.getColorFont("请输入参数" + (i + 1))));
		}
		mis.sb.put(player.getName(), sb10086);
		player.showFormWindow(new FormWindowCustom(sb10086.Text, FuckYou), MakeID.HeadIntoTheWater.getID());
	}
}
