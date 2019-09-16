package org.home.hello.api;

public interface IHello {
	/**
	 * 
	 * @param name
	 * @return greeting string
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
    String sayHello(String name);
}
