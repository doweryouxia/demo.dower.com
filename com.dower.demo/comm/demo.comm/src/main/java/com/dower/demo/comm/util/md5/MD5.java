package com.dower.demo.comm.util.md5;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密
 * @author NiuXueJun
 * 2015-7-31 下午3:24:30
 */
public class MD5  {

	private static char[]	hex	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * MD5加密
	 * @param arg0 需加密的字符串
	 * @return String 加密后的字符串大写
	 */
	public static String encode(String arg0) {
		MessageDigest digest = null;
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(arg0.getBytes());
		byte[] bytes = digest.digest();
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	
	public final static String MD5Encoder(String s, String charset) {
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

	
	/**
	 * 获取字符串的MD5值（16进制）
	 * 
	 * @param s 字符串
	 * @return 字符串的MD5值
	 */
	public final static String compile ( String s )
	{
		
		byte[] tmp = s.getBytes ( );
		try
		{
			MessageDigest dig = MessageDigest.getInstance ( "MD5" );
			dig.update ( tmp );
			byte[] md = dig.digest ( );
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for ( int i = 0 ; i < j ; i++ )
			{
				byte byte0 = md[i];
				str[k++ ] = hex[byte0 >>> 4 & 0xf];
				str[k++ ] = hex[byte0 & 0xf];
			}
			return new String ( str );
		} catch ( NoSuchAlgorithmException ex )
		{
			ex.printStackTrace ( );
			return null;
		}
	}
	
	/**
	 * MD5（16进制）加密字节数组返回字符串
	 * @param toencode byte[] 字节数组
	 * @return String 
	 */
	public static String MD5Encode ( byte[] toencode )
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance ( "MD5" );
			md5.reset();
			md5.update(toencode);
			return HexEncode ( md5.digest ( ) );
		} catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace ( );
		}
		return "";
	}
	/**
	 * MD5加密字节数组
	 * @param toencode byte[] 字节数组
	 * @return byte[]  
	 */
	public static byte[] MD5EncodeByte ( byte[] toencode )
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance ( "MD5" );
			md5.reset ( );
			md5.update ( toencode );
			return md5.digest ( );
		} catch ( NoSuchAlgorithmException e )
		{
			e.printStackTrace ( );
		}
		return null;
	}
	
	private static String HexEncode ( byte[] toencode )
	{
		StringBuilder sb = new StringBuilder ( toencode.length * 2 );
		for ( byte b : toencode )
		{
			sb.append ( Integer.toHexString ( ( b & 0xf0 ) >>> 4 ) );
			sb.append ( Integer.toHexString ( b & 0x0f ) );
		}
		return sb.toString ( ).toUpperCase ( );
	}
	
	/**
	 * 获取文件内容的MD5值
	 * 
	 * @param file   文件
	 * @return 文件的MD5值
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String compile (File file) throws NoSuchAlgorithmException , IOException
	{
		if ( !file.exists() )
		{
			throw new FileNotFoundException ( );
		}
		
		InputStream fis = new FileInputStream ( file );
		byte[] buffer = new byte[1024];
		MessageDigest md5 = MessageDigest.getInstance ( "MD5" );
		int numRead = 0;
		while ( ( numRead = fis.read ( buffer ) ) > 0 )
		{
			md5.update ( buffer , 0 , numRead );
		}
		fis.close ( );
		byte[] md = md5.digest ( );
		int j = md.length;
		char[] str = new char[j * 2];
		int k = 0;
		for ( int i = 0 ; i < j ; i++ )
		{
			byte byte0 = md[i];
			str[k++ ] = hex[byte0 >>> 4 & 0xf];
			str[k++ ] = hex[byte0 & 0xf];
		}
		return new String ( str );
	}
	
	public static void main(String[] args) {
		String b = "IsSingleSubmit=1&IntentionCompany=2&LicenseNo=京P55M11&EngineNo=4LA4D8297&CarVin=LGXC16DF4A0169664&RegisterDate=2010-06-21&MoldName=比亚迪&CarType=0&IsNewCar=0&CarUsedType=0&Citycode=1&SanZhe=300000&BuJiMianSanZhe=1&SiJi=50000&ChengKe=50000&BuJiMianRenYuan=1&CheSun=1&BuJiMianCheSun=1&BoLi=1&DaoQiang=1&BuJiMianDaoQiang=1&ZiRan=1&SheShui=1&HuaHen=5000&BuJiMianFuJia=1&ForceTax=0&CheDeng=0&CustKey=492F4A6C11781385E9CF08651E77E148&Agent=3820";
		String sec = "993d5cbe34fe61033e0849fa38819281";
		System.err.println(MD5Encoder(b + "csdfse784", "utf-8"));
	}

}

