package DatabaseConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.ldap.client.api.DefaultLdapConnectionFactory;
import org.apache.directory.ldap.client.api.LdapConnection;
import org.apache.directory.ldap.client.api.LdapConnectionConfig;
import org.apache.directory.ldap.client.api.LdapConnectionPool;
import org.apache.directory.ldap.client.api.ValidatingPoolableLdapConnectionFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.cursor.EntryCursor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.entry.DefaultModification;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Modification;
import org.apache.directory.api.ldap.model.entry.ModificationOperation;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class Connection {

    @ConfigProperty(name = "ldap.host")
    String ldapHost;

    @ConfigProperty(name = "ldap.port")
    int ldapPort;

    @ConfigProperty(name = "ldap.bindDn")
    String bindDn;

    @ConfigProperty(name = "ldap.bindPassword")
    String bindPassword;

    @ConfigProperty(name = "ldap.connectionTimeout")
    long connectionTimeout;

    @ConfigProperty(name = "ldap.pool.maxActive", defaultValue = "8")
    int poolMaxActive;

    @ConfigProperty(name = "ldap.pool.maxIdle", defaultValue = "8")
    int poolMaxIdle;

    @Getter
    private LdapConnectionPool ldapConnectionPool;

    @ConfigProperty(name = "ldap.base.dn")
    private String baseDn;

    @ConfigProperty(name = "ldap.states.objectClasses.all")
    List<String> tranObjectClasses;

    @SuppressWarnings("rawtypes")
    @PostConstruct
    public
    void init() {
        LdapConnectionConfig config = new LdapConnectionConfig();
        config.setLdapHost(ldapHost);
        config.setLdapPort(ldapPort);
        config.setName(bindDn);
        config.setCredentials(bindPassword);

        DefaultLdapConnectionFactory factory = new DefaultLdapConnectionFactory(config);
        factory.setTimeOut(connectionTimeout);

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setLifo(true);
        poolConfig.setMaxTotal(poolMaxActive);
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMinIdle(0);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);
        poolConfig.setTestWhileIdle(false);
        poolConfig.setTimeBetweenEvictionRunsMillis(-1);
        poolConfig.setBlockWhenExhausted(true);

        ldapConnectionPool = new LdapConnectionPool(
                new ValidatingPoolableLdapConnectionFactory(factory), poolConfig);
        log.info("LdapConnectionPool started.");
    }

    public LdapConnection ldapconnection(){
        LdapConnection connection = null;
        try {
            connection = ldapConnectionPool.getConnection();
        } catch (LdapException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }
}