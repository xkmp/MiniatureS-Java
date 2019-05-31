package cn.xiaokai.mis.form.openbt.overfed;

import java.util.ArrayList;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.openbt.HandsomeXiaoKai;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;
/**
 * @author Winfxk
 */
public class DonFiddle {
	private MiniatureS mis;
	private Player player;
	private FormResponseCustom data;

	/**
	 * 处理执行命令UI传回的参数数据
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   传回的数据
	 */
	public DonFiddle(Player player, FormResponseCustom data) {
		mis = MiniatureS.mis;
		this.player = player;
		this.data = data;
	}

	/**
	 * 开始搞基
	 */
	public void Rape() {
		HandsomeXiaoKai kai = mis.sb.get(player.getName());
		ArrayList<String> FuckMR = new ArrayList<>();
		for (int i = 1; i <= kai.SBCount; i++)
			FuckMR.add(String.valueOf(data.getResponse(i)));
		String Sb = kai.Command;
		String[] YouSB = Sb.split("\\{msg\\}");
		String SX = YouSB[0];
		for (int JJLength = 0; (JJLength < YouSB.length && YouSB.length > 1); JJLength++) {
			String BB = "";
			if (FuckMR.size() > JJLength)
				BB = FuckMR.get(JJLength);
			SX = SX + BB + (YouSB.length <= (JJLength + 1) ? "" : YouSB[JJLength + 1]);
		}
		int Money = kai.Money;
		Plugin ePlugin = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Money > 0 && ePlugin != null && ePlugin.isEnabled()) {
			if (EconomyAPI.getInstance().myMoney(player) < Money) {
				MakeForm.makeTip(player,
						TextFormat.RED + "噢！\n\n" + Tool.getColorFont("很抱歉！该按钮禁止穷逼使用！") + "\n\n" + TextFormat.YELLOW
								+ "若想使用，请在准备" + TextFormat.GOLD + (Money - EconomyAPI.getInstance().myMoney(player))
								+ TextFormat.YELLOW + mis.getMoneyName());
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, Money);
			player.sendMessage(Tool.getColorFont("已扣除使用该按钮的费用：" + Money));
		}
		if (SX != null && !SX.isEmpty())
			if (String.valueOf(kai.Button.get("Commander")).toLowerCase() == "player")
				Server.getInstance().dispatchCommand(player, SX);
			else
				Server.getInstance().dispatchCommand(new ConsoleCommandSender(), SX);
	}
}
