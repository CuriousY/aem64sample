package com.nk.sample.core.utils;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * OSGI Context utils to get service reference from non osgi context and
 * other related operations.
 * 
 * @author <a href="mailto:mauricio.rodriguez@globant.com">mauricio.rodriguez</a>
 *
 */

public class OSGIUtils {
	 public static <T> T getServiceReference(final Class<T> serviceClass) {
	        /**
	        * Get the BundleContext associated with the passed class reference.
	        */
	        BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
	        ServiceReference osgiRef = bundleContext.getServiceReference(serviceClass);
	        T serviceRef = (T) bundleContext.getService(osgiRef);
	        return serviceRef;
	    }
	    
	    public static <T> T getServiceReferenceOrNull(final Class<T> serviceClass) {
	        try {
	            /**
	            * Get the BundleContext associated with the passed class reference.
	            */
	            BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
	            ServiceReference osgiRef = bundleContext.getServiceReference(serviceClass);
	            T serviceRef = (T) bundleContext.getService(osgiRef);
	            return serviceRef;
	        } catch (RuntimeException e) {
	            return null;
	        }
	    }
	    
	    public static <T> T getServiceReferenceOrNull(final Class<T> serviceClass, String filter) {
	        try {
	            /**
	            * Get the BundleContext associated with the passed class reference.
	            */
	            BundleContext bundleContext = FrameworkUtil.getBundle(serviceClass).getBundleContext();
	            Collection<ServiceReference<T>> osgiRef = bundleContext.getServiceReferences(serviceClass, filter);
	            T serviceRef = (T) bundleContext.getService(osgiRef.iterator().next());
	            return serviceRef;
	        } catch (RuntimeException | InvalidSyntaxException e) {
	            return null;
	        }
	    }


}
