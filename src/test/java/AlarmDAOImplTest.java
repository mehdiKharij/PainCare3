import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.JAVA.Beans.Alarm;
import com.JAVA.DAO.AlarmDAOImpl;
import com.JAVA.DAO.DAOFactory;

public class AlarmDAOImplTest {

    private DAOFactory mockDAOFactory;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private AlarmDAOImpl alarmDAO;

    @Before
    public void setUp() throws SQLException {
        // Mocking the DAOFactory and DB-related objects
        mockDAOFactory = mock(DAOFactory.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Setting up expectations for the factory method
        when(mockDAOFactory.getConnection()).thenReturn(mockConnection);

        // Initializing the DAOImpl with the mock DAOFactory
        alarmDAO = new AlarmDAOImpl(mockDAOFactory);
    }

    @Test
    public void testInsertAlarm() throws SQLException {
        // Setting up an Alarm object
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setTime(Time.valueOf("12:00:00"));
        alarm.setRepeatDays(new HashSet<>(List.of("Monday", "Wednesday")));

        // Preparing the mock PreparedStatement and result set for generated keys
        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Call the method to insert the alarm
        alarmDAO.insertAlarm(alarm, 1);

        // Verifying that the insert query was executed
        verify(mockPreparedStatement, times(1)).executeUpdate();

        // Verify the generated alarm ID
        assertEquals(1, alarm.getAlarmId());
    }

    @Test(expected = SQLException.class)
    public void testInsertAlarmFailure() throws SQLException {
        // Setup for failure case (no rows affected)
        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows inserted

        // Call the method, expect it to throw an exception
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarmDAO.insertAlarm(alarm, 1);
    }

    @Test
    public void testGetAlarmsByUserId() throws SQLException {
        // Setup the result set to return fake alarms
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Return one alarm, then stop

        // Mock the result set fields
        when(mockResultSet.getInt("alarmId")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Morning Alarm");
        when(mockResultSet.getTime("time")).thenReturn(Time.valueOf("07:30:00"));
        when(mockResultSet.getString("repeatDays")).thenReturn("Monday,Tuesday");

        // Call the method to get alarms by user ID
        List<Alarm> alarms = alarmDAO.getAlarmsByUserId(1);

        // Verify the result
        assertEquals(1, alarms.size());
        Alarm alarm = alarms.get(0);
        assertEquals(1, alarm.getAlarmId());
        assertEquals("Morning Alarm", alarm.getTitle());
        assertEquals(Time.valueOf("07:30:00"), alarm.getTime());
    }

    @Test
    public void testDeleteAlarm() throws SQLException {
        // Setup the prepared statement for delete
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row deleted

        // Call the delete method
        alarmDAO.deleteAlarm(1);

        // Verify that the delete statement was executed
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void testDeleteAlarmFailure() throws SQLException {
        // Setup for failure case (no rows deleted)
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows deleted

        // Call the method, expect it to throw an exception
        alarmDAO.deleteAlarm(1);
    }
}
