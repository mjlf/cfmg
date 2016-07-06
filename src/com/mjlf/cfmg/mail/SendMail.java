package com.mjlf.cfmg.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;

public class SendMail {
	/**
	 * 邮箱服务器 如：smtp.163.com
	 */
	private String smtp = "";
	/**
	 * 邮件种类， 发送 smtp/ 获取 pop3
	 */
	  private String protocol = "";
	  /**
	   * 邮件服务器端口
	   */
	  private String port = "";
	  /**
	   * 邮箱账号
	   */
	  private String username = "";
	  /**
	   * 账号独立密码， 不同的邮箱密码不同， 有的邮箱可能需要设置对了密码
	   */
	  private String password = "";
	  /**
	   * 发件人邮箱
	   */
	  private String from = "";
//	  private String to = "";
	  /**
	   * 邮件主题
	   */
	  private String subject = "";
	  /**
	   * 邮件主体
	   */
	  private String body = "";
	  
	  Map<String, String> image;
	  /**
	   * 附件集合文件全路径
	   */
	  List<String> list;
	  /**
	   * 收件人邮箱账号集合
	   */
	  private Set<String> sto = new HashSet<String>();
	  
	  /**
	   * 
	   * @param map 设置 smtp 邮件服务器， protocol， port 端口， username 发件人, password 发件人密码, from 发件人账号, to 收件人, subject 标题, body 内容
	   * @param filelist 附件集合
	   * @param image 
	   * @param to 收件人邮箱账号集合
	   */
	  public SendMail(MailConfigure configure)
	  {
	    this.smtp = configure.getSmtp();
	    this.protocol = configure.getProtocol();
	    this.port = configure.getPort();
	    this.username = configure.getUsername();
	    this.password = configure.getPassword();
	    this.from = configure.getFrom();
//	    this.to = ((String)map.get("to"));
	    this.sto = configure.getToUserName();
	    this.subject = configure.getSubject();
	    this.body = configure.getBody();

	    this.list = configure.getAccessory();
	    this.image = configure.getImage();
	  }

	  /**
	   * 邮件发送方法
	   * @throws Exception
	   */
	  public void send() throws Exception {
	    Properties pros = new Properties();
	    pros.setProperty("mail.transport.protocol", this.protocol);
	    pros.setProperty("mail.host", this.smtp);
	    pros.put("mail.smtp.auth", "true");
	    pros.put("mail.smtp.port", this.port);
	    MySendMailAuthenticator ma = new MySendMailAuthenticator(this.username, this.password);
	    Session session = Session.getInstance(pros, ma);
	    session.setDebug(true);

	    MimeMessage msg = createMessage(session);

	    Transport ts = session.getTransport();
	    ts.connect();
	    ts.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
	    ts.close();
	  }
	  
	  /**
	   * 
	   * @param session
	   * @return
	   * @throws Exception
	   */
	  private MimeMessage createMessage(Session session) throws Exception
	  {
	    MimeMessage message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(this.from));
//	    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.to));
	    Address[] address = new InternetAddress[this.sto.size()];
	    Iterator<String> iterator = sto.iterator();
	    int k = 0;
	    while(iterator.hasNext()){
	    	address[k++] = new InternetAddress(iterator.next());
	    }
	    message.setRecipients(Message.RecipientType.TO, address);
	    message.setSubject(this.subject);
	    MimeMultipart allMultipart = new MimeMultipart();
	    MimeBodyPart contentpart = createContent(this.body);
	    allMultipart.addBodyPart(contentpart);
	    for (int i = 0; i < this.list.size(); ++i) {
	      allMultipart.addBodyPart(createAttachment((String)this.list.get(i)));
	    }
	    message.setContent(allMultipart);
	    message.saveChanges();
	    return message;
	  }

	  private MimeBodyPart createContent(String body)
	    throws Exception
	  {
	    MimeBodyPart contentPart = new MimeBodyPart();
	    MimeMultipart contentMultipart = new MimeMultipart("related");

	    MimeBodyPart htmlbodypart = new MimeBodyPart();
	    htmlbodypart.setContent(this.body, "text/html;charset=UTF-8");
	    contentMultipart.addBodyPart(htmlbodypart);

	    if ((this.image != null) && (this.image.size() > 0)) {
	      Set set = this.image.entrySet();
	      for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
	        Map.Entry entry = (Map.Entry)iterator.next();

	        MimeBodyPart gifBodyPart = new MimeBodyPart();
	        FileDataSource fds = new FileDataSource((String)entry.getValue());

	        gifBodyPart.setDataHandler(new DataHandler(fds));
	        gifBodyPart.setContentID((String)entry.getKey());
	        contentMultipart.addBodyPart(gifBodyPart);
	      }

	    }

	    contentPart.setContent(contentMultipart);
	    return contentPart;
	  }

	  private MimeBodyPart createAttachment(String filename) throws Exception
	  {
	    MimeBodyPart attachPart = new MimeBodyPart();
	    FileDataSource fsd = new FileDataSource(filename);
	    attachPart.setDataHandler(new DataHandler(fsd));
	    attachPart.setFileName(fsd.getName());
	    return attachPart;
	  }
}
