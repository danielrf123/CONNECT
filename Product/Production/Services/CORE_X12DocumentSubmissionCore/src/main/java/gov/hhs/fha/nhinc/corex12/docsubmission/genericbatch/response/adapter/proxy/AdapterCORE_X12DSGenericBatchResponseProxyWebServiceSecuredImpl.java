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
package gov.hhs.fha.nhinc.corex12.docsubmission.genericbatch.response.adapter.proxy;

import gov.hhs.fha.nhinc.adaptercoresecured.AdapterCOREGenericBatchTransactionSecuredPortType;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterBatchSubmissionResponseSecuredType;
import gov.hhs.fha.nhinc.common.nhinccommonadapter.AdapterBatchSubmissionSecuredRequestType;
import gov.hhs.fha.nhinc.corex12.docsubmission.genericbatch.request.adapter.proxy.service.AdapterCORE_X12DSGenericBatchRequestSecuredServicePortDescriptor;
import gov.hhs.fha.nhinc.corex12.docsubmission.utils.CORE_X12DSAdapterExceptionBuilder;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClient;
import gov.hhs.fha.nhinc.messaging.client.CONNECTClientFactory;
import gov.hhs.fha.nhinc.messaging.service.port.ServicePortDescriptor;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants;
import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.webserviceproxy.WebServiceProxyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmission;
import org.caqh.soap.wsdl.corerule2_2_0.COREEnvelopeBatchSubmissionResponse;

/**
 * @author svalluripalli
 *
 */
public class AdapterCORE_X12DSGenericBatchResponseProxyWebServiceSecuredImpl extends CORE_X12DSAdapterExceptionBuilder implements AdapterCORE_X12DGenericBatchResponseProxy {

    private static final Logger LOG = LoggerFactory.getLogger(AdapterCORE_X12DSGenericBatchResponseProxyWebServiceSecuredImpl.class);
    private WebServiceProxyHelper oProxyHelper = null;

    /**
     * Constructor
     */
    public AdapterCORE_X12DSGenericBatchResponseProxyWebServiceSecuredImpl() {
        oProxyHelper = createWebServiceProxyHelper();
    }

    /**
     *
     * @return WebServiceProxyHelper
     */
    protected WebServiceProxyHelper createWebServiceProxyHelper() {
        return new WebServiceProxyHelper();
    }

    /**
     *
     * @param msg
     * @param assertion
     * @return COREEnvelopeBatchSubmissionResponse
     */
    @Override
    public COREEnvelopeBatchSubmissionResponse batchSubmitTransaction(COREEnvelopeBatchSubmission msg, AssertionType assertion) {
        COREEnvelopeBatchSubmissionResponse oResponse = null;

        try {
            String url = oProxyHelper.getAdapterEndPointFromConnectionManager(NhincConstants.ADAPTER_CORE_X12DS_GENERICBATCH_RESPONSE_SECURED_SERVICE_NAME);
            if (NullChecker.isNotNullish(url)) {
                ServicePortDescriptor<AdapterCOREGenericBatchTransactionSecuredPortType> portDescriptor = new AdapterCORE_X12DSGenericBatchRequestSecuredServicePortDescriptor();
                CONNECTClient<AdapterCOREGenericBatchTransactionSecuredPortType> client = CONNECTClientFactory.getInstance().getCONNECTClientSecured(portDescriptor, url, assertion);
                client.enableMtom();
                AdapterBatchSubmissionSecuredRequestType request = new AdapterBatchSubmissionSecuredRequestType();
                request.setCOREEnvelopeBatchSubmission(msg);
                client.enableMtom();
                AdapterBatchSubmissionResponseSecuredType adapterResponse = (AdapterBatchSubmissionResponseSecuredType) client.invokePort(AdapterCOREGenericBatchTransactionSecuredPortType.class, "batchSubmitTransaction", request);
                oResponse = adapterResponse.getCOREEnvelopeBatchSubmissionResponse();
            } else {
                oResponse = new COREEnvelopeBatchSubmissionResponse();
                buildCOREEnvelopeGenericBatchErrorResponse(msg, oResponse);
            }
        } catch (Exception ex) {
            LOG.error("Error sending Adapter CORE X12 Doc Submission Response Secured message: " + ex.getMessage(), ex);
            oResponse = new COREEnvelopeBatchSubmissionResponse();
            oResponse.setErrorMessage(NhincConstants.CORE_X12DS_ACK_ERROR_MSG);
            oResponse.setErrorCode(NhincConstants.CORE_X12DS_ACK_ERROR_CODE);
        }
        return oResponse;
    }
}
