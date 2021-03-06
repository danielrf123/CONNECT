/*
 * Copyright (c) 2009-2015, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.messaging.client;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.messaging.service.BaseServiceEndpoint;
import gov.hhs.fha.nhinc.messaging.service.ServiceEndpoint;
import gov.hhs.fha.nhinc.messaging.service.decorator.MTOMServiceEndpointDecorator;
import gov.hhs.fha.nhinc.messaging.service.decorator.cxf.WsAddressingServiceEndpointDecorator;
import gov.hhs.fha.nhinc.messaging.service.port.CachingCXFSecuredServicePortBuilder;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;

/**
 * @author akong
 *
 */
public class CONNECTTestClient<T> implements CONNECTClient<T> {

    protected ServiceEndpoint<T> serviceEndpoint = null;

    /**
     * Constructor for a client used solely for unit tests. This class will only do the base configuration for the
     * endpoint, but it will expose the service endpoint for test classes to use. This class will also use the caching
     * mechanism that will single instance the port.
     *
     * @param portDescriptor
     */
    public CONNECTTestClient(ServicePortDescriptor<T> portDescriptor) {
        serviceEndpoint = new BaseServiceEndpoint<T>(new CachingCXFSecuredServicePortBuilder<T>(portDescriptor).createPort());
    }

    public ServiceEndpoint<T> getServiceEndpoint() {
        return serviceEndpoint;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.messaging.client.CONNECTClient#getPort()
     */
    @Override
    public T getPort() {
        return serviceEndpoint.getPort();
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.messaging.client.CONNECTClient#invokePort(java.lang.Class, java.lang.String, java.lang.Object)
     */
    @Override
    public Object invokePort(Class<T> portClass, String methodName, Object ... operationInput) throws Exception {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.messaging.client.CONNECTClient#supportMtom()
     */
    @Override
    public void enableMtom() {
        serviceEndpoint = new MTOMServiceEndpointDecorator<T>(serviceEndpoint);
    }

    /* (non-Javadoc)
     * @see gov.hhs.fha.nhinc.messaging.client.CONNECTClient#enableWSA(gov.hhs.fha.nhinc.common.nhinccommon.AssertionType, java.lang.String, java.lang.String)
     */
    @Override
    public void enableWSA(AssertionType assertion, String wsAddressingTo, String wsAddressingActionId) {
        serviceEndpoint = new WsAddressingServiceEndpointDecorator<T>(serviceEndpoint, wsAddressingTo, wsAddressingActionId, assertion);
    }
}
