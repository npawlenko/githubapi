package com.example.github.provider;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@ApplicationScoped
public class MessageProvider {

	private final ResourceBundle resourceBundle;

	public MessageProvider() {
		this.resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
	}

	public String getMessage(String key) {
		return resourceBundle.getString(key);
	}

	public String getMessageOrDefault(String key, String defaultValue) {
		try {
			return getMessage(key);
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}
}
