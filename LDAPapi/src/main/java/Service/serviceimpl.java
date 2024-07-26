package Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;

import Data.trancategory;
import DatabaseConnection.Connection;
import DatabaseConnection.DatabaseMethods;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class serviceimpl implements service {
    
    @Inject
    DatabaseMethods databaseMethods;

    @Inject
    Connection connectionClass;

    LdapConnection connection;

    @PostConstruct
    void init() {
        connection = connectionClass.ldapconnection();
    }

    public void setLdapConnection(LdapConnection connection) {
        this.connection = connection;
    }
    
    @Override
    public Response addEntry(ArrayList<trancategory> tranCategoryList) {
        Response response = databaseMethods.AddEntryToDatabase(tranCategoryList,connection);
        return response;
    }

    @Override
    public Response searchEntry(Map<String, String> searchCriteria) {
        Response response = databaseMethods.searchEntryMethod(searchCriteria, connection);
        return response;
    }

    @Override
    public Response deleteEntry(String transactionGroupId) {
        Response response=Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to search entries").build();;
        try {
            response = databaseMethods.deleteEntry(transactionGroupId,connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateEntryAttributes(String transactionGroupId, Map<String, String> attributes) {
        Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update entry").build();
        try {
            response = databaseMethods.modifyEntry(transactionGroupId, attributes,connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}