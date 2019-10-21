package org.home.remote.hello.client;

import java.util.Map;

import org.home.hello.api.IHello;
import org.home.remote.hello.api.IRemoteHelloPrx;
import org.home.remote.hello.api.NotValidArgument;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.Util;

@Component(name="org.home.remote.hello.client", service= {IHello.class}, configurationPolicy=ConfigurationPolicy.REQUIRE)
public class Client implements IHello {

	private Communicator communicator;
	
	private IRemoteHelloPrx helloPrx;
	
	@Activate
	synchronized void activate(Map<String, ?> props, BundleContext ctx) {
    	InitializationData initData = new InitializationData();
    	initData.properties = Util.createProperties();
   		props.forEach((k,v)->initData.properties.setProperty(k, v.toString()));
//   		final String defaultPackage = initData.properties.getProperty("Ice.Default.Package");
//   		initData.classLoader = defaultPackage == null ? new IceRuntimeClassLoader(ctx.getBundle()) : new IceRuntimeClassLoader(ctx.getBundle(),defaultPackage);
   		communicator = Util.initialize(initData);
        helloPrx = IRemoteHelloPrx.checkedCast(communicator.propertyToProxy("Hello.Proxy"));//.ice_twoway().ice_secure(true);
	}
	
	@Deactivate
	synchronized void deactivate() {
	    if (helloPrx != null) {
	        helloPrx = null;
	    }
		if (communicator != null) {
			communicator.close();
			communicator = null;
		}
	}

    @Override
    public String sayHello(String name) {
        String greeting;
        try {
            greeting = helloPrx.sayHello(name);
        } catch (NotValidArgument e) {
            throw new IllegalArgumentException(e);
        }
        return greeting;
    }
}
