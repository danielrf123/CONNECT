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
package gov.hhs.fha.nhinc.mpi.adapter.component.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hl7.v3.PRPAIN201305UV02;
import org.hl7.v3.PRPAIN201306UV02;

import gov.hhs.fha.nhinc.adaptercomponentmpi.AdapterComponentMpiSecuredPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.messaging.client.CONNECTCXFClientFactory;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.fha.nhinc.mpi.adapter.component.proxy.service.AdapterComponentMpiSecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.properties.PropertyAccessException;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;

/**
 * Proxy to call the secured AdapterComponentMPI interface.
 *
 */
public class AdapterComponentMpiProxyWebServiceSecuredImpl implements AdapterComponentMpiProxy {

    private static final Logger LOG = LoggerFactory.getLogger(AdapterComponentMpiProxyWebServiceSecuredImpl.class);
    private final WebServiceProxyHelper oProxyHelper = new WebServiceProxyHelper();

    /**
     *
     * @param portDescriptor the port descriptor
     * @param url the intended url
     * @param assertion the message assertion
     * @return a CONNECTClient object for AdapterComponentMpiSecuredPortType
     * @throws PropertyAccessException
     */
    protected CONNECTClient<AdapterComponentMpiSecuredPortType> getCONNECTClientSecured(
            ServicePortDescriptor<AdapterComponentMpiSecuredPortType> portDescriptor, String url,
            AssertionType assertion) throws PropertyAccessException {

        return CONNECTCXFClientFactory.getInstance().getCONNECTClientSecured(portDescriptor, url, assertion);
    }

    /**
     * Find the matching candidates from the MPI.
     *
     * @param request The information to use for matching.
     * @param assertion The assertion data.
     * @return The matches that are found.
     */
    @Override
    public PRPAIN201306UV02 findCandidates(PRPAIN201305UV02 request, AssertionType assertion) {
        String url = null;
        PRPAIN201306UV02 response = new PRPAIN201306UV02();
        String sServiceName = NhincConstants.ADAPTER_COMPONENT_MPI_SECURED_SERVICE_NAME;

        try {
            if (request != null) {
                LOG.debug("Before target system URL look up.");
                url = oProxyHelper.getAdapterEndPointFromConnectionManager(sServiceName);
                LOG.debug("After target system URL look up. URL for service: " + sServiceName + " is: " + url);

                if (NullChecker.isNotNullish(url)) {
                    ServicePortDescriptor<AdapterComponentMpiSecuredPortType> portDescriptor =
                            new AdapterComponentMpiSecuredServicePortDescriptor();

                    CONNECTClient<AdapterComponentMpiSecuredPortType> client =
                            getCONNECTClientSecured(portDescriptor, url, assertion);

                    response =
                            (PRPAIN201306UV02) client.invokePort(AdapterComponentMpiSecuredPortType.class,
                                    "findCandidates", request);
                } else {
                    LOG.error("Failed to call the web service (" + sServiceName + ").  The URL is null.");
                }
            } else {
                LOG.error("Failed to call the web service (" + sServiceName + ").  The input parameter is null.");
            }
        } catch (Exception e) {
            LOG.error("Failed to call the web service (" + sServiceName + ").  An unexpected exception occurred.  "
                    + "Exception: " + e.getMessage(), e);
        }

        return response;
    }
}
