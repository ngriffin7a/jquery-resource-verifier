/**
 * Copyright (c) 2000-2019 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.jquery.resource.verifier;

import com.liferay.faces.util.application.ResourceUtil;
import com.liferay.faces.util.application.ResourceVerifier;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class JQueryResourceVerifier implements ResourceVerifier {

	// Private Constants
	private static final Set<String> LIFERAY_PORTAL_INCLUDED_RESOURCE_IDS;

	static {
		Set<String> liferayPortalIncludedResourceIds = new HashSet<>();
		liferayPortalIncludedResourceIds.add(ResourceUtil.getResourceId("primefaces", "jquery/jquery.js"));
		LIFERAY_PORTAL_INCLUDED_RESOURCE_IDS = Collections.unmodifiableSet(liferayPortalIncludedResourceIds);
	}

	private ResourceVerifier wrappedResourceVerifier;

	public JQueryResourceVerifier(ResourceVerifier resourceVerifier) {
		this.wrappedResourceVerifier = resourceVerifier;
	}

	@Override
	public boolean isDependencySatisfied(
		FacesContext facesContext, UIComponent componentResource) {

		ExternalContext externalContext = facesContext.getExternalContext();
		final Product LIFERAY_PORTAL = ProductFactory.getProductInstance(externalContext, Product.Name.LIFERAY_PORTAL);

		if (LIFERAY_PORTAL.isDetected() &&
			LIFERAY_PORTAL_INCLUDED_RESOURCE_IDS.contains(ResourceUtil.getResourceId(componentResource))) {
			return true;
		}

		return wrappedResourceVerifier.isDependencySatisfied(facesContext, componentResource);
	}
}
