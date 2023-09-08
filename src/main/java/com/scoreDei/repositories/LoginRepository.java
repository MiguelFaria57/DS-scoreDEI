package com.scoreDei.repositories;

import com.scoreDei.data.Login;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepository extends CrudRepository<Login, Integer>{
    @Query("SELECT l.username FROM Login l WHERE l.username=?1")
    public String findUsername(String username);

    @Query("SELECT l.email FROM Login l WHERE l.username=?1")
    public String findMail(String username);

    @Query("SELECT l.phone FROM Login l WHERE l.username=?1")
    public String findPhone(String username);

    @Query("SELECT l.password FROM Login l WHERE l.username=?1")
    public String findPassword(String username);

    @Query("SELECT l.is_admin FROM Login l WHERE l.username=?1")
    public boolean getPermissions(String username);
}
