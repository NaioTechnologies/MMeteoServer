package com.naio.mmeteoserver;

public class EntryPoint
{
	public static void main(String args[])
	{
		MMeteoController mmeteoController = new MMeteoController();
		
		mmeteoController.doStuff( args[0] );
	}
}
