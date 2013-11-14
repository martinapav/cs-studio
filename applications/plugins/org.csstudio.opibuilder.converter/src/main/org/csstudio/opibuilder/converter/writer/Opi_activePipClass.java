/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.converter.writer;

import org.apache.log4j.Logger;
import org.csstudio.opibuilder.converter.model.Edm_activePipClass;

/**
 * XML conversion class for Edm_activePipClass
 * @author Lei Hu, Xihui Chen
 */
public class Opi_activePipClass extends OpiWidget {

	private static Logger log = Logger.getLogger("org.csstudio.opibuilder.converter.writer.Opi_activePipClass");
	private static final String typeId = "linkingContainer";
	private static final String name = "EDM linkingContainer";
	private static final String version = "1.0";

	/**
	 * Converts the Edm_activeRectangleClass to OPI Rectangle widget XML.  
	 */
	public Opi_activePipClass(Context con, Edm_activePipClass r) {
		super(con, r);
		setTypeId(typeId);
		setVersion(version);
		setName(name);
		

		if(r.getDisplaySource()!=null && r.getFile()!=null)
		{
			String originPath = r.getFile();
			if (originPath.endsWith(".edl")) {
				originPath = originPath.replace(".edl", ".opi");
			} else
				originPath = originPath + ".opi";
			new OpiString(widgetContext, "opi_file", originPath);				
		}
		
		if(r.getDisplaySource()==null && r.getFilePv()!=null){
			createPVOutputRule(r, convertPVName(r.getFilePv()), "opi_file", "pvStr0", "OPIFileFromPVRule");
		}
		
		log.debug("Edm_activePipClass written.");

	}

}

