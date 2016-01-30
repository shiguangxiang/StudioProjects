package com.dalianbobo.lepaandroid.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 字符串判断，主要是判断是否为空
 * 
 * @author fish
 * 
 */
public class StringUtil {

	private static StringUtil instance;

	public static StringUtil getInstance() {
		if (null == instance)
			instance = new StringUtil();
		return instance;
	}

	public boolean IsEmpty(String str) {
		return null == str || "".equalsIgnoreCase(str) ;
	}
	/**
	 * 判断电话号码是否有效
	 * 移动：134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188
	 * 联通：130、131、132、145、155、156、185、186 电信：133、153、180、181、189 虚拟运营商：17x
	 */
	public static boolean isMobileNO(String number) {
		if (number.startsWith("+86")) {
			number = number.substring(3);
		}

		if (number.startsWith("+") || number.startsWith("0")) {
			number = number.substring(1);
		}

		number = number.replace(" ", "").replace("-", "");
		System.out.print("号码：" + number);
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

}
