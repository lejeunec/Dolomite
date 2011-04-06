import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class LdapTest extends UnitTest {

	private LdapUser flo;
	
	//Method before Test
	@Before
	public void createUser()
	{
		
		// Create a new user and save it
		new LdapUser("flora.dupont@utt.fr", "test", "Flora", "Dupont", "flora.dupont").addUser();
		// Retrieve the user with login+passwd
		flo = LdapUser.connect("flora.dupont", "test");
		
		// Test	
		assertNotNull(flo);
		
	}
	
	@Test
    public void retrieveUser() 
	{
	    // Test	
		assertNotNull(flo); 
		assertEquals("Flora", flo.getFirstname());
		assertEquals("Dupont", flo.getLastname());
		assertEquals("flora.dupont@utt.fr", flo.getEmail());
	    
    }
	
	@Test
	public void tryConnectAsUser() {
		
		LdapUser stef = LdapUser.connect("stephane.batteux", "pas_le_bon");
		LdapUser flo2 = LdapUser.connect("flora.dupont", "mauvais_mot_de_passe");

		// Test 
		assertNotNull(flo);
		assertNull(stef);
		assertNull(flo2);	
	}
	
	@Test 
	public void tryUpdateUser(){
		
		LdapUser admin = LdapUser.connect("admin", "if052010");
		
		flo.updateUser("flora.dupont@utt.fr", "hehehe", "arolf", "tnopud");
		
		LdapUser floModified = LdapUser.connect("flora.dupont", "hehehe");
		LdapUser floWithOldPwd = LdapUser.connect("flora.dupont", "test");
		
		//Test
		assertNull(floWithOldPwd);
		assertNotNull(floModified);
		assertEquals("flora.dupont@utt.fr", floModified.getEmail());
		assertEquals("arolf", floModified.getFirstname());
		assertEquals("tnopud", floModified.getLastname());
		
	}
	
	//Method after Test
	@After
	public void tryDeleteUser(){
		
		flo.deleteUser();
		assertNotNull(flo);
		
		LdapUser flo2 = LdapUser.connect("flora.dupont", "test");
		assertNull(flo2);
	
	}
}