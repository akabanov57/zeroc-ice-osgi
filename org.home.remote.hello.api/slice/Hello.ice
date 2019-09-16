// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

#ifndef HELLO_ICE
#define HELLO_ICE

[["java:package:org.home.remote"]]
module hello {

    module api {

		exception NotValidArgument {
	    	string message;
		};

		interface IRemoteHello {
	    	string sayHello(string name) throws NotValidArgument;
		};

    };
};

#endif
