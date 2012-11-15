package edu.gatech.arktos;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

/**
 * Session class to handle session login and logout and accessing worksheets
 * @author Pujita
 *
 */
public class Session {

	private SpreadsheetService service;
    private static URL SPREADSHEET_FEED_URL;

    public Session() throws MalformedURLException {
    	// Set URL for API access
	    SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
    }
    	
    /**
     * Login to the google account
     * @param username
     * @param password
     * @return 0 if successful and 1 if fail
     */
	public int login(String username, String password) {

		try {
			service = new SpreadsheetService("GradesDBSpreadsheet");
		    service.setUserCredentials(username, password);
		}
		catch (Throwable e) {
			service = null;
			return 1;
		}
	    
		return 0;
	}

	
	/**
	 * Logout from the google account
	 * @return 0 if successful
	 */
	public int logout() {
		service = null;
		return 0;
	}
	
	
	/**
	 * Get the database by name if the spreadsheet name is provided
	 * @param dbName
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	public GradesDB getDBByName(String dbName) throws IOException, ServiceException {
		
	    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
	    List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		// Check to see if Spreadsheets is empty 
		if (spreadsheets.size() == 0) {
	        return null;
	    }
		
	    // Iterate through Spreadsheets to find our matching title
	    for ( SpreadsheetEntry spreadsheet: spreadsheets ) {
	    	if( spreadsheet.getTitle().getPlainText().equals(dbName)) {
	    		GradesDB gradesDB = new GradesDB(spreadsheet, service);
	    		return gradesDB;	    	   	    		
	    	}
	    }
	    return null;
	}
}
