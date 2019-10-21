package org.home.remote.hello.client;

import java.util.Objects;

import org.osgi.framework.Bundle;

/**
 * Custom class loader.
 * 
 * @see <a href="https://doc.zeroc.com/ice/latest/language-mappings/java-mapping/custom-class-loaders">Custom Class Loaders</a>
 */
public class IceRuntimeClassLoader extends ClassLoader {
    
    private Bundle bundle;
    
    private String defaultPackage;

    public IceRuntimeClassLoader(Bundle bundle) {
        this.bundle = Objects.requireNonNull(bundle, "bundle is null.");
        defaultPackage = "";
    }
    
    public IceRuntimeClassLoader(Bundle bundle, String defaultPackage) {
        this.bundle = Objects.requireNonNull(bundle, "bundle is null.");
//        this.defaultPackage = defaultPackage == null || defaultPackage.isBlank() ? "" : defaultPackage.strip();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        final String className = defaultPackage.isEmpty() ? name : defaultPackage + "." + name;
        return bundle.loadClass(className);
    }

}
