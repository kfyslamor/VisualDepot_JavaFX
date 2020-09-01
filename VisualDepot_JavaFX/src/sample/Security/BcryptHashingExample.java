package sample.Security;

import java.security.NoSuchAlgorithmException;

public class BcryptHashingExample 
{
	/*public static void main(String[] args) throws NoSuchAlgorithmException
	{
		String originalPassword = "";
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		// hashing the password.
		System.out.println(generatedSecuredPasswordHash);
		System.out.println(generatedSecuredPasswordHash.length());
		// generatedSecuredPasswordHash, will be stored in the database.


		boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
		//checking if taking out the salt from the hashed password gives us the originalPassword.
		System.out.println(matched);
	}*/
	public String getSecurePassword(String originalPassword){
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		// hashing the password.
		System.out.println(generatedSecuredPasswordHash);


		boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
		//checking if taking out the salt from the hashed password gives us the originalPassword.
		if (matched){
			System.out.println(matched);
		}

		return generatedSecuredPasswordHash;
	}
}
/*
* Gets the originalPassword applies salt* returns a secured password and stores it.
*
* */