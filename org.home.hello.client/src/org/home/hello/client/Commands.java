package org.home.hello.client;

import org.apache.felix.service.command.Descriptor;
import org.home.hello.api.IHello;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(name = "org.home.hello.client", service = Commands.class, immediate=true, property = { "osgi.command.scope=demo",
        "osgi.command.function=greet" })
public class Commands {
    private IHello hello;

    @Reference
    synchronized protected void setHello(IHello hello) {
        this.hello = hello;
    }

    synchronized protected void unsetHello(IHello hello) {
        this.hello = null;
    }

    @Descriptor("Prints greeting.")
    public void greet(@Descriptor("Can't be null.") String name) {
        String greeting = null;
        try {
            greeting = hello.sayHello(name);
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        if (greeting != null) {
            System.out.println(greeting);
        }
    }
}
