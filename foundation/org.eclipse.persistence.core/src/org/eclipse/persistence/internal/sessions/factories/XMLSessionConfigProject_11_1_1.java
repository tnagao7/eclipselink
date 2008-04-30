/*******************************************************************************
 * Copyright (c) 1998, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.internal.sessions.factories;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.oxm.XMLDescriptor;
import org.eclipse.persistence.oxm.mappings.XMLDirectMapping;
import org.eclipse.persistence.oxm.mappings.nullpolicy.NullPolicy;
import org.eclipse.persistence.internal.sessions.factories.model.platform.oc4j.Oc4j_11_1_1_PlatformConfig;
import org.eclipse.persistence.internal.sessions.factories.model.platform.SunAS9PlatformConfig;
import org.eclipse.persistence.internal.sessions.factories.model.platform.WebLogic_10_PlatformConfig;
import org.eclipse.persistence.internal.sessions.factories.model.platform.WebLogic_9_PlatformConfig;
import org.eclipse.persistence.internal.sessions.factories.model.platform.WebSphere_6_1_PlatformConfig;
import org.eclipse.persistence.internal.sessions.factories.model.transport.Oc4jJGroupsTransportManagerConfig;
import org.eclipse.persistence.internal.sessions.factories.model.transport.TransportManagerConfig;

/**
 * INTERNAL:
 * OX mapping project for the 11gR1 sessions XML schema.
 * This subclasses the 10.1.3 project and adds any changes.
 */
public class XMLSessionConfigProject_11_1_1 extends XMLSessionConfigProject {
    // Default null values
    public static final boolean BIND_ALL_PARAMETERS_DEFAULT = true;
    public static final boolean USE_SINGLE_THREADED_NOTIFICATION_DEFAULT = false;

    public XMLSessionConfigProject_11_1_1() {
        super();
        addDescriptor(buildOc4jJGroupsTransportManagerConfigDescriptor());
        addDescriptor(buildServerPlatformConfigDescriptorFor(Oc4j_11_1_1_PlatformConfig.class));
    	addDescriptor(buildServerPlatformConfigDescriptorFor(SunAS9PlatformConfig.class));
        addDescriptor(buildServerPlatformConfigDescriptorFor(WebLogic_9_PlatformConfig.class));
        addDescriptor(buildServerPlatformConfigDescriptorFor(WebLogic_10_PlatformConfig.class));
        addDescriptor(buildServerPlatformConfigDescriptorFor(WebSphere_6_1_PlatformConfig.class));
    }

    public ClassDescriptor buildSessionConfigsDescriptor() {
        XMLDescriptor descriptor = (XMLDescriptor)super.buildSessionConfigsDescriptor();
        descriptor.setDefaultRootElement("sessions");
        return descriptor;
    }
    
