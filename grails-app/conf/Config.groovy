// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.config.locations = [ configs.WroConfig ]
	

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// Uncomment and edit the following lines to start using Grails encoding & escaping improvements

/* remove this line
// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside null
				scriptlet = 'none' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		filteringCodecForContentType {
			//'text/html' = 'html'
		}
	}
}
remove this line */




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

wro.grailsWroManagerFactory.preProcessors = [
	new CssUrlRewritingProcessor(),
	new CssImportPreProcessor(),
	new SemicolonAppenderPreProcessor(),
	new JSMinProcessor(),
	new JawrCssMinifierProcessor(),
	ExtensionsAwareProcessorDecorator.decorate(new CoffeeScriptProcessor()).addExtension("coffee"),
]
wro.grailsWroManagerFactory.postProcessors = [
	new CssVariablesProcessor(),
	new LessCssProcessor(),
]



/** PreProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory  */
/**
wro.grailsWroManagerFactory.preProcessors = [
	new CssUrlRewritingProcessor(),
	new CssImportPreProcessor(),
//    new BomStripperPreProcessor(),
	new SemicolonAppenderPreProcessor(),
	new JSMinProcessor(),
	new JawrCssMinifierProcessor(),
	ExtensionsAwareProcessorDecorator.decorate(new CoffeeScriptProcessor()).addExtension("coffee"),
	
]
**/


/** postProcessor used by wro4j.grails.plugin.GrailsWroManagerFactory  */
/**
wro.grailsWroManagerFactory.postProcessors = [
	new CssVariablesProcessor(),
	ExtensionsAwareProcessorDecorator.decorate(new LessCssProcessor()).addExtension("less"),
]
**/

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
