package com.dalianbobo.lepaandroid.uitls;


import android.os.Build;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 支付宝支付签名工具类
 */
public class SignUtils {

	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 对内容进行RSA加密
	 * @param content 内容
	 * @param privateKey 私钥
	 * @return
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf =null;
			if(Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){//安卓4.1以下
				keyf = KeyFactory.getInstance(ALGORITHM);
			}else{//安卓4.1以上
				keyf = KeyFactory.getInstance(ALGORITHM,"BC");
			}
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
