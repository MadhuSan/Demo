/*******************************************************************************
 * xFramium
 *
 * Copyright 2016 by Moreland Labs, Ltd. (http://www.morelandlabs.com)
 *
 * Some open source application is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *  
 * Some open source application is distributed in the hope that it will 
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with xFramium.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0+ <http://spdx.org/licenses/GPL-3.0+>
 *******************************************************************************/
package org.xframium.driver;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.xframium.device.DeviceManager;
import org.xframium.device.ng.AbstractSeleniumTest;
import org.xframium.page.PageManager;
import org.xframium.page.keyWord.KeyWordDriver;
import org.xframium.page.keyWord.KeyWordTest;
import org.xframium.spi.PropertyProvider;


public class XMLTestDriver extends AbstractSeleniumTest
{
	@Test( dataProvider = "deviceManager")
	public void testDriver( TestName testName ) throws Throwable
	{
		String deviceOs = getDevice().getOs();
		

		KeyWordTest test = KeyWordDriver.instance().getTest( testName.getTestName().split( "\\." )[ 0 ] );
		if ( test == null )
			throw new IllegalArgumentException( "The Test Name " + testName + " does not exist" );
		
		if ( test.getOs() != null && deviceOs != null )
		{
			if ( !deviceOs.toUpperCase().equals(  test.getOs().toUpperCase() ) )
				throw new SkipException( "This test is not designed to work on a device with [" + deviceOs + "]  It needs [" + test.getOs() + "]" );
		}
		
		if ( DeviceManager.instance().isDryRun() )
		{
			log.info( "This would have executed " +  testName.getTestName() );
			return;
		}
		
		
		if ( test.getDescription() != null && !test.getDescription().isEmpty() && getWebDriver() instanceof PropertyProvider )
		    ( (PropertyProvider) getWebDriver() ).setProperty( "testDescription", test.getDescription() );
		
		boolean returnValue = KeyWordDriver.instance().executeTest( testName.getTestName().split( "\\." )[ 0 ], getWebDriver() );
		
		if ( returnValue )
			return;
		
		if ( PageManager.instance().getThrowable() != null )
			throw PageManager.instance().getThrowable();
		
		
		Assert.assertTrue( returnValue );
	} 

}
