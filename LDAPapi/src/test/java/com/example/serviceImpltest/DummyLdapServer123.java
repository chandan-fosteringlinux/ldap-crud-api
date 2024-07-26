// // package com.example.serviceImpltest;

// // import static org.junit.jupiter.api.Assertions.assertNotNull;
// // import static org.junit.jupiter.api.Assertions.assertTrue;

// // import javax.inject.Inject;

// // import com.example.Extentions.DummyLdapServer;

// // import DatabaseConnection.Connection;

// // import org.apache.directory.ldap.client.api.LdapConnection;
// // import org.junit.jupiter.api.BeforeAll;
// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.extension.ExtendWith;
// // import org.slf4j.Logger;
// // import org.slf4j.LoggerFactory;

// // @ExtendWith(DummyLdapServer.class)
// // public class ConnectionTest {

// //     private static final Logger LOG = LoggerFactory.getLogger(ConnectionTest.class);

// //     @Inject
// //     Connection connection;

// //     @BeforeAll
// //     static void setUpClass() {
// //         System.setProperty("ldap.host", "127.0.0.1");
// //         System.setProperty("ldap.port", "12000");
// //         System.setProperty("ldap.bindDn", "cn=Directory Manager");
// //         System.setProperty("ldap.bindPassword", "redhat");
// //         System.setProperty("ldap.connectionTimeout", "3000");
// //         System.setProperty("ldap.pool.maxActive", "8");
// //         System.setProperty("ldap.pool.maxIdle", "8");
// //         System.setProperty("ldap.base.dn", "dc=transactionCategory,dc=com");
// //         System.setProperty("ldap.states.objectClasses.all", "objectClass");
// //     }

// //     @Test
// //     void testLdapConnection() {
// //         assertNotNull(connection, "Connection should be injected");
// //         LdapConnection ldapConnection = connection.ldapconnection();
// //         assertNotNull(ldapConnection, "LdapConnection should not be null");
// //         try {
// //             assertTrue(ldapConnection.isConnected(), "LdapConnection should be connected");
// //             LOG.info("Successfully connected to the LDAP server");
// //         } catch (Exception e) {
// //             LOG.error("Failed to connect to the LDAP server", e);
// //         } finally {
// //             try {
// //                 ldapConnection.close();
// //             } catch (Exception e) {
// //                 LOG.error("Failed to close the LDAP connection", e);
// //             }
// //         }
// //     }
// // }





// package com.example.serviceImpltest;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.InputStream;
// import java.net.InetAddress;
// import java.net.UnknownHostException;

// import org.junit.jupiter.api.extension.AfterAllCallback;
// import org.junit.jupiter.api.extension.BeforeAllCallback;
// import org.junit.jupiter.api.extension.ExtensionContext;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import com.unboundid.ldap.listener.InMemoryDirectoryServer;
// import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
// import com.unboundid.ldap.listener.InMemoryListenerConfig;
// import com.unboundid.ldap.sdk.LDAPException;
// import com.unboundid.ldap.sdk.Modification;
// import com.unboundid.ldap.sdk.ModificationType;
// import com.unboundid.ldap.sdk.AddRequest;
// import com.unboundid.ldap.sdk.LDAPConnection;
// import com.unboundid.ldap.sdk.LDIFReader;

// public class DummyLdapServer implements BeforeAllCallback, AfterAllCallback {

//     private static final Logger LOG = LoggerFactory.getLogger(DummyLdapServer.class);
    
//     String ldapHost = "127.0.0.1";
//     int ldapPort = 12000;
//     String bindDN = "cn=Directory Manager";
//     String superAdminPasswd = "redhat";
//     String baseDN = "dc=transactionCategory,dc=com";
//     String errorsBaseDN = "ou=ptapluserrorcode,dc=finoptaplus,dc=com";
//     String ldifFile = "src/test/java/com/example/config/crudtest.ldif";

//     public static InMemoryDirectoryServer testLdapServer;
//     private static String listenerName = "test-listener";

//     @Override
//     public void beforeAll(ExtensionContext context) throws Exception {
//         InetAddress bindAddress = InetAddress.getByName(ldapHost);
//         InMemoryListenerConfig listenerConfig = new InMemoryListenerConfig(listenerName, 
//                 bindAddress, ldapPort, null, null, null);
//         InMemoryDirectoryServerConfig config = initServerConfig(listenerConfig);
//         LDIFReader reader = populateLDAPServer(ldifFile);
//         startInMemoryLDAPServer(config, reader);
//         Thread.sleep(2000);
//     }

//     @Override
//     public void afterAll(ExtensionContext context) throws Exception {
//         stopInMemoryLDAPServer();
//     }

//     public InMemoryDirectoryServerConfig initServerConfig(InMemoryListenerConfig lsconfig) throws LDAPException, FileNotFoundException {
//         InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig(this.baseDN);
//         serverConfig.addAdditionalBindCredentials(bindDN, superAdminPasswd);
//         serverConfig.setSchema(null);
//         serverConfig.setListenerConfigs(lsconfig);
//         return serverConfig;
//     }

//     public LDIFReader populateLDAPServer(String filePath) throws FileNotFoundException {
//         File initialFile = new File(filePath);
//         InputStream inputStream = new FileInputStream(initialFile);
//         return new LDIFReader(inputStream);
//     }

//     public static void startInMemoryLDAPServer(InMemoryDirectoryServerConfig serverConfig, LDIFReader ldifReader) throws LDAPException, FileNotFoundException {
//         testLdapServer = new InMemoryDirectoryServer(serverConfig);
//         testLdapServer.importFromLDIF(true, ldifReader);
//         LOG.info("LDAP server starting...");
//         testLdapServer.startListening();
//         LOG.info("LDAP server started. Listen on port " + testLdapServer.getListenPort());
//     }

//     public static void stopInMemoryLDAPServer() {
//         if (testLdapServer != null) {
//             testLdapServer.shutDown(listenerName, true);
//         }
//     }

//     public static void restartInMemoryLDAPServer() throws LDAPException {
//         testLdapServer.restartListener(listenerName);
//     }

//     public void addLdapEntry(String dn, String[] objectClasses, String[] attributes) throws LDAPException {
//         LDAPConnection connection = null;
//         try {
//             connection = testLdapServer.getConnection();
//             AddRequest addRequest = new AddRequest(dn, objectClasses, attributes);
//             connection.add(addRequest);
//             LOG.info("Successfully added entry: " + dn);
//         } finally {
//             if (connection != null) {
//                 connection.close();
//             }
//         }
//     }
// }
