package com.aeondynamics.cnit_355_project;

import java.io.Serializable;

public interface IUserData extends Serializable {
    // Define all of the things that we should include in the UserData class

    boolean fetchUserData();

    boolean updateUserData();

    // TODO add more methods as necessary
    // Don't forget to also add them to the UserData class!
}
