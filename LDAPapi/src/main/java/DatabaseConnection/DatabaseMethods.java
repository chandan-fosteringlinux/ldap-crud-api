package DatabaseConnection;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.ldap.client.api.LdapConnection;

import Data.trancategory;
import Service.ServiceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.apache.directory.api.ldap.model.entry.Entry;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;



@Slf4j
@ApplicationScoped
public class DatabaseMethods {

    @ConfigProperty(name="ldap.rdn")
    String RDN;

    @ConfigProperty(name = "ldap.base.dn")
    private String baseDn;

    @ConfigProperty(name = "ldap.states.objectClasses.all")
    List<String> tranObjectClasses;

    @ConfigProperty(name="ldap.attributes")
    List<String> tranAttributes;

    public Response AddEntryToDatabase(ArrayList<trancategory> tranCategoryList, LdapConnection connection) {
        try {
            for (trancategory category : tranCategoryList) {
                validateCategoryAttributes(category);
    
                String rdn = RDN + "=" + category.getTransactionGroupId();
                String dn = rdn + "," + baseDn;
                Entry entry = new DefaultEntry(dn);
                addObjectClasses(entry);
                addAttributes(entry, category);
                connection.add(entry);
            }
            ServiceResponse serviceResponse = new ServiceResponse("0", "200", "Entries added successfully");
            return Response.ok(serviceResponse).build();
        } catch (Exception e) {
            log.error("Error adding entries to LDAP", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error adding entries to LDAP: " + e.getMessage())
                           .build();
        }
    }
    
    private void validateCategoryAttributes(trancategory category) throws InvalidCategoryAttributeException {
        if (category.getLastActivationDate() == null || category.getCreator() == null || 
            category.getCreatedDate() == null || category.getLastActivationUser() == null ||
            category.getTransactionGroupId() == null || category.getTransactionGroupsUid() == null ||
            category.getDescription() == null || category.getUniqueMember() == null ||
            category.getIsActive() == null) {
            throw new InvalidCategoryAttributeException("Some of the required attributes are missing");
        }
    }
    
    private class InvalidCategoryAttributeException extends Exception {
        public InvalidCategoryAttributeException(String message) {
            super(message);
        }
    }

    private void addObjectClasses(Entry entry) throws LdapException {
        for(int i=0;i<tranObjectClasses.size();i++){
            entry.add("objectClass",tranObjectClasses.get(i));
        }
    }

    private void addAttributes(Entry entry, trancategory category) throws LdapException{
            entry.add(tranAttributes.get(0), category.getLastActivationDate());
            entry.add(tranAttributes.get(1), category.getCreator());
            entry.add(tranAttributes.get(2), category.getCreatedDate());
            entry.add(tranAttributes.get(3), category.getLastActivationUser());
            entry.add(tranAttributes.get(4), category.getTransactionGroupsUid());
            entry.add(tranAttributes.get(5), category.getDescription());
            entry.add(tranAttributes.get(6), category.getUniqueMember());
            entry.add(tranAttributes.get(7), category.getIsActive());
    }

    public Response searchEntryMethod(Map<String, String> searchCriteria, LdapConnection connection) {
        EntryCursor cursor = null;
        List<trancategory> result = new ArrayList<>();
        try {
            StringBuilder filter = new StringBuilder("(&");
            for (Map.Entry<String, String> entry : searchCriteria.entrySet()) {
                filter.append("(").append(entry.getKey()).append("=").append(entry.getValue()).append(")");
            }
            filter.append(")");            
            String dn = baseDn; 
            
            cursor = connection.search(dn, filter.toString(), SearchScope.SUBTREE);
            for (Entry entry : cursor) {
                trancategory category = new trancategory();
                category.setLastActivationDate(entry.get(tranAttributes.get(0)).getString());
                category.setCreator(entry.get(tranAttributes.get(1)).getString());
                category.setCreatedDate(entry.get(tranAttributes.get(2)).getString());
                category.setLastActivationUser(entry.get(tranAttributes.get(3)).getString());
                category.setTransactionGroupId(entry.get("transactionGroupId").getString());
                category.setTransactionGroupsUid(entry.get(tranAttributes.get(4)).getString());
                category.setDescription(entry.get(tranAttributes.get(5)).getString());
                category.setUniqueMember(entry.get(tranAttributes.get(6)).getString());
                category.setIsActive(entry.get(tranAttributes.get(7)).getString());
                result.add(category);
            }
            return Response.ok(result).build();
        } catch (LdapException e) {
            log.error("Error searching entries in LDAP", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to search entries").build();
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (IOException e) {
                    log.error("Error closing LDAP cursor", e);
                }
            }
        }
    }

    public Response deleteEntry(String transactionGroupId, LdapConnection connection) throws CursorException{
        try{
            String dn = "transactionGroupId=" + transactionGroupId + "," + baseDn;
            log.info("Deleting LDAP entry with DN: " + dn);

            // Verify the entry exists before attempting to delete it
            EntryCursor cursor = connection.search(dn, "(objectclass=*)", SearchScope.OBJECT);
            if (!cursor.next()) {
                log.warn("Entry not found for transactionGroupId: " + transactionGroupId);
                return Response.status(Response.Status.NOT_FOUND).entity("Entry not found").build();
            }

            connection.delete(dn);
            log.info("Entry deleted successfully for DN: " + dn);
            return Response.ok("Entry deleted successfully").build();
        } catch (Exception e) {
            log.error("Error deleting entry from LDAP", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete entry").build();
        }
    }

    public Response modifyEntry(String transactionGroupId, Map<String, String> attributes, LdapConnection connection) throws CursorException {
        try{
            String dn = "transactionGroupId=" + transactionGroupId + "," + baseDn;
            log.info("Updating LDAP entry with DN: " + dn);
    
            // Verify the entry exists before attempting to update it
            EntryCursor cursor = connection.search(dn, "(objectclass=*)", SearchScope.OBJECT);
            if (!cursor.next()) {
                log.warn("Entry not found for transactionGroupId: " + transactionGroupId);
                return Response.status(Response.Status.NOT_FOUND).entity("Entry not found").build();
            }
            if (attributes.containsKey("transactionGroupId")) {
                log.warn("Modification of transactionGroupId is not allowed");
                return Response.status(Response.Status.BAD_REQUEST).entity("Modification of transactionGroupId is not allowed").build();
            }
    
            List<Modification> modifications = new ArrayList<>();
            for (Map.Entry<String, String> attribute : attributes.entrySet()) {
                modifications.add(new DefaultModification(ModificationOperation.REPLACE_ATTRIBUTE, attribute.getKey(), attribute.getValue()));
            }
    
            connection.modify(dn, modifications.toArray(new Modification[0]));
            log.info("Entry updated successfully for DN: " + dn);
            return Response.ok("Entry updated successfully").build();
        } catch (Exception e) {
            log.error("Error closing LDAP cursor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update entry").build();
        }
    }
}