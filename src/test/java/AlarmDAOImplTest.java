import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import com.JAVA.DAO.DAOFactory;
import com.JAVA.DAO.AlarmDAOImpl;

public class AlarmDAOImplTest {

    private DAOFactory mockDAOFactory;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private AlarmDAOImpl alarmDAO;

    @Before
    public void setUp() throws SQLException {
        // Mock the DAOFactory and database-related objects
        mockDAOFactory = mock(DAOFactory.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Simulate the behavior of the getConnection method
        when(mockDAOFactory.getConnection()).thenReturn(mockConnection);

        // Initialize the AlarmDAOImpl with the mocked DAOFactory
        alarmDAO = new AlarmDAOImpl(mockDAOFactory);
    }

    @Test
    public void testInsertAlarm() throws SQLException {
        // Define the alarm object and set up the mock behavior for inserting an alarm
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");

        // Mock the prepared statement behavior
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Execute the DAO method
        alarmDAO.insertAlarm(alarm, 1);

        // Verify that the insert operation was called
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
}
