/**
 * Copyright 2014 Telefonica Investigación y Desarrollo, S.A.U <br>
 * This file is part of FI-WARE project.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 * </p>
 * <p>
 * You may obtain a copy of the License at:<br>
 * <br>
 * http://www.apache.org/licenses/LICENSE-2.0
 * </p>
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * </p>
 * <p>
 * See the License for the specific language governing permissions and limitations under the License.
 * </p>
 * <p>
 * For those usages not covered by the Apache version 2.0 License please contact with opensource@tid.es
 * </p>
 */

package com.telefonica.euro_iaas.paasmanager.rest.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.telefonica.euro_iaas.commons.dao.EntityNotFoundException;


import com.telefonica.euro_iaas.paasmanager.exception.AlreadyExistEntityException;
import com.telefonica.euro_iaas.paasmanager.exception.ApplicationInstanceNotFoundException;
import com.telefonica.euro_iaas.paasmanager.exception.InfrastructureException;
import com.telefonica.euro_iaas.paasmanager.exception.InvalidApplicationReleaseException;
import com.telefonica.euro_iaas.paasmanager.exception.InvalidEntityException;

import com.telefonica.euro_iaas.paasmanager.exception.QuotaExceededException;

import com.telefonica.euro_iaas.paasmanager.manager.ApplicationInstanceManager;
import com.telefonica.euro_iaas.paasmanager.manager.EnvironmentInstanceManager;
import com.telefonica.euro_iaas.paasmanager.manager.EnvironmentManager;
import com.telefonica.euro_iaas.paasmanager.manager.TierManager;
import com.telefonica.euro_iaas.paasmanager.model.ClaudiaData;

import com.telefonica.euro_iaas.paasmanager.model.ApplicationInstance;
import com.telefonica.euro_iaas.paasmanager.model.Environment;
import com.telefonica.euro_iaas.paasmanager.model.EnvironmentInstance;
import com.telefonica.euro_iaas.paasmanager.model.Metadata;
import com.telefonica.euro_iaas.paasmanager.model.ProductRelease;
import com.telefonica.euro_iaas.paasmanager.model.Tier;

import com.telefonica.euro_iaas.paasmanager.model.dto.ApplicationReleaseDto;
import com.telefonica.euro_iaas.paasmanager.model.dto.TierDto;
import com.telefonica.euro_iaas.paasmanager.model.searchcriteria.EnvironmentInstanceSearchCriteria;
import com.telefonica.euro_iaas.paasmanager.util.SystemPropertiesProvider;

public class AppInstanceResourceValidatorImplTest {
	
	ApplicationInstanceResourceValidatorImpl appInstanceResourceValidator ;
	ApplicationInstanceManager applicationInstanceManager;
	EnvironmentInstanceManager environmentInstanceManager;


	
	@Before
	public void setUp () throws EntityNotFoundException, InvalidEntityException {
		appInstanceResourceValidator = new ApplicationInstanceResourceValidatorImpl();
		applicationInstanceManager = mock (ApplicationInstanceManager.class);
		environmentInstanceManager = mock (EnvironmentInstanceManager.class);
		appInstanceResourceValidator.setApplicationInstanceManager(applicationInstanceManager);
		appInstanceResourceValidator.setEnvironmentInstanceManager(environmentInstanceManager);
        
	}

    @Test
    public void testCheckValidateInstall() throws InvalidApplicationReleaseException, ApplicationInstanceNotFoundException, EntityNotFoundException {
        // given
    	ApplicationReleaseDto applicationReleaseDto = new ApplicationReleaseDto ("app", "version", null);
   
    	when (applicationInstanceManager
                .load(any(String.class), any(String.class))).thenThrow(
                		new EntityNotFoundException (ApplicationInstance.class, "", applicationReleaseDto));
    	appInstanceResourceValidator.validateInstall("vdc", "environmentInstance", applicationReleaseDto);
    	verify(applicationInstanceManager)
                .load(any(String.class), any(String.class));

    }
    
    @Test(expected=InvalidApplicationReleaseException.class)
    public void testCheckValidateInstallError() throws InvalidApplicationReleaseException, ApplicationInstanceNotFoundException, EntityNotFoundException {
        // given
    	ApplicationReleaseDto applicationReleaseDto = new ApplicationReleaseDto ("app", "version", null);
   
    	when (applicationInstanceManager
                .load(any(String.class), any(String.class))).thenReturn(new ApplicationInstance());
        appInstanceResourceValidator.validateInstall("vdc", "environmentInstance", applicationReleaseDto);
    	verify(applicationInstanceManager)
                .load(any(String.class), any(String.class));

    }
    
    @Test
    public void testCheckValidateUnInstall() throws InvalidApplicationReleaseException, ApplicationInstanceNotFoundException, EntityNotFoundException, com.telefonica.euro_iaas.commons.dao.InvalidEntityException {
        // given
   
    	when (applicationInstanceManager
                .load(any(String.class), any(String.class))).thenReturn(new ApplicationInstance());
    	when (environmentInstanceManager
                .load(any(String.class), any(String.class))).thenReturn(new EnvironmentInstance());
    	appInstanceResourceValidator.validateUnInstall("vdc", "environmentInstance", "applicationName");
    	verify(applicationInstanceManager)
                .load(any(String.class), any(String.class));
    	verify(environmentInstanceManager)
                .load(any(String.class), any(String.class));

    }

    

}
