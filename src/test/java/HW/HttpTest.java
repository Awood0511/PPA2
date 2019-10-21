package HW;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//mockito imports
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpTest  {

  Server server;
  Server.MainHandler handler;

  @Mock ResultSet rs;
  @Mock ResultSetMetaData rsMeta;
  @Mock ResultSet rsJSON;
  @Mock ResultSetMetaData rsJSONMeta;
  @Mock Statement s;
  @Mock Connection c;

  @Mock HttpExchange ex;
  @Mock OutputStream os;
  @Mock Headers headMock;

  @InjectMocks ppa1Function f;

  @BeforeEach
  public void setUp() throws SQLException{
    server = new Server();
    handler = server.new MainHandler();

    lenient().when(c.createStatement()).thenReturn(s);
    lenient().when(s.executeUpdate(any(String.class))).thenReturn(1);
    lenient().when(s.executeQuery(any(String.class))).thenReturn(rs);

    server.setConn(c);

  }

  String homeText = "Welcome to the http interface.\n"
  + "To see a list of all Split The Tip database entries go to /splitthetip\n"
  + "To see a list of all BMI database entries go to /bmi\n"
  + "To call the function Split the Tip send a post request to /splitthetip with these arguments\n"
  + "\tVariable \tData Type\n"
  + "\tdinnerAmount \tdouble\n"
  + "\tguests \t\tint\n"
  + "To call the function BMI send a post request to /bmi with these arguments\n"
  + "\tVariable \tData Type\n"
  + "\tfeet \t\tint\n"
  + "\tinches \t\tint\n"
  + "\tweight \t\tdouble\n";

  @Test
  public void testHomeGivesRouteMenu() throws Exception {
    //Create stubs
    URI uri = new URI("/");

    //set up our mock function returns
    when(ex.getRequestURI()).thenReturn(uri);
    when(ex.getRequestMethod()).thenReturn("GET");
    doNothing().when(ex).sendResponseHeaders(any(Integer.class), any(Long.class));
    when(ex.getResponseBody()).thenReturn(os);
    doNothing().when(os).write(any(byte[].class));
    doNothing().when(os).close();

    //call handle
    handler.handle(ex);
    verify(os).write(homeText.getBytes());
  }

  @Test
  public void postToSplitTheTipUpdatesDatabaseCorrectly() throws Exception {
    //create stubs
    Headers head = new Headers();
    head.add("dinnerAmount","10.0");
    head.add("guests","2");
    String answer = "Each person pays $" + 5.75 + " and must unequally split a remainder of $" + 0.0 + ".\n";

    //set up our mock function returns
    URI uri = new URI("/splitthetip");
    when(ex.getRequestURI()).thenReturn(uri);
    when(ex.getRequestMethod()).thenReturn("POST");
    when(ex.getRequestHeaders()).thenReturn(head);
    doNothing().when(ex).sendResponseHeaders(any(Integer.class), any(Long.class));
    when(ex.getResponseBody()).thenReturn(os);
    doNothing().when(os).write(any(byte[].class));
    doNothing().when(os).close();

    //call handle
    handler.handle(ex);
    verify(os).write(answer.getBytes());
    verify(s, times(1)).executeUpdate("Insert into splitTheTip (dinnerAmount,guests,costPerGuest,remainder) values("+10.0+","+2+","+5.75+","+0.0+")");
  }

  @Test
  public void postToBMIUpdatesDatabaseCorrectly() throws Exception {
    //create stubs
    Headers head = new Headers();
    head.add("feet","5");
    head.add("inches","11");
    head.add("weight","140");
    String answer = "Your BMI is " + "20.0" + " which is considered " + "Normal Weight" + ".\n";

    //set up our mock function returns
    URI uri = new URI("/bmi");
    when(ex.getRequestURI()).thenReturn(uri);
    when(ex.getRequestMethod()).thenReturn("POST");
    when(ex.getRequestHeaders()).thenReturn(head);
    doNothing().when(ex).sendResponseHeaders(any(Integer.class), any(Long.class));
    when(ex.getResponseBody()).thenReturn(os);
    doNothing().when(os).write(any(byte[].class));
    doNothing().when(os).close();

    //call handle
    handler.handle(ex);
    verify(os).write(answer.getBytes());
    verify(s, times(1)).executeUpdate("Insert into bodymass (feet,inches,weight,bmi,bodytype) values(" + 5 + "," + 11 + "," + 140.0 + "," + 20.0 + ",'" + "Normal Weight" + "')");
  }
@Test
  public void getToSplitTHETIPQueriesDatabaseAndFormatsToJSON() throws Exception {
    //create stubs
    String answer;

    //set up our mock function returns
    URI uri = new URI("/splitthetip");
    when(ex.getRequestURI()).thenReturn(uri);
    when(ex.getRequestMethod()).thenReturn("GET");
    when(ex.getResponseHeaders()).thenReturn(headMock);
    doNothing().when(headMock).set(any(String.class),any(String.class));
    doNothing().when(ex).sendResponseHeaders(any(Integer.class), any(Long.class));
    when(ex.getResponseBody()).thenReturn(os);
    doNothing().when(os).write(any(byte[].class));
    doNothing().when(os).close();

    //set up rs data
    lenient().when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    lenient().when(rs.getString("createdAt")).thenReturn("2019-10-19 15:26:41.45419-04").thenReturn("2019-10-19 15:29:13.602471-04");
    lenient().when(rs.getDouble("dinnerAmount")).thenReturn(Double.valueOf("10.0")).thenReturn(Double.valueOf("10.0"));
    lenient().when(rs.getInt("guests")).thenReturn(2).thenReturn(2); 
    lenient().when(rs.getDouble("costPerGuest")).thenReturn(Double.valueOf("5.75")).thenReturn(Double.valueOf("5.75"));
    lenient().when(rs.getDouble("remainder")).thenReturn(Double.valueOf("0.0")).thenReturn(Double.valueOf("0.0"));
    lenient().when(rsJSON.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    lenient().when(rsJSON.getString("createdAt")).thenReturn("2019-10-19 15:26:41.45419-04").thenReturn("2019-10-19 15:29:13.602471-04");
    lenient().when(rsJSON.getDouble("dinnerAmount")).thenReturn(Double.valueOf("10.0")).thenReturn(Double.valueOf("10.0"));
    lenient().when(rsJSON.getInt("guests")).thenReturn(2).thenReturn(2); 
    lenient().when(rsJSON.getDouble("costPerGuest")).thenReturn(Double.valueOf("5.75")).thenReturn(Double.valueOf("5.75"));
    lenient().when(rs.getDouble("remainder")).thenReturn(Double.valueOf("0.0")).thenReturn(Double.valueOf("0.0"));
    lenient().when(rs.getMetaData()).thenReturn(rsMeta);
    lenient().when(rsJSON.getMetaData()).thenReturn(rsJSONMeta);
    lenient().when(rsMeta.getColumnCount()).thenReturn(2);
    lenient().when(rsJSONMeta.getColumnCount()).thenReturn(2);
    lenient().when(rsMeta.getTableName(1)).thenReturn("splitTheTip");
    lenient().when(rsJSONMeta.getTableName(1)).thenReturn("splitTheTip");

    //call handle
    handler.handle(ex);
    answer = handler.formatIntoJSON(rsJSON);
    verify(os).write(answer.getBytes());
    verify(headMock).set("Content-type","application/json");
    verify(s, times(1)).executeQuery("SELECT * FROM splitTheTip");
  }
  @Test
  public void getToBMIQueriesDatabaseAndFormatsToJSON() throws Exception {
    //create stubs
    String answer;

    //set up our mock function returns
    URI uri = new URI("/bmi");
    when(ex.getRequestURI()).thenReturn(uri);
    when(ex.getRequestMethod()).thenReturn("GET");
    when(ex.getResponseHeaders()).thenReturn(headMock);
    doNothing().when(headMock).set(any(String.class),any(String.class));
    doNothing().when(ex).sendResponseHeaders(any(Integer.class), any(Long.class));
    when(ex.getResponseBody()).thenReturn(os);
    doNothing().when(os).write(any(byte[].class));
    doNothing().when(os).close();

    //set up rs data
    lenient().when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    lenient().when(rs.getString("createdAt")).thenReturn("2019-10-19 15:26:41.458126-04").thenReturn("2019-10-19 15:29:13.603724-04");
    lenient().when(rs.getInt("feet")).thenReturn(0).thenReturn(5);
    lenient().when(rs.getInt("inches")).thenReturn(2).thenReturn(5);
    lenient().when(rs.getDouble("weight")).thenReturn(Double.valueOf("192.17")).thenReturn(Double.valueOf("140.66"));
    lenient().when(rs.getDouble("bmi")).thenReturn(Double.valueOf("-1.0")).thenReturn(Double.valueOf("23.97"));
    lenient().when(rs.getString("bodytype")).thenReturn("heightless").thenReturn("Normal Weight");
    lenient().when(rsJSON.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    lenient().when(rsJSON.getString("createdAt")).thenReturn("2019-10-19 15:26:41.458126-04").thenReturn("2019-10-19 15:29:13.603724-04");
    lenient().when(rsJSON.getInt("feet")).thenReturn(0).thenReturn(5);
    lenient().when(rsJSON.getInt("inches")).thenReturn(2).thenReturn(5);
    lenient().when(rsJSON.getDouble("weight")).thenReturn(Double.valueOf("192.17")).thenReturn(Double.valueOf("140.66"));
    lenient().when(rsJSON.getDouble("bmi")).thenReturn(Double.valueOf("-1.0")).thenReturn(Double.valueOf("23.97"));
    lenient().when(rsJSON.getString("bodytype")).thenReturn("heightless").thenReturn("Normal Weight");
    lenient().when(rs.getMetaData()).thenReturn(rsMeta);
    lenient().when(rsJSON.getMetaData()).thenReturn(rsJSONMeta);
    lenient().when(rsMeta.getColumnCount()).thenReturn(2);
    lenient().when(rsJSONMeta.getColumnCount()).thenReturn(2);
    lenient().when(rsMeta.getTableName(1)).thenReturn("bodymass");
    lenient().when(rsJSONMeta.getTableName(1)).thenReturn("bodymass");
    //call handle
    handler.handle(ex);
    answer = handler.formatIntoJSON(rsJSON);
    verify(os).write(answer.getBytes());
    verify(headMock).set("Content-type","application/json");
    verify(s, times(1)).executeQuery("SELECT * FROM bodymass");
  }

}
