package org.home.remote.hello.service;

import java.util.Map;

import org.home.hello.api.IHello;
import org.home.remote.hello.api.IRemoteHello;
import org.home.remote.hello.api.NotValidArgument;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

@Component(name="org.home.remote.hello.service", service={}, immediate=true, configurationPolicy=ConfigurationPolicy.REQUIRE)
public class Hello implements IRemoteHello {
	
	/**
	 * Wrap IHello service.
	 */
    private IHello hello;
	
    private Communicator communicator;
	private ObjectAdapter adapter;

	@Override
	public String sayHello(String name, Current current) throws NotValidArgument{
	    String greeting;
	    try {
	        greeting = hello.sayHello(name);
	    } catch (NullPointerException | IllegalArgumentException e) {
	        e.printStackTrace();
	        throw new NotValidArgument(e.getMessage());
	    }
		return greeting;
	}

	@Activate
	synchronized protected void activate(Map<String, ?> props) {
    	InitializationData initData = new InitializationData();
    	initData.properties = Util.createProperties();
   		props.forEach((k,v)->initData.properties.setProperty(k, v.toString()));
   		communicator = Util.initialize(initData);
   		final String adapterName = "Hello";
		adapter = communicator.createObjectAdapter(adapterName);
		adapter.add(this, Util.stringToIdentity(adapterName));
		adapter.activate();
	}
	
	@Deactivate
	synchronized protected void deactivate() {
		if (adapter != null) {
			adapter.destroy();
			adapter = null;
			communicator.close();
			communicator = null;
		}
	}
	
	@Reference
	synchronized protected void setHello(IHello hello) {
	    this.hello = hello;
	}
	
	synchronized protected void unsetHello(IHello hello) {
	    this.hello = null;
	}
	
}
