package io.sitewhere;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.sitewhere.exception.SiteWhereException;
import io.sitewhere.k8s.crd.ISiteWhereKubernetesClient;
import io.sitewhere.k8s.crd.SiteWhereKubernetesClient;

/**
 * Common base class for microservices.
 */
@ApplicationScoped
public class Microservice {

    /** Static logger instance */
    private static Logger LOGGER = LoggerFactory.getLogger(Microservice.class);

    /** Kubernetes client */
    private DefaultKubernetesClient kubernetesClient;

    /** SiteWhere Kubernetes client wrapper */
    private ISiteWhereKubernetesClient sitewhereKubernetesClient;

    /** Shared informer factory for k8s resources */
    private SharedInformerFactory sharedInformerFactory;

    /**
     * Called when microservice is started.
     * 
     * @param ev
     */
    void onStart(@Observes StartupEvent ev) {
	LOGGER.info("Application starting...");

	// Initialize configuration model.
	try {
	    initializeK8sConnectivity();
	} catch (SiteWhereException e) {
	    LOGGER.error("Unable to start.", e);
	}
    }

    /**
     * Initialize Kubernetes connectivity.
     * 
     * @throws SiteWhereException
     */
    protected void initializeK8sConnectivity() throws SiteWhereException {
	Config config = new ConfigBuilder().withNamespace(null).build();
	this.kubernetesClient = new DefaultKubernetesClient(config);
	this.sitewhereKubernetesClient = new SiteWhereKubernetesClient(getKubernetesClient());
	this.sharedInformerFactory = getKubernetesClient().informers();

	// Create controllers and start informers.
	// createKubernetesResourceControllers(getSharedInformerFactory());
	getSharedInformerFactory().startAllRegisteredInformers();
    }

    /**
     * Called when microservice is stopped.
     * 
     * @param ev
     */
    void onStop(@Observes ShutdownEvent ev) {
	LOGGER.info("Application shutting down...");
    }

    protected DefaultKubernetesClient getKubernetesClient() {
	return kubernetesClient;
    }

    protected ISiteWhereKubernetesClient getSitewhereKubernetesClient() {
	return sitewhereKubernetesClient;
    }

    protected SharedInformerFactory getSharedInformerFactory() {
	return sharedInformerFactory;
    }
}
