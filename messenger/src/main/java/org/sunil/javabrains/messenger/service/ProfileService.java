package org.sunil.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sunil.javabrains.messenger.database.DatabaseClass;
import org.sunil.javabrains.messenger.model.Message;
import org.sunil.javabrains.messenger.model.Profile;

public class ProfileService {

private Map<String, Profile> profiles = DatabaseClass.getProfile();
	
	public ProfileService()
	{
		profiles.put("SunilGaikwad",new Profile(1, "sunil", "sunil","gaikwad"));
		profiles.put("ShaileshTikhe",new Profile(2, "ShaileshTikhe", "Shailesh","Tikhe"));
	}

	public List<Profile> getAllProfiles() {
		
		return new ArrayList<>(profiles.values());
	}

	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);

	}
}
