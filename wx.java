package com.crxl.h5.controller;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crxl.h5.Entity.Token_wx;
import com.crxl.h5.Entity.User_wx;
import com.google.gson.Gson;


/**
 * 我的测评
 *
 */
@Controller
@RequestMapping("/home")
public class wx {
	
	@RequestMapping(value="/wx.do")
	@ResponseBody
	public void getwxuuer(String code,String state) throws Exception {
		System.out.println("进来了");
		System.out.println("code="+code);
		System.out.println("state="+state);
		String str ="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=wx0817b8911ec9c5d3"
				+ "&secret=ab8e9851d47c9dda61df148564ed47db&code="+code+ "&grant_type=authorization_code";
		System.out.println(str);
		String buffer = getwx(str);
		Token_wx token_wx = new Token_wx();
		Gson gson = new Gson();
		token_wx=gson.fromJson(buffer.toString(), Token_wx.class );
		System.out.println(token_wx);
		System.out.println("operid="+token_wx.getOpenid());
		String str1 = " https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token="+token_wx.getAccess_token()
				+ "&openid="+token_wx.getOpenid()
				+ "&lang=zh_CN";
		
		buffer=getwx(str1);
		System.out.println(buffer);
		User_wx wx = new User_wx();
		wx=gson.fromJson(buffer.toString(), User_wx.class);
		System.out.println("-------------------开始获取微信用户数据-------------------");
		System.out.println(wx);
		
		
	}
	public static String getwx(String str) {
		StringBuffer buffer = null;
		URL url;
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStream is = null;
		try {
			url = new URL(str);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			is=conn.getInputStream();
			isr= new InputStreamReader(is,"utf-8");
			br= new BufferedReader(isr);
			buffer=new StringBuffer();
			String line=null;
			while((line=br.readLine())!=null) {
				buffer.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(br!=null) {
				try {
					br.close();
					System.out.println("关闭br");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null) {
				try {
					isr.close();
					System.out.println("关闭isr");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is!=null) {
				try {
					is.close();
					System.out.println("关闭is");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return buffer.toString();
	}
		
	
}
