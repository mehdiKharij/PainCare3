import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
        // Mock DAOFactory, Connection, PreparedStatement, and ResultSet
        mockDAOFactory = mock(DAOFactory.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Set up DAOFactory to return mock Connection
        when(mockDAOFactory.getConnection()).thenReturn(mockConnection);

        // Instantiate AlarmDAOImpl with mock DAOFactory
        alarmDAO = new AlarmDAOImpl(mockDAOFactory);
    }

    @Test
    public void testInsertAlarm() throws SQLException {
        // Arrange
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setTime(Time.valueOf("12:00:00"));
        alarm.setRepeatDays(new HashSet<>(List.of("Monday", "Wednesday")));

        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Act
        alarmDAO.insertAlarm(alarm, 1);

        // Assert
        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals(1, alarm.getAlarmId());
    }

    @Test(expected = SQLException.class)
    public void testInsertAlarmFailure() throws SQLException {
        // Arrange
        Alarm alarm = new Alarm();
        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows inserted

        // Act
        alarmDAO.insertAlarm(alarm, 1);
    }

    @Test
    public void testGetAlarmsByUserId() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // One alarm in result

        when(mockResultSet.getInt("alarmId")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Morning Alarm");
        when(mockResultSet.getTime("time")).thenReturn(Time.valueOf("07:30:00"));
        when(mockResultSet.getString("repeatDays")).thenReturn("Monday,Tuesday");

        // Act
        List<Alarm> alarms = alarmDAO.getAlarmsByUserId(1);

        // Assert
        assertEquals(1, alarms.size());
        Alarm alarm = alarms.get(0);
        assertEquals(1, alarm.getAlarmId());
        assertEquals("Morning Alarm", alarm.getTitle());
        assertEquals(Time.valueOf("07:30:00"), alarm.getTime());
    }

    @Test
    public void testDeleteAlarm() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // One row deleted

        // Act
        alarmDAO.deleteAlarm(1);

        // Assert
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void testDeleteAlarmFailure() throws SQLException {
        // Arrange
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows deleted

        // Act
        alarmDAO.deleteAlarm(1);
    }
}
