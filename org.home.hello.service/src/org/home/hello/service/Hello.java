package org.home.hello.service;

import java.util.Objects;

import org.home.hello.api.IHello;
import org.osgi.service.component.annotations.Component;

@Component(name="org.home.hello.service", service=IHello.class)
public class Hello implements IHello {

	@Override
	public String sayHello(String name) {
	    final String greetingName = Objects.requireNonNull(name, "name is null.").strip();
	    if (greetingName.isEmpty()) {
	        throw new IllegalArgumentException("name is empty.");
	    }
		return "Hello " + greetingName + ".";
	}

}