    public ClassDescriptor buildDatabaseLoginConfigDescriptor() {
        ClassDescriptor descriptor = super.buildDatabaseLoginConfigDescriptor();

        XMLDirectMapping bindAllParametersMapping = (XMLDirectMapping)descriptor.getMappingForAttributeName("m_bindAllParameters");
        bindAllParametersMapping.setNullValue(new Boolean(BIND_ALL_PARAMETERS_DEFAULT));

        XMLDirectMapping validateConnectionHealthOnErrorMapping = new XMLDirectMapping();
        validateConnectionHealthOnErrorMapping.setAttributeName("connectionHealthValidatedOnError");
        validateConnectionHealthOnErrorMapping.setGetMethodName("isConnectionHealthValidatedOnError");
        validateConnectionHealthOnErrorMapping.setSetMethodName("setConnectionHealthValidatedOnError");
        validateConnectionHealthOnErrorMapping.setXPath("toplink:connection-health-validated-on-error/text()");
        validateConnectionHealthOnErrorMapping.setNullPolicy(new NullPolicy(null, false, false, false));
        validateConnectionHealthOnErrorMapping.setNullValue(true);
        descriptor.addMapping(validateConnectionHealthOnErrorMapping);

        XMLDirectMapping delayBetweenReconnectAttempts = new XMLDirectMapping();
        delayBetweenReconnectAttempts.setAttributeName("delayBetweenConnectionAttempts");
        delayBetweenReconnectAttempts.setGetMethodName("getDelayBetweenConnectionAttempts");
        delayBetweenReconnectAttempts.setSetMethodName("setDelayBetweenConnectionAttempts");
        delayBetweenReconnectAttempts.setXPath("toplink:delay-between-reconnect-attempts/text()");
        delayBetweenReconnectAttempts.setNullPolicy(new NullPolicy(null, false, false, false));
        descriptor.addMapping(delayBetweenReconnectAttempts);

        XMLDirectMapping queryRetryAttemptCount = new XMLDirectMapping();
        queryRetryAttemptCount.setAttributeName("queryRetryAttemptCount");
        queryRetryAttemptCount.setGetMethodName("getQueryRetryAttemptCount");
        queryRetryAttemptCount.setSetMethodName("setQueryRetryAttemptCount");
        queryRetryAttemptCount.setXPath("toplink:query-retry-attempt-count/text()");
        queryRetryAttemptCount.setNullPolicy(new NullPolicy(null, false, false, false));
        descriptor.addMapping(queryRetryAttemptCount);

        XMLDirectMapping pingSQLMapping = new XMLDirectMapping();
        pingSQLMapping.setAttributeName("pingSQL");
        pingSQLMapping.setGetMethodName("getPingSQL");
        pingSQLMapping.setSetMethodName("setPingSQL");
        pingSQLMapping.setXPath("toplink:ping-sql/text()");
        pingSQLMapping.setNullPolicy(new NullPolicy(null, false, false, false));
        descriptor.addMapping(pingSQLMapping);

        return descriptor;
    }

    public ClassDescriptor buildOc4jJGroupsTransportManagerConfigDescriptor() {
        XMLDescriptor descriptor = new XMLDescriptor();
        descriptor.setJavaClass(Oc4jJGroupsTransportManagerConfig.class);
        descriptor.getInheritancePolicy().setParentClass(TransportManagerConfig.class);

        XMLDirectMapping useSingleThreadedNotificationMapping = new XMLDirectMapping();
        useSingleThreadedNotificationMapping.setAttributeName("m_useSingleThreadedNotification");
        useSingleThreadedNotificationMapping.setGetMethodName("useSingleThreadedNotification");
        useSingleThreadedNotificationMapping.setSetMethodName("setUseSingleThreadedNotification");
        useSingleThreadedNotificationMapping.setXPath("use-single-threaded-notification/text()");
        useSingleThreadedNotificationMapping.setNullValue(new Boolean(USE_SINGLE_THREADED_NOTIFICATION_DEFAULT));
        descriptor.addMapping(useSingleThreadedNotificationMapping);

        XMLDirectMapping topicNameMapping = new XMLDirectMapping();
        topicNameMapping.setAttributeName("m_topicName");
        topicNameMapping.setGetMethodName("getTopicName");
        topicNameMapping.setSetMethodName("setTopicName");
        topicNameMapping.setXPath("topic-name/text()");
        descriptor.addMapping(topicNameMapping);

        return descriptor;
    }

    public ClassDescriptor buildTransportManagerConfigDescriptor() {
        XMLDescriptor descriptor = (XMLDescriptor)super.buildTransportManagerConfigDescriptor();
        descriptor.getInheritancePolicy().addClassIndicator(Oc4jJGroupsTransportManagerConfig.class, "oc4j-jgroups-transport");

        return descriptor;
    }
    
    public ClassDescriptor buildServerPlatformConfigDescriptor() {
        XMLDescriptor descriptor =(XMLDescriptor)super.buildServerPlatformConfigDescriptor();
        descriptor.getInheritancePolicy().addClassIndicator(Oc4j_11_1_1_PlatformConfig.class, "oc4j-1111-platform");
        descriptor.getInheritancePolicy().addClassIndicator(SunAS9PlatformConfig.class, "sunas-9-platform");
        descriptor.getInheritancePolicy().addClassIndicator(WebLogic_9_PlatformConfig.class, "weblogic-9-platform");
        descriptor.getInheritancePolicy().addClassIndicator(WebLogic_10_PlatformConfig.class, "weblogic-10-platform");
        descriptor.getInheritancePolicy().addClassIndicator(WebSphere_6_1_PlatformConfig.class, "websphere-61-platform");
	
        return descriptor;
    }
}
