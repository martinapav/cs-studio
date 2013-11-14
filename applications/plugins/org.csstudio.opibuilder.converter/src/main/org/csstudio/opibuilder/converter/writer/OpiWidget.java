/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.opibuilder.converter.writer;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.csstudio.java.string.StringSplitter;
import org.csstudio.opibuilder.converter.model.EdmAttribute;
import org.csstudio.opibuilder.converter.model.EdmWidget;
import org.w3c.dom.Element;

/**
 * General class for outputting widgets.
 * 
 * @author Matevz, Xihui Chen
 */
public class OpiWidget {

	protected Context widgetContext;

	/**
	 * Creates element: <widget typeId="org.csstudio.opibuilder.widgets.type">
	 * </widget>
	 */
	public OpiWidget(Context parentContext, EdmWidget r) {

		Element element = parentContext.getDocument().createElement("widget");
		parentContext.getElement().appendChild(element);

		// Move context to this object.
		this.widgetContext = new Context(parentContext.getDocument(), element,
				parentContext.getRootDisplay(), parentContext.getX(), parentContext.getY());
		setDefaultPropertyValue();
		new OpiInt(widgetContext, "x", r.getX() - widgetContext.getX());
		new OpiInt(widgetContext, "y", r.getY() - widgetContext.getY());
		new OpiInt(widgetContext, "width", r.getW() + 1);
		new OpiInt(widgetContext, "height", r.getH() + 1);

		if (r.getFgColor().isExistInEDL())
			new OpiColor(widgetContext, "foreground_color", r.getFgColor());
		if (r.getBgColor().isExistInEDL())
			new OpiColor(widgetContext, "background_color", r.getBgColor());
		if (r.getAttribute("fgAlarm").isExistInEDL()){
			if(r.getAlarmPv()!=null){
				createColorAlarmRule(r, convertPVName(r.getAlarmPv()), "foreground_color", "ForeColorAlarm", true);
			}else
				new OpiBoolean(widgetContext, "forecolor_alarm_sensitive", r.isFgAlarm());
		}
		if (r.getAttribute("bgAlarm").isExistInEDL()){
			if(r.getAlarmPv()!=null){
				createColorAlarmRule(r, convertPVName(r.getAlarmPv()), "background_color", "BackColorAlarm", true);
			}else
				new OpiBoolean(widgetContext, "backcolor_alarm_sensitive", r.isBgAlarm());
		}
		if (r.getFont().isExistInEDL())
			new OpiFont(widgetContext, "font", r.getFont());

		EdmAttribute visPvAttr = r.getAttribute("visPv");
		if (visPvAttr != null && visPvAttr.isExistInEDL()) {
			LinkedHashMap<String, Element> expressions = new LinkedHashMap<String, Element>();
			Element valueNode = parentContext.getDocument().createElement("value");
			valueNode.setTextContent(String.valueOf(!r.isVisInvert()));
			expressions.put("pv0>=" + r.getVisMin() + "&& pv0<" + r.getVisMax(), valueNode);
			valueNode = parentContext.getDocument().createElement("value");
			valueNode.setTextContent(String.valueOf(r.isVisInvert()));
			expressions.put("true", valueNode);
			new OpiRule(widgetContext, "visibleRule", "visible", false,
					Arrays.asList(convertPVName(r.getVisPv())), expressions);
		}

	}

	/**
	 * Sets the attribute typeId of the OPI widget with
	 * 'org.csstudio.opibuilder.widgets.' prefix.
	 * 
	 * @param typeId
	 */
	protected void setTypeId(String typeId) {
		widgetContext.getElement().setAttribute("typeId",
				"org.csstudio.opibuilder.widgets." + typeId);
	}

	protected void setVersion(String version) {
		widgetContext.getElement().setAttribute("version", version);
	}

	protected void setName(String name) {
		new OpiString(widgetContext, "name", name);
	}

	protected void setDefaultPropertyValue() {
		new OpiBoolean(widgetContext, "border_alarm_sensitive", false);
	}
	
