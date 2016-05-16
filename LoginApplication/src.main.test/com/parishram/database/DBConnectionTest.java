package com.parishram.database;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests {@link DBConnection} which establishes a database
 * connection.
 * 
 * @author Yogesh Ghimire
 *
 */
public class DBConnectionTest {
	private static final String DRIVER = "DRIVER";
	private static final String JDBC_URL = "JDBC_URL";
	private static final String USERNAME = "USERNAME";
	private static final String PASSWORD = "PASSWORD";
	private static final String DRIVER_VALUE = "com.mysql.jdbc.Driver";
	private static final String URL_VALUE = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME_VALUE = "root";
	private static final String PASSWORD_VALUE = "root123";
	private static final String MOCK_DRIVER = "TEST_DRIVER";
	private static final String MOCK_URL = "TEST_URL";
	private static final String MOCK_USERNAME = "TEST_USERNAME";
	private static final String MOCK_PASSWORD = "TEST_PASSWORD";

	private DBConnection dbConnection = null;

	@Before
	public void setUp() throws Exception {
		dbConnection = new DBConnection();
	}

	@After
	public void tearDown() throws Exception {
		dbConnection = null;
	}

	/**
	 * This method tests the database connection when the user passes all the
	 * valid parameters to establish the database connection.
	 * 
	 * @throws NoSuchFieldException
	 *             The {@link NoSuchFieldException} is thrown if the name of the
	 *             field does not exist in the {@link DBConnection}.
	 * @throws SecurityException
	 *             The {@link SecurityException} is thrown by the
	 *             {@code security manager} to indicate a security violation.
	 * @throws Exception
	 *             The {@link Exception} is thrown in case there are other
	 *             exceptions thrown other than the ones mentioned above.
	 */
	@Test
	public void testDatabaseConnectionHappyPath() throws NoSuchFieldException, SecurityException, Exception {
		setStaticFinal(DBConnection.class.getDeclaredField(DRIVER), DRIVER_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(JDBC_URL), URL_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(USERNAME), USERNAME_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(PASSWORD), PASSWORD_VALUE);
		Connection connection = getConnection();
		Assert.assertNotNull(connection);
	}

	/**
	 * This method tests the database connection when the user passes an invalid
	 * {@code driver} while establishing the database connection.
	 * 
	 * @throws ClassNotFoundException
	 *             The {@link ClassNotFoundException} is thrown if the
	 *             {@link #DRIVER_VALUE} is invalid while establishing a
	 *             database connection.
	 */
	@Test(expected = ClassNotFoundException.class)
	public void testDatabaseConnectionWhenDriverIsInvalid() throws NoSuchFieldException, SecurityException, Exception {
		setStaticFinal(DBConnection.class.getDeclaredField(DRIVER), MOCK_DRIVER);
		getConnection();
	}

	/**
	 * This method tests the database connection when the user passes an invalid
	 * {@code username} while establishing the database connection.
	 * 
	 * @throws NoSuchFieldException
	 *             The {@link NoSuchFieldException} is thrown if the name of the
	 *             field does not exist in the {@link DBConnection}.
	 * @throws SecurityException
	 *             The {@link SecurityException} is thrown by the
	 *             {@code security manager} to indicate a security violation.
	 * @throws Exception
	 *             The {@link Exception} is thrown in case there are other
	 *             exceptions thrown other than the ones mentioned above.
	 */
	@Test(expected = SQLException.class)
	public void testDatabaseConnectionWhenUserNameIsInvalid()
			throws NoSuchFieldException, SecurityException, Exception {
		setStaticFinal(DBConnection.class.getDeclaredField(DRIVER), DRIVER_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(JDBC_URL), URL_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(USERNAME), MOCK_USERNAME);
		setStaticFinal(DBConnection.class.getDeclaredField(PASSWORD), PASSWORD_VALUE);
		getConnection();
	}

	/**
	 * This method tests the database connection when the user passes an invalid
	 * {@code password} while establishing the database connection.
	 * 
	 * @throws NoSuchFieldException
	 *             The {@link NoSuchFieldException} is thrown if the name of the
	 *             field does not exist in the {@link DBConnection}.
	 * @throws SecurityException
	 *             The {@link SecurityException} is thrown by the
	 *             {@code security manager} to indicate a security violation.
	 * @throws Exception
	 *             The {@link Exception} is thrown in case there are other
	 *             exceptions thrown other than the ones mentioned above.
	 */
	@Test(expected = SQLException.class)
	public void testDatabaseConnectionWhenPasswordIsInvalid()
			throws NoSuchFieldException, SecurityException, Exception {
		setStaticFinal(DBConnection.class.getDeclaredField(DRIVER), DRIVER_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(JDBC_URL), URL_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(USERNAME), USERNAME_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(PASSWORD), MOCK_PASSWORD);
		getConnection();
	}

	/**
	 * This method tests the database connection when the user passes an invalid
	 * {@code url} to establishing the database connection.
	 * 
	 * @throws NoSuchFieldException
	 *             The {@link NoSuchFieldException} is thrown if the name of the
	 *             field does not exist in the {@link DBConnection}.
	 * @throws SecurityException
	 *             The {@link SecurityException} is thrown by the
	 *             {@code security manager} to indicate a security violation.
	 * @throws Exception
	 *             The {@link Exception} is thrown in case there are other
	 *             exceptions thrown other than the ones mentioned above.
	 */
	@Test(expected = SQLException.class)
	public void testDatabaseConnectionWhenUrlIsInvalid() throws NoSuchFieldException, SecurityException, Exception {
		setStaticFinal(DBConnection.class.getDeclaredField(DRIVER), DRIVER_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(JDBC_URL), MOCK_URL);
		setStaticFinal(DBConnection.class.getDeclaredField(USERNAME), USERNAME_VALUE);
		setStaticFinal(DBConnection.class.getDeclaredField(PASSWORD), PASSWORD_VALUE);
		getConnection();
	}

	/**
	 * The purpose of this class is to return an instance of database connection
	 * after a successful attempt is made.
	 * 
	 * @return <output>dbConnection.getDatabaseConnection();</output>
	 * @throws ClassNotFoundException
	 *             The {@link ClassNotFoundException} is thrown if the
	 *             {@link #DRIVER} is invalid.
	 * @throws SQLException
	 *             If the {@code username} or {@code password} or {@code url} is
	 *             invalid.
	 */
	private Connection getConnection() throws ClassNotFoundException, SQLException {
		return dbConnection.getDatabaseConnection();
	}

	/**
	 * The purpose of this method is to replace the {@code static final} fields
	 * of {@link DBConnection} class with mock data to test the database
	 * connection.
	 * 
	 * @see <a href=
	 *      "http://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection">
	 *      Using reflection in unit test</a>
	 * @param field
	 * @param newValue
	 * @throws Exception
	 */
	private static void setStaticFinal(Field field, Object newValue) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}
}
