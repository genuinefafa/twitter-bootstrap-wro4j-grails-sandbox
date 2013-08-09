
/*********************************************************************************/
/* Wro Config */
/*********************************************************************************/
/*
import ro.isdc.wro.model.resource.processor.impl.BomStripperPreProcessor
import ro.isdc.wro.model.resource.processor.impl.js.JSMinProcessor
import ro.isdc.wro.model.resource.processor.impl.js.SemicolonAppenderPreProcessor
import ro.isdc.wro.model.resource.processor.impl.css.*
*/
import ro.isdc.wro.model.resource.processor.impl.*
import ro.isdc.wro.model.resource.processor.impl.js.*
import ro.isdc.wro.model.resource.processor.impl.css.*
import ro.isdc.wro.model.resource.processor.decorator.*
import ro.isdc.wro.extensions.processor.css.*
import ro.isdc.wro.extensions.processor.js.*

import ro.isdc.wro.model.resource.locator.UrlUriLocator
import ro.isdc.wro.model.resource.locator.ClasspathUriLocator
import ro.isdc.wro.model.resource.locator.ServletContextUriLocator

println "WroConfig loading"

/**
 * Boolean flag for enable/disable resource gzipping.
 */
wro.gzipResources = true
/**
 * Parameter allowing to turn jmx on or off.
 */
wro.jmxEnabled = true
/**
 * Parameter containing an integer value for specifying how often (in seconds) the cache should be refreshed.
 */
wro.cacheUpdatePeriod = 0
/**
 * Parameter containing an integer value for specifying how often (in seconds) the model should be refreshed.
 */
wro.modelUpdatePeriod = 0
/**
 * Disable cache configuration option. When true, the processed content won't be cached in DEVELOPMENT mode. In
 * DEPLOYMENT mode changing this flag will have no effect.
 */
wro.disableCache = false
/**
 * Instructs wro4j to not throw an exception when a resource is missing.
 */
wro.ignoreMissingResources = true
/**
 * Encoding to use when reading and writing bytes from/to stream
 */
wro.encoding = null
/**
 * The fully qualified class name of the {@link ro.isdc.wro.manager.WroManagerFactory} implementation.
 */
wro.managerFactoryClassName = "wro4j.grails.plugin.GrailsWroManagerFactory"

/** PreProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory  */
wro.grailsWroManagerFactory.preProcessors = [
	new CssUrlRewritingProcessor(),
	new CssImportPreProcessor(),
	new SemicolonAppenderPreProcessor(),
	new JSMinProcessor(),
	new JawrCssMinifierProcessor(),
	ExtensionsAwareProcessorDecorator.decorate(new CoffeeScriptProcessor()).addExtension("coffee"),
]

/** postProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory  */
wro.grailsWroManagerFactory.postProcessors = [
	new CssVariablesProcessor(),
	new LessCssProcessor()
//	the following DO NOT work
//	ExtensionsAwareProcessorDecorator.decorate(new LessCssProcessor()).addExtension("less"),
]

/** uriLocator used by wro4j.grails.plugin.GrailsWroManagerFactory  */
wro.grailsWroManagerFactory.uriLocators = [
	new ServletContextUriLocator(),
	new ClasspathUriLocator(),
	new UrlUriLocator(),
]

/**
 * the name of MBean to be used by JMX to configure wro4j.
 */
wro.mbeanName = null
/**
 * The parameter used to specify headers to put into the response, used mainly for caching.
 */
wro.header = null

environments {
  production {
	wro.debug = false
  }
  development {
	wro.debug = true
  }
  test {
	wro.debug = true
  }
}
