package com.example.Extentions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;


import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;


public class DummyLdapServer implements BeforeAllCallback, AfterAllCallback {

    private static final Logger LOG = LoggerFactory.getLogger(DummyLdapServer.class);
    
    String ldapHost = "127.0.0.1";
    int ldapPort=12000;
    String bindDN="cn=Directory Manager";
    String superAdminPasswd = "redhat";
    String baseDN="dc=transactionCategory,dc=com";
    String ldifFile="src/test/java/com/example/config/crudtest.ldif";


    public static InMemoryDirectoryServer testLdapServer;
    private static String listenerName="test-listener";

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
            InetAddress bindAddress = InetAddress.getByName(ldapHost);
            InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig(listenerName, 
                         bindAddress, ldapPort, null, null, null);
            InMemoryDirectoryServerConfig config= initServerConfig(listenerConfig);
            LDIFReader reader = populateLDAPServer(ldifFile);
            startInMemoryLDAPServer(config, reader );
            Thread.sleep(2000);
    }
    @Override
        public void afterAll(ExtensionContext context) throws Exception {
                stopInMemoryLDAPServer();
        }

    public InMemoryDirectoryServerConfig initServerConfig(InMemoryListenerConfig lsconfig) throws LDAPException, FileNotFoundException{
            InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig(this.baseDN);
            serverConfig.addAdditionalBindCredentials(bindDN, superAdminPasswd);
            serverConfig.setSchema(null);
            serverConfig.setListenerConfigs(lsconfig);
            return serverConfig;
    }

    public LDIFReader populateLDAPServer(String filePath) throws FileNotFoundException {
            File initialFile = new File(filePath);
            InputStream inputStream = new FileInputStream(initialFile);
            return new LDIFReader(inputStream);
    }
    

    public static void startInMemoryLDAPServer(InMemoryDirectoryServerConfig serverConfig, LDIFReader ldifReader) throws LDAPException, FileNotFoundException {        
            testLdapServer = new InMemoryDirectoryServer(serverConfig);
            testLdapServer.importFromLDIF(true,ldifReader);
            LOG.info("LDAP server starting...");
            testLdapServer.startListening();
            LOG.info("LDAP server started. Listen on port " + testLdapServer.getListenPort());
    }

    

    public static void stopInMemoryLDAPServer() {
        
        if(testLdapServer!=null){
        testLdapServer.shutDown(listenerName, true);
        }
    }

    public static void restartInMemoryLDAPServer() throws LDAPException {
        testLdapServer.restartListener(listenerName);
    }
}