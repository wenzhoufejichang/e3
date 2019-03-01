package com.hzm.register;

import com.hzm.pojo.Message;

public interface LoginInterface {

	public abstract Message login(String username, String password);

	public abstract Message getName(String token);

	public abstract void remove(String token);

}
