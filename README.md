# LDAP(Lightweight Directory Access Protocol

**Table of Content**

1. LDAP History

2. directory

3. Why is LDAP Lightweight ?

4. Why is writing LDAP slow ?

5. LDAP definition

6. Uses of LDAP(information resource)

7. What is Schema, objectclass and attributes? (Information resource)

8. Understand attributes with example

9. [Understand objectclass with example]
   
Let's look at the history of LDAP. It started with electronic mail systems. ([information resource](https://en.wikipedia.org/wiki/History\_of\_email))

In 1971, ARPANET enabled the first network email using the user@domain.com, symbol  
\*@ symbol was used to separate the user's name from their host sytem's address. For e.g. email addresses like user@domain.com,  
user represents recipient name and [domain.com](http://domain.com) represents host address.

This format was used by ARPANET and later standardised by [SMTP](https://en.wikipedia.org/wiki/Simple\_Mail\_Transfer\_Protocol).

but SMTP lacked comprehensive standards(protocol and format differences) for interoperability across different email systems, security etc.

In response to these limitations [X.400](https://en.wikipedia.org/wiki/X.400) was developed by the ITU-T to provide a standardised and comprehensive framework for electronic messaging, security protocols, and enhanced interoperability(ability of different systems to work together and exchange informations) with advanced features of [directory services](https://en.wikipedia.org/wiki/Directory\_service). Thus  X.400 emerged as an alternative solution of [SMTP](https://en.wikipedia.org/wiki/Simple\_Mail\_Transfer\_Protocol), catering(serving) to more demanding email communication requirements.

but \*[X.400](https://en.wikipedia.org/wiki/X.400) lacked addressing format(as in x.400 there is no standard for addresses). It creates confusion for people about how to type their address.

The confusion caused by the [X.400](https://en.wikipedia.org/wiki/X.400) addressing format led to the creation of the [X.500](https://en.wikipedia.org/wiki/X.500) standard for directory services. The idea was to create a hierarchical and standardised email address directory with replication(data sharing across multiple locations) and distribution functionality(routing messages to recipients) that allowed multiple organisations to produce a single public directory

but [X.500](https://en.wikipedia.org/wiki/X.500) uses DAP based on the OSI model which is more resource intensive(such as CPU processing power, memory, or network bandwidth) and less flexible(strictly following OSI model).  
[**TCP/IP vs OSI**](https://www.geeksforgeeks.org/open-systems-interconnection-model-osi/)

In response, [LDAP](https://en.wikipedia.org/wiki/Lightweight\_Directory\_Access\_Protocol) came into the picture, utilising the simpler and more efficient(uses less CPU processing power, memory, or network bandwidth) TCP/IP protocols for accessing and interacting with directory services.

[LDAP](https://en.wikipedia.org/wiki/Lightweight\_Directory\_Access\_Protocol)  was developed in 1993 by **Tim Howes and his colleagues at the University of Michigan**. It was created to be a lightweight, low-overhead version of the X. 500 directory services protocols used at the time.

# **LDAP(Lightweight Directory Access Protocol)** 

It is a protocol that help users find data about organisations, persons and more from LDAP \*directory.It is a protocol that help users find data about organisations, persons and more from LDAP \*directory.  
\-It has two main goals  
	1\. To store data in LDAP directory  
	2\. To authenticate users to access the directory  
([Information source](https://www.redhat.com/en/topics/security/what-is-ldap-authentication))  
\-It works on \*TCP/IP protocols to access information from directories.  
Protocols:-  
\-The protocol refers to a set of rules that defines how computers(application server, web server etc.) communicate with the \*directory service.  
\*TCP/IP([information resource](https://www.techtarget.com/searchnetworking/definition/TCP-IP\#:\~:text=TCP%2FIP%20stands%20for%20Transmission,network%20devices%20on%20the%20internet))  
It defines how [computers](https://en.wikipedia.org/wiki/Computer)(application server, web server etc) exchange data over the [**internet**](https://en.wikipedia.org/wiki/Internet) to one another  
It identifies how data should be  
\-broken down(into packets)  
\-addressed (using IP addresses)  
\-transmitted (over the network)  
\-routed (through intermediate devices)  
\-received (and reassembled)  
TCP :- Defines how applications create communication channels and Manage how a message is broken down to be transmitted.  
IP :- Defines how to address and route packets for delivery.  
([information resource](https://www.techtarget.com/searchnetworking/definition/TCP-IP\#:\~:text=TCP%2FIP%20stands%20for%20Transmission,network%20devices%20on%20the%20internet))

[**TCP/IP vs OSI**](https://www.geeksforgeeks.org/open-systems-interconnection-model-osi/)

**directory** 

It is a repository of data, much like a database (but with significant differences) that is used to store huge amounts of data.  
In these kinds of directories data is stored hierarchically(a tree-like structure with root at the top and branches leading to child entries that represent a specific resource(user,group etc.)) manner and optimised for fast search and retrieval.  
It is optimised for fast search and retrieval.  
[(resource)](https://www.opensourceforu.com/2010/02/openldap-part-1-setting-up-directory-server/)

# **Why is LDAP Lightweight ?** 

\* It uses TCP/IP protocols to exchange data over the internet(which is the basic communication language over the internet) on the other hand X.500 uses DAP based on the OSI model.  
\* Its implementations use efficient algorithms(indexing, caching-mechanism etc) for common operations like searching, which reduces the computational load and allows the software to run on less powerful hardware.  
\*It uses simple string-based(easily understandable) query to extract information from Directory, due to simplicity in parese(understand) and processing data which make ldap use less resources on the computer.  
[information resource](https://www.techtarget.com/searchmobilecomputing/definition/LDAP)  
It provides **efficient resource management** strategies by providing [caching mechanism,](https://www.openldap.org/doc/admin23/proxycache.html) [indexing](https://ldapwiki.com/wiki/Wiki.jsp?page=LDAP%20Indexes) etc.)  
[**TCP/IP vs OSI**](https://www.geeksforgeeks.org/open-systems-interconnection-model-osi/)

# Why is writing LDAP slow ?** 

It is because LDAP needs to maintain tree structure(hierarchical structure between entries, such as parent-child links) when writing new or modifying existing entries, the system needs to ensure that these relationships are updated correctly, which can be time-consuming.  
Its lightweight structure and use of a DIT make it possible to quickly run an LDAP search and successfully provide results.

# **LDAP definition** 

Based on the learning we do above, the Lightweight Directory Access Protocol (LDAP) is a \*directory service protocol that runs directly over the TCP/IP stack and follows \*X.500 Directory Access Protocol (DAP) string-encoding scheme on the Internet. The information model of LDAP is similar to that of the X.500 OSI directory service, but with fewer features and lower resource requirements than X.500  
([resource link](https://learn.microsoft.com/en-us/previous-versions/windows/desktop/ldap/what-is-ldap))  
\*[X.500](https://en.wikipedia.org/wiki/X.500) :- It is a series of computer networking standards covering electronic [directory services](https://en.wikipedia.org/wiki/Directory\_service). for managing and accessing directory information over the internet.  
\*[directory service](https://en.wikipedia.org/wiki/Directory\_service\#Implementations) :- In computing directory service maps the name of networking resources(file name, username, printer, server etc) to their respective network address(DN, IP addresses etc)  
It is a shared information infrastructure for locating(finding the physical or network address(DN,IP address etc)), managing(), administering(setting policies,handling authentication etc) and organising everyday items and network resources, which can include volumes, folders, files, printers, users, groups, devices, telephone numbers and other objects.  
It organizes and manages resources such as files, printers, and users in a hierarchical manner. Resources like files, printers etc. are treated as objects(uniquely identified set of information).  
\*physical address:- refer to hardware address bluetooth address, ethernet address, MAC(media access control) etc  
\*network address:-DN,IP-address etc

# **Uses of LDAP([information resource](https://www.techtarget.com/searchmobilecomputing/definition/LDAP))** 

**The most common use of LDAP is:**  
\-to provide a central place for authentication, meaning it stores usernames and passwords.  
\-to add operations into a directory server database, authenticate or "bind"(refers to the process of authentication and establishing a connection between a client and an LDAP server)  sessions, delete LDAP entries, search and compare entries using different commands, modify existing entries, extend entries etc.

**When to use LDAP:**  
	\-a single piece of data needs to be found and accessed regularly  
	\-the organization has a lot of smaller data entries  
	\-the organization wants all smaller pieces of data in one centralized location, and there doesn't need to be an extreme amount of organization between the data.

# **What is Schema, objectclass and attributes? ([Information resource](https://www.opensourceforu.com/2010/05/openldap-part-3-understanding-ldap-schema/))**

\-\>To understand it clearly we need to understand what is spreadsheet and sheet ?  
A spreadsheet is a document that organises data into rows and columns. It contains multiple sheets and is used for data analysis. A sheet is where the data entry and analysis occur. The spreadsheet stores and organises these sheets.

\-Schema is the structure in which data units (records) are organised in a directory. In spreadsheet collection the sheet defines the schema of the spreadsheet.  
\-ObjectClasses can be considered to be similar to each sheet within a spreadsheet file. \-Attributes are analogous to each column within a sheet.   
In other words, multiple attributes are grouped together in an ObjectClass, multiple ObjectClass can be grouped to form a record.

# **Attributes**

**\# Add doctor-related attribute types**  
dn: cn=schema(schema entry)  
changetype: modify (modification operation)  
add: attributetypes (adding new attribute types)  
attributetypes: ( doctorName-oid NAME 'doctorName' DESC 'Full name of the doctor' EQUALITY caseIgnoreMatch SUBSTR caseIgnoreSubstringsMatch SYNTAX 1.3.6.1.4.1.1466.115.121.1.15 SINGLE-VALUE )

**A description of of attribute terms used while declaring an attribute**

**OID (Object Identifier)**: A unique identifier for the attribute type.   
**NAME**: Specifies the name of the attribute type.  
**DESC**: A descriptive text providing information about the attribute type.  
**EQUALITY** caseIgnoreMatch: Specifies the matching rule used for equality comparisons on this attribute.([Matching rules](https://www.ibm.com/docs/en/zvm/7.2?topic=attributes-matching-rules))

| Name | Numeric object identifier | Assertion syntax |
| :---: | :---: | :---: |

| caseIgnoreMatch | 2.5.13.2 | Directory String. Leading and trailing whitespace is ignored. Embedded whitespace is replaced by a single blank. Case is ignored. |
| :---- | :---- | :---- |

**SUBSTR** caseIgnoreSubstringsMatch: Specifies the matching rule used for substring searches on this attribute.([Matching rules](https://www.ibm.com/docs/en/zvm/7.2?topic=attributes-matching-rules))

| Name | Numeric object identifier | Assertion syntax |
| :---: | :---: | :---: |

| caseIgnoreSubstringsMatch | 2.5.13.4 | Directory String. Leading and trailing whitespace is ignored. Embedded whitespace is replaced by a single blank. Case is ignored. |
| :---- | :---- | :---- |

**SYNTAX** 1.3.6.1.4.1.1466.115.121.1.15: Specifies the LDAP syntax for the attribute, defining the data format.([Matching Rules](https://www.ibm.com/docs/en/zvm/7.2?topic=attributes-matching-rules))

| Numeric object identifier | Description | Valid values |
| :---: | :---: | :---: |

| 1.3.6.1.4.1.1466.115.121.1.15 | Directory String | UTF-8 characters |
| :---- | :---- | :---- |

**SINGLE-VALUE**: Indicates that the attribute can have only one value for each entry.(resource)

# **Object Classes** 

dn: cn=schema  
changetype: modify  
add: objectclasses  
objectclasses: ( doctorObject-oid NAME 'doctorObject' DESC 'Object class for doctors' SUP top STRUCTURAL MUST ( doctorName $ doctorAadharcard ) MAY ( doctorEmail $ doctorAge ) ) 

**A description of Object Classes terms used while declaring an Object Class**

**objectclasses: ( ... )** :- This indicates the beginning of an object class definition.  
**doctorObject-oid** :- This is the Object Identifier (OID) for the object class. It uniquely identifies the object class within the LDAP schema.  
**NAME 'doctorObject** :- This specifies the name of the object class. It is a human-readable identifier used within LDAP entries.  
**DESC 'Object class for doctors’** :- This provides a description for object class.  
**SUP top** :- This specifies the superior class from which the object class inherits, top is a common superior class in LDAP schemas.  
**STRUCTURAL:-** Define type of object class.  
**MUST ( doctorName $ doctorAadharcard )** :- This specifies the mandatory attributes that entries of this object class must contain.  
**MAY ( doctorEmail $ doctorAge ) :-** This specifies the optional attributes that entries of this object class may contain.

**⇒ Types of Object Classes**   
**Structural Object Classes** :- Core classes used to create entries.  
	Examples: inetOrgPerson, organizationalUnit  
	Used for: Defining the primary structure and attributes of entries.  
**Auxiliary Object Classes** :- Add additional attributes to existing entries.  
	Examples: posixAccount, shadowAccount  
	Used for: Extending entries with additional attributes without changing their primary structure.  
**Abstract Object Classes** :- Base classes used for inheritance.  
	Examples: top  
	Used for: Providing a set of attributes to be inherited by other object classes.

To add object class and attributes to ldap server  
 ldapmodify \-a \-c \-h localhost \-p 3389 \-D "cn=Directory Manager" \-w chandan  \-f hospital.ldif

1. **What are the types of object classes with examples? ([information resource](https://ldap.com/object-classes/))**

**Structural object classes:**Define the main type of object an entry represents (e.g., user, device). Provide primary structure for entries. Examples: roomObject,person etc.  
Eg. :- In the below example you can see how a structural class could be defined inside the ldap server.  
objectClasses: ( roomObject-oid NAME 'roomObject' DESC 'Object class for rooms  
 ' SUP top STRUCTURAL MUST ( roomName1 $ roomNumber1 $ roomLocation1 ) X-ORIGI  
 N 'user defined' )  
objectClasses: ( 2.5.6.6 NAME 'person' SUP top STRUCTURAL MUST ( sn $ cn ) MAY  
  ( userPassword $ telephoneNumber $ seeAlso $ description ) X-ORIGIN 'RFC 451  
 9' )

**Abstract object classes:**Define required and optional attributes, but can't be used directly to create entries. It must be extended by other object classes. Example: top,humanAbstract etc.   
Eg. :- In the below example you can see how an abstract class could be defined inside the ldap server.  
objectClasses: ( 2.5.6.0 NAME 'top' ABSTRACT MUST objectClass X-ORIGIN 'RFC 45  
 12' )  
objectClasses: ( 1.3.6.1.4.1.9999.2.1 NAME 'humanAbstract' DESC 'Abstract clas  
 s for humans' SUP top ABSTRACT MUST fullName MAY age X-ORIGIN 'user defined'  
 )

**Auxiliary object classes:**Add additional attributes to entries without changing their primary type. Example: subschema, etc.   
Eg. :- In the below example you can see how an abstract class could be defined inside the ldap server.  
objectClasses: ( 2.5.20.1 NAME 'subschema' AUXILIARY MAY ( dITStructureRules $  
  nameForms $ dITContentRules $ objectClasses $ attributeTypes $ matchingRules  
  $ matchingRuleUse ) X-ORIGIN 'RFC 4512' )

**Some Object Class terms:-**   
**MUST**: Specifies required attributes for an object class.  
**MAY**: Specifies optional attributes for an object class.  
**$ (Dollar Sign)**: Used to separate multiple attributes within the MUST and MAY lists.

1. [**LDAP Installation and setup with explanation of some ldap terms.**](https://docs.google.com/document/d/1YbHSWqYFdzpAxHfs64sPmwM9oEM80twk-yrX6qoGZjw/edit?usp=sharing)   
1. [**Hospital-Management in ldap Documentation.**](https://docs.google.com/document/d/1VYlKS6NGUEZ\_Dcg\_lYeBqYaN34bPGt\_nCT\_C2cUEL5c/edit\#heading=h.mgp4pwme9l46)   
1. [**LDAP Operation Documentation.**](https://docs.google.com/document/d/1x-OZN-lBgnKFPchQRun4BgncHo25h82s2BarNZlkRgk/edit)  
1. [**LDAP QNA**](https://docs.google.com/document/d/17wk\_1My2Kcph0luE9\_LcjP7KpKKGnkn0urqqV38FpPA/edit)
