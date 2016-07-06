package com.mjlf.cfmg.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author demo1
 *
 */
public class MailConfigure {
	/**
	 * 邮件服务器
	 */
	private String smtp;
	/**
	 * 邮件服务器端口
	 */
	private String port;
	/**
	 * 控制发送/接受邮件
	 * 默认发送
	 */
	private String protocol = "smtp";
	/**
	 * 发送者邮箱账号
	 */
	private String from;
	/**
	 * 发送者邮箱账号/必须与from相同
	 */
	private String username;
	/**
	 * 发送者邮箱账号密码
	 */
	private String password;
	/**
	 * 接受邮件账号集合
	 */
	private Set<String> toUserName = new HashSet<String>();
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String body;
	/**
	 * 邮件附件集合
	 */
	private List<String> accessory = new ArrayList<String>();
	/**
	 * 不知道是什么
	 */
	private Map<String, String> image = new HashMap<String, String>();
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public List<String> getAccessory() {
		return accessory;
	}
	public void setAccessory(List<String> accessory) {
		this.accessory = accessory;
	}
	public Map<String, String> getImage() {
		return image;
	}
	public void setImage(Map<String, String> image) {
		this.image = image;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getToUserName() {
		return toUserName;
	}
	public void setToUserName(Set<String> toUserName) {
		this.toUserName = toUserName;
	}
	
	/**
	 * 添加收件者账号， 并对其进去正则表达式匹配，
	 * 	如果匹配成功，添加到toUserName中
	 * 	如果匹配不成功， 添加抛出异常
	 * @param mail
	 * @throws Exception
	 */
	public void addToUserName(String mail) throws Exception {
		String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(mail);
			if(matcher.matches()){
				this.toUserName.add(mail);
			}else{
				throw new Exception(mail+":不是一个邮箱账", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public String toString() {
		return "MailConfigure [smtp=" + smtp + ", port=" + port + ", protocol="
				+ protocol + ", from=" + from + ", username=" + username
				+ ", password=" + password + ", toUserName=" + toUserName
				+ ", subject=" + subject + ", body=" + body + ", accessory="
				+ accessory + ", image=" + image + "]";
	}
}
