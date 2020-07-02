package com.dac.chatting.entities;

import com.github.jasync.sql.db.RowData;
import com.github.jasync.sql.db.general.ArrayRowData;
import org.joda.time.LocalDateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class TokenEntityTest {

    @BeforeMethod
    public void setUp() {
    }

    @Test
    public void shouldCreateTokenEntityFromRow() {
        // Arrange
        final Map<String, Integer> map = new HashMap<>();
        map.put("id", 0);
        map.put("id_account", 1);
        map.put("token", 2);
        map.put("expiration_time", 3);
        map.put("creation_date", 4);
        map.put("last_refresh_date", 5);
        final Object[] objects = new Object[]{
            1,
            23,
            "some.token",
            10L,
            LocalDateTime.now(),
            LocalDateTime.now()
        };

        // Act
        final RowData rowData = new ArrayRowData(0, map, objects);
        final TokenEntity token = TokenEntity.fromRow(rowData);

        // Assert
        assertEquals(token.id().intValue(), 1);
        assertEquals(token.expirationTime().longValue(), 10L);
        assertEquals(token.accountId().intValue(), 23);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNullPointerException() {
        // Arrange
        final Map<String, Integer> map = new HashMap<>();
        map.put("id", 0);
        map.put("id_account", 1);
        map.put("token", 2);
        map.put("expiration_time", 3);
        map.put("creation_date", 4);
        map.put("last_refresh_date", 5);
        final Object[] objects = new Object[]{
            1,
            23,
            "some.token",
            10L,
            LocalDateTime.now(),
            null
        };

        // Act
        final RowData rowData = new ArrayRowData(0, map, objects);
        TokenEntity.fromRow(rowData);
    }

}
