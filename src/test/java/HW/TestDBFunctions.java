package HW;

//junit imports
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

//mockito imports
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.sql.*;

@ExtendWith(MockitoExtension.class)
public class TestDBFunctions  {

  @Mock Statement s;
  @Mock Connection c;

  @InjectMocks ppa1Function f;

  @BeforeEach
  public void setUp() throws SQLException{
    when(c.createStatement()).thenReturn(s);
    when(s.executeUpdate(any(String.class))).thenReturn(1);
  }

  @Test
  public void splitTheTipConnectionShouldCreateStatementTest() throws SQLException {
    f.splitTheTipDB(10,2,c);
    verify(c).createStatement();
  }

  @Test
  public void splitTheTipNormalExecutionHasCorrectExecuteUpdate() throws SQLException {
    double dinnerAmount = 10.0;
    int guests = 2;
    double[] ret = {5.75,0};
    double[] answer = f.splitTheTipDB(dinnerAmount,guests,c);
    assertArrayEquals(ret, answer,
        "10 + 1.50 tip should split evenly between 2 people with 5.75 each.");
    String shouldHaveHappened = "Insert into splitTheTip (dinnerAmount,guests,costPerGuest,remainder) values("+dinnerAmount+","+guests+","+ret[0]+","+ret[1]+")";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void splitTheTipNegativeDinnerExecutionHasCorrectExecuteUpdate() throws SQLException {
    double dinnerAmount = -4.03;
    int guests = 3;
    double[] ret = {-1.0,-1.0};
    double[] answer = f.splitTheTipDB(dinnerAmount,guests,c);
    assertArrayEquals(ret, answer,
        "Should return -1.0 -1.0 since there was bad input.");
    String shouldHaveHappened = "Insert into splitTheTip (dinnerAmount,guests,costPerGuest,remainder) values("+dinnerAmount+","+guests+","+ret[0]+","+ret[1]+")";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void splitTheTipZeroGuestsExecutionHasCorrectExecuteUpdate() throws SQLException {
    double dinnerAmount = 17.54;
    int guests = 0;
    double[] ret = {-1.0,-1.0};
    double[] answer = f.splitTheTipDB(dinnerAmount,guests,c);
    assertArrayEquals(ret, answer,
        "Should return -1.0 -1.0 since there was bad input.");
    String shouldHaveHappened = "Insert into splitTheTip (dinnerAmount,guests,costPerGuest,remainder) values("+dinnerAmount+","+guests+","+ret[0]+","+ret[1]+")";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void splitTheTipErrorShouldReturn() throws SQLException {
    
  }

}
