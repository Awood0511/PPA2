package HW;

//junit imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Before;

//mockito imports
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

public class TestDBFunctions  {

  ppa1Function f = new ppa1Function();

  @Mock Statement s;
  @Mock Connection c;

  @Before
  public void setUp() throws SQLException{
    when(c.createStatement()).thenReturn(s);
    when(s.executeUpdate(any(String.class))).thenReturn(1);
  }

  @Test
  public void ConnectionShouldCreateStatementTest() throws SQLException {
    f.splitTheTipDB(10,2,c);
  }

  @Test
  public void ConnectionShouldCreateStatementTest2() throws SQLException {
    double dinnerAmount = 10.0;
    int guests = 2;
    double[] ret = {5.75,2};
    assertArrayEquals(ret, f.splitTheTipDB(10,2,c));
  }

}
