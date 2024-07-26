package com.example.serviceImpltest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapNetworkConnection;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.Extentions.DummyLdapServer;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;

import Data.trancategory;
import DatabaseConnection.Connection;
import DatabaseConnection.DatabaseMethods;
import Service.ServiceResponse;
import Service.serviceimpl;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.apache.directory.api.ldap.model.entry.Entry; // Add this import statement

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;



@QuarkusTest
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(com.example.Extentions.DummyLdapServer.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class serviceunitTest {

    private LdapConnection connection;

    @Inject
    DatabaseMethods databaseMethods;

    @Inject
    serviceimpl ServiceImpl;

    ArrayList<trancategory> tranCategoryList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        connection = new LdapNetworkConnection("127.0.0.1", 12000);
    }

    @BeforeAll
    public void setUpdata() {
        trancategory tc = new trancategory();
        tc.setLastActivationDate("20240729000000Z");
        tc.setCreator("cn=Directory Manager");
        tc.setCreatedDate("20240719000000Z");
        tc.setLastActivationUser("cn=Directory Manager");
        tc.setTransactionGroupId("TG777777777");
        tc.setTransactionGroupsUid("UID21212");
        tc.setDescription("This is a sample transaction category");
        tc.setUniqueMember("cn=Directory Manager");
        tc.setIsActive("true");
        tranCategoryList.add(tc);
        System.out.println("trancategory==="+tc.getDescription());
        System.out.println("uniqueMember==="+tc.getUniqueMember());
        System.out.println("Description==="+tc.getDescription());

    }

    @Order(1)
    @Test
    public void addEntryToDatabaseTest(){
        Response response = databaseMethods.AddEntryToDatabase(tranCategoryList,connection);
        tranCategoryList.get(0).getUniqueMember();
        System.out.println("--------------tcl---------"+tranCategoryList.get(0).getUniqueMember());
        assertNotNull(response);    
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "The response status should be 200 OK");

        ServiceResponse serviceResponse = (ServiceResponse) response.getEntity();
    
        assertNotNull(serviceResponse);

        assertEquals("0", serviceResponse.getReturnCode());
        assertEquals("200", serviceResponse.getResponseCode());
        assertEquals("Entries added successfully", serviceResponse.getResponseMessage());
    }

    @Order(2)
    @Test
    public void searchEntryTest(){
        Map<String, String> searchCriteria = new HashMap<>();
        searchCriteria.put("transactionGroupId", "TG777777777");
        Response response = databaseMethods.searchEntryMethod(searchCriteria, connection);
        assertNotNull(response);


        ArrayList<trancategory> result = (ArrayList<trancategory>) response.getEntity();
        assertNotNull(result);
        assertEquals(1, result.size());

        trancategory category = result.get(0);
        System.out.println("Description==="+category.getDescription());
        System.out.println("uniqueMember==="+category.getUniqueMember());
        System.out.println("transactionGroupId==="+category.getTransactionGroupsUid());

        assertEquals("20240729000000Z", category.getLastActivationDate());
        assertEquals("cn=Directory Manager", category.getCreator());
        assertEquals("20240719000000Z", category.getCreatedDate());
        assertEquals("cn=Directory Manager", category.getLastActivationUser());
        assertEquals("TG777777777", category.getTransactionGroupId());
        assertEquals("UID21212", category.getTransactionGroupsUid());
        assertEquals("This is a sample transaction category", category.getDescription());
        assertEquals("cn=Directory Manager", category.getUniqueMember());
        assertEquals("true", category.getIsActive());
    }

    @Order(3)
    @Test
    public void updateEntityTest(){
        try {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("lastActivationDate", "20240927191300Z");
            String transactionGroupId = "TG777777777";
            Response response = databaseMethods.modifyEntry(transactionGroupId, attributes,connection);
            assertNotNull(response);
            assertEquals("Entry updated successfully", response.getEntity().toString());
            assertEquals(200,response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Order(4)
    @Test
    public void deleteEntry(){
        String transactionGroupId = "TG777777777";        
        try {
        Response response = databaseMethods.deleteEntry(transactionGroupId,connection);
        assertNotNull(response);    
        assertEquals(response.getStatus(),200);
        assertEquals(response.getEntity().toString(), "Entry deleted successfully");
        System.out.println("responsestatus"+response.getStatus());
        System.out.println("statusCode "+Response.Status.OK.getStatusCode());
        System.out.println("responseentity"+response.getEntity().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    










































































    // @Order(3)
    // @Test
    // public void updateEntityTest(){
    //     Map<String, String> attributes = new HashMap<>();
    //     attributes.put("transactionGroupId", "TG999999");
    //     String transactionGroupId = "TG777777777";

    //     try {
    //         // Call the service method
    //         Response response = databaseMethods.modifyEntry(transactionGroupId, attributes, connection);

    //         // Assertions to validate the response
    //         assertNotNull(response);
    //         System.out.println("-------------------------------------"+response.getEntity());
    //         // assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    //         // assertEquals("Entry updated successfully", response.getEntity().toString());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         fail("Test failed due to exception: " + e.getMessage());
    //     }
    // }

    























    // @InjectMock
    // serviceimpl serviceImpl;

    // @Test
    // public void testAddEntry() {
    //     // Create a sample trancategory object
    //     trancategory sampleCategory = new trancategory(
    //         "2021-07-19T12:34:56Z", // lastActivationDate
    //         "creatorName", // creator
    //         "2021-07-19T12:00:00Z", // createdDate
    //         "lastActivationUser", // lastActivationUser
    //         "transactionGroupId123", // transactionGroupId
    //         "transactionGroupsUid123", // transactionGroupsUid
    //         "Sample description", // description
    //         "uniqueMember123", // uniqueMember
    //         "true" // isActive
    //     );

    //     ArrayList<trancategory> tranCategoryList = new ArrayList<>();
    //     tranCategoryList.add(sampleCategory);

    //     given()
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .body(tranCategoryList)
    //     .when()
    //         .post("/entry/add")
    //     .then()
    //         .statusCode(HttpStatus.SC_OK)
    //         .body("code", equalTo("0"))
    //         .body("message", equalTo("Entries added successfully"));
    // }

    
    // @Inject
    // Connection connection;

    // DummyLdapServer dummyServer;
    // LDAPConnection dummyConnection = null;

    // @BeforeAll
    // void setup(){
    //     dummyServer = new DummyLdapServer();
    //     try {
    //         dummyConnection = dummyServer.testLdapServer.getConnection();
    //     } catch (LDAPException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }
    
    // @Test
    // public void addStates(ArrayList<trancategory> tranCategoryList) {
    //     try{
    //         for (trancategory category : tranCategoryList) {
    //             String rdn = "transactionGroupId=" + category.getTransactionGroupId();
    //             String dn = rdn + "," + baseDn;

    //             Entry entry = new DefaultEntry(dn);
    //             // entry.add("objectClass", category.getObjectClass() != null ? category.getObjectClass() : objectClass);
    //             addObjectClasses(entry);
    //             entry.add("lastActivationDate", category.getLastActivationDate());
    //             entry.add("creator", category.getCreator());
    //             entry.add("createdDate", category.getCreatedDate());
    //             entry.add("lastActivationUser", category.getLastActivationUser());
    //             entry.add("transactionGroupsUid", category.getTransactionGroupsUid());
    //             entry.add("description", category.getDescription());
    //             entry.add("uniqueMember1", category.getUniqueMember());
    //             entry.add("isActive", category.getIsActive());
    //             connection.add(entry);
    //         }
    //         ServiceResponse serviceResponse = new ServiceResponse("0", "200", "Entries added successfully");
    //         return Response.ok(serviceResponse).build();
    //     } catch (Exception e) {
    //         log.error("Error adding entries to LDAP", e);
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error adding entries to LDAP: " + e.getMessage()).build();
    //     }
    // }



}
