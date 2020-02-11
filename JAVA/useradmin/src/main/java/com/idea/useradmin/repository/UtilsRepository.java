package com.idea.useradmin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;

@Repository
public class UtilsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    public String getVersion() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pkg_kor_api")
                .withFunctionName("getVersion")
                .withReturnValue();
        return jdbcCall.executeFunction(String.class);
    }

    public String getRoles() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pkg_kor_api")
                .withFunctionName("getRoles")
                .withReturnValue();
        return jdbcCall.executeFunction(String.class);
    }

    public String resetPassword(String username) {
        StoredProcedureQuery procedureQuery = entityManager
                .createStoredProcedureQuery("pkg_kor_api.resetPassword")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.OUT)
                .setParameter(1, username);
        return (String) procedureQuery.getOutputParameterValue(2);
    }

    public String getUserList()  {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("pkg_kor_api")
                .withFunctionName("getUserList")
                .withReturnValue();
        StringWriter stringWriter = new StringWriter();
        try {
            Reader read = jdbcCall.executeFunction(Clob.class).getCharacterStream();
            int c = -1;
            while ((c = read.read()) != -1) {
                stringWriter.write(c);
            }
            stringWriter.flush();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }


}