	protected String convertPVName(final String pvName){
		if(pvName.startsWith("LOC\\")){
			try {
				String newName = pvName.replace("$(!W)", "$(DID)");
				String[] parts = StringSplitter.splitIgnoreInQuotes(newName, '=', true);
				String r="loc://"+parts[0].substring(5);
				String type="";
				String initValue=parts[1];
				if(parts[1].startsWith("d:")){
					type="<VDouble>";
					initValue=parts[1].substring(2);
				}else if(parts[1].startsWith("i:")){
					type="<VDouble>";
					initValue=parts[1].substring(2);
				}else if(parts[1].startsWith("s:")){
					type="<VString>";
					initValue=parts[1].substring(2);
				}else if(parts[1].startsWith("e:")){ //Enumerated pv cannot be converted.
					return pvName;
				}
				
				r=r+type+"("+initValue+")";
				return r;	
				
			} catch (Exception e) {
				return pvName;
			}
			
		}
		
		return pvName;
	}
	
	/**Create a rule that directly output PV's value to an opi property.
	 * @param edmWidgetClass
	 * @param pvName
	 * @param opiPropId
	 * @param outputExpression the output expression such as pv0, pvStr0
	 * @param ruleName
	 */
	protected void createPVOutputRule(EdmWidget edmWidgetClass, String pvName, String opiPropId, String outputExpression,
			String ruleName){
		LinkedHashMap<String, Element> expressions = new LinkedHashMap<String, Element>();
		Element valueNode = widgetContext.getDocument().createElement("value");
		valueNode.setTextContent(outputExpression);
		expressions.put("true", valueNode);
		new OpiRule(widgetContext, ruleName, opiPropId, true, Arrays.asList(pvName), expressions);		
	}
	
	/**
	 * Create a rule that make a color property alarm sensitive.
	 * 
	 * @param edmWidgetClass
	 * @param edmAlarmAttr
	 * @param edmAlarmPVAttr
	 * @param opiProperty
	 * @param ruleName
	 */
	protected void createColorAlarmRule(EdmWidget edmWidgetClass, String alarmPVName,
			String opiProperty, String ruleName, boolean greenOnOK) {
		LinkedHashMap<String, Element> expressions = new LinkedHashMap<String, Element>();
		Element valueNode; 
		Element colorNode;
		if (greenOnOK) {
			valueNode = widgetContext.getDocument().createElement("value");
			colorNode = widgetContext.getDocument().createElement("color");
			colorNode.setAttribute("name", "OK");
			colorNode.setAttribute("red", "0");
			colorNode.setAttribute("green", "255");
			colorNode.setAttribute("blue", "0");
			valueNode.appendChild(colorNode);
			expressions.put("pvSev0==0", valueNode);
		}
		valueNode = widgetContext.getDocument().createElement("value");
		colorNode = widgetContext.getDocument().createElement("color");
		colorNode.setAttribute("name", "Invalid");
		colorNode.setAttribute("red", "255");
		colorNode.setAttribute("green", "0");
		colorNode.setAttribute("blue", "255");
		valueNode.appendChild(colorNode);
		expressions.put("pvSev0==-1", valueNode);

		valueNode = widgetContext.getDocument().createElement("value");
		colorNode = widgetContext.getDocument().createElement("color");
		colorNode.setAttribute("name", "Major");
		colorNode.setAttribute("red", "255");
		colorNode.setAttribute("green", "0");
		colorNode.setAttribute("blue", "0");
		valueNode.appendChild(colorNode);
		expressions.put("pvSev0==1", valueNode);

		valueNode = widgetContext.getDocument().createElement("value");
		colorNode = widgetContext.getDocument().createElement("color");
		colorNode.setAttribute("name", "Minor");
		colorNode.setAttribute("red", "255");
		colorNode.setAttribute("green", "0");
		colorNode.setAttribute("blue", "0");
		valueNode.appendChild(colorNode);
		expressions.put("pvSev0==2", valueNode);

		new OpiRule(widgetContext, ruleName, opiProperty, false, Arrays.asList(alarmPVName),
				expressions);
	}

}
