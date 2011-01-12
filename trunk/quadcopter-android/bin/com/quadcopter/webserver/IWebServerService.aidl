//This interface is used for all communication between
//the service and the activity that started the service

package com.quadcopter.webserver;

interface IWebServerService {
	//returns the the port number 
	//that the server is running on
 	int getPort();

	//Send the byte array to the server. This image is
	//in the jpeg format and can be viewed when a user
	//goes to http://ip.address/webcam.jpg
	void setWebCamByteArray(in byte[] img);
	
	//start the web server thread
	void start();
	
	//stop the web server thread
	void stop();
	
	//is the server running
	boolean isRunning();
}