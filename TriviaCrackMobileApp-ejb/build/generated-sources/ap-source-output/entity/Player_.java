package entity;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-15T15:55:06")
@StaticMetamodel(Player.class)
public class Player_ { 

    public static volatile SingularAttribute<Player, String> fname;
    public static volatile SingularAttribute<Player, String> lname;
    public static volatile SingularAttribute<Player, String> password;
    public static volatile SingularAttribute<Player, Integer> level3Correct;
    public static volatile SingularAttribute<Player, Integer> level5Correct;
    public static volatile SingularAttribute<Player, Integer> level1Correct;
    public static volatile SingularAttribute<Player, Integer> level2Correct;
    public static volatile SingularAttribute<Player, String> email;
    public static volatile SingularAttribute<Player, Serializable> picture;
    public static volatile SingularAttribute<Player, Integer> level4Correct;

}