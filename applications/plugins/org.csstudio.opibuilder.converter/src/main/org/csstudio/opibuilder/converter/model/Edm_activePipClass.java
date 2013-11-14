/*******************************************************************************
 * Copyright (c) 2013 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.converter.model;

/**
 * Specific class representing Embedded Window widget.
 *
 * @author Lei Hu, Xihui Chen
 *
 */
public class Edm_activePipClass extends EdmWidget {

	@EdmAttributeAn @EdmOptionalAn private String displaySource;
	@EdmAttributeAn @EdmOptionalAn private String file;
	@EdmAttributeAn @EdmOptionalAn private String filePv;
	
	public Edm_activePipClass(EdmEntity genericEntity) throws EdmException {
		super(genericEntity);
	}


	/**
	 * @return the alarmPv
	 */
	public final String getDisplaySource() {
		return displaySource;
	}

	public final String getFile() {
		return file;
	}

	public String getFilePv() {
		return filePv;
	}


}
