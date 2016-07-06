package com.mjlf.cfmg.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.junit.Test;

public class PropertiesMailConfig {
	public static InputStream inputStream = PropertiesMailConfig.class.getClassLoader()
			.getResourceAsStream("com/mjlf/cfmg/mail/mailconfig.properties");
	public static MailConfigure getMailConfig() throws IOException{
		MailConfigure mailConfigure = new MailConfigure();
		BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		try {
			Properties properties = new Properties();
			properties.load(bf);
			mailConfigure.setSmtp(properties.getProperty("smtp"));
			mailConfigure.setPort(properties.getProperty("port"));
			mailConfigure.setBody(properties.getProperty("body"));
			mailConfigure.setSubject(properties.getProperty("subject"));
			mailConfigure.setProtocol(properties.getProperty("protocol"));
			mailConfigure.setFrom(properties.getProperty("from"));
			mailConfigure.setPassword(properties.getProperty("password"));
			mailConfigure.setUsername(properties.getProperty("username"));
			return mailConfigure;
		} catch (Exception e) {
			throw e;
		}
	}
}

