package HW;

//junit imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

//mockito imports
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

public class IntegrationTests  {
  ppa1Function test = new ppa1Function();
  ResultSet rs;
  Statement statement;
  Connection connection;

  @Before
  public void setUp() throws SQLException{
    rs = mock(ResultSet.class);
    statement = mock(Statement.class);
    connection = mock(Connection.class);
    //when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
    //when(resultSet.getString(1)).thenReturn("table_r3").thenReturn("table_r1").thenReturn("table_r2");
    when(statement.executeQuery("SELECT name FROM tables")).thenReturn(rs);
    when(connection.createStatement()).thenReturn(statement);
  }

  @Test
  public void testtest() throws SQLException{
    double[] ret = {5.75, 0.0};
    assertArrayEquals(ret, test.splitTheTipDB(10.0, 2, connection));
  }

}
