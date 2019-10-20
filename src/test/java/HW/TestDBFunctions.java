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
    lenient().when(c.createStatement()).thenReturn(s);
    lenient().when(s.executeUpdate(any(String.class))).thenReturn(1);
  }

  //Split the tip tests

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
  public void splitTheTipErrorShouldThrowSQLException() throws SQLException {
    lenient().when(c.createStatement()).thenThrow(new SQLException());
    assertThrows(SQLException.class,
           () -> f.splitTheTipDB(10,2,c),
           "Expected splitTheTipDB() to throw SQLException");
    verify(c).createStatement();
    verify(s, never()).executeUpdate(any(String.class));
  }

  //BMI Tests
  @Test
  public void bmiConnectionShouldCreateStatementTest() throws SQLException {
    f.bodymassDB(5,5,120,c);
    verify(c).createStatement();
  }

  @Test
  public void bmiNormalExecutionHasCorrectExecuteUpdate() throws SQLException {
    int feet = 5;
    int inches = 11;
    double weight = 140.0;
    double bmi;
    String bodytype;
    String ret = "Normal Weight|20.0";
    String answer = f.bodymassDB(feet,inches,weight,c);
    assertEquals(ret, answer,
        "BMI should be 20.0, normal weight");
    int parseIndex = ret.indexOf('|');
    bmi = Double.parseDouble(answer.substring(parseIndex + 1));
    bodytype = answer.substring(0, parseIndex);
    String shouldHaveHappened = "Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + feet + "," + inches + "," + weight + "," + bmi + ",'" + bodytype + "')";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void bmiNegativeFeetHasCorrectExecuteUpdate() throws SQLException {
    int feet = -6;
    int inches = 11;
    double weight = 140.0;
    double bmi = -1.0;
    String ret = "heightless";
    String answer = f.bodymassDB(feet,inches,weight,c);
    assertEquals(ret, answer,
        "BMI should return heightless");
    String shouldHaveHappened = "Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + feet + "," + inches + "," + weight + "," + bmi + ",'" + ret + "')";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void bmiNegativeInchesHasCorrectExecuteUpdate() throws SQLException {
    int feet = 6;
    int inches = -4;
    double weight = 140.0;
    double bmi = -1.0;
    String ret = "heightless";
    String answer = f.bodymassDB(feet,inches,weight,c);
    assertEquals(ret, answer,
        "BMI should return heightless");
    String shouldHaveHappened = "Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + feet + "," + inches + "," + weight + "," + bmi + ",'" + ret + "')";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void bmiTooShortHasCorrectExecuteUpdate() throws SQLException {
    int feet = 1;
    int inches = 4;
    double weight = 140.0;
    double bmi = -1.0;
    String ret = "heightless";
    String answer = f.bodymassDB(feet,inches,weight,c);
    assertEquals(ret, answer,
        "BMI should return heightless");
    String shouldHaveHappened = "Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + feet + "," + inches + "," + weight + "," + bmi + ",'" + ret + "')";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void bmiTooLightHasCorrectExecuteUpdate() throws SQLException {
    int feet = 4;
    int inches = 6;
    double weight = 10.67;
    double bmi = -1.0;
    String ret = "weightless";
    String answer = f.bodymassDB(feet,inches,weight,c);
    assertEquals(ret, answer,
        "BMI should return weightless");
    String shouldHaveHappened = "Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + feet + "," + inches + "," + weight + "," + bmi + ",'" + ret + "')";
    verify(s).executeUpdate(shouldHaveHappened);
  }

  @Test
  public void bmiErrorShouldThrowSQLException() throws SQLException {
    lenient().when(c.createStatement()).thenThrow(new SQLException());
    assertThrows(SQLException.class,
           () -> f.bodymassDB(5,5,132.16,c),
           "Expected bodymassDB() to throw SQLException");
    verify(c).createStatement();
    verify(s, never()).executeUpdate(any(String.class));
  }

}
