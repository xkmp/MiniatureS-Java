package cn.xiaokai.mis.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Tool {
	private static String colorKeyString = "123456789abcdef";
	private static String randString = "0123456789-+abcdefghijklmnopqrstuvwxyz_=";

	/**
	 * 判断两个ID是否匹配，x忽略匹配
	 * 
	 * @param ID1 第一个ID
	 * @param ID2 第二个ID
	 * @return
	 */
	public static boolean isMateID(String ID1, String ID2) {
		if (!ID1.contains(":"))
			ID1 += ":0";
		if (!ID2.contains(":"))
			ID2 += ":0";
		String[] ID1s = ID1.split(":"), ID2s = ID2.split(":");
		if (ID1s[0].equals("x") || ID2s[0].equals("x") || ID1s[0].equals(ID2s[0]))
			if (ID1s[1].equals("x") || ID2s[1].equals("x") || ID2s[1].equals(ID1s[1]))
				return true;
			else
				return false;
		else
			return false;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getTime() {
		SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
		return time.format(new Date());
	}

	/**
	 * 返回当前时间 <年-月-日>
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		return data.format(new Date());
	}

	/**
	 * 自动检查ID是否包含特殊值，若不包含则默认特殊值为0后返回数组
	 * 
	 * @param ID 要检查分解的ID
	 * @return int[]{ID, Damage}
	 */
	public static int[] IDtoFullID(String ID) {
		return IDtoFullID(ID, 0);
	}

	/**
	 * 自动检查ID是否包含特殊值，若不包含则设置特殊值为用户定义值后返回数组
	 * 
	 * @param ID     要检查的ID
	 * @param Damage 要默认设置的特殊值
	 * @return int[]{ID, Damage}
	 */
	public static int[] IDtoFullID(String ID, int Damage) {
		if (ID.contains(":"))
			ID += ":" + Damage;
		String[] strings = ID.split(":");
		return new int[] { Integer.valueOf(strings[0]), Integer.valueOf(strings[1]) };
	}

	/**
	 * 获取随机数
	 * 
	 * @param min 随机数的最小值
	 * @param max 随机数的最大值
	 * @return
	 */
	public static int getRand(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	/**
	 * 返回一个随机颜色代码
	 * 
	 * @return
	 */
	public static String getRandColor() {
		return getRandColor(colorKeyString);
	}

	/**
	 * 返回一个随机颜色代码
	 * 
	 * @param ColorFont 可以随机到的颜色代码
	 * @return
	 */
	public static String getRandColor(String ColorFont) {
		int rand = Tool.getRand(0, ColorFont.length() - 1);
		return "§" + ColorFont.substring(rand, rand + 1);
	}

	/**
	 * 将字符串染上随机颜色
	 * 
	 * @param Font 要染色的字符串
	 * @return
	 */
	public static String getColorFont(String Font) {
		return getColorFont(Font, colorKeyString);
	}

	/**
	 * 返回一个随机字符
	 * 
	 * @return 随机字符
	 */
	public static String getRandString() {
		return getRandString(randString);
	}

	/**
	 * 返回一个随机字符
	 * 
	 * @param string 要随机字符的范围
	 * @return 随机字符
	 */
	public static String getRandString(String string) {
		int r1 = getRand(0, string.length() - 1);
		return string.substring(r1, r1 + 1);
	}

	/**
	 * 将字符串染上随机颜色
	 * 
	 * @param Font      要染色的字符串
	 * @param ColorFont 随机染色的颜色代码
	 * @return
	 */
	public static String getColorFont(String Font, String ColorFont) {
		String text = "";
		for (int i = 0; i < Font.length(); i++) {
			int rand = Tool.getRand(0, ColorFont.length() - 1);
			text += "§" + ColorFont.substring(rand, rand + 1) + Font.substring(i, i + 1);
		}
		return text;
	}

	/**
	 * 判断字符串是否是整数型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		try {
			Float.valueOf(str).intValue();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断一段字符串中是否只为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9[.]]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 字符串转换Unicode
	 * 
	 * @param string 要转换的字符串
	 * @return
	 */
	public static String StringToUnicode(String string) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++)
			unicode.append("\\u" + Integer.toHexString(string.charAt(i)));
		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 * 
	 * @param unicode 全为 Unicode 的字符串
	 * @return
	 */
	public static String UnicodeToString(String unicode) {
		StringBuffer string = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++)
			string.append((char) Integer.parseInt(hex[i], 16));
		return string.toString();
	}
}
