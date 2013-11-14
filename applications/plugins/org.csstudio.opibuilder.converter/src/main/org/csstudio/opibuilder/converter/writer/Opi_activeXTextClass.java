/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.converter.writer;

import org.apache.log4j.Logger;
import org.csstudio.opibuilder.converter.model.Edm_activeXTextClass;

/**
 * XML conversion class for Edm_activeXTextClass.
 * @author Matevz
 */
public class Opi_activeXTextClass extends OpiWidget {
	
	private static Logger log = Logger.getLogger("org.csstudio.opibuilder.converter.writer.Opi_activeXTextClass");
	private static final String typeId = "Label";	
	private static final String name = "EDM Label";
	private static final String version = "1.0";
	
	/**
	 * Converts the Edm_activeXTextClass to OPI Label widget XML.  
	 */
	public Opi_activeXTextClass(Context con, Edm_activeXTextClass t) {
		super(con,t);
		if(t.getAttribute("alarmPv").isExistInEDL())
			setTypeId("TextUpdate");
		else 
			setTypeId(typeId);
		
		setName(name);
		setVersion(version);;
		
		new OpiString(widgetContext, "text", t.getValue().get());
		
		boolean autoSize = t.getAttribute("autoSize").isExistInEDL() && t.isAutoSize();
		new OpiBoolean(widgetContext, "auto_size", autoSize);
		
		// There is no border (border style == 0) when border attribute is not set. 
		int borderStyle = 0;
		if (t.getAttribute("border").isExistInEDL() && t.isBorder()) {
			// From EDM C code it looks like activeXText always uses solid style. 
			borderStyle = 1;
		}
		new OpiInt(widgetContext, "border_style", borderStyle);
		
		if (t.getAttribute("lineWidth").isExistInEDL()) {
			new OpiInt(widgetContext, "border_width", t.getLineWidth());
		}
		
		new OpiColor(widgetContext, "border_color", t.getFgColor());
				
		boolean useDisplayBg = t.getAttribute("useDisplayBg").isExistInEDL() && t.isUseDisplayBg();  
		new OpiBoolean(widgetContext, "transparent", useDisplayBg);
		
		if(t.getAttribute("alarmPv").isExistInEDL()){
			new OpiString(widgetContext, "pv_name", convertPVName(t.getAlarmPv()));
			new OpiBoolean(widgetContext, "backcolor_alarm_sensitive", t.isBgAlarm());
			new OpiBoolean(widgetContext, "forecolor_alarm_sensitive", t.isFgAlarm());
		}
		
		
		log.debug("Edm_activeXTextClass written.");
	}

}
