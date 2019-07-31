Apache River and JavaSpace

To make JavaSpace programmes work in an IDE like IntelliJ or Eclipse you need to tell the IDE where to find the necessary libraries.

The minimum set of libraries that must be added to be able to compile and run JavaSpace programmes is as follows:

/local/apache-river-3.0.0/lib/jsk-lib.jar

/local/apache-river-3.0.0/lib/jsk-platform.jar

/local/apache-river-3.0.0/lib/outrigger.jar

/local/apache-river-3.0.0/lib/reggie.jar

/local/apache-river-3.0.0/lib-dl/reggie-dl.jar

To do this:

In IntelliJ – create a project to contain your work, then go to File ? Project Structure... On the left hand side of the window that appears, select Modules (NOT “Libraries”), then click the green plus sign (“+”) on the right hand side of the window, and select Jars or Directories... In the pop-up file chooser window that appears you need to find and add each of the libraries listed above.

In eclipse - while creating a new Eclipse project, on the "Java Settings" page click on the "Libraries" tab, then click the "Add External JARS..." button and add the above libraries. Alternatively, if you have already created the project then right click the project name in the Package Explorer pane and select "Properties" then the "Java Build Path" tab, then add the libraries as described above.

Instructions for installing and running JavaSpaces at home



The JavaSpace set-up we are using used to be distributed as part of Sun Microsystem's JINI architecture. Since Oracle bought out Sun the JINI project has been redeveloped and re-branded as Apache River, under the control of the Apache Software Foundation. Full details can be found at: http://river.apache.org/

To download and install the software needed to run JavaSpaces there are two options:

Download from http://river.apache.org/ and follow the instructions on that web site. This will allow you to download different versions of the software and to obtain a “clean” download, but it will also require you to do some installation, set up, and management of the software yourself. We are running version 3.0.0, and this is not available pre-compiled, so you will have to build it yourself and then deploy the various services. This is not really recommended unless you like a challenge!

Download this file, which is a modified distribution, created by me, which contains release 3.0.0 of the Apache River software along with some additional scripts to make life easier when installing and running the software.

If you chose option 1 above then you need to work it out for yourselves.

If you chose option 2 above then this is what you need to do (NOTE – these instructions assume you are running the software on Linux – I recommend Ubuntu. There is some further information for Windows users below):

Download the file from the link above

Unpack it somewhere. This could be in your home directory, or you could put it into a system directory (you will need to use sudo to do this).

To run the software/start the JavaSpace:

open a terminal

cd to 'wherever-you-installed-the-software'/start-scripts. For example, this might be:

cd apache-river-3.0.0/start-scripts

run the script “start-all-services.sh'. e.g.:

./start-all-services

That's it. You should now have an executing JavaSpace.

To stop the software again, the easiest way is to type:

killall java

which will kill all executing Java processes.