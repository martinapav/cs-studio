package org.csstudio.swt.chart;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
    private static final String BUNDLE_NAME = "org.csstudio.swt.chart.messages"; //$NON-NLS-1$

    public static String Chart_Default_Scale;
    public static String Chart_DeselectY_TT;
    public static String Chart_HideButtonBar;
    public static String Chart_MoveDown;
    public static String Chart_MoveLeft;
    public static String Chart_MoveRight;
    public static String Chart_MoveUp;
    public static String Chart_PointColon;
    public static String Chart_ShowButtonBar;
    public static String Chart_Stagger;
    public static String Chart_SelectY_TT;
    public static String Chart_Time;
    public static String Chart_TimeIn;
    public static String Chart_TimeOut;
    public static String Chart_x;
    public static String Chart_y;
    public static String Chart_ZoomAuto;
    public static String Chart_ZoomIn;
    public static String Chart_ZoomOut;
    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages()
    { /* prevent instantiation */ }
}
