package cn.epicfx.xiaokai.mis.msg;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.Tool;

public class Message {
	private MiniatureS mis;
	private String[] Global_Key;
	private String[] Global_Data;

	public Message(MiniatureS mis) {
		this.mis = mis;
		update();
	}

	/**
	 * 获取语言消息
	 * 
	 * @param Key 语言消息存储在配置文件中的索引键
	 * @return 语言消息
	 */
	public String getMessage(String Key) {
		return this.getMessage(Key, new String[] {}, new String[] {});
	}

	public void update() {
		Global_Key = new String[] { "{n}", "{Plugin_Name}", "{Server_Name}", "{Time}", "{Data}", "{Rand_Color}" };
		Global_Data = new String[] { "\n", mis.getName(), mis.getServer().getMotd(), Tool.getTime(), Tool.getDate(),
				Tool.getRandColor() };
	}

	/**
	 * 读取语言文件中的特定内容并将其替换为所需要替换的内容后返回
	 * 
	 * @param Key     配置文件中村组文本数据的键
	 * @param MsgKey  所需要替换的变量
	 * @param MsgData 要替换成的内容
	 * @return 替换完毕的内容
	 */
	public String getMessage(String Key, String[] MsgKey, String[] MsgData) {
		String msg = mis.MsgConfig.getString(Key, null);
		if (msg == null)
			return null;
		return getText(msg, MsgKey, MsgData);
	}

	/**
	 * 将指定内容替换变量
	 * 
	 * @param msg 要替换的内容
	 * @return
	 */
	public String getText(String msg) {
		return getText(msg, new String[] {}, new String[] {});
	}

	/**
	 * 将指定内容替换变量
	 * 
	 * @param msg     字符内容
	 * @param MsgKey  要替换的变量名
	 * @param MsgData 替换后的内容
	 * @return
	 */
	public String getText(String msg, String[] MsgKey, String[] MsgData) {
		update();
		for (int j1 = 0; j1 < Global_Key.length && j1 < Global_Data.length; j1++)
			if (msg.contains(Global_Key[j1]))
				msg = msg.replace(Global_Key[j1], Global_Data[j1]);
		for (int i = 0; i < MsgKey.length && i < MsgData.length; i++)
			if (msg.contains(MsgKey[i]))
				msg = msg.replace(MsgKey[i], MsgData[i]);
		return msg;
	}
}
