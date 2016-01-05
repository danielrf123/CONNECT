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
package gov.hhs.fha.nhinc.connectmgr;

import gov.hhs.fha.nhinc.nhinclib.NhincConstants.GATEWAY_API_LEVEL;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants.NHIN_SERVICE_NAMES;
import gov.hhs.fha.nhinc.nhinclib.NhincConstants.UDDI_SPEC_VERSION;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author mweaver
 *
 */
public class PDSpecVersionRegistryTest {

    @Test
    public void testGetSupportedSpecs_g0_PD() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g0;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY);
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
    }

    @Test
    public void testGetSupportedSpecs_g1_PD() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g1;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY);
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
    }

    @Test
    public void testGetSupportedSpecsPDDeferredRequestService_g0() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g0;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY_DEFERRED_REQUEST);
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
    }

    @Test
    public void testGetSupportedSpecsPDDeferredResponseService_g0() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g0;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY_DEFERRED_RESPONSE);
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
    }

    @Test
    public void testGetSupportedSpecsPDDeferredRequestService_g1() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g1;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY_DEFERRED_REQUEST);
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
    }

    @Test
    public void testGetSupportedSpecsPDDeferredResponseService_g1() {
        GATEWAY_API_LEVEL api = GATEWAY_API_LEVEL.LEVEL_g1;
        ArrayList<UDDI_SPEC_VERSION> list = UddiSpecVersionRegistry.getInstance().getSupportedSpecs(api, NHIN_SERVICE_NAMES.PATIENT_DISCOVERY_DEFERRED_RESPONSE);
        assertTrue(list.contains(UDDI_SPEC_VERSION.SPEC_2_0));
        assertTrue(!list.contains(UDDI_SPEC_VERSION.SPEC_1_0));
    }
}
