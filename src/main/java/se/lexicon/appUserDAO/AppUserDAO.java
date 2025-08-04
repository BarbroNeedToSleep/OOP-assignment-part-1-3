package se.lexicon.appUserDAO;

import se.lexicon.model.AppUser;

import java.util.List;

public interface AppUserDAO {

    AppUser persist (AppUser appUser);

    AppUser findByUsername (String userName);

    List<AppUser> findAll();

    AppUser remove (AppUser appUser);
}
