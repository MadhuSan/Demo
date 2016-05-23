/*******************************************************************************
 * xFramium
 *
 * Copyright 2016 by Moreland Labs LTD (http://www.morelandlabs.com)
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
package com.xframium.test;
    
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.xframium.content.*;
import org.xframium.device.*;
import org.xframium.device.cloud.*;
import org.xframium.device.data.*;
import org.xframium.page.*;
import org.xframium.page.data.*;
import org.xframium.page.element.*;
import org.xframium.spi.Device;


public class SQLDataProvidersTest
{
    @Test
    public void testSQLCloudProvider()
    {
        CloudDescriptor cloud = CloudRegistry.instance().getCloud( "partners" );

        Assert.assertTrue( cloud != null, "Got a cloud" );
        Assert.assertTrue( "partners.perfectomobile.com".equals(cloud.getHostName()), "Got the right cloud" );
    }

    @Test
    public void testSQLDeviceProvider()
    {
        Device device = DeviceManager.instance().getDevice( "Samsung S6" );

        Assert.assertTrue( device != null, "Got a device" );
        Assert.assertTrue( "Android".equals( device.getOs()), "Got the right device" );
    }

    @Test
    public void testSQLContentProvider()
    {
        ContentData content = ContentManager.instance().getContent( "test" );

        Assert.assertTrue( content != null, "Got some content" );
        Assert.assertTrue( "two".equals( content.getValue( 1 )) , "Got the right content" );
    }

    @Test
    public void testSQLPageDataProvider()
    {
        PageData[] data = PageDataManager.instance().getRecords( "searchData1" );

        Assert.assertTrue( data != null, "Got some data" );
        Assert.assertTrue( data.length > 0, "Got some data II" );
        Assert.assertTrue( "perfecto".equals( data[0].getData( "text" )) , "Got the right data" );
    }

    @Test
    public void testSQLPageElementProvider()
    {
        ElementDescriptor elementDescriptor = new ElementDescriptor( "Google",
                                                                     "Home",
                                                                     "SEARCH_FOR" );
        
        Element element = PageManager.instance().getElementProvider().getElement( elementDescriptor );

        Assert.assertTrue( element != null, "Got an element" );
        Assert.assertTrue( "//input[@id='lst-ib']".equals( element.getKey()) , "Got the right element" );
    }

}
