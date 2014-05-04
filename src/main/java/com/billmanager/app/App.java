package com.billmanager.app;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.billmanager.app.views.home.LoginScreen;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Starting application" );
//       
        ApplicationContext ctx=new FileSystemXmlApplicationContext("beans.xml");
        LoginScreen loginScreen = (LoginScreen)ctx.getBean("loginScreen");
    	loginScreen.createAndShowLogin();  
    }
    
}
