import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

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
        // Simuler DAOFactory, Connection, PreparedStatement, et ResultSet
        mockDAOFactory = mock(DAOFactory.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Simuler le comportement du DAOFactory pour renvoyer une connexion
        when(mockDAOFactory.getConnection()).thenReturn(mockConnection);

        // Initialiser AlarmDAOImpl avec la DAOFactory simulée
        alarmDAO = new AlarmDAOImpl(mockDAOFactory);
    }

    @Test
    public void testInsertAlarm() throws SQLException {
        // Préparer l'objet Alarm
        Alarm alarm = new Alarm();
        alarm.setTitle("Test Alarm");
        alarm.setTime(Time.valueOf("12:00:00"));
        alarm.setRepeatDays(new HashSet<>(List.of("Monday", "Wednesday")));

        // Simuler l'insertion et l'obtention des clés générées
        when(mockConnection.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Appeler la méthode à tester
        alarmDAO.insertAlarm(alarm, 1);

        // Vérifier que l'insertion a eu lieu
        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertEquals(1, alarm.getAlarmId()); // Vérifier que l'ID généré est bien attribué
    }

    @Test
    public void testGetAlarmsByUserId() throws SQLException {
        // Simuler la préparation de la requête
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Un seul résultat

        // Simuler les champs du résultat
        when(mockResultSet.getInt("alarmId")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Morning Alarm");
        when(mockResultSet.getTime("time")).thenReturn(Time.valueOf("07:30:00"));
        when(mockResultSet.getString("repeatDays")).thenReturn("Monday,Tuesday");

        // Appeler la méthode à tester
        List<Alarm> alarms = alarmDAO.getAlarmsByUserId(1);

        // Vérifier le résultat
        assertEquals(1, alarms.size());
        Alarm alarm = alarms.get(0);
        assertEquals(1, alarm.getAlarmId());
        assertEquals("Morning Alarm", alarm.getTitle());
        assertEquals(Time.valueOf("07:30:00"), alarm.getTime());
    }

    @Test
    public void testDeleteAlarm() throws SQLException {
        // Simuler la préparation de la requête pour supprimer l'alarme
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1); // Suppression réussie

        // Appeler la méthode à tester
        alarmDAO.deleteAlarm(1);

        // Vérifier que la suppression a eu lieu
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test(expected = SQLException.class)
    public void testDeleteAlarmFailure() throws SQLException {
        // Simuler la préparation de la requête pour échouer lors de la suppression
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0); // Aucun rang supprimé

        // Appeler la méthode et s'attendre à une exception
        alarmDAO.deleteAlarm(1);
    }
}
