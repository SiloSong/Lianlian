package application.cartoon.com.lianlian;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils extends Thread{
	protected static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
	protected static final String CHARSET = "UTF-8";
	/** 建立连接的超时时间 */
	protected static final int connectTimeout = 5 * 1000;
	/** 建立到资源的连接后从 input 流读入时的超时时间 */
	protected static final int readTimeout = 10 * 1000;
	public static final int FLAG_POST=0;
	public static final int  FLAG_GET=1;
	public static final int SUCCESS=1;
	public static final int FAIL=2;
	private Handler mhandler;
	private String url;
	private String params;
	private int flag;

	/**
	 * post方式用到的构造器
	 * @param mhandler
	 * @param url
	 * @param params
	 * @param flag
	 */
	public HttpUtils(Handler mhandler, String url, String params, int flag) {
		this.mhandler = mhandler;
		this.url = url;
		this.params = params;
		this.flag = flag;
	}
	/**
	 * post方式用到的构造器
	 * @param mhandler
	 * @param url
	 * @param flag
	 */
	public HttpUtils(Handler mhandler, String url, int flag) {
		this.mhandler = mhandler;
		this.url = url;
		this.flag = flag;
	}
public HttpUtils(){


}
	@Override
	public void run() {
		String json=null;
		if (flag==FLAG_GET){
			json=getString(url);
		}else if (flag==FLAG_POST){
			json=postString(url,params);
		}
		Message msg=Message.obtain();
		if (json!=null){
			msg.what=HttpUtils.SUCCESS;
            msg.obj=json;
		}else{
			msg.what=HttpUtils.FAIL;
		}
		mhandler.sendMessage(msg);
	}



	public HttpURLConnection createConnection(String url) throws IOException {
		String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl)
				.openConnection();
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		return conn;

	}

	public InputStream getInputStream(String url) {
		InputStream is = null;

		try {
			HttpURLConnection conn = createConnection(url);
			conn.setRequestMethod("GET");
			if (!shouldBeProcessed(conn)) return null;
			is = conn.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return is;
	}

	public String getString(String url) {
		String result = null;
		InputStream is = null;
		BufferedReader br = null;
		try {

			is = getInputStream(url);
			if (is==null) return null;
			br = new BufferedReader(new InputStreamReader(is, CHARSET));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}

		return result;
	}

	public String postString(String url, String params) {
		String result = null;
		OutputStream os = null;
		InputStream is = null;
		BufferedReader br = null;
		try {
			HttpURLConnection conn = createConnection(url);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);// POST方式不能缓存数据
			// conn.setRequestProperty(field, newValue);//header

			conn.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
			if (params != null) {
				os = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				dos.write(params.getBytes(CHARSET));
				dos.flush();
				dos.close();
			}
			if (!shouldBeProcessed(conn)) return null;
			is = conn.getInputStream();
			br = new BufferedReader(new InputStreamReader(is, CHARSET));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
			}
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}

		return result;
	}

	protected boolean shouldBeProcessed(HttpURLConnection conn)
			throws IOException {
		return conn.getResponseCode() == 200;
	}


}
