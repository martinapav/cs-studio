/*
 * Copyright (c) 2010 Stiftung Deutsches Elektronen-Synchrotron,
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS.
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE.
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS,
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION,
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */
package org.csstudio.domain.desy.alarm.epics;


import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link EpicsAlarm}.
 *
 * @author bknerr
 * @since 18.11.2010
 */
public class EpicsAlarmUnitTest {

    @Test
    public void testOrderBySeverity() {

        Assert.assertEquals(EpicsAlarm.getLowest(), EpicsAlarm.UNKNOWN);
        Assert.assertEquals(EpicsAlarm.UNKNOWN.compareAlarmTo(EpicsAlarm.OK), -1);
        Assert.assertEquals(EpicsAlarm.OK.compareAlarmTo(EpicsAlarm.MINOR), -1);
        Assert.assertEquals(EpicsAlarm.MINOR.compareAlarmTo(EpicsAlarm.MAJOR), -1);
        Assert.assertEquals(EpicsAlarm.MAJOR.compareAlarmTo(EpicsAlarm.INVALID), -1);
    }

    @Test
    public void testIsAlarm() {

       Assert.assertFalse(EpicsAlarm.UNKNOWN.isAlarm());
       Assert.assertFalse(EpicsAlarm.OK.isAlarm());
       Assert.assertTrue(EpicsAlarm.MINOR.isAlarm());
       Assert.assertTrue(EpicsAlarm.MAJOR.isAlarm());
       Assert.assertTrue(EpicsAlarm.INVALID.isAlarm());
    }
}
