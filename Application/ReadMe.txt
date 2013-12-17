Acknowledgement
---------------

We would like to express my deepest appreciation to all those who provided me the possibility to complete our project. A special gratitude  we give to Aakash team( the organizer of Aakash Android Application Programming Workshop for Students (AAAPW-2013) )and also to ZXing team for providing us with barcode scanner libraries.

Project title :- MyELib
-----------------------
-----------------------

Team members:
-------------
Mulla Hawa K.(hawa4frnds@yahoo.com)
Kapopara Eeva N.(Kapoparaeeva.uis@gmail.com)


The project is all about the library functionality.
The steps to install and run our application are as follows.

-------
Step 1: 
-------
You must have following applications running on your machine

	1) Android version: Android SDK higher than 2.3.3– API level 10
	2) Target Android version is Android SDK higher than 4.2– API level 17
	3) RAM-min. 512 MB(machine) , 20 MB of RAM(Android)20 MB of SD card
	4) ASP .Net (Framework-3.5 or higher), Sql Server 2005 or higher.
	5) IIS Server(Operating System -Windows 7)

--------
Step 2:
--------
>> Restore MyELib.bak (Backup file of the database in SQL Server)
>> Import the Project in eclipse environment.
>> Import MyELibwebservice in ASP .Net
>> Also put the MyELibwebservice on IIS Server for that follow following steps:

1. (start->run->inetmgr->Connection pannel->sites->default website->rightclick->add application-> provide alias as(MyELibwebservice)->set physical path by browsing (SourceCode\MyELibwebservice)->press ok)
       
2. Browse 
	(MyELibwebservice->right click->manage application->Browse)

3. After this if any error follow the two steps below:-         
	[a] In inetmgr (MyELibwebservice->right click->manage application-> Advanced settings->Application pool->Asp.Netv(4.0 or your version of framework)->press ok)
	
	[B] In inetmgr (select MyELibwebservice->MyELibwebserice Home(middle pannel)->Directory browsing->Action pannel(Right side)->check if there is enable 			then click on it(otherwise dont go further) ->click on enable)
	  
4.then browse
	(MyELibwebservice->right click->manage application->Browse)

--------             
Step 3 : 
--------
In code just follow the following changes:
1. Go to the folder hierarchy SourceCode\MyELibwebservice

	In that open web.config file and make changes in connectionString in following two lines.
		
	-->>To change connectionString  ASP.Net/server explorer/MyELib.dbo/rightclick on that/properties/advanced setting/select the connection string. 
		<add 	name="MyELibConnectionString" 
			connectionString="DataSource=HAWAMULLA;Initial Catalog=MyELib;Persist Security Info=True;User ID=sa;" 
			providerName="System.Data.SqlClient" 
		/>
		
	-->>In the next line change connection string same as above but only the following portion of that string
		"DataSource=HAWAMULLA;Initial Catalog=MyELib;Persist Security Info=True;UserID=sa;"	
			
	-->>Also Change the password to your SQL Server password. 
		Eg. change  Password=hawa4frnds to Password=yourSqlserverPassword
		
		<add 	name="MyELibConnectionString1" 
			connectionString="DataSource=HAWAMULLA;Initial Catalog=MyELib;Persist Security Info=True;UserID=sa;Password=hawa4frnds"
			providerName="System.Data.SqlClient" 
		/>

2. Some changes also need to be done in android code by following steps listed below		
	-->>From Source code folder import MyELib
	-->>Follow the folder hierarchy MyELib\src\com\example\myelib
	-->>In that open the Global_Application.java file and change the IP address(192.168.1.12) with IP address of your machine in the following two lines.
		String Url="http://192.168.1.12/MyELibwebservice/Service.asmx/";
		String EbookUrl="http://192.168.1.12/MyELibwebservice";

--------
Step 4 : 
--------
Now run the application in emmulator and then you will get .apk file that can be run on any android device.


/************************Flow of the Application************************/

-> There wil be two options on the home activity
	1) Login
		just need to provide Stud_Libcard_no(simcmc1190) and stud_enrollmentno(115310693043)

	2) Login By Barcode
		In this activity you need to scan the library card's barcode if there
		(Note :- we have put one library card's scanned copy having barcode)		

-> Then you will get tabs like Home, Search, Ebook and Suggestion,
	- At Home tab
		You will find the detail of student who is logged in and the book list which are issued by him/her.

	- At Search tab
		You will able to search the books by auther name, book id(if you know), book name, publisher name or by any keyword related to the book.	
	
	- At Ebook tab
		You will get the list of ebooks available in the library and just by clicking on it you will be able to download the ebooks.
	
	-At Suggestion tab
		You can send the suggestion by the email.

-> Additional functionality is as follows:
	Any student, having library books issued will get notification of submission of his/her book before two days of return date. 

Note: 
-----
->To check the results of each and every tabs and fields enter the following data where and when required as correct data that we have entered in our database
		Student enrollment number - 115310693044
		Student librarycard number - SIMCMC110053
		Book name - core java 2
		Book auther - gary cornell 
		Book publisher - pearson education
