package edu.gatech.arktos;

import edu.gatech.arktos.Constants;
import edu.gatech.arktos.Session;
import junit.framework.TestCase;

public class SessionTest extends TestCase {

	Session session;
	protected void setUp() throws Exception {
		session = new Session();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLogin() {
		assertEquals(0, session.login(Constants.USERNAME, Constants.PASSWORD));
		assertEquals(1, session.login(Constants.USERNAME, "wrong password"));
	}

	public void testLogout() {
		assertEquals(0, session.logout());
	}

}
